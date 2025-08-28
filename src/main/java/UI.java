import java.util.ArrayList;
import java.util.Scanner;

public class UI {
    private Scanner input;
    public UI() {
        input = new Scanner(System.in);
    }

    public void showLine() {
        System.out.println("--------------------------------------------------------------");
    }

    public void showWelcome() {
        System.out.println("Hey There! James at your service. \n" +
                "How can I help you today?");
        showLine();
    }

    public void showBye() {
        System.out.println("Bye, feel free to ask me anything anytime!");
    }

    /**
     * Displays the task list in a numbered format line after line.
     *
     * @param arr Array containing tasks.
     */
    public void displayList(TaskList arr) {
        System.out.println(arr);
        arr.displayTask();
    }

    public String readCommand() {
        String query = input.nextLine().trim();
        return query;
    }

    public void showError(String err) {
        System.out.println("DukeException: " +  err);
    }
}
