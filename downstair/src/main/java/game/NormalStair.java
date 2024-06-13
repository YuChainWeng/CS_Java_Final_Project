package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class NormalStair extends Stage {
	// The constructor of the normal stair
	public NormalStair(double speed, double positionX, double positionY, double width, double height) {
		super.setStairImage(new Image("file:src/main/resources/images/雲.png"));
		super.setSpeed(speed);
		super.setPositionX(positionX);
		super.setPositionY(positionY);
		super.setWidth(width);
		super.setHeight(height);
	}
	// Get the image of the normal stair
	public Image getImage() {
		return super.getStairImage();
	}
	// Get the image view of the normal stair
	public ImageView getImageView() {
		return super.getStairImageView();
	}
	// Get the speed of the normal stair
	public double getSpeed() {
		return super.getSpeed();
	}
	// Set the speed of the normal stair
	public void setSpeed(double speed) {
		super.setSpeed(speed);
	}
	// Get the x position of the normal stair
	public double getPositionX() {
		return super.getPositionX();
	}
	// Set the x position of the normal stair
	public void setPositionX(double positionX) {
		super.setPositionX(positionX);
	}
	// Get the y position of the normal stair
	public double getPositionY() {
		return super.getPositionY();
	}
	// Set the y position of the normal stair
	public void setPositionY(double positionY) {
		super.setPositionY(positionY);
	}

}
