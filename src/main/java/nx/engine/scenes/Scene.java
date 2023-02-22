package nx.engine.scenes;

import javafx.scene.canvas.GraphicsContext;

/**
 * Defines a scene
 */
public interface Scene {

    void update(double delta);

    void draw(GraphicsContext gc);

}
