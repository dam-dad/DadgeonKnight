package nx.engine.UI;

import javafx.scene.canvas.GraphicsContext;

/**
 * Represents a UI element
 */
public interface UI {

    /**
     * Updates the UI element
     * @param delta
     */
    void update(double delta);

    /**
     * Draws the UI element
     * @param gc GraphicsContext to draw on
     */
    void draw(GraphicsContext gc);

}
