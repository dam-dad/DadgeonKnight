package nx.engine.scenes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import nx.engine.Camera;
import nx.engine.Game;
import nx.engine.particles.ParticleManager;
import nx.engine.tile.TileSet;
import nx.engine.world.World;
import nx.engine.world.entities.*;

public class WorldScene implements Scene {

	private final World world;
	//Entities
	private final Player player;
	private final Sword sword;
	private final Bow bow;
	
	private final ParticleManager particleManager;
	private final Camera camera;
	private Font font = new Font(50);

	private final Image uiImage;

	public WorldScene() {
		this.uiImage = new Image("/assets/textures/items/itemSlot.png");

		String entities = "/assets/levels/entitties.csv";
		String[] mapfiles = {"/assets/levels/secret/Secret_Floor.csv",
							"/assets/levels/secret/Secret_Collisions.csv"};
		this.world = new World(TileSet.SECRET_TILES, mapfiles);
		this.camera = new Camera();

		// TODO: Custom particle image
		this.particleManager = new ParticleManager(world, "/assets/textures/bola_du_fogo.gif");

		this.player = new Player(58, 110, 4, camera);
		this.sword = new Sword(TileSet.ITEMS_TILES,13, 14, Game.tileSize, Game.tileSize);
		this.bow = new Bow(TileSet.ITEMS_TILES, 5, 6, Game.tileSize, Game.tileSize);
		
		world.addEntity(player);
		world.addEntity(sword);
		world.addEntity(bow);
		world.addEntity(new Wizard(14, 16));
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

		gc.drawImage(uiImage, 0, 0, 90, 90);
		e.drawUI(gc);
	}

	public World getWorld() {
		return world;
	}

	public Player getPlayer() {
		return player;
	}

}
