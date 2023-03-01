package nx.engine;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import nx.engine.scenes.FinalScene;
import nx.engine.scenes.Scene;
import nx.engine.scenes.TextScene;
import nx.engine.scenes.WorldScene;
import nx.engine.world.World;
import nx.engine.world.WorldData;
import nx.engine.world.entities.Player;
import nx.game.App;
import nx.game.GameController;
import nx.util.StopWatch;

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
	public static Font fontBIG = Font.loadFont(TextScene.class.getResourceAsStream("/assets/fonts/PressStart2P-Regular.ttf"), 20);

	public StopWatch stopWatch = new StopWatch();
	
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
	
	public static Game get(Canvas canvas) {
		return instance == null ? instance = new Game(canvas) : instance;
	}
	public static Game get() {
		if(instance != null)
			return instance;
		return null;
	}
	
	public void init() {
		try {
			mainScene = new TextScene("/assets/levels/intro/introEN.csv");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void start() {
		this.lastTime = System.nanoTime();
		super.start();
	}
	
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
				if(mainScene instanceof WorldScene) {
					Player.get().setPosition(World.spawn);
				}
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
			Game.logger.log(Level.CONFIG,"FPS: " + drawCount);
			LastFrameRate = drawCount;
			drawCount = 0;
			timer = 0;
		}
		
		lastTime = currentNanoTime;
	}
	public void update() {

		if(mainScene instanceof TextScene) {
			if(((TextScene) mainScene).hasEnded() || inputHandler.getActiveKeys().contains(KeyCode.ESCAPE)) {
				App.mixer.getMusic().fadeOut(20);
				mainScene = new FinalScene("You Win.Now place your name bellow");
//				changeScene(new WorldScene(WorldData.START_LEVEL));
			}
		}
		else if(mainScene instanceof WorldScene) {
			if(inputHandler.getActiveKeys().contains(KeyCode.ESCAPE)) {
				inputHandler.ClearActiveKeys();
				GameController.getInstance().onOpenSettings();
			}
		}
		
		if(!GameController.getInstance().onSettings)
			mainScene.update(deltaTime);
	}
	
	public void draw(GraphicsContext gc) {
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, screenWidth, screenheigth);
		
		mainScene.draw(gc);
	}

	public static void changeScene(Scene scene) {
		sceneToChangeTo = scene;
		alpha = 0.1f;
		transitionDirection = 1;
		transitioning = true;
	}
	
	public static Scene getMainScene() {
		return Game.mainScene;
	}

	public StopWatch getStopWatch() {
		return stopWatch;
	}

	public void setStopWatch(StopWatch stopWatch) {
		this.stopWatch = stopWatch;
	}
	
	

}
