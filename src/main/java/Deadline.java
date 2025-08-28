import java.util.Arrays;

public class Deadline extends Task{
    private String extendedMessage;
    public Deadline(String s) {
        super(s.split(" /")[0].replaceFirst("^\\s*\\S+\\s*", ""));
        this.extendedMessage = s;
    }
    public Deadline(String s, boolean b) {
        super(s.split(" /")[0].replaceFirst("^\\s*\\S+\\s*", ""), b);
        this.extendedMessage = s;
    }

    @Override
    public String getExtendedMessage() {
        return this.extendedMessage;
    }

    @Override
    public TaskType getType() {
        return TaskType.DEADLINE;
    }

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

    public String getDeadlineDetails() {
        String[] untrimmedWords = this.extendedMessage.split(" /");
        String[] words = Arrays.stream(untrimmedWords)
                .map(s -> s == null ? null : s.trim())
                .toArray(String[]::new);
        String[] deadlineArray = words[1].split(" ", 2);
        String date = parseDateTime(deadlineArray[1]);
        String deadline = (deadlineArray[0] + ": " + date).trim();
        return "(" + deadline + ")";
    }

    @Override
    public String toString() {
        return "[D] " + super.toString() + " " +  this.getDeadlineDetails();
    }
}
