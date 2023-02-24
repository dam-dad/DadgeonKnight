package nx.engine.UI;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import nx.engine.Game;
import nx.engine.TextAnimation;

/**
 * Represents a dialog
 */
public class Dialog implements UI {
	
	private String title;
	
	private UserInterfaceImage bg;
	private TextAnimation text;
	
	private boolean isOn = false;

	/**
	 * Constructor
	 * @param title Dialog title
	 * @param texts Texts to show
	 * @param bg Interface image
	 */
	public Dialog(String title, String texts, UserInterfaceImage bg) {
		try {
			this.title = title;
			text = new TextAnimation(texts);
			this.bg = bg;
			text.setSpeed(3);
			text.play();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updates the dialog
	 * @param timeDifference Frame delta time
	 */
	@Override
	public void update(double timeDifference) {
		if(!isOn)
			return;
		
		text.update(timeDifference);
		
	}

	/**
	 * Draws the dialog
	 * @param gc GraphicsContext to draw on
	 */
	@Override
	public void draw(GraphicsContext gc) {
		if(!isOn)
			return;
		
		gc.drawImage(bg.getImage(), bg.getPosX(), bg.getPosY(),bg.getWith(),bg.getHeight());
		
		gc.setFont(Game.font);
		gc.setFill(Color.FLORALWHITE);
		gc.fillText(title, bg.getPosX() + 50, bg.getPosY() + 25);
		
		gc.fillText(text.getCurrentFrame(), 50, bg.getPosY() + bg.getHeight()/2);
	}
	
	public boolean hasFinish() {
		return text.hasEnded();
	}
	
	public boolean isOn() {
		return this.isOn;
	}
	
	public void play() {
		this.isOn = true;
	}
	public void pause() {
		this.isOn = false;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public UserInterfaceImage getBg() {
		return bg;
	}

	public void setBg(UserInterfaceImage bg) {
		this.bg = bg;
	}

	public TextAnimation getText() {
		return text;
	}

	public void setText(TextAnimation text) {
		this.text = text;
	}

}
