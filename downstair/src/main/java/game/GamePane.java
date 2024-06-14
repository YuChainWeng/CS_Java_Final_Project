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
	// The canvas and graphics context used to draw the game
	private Canvas canvas;
	// The graphics context used to draw the game
	private GraphicsContext gc;
	// The game loop
	private AnimationTimer gameLoop;
	// The time of the last update
	private long lastUpdateTime = 0;
	// Start from 3 seconds
	private int countdown = 3;
	private long countdownStartTime;
	private boolean gameStarted = false;
	private Character character;
	// The life bars
	private Rectangle[] lifeBars = new Rectangle[10];
	// The current level
	private int level = 0;
	// The label to display the current level
	private Label levelCountLabel = new Label(Integer.toString(level));
	// The list of stairs
	private ArrayList<Stage> stairs = new ArrayList<>();
	// Gravity force
	private double gravity = 300;
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
		// Set the background image
		Image bgImage = new Image("file:src/main/resources/images/遊戲背景.jpeg");
		// Create a canvas to draw the game
		canvas = new Canvas(650, 900); // Set canvas size
		this.getChildren().add(canvas);
		gc = canvas.getGraphicsContext2D();
		this.gameStartTime = Instant.now().toEpochMilli();
		this.SoundEffect = SoundEffect;
		// Create a label to display the current life
		Label lifeLabel = new Label("Life");
		lifeLabel.setStyle("-fx-font-weight: bold;");
		lifeLabel.setFont(new Font("Arial Black", 30));
		lifeLabel.setTextFill(Color.WHITE);
		lifeLabel.setLayoutX(100);
		lifeLabel.setLayoutY(11);

		// Create a label to display the current level
		Label levelLabel = new Label("Level");
		levelLabel.setStyle("-fx-font-weight: bold;");
		levelLabel.setFont(new Font("Arial Black", 30));
		levelLabel.setTextFill(Color.WHITE);
		levelLabel.setLayoutX(400);
		levelLabel.setLayoutY(11);

		// Set the level count label
		levelCountLabel.setStyle("-fx-font-weight: bold;");
		levelCountLabel.setFont(new Font("Arial Black", 30));
		levelCountLabel.setTextFill(Color.WHITE);
		levelCountLabel.setLayoutX(500);
		levelCountLabel.setLayoutY(11);
		// Add the labels to the pane
		this.getChildren().addAll(lifeLabel, levelLabel, levelCountLabel);

		// Create ten life bars
		for (int i = 0; i < lifeBars.length; i++) {
			lifeBars[i] = new Rectangle(205 + i * 12, 20, 8, 25); // 設定每個長方形的位置和大小
			lifeBars[i].setFill(Color.RED); // 設定生命長方形的顏色
			this.getChildren().add(lifeBars[i]); // 添加到Pane中
		}
		// Set the background image
		BackgroundImage backgroundImage = new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
				new BackgroundSize(100, 100, true, true, false, true));
		this.setBackground(new Background(backgroundImage));
		// Create the character
		this.character = new Character(character.getCharacterName(), 300, 100, characterWidth, characterHeight,
				characterVelocityX);
		setupControls();
		generateInitialStairs();
		draw();
		startCountdown();
	}

	// Set the game end listener
	public void setGameEndListener(Runnable callback) {
		this.gameEndCallback = callback;
	}

	// Start the countdown
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

	// Start the game loop
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

	// Stop the game loop
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

	private void setupControls() { // set up keyboard controls
		this.setOnKeyPressed(event -> {
			switch (event.getCode()) {
				case RIGHT: // Move right
					if (character.getPositionX() <= 541 && !rightcollision && gameStarted) { // Check if the character
																								// is at the right edge
																								// of the screen
						character.setPositionX(character.getPositionX() + character.getVelocityX()); // Move the
																										// character to
																										// the right
						character.setDirection("right"); // Set the character's direction to right
					}
					break;
				case LEFT: // Move left
					if (character.getPositionX() >= 40 && !leftcollision && gameStarted) { // Check if the character is
																							// at the left edge of the
																							// screen
						character.setPositionX(character.getPositionX() - character.getVelocityX()); // Move the
																										// character to
																										// the left
						character.setDirection("left"); // Set the character's direction to left
					}
					break;
			}
		});
		this.setOnKeyReleased(event -> { // When a key is released
			switch (event.getCode()) { // Check which key was released
				case RIGHT:
				case LEFT:
					character.setDirection("front"); // Set the character's direction to front
					break;
			}
		});
		this.setFocusTraversable(true);
		this.requestFocus();
	}

	private void update(double deltaTime) { // Update game state
		updateCharacter(deltaTime);
		updateStairs(deltaTime);
		manageStairs();
		updateLifeBar(character.getLife());
		checkGameOverConditions();
		reganlife(deltaTime);
	}

	private void reganlife(double deltaTime) { // Regain life every 20 seconds
		if (ReganLifeTimeCountdown <= 0) {
			character.ReganLife();
			ReganLifeTimeCountdown = 20;
		} else {
			ReganLifeTimeCountdown -= deltaTime;
		}
	}

	private void checkGameOverConditions() { // Check if the game is over
		if (character.getLife() <= 0 || character.getPositionY() < -200 || character.getPositionY() > 1000) { // Check
																												// if
																												// the
																												// character's
																												// life
																												// is
																												// less
																												// than
																												// or
																												// equal
																												// to 0
			if (gameEndCallback != null) // Check if a game end callback is set
				if (SoundEffect)
					playAudio("src/main/resources/audios/lose.wav"); // Play a sound effect
			gameEndCallback.run(); // Run the game end callback
		}
	}

	private void updateCharacter(double deltaTime) { // Update character state
		character.setVelocityY(character.getVelocityY() + gravity * deltaTime); // Apply gravity to character
		character.setPositionY(character.getPositionY() + character.getVelocityY() * deltaTime);
	}

	private void manageStairs() { // Manage stairs
		stairs.removeIf(stair -> stair.getPositionY() + stair.getHeight() < -100); // Remove stairs that are no longer
		// visible
		while (stairs.size() < 20 && (stairs.isEmpty() || 900 - stairs.get(stairs.size() - 1).getPositionY() > 135)) {
			double x = new Random().nextDouble() * 495 + 35;
			double y = 900; // Start new stairs at the bottom of the screen
			GenerateRandomStair(x, y);
		}

	}

	private void updateStairs(double deltaTime) { // Update stairs
		double timeFactor = Math.max(0, (System.currentTimeMillis() - gameStartTime) / 10000.0); // increases every
		double currentSpeed = stairSpeed - timeFactor * timeFactor; // Slowly increase the speed of the stairs
		if (currentSpeed <= -200)
			currentSpeed = -200;
		boolean onStair = false;
		for (Stage stair : stairs) {
			stair.setPositionY(stair.getPositionY() + currentSpeed * deltaTime); // Move stairs down the screen
			if (checkOnStair(character, stair)) {
				onStair = true;
				character.setPositionY(stair.getPositionY() - character.getHeight()); // Adjust character's position //
																						// stand on stair
				character.setVelocityY(0); // Stop falling when on stair
				if (stair instanceof SlowStair && character.getVelocityX() > 5) { // Slow down the character if on a
																					// slow stair
					character.setVelocityX(characterVelocityX * ((SlowStair) stair).getSlowingspeed()); // Slow down the
																										// character
				} else if (stair instanceof DamageStair) { // Check if the character is on a damage stair
					if (character.getInvincibleTime() <= 0) { // Check if the character is invincible
						if (character.getCharacterName() == "Black") // Black character takes half damage
							character.setLife(character.getLife() - ((DamageStair) stair).getDamage() / 2); // Deal
																											// damage to
																											// the
																											// character
						else
							character.setLife(character.getLife() - ((DamageStair) stair).getDamage()); // Deal damage
																										// to the
																										// character
						character.setInvincibleTime(1); // Make the character invincible for 1 second
						if (SoundEffect) { // if sound effect is enabled , play sound effect
							playAudio("src/main/resources/audios/被火燒到.mp3");
						}
					}
				} else {
					if (!lastUpdateOnStair && SoundEffect) { // Play sound effect when the character lands on a stair
																// and sound effect is enabled
						playAudio("src/main/resources/audios/掉到雲上面.mp3");
					}
				}
			}
			checkEdgeCollision(character, stair); // Check if the character is touching the edge of the stair
		}
		// Update character's position and velocity if not on a stair
		if (!onStair) {
			character.setVelocityY(character.getVelocityY() + gravity * deltaTime);
			character.setVelocityX(characterVelocityX);
		}else if (onStair && !lastUpdateOnStair){
			level++;
		}
		// Update character's invincible time
		if (character.getInvincibleTime() > 0) {
			character.setInvincibleTime(character.getInvincibleTime() - deltaTime);
		}
		// Update character's slowing time
		if (character.getSlowingTime() > 0) {
			character.setSlowingTime(character.getSlowingTime() - deltaTime);
		}
		// Update the last update on stair
		lastUpdateOnStair = onStair;
	}

	// Check if the character is on a stair
	private boolean checkOnStair(Character character, Stage stair) {
		return character.getPositionY() + character.getHeight() >= stair.getPositionY()
				&& character.getPositionY() + character.getHeight() <= stair.getPositionY() + stair.getHeight()
				&& character.getPositionX() + character.getWidth() >= stair.getPositionX()
				&& character.getPositionX() <= stair.getPositionX() + stair.getWidth();
	}

	// Check if the character is touching the edge of the stair
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

	// Draw the game
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

	private void updateLifeBar(int remainingLife) { // Update the life bar
		for (int i = 0; i < lifeBars.length; i++) {
			if (i < remainingLife) {
				lifeBars[i].setVisible(true); // Set the life bar to visible
			} else {
				lifeBars[i].setVisible(false); // Set the life bar to invisible
			}
		}
		levelCountLabel.setText(Integer.toString(level));
	}

	private void generateInitialStairs() { // Generate initial stairs
		Random random = new Random();
		// Generate the first stair
		stairs.add(new NormalStair(stairSpeed, character.getPositionX() - 30, character.getPositionY() + 135,
				stairWidth, stairHeight));
		// Generate 10 more stairs
		for (int i = 1; i < 10; i++) {
			double x = random.nextDouble() * 495 + 35;
			double y = i * 800 / 6 + 270;
			GenerateRandomStair(x, y);
		}
		draw();
	}

	// Generate random stairs
	private void GenerateRandomStair(double x, double y) {
		Random random = new Random();
		int stairType = random.nextInt(3); // Randomly generate a stair type
		switch (stairType) {
			case 0:
				stairs.add(new NormalStair(stairSpeed, x, y, stairWidth, stairHeight));
				break;
			case 1:
				stairs.add(new SlowStair(0.25, stairSpeed, x, y, stairWidth, stairHeight));
				break;
			case 2:
				stairs.add(new DamageStair(2, stairSpeed, x, y, stairWidth, stairHeight * 2));
				break;
			default:
				break;
		}
	}

	// Get the current level
	public int getLevel() {
		return level;
	}

	// Play audio file
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
