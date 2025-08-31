package james;

import java.util.Arrays;

public class Deadline extends Task{
    /** Full input string query */
    private String extendedMessage;

    /**
     * Constructs a Deadline object from an input string.
     * Extracts the task description and stores the full message for processing.
     *
     * @param s the full deadline string query
     */
    public Deadline(String s) {
        super(s.split(" /")[0].replaceFirst("^\\s*\\S+\\s*", ""));
        this.extendedMessage = s;
    }
    /**
     * Constructs a Deadline object from an input string.
     * Extracts the task description and stores the full message for processing.
     *
     * @param s the full deadline string query
     * @param isMarked the completion status (true if done, false otherwise)
     */
    public Deadline(String s, boolean isMarked) {
        super(s.split(" /")[0].replaceFirst("^\\s*\\S+\\s*", ""), isMarked);


    @Override
    public String getExtendedMessage() {
        return this.extendedMessage;
    }

    @Override
    public TaskType getType() {
        return TaskType.DEADLINE;
    }

    @Override
    public void undoTask() {
        super.undoTask();
    }

    @Override
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

    /**
     * Extracts and formats the deadline details from the extended message.
     * Parses the date-time string and returns a readable summary.
     *
     * @return formatted deadline details string
     */
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
