package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Character extends ImageView {

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

	public Character(String character, double positionX, double positionY, double width, double height, double velocityX) {
		this.CharacterName = character;
		this.positionX = positionX;
		this.positionY = positionY;
		this.width = width;
		this.height = height;
		this.velocityX = velocityX;
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

	public String getCharacterName() {
		return CharacterName;
	}

	public void chooseCharacter(String ChoosenCharacter) {
		this.CharacterName = ChoosenCharacter;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public double getPositionX() {
		return positionX;
	}

	public void setPositionX(double positionX) {
		this.positionX = positionX;
	}

	public double getPositionY() {
		return positionY;
	}

	public void setPositionY(double positionY) {
		this.positionY = positionY;
	}

	public double getVelocityY() {
		return velocityY;
	}

	public void setVelocityY(double velocityY) {
		this.velocityY = velocityY;
	}

	public double getGravity() {
		return gravity;
	}

	public void setGravity(double gravity) {
		this.gravity = gravity;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getVelocityX() {
		return velocityX;
	}

	public void setVelocityX(double velocityX) {
		this.velocityX = velocityX;
	}

	public double getInvincibleTime() {
		return invincibleTime;
	}

	public void setInvincibleTime(double invincibleTime) {
		this.invincibleTime = invincibleTime;
	}

	public double getSlowingTime(){
		return slowingTime;
	}

	public void setSlowingTime(double slowingTime){
		this.slowingTime = slowingTime;
	}
}
