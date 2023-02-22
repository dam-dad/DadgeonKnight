package nx.engine.scenes;

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

public class WorldScene implements Scene {

	private World world;
	
	private final ParticleManager particleManager;
	
	private final Camera camera;
	
	public static Dialog dialog;
	public static Image smoke = new Image("/assets/textures/items/smoke.gif"); 
	public static Image exclamation = new Image("/assets/textures/items/exclamation.png");
	
	private RadialGradient radialGradient;
	
	private HealthUI healthUI;

	public WorldScene(WorldData worldData) {
		this.camera = Player.get().getCamera();
		this.world = new World(worldData.getTileSet(), worldData.getEntities(), worldData.getMapLayers());
		
		World.spawn = worldData.getSpawn() != null ? worldData.getSpawn() : new Vector2D(0,0);
		Player.get().setPosition(World.spawn);

		// TODO: Custom particle image
		this.particleManager = new ParticleManager(world, "/assets/textures/bola_du_fogo.gif");
		this.healthUI = new HealthUI(Player.get());
		
		
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
		
		radialGradient = new RadialGradient(0,0,.5,.5, 1 - Game.alpha, true, CycleMethod.NO_CYCLE,
				new Stop(0, Color.TRANSPARENT),
				new Stop(0.8, Color.rgb(10, 10, 10,0.99))
		);

		gc.setFill(radialGradient);
		gc.fillRect(Game.screenWidth/2 - 500, Game.screenheigth/2 - 500, 1000, 1000);

		PickableEntity e = (PickableEntity) Player.get().getItemSelected();
		if(!Player.get().getInventory().isEmpty())
			e.drawUI(gc);
		
		if(dialog != null)
			dialog.draw(gc);
		
		healthUI.draw(gc);
	}

	public World getWorld() {
		return world;
	}
	public void setWordl(WorldData worldData) {
		this.world = new World(worldData.getTileSet(), worldData.getEntities(), worldData.getMapLayers());
	}

}
