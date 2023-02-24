package nx.engine.world.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import nx.engine.Camera;
import nx.engine.Game;
import nx.engine.world.MobEntity;
import nx.engine.world.entities.boss.*;
import nx.game.App;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a boss entity
 */
public class TestBoss extends MobEntity {

    protected static final Image sprite = new Image("/assets/textures/boss/Selection84.png");
    protected static final Image shadow = new Image("/assets/textures/shadow.png");
    private static final double RADIUS = 10 * Game.tileSize;
	protected boolean canDie = true;
	protected double mobHealth = 150;

    private BossAttack currentAttack;
    private final List<BossAttack> attackList;
    private final Random random;
    private double yOffset = 0.0;
    private boolean isShadowEnabled;

    private boolean musicPlayed;

    private final int initialX, initialY;

    /**
     * Boss constructor
     * @param x Spawn position tile X
     * @param y Spawn position tile Y
     */
    public TestBoss(int x, int y) {
        super(x * Game.tileSize, y * Game.tileSize);
        setImage(sprite);
        height = Game.tileSize;
        scale = 2;

        this.initialX = x;
        this.initialY = y;

        this.random = new Random();

        this.attackList = new ArrayList<>();
        attackList.add(new FireAttack(this));
        attackList.add(new MagicAttack(this));
        attackList.add(new ChargeAttack(this));
        attackList.add(new JumpAttack(this));

        currentAttack = attackList.get(random.nextInt(attackList.size()));
    }

    /**
     * Constructor for parsing
     * @param parseDouble Spawn position X
     * @param parseDouble2 Spawn position Y
     */
    public TestBoss(double parseDouble, double parseDouble2) {
    	this((int) parseDouble,(int) parseDouble2);
	}

    /**
     * Subtract health to the boss when it is on the ground
     * @param damage Damage to deal
     */
    public void getAttacked(int damage) {
        if (yOffset != 0.0)
            return;

		mobHealth -= canDie? damage : 0;
	}

    /**
     * Updates the boss and its current attack
     * @param deltaTime Frame delta
     */
	@Override
    public void update(double deltaTime) {
        if (mobHealth <= 0) {
            getWorld().removeEntity(this);
            return;
        }

        if (!musicPlayed) {
            Player player = getWorld().getEntities().stream().filter(entity -> entity instanceof Player)
                    .map(Player.class::cast)
                    .findFirst().get();

            if (player.getDistanceToEntity(this) < RADIUS) {
                musicPlayed = true;
                App.mixer.addGameSound("battleThemeA.mp3").setVolume(0.5).play();
            }
            return;
        }

        if (currentAttack.shouldChange()) {
            currentAttack.onFinish();
            currentAttack = attackList.get(random.nextInt(attackList.size()));
            currentAttack.onStart();
        }

        currentAttack.update(deltaTime);
    }

    /**
     * Overrides the draw function
     * @param gc
     * @param camera
     */
    @Override
    public void draw(GraphicsContext gc, Camera camera) {
        super.draw(gc, camera);

        currentAttack.draw(gc, camera);
    }

    /**
     * Overrides the internal draw function to show a shadow
     * @param gc GraphicsContext to draw on
     * @param camera World camera
     * @param scale Scale of the boss
     */
    protected void drawInternal(GraphicsContext gc, Camera camera, double scale) {
        if (image == null)
            return;

        if (isShadowEnabled)
            gc.drawImage(shadow, Game.SCREEN_CENTER_X - camera.getX() + getPosX() - getWidth() / 2 * scale, Game.SCREEN_CENTER_Y - camera.getY() + getPosY() - getHeight() / 4 * scale, Game.tileSize * scale, Game.tileSize * scale);

        gc.drawImage(image, Game.SCREEN_CENTER_X - camera.getX() + getPosX() - getWidth() / 2 * scale, Game.SCREEN_CENTER_Y - camera.getY() + getPosY() - getHeight() / 2 * scale - yOffset, Game.tileSize * scale, Game.tileSize * scale);
    }

    /**
     * Sets Y offset
     * @param yOffset New value
     */
    public void setyOffset(double yOffset) {
        this.yOffset = yOffset;
    }

    /**
     * Sets if the boss casts a shadow on the ground
     * @param shadowEnabled True if enabled, false if not
     */
    public void setShadowEnabled(boolean shadowEnabled) {
        isShadowEnabled = shadowEnabled;
    }

    /**
     * @return Health of the boss
     */
    public double getMobHealth() {
        return mobHealth;
    }

    /**
     * @return Initial X component of the spawn position
     */
    public int getInitialX() {
        return initialX;
    }

    /**
     * @return Initial Y component of the spawn position
     */
    public int getInitialY() {
        return initialY;
    }

}
