package nx.engine.world.entities;

import javafx.scene.image.Image;
import nx.engine.Game;
import nx.engine.particles.Particle;
import nx.game.App;
import nx.util.Music;
import nx.util.Vector2f;

import java.util.Optional;
import java.util.Random;

public class Fireball extends Entity {

    private static final Image IMAGE = new Image("/assets/textures/bola_du_fogo.gif");
    private static final float DEFAULT_SPEED = 3.5f * Game.tileSize;
    private static final double MAX_TIME_ALIVE = 2.5f;
    private static final double RADIUS = 48;

    private final Vector2f direction;
    private final float speed;
    private double timeAlive = 0.0;

    public Fireball(double x, double y, Vector2f direction) {
        this(x, y, direction, DEFAULT_SPEED);
    }

    public Fireball(double x, double y, Vector2f direction, float speed) {
        super(x, y, IMAGE);

        this.speed = speed;
        this.direction = direction.normalize();
    }

    @Override
    public void update(double deltaTime) {
        setPosX(getPosX() + direction.x * deltaTime * speed);
        setPosY(getPosY() + direction.y * deltaTime * speed);

        timeAlive += deltaTime;
        if (timeAlive > MAX_TIME_ALIVE) {
            getWorld().removeEntity(this);
            return;
        }

        if (getWorld().getLevel().isSolid((int) Math.floor(getPosX() / Game.tileSize), (int) Math.floor(getPosY() / Game.tileSize))) {
            getWorld().removeEntity(this);
            createParticleEffect(getPosX(), getPosY(), 10);
            return;
        }

        Optional<Player> playerOptional = getWorld().getEntities().stream()
                .filter(entity -> entity instanceof Player)
                .map(entity -> (Player) entity)
                .findAny();

        if (playerOptional.isPresent()) {
            Player player = playerOptional.get();
            if (new Vector2f((float) getPosX(), (float) getPosY()).distance(player.getPosX(), player.getPosY()) > RADIUS)
                return;

            player.getAttacked(2);
            getWorld().removeEntity(this);

            App.mixer.addGameSound("explosion.wav").setVolume(0.04).play();

            createParticleEffect(getPosX(), getPosY(), 30);
        }
    }
    


    private void createParticleEffect(double posX, double posY, int particleAmount) {
    	//TODO No se si es esto pero cuando las particulas son creadas generan un poco de lag.
    	//cambie Random por Math.random por si pudiera ser eso.
    	// P.S cambie el tiempo de vida de las particulas y su velocidad para que se queden cerca del jugador.

        for (int i = 0; i < particleAmount; i++) {
            float directionX = randomFromInterval(-1.0f, 1.0f);
            float directionY = randomFromInterval(-1.0f, 1.0f);
            getWorld().addEntity(new Particle((float) posX, (float) posY, new Vector2f(directionX, directionY).normalize(), image, randomFromInterval(1.0f, 200.0f) + 100.0f));
        }
        
    	
//      Random random = new Random();

//      for (int i = 0; i < 20; i++) {
//          float directionX = (random.nextFloat(2) - 1);
//          float directionY = (random.nextFloat(2) - 1);
//          getWorld().addEntity(new Particle((float) posX, (float) posY, new Vector2f(directionX, directionY).normalize(), image, random.nextFloat(200) + 300));
//      }
    }

}
