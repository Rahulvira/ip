package james;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Used chatgpt to implement junit tests
 * to exhaustively test all possible aspects
 * and implement more tests for James
 */

class EventTest {

    private Event eventUnmarked;
    private Event eventMarked;


    @BeforeEach
    void setUp() {
        // Example format: "event project meeting /from 2025-09-20 14:00 /to 2025-09-20 16:00"
        eventUnmarked = new Event("event project meeting /from 2025-09-20 14:00 /to 2025-09-20 16:00");
        eventMarked = new Event("event hackathon /from 2025-10-05 09:00 /to 2025-10-05 18:00", true);
    }

    @Test
    void testGetExtendedQuery() {
        assertEquals("event project meeting /from 2025-09-20 14:00 /to 2025-09-20 16:00",
                eventUnmarked.getExtendedQuery());
        assertEquals("event hackathon /from 2025-10-05 09:00 /to 2025-10-05 18:00",
                eventMarked.getExtendedQuery());
    }

    @Test
    void testGetType() {
        assertEquals(TaskType.EVENT, eventUnmarked.getType());
        assertEquals(TaskType.EVENT, eventMarked.getType());
    }

    @Test
    void testGetTaskDescription() {
        assertEquals("project meeting", eventUnmarked.getTask());
        assertEquals("hackathon", eventMarked.getTask());
    }

    @Test
    void testGetStatusInitially() {
        assertFalse(eventUnmarked.getStatus(), "Unmarked event should start with false status");
        assertTrue(eventMarked.getStatus(), "Marked event should start with true status");
    }

    @Test
    void testFinishAndUndoTask() {
        eventUnmarked.finishTask();
        assertTrue(eventUnmarked.getStatus(), "Event should be marked as done after finishTask()");

        eventUnmarked.undoTask();
        assertFalse(eventUnmarked.getStatus(), "Event should revert to undone after undoTask()");
    }

    @Test
    void testGetEventDetails() {
        String details = eventUnmarked.getEventDetails();
        assertTrue(details.contains("from:"), "Details should contain 'from:' label");
        assertTrue(details.contains("to:"), "Details should contain 'to:' label");
        assertTrue(details.contains("2025-09-20"), "Details should contain the event date");
    }

    @Test
    void testToStringFormat() {
        String str = eventUnmarked.toString();
        assertTrue(str.startsWith("[E]"), "String should start with [E]");
        assertTrue(str.contains(eventUnmarked.getTask()), "String should contain task description");
        assertTrue(str.contains(eventUnmarked.getEventDetails()), "String should contain event details");
    }
}
