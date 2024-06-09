package game;

import javafx.scene.image.Image;

public class SlowStair {
	private int slow = 1;
	
	public SlowStair(int slow) {
		this.slow = slow;
	}
		
	
	public Image getSlowStair() {
		Image slowStair = new Image("file:src/main/resources/images/手.png");
		return slowStair;
	}
}
