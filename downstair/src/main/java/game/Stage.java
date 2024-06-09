package game;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Random;

public class Stage {

    private NormalStair normalStair;
    private SlowStair slowStair;
    private DamageStair damageStair;

    public Stage() {
        normalStair = new NormalStair();
        slowStair = new SlowStair(2);
        damageStair = new DamageStair(1); 
    }

    public ImageView generateRandomStair() {
        Random random = new Random();
        int randomNumber = random.nextInt(3); // 生成0到2之間的隨機數，用於選擇階梯類型

        // 根據隨機數選擇階梯類型並返回相應的圖像視圖
        switch (randomNumber) {
            case 0:
                return new ImageView(normalStair.getNormalStair());
            case 1:
                return new ImageView(slowStair.getSlowStair());
            case 2:
                return new ImageView(damageStair.getDamageStair());
            default:
                return null;
        }
    }
}
