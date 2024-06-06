package game;

import javafx.application.Application;
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

public class NSShaftGame extends Application {

    private final int WIDTH = 320;
    private final int HEIGHT = 458;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("NS-SHAFT Game");

        // Use StackPane for easier background setting
        StackPane root = new StackPane();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(scene);

        // Load and set the background image
        Image bgImage = new Image("file:src/main/resources/images/Background.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT, 
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        root.setBackground(new Background(backgroundImage));

        // Settings Image as clickable
        Image settingsImage = new Image("file:src/main/resources/images/SettingButton.jpg");
        if (settingsImage.isError()) {
            System.out.println("Error loading settings image: " + settingsImage.getException());
        }
        ImageView settingsImageView = new ImageView(settingsImage);
        settingsImageView.setFitWidth(25);
        settingsImageView.setFitHeight(25);
        settingsImageView.setOnMouseClicked(e -> showSettings(primaryStage));

        // Main Menu
        VBox menuBox = new VBox(10);
        menuBox.setTranslateX(WIDTH / 2 - 50);
        menuBox.setTranslateY(HEIGHT / 3);

        Text title = new Text("NS-SHAFT");
        title.setFont(Font.font("Arial", 30));
        title.setFill(Color.WHITE);

        // Start Image as clickable
        Image startImage = new Image("file:src/main/resources/images/StartButton.jpg");
        if (startImage.isError()) {
            System.out.println("Error loading start image: " + startImage.getException());
        }
        ImageView startImageView = new ImageView(startImage);
        startImageView.setFitWidth(100);
        startImageView.setFitHeight(50);
        startImageView.setOnMouseClicked(e -> startGame(primaryStage));

        // High Scores and Exit Buttons
        Button highScoresButton = new Button("High Scores");
        highScoresButton.setOnAction(e -> showHighScores(primaryStage));

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> primaryStage.close());

        menuBox.getChildren().addAll(title, settingsImageView, startImageView, highScoresButton, exitButton);
        root.getChildren().add(menuBox);

        primaryStage.show();
    }

    private void showSettings(Stage stage) {
        System.out.println("Settings Panel Opened");
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
