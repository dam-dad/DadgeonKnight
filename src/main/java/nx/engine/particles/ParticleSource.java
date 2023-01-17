package nx.engine.particles;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import nx.engine.Game;
import nx.util.Vector2f;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParticleSource {

    private static final double MAX_TIME_ALIVE = 5;

    private final List<Particle> particles;
    private final Image image;

    private double delay = 0;

    public ParticleSource(float x, float y, String particle) {
        this.particles = new ArrayList<>();
        this.image = new Image("/assets/textures/player/kevin_idle_00.png");
    }

    public void update(double delta) {
        Iterator<Particle> iterator = particles.iterator();
        while (iterator.hasNext()) {
            Particle particle = iterator.next();
            particle.update(delta);

            if (particle.getTimeAlive() > 5)
                iterator.remove();
        }

        delay += delta;
        if (delay > 0.25) {
            particles.add(new Particle(8 * 48, 6* 48, new Vector2f(60, 60)));
            delay -= 0.25;
        }
    }

    public void draw(GraphicsContext gc) {
        particles.forEach(particle -> {
            gc.drawImage(image, Game.SCREEN_CENTER_X - Game.camera.getX() + particle.getPosX(), Game.SCREEN_CENTER_Y - Game.camera.getY() + particle.getPosY(), 10, 10);
        });
    }

}
