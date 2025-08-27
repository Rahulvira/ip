import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.util.*;

public class Database {
    public final Path file;
    public Database(Path p) {
        this.file = p;
    };

    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> tasks = new ArrayList<Task>();
        try (BufferedReader r = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String task;
            while ((task = r.readLine()) != null) {
                if (task.isEmpty()) continue;

                try {
                    // convert from string to task
                    // add task to tasks
                } catch (Exception e) {
                    // print error with task
                }
            }
        }
        return tasks;
    }

    public void store(ArrayList<Task> tasks) {
        // loop through tasks, convert tasks to its string representation
        // to store them in ./data/James.txt
        // use Buffered Writer
        // check if directory and file exists. If not, create it
    }
}
