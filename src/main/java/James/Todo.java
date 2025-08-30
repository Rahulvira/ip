package James;

public class Todo extends Task{
    private String extendedMessage;
    /**
     * Creates a to-do task from a raw user command.
     *
     * @param s String user input.
     */
    public Todo(String s) {
        super(s.split(" /")[0].replaceFirst("^\\s*\\S+\\s*", ""));
        this.extendedMessage = s;
    }

    /**
     * Creates a to-do task from a raw user command with an explicit status.
     *
     * @param s String user input.
     * @param b Completion status. {@code true} if finished, otherwise {@code false}.
     */
    public Todo(String s, boolean b) {
        super(s.split(" /")[0].replaceFirst("^\\s*\\S+\\s*", ""), b);
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

    /**
     * Sets the task status to not done.
     */
    public void undoTask() {
        super.undoTask();
    }

    /**
     * Sets the task status to done.
     */
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
