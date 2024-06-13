package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// Inherit the Stage class to create a damage stair object
public class DamageStair extends Stage {
	// The damage of the damage stair
	private int damage = 1;

	// Constructor
	public DamageStair(int damage, double speed, double positionX, double positionY, double width, double height) {
		this.damage = damage;
		super.setStairImage(new Image("file:src/main/resources/images/油鍋.png"));
		super.setSpeed(speed);
		super.setPositionX(positionX);
		super.setPositionY(positionY);
		super.setWidth(width);
		super.setHeight(height);
	}

	// Get and return all the attributes of the damage stair
	public int getDamage() {
		return damage;
	}
	// Set the damage of the damage stair
	public void setDamage(int damage) {
		this.damage = damage;
	}
	// Get the image of the damage stair
	public Image getImage() {
		return super.getStairImage();
	}
	// Get the image view of the damage stair
	public ImageView getImageView() {
		return super.getStairImageView();
	}
	// Get the speed of the damage stair
	public double getSpeed() {
		return super.getSpeed();
	}
	// Set the speed of the damage stair
	public void setSpeed(double speed) {
		super.setSpeed(speed);
	}
	// Get the x position of the damage stair
	public double getPositionX() {
		return super.getPositionX();
	}
	// Set the x position of the damage stair
	public void setPositionX(double positionX) {
		super.setPositionX(positionX);
	}
	// Get the y position of the damage stair
	public double getPositionY() {
		return super.getPositionY();
	}
	// Set the y position of the damage stair
	public void setPositionY(double positionY) {
		super.setPositionY(positionY);
	}
}
