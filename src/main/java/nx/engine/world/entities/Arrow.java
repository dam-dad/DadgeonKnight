package nx.engine.world.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;
import nx.engine.Camera;
import nx.engine.Game;
import nx.engine.particles.Particle;
import nx.engine.tile.TileSet;
import nx.engine.tile.TileSetManager;
import nx.engine.world.MobEntity;
import nx.util.Music;
import nx.util.Vector2f;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Arrow extends Entity {

    private static final float SPEED = 8.5f * Game.tileSize;
    private static final double MAX_TIME_ALIVE = 0.5f;
    private static final double RADIUS = 48;
    private static final int DAMAGE = 2;

    private final Vector2f direction;
    private double timeAlive = 0.0;

    private double cos;

    public Arrow(double x, double y, Vector2f direction) {
        super(x, y, TileSetManager.loadImageFromTileSet(TileSet.ITEMS_TILES, 109, 16, 16));

        this.direction = direction.normalize();
        this.cos = 180.0 / Math.PI * Math.atan2(direction.x - 0, direction.y);
    }

    @Override
    public void update(double deltaTime) {
        setPosX(getPosX() + direction.x * deltaTime * SPEED);
        setPosY(getPosY() + direction.y * deltaTime * SPEED);

        timeAlive += deltaTime;
        if (timeAlive > MAX_TIME_ALIVE) {
            getWorld().removeEntity(this);
        }

        List<Enemy> entities = getWorld().getEntities().stream()
                .filter(entity -> entity instanceof Enemy)
                .filter(entity -> getDistanceToEntity(entity) < 0.5 * Game.tileSize)
                .map(Enemy.class::cast)
                .collect(Collectors.toList());

        if (entities.size() > 0)
            getWorld().removeEntity(this);

        entities.forEach(mobEntity -> mobEntity.getAttacked(DAMAGE));
    }

    @Override
    public void draw(GraphicsContext gc, Camera camera) {
        gc.save();

        Rotate rotate = new Rotate(- cos + 135, getPosX() - camera.getX() + Game.screenWidth / 2, getPosY() - camera.getY() + Game.screenheigth / 2);
        gc.setTransform(rotate.getMxx(), rotate.getMyx(), rotate.getMxy(), rotate.getMyy(), rotate.getTx(), rotate.getTy());
        super.draw(gc, camera);
        gc.restore();
    }

}
