package game;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
public class InstructionPane extends StackPane {
    private boolean isVisible = false;
    private boolean soundEffect = true;

    public InstructionPane(boolean soundEffect) {
        this.soundEffect = soundEffect;
        initializeUI();
    }

    private void initializeUI() {
        this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8); -fx-padding: 20;");
        this.setAlignment(javafx.geometry.Pos.CENTER);

        Image firstInstruction = new Image("file:src/main/resources/images/遊戲說明1.png");
        Image secondInstruction = new Image("file:src/main/resources/images/遊戲說明2.png");
        Image leftButton = new Image("file:src/main/resources/images/向左頁.png");
        Image rightButton = new Image("file:src/main/resources/images/向右頁.png");

        ImageView firstInstructionView = new ImageView(firstInstruction);
        ImageView secondInstructionView = new ImageView(secondInstruction);
        ImageView leftButtonView = new ImageView(leftButton);
        ImageView rightButtonView = new ImageView(rightButton);

        firstInstructionView.setX(0);
        firstInstructionView.setY(0);

        rightButtonView.setX(1400);
        rightButtonView.setY(1450);
        
        secondInstructionView.setX(0);
        secondInstructionView.setY(0);
        
        leftButtonView.setX(180);
        leftButtonView.setY(1450);
        Group firstPage = new Group();
        firstPage.getChildren().addAll(firstInstructionView, rightButtonView);
        firstPage.setScaleX(0.4);
        firstPage.setScaleY(0.4);

        Group secondPage = new Group();
        secondPage.getChildren().addAll(secondInstructionView, leftButtonView);
        secondPage.setScaleX(0.4);
        secondPage.setScaleY(0.4);
        secondPage.setVisible(false);
        
		leftButtonView.setOnMouseClicked(e -> {
			playAudio("src/main/resources/audios/關閉背景音樂.mp3");
			firstPage.setVisible(true);
			secondPage.setVisible(false);
		});
		rightButtonView.setOnMouseClicked(e -> {
			playAudio("src/main/resources/audios/關閉背景音樂.mp3");
			firstPage.setVisible(false);
			secondPage.setVisible(true);
		});
        Image closeImage = new Image("file:src/main/resources/images/離開.png");
        ImageView closeImageView = new ImageView(closeImage);
        closeImageView.setFitWidth(40);
        closeImageView.setFitHeight(80);
        closeImageView.setOnMouseClicked(e -> Leave());
        closeImageView.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        closeImageView.setTranslateY(250);
    
        this.getChildren().addAll(firstPage, secondPage, closeImageView);
        this.setVisible(false); // Initially hide the settings pane
    }

    public void setSoundEffect(boolean soundEffect) {
        this.soundEffect = soundEffect;
    }

    public void Leave() {
        if(soundEffect)
            playAudio("src/main/resources/audios/關閉音效.mp3");
        this.setVisible(false);
    }

    public void toggleVisibility() {
        isVisible = !isVisible;
        this.setVisible(isVisible);
    }
    
	public void playAudio(String Path) {
		Media media = new Media(new File(Path).toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
	}
}
