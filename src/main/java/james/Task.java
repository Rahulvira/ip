package james;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalTime;

enum TaskType {TASK, TODO, DEADLINE, EVENT}

public class Task {
    /** Status of completion of task */
    private boolean isMarked;
    /** Message of task */
    private String task;

    public Task (String t) {
        this.task = t;
    }

    /**
     * Constructs a Task with the given message and specified completion status.
     *
     * @param t the task description
     * @param s the completion status (true if done, false otherwise)
     */
    public Task(String t, boolean s) {
        this.task = t;
        this.isMarked =s;
    }

    /**
     * Returns an extended message for the task.
     * Subclasses may override this to include additional metadata.
     *
     * @return an extended message string
     */
    public String getExtendedQuery() {
        return "";
    }

    /**
     * Returns the type of the task.
     * Default is TaskType.TASK subclasses override this.
     *
     * @return the task type
     */
    public TaskType getType() {
        return TaskType.TASK;
    }

    /**
     * Returns the completion status of the task.
     *
     * @return true if the task is completed, false otherwise
     */
    public boolean getStatus() {
        return this.isMarked;
    }

    /**
     * Returns the textual description of the task.
     *
     * @return the task message
     */
    public String getTask() {
        return this.task;
    }

    /**
     * Marks the task as not completed.
     */
    public void undoTask() {
        this.isMarked = false;
    }

    /**
     * Marks the task as completed.
     */
    public void finishTask() {
        this.isMarked = true;
    }

    /**
     * Converts a string representation of a task into a Task object.
     *
     * @param s the string to parse
     * @return the corresponding Task object
     * @throws IllegalArgumentException if the string format is invalid
     */
    public static Task stringToTask (String s) throws IllegalArgumentException{
        String[] splitString = s.split("\\|",3);
        //System.out.println(Arrays.toString(splitString));
        if (splitString.length != 3) {
            throw new IllegalArgumentException("Invalid Line!");
        }
        boolean isDone = splitString[1].equals("1");
        if (splitString[0].startsWith("T")) {
            return new Todo(splitString[2], isDone);
        } else if (splitString[0].startsWith("D")) {
            return new Deadline(splitString[2], isDone);
        } else if (splitString[0].startsWith("E")) {
            return new Event(splitString[2], isDone);
        } else {
            throw new IllegalArgumentException("invalid line!");
        }
    }

    /**
     * Converts a Task object into its string representation for storage.
     *
     * @param t the task to convert
     * @return the string representation of the task
     */
    public static String taskToString(Task t) {
        int done = 0;
        if (t.isMarked) {
             done = 1;
        }
        if (t.getType() == TaskType.TODO) {
            return "T|" + done + "|" + t.getExtendedQuery();
        } else if (t.getType() == TaskType.DEADLINE) {
            return "D|" + done + "|" + t.getExtendedQuery();
        } else if (t.getType() == TaskType.EVENT) {
            return "E|" + done + "|" + t.getExtendedQuery();
        } else {
            return "";
        }
    }

    /**
     * Parses a date-time string and returns a formatted human-readable version.
     * Expected input format: {@code "d/M/yyyy HHmm"} or {@code "d/M/yyyy"}.
     *
     * @param input the date-time string to parse
     * @return formatted string or original input if parsing fails
     */
    public String parseDateTime(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("HHmm");
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("h mm a");
        try {
            String[] splitInput = input.split(" ");
            String formattedTime = "";
            // if time is mentioned parse time
            if (splitInput.length == 2) {
                LocalTime time = LocalTime.parse(splitInput[1], inputFormat);
                formattedTime = time.format(outputFormat);
            }
            LocalDate date = LocalDate.parse(splitInput[0], formatter);
            // will throw exception if no time is specified, so will return original input
            int day = date.getDayOfMonth();
            String month = date.getMonth().toString();
            int year = date.getYear();
            return "Day " + day + " of " + month + " " + year + " " + formattedTime;
        } catch (DateTimeParseException e) {
            return input;
        }
    }


    @Override
    public String toString() {
        if (isMarked) {
            return "[X] " + task;
        } else {
            return "[ ] " + task;
        }
    }
}
