package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class NormalStair extends Stage {

	public NormalStair(double speed, double positionX, double positionY, double width, double height) {
		super.setStairImage(new Image("file:src/main/resources/images/雲.png"));
		super.setSpeed(speed);
		super.setPositionX(positionX);
		super.setPositionY(positionY);
		super.setWidth(width);
		super.setHeight(height);
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

}
