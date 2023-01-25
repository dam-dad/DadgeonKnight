package nx.engine;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import nx.engine.scenes.Scene;
import nx.engine.scenes.WorldScene;
import nx.engine.tile.Tile;
import nx.engine.tile.TileSet;
import nx.engine.world.Level;
import nx.engine.world.entities.Orco;
import nx.engine.world.entities.Player;

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

	public static final InputHandler inputHandler = new InputHandler();

	public static int SCREEN_CENTER_X = Game.screenWidth / 2 - (Game.tileSize/2);
	public static int SCREEN_CENTER_Y = Game.screenheigth / 2 - (Game.tileSize/2);

	private Scene scene;
	
	public Game(Canvas canvas) {
		this.graphicsContext = canvas.getGraphicsContext2D();
		
		canvas.setWidth(screenWidth);
		canvas.setHeight(screenheigth);
		
		canvas.setOnKeyPressed(inputHandler);
		canvas.setOnKeyReleased(inputHandler);
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
			checkCollisions();
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

	private void checkCollisions() {
		if (!(scene instanceof WorldScene worldScene))
			return;

		Player player = worldScene.getPlayer();
		Level level = worldScene.getWorld().getLevel();
		int levelWidth = level.getLayers().get(0).getLayerWidth();
		int levelHeight = level.getLayers().get(0).getLayerHeight();

		for (int i = 0; i < levelWidth; i++) {
			for (int j = 0; j < levelHeight; j++) {
				//Collision entity with player
				System.out.println("xxx");
//				if (level.isSolid(i, j) && Tile.checkCollision(player, i, j)) {
//					inputHandler.ClearActiveKeys();
//					player.pushOut(i, j, Player.PLAYER_FORCE);
//				}
				// TODO
				//Collision entity with map
//				if (level.isSolid(i, j) && Tile.checkCollision(worldScene.getWorld().getEntities().get(0), i, j)) {
//					Orco orc = (Orco) worldScene.getWorld().getEntities().get(0);
//					orc.pushOut(i, j, Player.PLAYER_FORCE);
//					orc.changeDirection();
//				}
			}
		}

		// TODO
//		//Collision entities with player
//		if (entities.get(0).checkCollision(player)) {
//			Orco orc = (Orco) entities.get(0);
//			orc.stop();
//
//
//			input.ClearActiveKeys();
//			player.pushOut(entities.get(0), Player.PLAYER_FORCE);
//
////			orc.reset();
//		}
	}

	public void update() {
		// TODO: Adaptar esto (Rama de Alejandro)
//		player.update(input.getActiveKeys(),deltaTime);
//
//
//		entities.forEach(e -> {
//			Orco a = (Orco) e;
//			a.update(null,deltaTime);
//		});

		scene.update(deltaTime);
	}
	
	public void draw(GraphicsContext gc) {
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, screenWidth, screenheigth);

		scene.draw(gc);
	}



}
