package nx.engine;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import nx.engine.entity.Player;
import nx.engine.entity.Wizard;
import nx.engine.level.Level;
import nx.engine.particles.ParticleSource;
import nx.engine.tile.Tile;
import nx.engine.tile.TileManager;

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
	
	double drawInterval = 1000000000/fps;
	private long lastTime;
	double delta = 0;
	private double deltaTime = 0;
	private int drawCount = 0;
	private long timer = 0;
	
	public static GraphicsContext graphicsContext;
	
	public static InputHandler input = new InputHandler();

	public static int SCREEN_CENTER_X = Game.screenWidth / 2 - (Game.tileSize/2);
	public static int SCREEN_CENTER_Y = Game.screenheigth / 2 - (Game.tileSize/2);

	public static Player player;
	private Wizard wizard;
	private Level level;
	private ParticleSource particleSource;

	public static Camera camera;
	
	private TileManager tm = new TileManager(this);
	
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
		camera = new Camera();
		player = new Player(10 * tileSize, 10 * tileSize,4, camera);
		wizard = new Wizard();
		level = new Level("/assets/levels/dungeon/DungeonLevel_Mapa.csv", "/assets/levels/dungeon/DungeonLevel_Mapa.csv");
		particleSource = new ParticleSource(8 * 48, 6 * 48, "/assets/textures/player/kevin_idle_00.png");
		
//		Music music = new Music("Tour du Jugement_The Legend of Zelda Twilight Princess HD_OST");
//		music.play();
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
		
		if(delta >= 1) {
//			checkCollisions();
			update();
			draw(graphicsContext);
			
			delta--;
			drawCount++;
		}

		
		if(timer >= 1000000000) {
			System.out.println("FPS: " + drawCount);
			LastFrameRate = drawCount;
			drawCount = 0;
			timer = 0;
		}
		
		lastTime = currentNanoTime;
	}

	private void checkCollisions() {
		
		Tile[][] a = tm.getMapTiles();
		
		for(int i = 0; i < a.length; i++) {
			for(int j = 0; j < a[0].length; j++) {
				if(a[i][j].isSolid() && new Rectangle(i * Game.tileSize, j * Game.tileSize, Game.tileSize, Game.tileSize).intersects(player.getCollisionShape().getLayoutBounds())) {
					input.ClearActiveKeys();
					System.out.println("collision");
//					player.pushOut(a[i][j],0.01);
				}
			}
		}

	}
	public void update() {
		player.update(deltaTime);
		wizard.update(deltaTime);
		particleSource.update(deltaTime);
	}
	
	public void draw(GraphicsContext gc) {
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, screenWidth, screenheigth);

		level.draw(gc, camera);

		player.draw(gc);
		wizard.draw(gc);
		particleSource.draw(gc);
	}



}
