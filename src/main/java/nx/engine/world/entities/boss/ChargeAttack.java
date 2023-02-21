package nx.engine.world.entities.boss;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import nx.engine.Camera;
import nx.engine.Game;
import nx.engine.world.entities.TestBoss;

public class ChargeAttack extends BossAttack {

    private static final Image IMAGE = new Image("/assets/textures/magic2.gif");
    private static final double MAX_ATTACK_TIME = 2.0;

    private double timeAttacking = 0.0;

    public ChargeAttack(TestBoss boss) {
        super(boss);
    }

    @Override
    public void update(double delta) {
        timeAttacking += delta;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void draw(GraphicsContext gc, Camera camera) {
        gc.drawImage(IMAGE, Game.SCREEN_CENTER_X - camera.getX() + getBoss().getPosX() - Game.tileSize, Game.SCREEN_CENTER_Y - camera.getY() + getBoss().getPosY() - (getBoss().getHeight() / 2) * 4, Game.tileSize * 4, Game.tileSize * 4);
    }

    @Override
    public void onFinish() {
        timeAttacking = 0.0;
    }

    @Override
    public boolean shouldChange() {
        return timeAttacking >= MAX_ATTACK_TIME;
    }

}
