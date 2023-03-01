package nx.engine.world.entities;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import nx.engine.Camera;
import nx.engine.Game;

/**
 * Represents an entity that does not move
 */
public class StaticEntity extends Entity {

	/**
	 * Constructor
	 * @param image Entity texture
	 * @param x Spawn position X
	 * @param y Spawn position Y
	 * @param width Entity width
	 * @param height Entity height
	 */
	public StaticEntity(Image image, double x, double y,double width,double height) {
		super(image,x * Game.tileSize,y * Game.tileSize,width,height);
	}

	/**
	 * Updates the entity
	 * @param deltaTime Frame delta
	 */
	@Override
	public void update(double deltaTime) {}

	/**
	 * Draws the entity
	 * @param gc GraphicsContext to draw on
	 * @param camera World camera
	 */
	@Override
	public void draw(GraphicsContext gc, Camera camera) {
		if(getPosX() + Game.tileSize > camera.getX() - Game.screenWidth &&
				getPosX() - Game.tileSize < camera.getX() + Game.screenWidth &&
				getPosY() + Game.tileSize > camera.getY() - Game.screenheigth &&
				getPosY() - Game.tileSize  < camera.getY() + Game.screenheigth) {
			drawInternal(gc, camera,width,height);
		}
	}
}
