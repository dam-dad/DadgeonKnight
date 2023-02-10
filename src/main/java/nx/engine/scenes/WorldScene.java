package nx.engine.scenes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import nx.engine.Camera;
import nx.engine.Game;
import nx.engine.UI.Dialog;
import nx.engine.particles.ParticleManager;
import nx.engine.world.World;
import nx.engine.world.WorldData;
import nx.engine.world.entities.PickableEntity;
import nx.engine.world.entities.Player;

public class WorldScene implements Scene {

	private final World world;
	
	private final ParticleManager particleManager;
	private final Camera camera;
	private Font font = new Font(50);
	
	public static Player player;
	
	public static Dialog dialog;
	public static Image smoke = new Image("/assets/textures/items/smoke.gif"); 
	
	private RadialGradient radialGradient = new RadialGradient(0,0,.5,.5,0.15, true, CycleMethod.NO_CYCLE,
	        new Stop(0, Color.TRANSPARENT),
	        new Stop(1, Color.rgb(10, 10, 10,0.6))
	        );

	public WorldScene(WorldData worldData) {
		this.camera = new Camera();

		this.world = new World(worldData.getTileSet(), worldData.getEntities(), camera, worldData.getMapLayers());

		// TODO: Custom particle image
		this.particleManager = new ParticleManager(world, "/assets/textures/bola_du_fogo.gif");
		
		
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

}
