package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SlowStair extends Stage {
	private double slowingspeed = 0.5;

	public SlowStair(double slowingspeed, double speed, double positionX, double positionY, double width, double height) {
		this.slowingspeed = slowingspeed;
		super.setStairImage(new Image("file:src/main/resources/images/手.png"));
		super.setSpeed(speed);
		super.setPositionX(positionX);
		super.setPositionY(positionY);
		super.setWidth(width);
		super.setHeight(height);
	}

	public double getSlowingspeed() {
		return slowingspeed;
	}

	public void setSlowingspeed(double slowingspeed) {
		this.slowingspeed = slowingspeed;
	}

	public Image getImage() {
		return super.getStairImage();
	}

	public ImageView getImageView() {
		return super.getStairImageView();
	}

	public double getSpeed() {
		return super.getSpeed();
	}

	public void setSpeed(double speed) {
		super.setSpeed(speed);
	}

	public double getPositionX() {
		return super.getPositionX();
	}

	public void setPositionX(double positionX) {
		super.setPositionX(positionX);
	}

	public double getPositionY() {
		return super.getPositionY();
	}

	public void setPositionY(double positionY) {
		super.setPositionY(positionY);
	}

	public double getWidth() {
		return super.getWidth();
	}

	public void setWidth(double width) {
		super.setWidth(width);
	}
}
