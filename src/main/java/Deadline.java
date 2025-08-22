import java.util.Arrays;

public class Deadline extends Task{
    private String extendedMessage;
    public Deadline(String s) {
        super(s.split(" /")[0].replaceFirst("^\\s*\\S+\\s*", ""));
        this.extendedMessage = s;
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
        String deadline = (deadlineArray[0] + ": " + deadlineArray[1]).trim();
        return "(" + deadline + ")";
    }

    @Override
    public String toString() {
        return "[E] " + super.toString() + " " +  this.getDeadlineDetails();
    }
}
