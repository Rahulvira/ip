package james;

import java.util.ArrayList;
import java.util.stream.Stream;

public class TaskList {
    private ArrayList<Task> tasks;
    private int size;

    /**
     * Constructs a TaskList using an existing list of tasks.
     *
     * @param t ArrayList of Task objects to initialize the task list.
     */
    public TaskList(ArrayList<Task> t) {
        this.tasks = t;
        this.size = t.size();
    }

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
        this.size = 0;
    }

    /**
     * Adds a new task to the task list and increments the size counter.
     *
     * @param t Task object to be added.
     */
    public void add(Task t){
        tasks.add(t);
        this.size++;
    }

    /**
     * Retrieves the task at the specified index.
     *
     * @param n Index of the task to retrieve (0-based).
     * @return Task object at the given index.
     */
    public Task get(int n) {
        return this.tasks.get(n);
    }

    /**
     * Returns the entire list of tasks.
     *
     * @return ArrayList containing all Task objects.
     */
    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    /**
     * Displays all tasks in the list with their corresponding index.
     * Skips null entries if any exist.
     */
    public void displayTasks() {
        int count = 1;
        for (Task tsk: this.tasks) {
            if (tsk != null) {
                System.out.println("<" + count + "> " + tsk.toString());
            }
            count++;
        }
    }
    /**
     * Checks or unchecks task based on input.
     *
     * @param query String containing the scanned input.
     * @return James.Task Updated James.Task.
     */
    public Task markTask(String query) {
        String[] words = query.split(" ");
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
     * Checks or unchecks task based on input.
     *
     * @param query String containing the scanned input.
     * @return James.Task Updated James.Task.
     */
    public ArrayList<Task> markTasks(String query) {
        String[] words = query.split(" ", 2);
        String[] taskStringNumbers = words[1].split(" ");
        String command = words[0].toLowerCase();
        ArrayList<Task> modifiedTasks = new ArrayList<Task>();
        for (String index : taskStringNumbers) {
            int taskNumber = Integer.parseInt(index) - 1;
            if (command.equals("mark")) {
                tasks.get(taskNumber).finishTask();
            }

            if (command.equals("unmark")) {
                tasks.get(taskNumber).undoTask();
            }
            modifiedTasks.add(tasks.get(taskNumber));
        }
        return modifiedTasks;
    }

    public ArrayList<Task> deleteTasks(String query) {
        String[] words = query.split(" ", 2);
        String[] taskStringNumbers = words[1].split(" ");
        ArrayList<Task> deletedTasks = new ArrayList<Task>();
        for (String index : taskStringNumbers) {
            int taskNumber = Integer.parseInt(index) - 1;
            this.size--;
            deletedTasks.add(this.tasks.remove(taskNumber));
        }
        return deletedTasks;
    }

    /**
     * Deletes a task based on the input query string.
     * Assumes the query is in the format "delete <Index>".
     *
     * @param query String containing the delete command and task number.
     * @return Task object that was removed.
     */
    public Task deleteTask(String query) {
        String[] words = query.split(" ");
        int taskNo = Integer.parseInt(words[1].trim()) - 1;
        assert taskNo != 0 : "Cannot delete 0th task";
        System.out.println("deleted the following task!");
        this.size--;
        return this.tasks.remove(taskNo);
    }


    /**
     * Generates a list of boolean flags indicating which tasks contain a given search term.
     * Assumes the query is in the format "find <searchItem>".
     *
     * @param query String containing the search command and keyword.
     * @return ArrayList<Boolean> list where each element corresponds to a task and indicates
     * whether it matches the search keyword
     */
    public ArrayList<Boolean> getDisplayFlags(String query) {
        String searchItem = query.split(" ", 2)[1];
        ArrayList<Boolean> flagsList = new ArrayList<>();
        for (int i = 0; i < this.size; i++) {
            Task task = this.tasks.get(i);
            if (task == null) {
                continue;
            }
            String[] taskWords = task.getTask().split(" ");
            boolean isFound = Stream.of(taskWords)
                                    .anyMatch(item -> item.equalsIgnoreCase(searchItem));
            if (isFound) {
                flagsList.add(true);
            } else {
                flagsList.add(false);
            }
        }
        return flagsList;
    }

    /**
     * Displays tasks that contain a specific keyword in their description.
     *
     * @param displayFlags ArrayList containing a list of boolean flags
     */
    public void displayTasksWithString(ArrayList<Boolean> displayFlags) {
        for (int i = 0; i < tasks.size(); i++) {
            if (displayFlags.get(i)) {
                System.out.println("<" + (i + 1) + "> " + tasks.get(i).toString());
            }
        }
    }

    /**
     * Formats the list of tasks for display, filtering only those marked as visible.
     *
     * @param displayFlags An ArrayList of booleans indicating which tasks should be shown (true = visible).
     * @return A formatted string listing all visible tasks with their corresponding indices.
     */
    public String formatAsStringResponse(ArrayList<Boolean> displayFlags) {
        StringBuilder listOutput = new StringBuilder("Here are your tasks:\n");
        for (int i = 0; i < this.tasks.size(); i++) {
            if (displayFlags.get(i)) {
                listOutput.append("<").append(i + 1).append("> ").append(this.tasks.get(i).toString()).append("\n");
            }
        }
        return listOutput.toString();
    }

    /**
     * Returns the current number of tasks in the list.
     *
     * @return Integer representing the size of the task list.
     */
    public int getSize() {
        return this.size;
    }
}
