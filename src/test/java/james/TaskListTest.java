package james;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Used chatgpt to implement junit tests
 * to exhaustively test all possible aspects
 * and implement more tests for James
 */

class TaskListTest {

    private TaskList taskList;
    private Deadline task1;
    private Deadline task2;
    private Deadline task3;

    @BeforeEach
    void setUp() {
        taskList = new TaskList();
        task1 = new Deadline("deadline submit report /by 2025-09-20 18:00");
        task2 = new Deadline("deadline finish homework /by 2025-10-01 09:00");
        task3 = new Deadline("deadline prepare slides /by 2025-11-15 14:00");

        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);
    }

    @Test
    void testAddAndGetSize() {
        assertEquals(3, taskList.getSize(), "Size should be 3 after adding 3 tasks");
    }

    @Test
    void testGetTaskByIndex() {
        assertEquals(task1, taskList.get(0));
        assertEquals(task2, taskList.get(1));
        assertEquals(task3, taskList.get(2));
    }

    @Test
    void testGetTasksReturnsList() {
        ArrayList<Task> tasks = taskList.getTasks();
        assertEquals(3, tasks.size());
        assertTrue(tasks.contains(task1));
        assertTrue(tasks.contains(task2));
        assertTrue(tasks.contains(task3));
    }

    @Test
    void testMarkTasks() {
        ArrayList<Task> modified = taskList.markTasks("mark 1 3");
        assertTrue(task1.getStatus());
        assertTrue(task3.getStatus());
        assertFalse(task2.getStatus());
        assertEquals(2, modified.size());
    }

    @Test
    void testUnmarkTasks() {
        // First mark then unmark
        taskList.markTasks("mark 2");
        assertTrue(task2.getStatus());

        ArrayList<Task> modified = taskList.markTasks("unmark 2");
        assertFalse(task2.getStatus());
        assertEquals(1, modified.size());
    }

    @Test
    void testDeleteTasks() {
        ArrayList<Task> deleted = taskList.deleteTasks("delete 2");
        assertEquals(1, deleted.size());
        assertEquals(task2, deleted.get(0));
        assertEquals(2, taskList.getSize());
        assertEquals(task3, taskList.get(1));
    }

    @Test
    void testGetDisplayFlags() {
        ArrayList<Boolean> flags = taskList.getDisplayFlags("find report");
        assertEquals(3, flags.size());
        assertTrue(flags.get(0));   // task1 contains "report"
        assertFalse(flags.get(1));
        assertFalse(flags.get(2));
    }

    @Test
    void testFormatAsStringResponse() {
        ArrayList<Boolean> flags = taskList.getDisplayFlags("find homework");
        String response = taskList.formatAsStringResponse(flags);
        assertTrue(response.contains("finish homework"));
        assertTrue(response.startsWith("Here are your tasks:"));
    }

    @Test
    void testDisplayTasksWithStringDoesNotThrow() {
        ArrayList<Boolean> flags = taskList.getDisplayFlags("find slides");
        assertDoesNotThrow(() -> taskList.displayTasksWithString(flags));
    }

    @Test
    void testDisplayTasksDoesNotThrow() {
        assertDoesNotThrow(() -> taskList.displayTasks());
    }
}
