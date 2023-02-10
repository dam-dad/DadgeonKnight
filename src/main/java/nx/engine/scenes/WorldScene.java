package nx.engine.scenes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import nx.engine.Camera;
import nx.engine.Game;
import nx.engine.UI.Dialog;
import nx.engine.UI.UserInterfaceImage;
import nx.engine.particles.ParticleManager;
import nx.engine.tile.TileSet;
import nx.engine.world.World;
import nx.engine.world.entities.Bow;
import nx.engine.world.entities.OldMan;
import nx.engine.world.entities.PickableEntity;
import nx.engine.world.entities.Player;
import nx.engine.world.entities.Sword;
import nx.engine.world.entities.Wizard;

public class WorldScene implements Scene {

	private final World world;
	//Entities
	private final Player player;
	
	private final ParticleManager particleManager;
	private final Camera camera;
	private Font font = new Font(50);
	
	private RadialGradient radialGradient = new RadialGradient(0,0,.5,.5,0.15, true, CycleMethod.NO_CYCLE,
	        new Stop(0, Color.TRANSPARENT),
	        new Stop(1, Color.rgb(10, 10, 10,0.6))
	        );
	
	private String startedMap_entities = "/assets/levels/startedMap/StartedLevel_Entities.csv";
	private String[] startedMap = {"/assets/levels/startedMap/StartMap_map.csv",
			"/assets/levels/startedMap/StartMap_detail.csv",
			"/assets/levels/startedMap/StartMap_collitions.csv"};
	
	private String secretMap_entities = "";
	private String[] secretMap = {"/assets/levels/secret/Secret_Floor.csv",
								"/assets/levels/secret/Secret_Collisions.csv"};
	
	private String dungeonMap_entities = "/assets/levels/level1/DungeonLevel_Entities.csv";
	private String[] dungeonMap = {"/assets/levels/level1/DungeonLevel_Mapa.csv",
								"/assets/levels/level1/DungeonLevel_Collitions.csv"};
	
	public static Dialog dialog;
	
	public WorldScene() {
		String entities = "/assets/levels/entitties.csv";

		this.world = new World(TileSet.WORLD_DARK_TILES,startedMap_entities, startedMap);
		this.camera = new Camera();

		// TODO: Custom particle image
		this.particleManager = new ParticleManager(world, "/assets/textures/bola_du_fogo.gif");

		this.player = new Player(26, 23, 4, camera);
		
		world.addEntity(player);
		
		world.addEntity(new OldMan(26, 27));
	}

	@Override
	public void update(double delta) {
		world.update(delta);
		particleManager.update(delta);
		
		if(dialog != null)
			dialog.update(delta);
	}

	@Override
	public void draw(GraphicsContext gc) {
		world.draw(gc, camera);
		
		gc.setFill(radialGradient);
		gc.fillRect(Game.screenWidth/2 - 500, Game.screenheigth/2 - 500, 1000, 1000);

		gc.setFont(font);
		gc.setFill(Color.WHITESMOKE);
		gc.fillText(String.format("Vida: %d", player.getHealth()), 10, Game.screenheigth - 10);
		
		PickableEntity e = (PickableEntity) player.getItemSelected();
		
		if(!player.getInventory().isEmpty())
			e.drawUI(gc);
		
		if(dialog != null)
			dialog.draw(gc);
	}

	public World getWorld() {
		return world;
	}

	public Player getPlayer() {
		return player;
	}

}
