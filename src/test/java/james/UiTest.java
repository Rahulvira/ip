package james;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Ui} class.
 * Captures console output to verify printed messages.
 */
class UiTest {

    private Ui ui;
    private ByteArrayOutputStream outputStream;

    /**
     * Redirects System.out to a ByteArrayOutputStream before each test.
     */
    @BeforeEach
    void setUp() {
        ui = new Ui();
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    /**
     * Helper method to get printed console output as a String.
     *
     * @return captured console output
     */
    private String getOutput() {
        return outputStream.toString().trim();
    }

    /**
     * Tests that showLine prints a divider line.
     */
    @Test
    void testShowLine() {
        ui.showLine();
        assertTrue(getOutput().contains("--------------------------------------------------------------"));
    }

    /**
     * Tests that showWelcome prints the welcome message followed by a line.
     */
    @Test
    void testShowWelcome() {
        ui.showWelcome();
        String output = getOutput();
        assertTrue(output.contains("Hey There! James at your service."));
        assertTrue(output.contains("How can I help you today?"));
        assertTrue(output.contains("--------------------------------------------------------------"));
    }

    /**
     * Tests that showBye prints the farewell message.
     */
    @Test
    void testShowBye() {
        ui.showBye();
        assertTrue(getOutput().contains("Bye, feel free to ask me anything anytime!"));
    }

    /**
     * Tests that displayList delegates printing to TaskList.displayTasks().
     */
    @Test
    void testDisplayList() {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("todo finish report"));
        ui.displayList(taskList);
        String output = getOutput();
        assertTrue(output.contains("finish report"));
    }

    /**
     * Tests that displayFilteredList prints only flagged tasks.
     */
    @Test
    void testDisplayFilteredList() {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("todo buy milk"));
        taskList.add(new Todo("todo read book"));
        ArrayList<Boolean> flags = new ArrayList<>();
        flags.add(true);
        flags.add(false);

        ui.displayFilteredList(taskList, flags);
        String output = getOutput();

        assertTrue(output.contains("buy milk"));
        assertFalse(output.contains("read book"));
    }

    /**
     * Tests that readCommand reads a line from simulated user input.
     */
    @Test
    void testReadCommand() {
        String simulatedInput = "hello world\n";
        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(in);

        Ui uiWithInput = new Ui();
        String result = uiWithInput.readCommand();

        assertEquals("hello world", result);
    }

    /**
     * Tests that showError prints formatted error messages.
     */
    @Test
    void testShowError() {
        ui.showError("Something went wrong");
        String output = getOutput();
        assertTrue(output.contains("DukeException: Something went wrong"));
    }
}
