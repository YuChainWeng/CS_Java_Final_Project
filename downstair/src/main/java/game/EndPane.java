package game;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Background;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class EndPane extends Pane {
    private int levelReached;
    private Character character;
    private boolean soundEffect = true;

    public EndPane(int levelReached, Runnable onRestart, Runnable onReturnToMain, Character character, Runnable exit) {
        this.levelReached = levelReached;
        this.character = character;
        initializeUI(onRestart, onReturnToMain, exit);
    }

    private void initializeUI(Runnable onRestart, Runnable onReturnToMain, Runnable exit) {
        // Set the background image
        Image bgImage = new Image("file:src/main/resources/images/結束背景.png");
        BackgroundImage backgroundImage = new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, false, true));
        this.setBackground(new Background(backgroundImage));

        // create the character died image
        ImageView CharacterDiedImage = character.getCharacterDiedImage(character.getCharacterName(), 300, 600);
        CharacterDiedImage.setLayoutX(165);
        CharacterDiedImage.setLayoutY(225);
        this.getChildren().add(CharacterDiedImage);

        // create the game over image
        Image gameoverImage = new Image("file:src/main/resources/images/封印.png");
        ImageView gameoverImageView = new ImageView(gameoverImage);
        // set the position of the game over image
        gameoverImageView.setFitWidth(300);
        gameoverImageView.setFitHeight(150);
        gameoverImageView.setLayoutX(155);
        gameoverImageView.setLayoutY(25);
        this.getChildren().add(gameoverImageView);

        // create the level box put the level label and text
        HBox levelbox = new HBox(20);
        levelbox.setAlignment(Pos.CENTER);
        // create the level label
        Label levelLabel = new Label("Level");
        levelLabel.setFont(Font.font("Arial", 35));
        levelLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        levelbox.getChildren().add(levelLabel);
        // create the level text
        Text levelText = new Text(" " + levelReached);
        levelText.setFont(Font.font("Arial", 35));
        levelText.setStyle("-fx-fill: white; -fx-font-weight: bold;");
        levelbox.getChildren().add(levelText);
        // set the position of the level box
        levelbox.setLayoutX(240);
        levelbox.setLayoutY(170);

        // create the restart button
        Button restartButton = new Button("");
        Image restartImage = new Image("file:src/main/resources/images/重新開始.png");
        ImageView restartImageView = new ImageView(restartImage);
        restartImageView.setFitWidth(120);
        restartImageView.setFitHeight(144);
        // set the position of the button
        restartButton.setLayoutX(450);
        restartButton.setLayoutY(400);
        restartButton.setGraphic(restartImageView);
        restartButton.setStyle("-fx-background-color: transparent;");
        restartButton.setOnAction(e -> restart(onRestart));

        // create the return to main button
        Button returnToMainButton = new Button("");
        Image returnToMainImage = new Image("file:src/main/resources/images/回到主頁.png");
        ImageView returnToMainImageView = new ImageView(returnToMainImage);
        returnToMainImageView.setFitWidth(120);
        returnToMainImageView.setFitHeight(144);
        // set the position of the button
        returnToMainButton.setLayoutX(50);
        returnToMainButton.setLayoutY(400);
        returnToMainButton.setGraphic(returnToMainImageView);
        returnToMainButton.setStyle("-fx-background-color: transparent;");
        returnToMainButton.setOnAction(e -> returnToMain(onReturnToMain));

        // Exit Button with image
        Image exitImage = new Image("file:src/main/resources/images/離開.png");
        ImageView imageView = new ImageView(exitImage);
        imageView.setFitWidth(37.5);
        imageView.setFitHeight(75);
        Button exitButton = new Button("", imageView);
        exitButton.setOnAction(e -> exit(exit));
        exitButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        exitButton.setLayoutX(580);
        exitButton.setLayoutY(800);

        // add the level box, restart button and return to main button
        this.getChildren().addAll(levelbox, restartButton, returnToMainButton, exitButton);
    }

    // return to main page
    public void returnToMain(Runnable onReturnToMain) {
        if (soundEffect) // play the sound effect
            playAudio("src/main/resources/audios/打開音效.mp3");
        onReturnToMain.run();
    }

    // restart the game
    public void restart(Runnable onRestart) {
        if (soundEffect) // play the sound effect
            playAudio("src/main/resources/audios/打開音效.mp3");
        onRestart.run();
    }

    // set the sound effect enable or disable
    public void setSoundEffect(boolean soundEffect) {
        this.soundEffect = soundEffect;
    }

    // play the audio file
    public void playAudio(String path) {
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public void exit(Runnable exRunnable) {
        exRunnable.run();
    }
}