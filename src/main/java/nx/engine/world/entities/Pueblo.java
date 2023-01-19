package nx.engine.world.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import nx.engine.Camera;
import nx.engine.Game;
import nx.engine.tile.TileSet;

@Deprecated
public class Pueblo extends Entity {
	
	public Pueblo(double posX,double posY) {
		this.posX = posX;
		this.posY = posY;
		this.image =
		TileSet.loadImageFromTileSet(
				new Image("/assets/textures/levels/WorldTiles.png"),
				101,
				Game.tileSize,
				Game.tileSize,
				0);
	}

	@Override
	public void update(double deltaTime) {}

	@Override
	public void draw(GraphicsContext gc, Camera camera) {
		gc.drawImage(image, posX, posY);
	}

}
