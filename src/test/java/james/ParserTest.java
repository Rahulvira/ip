package james;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Parser} class.
 * Covers validation, command parsing, and execution logic.
 */
class ParserTest {

    private TaskList tasks;
    private StubUi ui;
    private StubDatabase db;

    /**
     * Simple stub implementation of Ui for testing.
     */
    static class StubUi extends Ui {
        private String lastMessage;

        @Override
        public void showBye() {
            lastMessage = "bye";
        }

        @Override
        public void showError(String message) {
            lastMessage = "error: " + message;
        }

        @Override
        public void displayList(TaskList tasks) {
            lastMessage = "list shown";
        }

        @Override
        public void displayFilteredList(TaskList tasks, ArrayList<Boolean> displayFlags) {
            lastMessage = "filtered list shown";
        }

        public String getLastMessage() {
            return lastMessage;
        }
    }

    /**
     * Simple stub implementation of Database for testing.
     */
    static class StubDatabase extends Database {
        boolean stored = false;

        /**
         * Constructs a Database object with the specified file path.
         *
         * @param p Path to the file where tasks will be stored and retrieved.
         */
        public StubDatabase(Path p) {
            super(p);
        }

        @Override
        public void store(TaskList tasks) throws IOException {
            stored = true;
        }
    }

    /**
     * Sets up a fresh TaskList and stubs before each test.
     */
    @BeforeEach
    void setUp() {
        tasks = new TaskList();
        ui = new StubUi();
        db = new StubDatabase(Paths.get("data/James.txt"));
    }

    /**
     * Tests that "todo" query is parsed and validated correctly.
     */
    @Test
    void testParseTodoValid() throws JamesException {
        assertEquals("todo", Parser.parse("todo finish homework", 0));
    }

    /**
     * Tests that missing description in "todo" throws exception.
     */
    @Test
    void testParseTodoInvalid() {
        assertThrows(JamesException.class, () -> Parser.parse("todo", 0));
    }

    /**
     * Tests that "event" requires both /from and /to.
     */
    @Test
    void testParseEventInvalid() {
        assertThrows(JamesException.class, () -> Parser.parse("event party /from 2025-09-20", 0));
    }

    /**
     * Tests that "deadline" requires /by section.
     */
    @Test
    void testParseDeadlineInvalid() {
        assertThrows(JamesException.class, () -> Parser.parse("deadline submit report", 0));
    }

    /**
     * Tests that "list" command works without arguments.
     */
    @Test
    void testParseListValid() throws JamesException {
        assertEquals("list", Parser.parse("list", 0));
    }

    /**
     * Tests that "list" with extra args throws exception.
     */
    @Test
    void testParseListInvalid() {
        assertThrows(JamesException.class, () -> Parser.parse("list extra", 0));
    }

    /**
     * Tests that "find" requires a keyword.
     */
    @Test
    void testParseFindInvalid() {
        assertThrows(JamesException.class, () -> Parser.parse("find", 0));
    }

    /**
     * Tests that "delete" requires valid indexes.
     */
    @Test
    void testParseDeleteInvalidIndex() {
        assertThrows(JamesException.class, () -> Parser.parse("delete 10", 0));
    }

    /**
     * Tests that invalid commands throw JamesException.
     */
    @Test
    void testParseInvalidCommand() {
        assertThrows(JamesException.class, () -> Parser.parse("nonsense something", 0));
    }

    /**
     * Tests execution of "todo" adds a task and formats response.
     */
    @Test
    void testExecuteTodo() {
        JamesResponse response = Parser.execute("todo", "todo buy milk", tasks, ui, db);
        assertTrue(response.getMessage().contains("Added:"));
        assertEquals(1, tasks.getSize());
    }

    /**
     * Tests execution of "event" adds a task.
     */
    @Test
    void testExecuteEvent() {
        JamesResponse response = Parser.execute("event", "event meeting /from 2025-09-20 10:00 /to 2025-09-20 12:00", tasks, ui, db);
        assertTrue(response.getMessage().contains("Added:"));
        assertEquals(1, tasks.getSize());
    }

    /**
     * Tests execution of "deadline" adds a task.
     */
    @Test
    void testExecuteDeadline() {
        JamesResponse response = Parser.execute("deadline", "deadline project /by 2025-09-21 18:00", tasks, ui, db);
        assertTrue(response.getMessage().contains("Added:"));
        assertEquals(1, tasks.getSize());
    }

    /**
     * Tests execution of "list" returns formatted tasks.
     */
    @Test
    void testExecuteList() {
        tasks.add(new Todo("todo buy milk"));
        JamesResponse response = Parser.execute("list", "list", tasks, ui, db);
        assertTrue(response.getMessage().contains("Here are your tasks:"));
    }

    /**
     * Tests execution of "find" filters tasks correctly.
     */
    @Test
    void testExecuteFind() {
        tasks.add(new Todo("todo buy milk"));
        JamesResponse response = Parser.execute("find", "find milk", tasks, ui, db);
        assertTrue(response.getMessage().contains("buy milk"));
    }

    /**
     * Tests execution of "mark" updates task status.
     */
    @Test
    void testExecuteMark() {
        tasks.add(new Todo("todo read book"));
        JamesResponse response = Parser.execute("mark", "mark 1", tasks, ui, db);
        assertTrue(response.getMessage().contains("marked:"));
        assertTrue(tasks.get(0).getStatus());
    }

    /**
     * Tests execution of "delete" removes task.
     */
    @Test
    void testExecuteDelete() {
        tasks.add(new Todo("todo clean room"));
        JamesResponse response = Parser.execute("delete", "delete 1", tasks, ui, db);
        assertTrue(response.getMessage().contains("deleted:"));
        assertEquals(0, tasks.getSize());
    }

    /**
     * Tests execution of "bye" sets exit flag and calls db.store.
     */
    @Test
    void testExecuteBye() {
        JamesResponse response = Parser.execute("bye", "bye", tasks, ui, db);
        assertEquals("bye", response.getMessage());
        assertTrue(Parser.isExit(), "Exit flag should be set to true");
        assertTrue(db.stored, "Database store() should be called");
        assertEquals("bye", ui.getLastMessage());
    }

    /**
     * Tests that unknown command type returns "invalid command".
     */
    @Test
    void testExecuteInvalidType() {
        JamesResponse response = Parser.execute("unknown", "blah", tasks, ui, db);
        assertEquals("invalid command", response.getMessage());
    }
}
