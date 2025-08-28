import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.ArrayList;
public class James {
    private Database db;
    private TaskList tasks;
    private UI ui;

    public James(String filePath) {
        ui = new UI();
        db = new Database(Paths.get(filePath));
        try {
            tasks = new TaskList(db.load());
        } catch (IOException e) {
            //ui.showLoadingError();
            tasks = new TaskList();
        }
    }
    /**
     * Displays the task list in a numbered format line after line.
     *
     * @param arr Array containing tasks.
     */
//    public static void displayList(ArrayList<Task> arr) {
//        System.out.println(arr);
//        int count = 1;
//        for (Task tsk: arr) {
//            if (tsk != null) {
//                System.out.println("<" + count + "> " + tsk.toString());
//            }
//            count++;
//        }
//    }

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

    public static boolean isValidDeleteQuery(String[] words, int size) {
        return (words.length == 2)
                &&
                (words[0].equalsIgnoreCase("delete"))
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
    public static Task markTask(String[] words, ArrayList<Task> tasks) {
        int taskNo = Integer.parseInt(words[1].trim()) - 1;
        if (words[0].equalsIgnoreCase("mark")) {
            System.out.println("marked the following task!");
            //tasks[taskNo].finishTask();
            tasks.get(taskNo).finishTask();
        } else {
            System.out.println("unmarked the following task!");
            //tasks[taskNo].undoTask();
            tasks.get(taskNo).undoTask();
        }
        return tasks.get(taskNo);
    }

    public static Task deleteTask(String[] words, ArrayList<Task> tasks) {
        int taskNo = Integer.parseInt(words[1].trim()) - 1;
        System.out.println("deleted the following task!");
        return tasks.remove(taskNo);
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
        } else if (firstWord.equalsIgnoreCase("delete")) {
            if (splitQuery.length == 1) {
                throw new JamesException("Please specify which task to delete!");
            }
        } else if (firstWord.equalsIgnoreCase("mark") || firstWord.equalsIgnoreCase("unmark")) {
            if (!isValidMarkQuery(splitQuery, size)) {
                throw new JamesException("I can only mark the tasks we have, sorry!");
            }
        } else if (firstWord.equalsIgnoreCase("bye")) {
            // do nothing
        } else {
            throw new JamesException("Sorry!, I am not smart enough for this yet!");
        }
    }

    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String query = ui.readCommand();
                ui.showLine(); // show the divider line ("_______")
                String type = Parser.parse(query, tasks.getSize());
                Parser.execute(type, query, tasks, ui, db);
                isExit = Parser.isExit();
            } catch (JamesException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }


    public static void main(String[] args) throws JamesException, IOException {
        new James("data/James.txt").run();
//        Database db = new Database(Paths.get("data", "james.txt"));
//        UI ui = new UI();
//        ArrayList<Task> tasks = db.load();
//        int size = tasks.size();
//        // Scanner input = new Scanner(System.in);
////        System.out.println("Hey There! James at your service. \n" +
////                "How can I help you today?");
////        System.out.println("--------------------------------------------------------------");
//        ui.showWelcome();
//        ui.showLine();
//        while (true) {
//            System.out.println("input: ");
//            //if (!input.hasNextLine()) break;
//            String query = ui.readCommand();
//            try {
//                James.checkQuery(query, size);
//                //System.out.println("--------------------------------------------------------------");
//                ui.showLine();
//                if (query.equalsIgnoreCase("bye")) {
//                    break;
//                } else if (query.equalsIgnoreCase("list")) {
//                    System.out.print("output:\n");
//                    System.out.println("Task count: " + size);
//                    ui.displayList(tasks);
//                } else if (James.isValidMarkQuery(query.split(" "), size)) {
//                    Task editedTask = James.markTask(query.split(" "), tasks);
//                    System.out.println(editedTask);
//                } else if (James.isValidDeleteQuery(query.split(" "), size)){
//                    Task deletedTask = James.deleteTask(query.split(" "), tasks);
//                    System.out.println(deletedTask);
//                    size--;
//                } else {
//                    if (query.startsWith("todo")) {
//                        tasks.add(new Todo(query));
//                        System.out.println("output:\n" + "added: " + tasks.get(size));
//                        System.out.println(Task.TaskToString(tasks.get(size)));
//                        size++;
//                        System.out.println("Added as task number " + size);
//                    } else if (query.startsWith("event")) {
//                        tasks.add(new Event(query));
//                        System.out.println("output:\n" + "added: " + tasks.get(size));
//                        System.out.println(Task.TaskToString(tasks.get(size)));
//                        size++;
//                        System.out.println("Added as task number " + size);
//                    } else if (query.startsWith("deadline")) {
//                        tasks.add(new Deadline(query));
//                        System.out.println("output:\n" + "added: " + tasks.get(size));
//                        System.out.println(Task.TaskToString(tasks.get(size)));
//                        size++;
//                        System.out.println("Added as task number " + size);
//                    } else {
//                        System.out.println("invalid query");
//                    }
//                }
//                //System.out.println("--------------------------------------------------------------");
//                ui.showLine();
//            } catch(Exception e) {
//                //System.out.println("--------------------------------------------------------------");
//                ui.showLine();
//                ui.showError(e.getMessage());
//                //System.out.println("--------------------------------------------------------------");
//                ui.showLine();
//            }
//        }
//        //System.out.println("Bye, feel free to ask me anything anytime!");
//        ui.showBye();
//        // add all tasks to database
//        db.store(tasks);
    }
}



