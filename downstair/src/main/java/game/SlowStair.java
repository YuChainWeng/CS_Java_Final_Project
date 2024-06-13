package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SlowStair extends Stage {
	// The slowing speed of the slow stair
	private double slowingspeed = 0.5;
	//  The constructor of the slow stair
	public SlowStair(double slowingspeed, double speed, double positionX, double positionY, double width, double height) {
		this.slowingspeed = slowingspeed;
		super.setStairImage(new Image("file:src/main/resources/images/手.png"));
		super.setSpeed(speed);
		super.setPositionX(positionX);
		super.setPositionY(positionY);
		super.setWidth(width);
		super.setHeight(height);
	}
	// Get and return all the attributes of the slow stair
	public double getSlowingspeed() {
		return slowingspeed;
	}
    // Set the slowing speed of the slow stair
	public void setSlowingspeed(double slowingspeed) {
		this.slowingspeed = slowingspeed;
	}
	// Get the image of the slow stair
	public Image getImage() {
		return super.getStairImage();
	}
	// Get the image view of the slow stair
	public ImageView getImageView() {
		return super.getStairImageView();
	}
	// Get the speed of the slow stair
	public double getSpeed() {
		return super.getSpeed();
	}
	// Set the speed of the slow stair
	public void setSpeed(double speed) {
		super.setSpeed(speed);
	}
	// Get the x position of the slow stair
	public double getPositionX() {
		return super.getPositionX();
	}
	// Set the x position of the slow stair
	public void setPositionX(double positionX) {
		super.setPositionX(positionX);
	}
	// Get the y position of the slow stair
	public double getPositionY() {
		return super.getPositionY();
	}
	// Set the y position of the slow stair
	public void setPositionY(double positionY) {
		super.setPositionY(positionY);
	}
	// Get the width of the slow stair
	public double getWidth() {
		return super.getWidth();
	}
	// Set the width of the slow stair
	public void setWidth(double width) {
		super.setWidth(width);
	}
}
