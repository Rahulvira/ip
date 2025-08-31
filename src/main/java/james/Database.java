package james;

import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.util.*;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Database {
    public final Path file;
    public Database(Path p) {
        this.file = p;
    };

    private void handleDirectory() throws IOException{
        Path parent = file.getParent();
        // Check if we store database under a different directory
        if (parent != null) {
            // If folder does not exist it is created, if exists nothing happens
            Files.createDirectories(parent);
        }
    }

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
