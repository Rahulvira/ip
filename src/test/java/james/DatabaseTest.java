package james;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DatabaseTest {
    private Path tempDir;
    private Path tempFile;
    private Database db;

    @BeforeEach
    void setup() throws IOException {
        tempDir = Files.createTempDirectory("testDir");
        tempFile = tempDir.resolve("testDb.txt");
        db = new Database(tempFile);
    }

    @AfterEach
    void cleanup() throws IOException {
        deleteRecursively(tempDir);
    }

    private void deleteRecursively(Path path) throws IOException {
        if (Files.exists(path)) {
            if (Files.isDirectory(path)) {
                try (DirectoryStream<Path> entries = Files.newDirectoryStream(path)) {
                    for (Path entry : entries) {
                        deleteRecursively(entry);
                    }
                }
            }
            Files.delete(path);
        }
    }

    @Test
    void store_createsFileAndWritesTasks() throws IOException {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("todo eat food"));
        taskList.add(new Todo("todo water plants", true));

        db.store(taskList);

        assertTrue(Files.exists(tempFile));
        List<String> lines = Files.readAllLines(tempFile);
        assertEquals(2, lines.size());
        assertTrue(lines.get(0).contains("eat food"));
        assertTrue(lines.get(1).contains("water plants"));
    }

    @Test
    void load_readsTasksCorrectly() throws IOException {
        List<String> lines = List.of(
                Task.TaskToString(new Todo("todo eat food")),
                Task.TaskToString(new Todo("todo water plants", true))
        );
        Files.write(tempFile, lines);

        ArrayList<Task> tasks = db.load();

        assertEquals(2, tasks.size());
        assertEquals("eat food", tasks.get(0).getTask());
        assertEquals("water plants", tasks.get(1).getTask());
        assertTrue(tasks.get(1).getStatus());
    }


    @Test
    void handleDirectory_createsParentDirectory() throws IOException {
        Path innerFile = tempDir.resolve("inner/dir/test.txt");
        Database nestedDb = new Database(innerFile);

        // Should not throw
        nestedDb.store(new TaskList());

        assertTrue(Files.exists(innerFile));
    }

}
