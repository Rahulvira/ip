package james;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DeadlineTest {

    private Deadline deadlineUnmarked;
    private Deadline deadlineMarked;

    @BeforeEach
    void setUp() {
        // Example query format: "deadline submit report /by 2025-09-20 18:00"
        deadlineUnmarked = new Deadline("deadline submit report /by 2025-09-20 18:00");
        deadlineMarked = new Deadline("deadline finish homework /by 2025-10-01 09:00", true);
    }

    @Test
    void testGetExtendedQuery() {
        assertEquals("deadline submit report /by 2025-09-20 18:00", deadlineUnmarked.getExtendedQuery());
        assertEquals("deadline finish homework /by 2025-10-01 09:00", deadlineMarked.getExtendedQuery());
    }

    @Test
    void testGetType() {
        assertEquals(TaskType.DEADLINE, deadlineUnmarked.getType());
        assertEquals(TaskType.DEADLINE, deadlineMarked.getType());
    }

    @Test
    void testGetTaskDescription() {
        // It should strip the command word ("deadline") and return only description
        assertEquals("submit report", deadlineUnmarked.getTask());
        assertEquals("finish homework", deadlineMarked.getTask());
    }

    @Test
    void testGetStatusInitially() {
        assertFalse(deadlineUnmarked.getStatus(), "Unmarked deadline should start as false");
        assertTrue(deadlineMarked.getStatus(), "Marked deadline should start as true");
    }

    @Test
    void testFinishAndUndoTask() {
        deadlineUnmarked.finishTask();
        assertTrue(deadlineUnmarked.getStatus());

        deadlineUnmarked.undoTask();
        assertFalse(deadlineUnmarked.getStatus());
    }

    @Test
    void testGetDeadlineDetails() {
        // Format: (by: parsedDateTime)
        String details = deadlineUnmarked.getDeadlineDetails();
        assertTrue(details.contains("by:"), "Deadline details should contain 'by:'");
        assertTrue(details.contains("2025"), "Deadline details should contain the year part");
    }

    @Test
    void testToStringFormat() {
        String str = deadlineUnmarked.toString();
        assertTrue(str.startsWith("[D]"), "String should start with [D]");
        assertTrue(str.contains(deadlineUnmarked.getTask()), "Should contain task description");
        assertTrue(str.contains(deadlineUnmarked.getDeadlineDetails()), "Should contain formatted deadline details");
    }
}
