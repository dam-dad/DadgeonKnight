package nx.engine.tile;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import nx.engine.Game;

/**
 * Represents a tile
 */
public class Tile {
	
	public Tile parent;
	
	public boolean start,end,open,checked;
	
	public int col;
	public int row;
	
	public int gCost,hCost,fCost;

	private int id;
	private boolean solid;

	/**
	 * Constructor
	 * @param col Tile position X
	 * @param row Tile position Y
	 * @param solid True if it is solid, false if not
	 */
	public Tile(int col,int row,boolean solid) {
		this.col = col;
		this.row = row;
		this.solid = solid;
	}

	/**
	 * Check collision with a shape
	 * @param entity Shape to check colllision with
	 * @param posX Tile position X
	 * @param posY Tile position Y
	 * @return True if it collides, false if not
	 */
	public static boolean checkCollision(Shape entity, int posX, int posY) {
		return new Rectangle(posX * Game.tileSize, posY * Game.tileSize, Game.tileSize, Game.tileSize).intersects(entity.getLayoutBounds());
	}

	public void setStart() {
		start = true;
	}

	public void setEnd() {
		end = true;
	}

	public void setAsOpen() {
		open = true;
	}

	public void setAsChecked() {
		checked = true;
	}

	public boolean isSolid() {
		return solid;
	}
	
	@Override
	public String toString() {
		return "[" + col + "," + row + "," + isSolid() + "]";
	}

}
