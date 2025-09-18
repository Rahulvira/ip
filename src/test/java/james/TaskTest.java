package james;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Task} class and its utility methods.
 */
class TaskTest {

    /**
     * Tests constructor initializes task description and default status correctly.
     */
    @Test
    void testConstructorDefaultStatus() {
        Task task = new Task("read book");
        assertEquals("read book", task.getTask());
        assertFalse(task.getStatus(), "Default status should be false (not done)");
    }

    /**
     * Tests constructor with explicit completion status.
     */
    @Test
    void testConstructorWithStatus() {
        Task task = new Task("write essay", true);
        assertTrue(task.getStatus());
        assertEquals("write essay", task.getTask());
    }

    /**
     * Tests finishTask marks a task as complete.
     */
    @Test
    void testFinishTask() {
        Task task = new Task("do homework");
        task.finishTask();
        assertTrue(task.getStatus());
    }

    /**
     * Tests undoTask marks a task as incomplete.
     */
    @Test
    void testUndoTask() {
        Task task = new Task("clean room", true);
        task.undoTask();
        assertFalse(task.getStatus());
    }

    /**
     * Tests toString outputs correct format depending on status.
     */
    @Test
    void testToString() {
        Task undone = new Task("buy milk");
        assertEquals("[ ] buy milk", undone.toString());

        Task done = new Task("buy milk", true);
        assertEquals("[X] buy milk", done.toString());
    }

    /**
     * Tests stringToTask creates correct Todo object.
     */
    @Test
    void testStringToTaskTodo() {
        Task task = Task.stringToTask("T|1|todo finish report");
        assertTrue(task instanceof Todo);
        assertTrue(task.getStatus());
        assertTrue(task.getTask().contains("finish report"));
    }

    /**
     * Tests stringToTask creates correct Deadline object.
     */
    @Test
    void testStringToTaskDeadline() {
        Task task = Task.stringToTask("D|0|deadline submit report /by 12/9/2025 1800");
        assertTrue(task instanceof Deadline);
        assertFalse(task.getStatus());
    }

    /**
     * Tests stringToTask creates correct Event object.
     */
    @Test
    void testStringToTaskEvent() {
        Task task = Task.stringToTask("E|1|event meeting /from 10/9/2025 1000 /to 10/9/2025 1200");
        assertTrue(task instanceof Event);
        assertTrue(task.getStatus());
    }

    /**
     * Tests stringToTask throws exception on malformed input.
     */
    @Test
    void testStringToTaskInvalid() {
        assertThrows(IllegalArgumentException.class, () -> Task.stringToTask("invalid input"));
        assertThrows(IllegalArgumentException.class, () -> Task.stringToTask("X|1|something"));
    }

    /**
     * Tests taskToString converts Todo correctly.
     */
    @Test
    void testTaskToStringTodo() {
        Todo todo = new Todo("todo finish report", true);
        String result = Task.taskToString(todo);
        assertEquals("T|1|todo finish report", result);
    }

    /**
     * Tests taskToString converts Deadline correctly.
     */
    @Test
    void testTaskToStringDeadline() {
        Deadline deadline = new Deadline("deadline project /by 12/9/2025 1800", false);
        String result = Task.taskToString(deadline);
        assertEquals("D|0|deadline project /by 12/9/2025 1800", result);
    }

    /**
     * Tests taskToString converts Event correctly.
     */
    @Test
    void testTaskToStringEvent() {
        Event event = new Event("event workshop /from 12/9/2025 1000 /to 12/9/2025 1200", true);
        String result = Task.taskToString(event);
        assertEquals("E|1|event workshop /from 12/9/2025 1000 /to 12/9/2025 1200", result);
    }

    /**
     * Tests parseDateTime with valid date only input.
     */
    @Test
    void testParseDateOnly() {
        Task task = new Task("dummy");
        String result = task.parseDateTime("12/9/2025");
        assertTrue(result.contains("Day 12"));
        assertTrue(result.contains("SEPTEMBER"));
    }

    /**
     * Tests parseDateTime with valid date and time input.
     */
    @Test
    void testParseDateTimeValid() {
        Task task = new Task("dummy");
        String result = task.parseDateTime("12/9/2025 1800");
        assertTrue(result.contains("Day 12"));
        assertTrue(result.contains("SEPTEMBER"));
        assertTrue(result.contains("6 00 pm"));
    }

    /**
     * Tests parseDateTime returns input if format invalid.
     */
    @Test
    void testParseDateTimeInvalid() {
        Task task = new Task("dummy");
        String result = task.parseDateTime("not-a-date");
        assertEquals("not-a-date", result);
    }
}
