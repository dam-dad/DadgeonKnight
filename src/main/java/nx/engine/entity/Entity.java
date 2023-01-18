package nx.engine.entity;

import java.util.Set;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Shape;
import nx.engine.Game;

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
	
	public double getDistanceToEntity(Entity e) {
		return Math.sqrt(Math.pow((e.posX + (e.width/2)) - (this.posX + (this.width/2)), 2) + Math.pow((e.posY + (e.height/2)) - (this.posY + (this.height/1.5)), 2));
	}
	
	public Vector2D getDirectionToEntity(Entity e) {
		Vector2D direction = new Vector2D((e.posX + (e.width/2)) - (this.posX + (this.width/2)), (e.posY + (e.height/2)) - (this.posY + (this.height/2)));
	    direction = direction.normalize();
	    
	    return direction;
	}
	public double pushOut(Entity collition,double force) {
		double distance = getDistanceToEntity(collition);
		
		Vector2D collisionNormal = new Vector2D(
				(collition.posX + collition.width/2) - (this.posX + (this.width/2)), 
				(collition.posY + collition.height/2) - (this.posY + (this.height/1.5))
				);
		collisionNormal = collisionNormal.normalize();
		
		Vector2D movement = collisionNormal.scalarMultiply(distance).scalarMultiply(force).scalarMultiply(-1);
		
		this.posX += movement.getX();
		this.posY += movement.getY();
		
		return distance;
	}
	
	public Image getImage() {
		return image;
	}

}
