package nx.engine.tile;


import java.util.Set;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import nx.engine.Game;
import nx.engine.entity.Entity;
import nx.engine.entity.MobEntity;

public class Tile extends Entity<Rectangle> {
	
	private boolean hasCollition = false;
	
	public Tile() {
	}
	
	public Tile(Image image) {
		setImage(image);
	}
	public Tile(Image image,boolean a) {
		setImage(image);
		hasCollition = a;
	}
	public Tile(Image image,int posX,int posY) {
		setImage(image);
		this.posX = posX;
		this.posY = posY;
	}
	public Tile(Image image,int posX,int posY,boolean a) {
		setImage(image);
		hasCollition = a;
		this.posX = posX;
		this.posY = posY;
	}
	@Override
	public void update(Set<KeyCode> activeKeys, double deltaTime) {}

	@Override
	public void draw(GraphicsContext gc) {
		gc.setFill(Color.WHITE);
		gc.fillRect(posX, posY, width, height);
	}

	@Override
	public Rectangle getCollisionShape() {
		return hasCollition ? new Rectangle(posX,posY,Game.tileSize,Game.tileSize) : null;
	}
	
	public void setImage(Image image) {
		this.image = image;
	}
	
	public boolean isCollider() {
		return hasCollition;
	}

	

}
