package game;

import java.util.Stack;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.scene.layout.HBox;



public class MainPage extends Application {

    private final int WIDTH = 650;
    private final int HEIGHT = 900;
    private SettingsPane settingsPane;

    @Override
    public void start(Stage primaryStage) {

        // Use StackPane for easier background setting
        StackPane root = new StackPane();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(scene);

        // Load and set the background image to fit the scene
        Image bgImage = new Image("file:src/main/resources/images/Background.jpeg");
        BackgroundImage backgroundImage = new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT, 
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, 
                new BackgroundSize(100, 100, true, true, false, true));
        root.setBackground(new Background(backgroundImage));

        // Load character images
        Image char1Image = new Image("file:src/main/resources/images/黑無常正面.png");
        ImageView char1ImageView = new ImageView(char1Image);
        char1ImageView.setFitWidth(150);
        char1ImageView.setFitHeight(300);
        char1ImageView.setOnMouseClicked(e -> chooseCharacter("Black"));
        StackPane.setAlignment(char1ImageView, Pos.CENTER_LEFT);
        StackPane.setMargin(char1ImageView, new Insets(200, 0, 0, 25));  // Add some margin

        Image char2Image = new Image("file:src/main/resources/images/白無常正面.png");
        ImageView char2ImageView = new ImageView(char2Image);
        char2ImageView.setFitWidth(150);
        char2ImageView.setFitHeight(300);
        char2ImageView.setOnMouseClicked(e -> chooseCharacter("White"));
        StackPane.setAlignment(char2ImageView, Pos.CENTER_RIGHT);
        StackPane.setMargin(char2ImageView, new Insets(200, 25, 0, 0));  // Add some margin

        // Settings Button with image
        Image settingsImage = new Image("file:src/main/resources/images/SettingButton.jpg");
        ImageView settingsImageView = new ImageView(settingsImage);
        settingsImageView.setFitWidth(25);
        settingsImageView.setFitHeight(25);
        Button settingsButton = new Button("", settingsImageView);
        settingsButton.setOnMouseClicked(e -> showSettings(primaryStage));
        settingsButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        StackPane.setAlignment(settingsButton, Pos.TOP_LEFT);
        StackPane.setMargin(settingsButton, new Insets(10));  // Add some margin

        // Start Image as clickable
        Image startImage = new Image("file:src/main/resources/images/StartButton.png");
        ImageView startImageView = new ImageView(startImage);
        startImageView.setFitWidth(150);
        startImageView.setFitHeight(75);
        startImageView.setOnMouseClicked(e -> startGame(primaryStage));
        StackPane.setAlignment(startImageView, Pos.BOTTOM_CENTER);
        StackPane.setMargin(startImageView, new Insets(0, 0, 150, 5));  // Adjust vertical position by setting bottom margin

        // High Scores and Exit Buttons
        Button highScoresButton = new Button("High Scores");
        highScoresButton.setOnAction(e -> showHighScores(primaryStage));
        StackPane.setAlignment(highScoresButton, Pos.BOTTOM_CENTER);
        StackPane.setMargin(highScoresButton, new Insets(0, 0, 100, 0));

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> primaryStage.close());
        StackPane.setAlignment(exitButton, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(exitButton, new Insets(0, 50, 50, 0));

        // Add all nodes to root
        root.getChildren().addAll(settingsButton, startImageView, highScoresButton, exitButton, char1ImageView, char2ImageView);

        // Initialize Settings Pane
        settingsPane = new SettingsPane();
        StackPane.setAlignment(settingsPane, Pos.CENTER);
        StackPane.setMargin(settingsPane, new Insets(0, 0, 0, 0));  // Add some margin
        root.getChildren().add(settingsPane);


        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void chooseCharacter(String characterName) {
        System.out.println(characterName + " chosen");
        // Here you can add logic to proceed with the chosen character
    }

    private void showSettings(Stage stage) {
        System.out.println("Settings Panel Opened");
        settingsPane.toggleVisibility();

        // Logic to display settings
    }

    private void startGame(Stage stage) {
        System.out.println("Game Started");
        // Logic to start the game
    }

    private void showHighScores(Stage stage) {
        // Logic to display high scores
    }

    public static void main(String[] args) {
        launch(args);
    }
}