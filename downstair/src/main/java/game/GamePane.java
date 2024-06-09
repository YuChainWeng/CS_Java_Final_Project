package game;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import java.util.*;
public class GamePane extends Pane {

    private Canvas canvas;
    private GraphicsContext gc;
    private long lastUpdateTime = 0;
    Character character;
    private double charaX = 300;
    private double charaY = 400;
    private Rectangle[] lifeBars = new Rectangle[10];
    private int level = 1;
    private Label levelLabel = new Label(Integer.toString(level));
    private Stage stage = new Stage();
    private ArrayList<ImageView> stairs = new ArrayList<>();

    public GamePane(Character character) {
        Image bgImage = new Image("file:src/main/resources/images/遊戲背景.jpeg");
        Image lifeImage = new Image("file:src/main/resources/images/Life.png");
        Image levelImage = new Image("file:src/main/resources/images/Level.png");
        ImageView lifeImageView = new ImageView(lifeImage);
        ImageView levelImageView = new ImageView(levelImage);
        
        lifeImageView.setPreserveRatio(true); // 設定為保持比例
        lifeImageView.setFitWidth(80);
        lifeImageView.setX(100);
        lifeImageView.setY(10);
        
        levelImageView.setPreserveRatio(true); // 設定為保持比例
        levelImageView.setFitWidth(80);
        levelImageView.setX(400);
        levelImageView.setY(15);        
        
        levelLabel.setStyle("-fx-font-weight: bold;");
        levelLabel.setFont(new Font("Arial Black", 30));
        levelLabel.setTextFill(Color.WHITE);
        levelLabel.setLayoutX(500);
        levelLabel.setLayoutY(11);
        this.getChildren().addAll(lifeImageView, levelImageView, levelLabel);
        for (int i = 0; i < lifeBars.length; i++) {
            lifeBars[i] = new Rectangle(205 + i * 12, 20, 8, 25); // 設定每個長方形的位置和大小
            lifeBars[i].setFill(Color.RED); // 設定生命長方形的顏色
            this.getChildren().add(lifeBars[i]); // 添加到Pane中
        }
        BackgroundImage backgroundImage = new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT, 
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, 
                new BackgroundSize(100, 100, true, true, false, true));
        this.setBackground(new Background(backgroundImage));
        this.character = character;
        initializeGame();
        setupGameLoop();
    }

    private void initializeGame() {
        canvas = new Canvas(650, 800); // Set canvas size
        this.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();
        this.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> this.requestFocus());
        setupControls();
        generateInitialStairs();
    }

    private void setupGameLoop() {
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastUpdateTime > 0) {
                    double deltaTime = (now - lastUpdateTime) / 1e9;
                    update(deltaTime);
                }
                draw();
                lastUpdateTime = now;
            }
        };
        gameLoop.start();
    }

    private void setupControls() {
        this.setOnKeyPressed(event -> {
            System.out.println("Key pressed: " + event.getCode()); // Debug print statement
            switch (event.getCode()) {
                case RIGHT:
                	if (charaX <= 560) {
                		charaX += 10;
                		character.setDirection("right");
                		System.out.println(character.getDirection());
                    	System.out.println("Moving right to: " + charaX); // Debug print statement
                	}
                    break;
                case LEFT:
                    if (charaX >= 35) {
                    	charaX -= 10;
                    	character.setDirection("left");
                    	System.out.println(character.getDirection());
                    	System.out.println("Moving left to: " + charaX); // Debug print statement
                    }
                    break;
            }
        });
        this.setOnKeyReleased(event -> { // 放開按鍵回復正面
            System.out.println("Key released: " + event.getCode()); // Debug print statement
            switch (event.getCode()) {
                case RIGHT:
                case LEFT:
                    character.setDirection("front");
                    break;
            }
        });
        this.setFocusTraversable(true);
        this.requestFocus();
    }

    private void update(double deltaTime) {
        // Update game logic here, e.g., character movement, check collisions
        //charaX += 100 * deltaTime;
    	updateLifeBar(character.getLife());
    }

    private void draw() {
        // Clear the screen
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        Image cha = character.getCharacterImageWithDirection(character.getCharacterName(), character.getDirection());
        // Draw game elements like characters, enemies, and background
        gc.drawImage(cha, charaX, charaY, 45, 90);
    }
    
    private void updateLifeBar(int remainingLife) { // 更新生命條
        for (int i = 0; i < lifeBars.length; i++) {
            if (i < remainingLife) {
                lifeBars[i].setVisible(true); // 將前remainingLife個長方形設置為可見
            } else {
                lifeBars[i].setVisible(false); // 其餘的設置為不可見
            }
        }
    }
    
    private void generateInitialStairs() { //範圍 x : 35 ~ 530, y : 5 ~ 755
    	Random random = new Random();

        // 在角色腳下生成一個正常階梯
        NormalStair normalStair = new NormalStair();
        ImageView normalStairView = new ImageView(normalStair.getNormalStair());
        normalStairView.setPreserveRatio(true);
        normalStairView.setFitWidth(80);
        normalStairView.setX(charaX - 13);
        normalStairView.setY(charaY + 90); // 階梯在角色腳下
        stairs.add(normalStairView);
        this.getChildren().add(normalStairView);

        // 生成其他階梯在角色以下的範圍
        int maxAttempts = 100; // 最多尝试次数，避免无限循环
        for (int i = 1; i < 10; i++) { // 假設生成10個階梯
            ImageView stair = null;
            boolean overlap;
            int attempts = 0;
            do {
                overlap = false;
                stair = stage.generateRandomStair();
                stair.setPreserveRatio(true);
                stair.setFitWidth(80);
                stair.setX(35 + random.nextInt(495)); //生成範圍在35到530之間
                stair.setY(charaY + 100 + random.nextInt((int) (canvas.getHeight() - charaY - 100))); // 階梯生成在角色以下的範圍
                
                // 檢查是否與已有階梯重疊
                for (ImageView existingStair : stairs) {
                    if (stair.getBoundsInParent().intersects(existingStair.getBoundsInParent())) {
                        overlap = true;
                        break;
                    }
                }

                attempts++;
            } while (overlap && attempts < maxAttempts);

            if (!overlap) {
                stairs.add(stair);
                this.getChildren().add(stair); // 將階梯添加到Pane中
            }
        }
    }
}