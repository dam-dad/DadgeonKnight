package nx.engine.scenes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import nx.engine.Camera;
import nx.engine.Game;
import nx.engine.particles.ParticleManager;
import nx.engine.world.World;
import nx.engine.world.entities.Orc;
import nx.engine.world.entities.Player;
import nx.engine.world.entities.Wizard;

public class WorldScene implements Scene {

    private final World world;

    private final Player player;
    private final Wizard wizard;
    private final Orc orco;

    private final ParticleManager particleManager;

    private final Camera camera;

    public WorldScene() {
        this.world = new World("/assets/levels/dungeon/DungeonLevel_Mapa.csv", "/assets/levels/dungeon/DungeonLevel_Collitions.csv");
        this.camera = new Camera();

        // TODO: Custom particle image
        this.particleManager = new ParticleManager(world, "/assets/textures/bola_du_fogo.gif");

        this.player = new Player(10, 10,4, camera);
        this.wizard = new Wizard(10,12);
        this.orco = new Orc(14, 10, 0.2,1.5);

        world.addEntity(player);
//        world.addEntity(wizard);
        world.addEntity(orco);
    }

    @Override
    public void update(double delta) {
        world.update(delta);
        particleManager.update(delta);
    }

    Font font = new Font(50);

    @Override
    public void draw(GraphicsContext gc) {
        world.draw(gc, camera);

        gc.setFont(font);
        gc.setFill(Color.WHITESMOKE);
        gc.fillText(String.format("Vida: %d", player.getHealth()),10, Game.screenheigth - 10);
    }

    public World getWorld() {
        return world;
    }

    public Player getPlayer() {
        return player;
    }

}
