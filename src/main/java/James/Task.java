package James;

import java.util.Arrays;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalTime;

enum TaskType {TASK, TODO, DEADLINE, EVENT}

public class Task {
    /** Status of completion of task */
    private boolean status;
    /** Message of task */
    private String task;

    public Task (String t) {
        this.task = t;
    }

    public Task(String t, boolean s) {
        this.task = t;
        this.status =s;
    }

    public String getExtendedMessage() {
        return "";
    }

    public TaskType getType() {
        return TaskType.TASK;
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

    public static Task stringToTask (String s) throws IllegalArgumentException{
        String[] splitString = s.split("\\|",3);
        //System.out.println(Arrays.toString(splitString));
        if (splitString.length != 3) {
            throw new IllegalArgumentException("Invalid Line!");
        }
        boolean done = splitString[1].equals("1");
        if (splitString[0].startsWith("T")) {
            return new Todo(splitString[2], done);
        } else if (splitString[0].startsWith("D")) {
            return new Deadline(splitString[2], done);
        } else if (splitString[0].startsWith("E")) {
            return new Event(splitString[2], done);
        } else {
            throw new IllegalArgumentException("invalid line!");
        }
    }

    public static String TaskToString(Task t) {
        int done = 0;
        if (t.status) {
             done = 1;
        }
        if (t.getType() == TaskType.TODO) {
            return "T|" + done + "|" + t.getExtendedMessage();
        } else if (t.getType() == TaskType.DEADLINE) {
            return "D|" + done + "|" + t.getExtendedMessage();
        } else if (t.getType() == TaskType.EVENT) {
            return "E|" + done + "|" + t.getExtendedMessage();
        } else {
            return "";
        }
    }

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
        if (status) {
            return "[X] " + task;
        } else {
            return "[ ] " + task;
        }
    }
}
