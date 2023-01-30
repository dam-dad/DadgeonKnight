package nx.engine.world.entities;

import javafx.scene.image.Image;
import nx.engine.Game;
import nx.util.Vector2f;

public class Wizard extends Entity {

    protected static final Image sprite = new Image("/assets/textures/player/kevin_idle_00.png");
    private static final double attackDelay = 1.0;
    private static final double RADIUS = 5;
	protected boolean canDie = true;
	protected double mobHealth = 20;

    private double timeSinceLastAttack = 0.0;

    public Wizard(int x,int y) {
        super(x * Game.tileSize, y * Game.tileSize, sprite);
        height = Game.tileSize;
    }

    public Wizard(double parseDouble, double parseDouble2) {
    	this((int) parseDouble,(int) parseDouble2);
	}
    
	public void getAttacked(int damage) {
		mobHealth -= canDie? damage : 0;
	}

	@Override
    public void update(double deltaTime) {
        timeSinceLastAttack += deltaTime;
        
        if(mobHealth < 0) {
			getWorld().removeEntity(this);
			return;
        }

        if (timeSinceLastAttack > attackDelay) {
            timeSinceLastAttack -= attackDelay;

            getWorld().getEntities().stream()
                    .filter(entity -> {
                        return Math.abs(entity.getPosX() - getPosX()) < RADIUS * Game.tileSize && Math.abs(entity.getPosY() - getPosY()) < RADIUS * Game.tileSize && entity instanceof Player;
                    })
                    .map(Player.class::cast)
                    .forEach(player -> {
                        Vector2f direction = new Vector2f((float) (player.getPosX() - getPosX()), (float) (player.getPosY() - getPosY()));

                        getWorld().addEntity(new Fireball(getPosX(), getPosY(), direction));
                    });
        }
    }

}
