package nx.engine.tile;

@Deprecated
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
