package James;

import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.util.*;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Database {
    /** Path to the database file */
    public final Path file;

    /**
     * Constructs a Database object with the specified file path.
     *
     * @param p Path to the file where tasks will be stored and retrieved.
     */
    public Database(Path p) {
        this.file = p;
    };

    /**
     * Ensures that the directory structure for the database file exists.
     * Creates parent directories if they are missing.
     *
     * @throws IOException if directory creation fails.
     */
    private void handleDirectory() throws IOException{
        Path parent = file.getParent();
        // Check if we store database under a different directory
        if (parent != null) {
            // If folder does not exist it is created, if exists nothing happens
            Files.createDirectories(parent);
        }
    }

    /**
     * Loads tasks from the database file.
     * Each line in the file is parsed into a Task object.
     * Invalid lines are skipped with error messages.
     *
     * @return ArrayList of Task objects loaded from the file.
     * @throws IOException if reading from the file fails.
     */
    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> tasks = new ArrayList<Task>();
        try (BufferedReader r = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String task;
            while ((task = r.readLine()) != null) {
                if (task.isEmpty()) continue;
                try {
                    // convert from string to task
                    // add task to tasks
                    tasks.add(Task.stringToTask(task));
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return tasks;
    }

    /**
     * Stores the current list of tasks into the database file.
     * Each task is converted to its string representation and written line by line.
     * Overwrites existing content in the file.
     *
     * @param tasks TaskList containing all tasks to be stored.
     * @throws IOException if writing to the file fails.
     */
    public void store(TaskList tasks) throws IOException{
        // loop through tasks, convert tasks to its string representation
        // to store them in ./data/James.James.txt
        // use Buffered Writer
        // check if directory and file exists. If not, create it
        handleDirectory();
        try (BufferedWriter w = Files.newBufferedWriter(
                file, UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (Task t : tasks.getTasks()) {
                w.write(Task.TaskToString(t)); w.newLine();
            }
        }

    }
}
