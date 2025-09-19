package james;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Used chatgpt to implement junit tests
 * to exhaustively test all possible aspects
 * and implement more tests for James
 */

class TaskTest {

    @Test
    void testConstructorDefaultStatus() {
        Task task = new Task("read book");
        assertEquals("read book", task.getTask());
        assertFalse(task.getStatus(), "Default status should be false (not done)");
    }

    @Test
    void testConstructorWithStatus() {
        Task task = new Task("write essay", true);
        assertTrue(task.getStatus());
        assertEquals("write essay", task.getTask());
    }

    @Test
    void testFinishTask() {
        Task task = new Task("do homework");
        task.finishTask();
        assertTrue(task.getStatus());
    }

    @Test
    void testUndoTask() {
        Task task = new Task("clean room", true);
        task.undoTask();
        assertFalse(task.getStatus());
    }

    @Test
    void testToString() {
        Task undone = new Task("buy milk");
        assertEquals("[ ] buy milk", undone.toString());

        Task done = new Task("buy milk", true);
        assertEquals("[X] buy milk", done.toString());
    }

    @Test
    void testStringToTaskTodo() {
        Task task = Task.stringToTask("T|1|todo finish report");
        assertTrue(task instanceof Todo);
        assertTrue(task.getStatus());
        assertTrue(task.getTask().contains("finish report"));
    }

    @Test
    void testStringToTaskDeadline() {
        Task task = Task.stringToTask("D|0|deadline submit report /by 12/9/2025 1800");
        assertTrue(task instanceof Deadline);
        assertFalse(task.getStatus());
    }

    @Test
    void testStringToTaskEvent() {
        Task task = Task.stringToTask("E|1|event meeting /from 10/9/2025 1000 /to 10/9/2025 1200");
        assertTrue(task instanceof Event);
        assertTrue(task.getStatus());
    }

    @Test
    void testStringToTaskInvalid() {
        assertThrows(IllegalArgumentException.class, () -> Task.stringToTask("invalid input"));
        assertThrows(IllegalArgumentException.class, () -> Task.stringToTask("X|1|something"));
    }

    @Test
    void testTaskToStringTodo() {
        Todo todo = new Todo("todo finish report", true);
        String result = Task.taskToString(todo);
        assertEquals("T|1|todo finish report", result);
    }

    @Test
    void testTaskToStringDeadline() {
        Deadline deadline = new Deadline("deadline project /by 12/9/2025 1800", false);
        String result = Task.taskToString(deadline);
        assertEquals("D|0|deadline project /by 12/9/2025 1800", result);
    }

    @Test
    void testTaskToStringEvent() {
        Event event = new Event("event workshop /from 12/9/2025 1000 /to 12/9/2025 1200", true);
        String result = Task.taskToString(event);
        assertEquals("E|1|event workshop /from 12/9/2025 1000 /to 12/9/2025 1200", result);
    }

    @Test
    void testParseDateOnly() {
        Task task = new Task("dummy");
        String result = task.parseDateTime("12/9/2025");
        assertTrue(result.contains("Day 12"));
        assertTrue(result.contains("SEPTEMBER"));
    }

    @Test
    void testParseDateTimeValid() {
        Task task = new Task("dummy");
        String result = task.parseDateTime("12/9/2025 1800");
        assertTrue(result.contains("Day 12"));
        assertTrue(result.contains("SEPTEMBER"));
        assertTrue(result.contains("6 00 pm"));
    }

    @Test
    void testParseDateTimeInvalid() {
        Task task = new Task("dummy");
        String result = task.parseDateTime("not-a-date");
        assertEquals("not-a-date", result);
    }
}
