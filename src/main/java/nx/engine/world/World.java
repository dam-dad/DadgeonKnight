package nx.engine.world;

import javafx.scene.canvas.GraphicsContext;
import nx.engine.Camera;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class World {

    private final Level level;
    private final List<Entity> entities;

    private final List<Entity> entitiesToAdd;
    private final List<Entity> entitiesToRemove;

    public World(String... fileNames) {
        this.level = new Level(fileNames);
        this.entities = new ArrayList<>();

        this.entitiesToAdd = new ArrayList<>();
        this.entitiesToRemove = new ArrayList<>();
    }

    public void update(double delta) {
        entities.forEach(entity -> entity.update(delta));

        entities.removeAll(entitiesToRemove);
        entitiesToRemove.clear();

        entities.addAll(entitiesToAdd);
        entitiesToAdd.clear();
    }

    public void draw(GraphicsContext gc, Camera camera) {
        level.draw(gc, camera);

        entities.stream()
                .sorted(Comparator.comparingDouble(e -> e.posY + e.height))
                .forEach(entity -> entity.draw(gc, camera));
    }

    public void addEntity(Entity entity) {
        entity.setWorld(this);
        entitiesToAdd.add(entity);
    }

    public void removeEntity(Entity entity) {
        entitiesToRemove.add(entity);
    }

    public List<Entity> getEntities() {
        return entities;
    }

}
