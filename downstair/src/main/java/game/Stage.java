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
    public Image getStairImage() {
        return stariImage;
    }
    public ImageView getStairImageView() {
        return stairImageView;
    }
    public void setStairImage(Image stairImage) {
        this.stariImage = stairImage;
        this.stairImageView = new ImageView(stairImage);
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
    public double getSpeed() {
        return speed;
    }
    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
