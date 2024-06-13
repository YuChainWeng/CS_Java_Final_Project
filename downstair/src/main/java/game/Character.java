package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// This class is used to create a character object
public class Character extends ImageView {
	// The name of the character and the basic attributes of the character
	private String CharacterName;
	private int life = 10;
	private String direction = "front";
	private double positionX;
	private double positionY;
	private double width;
	private double height;
	private double velocityY = 0;
	private double velocityX = 0;
	private double gravity = 1;
	private double invincibleTime = 0;
	private double slowingTime=0;
	// Constructor
	public Character(String character, double positionX, double positionY, double width, double height, double velocityX) {
		this.CharacterName = character;
		this.positionX = positionX;
		this.positionY = positionY;
		this.width = width;
		this.height = height;
		this.velocityX = velocityX;
	}
	// Return the image of the character
	public Image getCharacterImage(String ChoosenCharacter) {
		Image charImage;
		if (ChoosenCharacter.equals("Black")) {
			charImage = new Image("file:src/main/resources/images/黑無常正面.png");
		} else {
			charImage = new Image("file:src/main/resources/images/白無常正面.png");
		}
		return charImage;
	}
	// Return the image of the character when the character is dead
	public Image getChatacterDiedImage(String ChoosenCharacter) {
		Image charImage;
		if (ChoosenCharacter.equals("Black")) {
			charImage = new Image("file:src/main/resources/images/黑無常死掉.png");
		} else {
			charImage = new Image("file:src/main/resources/images/白無常死掉.png");
		}
		return charImage;
	}
	// Return the image of the character with the specified width and height
	public ImageView getCharacterImage(String ChoosenCharacter, int width, int height) {
		Image charImage = getCharacterImage(ChoosenCharacter);
		ImageView charImageView = new ImageView(charImage);
		charImageView.setFitWidth(width);
		charImageView.setFitHeight(height);
		return charImageView;
	}
	// Return the image of the character when the character is dead with the specified width and height
	public ImageView getCharacterDiedImage(String ChoosenCharacter, int width, int height) {
		Image charImage = getChatacterDiedImage(ChoosenCharacter);
		ImageView charImageView = new ImageView(charImage);
		charImageView.setFitWidth(width);
		charImageView.setFitHeight(height);
		return charImageView;
	}
	// Return the image
	public Image getThisCharacterImage() {
		return getCharacterImage(CharacterName);
	}
	// Return the image of the character with the specified width and height
	public ImageView getThisCharacterImage(int width, int height) {
		return getCharacterImage(CharacterName, width, height);
	}

	// Return the image with the specified direction
	public Image getCharacterImageWithDirection(String ChoosenCharacter, String direction) {
		Image charImage;
		if (ChoosenCharacter.equals("Black")) {
			if (direction.equals("left"))
				charImage = new Image("file:src/main/resources/images/黑無常左側.png");
			else if (direction.equals("right"))
				charImage = new Image("file:src/main/resources/images/黑無常右側.png");
			else
				charImage = new Image("file:src/main/resources/images/黑無常正面.png");
		} else {
			if (direction.equals("left"))
				charImage = new Image("file:src/main/resources/images/白無常左側.png");
			else if (direction.equals("right"))
				charImage = new Image("file:src/main/resources/images/白無常右側.png");
			else
				charImage = new Image("file:src/main/resources/images/白無常正面.png");
		}
		return charImage;
	}
	// Character regains life based on the character's name
	public void ReganLife() {
		if(life<10)
			life++;
		if(life<10 && !CharacterName.equals("Black"))
			life++;
	}
	// Get the name of the character and return the name of the character
	public String getCharacterName() {
		return CharacterName;
	}
	// Set the name of the character
	public void chooseCharacter(String ChoosenCharacter) {
		this.CharacterName = ChoosenCharacter;
	}

	// Get the life of the character and set the life of the character
	public int getLife() {
		return life;
	}
	// Set the life of the character
	public void setLife(int life) {
		this.life = life;
	}
	// Get the direction of the character and set the direction of the character
	public String getDirection() {
		return direction;
	}
	// Set the direction of the character
	public void setDirection(String direction) {
		this.direction = direction;
	}
	// Get the position of the character and set the position of the character
	public double getPositionX() {
		return positionX;
	}
	// Set the x position of the character
	public void setPositionX(double positionX) {
		this.positionX = positionX;
	}
	// Get the y position of the character
	public double getPositionY() {
		return positionY;
	}
	// Set the y position of the character
	public void setPositionY(double positionY) {
		this.positionY = positionY;
	}
	// Get the velocity of the character and set the velocity of the character
	public double getVelocityY() {
		return velocityY;
	}
	// Set the velocity of the character
	public void setVelocityY(double velocityY) {
		this.velocityY = velocityY;
	}
	// Get the gravity of the character and set the gravity of the character
	public double getGravity() {
		return gravity;
	}
	// Set the gravity of the character
	public void setGravity(double gravity) {
		this.gravity = gravity;
	}
	// Get the width and height of the character and set the width and height of the character
	public double getWidth() {
		return width;
	}
	// Set the width of the character
	public void setWidth(double width) {
		this.width = width;
	}
	// Get the height of the character
	public double getHeight() {
		return height;
	}
	// Set the height of the character
	public void setHeight(double height) {
		this.height = height;
	}
	// Get the velocity of the character
	public double getVelocityX() {
		return velocityX;
	}
	// Set the velocity of the character
	public void setVelocityX(double velocityX) {
		this.velocityX = velocityX;
	}
	// Get the invincible time of the character and set the invincible time of the character
	public double getInvincibleTime() {
		return invincibleTime;
	}
	// Set the invincible time of the character
	public void setInvincibleTime(double invincibleTime) {
		this.invincibleTime = invincibleTime;
	}
	// Get the slowing time of the character and set the slowing time of the character
	public double getSlowingTime(){
		return slowingTime;
	}
	//  Set the slowing time of the character
	public void setSlowingTime(double slowingTime){
		this.slowingTime = slowingTime;
	}
}
