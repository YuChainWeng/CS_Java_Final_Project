package game;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class EndPane extends Pane {
    private int levelReached;

    public EndPane(int levelReached, Runnable onRestart, Runnable onReturnToMain) {
        this.levelReached = levelReached;
        initializeUI(onRestart, onReturnToMain);
    }

    private void initializeUI(Runnable onRestart, Runnable onReturnToMain) {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);

        Text gameOverText = new Text("Game Over");
        gameOverText.setFont(Font.font("Arial", 24));

        Text levelText = new Text("Level reached: " + levelReached);
        levelText.setFont(Font.font("Arial", 18));

        Button restartButton = new Button("Restart");
        restartButton.setOnAction(e -> onRestart.run());

        Button returnToMainButton = new Button("Return to Main Menu");
        returnToMainButton.setOnAction(e -> onReturnToMain.run());

        layout.getChildren().addAll(gameOverText, levelText, restartButton, returnToMainButton);
        this.getChildren().add(layout);

        // Center the layout in the pane
        layout.setLayoutX(325 - layout.getBoundsInParent().getWidth() / 2);
        layout.setLayoutY(450 - layout.getBoundsInParent().getHeight() / 2);
    }
}