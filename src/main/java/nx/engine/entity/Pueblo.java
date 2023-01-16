package nx.engine.entity;

import java.util.Set;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import nx.engine.Game;
import nx.engine.tile.TileSet;

public class Pueblo extends Entity<Rectangle> {
	
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
	public void update(Set<KeyCode> activeKeys, double deltaTime) {}

	@Override
	public void draw(GraphicsContext gc) {
		gc.drawImage(image, posX, posY);
	}

	@Override
	public Rectangle getCollisionShape() {
		return new Rectangle(posX,posY,Game.tileSize,Game.tileSize);
	}

}
