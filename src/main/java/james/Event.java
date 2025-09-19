package james;

import java.util.Arrays;

public class Event extends Task{
    private String extendedQuery;
    /**
     * Constructs an Event class from an input string.
     * Extracts the task description and stores the full message for processing.
     *
     * @param s the full event string query
     */
    public Event(String s) {
        // Pass only the message to super class, remove event details
        super(s.split(" /")[0].replaceFirst("^\\s*\\S+\\s*", ""));
        this.extendedQuery = s;
    }
    /**
     * Constructs an Event class from a raw input string.
     * Extracts the task description and stores the full message for metadata parsing.
     *
     * @param s the full event string query
     */
    public Event(String s, boolean isMarked) {
        // Pass only the message to super class, remove event details
        super(s.split(" /")[0].replaceFirst("^\\s*\\S+\\s*", ""), isMarked);
        this.extendedQuery = s;
    }

    @Override
    public String getExtendedQuery() {
        return this.extendedQuery;
    }

    @Override
    public TaskType getType() {
        return TaskType.EVENT;
    }

    /**
     * Sets the task status to false.
     * */
    public void undoTask() {
        super.undoTask();
    }

    /**
     * Sets the task status to true.
     * */
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
     * Extracts and formats the start and end details of the event.
     * Parses date-time strings and returns a readable summary.
     *
     * @return formatted event details string
     */
    public String getEventDetails() {
        String[] untrimmedQueryWords = this.extendedQuery.split(" /");
        String[] trimmedQueryWords = Arrays.stream(untrimmedQueryWords)
                .map(s -> s == null ? null : s.trim())
                .toArray(String[]::new);
        String[] eventStartArray = trimmedQueryWords[1].split(" ", 2);
        String dateStart = parseDateTime(eventStartArray[1]);
        String eventStart = eventStartArray[0]+ ": " + dateStart;
        String[] eventEndArray = trimmedQueryWords[2].split(" ", 2);
        String dateEnd = parseDateTime(eventStartArray[1]);
        String eventEnd = (eventEndArray[0] + ": " + dateEnd);
        return "(" + eventStart + " " + eventEnd + ")";
    }
    @Override
    public String toString() {
        return "[E] " + super.toString() + " " + this.getEventDetails();
    }
}
