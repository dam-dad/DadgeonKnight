package nx.engine.tile;


import java.util.Set;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import nx.engine.Game;
import nx.engine.entity.Entity;

public class Tile {

	private final int id;
	private final boolean solid;
	
	public Tile(int id) {
		this(id, false);
	}

	public Tile(int id, boolean solid) {
		this.id = id;
		this.solid = solid;
	}

	public int getId() {
		return id;
	}

	public boolean isSolid() {
		return solid;
	}

}
