package nx.engine.scenes;

import nx.engine.world.entities.TestBoss;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import nx.engine.Camera;
import nx.engine.Game;
import nx.engine.UI.Dialog;
import nx.engine.UI.HealthUI;
import nx.engine.particles.ParticleManager;
import nx.engine.world.World;
import nx.engine.world.WorldData;
import nx.engine.world.entities.PickableEntity;
import nx.engine.world.entities.Player;

import java.util.Optional;

/**
 * Defines a scene with a world (player, entities, map...)
 */
public class WorldScene implements Scene {

	private final World world;
	
	private final ParticleManager particleManager;
	
	private final Camera camera;

	public static Dialog dialog;
	public static Image smoke = new Image("/assets/textures/items/smoke.gif");

	public static float light = 0.8f;

	private RadialGradient radialGradient;
	
	private final HealthUI healthUI;

	/**
	 * Constructor
	 * @param worldData World data to load the world from
	 */
	public WorldScene(WorldData worldData) {
		this.camera = Player.get().getCamera();
		this.world = new World(worldData.getTileSet(), worldData.getEntities(), worldData.getMapLayers());
		
		World.spawn = worldData.getSpawn() != null ? worldData.getSpawn() : new Vector2D(0,0);
		Player.get().setPosition(World.spawn);
		Player.get().setSpawn(World.spawn);

		// TODO: Custom particle image
		this.particleManager = new ParticleManager(world, "/assets/textures/bola_du_fogo.gif");
		this.healthUI = new HealthUI(Player.get());
	}

	/**
	 * Updates the world and the particle manager (and dialogs is there is any)
	 * @param delta
	 */
	@Override
	public void update(double delta) {
		world.update(delta);
		particleManager.update(delta);
		
		if(dialog != null)
			dialog.update(delta);
	}

	/**
	 * Draws the world with a light effect and the UI
	 * @param gc GraphicsContext to draw on
	 */
	@Override
	public void draw(GraphicsContext gc) {
		world.draw(gc, camera);
		
		radialGradient = new RadialGradient(0,0,.5,.5, 1 - Game.alpha, true, CycleMethod.NO_CYCLE,
				new Stop(0, Color.TRANSPARENT),
				new Stop(light, Color.rgb(10, 10, 10,0.99))
		);

		gc.setFill(radialGradient);

		gc.fillRect(Game.screenWidth/2 - 500, Game.screenheigth/2 - 500, 1000, 1000);

		
		if(!Player.get().getInventory().isEmpty())
			Player.get().getInventory().draw(gc);
		
		if(dialog != null)
			dialog.draw(gc);
		
		healthUI.draw(gc);

		Optional<TestBoss> bossOptional = world.getEntities().stream()
				.filter(entity -> entity instanceof TestBoss)
				.map(TestBoss.class::cast)
				.findFirst();

		if (bossOptional.isPresent()) {
			TestBoss boss = bossOptional.get();

			gc.setFill(Color.RED);
			gc.fillRect(16, Game.screenheigth - 24, (Game.screenWidth - 32) * (boss.getMobHealth() / 150f), 16);
		}
	}

	public World getWorld() {
		return world;
	}

}
