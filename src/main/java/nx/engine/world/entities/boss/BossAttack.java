package nx.engine.world.entities.boss;

import javafx.scene.canvas.GraphicsContext;
import nx.engine.Camera;
import nx.engine.world.entities.TestBoss;

/**
 * Represents a boss attack
 */
public abstract class BossAttack {

    private final TestBoss boss;

    /**
     * Constructor
     * @param boss Boss instance
     */
    public BossAttack(TestBoss boss) {
        this.boss = boss;
    }

    /**
     * Updates the attack
     * @param delta Frame delta time
     */
    public abstract void update(double delta);

    /**
     * Executes at the beginning of the attack
     */
    public abstract void onStart();

    /**
     * Executes at the end of the attack
     */
    public abstract void onFinish();

    /**
     * @return True if the boss should change to a different attack, false if not
     */
    public abstract boolean shouldChange();

    /**
     * Draws the attack
     * @param gc GraphicsContext to draw on
     * @param camera World camea
     */
    public void draw(GraphicsContext gc, Camera camera) {

    }

    public TestBoss getBoss() {
        return boss;
    }

}
