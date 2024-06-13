package game;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class MainPage extends Application {

	// Main stage
	private Stage primaryStage;
	// Window dimensions
	private final int WIDTH = 650;
	private final int HEIGHT = 900;
	// Settings Pane
	private SettingsPane settingsPane;
	// Instructions Pane
	private InstructionPane instructionsPane;
	// Character
	private Character character;
	// Label for choosing character
	private Label chooseCharacterLabel;
	// Current selected character ImageView
	private ImageView currentSelectedImageView;
	private MediaPlayer mediaPlayer;
	// Sound effect on or off
	private boolean SoundEffect = true;

	// Start the primary stage
	@Override
	public void start(Stage primaryStage) {

		// Set the primary stage
		this.primaryStage = primaryStage;
		// Set the background UI
		setupInitialUI(primaryStage);
		// Set it unresizable
		primaryStage.setResizable(false);
		primaryStage.show();

	}

	// Set up the initial UI
	private void setupInitialUI(Stage stage) {
		// Create a stack pane as the root
		StackPane root = new StackPane();
		Scene scene = new Scene(root, WIDTH, HEIGHT);
		stage.setScene(scene);
		character = new Character("Black", 0, 0, 45, 90, 0);
		// Play background music
		playBackgroundMusic("src/main/resources/audios/bgm.mp3");

		// Set background image
		Image bgImage = new Image("file:src/main/resources/images/首頁背景.jpeg");
		BackgroundImage backgroundImage = new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
				new BackgroundSize(100, 100, true, true, false, true));
		root.setBackground(new Background(backgroundImage));
		
		// choose character label
		chooseCharacterLabel = new Label("請選擇角色");
		chooseCharacterLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 25;");
		chooseCharacterLabel.setTranslateY(50);
		StackPane.setAlignment(chooseCharacterLabel, Pos.CENTER);
		StackPane.setMargin(chooseCharacterLabel, new Insets(0, 0, 0, 0));
		root.getChildren().add(chooseCharacterLabel);

		// Character1 selection images with opacity
		ImageView char1ImageView = character.getCharacterImage("Black", 150, 300);
		char1ImageView.setOpacity(0.5);
		char1ImageView.setOnMouseClicked(e -> chooseCharacter("Black", char1ImageView));
		StackPane.setAlignment(char1ImageView, Pos.CENTER_LEFT);
		StackPane.setMargin(char1ImageView, new Insets(200, 0, 0, 25));
		
		// Character2 selection images with opacity
		ImageView char2ImageView = character.getCharacterImage("White", 150, 300);
		char2ImageView.setOpacity(0.5);
		char2ImageView.setOnMouseClicked(e -> chooseCharacter("White", char2ImageView));
		StackPane.setAlignment(char2ImageView, Pos.CENTER_RIGHT);
		StackPane.setMargin(char2ImageView, new Insets(200, 25, 0, 0));

		// Settings Button with image
		Image settingsImage = new Image("file:src/main/resources/images/設定.png");
		ImageView settingsImageView = new ImageView(settingsImage);
		settingsImageView.setFitWidth(37.5);
		settingsImageView.setFitHeight(75);
		Button settingsButton = new Button("", settingsImageView);
		settingsButton.setOnMouseClicked(e -> showSettings(stage));
		settingsButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		StackPane.setAlignment(settingsButton, Pos.TOP_LEFT);
		StackPane.setMargin(settingsButton, new Insets(10));

		// Instruction Button with image
		Image instructionImage = new Image("file:src/main/resources/images/遊戲說明.png");
		ImageView instructionImageView = new ImageView(instructionImage);
		instructionImageView.setFitWidth(37.5);
		instructionImageView.setFitHeight(75);
		Button instructionButton = new Button("", instructionImageView);
		instructionButton.setOnMouseClicked(e -> showInstruction(stage));
		instructionButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		StackPane.setAlignment(instructionButton, Pos.TOP_RIGHT);
		StackPane.setMargin(instructionButton, new Insets(10));

		// Start image and button
		Image startImage = new Image("file:src/main/resources/images/開始.png");
		ImageView startImageView = new ImageView(startImage);
		startImageView.setFitWidth(150);
		startImageView.setFitHeight(180);
		startImageView.setOnMouseClicked(e -> startGame(stage));
		StackPane.setAlignment(startImageView, Pos.BOTTOM_CENTER);
		StackPane.setMargin(startImageView, new Insets(0, 0, 150, 5));

		// Exit Button with image
		Image exitImage = new Image("file:src/main/resources/images/離開.png");
		ImageView imageView = new ImageView(exitImage);
		imageView.setFitWidth(37.5);
		imageView.setFitHeight(75);
		Button exitButton = new Button("", imageView);
		exitButton.setOnAction(e -> stage.close());
		exitButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		StackPane.setAlignment(exitButton, Pos.BOTTOM_RIGHT);
		StackPane.setMargin(exitButton, new Insets(10));

		// Add all nodes to root
		root.getChildren().addAll(settingsButton, startImageView, exitButton, char1ImageView,
				char2ImageView,instructionButton);

		// Initialize Settings Pane
		settingsPane = new SettingsPane(this);
		StackPane.setAlignment(settingsPane, Pos.CENTER);
		StackPane.setMargin(settingsPane, new Insets(0, 0, 0, 0));
		root.getChildren().add(settingsPane);

		// Initialize Instructions Pane
		instructionsPane = new InstructionPane(SoundEffect);
		StackPane.setAlignment(instructionsPane, Pos.CENTER);
		StackPane.setMargin(instructionsPane, new Insets(0, 0, 0, 0));
		root.getChildren().add(instructionsPane);
	}

	// Choose character
	private void chooseCharacter(String characterName, ImageView charImageView) {
		// Check if the sound effect is on
		SoundEffect = settingsPane.getSoundeffect();
		// If there isn't a current selected image view, set the opacity to 0.5
		if (currentSelectedImageView != null && currentSelectedImageView != charImageView) {
			currentSelectedImageView.setOpacity(0.5);
		}
		// Play sound effect
		if (SoundEffect)
			playAudio("src/main/resources/audios/button03a.mp3");
		// Set the character with the chosen character name
		character.chooseCharacter(characterName);
		// Set the opacity of the chosen character to 1
		charImageView.setOpacity(1.0);

		// Update the current selected image view
		currentSelectedImageView = charImageView;
		chooseCharacterLabel.setVisible(false);
	}

	private void showSettings(Stage stage) {
		// Check if the sound effect is on
		SoundEffect = settingsPane.getSoundeffect();
		// Play sound effect
		if (SoundEffect)
			playAudio("src/main/resources/audios/打開音效.mp3");
		// Show the settings pane
		settingsPane.toggleVisibility();
	}

	private void showInstruction(Stage stage) {
		// Check if the sound effect is on and play sound effect
		SoundEffect = settingsPane.getSoundeffect();
		if (SoundEffect)
			playAudio("src/main/resources/audios/打開音效.mp3");
		// Show the instructions pane
		instructionsPane.setSoundEffect(SoundEffect);
		instructionsPane.toggleVisibility();
	}

	// Start the game
	private void startGame(Stage stage) {
		// Check if there is a selected character
		if (currentSelectedImageView == null)
			return;

		// Check if the sound effect is on and play sound effect
		SoundEffect = settingsPane.getSoundeffect();
		if (SoundEffect)
			playAudio("src/main/resources/audios/打開音效.mp3");

		// Create a new game pane
		GamePane gamePane = new GamePane(character, SoundEffect);
		Scene gameScene = new Scene(gamePane, WIDTH, HEIGHT);
		stage.setScene(gameScene);
		// Focus on the game pane
		gamePane.requestFocus();
		// Check if the game has ended
		gamePane.setGameEndListener(() -> handleGameEnd(gamePane, stage));
	}

	// Handle game end
	private void handleGameEnd(GamePane gamePane, Stage stage) {
		// Stop the game loop and cleanup resources
		gamePane.stopGameLoop();
		gamePane.cleanupResources();
		// Show the game over screen
		showGameOverScreen(gamePane.getLevel(), stage);

	}

	private void showGameOverScreen(int level, Stage stage) {
		// Show the end pane (game over screen) and determine if the player wants to restart the game or go back to the main page
		EndPane endPane = new EndPane(level, () -> restartGame(stage), () -> showMainPage(), character);
		stage.setScene(new Scene(endPane, WIDTH, HEIGHT));
	}

	// Restart the game
	private void restartGame(Stage stage) {
		startGame(stage);
	}

	// Return to the main page
	private void showMainPage() {
		setupInitialUI(primaryStage);
	}

	// Play audio
	private void playAudio(String audioFilePath) {
		Media sound = new Media(new File(audioFilePath).toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.play();
	}

	// Play background music
	public void playBackgroundMusic(String audioFilePath) {
		Media sound = new Media(new File(audioFilePath).toURI().toString());
		mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer.setVolume(0.3);
		mediaPlayer.play();
	}

	// Stop background music
	public void stopBackgroundMusic() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}