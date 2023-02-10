package nx.engine.world.entities;

import java.util.Optional;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import nx.engine.Game;
import nx.engine.UI.Dialog;
import nx.engine.UI.UserInterfaceImage;
import nx.engine.scenes.WorldScene;
import nx.engine.tile.TileSet;
import nx.engine.world.MobEntity;
import nx.util.Direction;

public class OldMan extends MobEntity {
	
	public boolean isFinish = false;

	public OldMan(double posX, double posY) {
		super(posX * Game.tileSize, posY * Game.tileSize);
		
		this.hasDamage = false;
		this.canDie = false;
		this.direction = Direction.EAST;
		
		this.image = new Image("/assets/textures/player/kevin_idle_00.png");
		
		this.sizePlayerDetection = 60;

	}
	
	@Override
	public void update(double deltaTime) {
		Optional<Player> player = getWorld()
				.getEntities()
				.stream()
				.filter(e -> e instanceof Player)
				.map(e -> (Player)e)
				.findAny();
		
		if(!player.isPresent() || isFinish)
			return;
		double distance = getDistanceToEntity(player.get());
		if(distance < sizePlayerDetection && WorldScene.dialog == null) {
			WorldScene.dialog = new Dialog("Prueba","/assets/levels/startedMap/oldManText.csv",UserInterfaceImage.Dialog);
			WorldScene.dialog.play();
			System.out.println(WorldScene.dialog.isOn());
		}
		else if(distance >= sizePlayerDetection) {
			WorldScene.dialog = null;
			
		}
		
		if(WorldScene.dialog != null && WorldScene.dialog.hasFinish()) {
			isFinish = true;
			getWorld().removeEntity(this);
			WorldScene.dialog = null;
			getWorld().addEntity(new Sword(TileSet.ITEMS_TILES, getPosX(), getPosY(), Game.tileSize, Game.tileSize));
		}
	}
	
	public void drawUI(GraphicsContext gc) {
		
	}

}
