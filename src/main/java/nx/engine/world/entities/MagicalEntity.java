package nx.engine.world.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import nx.engine.Animation;
import nx.engine.Game;
import nx.engine.UI.Dialog;
import nx.engine.UI.UserInterfaceImage;
import nx.engine.particles.Particle;
import nx.engine.particles.ParticleManager;
import nx.engine.scenes.WorldScene;
import nx.engine.tile.TileSet;
import nx.engine.world.MobEntity;
import nx.game.App;
import nx.util.Direction;
import nx.util.Vector2f;

/**
 * Represents a magical entity
 */
public class MagicalEntity extends MobEntity {
	
	public boolean isFinish = false;
	
	String walkTileSet = "/assets/textures/magicalMan.png";
	
	public double ANIMATION_SPEED = 0.4;
	
	private double time = 0.0;
	private final double timeToDie = 2.0;

	private final Entity entity;

	private final Map<Direction, Animation> walk = new HashMap<>() {{
		put(Direction.SOUTH, new Animation(ANIMATION_SPEED, walkTileSet, 0, 16, 32));
	}};

	/**
	 * Constructor
	 * @param posX Spawn position X
	 * @param posY Spawn position Y
	 */
	public MagicalEntity(double posX, double posY) {
		super(posX * Game.tileSize, posY * Game.tileSize);
		
		this.hasDamage = false;
		this.canDie = false;
		this.direction = Direction.EAST;
		
		this.sizeTextureX = 16;
		this.sizeTextureY = 32;
		this.scale = 2;

		this.animation  = walk.get(Direction.SOUTH);

		this.sizePlayerDetection = 120;
		
		entity = new Sword(TileSet.ITEMS_TILES, getPosX()/Game.tileSize, getPosY()/Game.tileSize, Game.tileSize, Game.tileSize);
	}

	/**
	 * Updates the entity
	 * @param deltaTime Frame delta
	 */
	@Override
	public void update(double deltaTime) {
		
		if(getPosX() + Game.tileSize > Player.get().getCamera().getX() - Game.screenWidth &&
				getPosX() - Game.tileSize < Player.get().getCamera().getX() + Game.screenWidth &&
				getPosY() + Game.tileSize > Player.get().getCamera().getY() - Game.screenheigth &&
				getPosY() - Game.tileSize  < Player.get().getCamera().getY() + Game.screenheigth){
			animation.update(deltaTime);
			
			if(isFinish)
				return;
			double distance = getDistanceToEntity(Player.get());
			if(distance < sizePlayerDetection && WorldScene.dialog == null) {
				WorldScene.dialog = new Dialog(".....","/assets/levels/startedMap/oldManText.csv",UserInterfaceImage.Dialog);
				WorldScene.dialog.play();
			}
			else if(distance >= sizePlayerDetection) {
				WorldScene.dialog = null;
				
			}
			
			if(WorldScene.dialog != null && WorldScene.dialog.hasFinish()) {
				time += deltaTime;
			}
			if(time > timeToDie) {
				getWorld().addEntity(entity);
				createParticleEffect(getPosX(),getPosY());
				App.mixer.addGameSound("SmokeExplosion.wav").setVolume(0.04).play();
				getWorld().removeEntity(this);
				WorldScene.dialog = null;
				isFinish = true;
			}
		}

		if(WorldScene.dialog != null && WorldScene.dialog.hasFinish()) {
			time += deltaTime;
		}

		if(time > timeToDie) {
			getWorld().addEntity(entity);
			createParticleEffect(getPosX(),getPosY());
			App.mixer.addGameSound("SmokeExplosion.wav").setVolume(0.04).play();
			getWorld().removeEntity(this);
			WorldScene.dialog = null;
			isFinish = true;
		}
	}

	/**
	 * Creates a particle effect
	 * @param posX Spawn position X
	 * @param posY Spawn position Y
	 */
    private void createParticleEffect(double posX, double posY) {
        for (int i = 0; i < 20; i++) {
            float directionX = randomFromInterval(-1.0f, 1.0f);
            float directionY = randomFromInterval(-1.0f, 1.0f);
            getWorld().addEntity(new Particle((float) posX, (float) posY, new Vector2f(directionX, directionY).normalize(), ParticleManager.smoke, randomFromInterval(1.0f, 200.0f) + 100.0f));
        }
    }

}
