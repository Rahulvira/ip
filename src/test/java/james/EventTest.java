package james;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Event} class.
 * Verifies parsing, status changes, metadata handling, and formatting.
 */
class EventTest {

    private Event eventUnmarked;
    private Event eventMarked;

    /**
     * Sets up sample Event objects for testing before each test case.
     */
    @BeforeEach
    void setUp() {
        // Example format: "event project meeting /from 2025-09-20 14:00 /to 2025-09-20 16:00"
        eventUnmarked = new Event("event project meeting /from 2025-09-20 14:00 /to 2025-09-20 16:00");
        eventMarked = new Event("event hackathon /from 2025-10-05 09:00 /to 2025-10-05 18:00", true);
    }

    /**
     * Tests that the extended query string is stored correctly.
     */
    @Test
    void testGetExtendedQuery() {
        assertEquals("event project meeting /from 2025-09-20 14:00 /to 2025-09-20 16:00",
                eventUnmarked.getExtendedQuery());
        assertEquals("event hackathon /from 2025-10-05 09:00 /to 2025-10-05 18:00",
                eventMarked.getExtendedQuery());
    }

    /**
     * Tests that getType returns TaskType.EVENT.
     */
    @Test
    void testGetType() {
        assertEquals(TaskType.EVENT, eventUnmarked.getType());
        assertEquals(TaskType.EVENT, eventMarked.getType());
    }

    /**
     * Tests that the task description is parsed correctly (removes the leading command word).
     */
    @Test
    void testGetTaskDescription() {
        assertEquals("project meeting", eventUnmarked.getTask());
        assertEquals("hackathon", eventMarked.getTask());
    }

    /**
     * Tests that the initial status of events matches constructor input.
     */
    @Test
    void testGetStatusInitially() {
        assertFalse(eventUnmarked.getStatus(), "Unmarked event should start with false status");
        assertTrue(eventMarked.getStatus(), "Marked event should start with true status");
    }

    /**
     * Tests that finishTask sets the status to true and undoTask resets it to false.
     */
    @Test
    void testFinishAndUndoTask() {
        eventUnmarked.finishTask();
        assertTrue(eventUnmarked.getStatus(), "Event should be marked as done after finishTask()");

        eventUnmarked.undoTask();
        assertFalse(eventUnmarked.getStatus(), "Event should revert to undone after undoTask()");
    }

    /**
     * Tests that getEventDetails extracts and formats start and end times properly.
     */
    @Test
    void testGetEventDetails() {
        String details = eventUnmarked.getEventDetails();
        assertTrue(details.contains("from:"), "Details should contain 'from:' label");
        assertTrue(details.contains("to:"), "Details should contain 'to:' label");
        assertTrue(details.contains("2025-09-20"), "Details should contain the event date");
    }

    /**
     * Tests that toString produces the correct formatted string.
     */
    @Test
    void testToStringFormat() {
        String str = eventUnmarked.toString();
        assertTrue(str.startsWith("[E]"), "String should start with [E]");
        assertTrue(str.contains(eventUnmarked.getTask()), "String should contain task description");
        assertTrue(str.contains(eventUnmarked.getEventDetails()), "String should contain event details");
    }
}
