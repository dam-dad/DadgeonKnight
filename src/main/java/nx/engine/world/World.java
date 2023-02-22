package nx.engine.world;

import javafx.scene.canvas.GraphicsContext;
import nx.engine.Camera;
import nx.engine.Game;
import nx.engine.UI.Dialog;
import nx.engine.scenes.WorldScene;
import nx.engine.tile.TileSet;
import nx.engine.world.entities.*;
import nx.util.CSV;

import java.util.*;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class World {

    private final Level level;
    private final List<Entity> entities;

    private final List<Entity> entitiesToAdd;
    private final List<Entity> entitiesToRemove;
    
	public static Vector2D spawn;

    public World(TileSet tileSet, Layer... layers) {
        this.level = new Level(tileSet, layers);
        this.entities = new ArrayList<>();

        this.entitiesToAdd = new ArrayList<>();
        this.entitiesToRemove = new ArrayList<>();
    }

    public World(TileSet tileSet, List<Entity> entities, Layer... layers) {
    	this(tileSet, layers);
    	entities.forEach(entity -> {
            this.entities.add(entity);
            entity.setWorld(this);
        });
    	
        addEntity(Game.player);
        addEntity(new Chest(26, 27));
    }

    public World(TileSet tileSet, String entitties, Layer... layers) {
    	this(tileSet, Entity.loadEntititiesFromCSV(entitties), layers);
    }

    public void update(double delta) {
        entities.removeAll(entitiesToRemove);
        entitiesToRemove.clear();

        entities.addAll(entitiesToAdd);
        entitiesToAdd.clear();

        entities.forEach(entity -> entity.update(delta));
    }

    public void draw(GraphicsContext gc, Camera camera) {
        level.draw(gc, camera);

        entities.stream()
                .sorted(Comparator.comparingDouble(e -> e.getPosY() + e.getHeight()))
                .forEach(entity -> entity.draw(gc, camera));
    }

    public void onPlayerDeath() {
        Optional<TestBoss> bossOptional = entities.stream()
                .filter(entity -> entity instanceof TestBoss)
                .map(TestBoss.class::cast)
                .findFirst();

        if (bossOptional.isEmpty())
            return;

        TestBoss boss = bossOptional.get();
        removeEntity(boss);

        TestBoss newBoss = new TestBoss(boss.getInitialX(), boss.getInitialY());
        addEntity(newBoss);
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

    public Level getLevel() {
        return level;
    }

	public Vector2D getSpawn() {
		return spawn;
	}

	public void setSpawn(Vector2D spawn) {
		this.spawn = spawn;
	}
    
    

}
