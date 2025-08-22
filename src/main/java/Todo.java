public class Todo extends Task{
    public Todo(String s) {
        super(s.split(" /")[0].replaceFirst("^\\s*\\S+\\s*", ""));
    }

    /** Sets the task status to false. */
    public void undoTask() {
        super.undoTask();
    }

    /** Sets the task status to true. */
    public void finishTask() {
        super.finishTask();
    }

    @Override
    public String getTask() {
        return super.getTask();
    }

    @Override
    public boolean getStatus() {
        return super.getStatus();
    }

    @Override
    public String toString() {
        return "[T] " + super.toString();
    }
}
