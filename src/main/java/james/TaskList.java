package james;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class TaskList {
    private ArrayList<Task> tasks;
    private int size;
    public TaskList(ArrayList<Task> t) {
        this.tasks = t;
        this.size = t.size();
    }

    public TaskList() {
        this.tasks = new ArrayList<>();
        this.size = 0;
    }

    public void add(Task t){
        tasks.add(t);
        this.size++;
    }

    public Task get(int n) {
        return this.tasks.get(n);
    }

    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    public void displayTask() {
        int count = 1;
        for (Task tsk: this.tasks) {
            if (tsk != null) {
                System.out.println("<" + count + "> " + tsk.toString());
            }
            count++;
        }
    }

    public Task deleteTask(String query) {
        String[] words = query.split(" ");
        int taskNo = Integer.parseInt(words[1].trim()) - 1;
        System.out.println("deleted the following task!");
        this.size--;
        return this.tasks.remove(taskNo);
    }

    public void displayTasksWithString(String query) {
        String searchItem = query.split(" ", 2)[1];
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
                System.out.println("<" + i + "> " + task.toString());
            }
        }

    }
    public int getSize() {
        return this.size;
    }
}
