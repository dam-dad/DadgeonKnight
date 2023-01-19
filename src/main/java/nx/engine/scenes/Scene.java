package nx.engine.scenes;

import javafx.scene.canvas.GraphicsContext;

public interface Scene {

    void update(double delta);

    void draw(GraphicsContext gc);

}
