package james;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Used chatgpt to implement junit tests
 * to exhaustively test all possible aspects
 * and implement more tests for James
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

    @Test
    void testShowLine() {
        ui.showLine();
        assertTrue(getOutput().contains("--------------------------------------------------------------"));
    }

    @Test
    void testShowWelcome() {
        ui.showWelcome();
        String output = getOutput();
        assertTrue(output.contains("Hey There! James at your service."));
        assertTrue(output.contains("How can I help you today?"));
        assertTrue(output.contains("--------------------------------------------------------------"));
    }

    @Test
    void testShowBye() {
        ui.showBye();
        assertTrue(getOutput().contains("Bye, feel free to ask me anything anytime!"));
    }

    @Test
    void testDisplayList() {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("todo finish report"));
        ui.displayList(taskList);
        String output = getOutput();
        assertTrue(output.contains("finish report"));
    }

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

    @Test
    void testReadCommand() {
        String simulatedInput = "hello world\n";
        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(in);

        Ui uiWithInput = new Ui();
        String result = uiWithInput.readCommand();

        assertEquals("hello world", result);
    }

    @Test
    void testShowError() {
        ui.showError("Something went wrong");
        String output = getOutput();
        assertTrue(output.contains("JamesException: Something went wrong"));
    }
}
