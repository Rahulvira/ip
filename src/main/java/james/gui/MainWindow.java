package james.gui;

import james.James;
import james.JamesResponse;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private James james;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/coolie.jpeg"));
    private Image botImage = new Image(this.getClass().getResourceAsStream("/images/simon.jpeg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the James instance */
    public void setJames(James j) {
        james = j;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing James's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        JamesResponse response = james.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getJamesDialog(response.getMessage(), botImage)
        );
        if (input.equalsIgnoreCase("bye")) {
            // Delay exit to allow message to render
            PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
            delay.setOnFinished(event -> Platform.exit());
            delay.play();
        }

        userInput.clear();
    }
}

