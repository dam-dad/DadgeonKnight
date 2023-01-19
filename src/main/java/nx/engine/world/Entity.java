package nx.engine.world;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import nx.engine.Camera;
import nx.engine.Game;

public abstract class Entity {
	
	protected double posX, posY;
	protected double width, height;
	protected Image image;
	private World world;

	public Entity() {
		this(0, 0);
	}

	public Entity(double posX, double posY) {
		this(posX, posY, null);
	}

	public Entity(double posX, double posY, Image image) {
		this.posX = posX;
		this.posY = posY;
		this.image = image;
	}

	public abstract void update(double deltaTime);

	public void draw(GraphicsContext gc, Camera camera) {
		drawInternal(gc, camera, 1.0);
	}

	// TODO: Make collisions work
	protected void move(double x, double y) {

	}

	protected void drawInternal(GraphicsContext gc, Camera camera, double scale) {
		if (image != null)
			gc.drawImage(image, Game.SCREEN_CENTER_X - camera.getX() + posX, Game.SCREEN_CENTER_Y - camera.getY() + posY, Game.tileSize * scale, Game.tileSize * scale);
	}

	protected void setWorld(World world) {
		this.world = world;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public World getWorld() {
		return world;
	}

	public Image getImage() {
		return image;
	}

	public double getPosX() {
		return posX;
	}

	public double getPosY() {
		return posY;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

}
