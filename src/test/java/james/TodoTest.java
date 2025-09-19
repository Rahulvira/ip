package james;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Used chatgpt to implement junit tests
 * to exhaustively test all possible aspects
 * and implement more tests for James
 */

public class TodoTest {
    @Test
    void constructor_setsDescriptionCorrectly() {
        Todo todo = new Todo("todo finish homework");
        assertEquals("finish homework", todo.getTask());
        assertEquals("todo finish homework", todo.getExtendedQuery());
        assertEquals(TaskType.TODO, todo.getType());
    }

    @Test
    void constructor_withStatus_setsStatusCorrectly() {
        Todo todo = new Todo("todo finish homework", false);
        assertFalse(todo.getStatus());
    }

    @Test
    void finishTask_setsStatusTrue() {
        Todo todo = new Todo("todo finish homework", false);
        todo.finishTask();
        assertTrue(todo.getStatus());
    }

    @Test
    void undoTask_setsStatusFalse() {
        Todo todo = new Todo("todo finish homework", true);
        todo.undoTask();
        assertFalse(todo.getStatus());
    }

    @Test
    void toString_returnsFormattedString() {
        Todo todo = new Todo("todo finish homework");
        String expected = "[T] [ ] finish homework";
        assertEquals(expected, todo.toString());
    }

    @Test
    void getType_returnsTodoType() {
        Todo todo = new Todo("todo finish homework");
        assertEquals(TaskType.TODO, todo.getType());
    }


}
