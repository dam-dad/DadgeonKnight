package nx.engine.world.entities.boss;

import nx.engine.world.entities.TestBoss;

public class ChargeAttack extends BossAttack {

    private static final double MAX_ATTACK_TIME = 5.0;

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
    public void onFinish() {
        timeAttacking = 0.0;
    }

    @Override
    public boolean shouldChange() {
        return timeAttacking >= MAX_ATTACK_TIME;
    }

}
