package nx.engine.tile;

import java.util.List;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import nx.engine.world.entities.Entity;

public interface SmartMovement {

	void find(Entity entity1,Entity entity2);
	
	boolean follow(List<Vector2D> v,double speed);
}
