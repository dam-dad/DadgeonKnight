package nx.engine.world.entities.boss;

import nx.engine.Game;
import nx.engine.world.entities.MagicSphere;
import nx.engine.world.entities.Player;
import nx.engine.world.entities.TestBoss;
import nx.util.Vector2f;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class MagicAttack extends BossAttack {

    private static final double ATTACK_RADIUS = 10;
    private static final double SPHERE_DELAY = 0.125;
    private static final int MAX_SPHERES = 8;
    private static final int SPHERE_RADIUS = 2 * Game.tileSize;

    private double timeSinceLastSphere = 0.0;
    private int spheresLaunched = 0;

    double space = (2 * Math.PI) / MAX_SPHERES;
    double start = 0;
    double cos;

    public MagicAttack(TestBoss boss) {
        super(boss);
    }

    @Override
    public void update(double delta) {
        timeSinceLastSphere += delta;

        if (timeSinceLastSphere >= SPHERE_DELAY && spheresLaunched < MAX_SPHERES) {
            timeSinceLastSphere -= SPHERE_DELAY;
            spheresLaunched++;

            Vector2D sphereSpawn = new Vector2D(getBoss().getPosX() + Game.tileSize / 2 + Math.cos(start) * SPHERE_RADIUS, getBoss().getPosY() - Game.tileSize / 2 + Math.sin(start) * SPHERE_RADIUS);
            this.cos = 180.0 / Math.PI * Math.atan2(sphereSpawn.getX() - getBoss().getPosX() - 0, sphereSpawn.getY() - getBoss().getPosY());

            start += space;

            getBoss().getWorld().getEntities().stream()
                    .filter(entity -> {
                        return Math.abs(entity.getPosX() - getBoss().getPosX()) < ATTACK_RADIUS * Game.tileSize && Math.abs(entity.getPosY() - getBoss().getPosY()) < ATTACK_RADIUS * Game.tileSize && entity instanceof Player;
                    })
                    .map(Player.class::cast)
                    .forEach(player -> {
                        getBoss().getWorld().addEntity(new MagicSphere(sphereSpawn.getX(), sphereSpawn.getY(), player));
                    });
        }
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onFinish() {
        timeSinceLastSphere = 0.0;
        spheresLaunched = 0;
    }

    @Override
    public boolean shouldChange() {
        return timeSinceLastSphere >= SPHERE_DELAY * MAX_SPHERES + MagicSphere.WAIT_TIME;
    }

}
