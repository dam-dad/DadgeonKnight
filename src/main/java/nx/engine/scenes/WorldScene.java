package nx.engine.scenes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import nx.engine.Camera;
import nx.engine.Game;
import nx.engine.particles.ParticleManager;
import nx.engine.tile.TileSet;
import nx.engine.world.World;
import nx.engine.world.entities.PickableEntity;
import nx.engine.world.entities.Player;
import nx.engine.world.entities.Sword;

public class WorldScene implements Scene {

	private final World world;

	//Entities
	private final Player player;
	private final Sword sword;

	
	private final ParticleManager particleManager;
	private final Camera camera;
	
	private RadialGradient radialGradient = new RadialGradient(0,0,.5,.5,0.15, true, CycleMethod.NO_CYCLE,
	        new Stop(0, Color.TRANSPARENT),
	        new Stop(1, Color.rgb(10, 10, 10,0.6))
	        );

	private String dungeonMap_entities = "/assets/levels/level1/DungeonLevel_Entities.csv";
	private String[] dungeonMap = {"/assets/levels/level1/DungeonLevel_Mapa.csv",
								"/assets/levels/level1/DungeonLevel_Collitions.csv"};
	private String[] worldMap = {"/assets/levels/startedMap/StartMap_map.csv",
								"/assets/levels/startedMap/StartMap_detail.csv",
								"/assets/levels/startedMap/StartMap_collitions.csv"};
	public WorldScene() {
		this.world = new World(TileSet.WORLD_DARK_TILES,worldMap);
		this.camera = new Camera();

		// TODO: Custom particle image
		this.particleManager = new ParticleManager(world, "/assets/textures/bola_du_fogo.gif");

		this.player = new Player(26, 23, 4, camera);
		this.sword = new Sword(TileSet.ITEMS_TILES,18, 36, Game.tileSize, Game.tileSize);

		
		world.addEntity(player);
		world.addEntity(sword);
	}

	@Override
	public void update(double delta) {
		world.update(delta);
		particleManager.update(delta);
	}



	@Override
	public void draw(GraphicsContext gc) {
		world.draw(gc, camera);
		

		gc.setFill(radialGradient);
		gc.fillRect(Game.screenWidth/2 - 500, Game.screenheigth/2 - 500, 1000, 1000);
		
		//UI
			//life
		gc.setFont(Game.font);
		gc.setFill(Color.WHITESMOKE);
		gc.fillText(String.format("Vida: %d", player.getHealth()), 10, Game.screenheigth - 10);
		
			//inventory
		PickableEntity e = (PickableEntity) player.getItemSelected();
		e.drawUI(gc);
		

	}

	public World getWorld() {
		return world;
	}

	public Player getPlayer() {
		return player;
	}

}
