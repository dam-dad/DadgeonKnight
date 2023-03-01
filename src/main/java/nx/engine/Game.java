package nx.engine;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import nx.engine.scenes.Scene;
import nx.engine.scenes.TextScene;
import nx.engine.scenes.WorldScene;
import nx.engine.world.WorldData;
import nx.engine.world.entities.Player;
import nx.game.App;

public class Game extends AnimationTimer {
	
	public static Logger logger = Logger.getLogger(Game.class.getName());
	
	private static Game instance;
	
	// Screen Settings
	final static int originalTileSize = 16; //16 x 16 tile
	final static int scale = 3;
	
	public static final int tileSize = originalTileSize * scale; //48 x 48 tile
	public static final int maxScreenCol = 16;
	public static final int maxScreenRow = 12;
	public static final int screenWidth = tileSize * maxScreenCol; // 768 pixels
	public static final int screenheigth = tileSize * maxScreenRow; // 576 pixels
	
	//FPS
	public static int fps = 60;
	public static int LastFrameRate = 60;
	
	double drawInterval = 1000000000.0 / fps;
	private long lastTime;
	double delta = 0;
	private double deltaTime = 0;
	private int drawCount = 0;
	private long timer = 0;

	private final GraphicsContext graphicsContext;

	public static final InputHandler inputHandler = new InputHandler();
	
	public static Player player = Player.get(new Camera());

	public static int SCREEN_CENTER_X = Game.screenWidth / 2 - (Game.tileSize/2);
	public static int SCREEN_CENTER_Y = Game.screenheigth / 2 - (Game.tileSize/2);

	private static Scene mainScene;
	private static Scene sceneToChangeTo;
	
	public static float alpha = 0;
	public static boolean transitioning;
	private static int transitionDirection = 1;
	
	public static Font font = Font.loadFont(TextScene.class.getResourceAsStream("/assets/fonts/PressStart2P-Regular.ttf"), 10);

	/**
	 * Constructor
	 * @param canvas Canvas to draw the game on
	 */
	private Game(Canvas canvas) {
		
		Game.logger.setLevel(Level.OFF);
		
		this.graphicsContext = canvas.getGraphicsContext2D();
		graphicsContext.setImageSmoothing(false);
		
		canvas.setWidth(screenWidth);
		canvas.setHeight(screenheigth);
		
		canvas.setOnKeyPressed(inputHandler.keyInputHandler);
		canvas.setOnKeyReleased(inputHandler.keyInputHandler);
		canvas.setOnMousePressed(inputHandler.mouseInputHandler);
		canvas.setOnMouseReleased(inputHandler.mouseInputHandler);
		canvas.setOnMouseMoved(inputHandler.mouseInputHandler);
		canvas.setOnScroll(inputHandler.scrollInputHandler);
		canvas.setFocusTraversable(true);
		canvas.requestFocus();

		init();
	}

	/**
	 * Returns the game instance, creating it before if it does not exist
	 * @param canvas Canvas to draw the game on
	 * @return Game instance if it does exist, null if not
	 */
	public static Game get(Canvas canvas) {
		return instance == null ? instance = new Game(canvas) : instance;
	}

	/**
	 * Returns the game instance
	 * @return Game instance if it does exist, null if not
	 */
	public static Game get() {
		return instance;
	}

	/**
	 * Loads the game
	 */
	public void init() {
		try {
			TextScene textScene = new TextScene("/assets/levels/intro/introEN.csv");
			textScene.setOnEndingAction(() -> {
				App.mixer.getMusic().fadeOut(20);
				changeScene(new WorldScene(WorldData.BOSS_ROOM));
			});
			mainScene = textScene;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Starts the game and AnimationTimer component
	 */
	@Override
	public void start() {
		this.lastTime = System.nanoTime();
		super.start();
	}

	/**
	 * Called each frame to draw the game
	 * @param currentNanoTime Current system nano time
	 */
	@Override
	public void handle(long currentNanoTime) {
		deltaTime = (currentNanoTime - lastTime) / 1000000000.0;
		delta += (currentNanoTime - lastTime) / drawInterval;
		timer += (currentNanoTime - lastTime);

		if (transitioning) {
			alpha += deltaTime * transitionDirection * 2;

			if (alpha > 1) {
				mainScene = sceneToChangeTo;
				sceneToChangeTo = null;
				mainScene.update(deltaTime);
				transitionDirection = -1;
				alpha = 1;
			} else if (alpha < 0.1f) {
				transitioning = false;
				alpha = 0.1f;
			}
		}
		
		if (delta >= 1) {
			if (!transitioning)
				update();

			draw(graphicsContext);
			
			delta--;
			drawCount++;
		}

		if (timer >= 1000000000) {
//			System.out.println("FPS: " + drawCount);
			LastFrameRate = drawCount;
			drawCount = 0;
			timer = 0;
		}
		
		lastTime = currentNanoTime;
	}

	/**
	 * Updates the current scene
	 */
	public void update() {
		if(mainScene instanceof TextScene textScene) {
			if(textScene.hasEnded() || inputHandler.getActiveKeys().contains(KeyCode.ESCAPE)) {
				textScene.getOnEndingAction().run();
			}
		}
		
		mainScene.update(deltaTime);
	}

	/**
	 * Clears the screen and draws the current scene
	 * @param gc GraphicsContext to draw the scene on
	 */
	public void draw(GraphicsContext gc) {
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, screenWidth, screenheigth);
		
		mainScene.draw(gc);
	}

	/**
	 * Changes the scene
	 * @param scene Scene to change to
	 */
	public static void changeScene(Scene scene) {
		sceneToChangeTo = scene;
		alpha = 0.1f;
		transitionDirection = 1;
		transitioning = true;
	}

}
