package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DamageStair extends Stage {
	private int damage = 1;

	public DamageStair(int damage, double speed, double positionX, double positionY, double width, double height) {
		this.damage = damage;
		super.setStairImage(new Image("file:src/main/resources/images/油鍋.png"));
		super.setSpeed(speed);
		super.setPositionX(positionX);
		super.setPositionY(positionY);
		super.setWidth(width);
		super.setHeight(height);
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
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
