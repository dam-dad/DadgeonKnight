package nx.engine.scenes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
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
	private Font font = new Font(50);

	public WorldScene() {
		String entities = "/assets/levels/level1/DungeonLevel_Entities.csv";
		String[] mapfiles = {"/assets/levels/level1/DungeonLevel_Mapa.csv",
							"/assets/levels/level1/DungeonLevel_Collitions.csv"};
		this.world = new World(entities,mapfiles);
		this.camera = new Camera();

		// TODO: Custom particle image
		this.particleManager = new ParticleManager(world, "/assets/textures/bola_du_fogo.gif");

		this.player = new Player(10, 10, 4, camera);
		this.sword = new Sword(TileSet.ITEMS_TILES,10, 12, Game.tileSize, Game.tileSize);

		
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

		gc.setFont(font);
		gc.setFill(Color.WHITESMOKE);
		gc.fillText(String.format("Vida: %d", player.getHealth()), 10, Game.screenheigth - 10);
		
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
