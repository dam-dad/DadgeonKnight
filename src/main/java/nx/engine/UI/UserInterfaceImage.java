package nx.engine.UI;

import javafx.scene.image.Image;
import nx.engine.Game;

public class UserInterfaceImage {
	
	public static UserInterfaceImage Dialog = new UserInterfaceImage(0, Game.screenheigth - 100, Game.screenWidth, 100,new Image("/assets/textures/ui/dialogbox.png"));
	
	private int posX;
	private int posY;
	
	private int With;
	private int height;
	
	private Image image;
	
	

	public UserInterfaceImage(int posX, int posY, int with, int height, Image image) {
		this.posX = posX;
		this.posY = posY;
		this.With = with;
		this.height = height;
		this.image = image;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getWith() {
		return With;
	}

	public void setWith(int with) {
		With = with;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
	
}
