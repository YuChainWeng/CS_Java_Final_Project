package game;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;
import javafx.scene.control.Button;



public class MainPage extends Application {

    private final int WIDTH = 650;
    private final int HEIGHT = 800;
    private SettingsPane settingsPane;
    Character character;
    private ImageView currentSelectedImageView; // Keep track of the currently selected character
    @Override
    public void start(Stage primaryStage) {

        // Use StackPane for easier background setting
        StackPane root = new StackPane();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(scene);
        character = new Character("Black");

        // Load and set the background image to fit the scene
        Image bgImage = new Image("file:src/main/resources/images/首頁背景.jpeg");
        BackgroundImage backgroundImage = new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT, 
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, 
                new BackgroundSize(100, 100, true, true, false, true));
        root.setBackground(new Background(backgroundImage));

        // Load character images
        ImageView char1ImageView = character.getCharacterImage("Black", 150, 300);
        //透明度50%
        char1ImageView.setOpacity(0.5);
        char1ImageView.setOnMouseClicked(e -> chooseCharacter("Black", char1ImageView));
        StackPane.setAlignment(char1ImageView, Pos.CENTER_LEFT);
        StackPane.setMargin(char1ImageView, new Insets(200, 0, 0, 25));  // Add some margin

        ImageView char2ImageView = character.getCharacterImage("White", 150, 300);
        char2ImageView.setOpacity(0.5);
        char2ImageView.setOnMouseClicked(e -> chooseCharacter("White", char2ImageView));
        StackPane.setAlignment(char2ImageView, Pos.CENTER_RIGHT);
        StackPane.setMargin(char2ImageView, new Insets(200, 25, 0, 0));  // Add some margin

        // Settings Button with image
        Image settingsImage = new Image("file:src/main/resources/images/設定.png");
        ImageView settingsImageView = new ImageView(settingsImage);
        settingsImageView.setFitWidth(37.5);
        settingsImageView.setFitHeight(75);
        Button settingsButton = new Button("", settingsImageView);
        settingsButton.setOnMouseClicked(e -> showSettings(primaryStage));
        settingsButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        StackPane.setAlignment(settingsButton, Pos.TOP_LEFT);
        StackPane.setMargin(settingsButton, new Insets(10));  // Add some margin

        // Start Image as clickable
        Image startImage = new Image("file:src/main/resources/images/開始.png");
        ImageView startImageView = new ImageView(startImage);
        startImageView.setFitWidth(150);
        startImageView.setFitHeight(75);
        startImageView.setOnMouseClicked(e -> startGame(primaryStage));
        StackPane.setAlignment(startImageView, Pos.BOTTOM_CENTER);
        StackPane.setMargin(startImageView, new Insets(0, 0, 150, 5));  // Adjust vertical position by setting bottom margin

        // High Scores
        Button highScoresButton = new Button("High Scores");
        highScoresButton.setOnAction(e -> showHighScores(primaryStage));
        StackPane.setAlignment(highScoresButton, Pos.BOTTOM_CENTER);
        StackPane.setMargin(highScoresButton, new Insets(0, 0, 100, 0));

        // Exit image and button
        Image exitImage = new Image("file:src/main/resources/images/離開.png");
        ImageView imageView = new ImageView(exitImage);
        imageView.setFitWidth(37.5);
        imageView.setFitHeight(75);
        Button exitButton = new Button("", imageView);
        exitButton.setOnAction(e -> primaryStage.close());
        exitButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        StackPane.setAlignment(exitButton, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(exitButton, new Insets(10));

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

    private void chooseCharacter(String characterName, ImageView charImageView) {
    	if (currentSelectedImageView != null && currentSelectedImageView != charImageView) {
            currentSelectedImageView.setOpacity(0.5);
        }
        // 選擇新角色並將透明度設置為 100%
        character.chooseCharacter(characterName);
        charImageView.setOpacity(1.0);

        // 更新當前選擇的角色及其 ImageView
        currentSelectedImageView = charImageView;
    }

    private void showSettings(Stage stage) {
        System.out.println("Settings Panel Opened");
        settingsPane.toggleVisibility();

        // Logic to display settings
    }

    private void startGame(Stage stage) {
        System.out.println("Game Started");
        GamePane gamePane = new GamePane(character);
        Scene gameScene = new Scene(gamePane, WIDTH, HEIGHT);
        stage.setScene(gameScene);
        // Logic to start the game
    }

    private void showHighScores(Stage stage) {
        // Logic to display high scores
    }

    public static void main(String[] args) {
        launch(args);
    }
}