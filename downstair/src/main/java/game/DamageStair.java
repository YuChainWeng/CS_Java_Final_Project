package game;

import javafx.scene.image.Image;

public class DamageStair {
	private int damage = 1;
	
	public DamageStair(int damage) {
		this.damage = damage;
	}

	public int getDamage() {
		return damage;
	}
	
	public Image getDamageStair() {
		Image damageStair = new Image("file:src/main/resources/images/油鍋.png");
		return damageStair;
	}
}
