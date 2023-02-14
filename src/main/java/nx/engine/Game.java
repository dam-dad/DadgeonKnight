package nx.engine;

import java.nio.file.Paths;
import java.util.Arrays;

import javax.swing.text.html.CSS;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import nx.engine.scenes.Scene;
import nx.engine.scenes.TextScene;
import nx.engine.scenes.WorldScene;
import nx.engine.tile.Tile;
import nx.engine.tile.TileSet;
import nx.engine.tile.TileSetManager;
import nx.engine.world.Level;
import nx.engine.world.WorldData;
import nx.engine.world.entities.Orc;
import nx.engine.world.entities.Player;
import nx.game.App;
import nx.util.CSV;

public class Game extends AnimationTimer {
	
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

	public static int SCREEN_CENTER_X = Game.screenWidth / 2 - (Game.tileSize/2);
	public static int SCREEN_CENTER_Y = Game.screenheigth / 2 - (Game.tileSize/2);

	private Scene scene;
	private static Scene sceneToChangeTo;
	
	public static Player player = Player.get(26, 25, new Camera());
	
	public static Font font = Font.loadFont(TextScene.class.getResourceAsStream("/assets/fonts/PressStart2P-Regular.ttf"), 10);

	
	public Game(Canvas canvas) {
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
	
	public void init() {
		try {
			System.out.println("game");
			scene = new TextScene("/assets/levels/intro/introEN.csv");
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
		
		if (delta >= 1) {
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

	public void update() {
		if (sceneToChangeTo != null) {
			this.scene = sceneToChangeTo;
			sceneToChangeTo = null;
		}

		if(scene instanceof TextScene) {
			if(((TextScene) scene).hasEnded() || inputHandler.getActiveKeys().contains(KeyCode.ESCAPE)) {
				App.mixer.getMusic().fadeOut(20);
				this.scene = new WorldScene(WorldData.START_LEVEL);
			}
		}
		
		scene.update(deltaTime);
	}
	
	public void draw(GraphicsContext gc) {
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, screenWidth, screenheigth);
		
		scene.draw(gc);
	}

	public static void changeScene(Scene scene) {
		sceneToChangeTo = scene;
	}

}
