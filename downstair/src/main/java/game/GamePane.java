package game;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
import java.io.File;
import java.time.Instant;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class GamePane extends Pane {

    private Canvas canvas;
    private GraphicsContext gc;
    private AnimationTimer gameLoop;
    private long lastUpdateTime = 0;
    private int countdown = 3; // Start from 3 seconds
    private long countdownStartTime;
    private boolean gameStarted = false;
    private  Character character;
    private Rectangle[] lifeBars = new Rectangle[10];
    private int level = 1;
    private Label levelCountLabel = new Label(Integer.toString(level));
    private ArrayList<Stage> stairs = new ArrayList<>();
    private double gravity = 300; // Gravity force
    private boolean rightcollision = false;
    private boolean leftcollision = false;
    private double stairSpeed = -50;
    private long gameStartTime;
    private Runnable gameEndCallback;
    private MediaPlayer mediaPlayer;
    private boolean lastUpdateOnStair = false;
    private boolean SoundEffect = false;
    private double stairWidth = 120;
    private double stairHeight = 30;
    private double characterWidth = 50;
    private double characterHeight = 100;
    private double characterVelocityX = 20;
    private double ReganLifeTimeCountdown = 20;

    public GamePane(Character character, boolean SoundEffect) {
        Image bgImage = new Image("file:src/main/resources/images/遊戲背景.jpeg");
        Label lifeLabel = new Label("Life");
        Label levelLabel = new Label("Level");
        canvas = new Canvas(650, 900); // Set canvas size
        this.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();
        this.gameStartTime = Instant.now().toEpochMilli();
        this.SoundEffect = SoundEffect;

        lifeLabel.setStyle("-fx-font-weight: bold;");
        lifeLabel.setFont(new Font("Arial Black", 30));
        lifeLabel.setTextFill(Color.WHITE);
        lifeLabel.setLayoutX(100);
        lifeLabel.setLayoutY(11);

        levelLabel.setStyle("-fx-font-weight: bold;");
        levelLabel.setFont(new Font("Arial Black", 30));
        levelLabel.setTextFill(Color.WHITE);
        levelLabel.setLayoutX(400);
        levelLabel.setLayoutY(11);

        levelCountLabel.setStyle("-fx-font-weight: bold;");
        levelCountLabel.setFont(new Font("Arial Black", 30));
        levelCountLabel.setTextFill(Color.WHITE);
        levelCountLabel.setLayoutX(500);
        levelCountLabel.setLayoutY(11);
        this.getChildren().addAll(lifeLabel, levelLabel, levelCountLabel);
        for (int i = 0; i < lifeBars.length; i++) {
            lifeBars[i] = new Rectangle(205 + i * 12, 20, 8, 25); // 設定每個長方形的位置和大小
            lifeBars[i].setFill(Color.RED); // 設定生命長方形的顏色
            this.getChildren().add(lifeBars[i]); // 添加到Pane中
        }
        BackgroundImage backgroundImage = new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, false, true));
        this.setBackground(new Background(backgroundImage));
        this.character = new Character(character.getCharacterName(), 300, 100, characterWidth, characterHeight, characterVelocityX);
        setupControls();
        generateInitialStairs();
        draw();
        startCountdown();
    }

    public void setGameEndListener(Runnable callback) {
        this.gameEndCallback = callback;
    }

    private void startCountdown() {
        countdownStartTime = System.currentTimeMillis();
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                long elapsedSeconds = (System.currentTimeMillis() - countdownStartTime) / 1000;
                if (elapsedSeconds >= 1) {
                    countdown--;
                    countdownStartTime = System.currentTimeMillis(); // Reset start time each second
                    if (countdown <= 0) {
                        this.stop(); // Stop the countdown timer
                        startGame(); // Start the main game loop
                    }
                }
                // drawCountdown(); // Draw the countdown on the screen
                draw();
            }
        }.start();
    }

    private void startGame() {
        gameStarted = true;
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastUpdateTime > 0) {
                    double deltaTime = (now - lastUpdateTime) / 1e9;
                    update(deltaTime);
                }
                draw(); // Your regular game drawing logic
                lastUpdateTime = now;
            }
        };
        gameLoop.start();
    }

    public void stopGameLoop() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }

    public void cleanupResources() {
        this.setOnKeyPressed(null);
        this.setOnKeyReleased(null);
        // Clean up other resources or listeners if necessary
    }

    private void setupControls() {
        this.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case RIGHT:
                    if (character.getPositionX() <= 530 && !rightcollision) {
                        character.setPositionX(character.getPositionX() + character.getVelocityX());
                        character.setDirection("right");
                    }
                    break;
                case LEFT:
                    if (character.getPositionX() >= 35 && !leftcollision) {
                        character.setPositionX(character.getPositionX() - character.getVelocityX());
                        character.setDirection("left");
                    }
                    break;
            }
        });
        this.setOnKeyReleased(event -> { 
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
        updateCharacter(deltaTime);
        updateStairs(deltaTime);
        manageStairs();
        updateLifeBar(character.getLife());
        checkGameOverConditions();
        reganlife(deltaTime);
    }

    private void reganlife(double deltaTime) {
        if (ReganLifeTimeCountdown <= 0) {
            character.ReganLife();
            ReganLifeTimeCountdown = 20;
        } else {
            ReganLifeTimeCountdown -= deltaTime;
        }
    }

    private void checkGameOverConditions() {
        if (character.getLife() <= 0 || character.getPositionY() < -200 || character.getPositionY() > 1000) {
            if (gameEndCallback != null)
                playAudio("src/main/resources/audios/lose.wav");
            gameEndCallback.run();
        }
    }

    private void updateCharacter(double deltaTime) {
        character.setVelocityY(character.getVelocityY() + gravity * deltaTime); // Apply gravity to character
        character.setPositionY(character.getPositionY() + character.getVelocityY() * deltaTime);
    }

    private void manageStairs() {
        stairs.removeIf(stair -> stair.getPositionY() + stair.getHeight() < -100); // Remove stairs that are no longer
                                                                                   // visible
        while (stairs.size() < 20 && (stairs.isEmpty() || 900 - stairs.get(stairs.size() - 1).getPositionY() > 135)) {
            level++;
            double x = new Random().nextDouble() * 495 + 35;
            double y = 900; // Start new stairs at the bottom of the screen
            GenerateRandomStair(x, y);
        }

    }

    private void updateStairs(double deltaTime) {
        double timeFactor = Math.max(0, (System.currentTimeMillis() - gameStartTime) / 10000.0); // increases every
        double currentSpeed = stairSpeed - timeFactor * timeFactor;
        if (currentSpeed <= -200)
            currentSpeed = -200;
        boolean onStair = false;
        for (Stage stair : stairs) {
            stair.setPositionY(stair.getPositionY() + currentSpeed * deltaTime); // Move stairs down the screen
            if (checkOnStair(character, stair)) {
                onStair = true;
                character.setPositionY(stair.getPositionY() - character.getHeight()); // Adjust character's position to                                                     // stand on stair
                character.setVelocityY(0); // Stop falling when on stair
                if (stair instanceof SlowStair && character.getVelocityX() > 5) {
                    character.setVelocityX(characterVelocityX * ((SlowStair) stair).getSlowingspeed());
                } else if (stair instanceof DamageStair) {
                    if (character.getInvincibleTime() <= 0) {
                        if(character.getCharacterName() == "Black")
                            character.setLife(character.getLife() - ((DamageStair) stair).getDamage()/2);
                        else
                            character.setLife(character.getLife() - ((DamageStair) stair).getDamage());
                        character.setInvincibleTime(1);
                        if(SoundEffect){
                            playAudio("src/main/resources/audios/被火燒到.mp3");
                        }
                    }
                } else {
                    if(!lastUpdateOnStair && SoundEffect){
                        playAudio("src/main/resources/audios/掉到雲上面.mp3");
                    }
                }
            }
            checkEdgeCollision(character, stair);
        }

        if (!onStair) {
            character.setVelocityY(character.getVelocityY() + gravity * deltaTime);    
            character.setVelocityX(characterVelocityX);                                                         
        }

        if (character.getInvincibleTime() > 0) {
            character.setInvincibleTime(character.getInvincibleTime() - deltaTime);
        }

        if (character.getSlowingTime() > 0) {
            character.setSlowingTime(character.getSlowingTime() - deltaTime);
        }

        lastUpdateOnStair = onStair;

    }

    private boolean checkOnStair(Character character, Stage stair) {
        return character.getPositionY() + character.getHeight() >= stair.getPositionY()
                && character.getPositionY() + character.getHeight() <= stair.getPositionY() + stair.getHeight()
                && character.getPositionX() + character.getWidth() >= stair.getPositionX()
                && character.getPositionX() <= stair.getPositionX() + stair.getWidth();
    }

    private void checkEdgeCollision(Character character, Stage stair) {
        // Check if the character is touching the left or right edge of the stair
        double charLeft = character.getPositionX();
        double charRight = charLeft + character.getWidth();
        double charTop = character.getPositionY();
        double charBottom = charTop + character.getHeight();
        double stairLeft = stair.getPositionX();
        double stairRight = stairLeft + stair.getWidth();
        double stairTop = stair.getPositionY();
        double stairBottom = stairTop + stair.getHeight();

        if (charBottom > stairTop && charTop < stairBottom) {
            if (charRight > stairLeft && charLeft < stairLeft) {
                rightcollision = true;
            } else if (charLeft < stairRight && charRight > stairRight) {
                leftcollision = true;
            }
        } else {
            rightcollision = false;
            leftcollision = false;
        }
    }

    private void draw() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // Clear the canvas

        // Draw stairs
        for (Stage stair : stairs) {
            gc.drawImage(stair.getStairImage(), stair.getPositionX(), stair.getPositionY(), stair.getWidth(),
                    stair.getHeight());
        }
        Image cha = character.getCharacterImageWithDirection(character.getCharacterName(), character.getDirection());
        gc.drawImage(cha, character.getPositionX(), character.getPositionY(), character.getWidth(),
                character.getHeight());

        // Draw other UI elements like countdown or life bars
        if (!gameStarted && countdown > 0) {
            gc.setFont(Font.font("Arial", 60));
            gc.setFill(Color.WHITE);
            gc.fillText(String.valueOf(countdown), canvas.getWidth() / 2 - 15, canvas.getHeight() / 2);
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
        levelCountLabel.setText(Integer.toString(level));
    }

    private void generateInitialStairs() { // 範圍 x : 35 ~ 530, y : 5 ~ 755
        Random random = new Random();
        // 在角色腳下生成一個正常階梯
        stairs.add(new NormalStair(stairSpeed, character.getPositionX()-30, character.getPositionY()+135, stairWidth, stairHeight));
        // 生成其他階梯在角色以下的範圍
        for (int i = 1; i < 10; i++) { // 假設生成10個階梯
            double x = random.nextDouble() * 495 + 35; // 生成35~530之間的隨機x座標
            double y = i * 800 / 6 + 270; // 生成5~755之間的隨機y座標
            GenerateRandomStair(x, y);
        }
        draw();
    }

    private void GenerateRandomStair(double x, double y) {
        Random random = new Random();
        int stairType = random.nextInt(3); // 隨機生成階梯類型
        switch (stairType) {
            case 0:
                stairs.add(new NormalStair(stairSpeed, x, y, stairWidth, stairHeight));
                break;
            case 1:
                stairs.add(new SlowStair(0.25, stairSpeed, x, y, stairWidth, stairHeight));
                break;
            case 2:
                stairs.add(new DamageStair(2, stairSpeed, x, y, stairWidth, stairHeight*2));
                break;
            default:
                break;
        }
    }

    public int getLevel() {
        return level;
    }

    private void playAudio(String audioFilePath) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        Media sound = new Media(new File(audioFilePath).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.stop());
        mediaPlayer.play();
    }
}
