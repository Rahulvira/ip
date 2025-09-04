package james;

import java.io.IOException;
import java.nio.file.Paths;

public class James {
    private Database db;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructs a James chatbot instance with the specified file path for storage.
     * Initializes the UI, database, and task list. If loading fails, starts with an empty task list.
     *
     * @param filePath Path to the file used for persistent task storage.
     */
    public James(String filePath) {
        ui = new Ui();
        db = new Database(Paths.get(filePath));
        try {
            tasks = new TaskList(db.load());
        } catch (IOException e) {
            //ui.showLoadingError();
            //System.out.println(e.getMessage());
            tasks = new TaskList();
        }
    }

    /**
     * Starts the main loop of the application.
     * Continuously reads user input, parses commands, and executes them until exit is triggered.
     */
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

    /**
     * Processes a user query by parsing its intent and executing the corresponding command.
     *
     * @param query The raw user input string to be interpreted and executed.
     * @return A JamesResponse object containing either the result of the command execution
     *         or an error message if the query fails to parse or execute.
     */
    public JamesResponse getResponse(String query) {
        try {
            String type = Parser.parse(query, tasks.getSize());
            return Parser.execute(type, query, tasks, ui, db);
        } catch (JamesException e){
            return new JamesResponse(e.getMessage());
        }
    }

    /**
     * Retrieves the current list of tasks managed by the system.
     *
     * @return The TaskList object containing all tracked tasks.
     */
    public TaskList getTasks() {
        return this.tasks;
    }

    /**
     * Provides access to the underlying database for storing tasks.
     *
     * @return The Database instance responsible for data storage and retrieval.
     */
    public Database getDb() {
        return this.db;
    }

    public static void main(String[] args) throws JamesException, IOException {
        new James("data/James.txt").run();
    }
}



