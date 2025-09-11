package james;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Parser {
    private static boolean canExit;
    /**
     * Checks if the mark or unmark query is valid.
     *
     * @param words Array containing the parsed elements of scanned input.
     * @return Boolean based on query validity.
     */
    public static boolean isValidMarkQuery(String[] words, int size) {
        return (words.length == 2)
                &&
                (words[0].equalsIgnoreCase("mark") || words[0].equalsIgnoreCase("unmark"))
                &&
                (words[1].matches("^[+-]?\\d+$")) // to check if it is an integer
                &&
                (Integer.parseInt(words[1]) <= size)
                &&
                (!words[1].equals("0"));
    }

    /**
     * Checks if the delete query is valid.
     *
     * @param words Array containing the parsed elements of scanned input.
     * @return Boolean based on query validity.
     */
    public static boolean isValidDeleteQuery(String[] words, int size) {
        return (words.length == 2)
                &&
                (words[0].equalsIgnoreCase("delete"))
                &&
                (words[1].matches("^[+-]?\\d+$")) // to check if it is an integer
                &&
                (Integer.parseInt(words[1]) <= size)
                &&
                (!words[1].equals("0"));
    }

    /**
     * Parses the input query, validates it, and returns the type of command it represents.
     *
     * This method is the single entry point for both command identification and query validation.
     * It ensures the query adheres to the expected format and throws a {@link JamesException}
     * if the input is malformed or incomplete.
     *
     * @param query The full input query string provided by the user.
     * @param size  The current number of tasks in the task list.
     * @return A lowercase string representing the command type (e.g., "todo", "event", "mark").
     * @throws JamesException if the query is invalid or incomplete.
     */
    public static String parse(String query, int size) throws JamesException {
        String[] splitQuery = query.trim().split(" ", 2);
        String command = splitQuery[0].toLowerCase();

        switch (command) {
            case "bye":
                return "bye";

            case "list":
                validateList(splitQuery);
                return "list";

            case "find":
                validateFind(splitQuery);
                return "find";

            case "todo":
                validateTodo(splitQuery);
                return "todo";

            case "event":
                validateEvent(splitQuery);
                return "event";

            case "deadline":
                validateDeadline(splitQuery);
                return "deadline";

            case "delete":
                validateDelete(splitQuery, size);
                return "delete";

            case "mark":
            case "unmark":
                validateMark(splitQuery, size);
                return "mark";

            default:
                throw new JamesException("Sorry! I am not smart enough for this yet!");
        }
    }

    /**
     * Validates a "todo" command query.
     *
     * @param splitQuery The query split into command and arguments.
     * @throws JamesException if no description is provided.
     */
    private static void validateTodo(String[] splitQuery) throws JamesException {
        if (splitQuery.length == 1) {
            throw new JamesException("You forgot the task buddy!");
        }
    }

    /**
     * Validates an "event" command query.
     *
     * @param splitQuery The query split into command and arguments.
     * @throws JamesException if no description is provided or if the event details
     *                        (description, /at, and date/time) are incomplete.
     */
    private static void validateEvent(String[] splitQuery) throws JamesException {
        if (splitQuery.length == 1) {
            throw new JamesException("You forgot the task buddy!");
        }
        if (splitQuery[1].split(" /").length < 3) {
            throw new JamesException("Event query incomplete!");
        }
    }

    /**
     * Validates a "deadline" command query.
     *
     * @param splitQuery The query split into command and arguments.
     * @throws JamesException if no description is provided or if the deadline details
     *                        (description and /by) are incomplete.
     */
    private static void validateDeadline(String[] splitQuery) throws JamesException {
        if (splitQuery.length == 1) {
            throw new JamesException("You forgot the task buddy!");
        }
        if (splitQuery[1].split(" /").length < 2) {
            throw new JamesException("Deadline query incomplete!");
        }
    }

    /**
     * Validates a "list" command query.
     *
     * @param splitQuery The query split into command and arguments.
     * @throws JamesException if additional arguments are provided with the "list" command.
     */
    private static void validateList(String[] splitQuery) throws JamesException {
        if (splitQuery.length > 1) {
            throw new JamesException("I can only reply if you just say 'list', sorry!");
        }
    }

    /**
     * Validates a "find" command query.
     *
     * @param splitQuery The query split into command and arguments.
     * @throws JamesException if no keyword is provided to search for.
     */
    private static void validateFind(String[] splitQuery) throws JamesException {
        if (splitQuery.length == 1) {
            throw new JamesException("Please specify what I should find");
        }
    }

    /**
     * Validates a "delete" command query.
     *
     * @param splitQuery The query split into command and arguments.
     * @param size       The current number of tasks in the task list.
     * @throws JamesException if no task index is provided or if the index is invalid.
     */
    private static void validateDelete(String[] splitQuery, int size) throws JamesException {
        if (splitQuery.length == 1) {
            throw new JamesException("Please specify which task to delete!");
        }
        if (!Parser.isValidDeleteQuery(splitQuery, size)) {
            throw new JamesException("Invalid delete query!");
        }
    }

    /**
     * Validates a "mark" or "unmark" command query.
     *
     * @param splitQuery The query split into command and arguments.
     * @param size       The current number of tasks in the task list.
     * @throws JamesException if the index provided does not match a valid task.
     */
    private static void validateMark(String[] splitQuery, int size) throws JamesException {
        if (!Parser.isValidMarkQuery(splitQuery, size)) {
            throw new JamesException("I can only mark the tasks we have, sorry!");
        }
    }

    /**
     * Returns whether the program should exit.
     */
    public static boolean isExit() {
        return Parser.canExit;
    }

    /**
     * Formats the output message for a newly added task.
     *
     * @param task       The task that was added.
     * @param taskNumber The index or identifier assigned to the task.
     * @return A formatted string indicating the task addition and its assigned number.
     */
    private static String formatTaskOutput(Task task, int taskNumber) {
        StringBuilder sb = new StringBuilder();
        sb.append("Added: ").append(task).append("\n");
        sb.append("Added as task number ").append(taskNumber);
        return sb.toString();
    }

    /**
     * Executes the parsed command by interacting with the task list, UI, and database.
     *
     * @param type   The type of command to execute.
     * @param query  The full input query string.
     * @param tasks  The current list of tasks.
     * @param ui     The UI handler for displaying messages.
     * @param db     The database handler for storing tasks.
     */
    public static JamesResponse execute(String type, String query, TaskList tasks, Ui ui, Database db) {
        assert type != null : "command should be of a specified type";
        assert query != null : "cannot execute an empty input";
        assert ui != null : "ui object cannot be null";
        assert db != null : "db cannot be null";
        assert tasks != null : "cannot have have a null TaskList object";
        switch (type) {
            case "bye":
                return handleBye(tasks, ui, db);

            case "list":
                return handleList(tasks, ui);

            case "find":
                return handleFind(query, tasks, ui);

            case "mark":
                return handleMark(query, tasks);

            case "delete":
                return handleDelete(query, tasks);

            case "todo":
                return handleTaskCreation(new Todo(query), tasks);

            case "event":
                return handleTaskCreation(new Event(query), tasks);

            case "deadline":
                return handleTaskCreation(new Deadline(query), tasks);

            default:
                return new JamesResponse("invalid command");
        }
    }

    /**
     * Handles the "bye" command by saving tasks, showing a farewell, and preparing exit.
     */
    private static JamesResponse handleBye(TaskList tasks, Ui ui, Database db) {
        ui.showBye();
        Parser.canExit = true;
        try {
            db.store(tasks);
        } catch (IOException e) {
            ui.showError(e.getMessage());
        }
        return new JamesResponse("bye");
    }

    /**
     * Handles the "list" command by displaying and returning the full task list.
     */
    private static JamesResponse handleList(TaskList tasks, Ui ui) {
        ArrayList<Boolean> flags = new ArrayList<>(Collections.nCopies(tasks.getSize(), true));
        ui.displayList(tasks);
        return new JamesResponse(tasks.formatAsStringResponse(flags));
    }

    /**
     * Handles the "find" command by filtering tasks based on the query.
     */
    private static JamesResponse handleFind(String query, TaskList tasks, Ui ui) {
        ArrayList<Boolean> displayFlags = tasks.getDisplayFlags(query);
        ui.displayFilteredList(tasks, displayFlags);
        return new JamesResponse(tasks.formatAsStringResponse(displayFlags));
    }

    /**
     * Handles the "mark"/"unmark" command by updating the task's status.
     */
    private static JamesResponse handleMark(String query, TaskList tasks) {
        Task editedTask = tasks.markTask(query);
        String action = editedTask.getStatus() ? "marked:\n" : "unmarked:\n";
        return new JamesResponse(action + editedTask);
    }

    /**
     * Handles the "delete" command by removing a task from the task list.
     */
    private static JamesResponse handleDelete(String query, TaskList tasks) {
        Task deletedTask = tasks.deleteTask(query);
        return new JamesResponse("deleted: " + deletedTask);
    }

    /**
     * Handles task creation for "todo", "event", and "deadline" commands.
     */
    private static JamesResponse handleTaskCreation(Task newTask, TaskList tasks) {
        tasks.add(newTask);
        return new JamesResponse(formatTaskOutput(newTask, tasks.getSize()));
    }
}
