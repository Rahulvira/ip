import java.util.Scanner;
public class James {
    /**
     * Displays the task list in a numbered format line after line.
     *
     * @param arr Array containing tasks.
     */
    public static void displayList(Task[] arr) {
        int count = 1;
        for (Task tsk: arr) {
            if (tsk != null) {
                System.out.println("<" + count + "> " + tsk.toString());
            }
            count++;
        }
    }

    /**
     * Checks if the mark or unmark query is valid.
     *
     * @param words Array containing the parsed elements of scanned input.
     * @return Boolean based on query validity.
     */
    public static boolean isValidMarkQuery(String[] words) {
        return (words.length == 2)
                &&
                (words[0].equalsIgnoreCase("mark") || words[0].equalsIgnoreCase("unmark"))
                &&
                (words[1].matches("^[+-]?\\d+$")); // to check if it is an integer
    }
    /**
     * Checks if the mark or unmark query is valid.
     *
     * @param words Array containing the parsed elements of scanned input.
     * @param tasks Array containing tasks.
     * @return Boolean based on query validity.
     */
    public static Task markTask(String[] words, Task[] tasks) {
        int taskNo = Integer.parseInt(words[1].trim()) - 1;
        if (words[0].equalsIgnoreCase("mark")) {
            System.out.println("marked the following task!");
            tasks[taskNo].finishTask();
        } else {
            System.out.println("unmarked the following task!");
            tasks[taskNo].undoTask();
        }
        return tasks[taskNo];
    }

    public static void main(String[] args) {
        Task[] tasks = new Task[100];
        int size = 0;
        Scanner input = new Scanner(System.in);
        System.out.println("Hey There! James at your service. \n" +
                "How can I help you today?");
        System.out.println("--------------------------------------------------------------");
        while (true) {
            System.out.println("input: ");
            String query = input.nextLine().trim();
            System.out.println("--------------------------------------------------------------");
            if (query.equalsIgnoreCase("bye")) break;
            if (query.equalsIgnoreCase("list")) {
                System.out.print("output:\n");
                System.out.println("Task count: " + size);
                James.displayList(tasks);
            } else if(James.isValidMarkQuery(query.split(" "))) {
                Task editedTask = James.markTask(query.split(" "), tasks);
                System.out.println(editedTask);
            } else {
                System.out.println("output:\n" + "added: " + query);
                tasks[size] = new Task(query);
                size++;
            }
            System.out.println("--------------------------------------------------------------");
        }
        System.out.println("Bye, feel free to ask me anything anytime!");
    }
}



