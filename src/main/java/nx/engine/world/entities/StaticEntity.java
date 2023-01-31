package nx.engine.world.entities;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import nx.engine.Camera;
import nx.engine.Game;


public class StaticEntity extends Entity {
	
	public StaticEntity(Image image, double x, double y,double width,double height) {
		super(image,x * Game.tileSize,y * Game.tileSize,width,height);
	}

	@Override
	public void update(double deltaTime) {}
	
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
