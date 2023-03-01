package nx.engine.world.entities.boss;

import javafx.animation.Interpolator;
import nx.engine.Game;
import nx.engine.world.entities.Player;
import nx.engine.world.entities.TestBoss;
import nx.game.App;

/**
 * Represents the jump attack of the boss
 */
public class JumpAttack extends BossAttack {

    private static final double ATTACK_TIME = 1.0;
    private static final double ATTACK_DAMAGE = 7.0;
    private static final double ATTACK_HEIGHT = 60.0 * Game.tileSize;

    private boolean hasAttackEnded;
    private double attackProgress;
    private boolean hasMoved;
    private Player player;

    /**
     * Constructor
     * @param boss Boss instance
     */
    public JumpAttack(TestBoss boss) {
        super(boss);
    }

    /**
     * Updates the attack
     * @param delta Delta time
     */
    @Override
    public void update(double delta) {
        attackProgress += delta;

        if (player == null) {
            player = getBoss().getWorld().getEntities().stream().filter(entity -> entity instanceof Player)
                    .map(Player.class::cast)
                    .findFirst().get();
        }

        double yOffset;

        if (attackProgress > ATTACK_TIME / 2.0) {
            yOffset = Interpolator.EASE_IN.interpolate(0, ATTACK_HEIGHT, (attackProgress - ATTACK_TIME / 2.0) / (ATTACK_TIME / 2.0));
            getBoss().setyOffset(ATTACK_HEIGHT - yOffset);
            getBoss().setShadowEnabled(true);

            if (!hasMoved) {
                getBoss().setPosX(player.getPosX());
                getBoss().setPosY(player.getPosY());
                hasMoved = true;
            }
        } else {
            yOffset = Interpolator.EASE_OUT.interpolate(0, ATTACK_HEIGHT, attackProgress / (ATTACK_TIME / 2.0));
            getBoss().setyOffset(yOffset);
        }

        if (attackProgress > ATTACK_TIME) {
            hasAttackEnded = true;
            App.mixer.addGameSound("explosion.wav").setVolume(0.04).play();
            if (player.getDistanceToEntity(getBoss()) < Game.tileSize) {
                player.getAttacked((int) ATTACK_DAMAGE);
            }
        }
    }

    /**
     * Executes at the beginning of the attack
     */
    @Override
    public void onStart() {

    }

    /**
     * Executes at the end of the attack
     */
    @Override
    public void onFinish() {
        attackProgress = 0;
        hasAttackEnded = false;
        getBoss().setyOffset(0.0);
        hasMoved = false;
        getBoss().setShadowEnabled(false);
    }

    /**
     * @return True if the boss should change to a different attack, false if not
     */
    @Override
    public boolean shouldChange() {
        return hasAttackEnded;
    }

}
