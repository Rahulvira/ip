package james;

import java.util.Scanner;

public class Ui {
    private Scanner input;

    /**
     * Constructs a UI object and initializes the input scanner.
     */
    public Ui() {
        input = new Scanner(System.in);
    }

    /**
     * Prints a horizontal divider line for visual separation in the console.
     */
    public void showLine() {
        System.out.println("--------------------------------------------------------------");
    }

    /**
     * Displays the welcome message when the application starts.
     * Includes a divider line for clarity.
     */
    public void showWelcome() {
        System.out.println("Hey There! James.James at your service. \n" +
                "How can I help you today?");
        showLine();
    }

    /**
     * Displays the farewell message when the user exits the application.
     */
    public void showBye() {
        System.out.println("Bye, feel free to ask me anything anytime!");
    }

    /**
     * Displays the task list in a numbered format line after line.
     *
     * @param tasks Array containing tasks.
     */
    public void displayList(TaskList tasks) {
        //System.out.println(arr);
        tasks.displayTask();
    }

    public void displayFilteredList(TaskList tasks, String query) {
        tasks.displayTasksWithString(query);
    }

    /**
     * Reads a command from the user input and trims leading/trailing whitespace.
     *
     * @return The trimmed input string entered by the user.
     */
    public String readCommand() {
        String query = input.nextLine().trim();
        return query;
    }

    /**
     * Displays an error message to the user.
     *
     * @param err The error message to be shown.
     */
    public void showError(String err) {
        System.out.println("DukeException: " +  err);
    }
}
