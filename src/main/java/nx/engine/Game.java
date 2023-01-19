package nx.engine;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import nx.engine.scenes.Scene;
import nx.engine.scenes.WorldScene;
import nx.engine.tile.TileSet;

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
	
	double drawInterval = 1000000000d / fps;
	private long lastTime;
	double delta = 0;
	private double deltaTime = 0;
	private int drawCount = 0;
	private long timer = 0;
	
	private final GraphicsContext graphicsContext;

	public static InputHandler input = new InputHandler();

	public static int SCREEN_CENTER_X = Game.screenWidth / 2 - (Game.tileSize/2);
	public static int SCREEN_CENTER_Y = Game.screenheigth / 2 - (Game.tileSize/2);

	private Scene scene;
	
	public Game(Canvas canvas) {
		this.graphicsContext = canvas.getGraphicsContext2D();
		
		canvas.setWidth(screenWidth);
		canvas.setHeight(screenheigth);
		
		canvas.setOnKeyPressed(input);
		canvas.setOnKeyReleased(input);
		canvas.setFocusTraversable(true);
		canvas.requestFocus();

		init();
	}
	
	public void init() {
		TileSet.loadTiles("/assets/textures/levels/DungeonTiles.png");

		scene = new WorldScene();
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
			System.out.println("FPS: " + drawCount);
			LastFrameRate = drawCount;
			drawCount = 0;
			timer = 0;
		}
		
		lastTime = currentNanoTime;
	}

	public void update() {
		scene.update(deltaTime);
	}
	
	public void draw(GraphicsContext gc) {
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, screenWidth, screenheigth);

		scene.draw(gc);
	}



}
