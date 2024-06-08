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


public class GamePane extends Pane {

    private Canvas canvas;
    private GraphicsContext gc;
    private long lastUpdateTime = 0;
    Character character;
    private double charaX = 0;
    private double charaY = 0;


    public GamePane(Character character) {
        Image bgImage = new Image("file:src/main/resources/images/遊戲背景.jpeg");
        BackgroundImage backgroundImage = new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT, 
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, 
                new BackgroundSize(100, 100, true, true, false, true));
        this.setBackground(new Background(backgroundImage));
        this.character = character;
        initializeGame();
    }

    private void initializeGame() {
        canvas = new Canvas(650, 900); // Set canvas size
        this.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();
        this.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> this.requestFocus());
        setupControls();
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
                    charaX += 10;
                    System.out.println("Moving right to: " + charaX); // Debug print statement
                    break;
                case LEFT:
                    charaX -= 10;
                    System.out.println("Moving left to: " + charaX); // Debug print statement
                    break;
            }
        });
        this.setFocusTraversable(true);
        this.requestFocus();
    }

    private void update(double deltaTime) {
        // Update game logic here, e.g., character movement, check collisions
        //charaX += 100 * deltaTime;
    }

    private void draw() {
        // Clear the screen
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Draw game elements like characters, enemies, and background
        gc.drawImage(character.getThisCharacterImage(), charaX, charaY, 45, 90);
    }
}