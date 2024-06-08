package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;

public class Character {

    private String CharacterName;
    private Character character;

    public Character(String character) {
        this.CharacterName = character;
    }

    public Image getCharacterImage(String ChoosenCharacter) {
        Image charImage;
        if (ChoosenCharacter.equals("Black")) {
            charImage = new Image("file:src/main/resources/images/黑無常正面.png");
        } else {
            charImage = new Image("file:src/main/resources/images/白無常正面.png");
        }
        return charImage;
    }

    public ImageView getCharacterImage(String ChoosenCharacter, int width, int height) {
        Image charImage = getCharacterImage(ChoosenCharacter);
        ImageView charImageView = new ImageView(charImage);
        charImageView.setFitWidth(width);
        charImageView.setFitHeight(height);
        return charImageView;
    }

    public Image getThisCharacterImage() {
        return getCharacterImage(CharacterName);
    }

    public ImageView getThisCharacterImage(int width, int height) {
        return getCharacterImage(CharacterName, width, height);
    }


    public void chooseCharacter(String ChoosenCharacter) {
        this.CharacterName = ChoosenCharacter;
    }

}
