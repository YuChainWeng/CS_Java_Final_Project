package game;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Background;
import javafx.scene.image.ImageView;

public class EndPane extends Pane {
    private int levelReached;
    Character character;

    public EndPane(int levelReached, Runnable onRestart, Runnable onReturnToMain, Character character) {
        this.levelReached = levelReached;
        this.character = character;
        initializeUI(onRestart, onReturnToMain);
    }

    private void initializeUI(Runnable onRestart, Runnable onReturnToMain) {

        Image bgImage = new Image("file:src/main/resources/images/結束背景.png");
        BackgroundImage backgroundImage = new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT, 
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, 
                new BackgroundSize(100, 100, true, true, false, true));
        this.setBackground(new Background(backgroundImage));

        Image gameoverImage = new Image("file:src/main/resources/images/封印.png");
        ImageView gameoverImageView = new ImageView(gameoverImage);
        gameoverImageView.setFitWidth(300);
        gameoverImageView.setFitHeight(150);
        gameoverImageView.setLayoutX(175);
        gameoverImageView.setLayoutY(450);
        this.getChildren().add(gameoverImageView);

        ImageView CharacterDiedImage = character.getCharacterDiedImage(character.getCharacterName(), 150, 300);
        CharacterDiedImage.setLayoutX(250);
        CharacterDiedImage.setLayoutY(150);
        this.getChildren().add(CharacterDiedImage);

        VBox layout = new VBox(20);

        HBox levelbox = new HBox(20);
        levelbox.setAlignment(Pos.CENTER);

        HBox button = new HBox(20);
        button.setAlignment(Pos.CENTER);

        Image LevelImage = new Image("file:src/main/resources/images/Level.png");
        ImageView LevelImageView = new ImageView(LevelImage);
        LevelImageView.setFitWidth(160);
        LevelImageView.setFitHeight(60);
        levelbox.getChildren().add(LevelImageView);

        Text levelText = new Text(" " + levelReached);
        levelText.setFont(Font.font("Arial", 35));
        levelText.setStyle("-fx-fill: white;");
        levelbox.getChildren().add(levelText);

        Button restartButton = new Button("");
        Image restartImage = new Image("file:src/main/resources/images/重新開始.png");
        ImageView restartImageView = new ImageView(restartImage);
        restartImageView.setFitWidth(100);
        restartImageView.setFitHeight(120);
        restartButton.setGraphic(restartImageView);
        button.getChildren().add(restartButton);
        restartButton.setStyle("-fx-background-color: transparent;");
        restartButton.setOnAction(e -> onRestart.run());

        Button returnToMainButton = new Button("");
        Image returnToMainImage = new Image("file:src/main/resources/images/回到主頁.png");
        ImageView returnToMainImageView = new ImageView(returnToMainImage);
        returnToMainImageView.setFitWidth(100);
        returnToMainImageView.setFitHeight(120);
        returnToMainButton.setGraphic(returnToMainImageView);
        button.getChildren().add(returnToMainButton);
        returnToMainButton.setStyle("-fx-background-color: transparent;");
        returnToMainButton.setOnAction(e -> onReturnToMain.run());

        layout.getChildren().addAll(levelbox, button);
        this.getChildren().add(layout);

        // Center the layout in the pane
        layout.setLayoutX(200);
        layout.setLayoutY(600);
    }
}