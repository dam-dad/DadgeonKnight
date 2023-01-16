package nx.engine.entity;

import java.util.Set;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Shape;

public abstract class Entity<T extends Shape> {
	
	public double posX, posY;
	public double width, height;
	public Image image;
	
	
	public abstract void update(Set<KeyCode> activeKeys,double deltaTime);
	public abstract void draw(GraphicsContext gc);
	public abstract T getCollisionShape();

	public boolean checkCollision(Entity<? extends Shape> entity) {
		return (getCollisionShape() != null && entity.getCollisionShape() != null && getCollisionShape().intersects(entity.getCollisionShape().getLayoutBounds()));
	}
	
	public Image getImage() {
		return image;
	}

}
