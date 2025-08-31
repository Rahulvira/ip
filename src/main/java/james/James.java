package james;

import java.io.IOException;
import java.nio.file.Paths;

public class James {
    private Database db;
    private TaskList tasks;
    private Ui ui;

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
    }
}



