package nx.engine.entity;

import java.util.Set;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Shape;

public class MobEntity<T extends Shape> extends Entity<T> {

	@Override
	public void update(Set<KeyCode> activeKeys, double deltaTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public T getCollisionShape() {
		// TODO Auto-generated method stub
		return null;
	}

}
