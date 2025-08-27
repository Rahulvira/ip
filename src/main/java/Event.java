import java.util.Arrays;

public class Event extends Task{
    private String extendedMessage;
    public Event(String s) {
        // Pass only the message to super class, remove event details
        super(s.split(" /")[0].replaceFirst("^\\s*\\S+\\s*", ""));
        this.extendedMessage = s;
    }

    public Event(String s, boolean b) {
        // Pass only the message to super class, remove event details
        super(s.split(" /")[0].replaceFirst("^\\s*\\S+\\s*", ""), b);
        this.extendedMessage = s;
    }

    @Override
    public String getExtendedMessage() {
        return this.extendedMessage;
    }

    @Override
    public TaskType getType() {
        return TaskType.EVENT;
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

    public String getEventDetails() {
        String[] untrimmedWords = this.extendedMessage.split(" /");
        //System.out.println(Arrays.toString(words));
        String[] words = Arrays.stream(untrimmedWords)
                .map(s -> s == null ? null : s.trim())
                .toArray(String[]::new);

        String[] eventStartArray = words[1].split(" ", 2);
        String eventStart = eventStartArray[0]+ ": " + eventStartArray[1];
        String[] eventEndArray = words[2].split(" ", 2);
        String eventEnd = (eventEndArray[0] + ": " + eventEndArray[1]);
        return "(" + eventStart + " " + eventEnd + ")";
    }
    @Override
    public String toString() {
        return "[E] " + super.toString() + " " + this.getEventDetails();
    }
}
