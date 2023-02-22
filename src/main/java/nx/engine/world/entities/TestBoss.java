package nx.engine.world.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import nx.engine.Camera;
import nx.engine.Game;
import nx.game.App;
import nx.util.SoundMixer;
import nx.util.Vector2f;

public class TestBoss extends Entity implements Enemy {

    protected static final Image sprite = new Image("/assets/textures/player/kevin_idle_00.png");
    private static final double attackDelay = 0.15;
    private static final double RADIUS = 10;
    private static final float FIREBALL_SPEED = 8.5f * Game.tileSize;
	protected boolean canDie = true;
	protected double mobHealth = 50;

    private double timeSinceLastAttack = 0.0;

    public TestBoss(int x, int y) {
        super(x * Game.tileSize, y * Game.tileSize, sprite);
        height = Game.tileSize;

        App.mixer.addGameSound("battleThemeA.mp3").setVolume(0.5).play();
    }

    public TestBoss(double parseDouble, double parseDouble2) {
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

                        getWorld().addEntity(new Fireball(getPosX(), getPosY(), direction, FIREBALL_SPEED));
                    });
        }
    }

    @Override
    public void draw(GraphicsContext gc, Camera camera) {
        drawInternal(gc, camera, 2);
    }

    @Override
    public void getAttacked(double damage) {
        this.mobHealth -= damage;
    }
}
