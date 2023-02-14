package nx.engine.world.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import nx.engine.Animation;
import nx.engine.Game;
import nx.engine.UI.Dialog;
import nx.engine.UI.UserInterfaceImage;
import nx.engine.particles.Particle;
import nx.engine.scenes.WorldScene;
import nx.engine.tile.TileSet;
import nx.engine.world.MobEntity;
import nx.game.App;
import nx.util.Direction;
import nx.util.Vector2f;

public class MagicalEntity extends MobEntity {
	
	public boolean isFinish = false;
	
	String walkTileSet = "/assets/textures/magicalMan.png";
	
	public double ANIMATION_SPEED = 0.4;
	
	private double time = 0.0;
	private final double timeToDie = 2.0;
	
	
	private Entity entity;
	
	
	private final Map<Direction, Animation> walk = new HashMap<>() {{
		put(Direction.SOUTH, new Animation(ANIMATION_SPEED,walkTileSet,0,16,32));
	}};

	public MagicalEntity(double posX, double posY) {
		super(posX * Game.tileSize, posY * Game.tileSize);
		
		this.hasDamage = false;
		this.canDie = false;
		this.direction = Direction.EAST;
		
		this.sizeTextureX = 16;
		this.sizeTextureY = 32;
		
		this.scale = 2;
		
		this.animation  = walk.get(Direction.SOUTH);
		
//		this.image = new Image("/assets/textures/player/kevin_idle_00.png");
		
		this.sizePlayerDetection = 120;
		
		entity = new Sword(TileSet.ITEMS_TILES, getPosX()/Game.tileSize, getPosY()/Game.tileSize, Game.tileSize, Game.tileSize);

	}
	
	@Override
	public void update(double deltaTime) {
		Optional<Player> player = getWorld()
				.getEntities()
				.stream()
				.filter(e -> e instanceof Player)
				.map(e -> (Player)e)
				.findAny();
		
		animation.update(deltaTime);
		
		if(!player.isPresent() || isFinish)
			return;
		double distance = getDistanceToEntity(player.get());
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
	
    private void createParticleEffect(double posX, double posY) {
        for (int i = 0; i < 20; i++) {
            float directionX = randomFromInterval(-1.0f, 1.0f);
            float directionY = randomFromInterval(-1.0f, 1.0f);
            getWorld().addEntity(new Particle((float) posX, (float) posY, new Vector2f(directionX, directionY).normalize(), WorldScene.smoke, randomFromInterval(1.0f, 200.0f) + 100.0f));
        }
    }
}
