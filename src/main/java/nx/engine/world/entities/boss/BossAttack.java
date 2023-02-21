package nx.engine.world.entities.boss;

import javafx.scene.canvas.GraphicsContext;
import nx.engine.Camera;
import nx.engine.world.entities.TestBoss;

public abstract class BossAttack {

    private final TestBoss boss;

    public BossAttack(TestBoss boss) {
        this.boss = boss;
    }

    public abstract void update(double delta);

    public abstract void onStart();

    public abstract void onFinish();

    public abstract boolean shouldChange();

    public void draw(GraphicsContext gc, Camera camera) {

    }

    public TestBoss getBoss() {
        return boss;
    }

}
