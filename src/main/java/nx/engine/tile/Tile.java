package nx.engine.tile;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import nx.engine.Game;
import nx.engine.world.entities.Entity;


public class Tile {
	
	public Tile parent;
	
	public boolean start,end,open,checked;
	
	public int col;
	public int row;
	
	public int gCost,hCost,fCost;

	private int id;
	private boolean solid;
	
	public Tile(int col,int row,boolean solid) {
		this.col = col;
		this.row = row;
		this.solid = solid;
	}

	public Tile(int id, boolean solid) {
		this.id = id;
		this.solid = solid;
	}
	


	public static boolean checkCollision(Entity entity, int posX, int posY) {
		if (entity.getCollisionShape() == null)
			return false;
		
		boolean collide = new Rectangle(posX * Game.tileSize, posY * Game.tileSize, Game.tileSize, Game.tileSize).intersects(entity.getCollisionShape().getLayoutBounds());
		return collide;
	}
	public static boolean checkCollision(Shape entity, int posX, int posY) {
		boolean collide = new Rectangle(posX * Game.tileSize, posY * Game.tileSize, Game.tileSize, Game.tileSize).intersects(entity.getLayoutBounds());
		return collide;
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
	public int getId() {
		return id;
	}

	public boolean isSolid() {
		return solid;
	}
	
	@Override
	public String toString() {
		return "[" + col + "," + row + "," + isSolid() + "]";
	}

}
