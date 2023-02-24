package nx.engine.scenes;

import javafx.scene.canvas.GraphicsContext;

/**
 * Defines a scene
 */
public interface Scene {

    /**
     * Updates the scene
     * @param delta Frame delta time
     */
    void update(double delta);

    /**
     * Draws the scene
     * @param gc Graphics
     */
    void draw(GraphicsContext gc);

}
