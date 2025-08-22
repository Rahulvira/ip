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
    public static boolean isValidMarkQuery(String[] words, int size) {
        return (words.length == 2)
                &&
                (words[0].equalsIgnoreCase("mark") || words[0].equalsIgnoreCase("unmark"))
                &&
                (words[1].matches("^[+-]?\\d+$")) // to check if it is an integer
                &&
                (Integer.parseInt(words[1]) <= size);
    }
    /**
     * Checks or unchecks task based on input.
     *
     * @param words Array containing the parsed elements of scanned input.
     * @param tasks Array containing tasks.
     * @return Task Updated Task.
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

    public static void checkQuery(String query, int size) throws JamesException {
        String[] splitQuery = query.split(" ", 2);
        String firstWord = splitQuery[0];
        if (firstWord.equalsIgnoreCase("todo")) {
            if (splitQuery.length == 1) {
                throw new JamesException("You forgot the task buddy!");
            }
        } else if (firstWord.equalsIgnoreCase("event")) {
            if (splitQuery.length == 1) {
                throw new JamesException("You forgot the task buddy!");
            }
        } else if (firstWord.equalsIgnoreCase("deadline")) {
            if (splitQuery.length == 1) {
                throw new JamesException("You forgot the task buddy!");
            }
        } else if (firstWord.equalsIgnoreCase("list")) {
            if (splitQuery.length > 1) {
                throw new JamesException("I can only reply if you just say 'list', sorry! ");
            }
        } else if (firstWord.equalsIgnoreCase("mark") || firstWord.equalsIgnoreCase("unmark")) {
            if (!isValidMarkQuery(splitQuery, size)) {
                throw new JamesException("I can only mark the tasks we have, sorry!");
            }
        } else {
            throw new JamesException("Sorry, I am not smart enough for this yet!");
        }
    }


    public static void main(String[] args) throws JamesException {
        Task[] tasks = new Task[100];
        int size = 0;
        Scanner input = new Scanner(System.in);
        System.out.println("Hey There! James at your service. \n" +
                "How can I help you today?");
        System.out.println("--------------------------------------------------------------");
        while (true) {
            System.out.println("input: ");
            if (!input.hasNextLine()) break;
            String query = input.nextLine().trim();
            try {
                James.checkQuery(query, size);

                System.out.println("--------------------------------------------------------------");
                if (query.equalsIgnoreCase("bye")) {
                    break;
                } else if (query.equalsIgnoreCase("list")) {
                    System.out.print("output:\n");
                    System.out.println("Task count: " + size);
                    James.displayList(tasks);
                } else if (James.isValidMarkQuery(query.split(" "), size)) {
                    Task editedTask = James.markTask(query.split(" "), tasks);
                    System.out.println(editedTask);
                } else {
                    if (query.startsWith("todo")) {
                        tasks[size] = new Todo(query);
                        System.out.println("output:\n" + "added: " + tasks[size]);
                        size++;
                        System.out.println("Added as task number " + size);
                    } else if (query.startsWith("event")) {
                        tasks[size] = new Event(query);
                        System.out.println("output:\n" + "added: " + tasks[size]);
                        size++;
                        System.out.println("Added as task number " + size);
                    } else if (query.startsWith("deadline")) {
                        tasks[size] = new Deadline(query);
                        System.out.println("output:\n" + "added: " + tasks[size]);
                        size++;
                        System.out.println("Added as task number " + size);
                    } else {
                        System.out.println("invalid query");
                    }
                }
                System.out.println("--------------------------------------------------------------");
            } catch(Exception e) {
                System.out.println("--------------------------------------------------------------");
                System.out.println(e.getMessage());
                System.out.println("--------------------------------------------------------------");
            }
        }
        System.out.println("Bye, feel free to ask me anything anytime!");
    }
}



