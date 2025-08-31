package james;

public class Todo extends Task{
    private String extendedMessage;
    public Todo(String s) {
        super(s.split(" /")[0].replaceFirst("^\\s*\\S+\\s*", ""));
        this.extendedMessage = s;
    }

    public Todo(String s, boolean isMarked) {
        super(s.split(" /")[0].replaceFirst("^\\s*\\S+\\s*", ""), isMarked);
        this.extendedMessage = s;
    }

    @Override
    public String getExtendedMessage() {
        return this.extendedMessage;
    }

    @Override
    public TaskType getType() {
        return TaskType.TODO;
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
