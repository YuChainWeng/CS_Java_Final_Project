package game;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class SettingsPane extends VBox {
    private boolean isVisible = false;

    public SettingsPane() {
        super(10); // 10 is the spacing between elements in the VBox
        initializeUI();
    }

    private void initializeUI() {
        this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8); -fx-padding: 20;");
        this.setAlignment(javafx.geometry.Pos.CENTER);

        // Volume control slider
        Slider volumeSlider = new Slider(0, 100, 50);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setShowTickLabels(true);

        // Close button to hide the settings pane
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> this.setVisible(false)); // Toggle visibility

        this.getChildren().addAll(volumeSlider, closeButton);
        this.setVisible(false); // Initially hide the settings pane
    }

    public void toggleVisibility() {
        isVisible = !isVisible;
        this.setVisible(isVisible);
    }
}