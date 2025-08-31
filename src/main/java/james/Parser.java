package james;

import java.io.IOException;

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
                (Integer.parseInt(words[1]) <= size);
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
                (Integer.parseInt(words[1]) <= size);
    }

    /**
     * Checks the validity of a query and throws a JamesException if the query is malformed.
     *
     * @param query The full input query string.
     * @param size  The current number of tasks in the task list.
     * @throws JamesException if the query is invalid or incomplete.
     */
    public static void checkQuery(String query, int size) throws JamesException {
        String[] splitQuery = query.split(" ", 2);
        String firstWord = splitQuery[0];
        if (firstWord.equalsIgnoreCase("todo")) {
            if (splitQuery.length == 1) {
                throw new JamesException("You forgot the task buddy!");
            }
        } else if (firstWord.equalsIgnoreCase("event")) {
            if (splitQuery.length == 1) {
                throw new JamesException("You forgot the task buddy!");
            } else if (splitQuery[1].split(" /").length < 3) {
                throw new JamesException("event query incomplete!");
            }
        } else if (firstWord.equalsIgnoreCase("deadline")) {
            if (splitQuery.length == 1) {
                throw new JamesException("You forgot the task buddy!");
            } else if (splitQuery[1].split(" /").length < 2) {
                throw new JamesException("deadline query incomplete!");
            }
        } else if (firstWord.equalsIgnoreCase("list")) {
            if (splitQuery.length > 1) {
                throw new JamesException("I can only reply if you just say 'list', sorry! ");
            }
        } else if (firstWord.equalsIgnoreCase("delete")) {
            if (splitQuery.length == 1) {
                throw new JamesException("Please specify which task to delete!");
            }
        } else if (firstWord.equalsIgnoreCase("mark") || firstWord.equalsIgnoreCase("unmark")) {
            if (!isValidMarkQuery(splitQuery, size)) {
                throw new JamesException("I can only mark the tasks we have, sorry!");
            }
        } else if (firstWord.equalsIgnoreCase("bye")) {
            // do nothing
        } else {
            throw new JamesException("Sorry!, I am not smart enough for this yet!");
        }
    }

    /**
     * Parses the input query and returns the type of command it represents.
     *
     * @param query The full input query string.
     * @param size  The current number of tasks in the task list.
     * @return A string representing the command type (e.g., "todo", "event", "mark").
     * @throws JamesException if the query is invalid.
     */
    public static String parse(String query, int size) throws JamesException {
        Parser.checkQuery(query, size);
        if (query.equalsIgnoreCase("bye")) {
            return "bye";
        } else if (query.equalsIgnoreCase("list")) {
            return "list";
        } else if (Parser.isValidMarkQuery(query.split(" "), size)) {
            return "mark";
        } else if (Parser.isValidDeleteQuery(query.split(" "), size)) {
            return "delete";
        } else {
            if (query.startsWith("todo")) {
                return "todo";
            } else if (query.startsWith("event")) {
                return "event";
            } else if (query.startsWith("deadline")) {
                return "deadline";
            } else {
                return "invalid";
            }
        }
    }

    /**
     * Returns whether the program should exit.
     */
    public static boolean isExit() {
        return Parser.canExit;
    }

    /**
     * Checks or unchecks task based on input.
     *
     * @param words Array containing the parsed elements of scanned input.
     * @param tasks Array containing tasks.
     * @return James.Task Updated James.Task.
     */
    public static Task markTask(String[] words, TaskList tasks) {
        int taskNo = Integer.parseInt(words[1].trim()) - 1;
        if (words[0].equalsIgnoreCase("mark")) {
            System.out.println("marked the following task!");
            //tasks[taskNo].finishTask();
            tasks.get(taskNo).finishTask();
        } else {
            System.out.println("unmarked the following task!");
            //tasks[taskNo].undoTask();
            tasks.get(taskNo).undoTask();
        }
        return tasks.get(taskNo);
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
    public static void execute(String type, String query, TaskList tasks, Ui ui, Database db) {
        if (type.equals("bye")) {
            ui.showBye();
            Parser.canExit = true;
            try {
                db.store(tasks);
            } catch (IOException e){
                ui.showError(e.getMessage());
            }
        } else if (type.equals("list")) {
            ui.displayList(tasks);
        } else if (type.equals("mark")) {
            Task editedTask = Parser.markTask(query.split(" "), tasks);
            System.out.println(editedTask);
        } else if (type.equals("delete")) {
            Task deletedTask = tasks.deleteTask(query);
            System.out.println(deletedTask);
        } else if (type.equals("todo")) {
            //add task
            tasks.add(new Todo(query));
            System.out.println("output:\n" + "added: " + tasks.get(tasks.getSize() - 1));
            System.out.println(Task.TaskToString(tasks.get(tasks.getSize() - 1)));
            System.out.println("Added as task number " + tasks.getSize());
        } else if (type.equals("event")) {
            tasks.add(new Event(query));
            System.out.println("output:\n" + "added: " + tasks.get(tasks.getSize() - 1));
            System.out.println(Task.TaskToString(tasks.get(tasks.getSize() - 1)));
            System.out.println("Added as task number " + tasks.getSize());
        } else if (type.equals("deadline")) {
            tasks.add(new Deadline(query));
            System.out.println("output:\n" + "added: " + tasks.get(tasks.getSize() - 1));
            System.out.println(Task.TaskToString(tasks.get(tasks.getSize() - 1)));
            System.out.println("Added as task number " + tasks.getSize());
        } else {
            // you won't come here
        }
    }
}
