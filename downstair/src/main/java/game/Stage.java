package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Stage{

	private double positionX;
	private double positionY;
	private Image stariImage;
	private ImageView stairImageView;
	private double width;
	private double height;
	private double speed;
	// Constructor
	public Image getStairImage() {
		return stariImage;
	}
	// Get the image of the stair
	public ImageView getStairImageView() {
		return stairImageView;
	}
	// Get the image view of the stair
	public void setStairImage(Image stairImage) {
		this.stariImage = stairImage;
		this.stairImageView = new ImageView(stairImage);
	}
	// Get the x position of the stair
	public double getPositionX() {
		return positionX;
	}
	// Set the x position of the stair
	public void setPositionX(double positionX) {
		this.positionX = positionX;
	}
	// Get the y position of the stair
	public double getPositionY() {
		return positionY;
	}
	// Set the y position of the stair
	public void setPositionY(double positionY) {
		this.positionY = positionY;
	}
	// Get the width of the stair
	public double getWidth() {
		return width;
	}
	// Set the width of the stair
	public void setWidth(double width) {
		this.width = width;
	}
	// Get the height of the stair
	public double getHeight() {
		return height;
	} 
	// Set the height of the stair
	public void setHeight(double height) {
		this.height = height;
	}
	// Get the speed of the stair
	public double getSpeed() {
		return speed;
	}
	// Set the speed of the stair
	public void setSpeed(double speed) {
		this.speed = speed;
	}
}
