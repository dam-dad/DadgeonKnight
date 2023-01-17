package nx.engine.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Shape;
import nx.engine.Game;
import nx.engine.level.Level;

public abstract class Entity {
	
	public double posX, posY;
	public double width, height;
	protected Image image;
	private Level level;

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

	public void draw(GraphicsContext gc) {
		drawInternal(gc, 1.0);
	}

	public abstract Shape getCollisionShape();

	public boolean checkCollision(Entity entity) {
		return (getCollisionShape() != null && entity.getCollisionShape() != null && getCollisionShape().intersects(entity.getCollisionShape().getLayoutBounds()));
	}

	protected void drawInternal(GraphicsContext gc, double scale) {
		if (image != null)
			gc.drawImage(image, Game.SCREEN_CENTER_X - Game.camera.getX() + posX, Game.SCREEN_CENTER_Y - Game.camera.getY() + posY, Game.tileSize * scale, Game.tileSize * scale);
	}

	public void setImage(Image image) {
		this.image = image;
	}

	protected void move(double x, double y) {

	}
	
	public Image getImage() {
		return image;
	}

}
