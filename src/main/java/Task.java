public class Task {
    /** Status of completion of task */
    private boolean status;
    /** Message of task */
    private String task;

    public Task(String t) {
        this.task = t;
    }

    public boolean getStatus() {
        return this.status;
    }

    public String getTask() {
        return this.task;
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
