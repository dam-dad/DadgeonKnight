package nx.engine.world.entities;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import nx.engine.Camera;
import nx.engine.Game;
import nx.engine.UI.Dialog;
import nx.engine.UI.UserInterfaceImage;
import nx.engine.scenes.WorldScene;
import nx.engine.tile.TileSetManager;
import nx.engine.world.MobEntity;
import nx.util.Direction;

/**
 * Represents a villager entity
 */
public class Villager extends MobEntity {
	
	String walkTileSet = "/assets/textures/npc/Green-Cap-Character-16x18.png";
	
	public boolean isFinish = false;

	/**
	 * Constructor
	 * @param posX Spawn position X
	 * @param posY Spawn position Y
	 */
	public Villager(double posX, double posY) {
		super(posX * Game.tileSize, posY * Game.tileSize);
		
		this.image = TileSetManager.cropImage(new Image(walkTileSet), 0, 16, 16);
		
		this.scale = 1;
		
		this.sizeTextureX = 16;
		this.sizeTextureY = 24;
		
		this.hasDamage = false;
		this.canDie = false;
		this.direction = Direction.SOUTH;
		
		this.sizePlayerDetection = 120;
	}
	
	@Override
	public void update(double deltaTime) {
		if(getPosX() + Game.tileSize > Player.get().getCamera().getX() - Game.screenWidth &&
				getPosX() - Game.tileSize < Player.get().getCamera().getX() + Game.screenWidth &&
				getPosY() + Game.tileSize > Player.get().getCamera().getY() - Game.screenheigth &&
				getPosY() - Game.tileSize  < Player.get().getCamera().getY() + Game.screenheigth){
			if(isFinish)
				return;
			double distance = getDistanceToEntity(Player.get());
			if(distance < sizePlayerDetection && WorldScene.dialog == null) {
				WorldScene.dialog = new Dialog("Steve","/assets/levels/startedMap/villager.csv",UserInterfaceImage.Dialog);
				WorldScene.dialog.play();
			}
			else if(distance >= sizePlayerDetection) {
				WorldScene.dialog = null;
				
			}
		}

	}

}
