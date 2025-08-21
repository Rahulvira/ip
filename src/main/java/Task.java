public class Task {
    /** Status of completion of task */
    private boolean status;
    /** Message of task */
    private String task;

    public Task(String t) {
        this.task = t;
    }

    /** Sets the task status to false. */
    public void undoTask() {
        this.status = false;
    }

    /** Sets the task status to true. */
    public void finishTask() {
        this.status = true;
    }

    @Override
    public String toString() {
        if (status) {
            return "[X] " + task;
        } else {
            return "[ ] " + task;
        }
    }
}
