package nx.engine.UI;

import javafx.scene.canvas.GraphicsContext;

public interface UI {
	
    void update(double delta);

    void draw(GraphicsContext gc);

}
