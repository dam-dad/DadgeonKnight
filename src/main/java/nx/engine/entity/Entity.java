package nx.engine.entity;

import java.util.Set;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Shape;
import nx.engine.level.Level;

public abstract class Entity {
	
	public double posX, posY;
	public double width, height;
	public Image image;
	private Level level;

	public abstract void update(double deltaTime);
	public abstract void draw(GraphicsContext gc);
	public abstract Shape getCollisionShape();

	public boolean checkCollision(Entity entity) {
		return (getCollisionShape() != null && entity.getCollisionShape() != null && getCollisionShape().intersects(entity.getCollisionShape().getLayoutBounds()));
	}

	protected void move(double x, double y) {

	}
	
	public Image getImage() {
		return image;
	}

}
