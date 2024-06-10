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
    private Rectangle[] lifeBars = new Rectangle[10];
    private int level = 1;
    private Label levelLabel = new Label(Integer.toString(level));
    private ArrayList<Stage> stairs = new ArrayList<>();
    private double gravity = 300; // Gravity force
    private boolean rightcollition = false;
    private boolean leftcollition = false;
    private double stairSpeed = -50;

    public GamePane(Character character) {
        Image bgImage = new Image("file:src/main/resources/images/遊戲背景.jpeg");
        Image lifeImage = new Image("file:src/main/resources/images/Life.png");
        Image levelImage = new Image("file:src/main/resources/images/Level.png");
        ImageView lifeImageView = new ImageView(lifeImage);
        ImageView levelImageView = new ImageView(levelImage);
        canvas = new Canvas(650, 900); // Set canvas size
        this.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();

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
        this.character = new Character(character.getCharacterName(), 300, 100, 45, 90,20);
        setupControls();
        generateInitialStairs();
        setupGameLoop();
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
                    if (character.getPositionX() <= 530 && !rightcollition) {
                        character.setPositionX(character.getPositionX() + character.getVelocityX());
                        character.setDirection("right");
                        System.out.println(character.getDirection());
                        System.out.println("Moving right to: " + character.getPositionX()); // Debug print statement
                    }
                    break;
                case LEFT:
                    if (character.getPositionX() >= 35 && !leftcollition) {
                        character.setPositionX(character.getPositionX() - character.getVelocityX());
                        character.setDirection("left");
                        System.out.println(character.getDirection());
                        System.out.println("Moving left to: " + character.getPositionX()); // Debug print statement
                    }
                    break;
            }
            System.out.println("Checking right collition: " + rightcollition);
            System.out.println("Checking left collition: " + leftcollition);
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
        character.setVelocityY(character.getVelocityY() + gravity * deltaTime); // Apply gravity to character
        character.setPositionY(character.getPositionY() + character.getVelocityY() * deltaTime);

        boolean onStair = false;
        for (Stage stair : stairs) {
            stair.setPositionY(stair.getPositionY() + stair.getSpeed() * deltaTime); // Move stairs at fixed speed
            if (checkOnStair(character, stair)) {
                onStair = true;
                character.setPositionY(stair.getPositionY() - character.getHeight()); // Adjust character's position to stand on stair
                character.setVelocityY(0); // Stop falling when on stair
                if(stair instanceof SlowStair){
                    character.setVelocityY(character.getVelocityY() + ((SlowStair) stair).getSlowingspeed());
                }else if(stair instanceof DamageStair){
                    character.setLife(character.getLife() - ((DamageStair) stair).getDamage());
                    character.setVelocityX(20);
                }else character.setVelocityX(20);
            }else checkEdgeCollision(character, stair);
        }

        // Remove stairs that move off screen and generate new ones
        manageStairs();

        if (!onStair) {
            character.setVelocityY(character.getVelocityY() + gravity * deltaTime); // Apply additional gravity if not on stair
        }

        updateLifeBar(character.getLife());
    }

    private void manageStairs() {
        stairs.removeIf(stair -> stair.getPositionY() + stair.getHeight() < 0); // Remove stairs that are no longer visible
        while (stairs.size() < 20 && 900-stairs.get(stairs.size()-1).getPositionY()>80) { // Ensure there are always 20 stairs
            level++;
            double x = new Random().nextDouble() * 495 + 35;
            double y = 900; // Start new stairs at the bottom of the screen
            GenerateRandomStair(x, y);
        }
    }

    private boolean checkOnStair(Character character, Stage stair) {
        return character.getPositionY() + 90 >= stair.getPositionY() && character.getPositionY() + 90 <= stair.getPositionY() + 20 &&character.getPositionX() + 45 >= stair.getPositionX() && character.getPositionX() <= stair.getPositionX() + 80;
    }

    private void checkEdgeCollision(Character character, Stage stair) {
        // Check if the character is touching the left or right edge of the stair
        /*if (character.getPositionX() + character.getWidth()/2 > stair.getPositionX()-stair.getWidth()/2&&character.getPositionY()-character.getHeight()/2 < stair.getPositionY()-stair.getHeight() && character.getPositionY() < stair.getPositionY() + stair.getHeight()){
            rightcollition = true;
        } else if (character.getPositionX()-character.getFitWidth()/2 < stair.getPositionX() + stair.getWidth()/2 && character.getPositionY()-character.getHeight() < stair.getPositionY()-stair.getHeight() && character.getPositionY() < stair.getPositionY() + stair.getHeight()){
            leftcollition = true;
        } else {
            rightcollition = false;
            leftcollition = false;
        }*/
    }

    private void draw() {
        // Clear the screen
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        Image cha = character.getCharacterImageWithDirection(character.getCharacterName(), character.getDirection());
        // Draw game elements like characters, enemies, and background
        gc.drawImage(cha, character.getPositionX(), character.getPositionY(), 45, 90);
        for (Stage stair : stairs) {
            gc.drawImage(stair.getStairImage(), stair.getPositionX(), stair.getPositionY(), stair.getWidth(), stair.getHeight());
            //System.out.println("Drawing stair at: " + stair.getPositionX() + ", " + stair.getPositionY());
        }
    }
    
    private void updateLifeBar(int remainingLife) { // 更新生命條
        for (int i = 0; i < lifeBars.length; i++) {
            if (i < remainingLife) {
                lifeBars[i].setVisible(true); // 將前remainingLife個長方形設置為可見
            } else {
                lifeBars[i].setVisible(false); // 其餘的設置為不可見
            }
        }
        levelLabel.setText(Integer.toString(level));
    }
    
    private void generateInitialStairs() { //範圍 x : 35 ~ 530, y : 5 ~ 755
        Random random = new Random();
        // 在角色腳下生成一個正常階梯
        stairs.add(new NormalStair(-0.1, character.getPositionX()-13, character.getPositionY()+90, 80, 20));
        // 生成其他階梯在角色以下的範圍
        for (int i = 1; i < 20; i++) { // 假設生成10個階梯
            double x = random.nextDouble() * 495 + 35; // 生成35~530之間的隨機x座標
            double y = i*800/20; // 生成5~755之間的隨機y座標
            GenerateRandomStair(x, y);
        }
    }

    private void GenerateRandomStair(double x, double y){
        Random random = new Random();
        int stairType = random.nextInt(3); // 隨機生成階梯類型
        System.out.println("Stair type: " + stairType);
        switch (stairType) {
            case 0:
                stairs.add(new NormalStair(-50, x, y, 80, 20));
                break;
            case 1:
                stairs.add(new SlowStair(-15,-50, x, y, 80, 20));
                break;
            case 2:
                stairs.add(new DamageStair(1,-50.1, x, y, 80, 40));
                break;
            default:
                break;
        }
    }
}
