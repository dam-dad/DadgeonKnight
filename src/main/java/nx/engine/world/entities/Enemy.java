package nx.engine.world.entities;

/**
 * Represents an enemy behavior
 */
public interface Enemy {

    /**
     * Deals damage to the enemy
     * @param damage Damage dealt
     */
    void getAttacked(double damage);

}
