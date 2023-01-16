package nx.engine;


import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import nx.engine.entity.Player;
import nx.engine.entity.Pueblo;
import nx.engine.tile.Tile;
import nx.engine.tile.TileManager;
import nx.util.Music;

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
	
	private GraphicsContext graphicsContext;
	
	private InputHandler input = new InputHandler();
	
	public Player player;
	
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
		player = new Player(10 * tileSize, 10 * tileSize,4,input);
		
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
			checkCollisions();
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
				if(a[i][j].isCollider() && a[i][j].checkCollision(player)) {
					input.ClearActiveKeys();
					player.pushOut(a[i][j],0.001);
				}
			}
		}

	}
	public void update() {
		player.update(input.getActiveKeys(),deltaTime);
	}
	
	public void draw(GraphicsContext gc) {
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, screenWidth, screenheigth);
		
		tm.draw(gc);
		
		player.draw(gc);
	}



}
