package nx.engine.world.entities.boss;

import nx.engine.Game;
import nx.engine.world.entities.Fireball;
import nx.engine.world.entities.Player;
import nx.engine.world.entities.TestBoss;
import nx.util.Vector2f;

public class FireAttack extends BossAttack {

    private static final double RADIUS = 10;
    private static final float FIREBALL_SPEED = 10 * Game.tileSize;
    private static final double MAX_ATTACK_TIME = 2.0;
    private static final double ATTACK_DELAY = 0.125;

    private double timeAttacking = 0.0;
    private double timeSinceLastAttack = 0.0;

    public FireAttack(TestBoss boss) {
        super(boss);
    }

    @Override
    public void update(double delta) {
        timeAttacking += delta;
        timeSinceLastAttack += delta;

        if (timeSinceLastAttack < ATTACK_DELAY) {
            return;
        }

        timeSinceLastAttack -= ATTACK_DELAY;

        getBoss().getWorld().getEntities().stream()
                .filter(entity -> {
                    return Math.abs(entity.getPosX() - getBoss().getPosX()) < RADIUS * Game.tileSize && Math.abs(entity.getPosY() - getBoss().getPosY()) < RADIUS * Game.tileSize && entity instanceof Player;
                })
                .map(Player.class::cast)
                .forEach(player -> {
                    Vector2f direction = new Vector2f((float) (player.getPosX() - getBoss().getPosX()), (float) (player.getPosY() - getBoss().getPosY()));

                    getBoss().getWorld().addEntity(new Fireball(getBoss().getPosX(), getBoss().getPosY(), direction, FIREBALL_SPEED));
                });
    }

    @Override
    public boolean shouldChange() {
        return timeAttacking >= MAX_ATTACK_TIME;
    }

    @Override
    public void onStart() {
        timeAttacking = 0.0;
        timeSinceLastAttack = 0.0;
    }

    @Override
    public void onFinish() {

    }

}
