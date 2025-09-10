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
    public void displayTask() {
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
     * Deletes a task based on the input query string.
     * Assumes the query is in the format "delete <Index>".
     *
     * @param query String containing the delete command and task number.
     * @return Task object that was removed.
     */
    public Task deleteTask(String query) {
        String[] words = query.split(" ");
        int taskNo = Integer.parseInt(words[1].trim()) - 1;
        System.out.println("deleted the following task!");
        this.size--;
        return this.tasks.remove(taskNo);
    }

    /**
     * Displays tasks that contain a specific keyword in their description.
     * Assumes the query is in the format "find <searchItem>".
     *
     * @param query String containing the search command and keyword.
     */
    public ArrayList<Boolean> getDisplayFlags(String query) {
        String searchItem = query.split(" ", 2)[1];
        ArrayList<Boolean> flagsList = new ArrayList<>();
        //System.out.println(searchItem);
        for (int i = 0; i < this.size; i++) {
            Task task = this.tasks.get(i);
            if (task == null) {
                continue;
            }
            String[] taskWords = task.getTask().split(" ");
            //System.out.println(Arrays.toString(taskWords));
            boolean isFound = Stream.of(taskWords)
                                    .anyMatch(item -> item.equalsIgnoreCase(searchItem));
            if (isFound) {
                System.out.println("<" + (i + 1) + "> " + task.toString());
                flagsList.add(true);
            } else {
                flagsList.add(false);
            }
        }
        return flagsList;
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
