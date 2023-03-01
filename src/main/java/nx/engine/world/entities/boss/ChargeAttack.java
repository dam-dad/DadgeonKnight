package nx.engine.world.entities.boss;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import nx.engine.Camera;
import nx.engine.Game;
import nx.engine.world.entities.TestBoss;

/**
 * Represents the charge attack of the boss
 */
public class ChargeAttack extends BossAttack {

    private static final Image IMAGE = new Image("/assets/textures/magic2.gif");
    private static final double MAX_ATTACK_TIME = 2.0;

    private double timeAttacking = 0.0;

    /**
     * Constructor
     * @param boss Boss instance
     */
    public ChargeAttack(TestBoss boss) {
        super(boss);
    }

    /**
     * Updates the attack
     * @param delta Frame delta time
     */
    @Override
    public void update(double delta) {
        timeAttacking += delta;
    }

    /**
     * Executes at the beginning of the attack
     */
    @Override
    public void onStart() {

    }

    /**
     * Draws the attack
     * @param gc GraphicsContext to draw on
     * @param camera World camea
     */
    @Override
    public void draw(GraphicsContext gc, Camera camera) {
        gc.drawImage(IMAGE, Game.SCREEN_CENTER_X - camera.getX() + getBoss().getPosX() - Game.tileSize, Game.SCREEN_CENTER_Y - camera.getY() + getBoss().getPosY() - (getBoss().getHeight() / 2) * 4, Game.tileSize * 4, Game.tileSize * 4);
    }

    /**
     * Executes at the end of the attack
     */
    @Override
    public void onFinish() {
        timeAttacking = 0.0;
    }

    /**
     * @return True if the boss should change to a different attack, false if not
     */
    @Override
    public boolean shouldChange() {
        return timeAttacking >= MAX_ATTACK_TIME;
    }

}
