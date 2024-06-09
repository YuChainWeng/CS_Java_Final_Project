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
        canvas = new Canvas(650, 900); // Set canvas size
        this.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();
        this.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> this.requestFocus());
        setupControls();
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
}