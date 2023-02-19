package nx.engine.world.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import nx.engine.Camera;
import nx.engine.Game;
import nx.engine.world.entities.boss.BossAttack;
import nx.engine.world.entities.boss.ChargeAttack;
import nx.engine.world.entities.boss.FireAttack;
import nx.engine.world.entities.boss.MagicAttack;
import nx.game.App;
import nx.util.SoundMixer;
import nx.util.Vector2f;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestBoss extends Entity implements Enemy {

    protected static final Image sprite = new Image("/assets/textures/player/kevin_idle_00.png");
    private static final double attackDelay = 0.15;
    private static final double RADIUS = 10 * Game.tileSize;
    private static final float FIREBALL_SPEED = 8.5f * Game.tileSize;
    private static final float SPEED = 2.5f * Game.tileSize;
	protected boolean canDie = true;
	protected double mobHealth = 50;

    private double timeSinceLastAttack = 0.0;
    private BossAttack currentAttack;
    private final List<BossAttack> attackList;
    private final Random random;

    private boolean musicPlayed;

    public TestBoss(int x, int y) {
        super(x * Game.tileSize, y * Game.tileSize, sprite);
        height = Game.tileSize;

        this.random = new Random();

        this.attackList = new ArrayList<>();
        attackList.add(new FireAttack(this));
        attackList.add(new MagicAttack(this));
        attackList.add(new ChargeAttack(this));

        currentAttack = attackList.get(random.nextInt(attackList.size()));
    }

    public TestBoss(double parseDouble, double parseDouble2) {
    	this((int) parseDouble,(int) parseDouble2);
	}
    
	public void getAttacked(int damage) {
		mobHealth -= canDie? damage : 0;
	}

	@Override
    public void update(double deltaTime) {
        if (!musicPlayed) {
            Player player = getWorld().getEntities().stream().filter(entity -> entity instanceof Player)
                    .map(Player.class::cast)
                    .findFirst().get();

            if (player.getDistanceToEntity(this) < RADIUS) {
                musicPlayed = true;
                App.mixer.addGameSound("battleThemeA.mp3").setVolume(0.5).play();
            }
        }

        if (currentAttack.shouldChange()) {
            currentAttack.onFinish();
            currentAttack = attackList.get(random.nextInt(attackList.size()));
            currentAttack.onStart();
            System.out.println("Changing attack: " + currentAttack.getClass());
        }

        currentAttack.update(deltaTime);
    }

    @Override
    public void draw(GraphicsContext gc, Camera camera) {
        drawInternal(gc, camera, 2);

        currentAttack.draw(gc);
    }

    @Override
    public void getAttacked(double damage) {
        this.mobHealth -= damage;
    }

    public void follow(double delta) {
//        double realSpeed = SPEED * Game.LastFrameRate * delta;
//        Vector2D direction = getVector2DToEntity(playerOptional.get());
//        direction = direction.scalarMultiply(realSpeed);
//
//        move(direction);
    }

}
