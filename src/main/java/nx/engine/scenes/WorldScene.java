package nx.engine.scenes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import nx.engine.Camera;
import nx.engine.Game;
import nx.engine.particles.ParticleManager;
import nx.engine.tile.TileSet;
import nx.engine.tile.TileSetManager;
import nx.engine.world.World;
import nx.engine.world.entities.Armor;
import nx.engine.world.entities.Orc;
import nx.engine.world.entities.PickableEntity;
import nx.engine.world.entities.Pillar;
import nx.engine.world.entities.Player;
import nx.engine.world.entities.Rock;
import nx.engine.world.entities.Sword;
import nx.engine.world.entities.Wizard;
import nx.engine.world.entities.Wolf;

public class WorldScene implements Scene {

	private final World world;

	//Entities
	private final Player player;
	private final Sword sword;
	private final Armor armor;

	
	private final ParticleManager particleManager;
	private final Camera camera;
	private Font font = new Font(50);

	public WorldScene() {
		this.world = new World("/assets/levels/dungeon/DungeonLevel_Mapa.csv",
				"/assets/levels/dungeon/DungeonLevel_Collitions.csv");
		this.camera = new Camera();

		// TODO: Custom particle image
		this.particleManager = new ParticleManager(world, "/assets/textures/bola_du_fogo.gif");

		this.player = new Player(10, 10, 4, camera);
		this.sword = new Sword(TileSet.ITEMS_TILES.getSet(),10, 12, Game.tileSize, Game.tileSize);
		this.armor = new Armor(TileSet.ITEMS_TILES.getSet(), 10, 14, Game.tileSize, Game.tileSize);
		world.addEntity(player);
		world.addEntity(sword);
		world.addEntity(armor);
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
