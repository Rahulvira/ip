package James;

import java.util.ArrayList;

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

    public int getSize() {
        return this.size;
    }
}
