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

public class TestBoss extends MobEntity {

    protected static final Image sprite = new Image("/assets/textures/player/kevin_idle_00.png");
    protected static final Image shadow = new Image("/assets/textures/shadow.png");
    private static final double attackDelay = 0.15;
    private static final double RADIUS = 10 * Game.tileSize;
    private static final float FIREBALL_SPEED = 8.5f * Game.tileSize;
    private static final float SPEED = 2.5f * Game.tileSize;
	protected boolean canDie = true;
	protected double mobHealth = 150;

    private double timeSinceLastAttack = 0.0;
    private BossAttack currentAttack;
    private final List<BossAttack> attackList;
    private final Random random;
    private double yOffset = 0.0;
    private boolean isShadowEnabled;

    private boolean musicPlayed;

    private final int initialX, initialY;

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

    public TestBoss(double parseDouble, double parseDouble2) {
    	this((int) parseDouble,(int) parseDouble2);
	}
    
	public void getAttacked(int damage) {
        if (yOffset != 0.0)
            return;

		mobHealth -= canDie? damage : 0;
	}

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

    @Override
    public void draw(GraphicsContext gc, Camera camera) {
        super.draw(gc, camera);

        currentAttack.draw(gc, camera);
    }

    protected void drawInternal(GraphicsContext gc, Camera camera, double scale) {
        if (image == null)
            return;

        if (isShadowEnabled)
            gc.drawImage(shadow, Game.SCREEN_CENTER_X - camera.getX() + getPosX() - getWidth() / 2 * scale, Game.SCREEN_CENTER_Y - camera.getY() + getPosY() - getHeight() / 4 * scale, Game.tileSize * scale, Game.tileSize * scale);

        gc.drawImage(image, Game.SCREEN_CENTER_X - camera.getX() + getPosX() - getWidth() / 2 * scale, Game.SCREEN_CENTER_Y - camera.getY() + getPosY() - getHeight() / 2 * scale - yOffset, Game.tileSize * scale, Game.tileSize * scale);
    }

    public void setyOffset(double yOffset) {
        this.yOffset = yOffset;
    }

    public void setShadowEnabled(boolean shadowEnabled) {
        isShadowEnabled = shadowEnabled;
    }

    public double getMobHealth() {
        return mobHealth;
    }

    public int getInitialX() {
        return initialX;
    }

    public int getInitialY() {
        return initialY;
    }

}
