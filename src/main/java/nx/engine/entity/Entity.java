package nx.engine.entity;

import java.util.Set;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Shape;
import nx.engine.Game;
import nx.util.Direction;

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
	
	public Vector2D getVector2DToEntity(Entity e) {
		Vector2D direction = new Vector2D((e.posX + (e.width/2)) - (this.posX + (this.width/2)), (e.posY + (e.height/2)) - (this.posY + (this.height/1.5)));
	    direction = direction.normalize();
	    
	    return direction;
	}
	public Direction getDirectionFromVector2D(Vector2D direction) {
		double angle = Math.atan2(direction.getY(), direction.getX());
		if (angle >= -Math.PI/4 && angle < Math.PI/4) {
		    return Direction.EAST;
		} else if (angle >= Math.PI/4 && angle < 3*Math.PI/4) {
		    return Direction.SOUTH;
		} else if (angle >= 3*Math.PI/4 || angle < -3*Math.PI/4) {
		    return Direction.WEST;
		} else {
		    return Direction.NORTH;
		}
	}
	
	public double pushOut(Entity collition,double force) {
		double distance = getDistanceToEntity(collition);
		
		Vector2D collisionNormal = getVector2DToEntity(collition);
		Vector2D movement = new Vector2D(0,0);
		switch (getDirectionFromVector2D(collisionNormal.scalarMultiply(-1))) {
		case WEST:
			movement = new Vector2D(-1,0);
			break;
		case EAST:
			movement = new Vector2D(1,0);
			break;
		case NORTH:
			movement = new Vector2D(0,-1);
			break;
		case SOUTH:
			movement = new Vector2D(0,1);
			break;
		default:
			break;
		}
		movement = movement.scalarMultiply(distance).scalarMultiply(force);
		
		this.posX += movement.getX();
		this.posY += movement.getY();
		
		return distance;
	}
	
	public Image getImage() {
		return image;
	}

}
