package game;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
public class SettingsPane extends VBox {
    private boolean isVisible = false;
    private MainPage mainPage;
    private boolean soundEffect = true;
    public SettingsPane(MainPage mainPage) {
        super(2); // 2 is the spacing between elements in the VBox
        this.mainPage = mainPage;
        initializeUI();
    }

    private void initializeUI() {
        this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8); -fx-padding: 20;");
        this.setAlignment(javafx.geometry.Pos.CENTER);
        
        Image sound_effect_img = new Image("file:src/main/resources/images/遊戲音效.png");
        Image bgm_img = new Image("file:src/main/resources/images/遊戲音樂.png");
        Image on_img = new Image("file:src/main/resources/images/on.png");
        Image off_img = new Image("file:src/main/resources/images/off.png");
        // 創建ImageView並設置圖片
        ImageView sound_effect_imgview = new ImageView(sound_effect_img);
        ImageView bmg_imgview = new ImageView(bgm_img);
        ImageView on_imgview = new ImageView(on_img);
        ImageView off_imgview = new ImageView(off_img);
        ImageView on_imgview_2 = new ImageView(on_img);
        ImageView off_imgview_2 = new ImageView(off_img);

        // 設置每個ImageView的位置，如果需要重疊，可以讓位置相同
        sound_effect_imgview.setX(0);
        sound_effect_imgview.setY(0);

        bmg_imgview.setX(0);
        bmg_imgview.setY(0);

        on_imgview.setX(400); // 為了顯示疊加效果，這裡稍微調整位置
        on_imgview.setY(87);

        off_imgview.setX(600); // 為了顯示疊加效果，這裡稍微調整位置
        off_imgview.setY(80);
        off_imgview.setOpacity(0.3);

        on_imgview_2.setX(400); // 為了顯示疊加效果，這裡稍微調整位置
        on_imgview_2.setY(65);

        off_imgview_2.setX(600); // 為了顯示疊加效果，這裡稍微調整位置
        off_imgview_2.setY(55);
        off_imgview_2.setOpacity(0.3);
        
        Group bgm = new Group(); // Background music
        bgm.getChildren().addAll(bmg_imgview, on_imgview, off_imgview);
        bgm.setScaleX(0.4);
        bgm.setScaleY(0.4);
        
        Group sound_effects = new Group(); // Sound effects
        sound_effects.getChildren().addAll(sound_effect_imgview, on_imgview_2, off_imgview_2);
        sound_effects.setScaleX(0.4);
        sound_effects.setScaleY(0.4);
        
        
        Image closeImage = new Image("file:src/main/resources/images/離開.png");
        ImageView closeImageView = new ImageView(closeImage);
        closeImageView.setFitWidth(40);
        closeImageView.setFitHeight(80);
        closeImageView.setOnMouseClicked((MouseEvent event) -> Leave());
        closeImageView.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");	
        
        on_imgview.setOnMouseClicked((MouseEvent event) -> {
            on_imgview.setOpacity(1);
            off_imgview.setOpacity(0.3);
            mainPage.playBackgroundMusic("src/main/resources/audios/bgm.mp3");
            if(soundEffect)
                playAudio("src/main/resources/audios/打開音效.mp3");

        });

        off_imgview.setOnMouseClicked((MouseEvent event) -> {
            off_imgview.setOpacity(1);
            on_imgview.setOpacity(0.3);
            mainPage.stopBackgroundMusic();
            if(soundEffect)
                playAudio("src/main/resources/audios/關閉音效.mp3");
        });
        
        on_imgview_2.setOnMouseClicked((MouseEvent event) -> {
			on_imgview_2.setOpacity(1);
			off_imgview_2.setOpacity(0.3);
            soundEffect = true;
			playAudio("src/main/resources/audios/打開音效.mp3");
        });

        off_imgview_2.setOnMouseClicked((MouseEvent event) -> {
			off_imgview_2.setOpacity(1);
			on_imgview_2.setOpacity(0.3);
            soundEffect = false;
			playAudio("src/main/resources/audios/關閉音效.mp3");
        });

        this.getChildren().addAll(bgm , sound_effects, closeImageView);
        this.setVisible(false); // Initially hide the settings pane
    }

    private void Leave(){
        if(soundEffect)
            playAudio("src/main/resources/audios/關閉音效.mp3");
        this.setVisible(false);
    }

    public void toggleVisibility() {
        isVisible = !isVisible;
        this.setVisible(isVisible);
    }
    
    private void playAudio(String audioFilePath) {
        Media sound = new Media(new File(audioFilePath).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    public boolean getSoundeffect() {
        return soundEffect;
    }
}