package nx.engine.scenes;

import javafx.scene.canvas.GraphicsContext;
import nx.engine.Camera;
import nx.engine.Game;
import nx.engine.particles.ParticleManager;
import nx.engine.world.World;
import nx.engine.world.entities.Player;
import nx.engine.world.entities.Wizard;

public class WorldScene implements Scene {

    private final World world;

    private final Player player;
    private final Wizard wizard;
    private final ParticleManager particleManager;

    private final Camera camera;

    public WorldScene() {
        this.world = new World("/assets/levels/dungeon/DungeonLevel_Mapa.csv", "/assets/levels/dungeon/DungeonLevel_Mapa.csv");
        this.camera = new Camera();

        // TODO: Custom particle image
        this.particleManager = new ParticleManager(world, "/assets/textures/player/kevin_idle_00.png");

        this.player = new Player(10 * Game.tileSize, 10 * Game.tileSize,4, camera);
        this.wizard = new Wizard();

        world.addEntity(player);
        world.addEntity(wizard);
    }

    @Override
    public void update(double delta) {
        world.update(delta);
        particleManager.update(delta);
    }

    @Override
    public void draw(GraphicsContext gc) {
        world.draw(gc, camera);
    }

}
