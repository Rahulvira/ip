package james.gui;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import james.Database;
import james.James;
import james.Task;
import james.TaskList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for James using FXML.
 */
public class Main extends Application {

    private James james = new James("data/James.txt");
    private Database db;
    private TaskList tasks;

    @Override
    public void start(Stage stage) {
        try {
            tasks = james.getTasks();
            assert tasks != null : "TaskList object must not be null";
            db = james.getDb();
            assert db != null : "database object must not be null";
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setJames(james);  // inject the James instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        try {
            db.store(tasks);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}

