package nx.engine.UI;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import nx.engine.Game;
import nx.engine.TextAnimation;

public class Dialog {
	
	private String title;
	
	private UserInterfaceImage bg;
	private TextAnimation text;
	
	public Dialog(String title,String texts,UserInterfaceImage bg) {
		try {
			this.title = title;
			text = new TextAnimation(texts);
			text.play();
			this.bg = bg;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(double timeDifference) {
		text.update(timeDifference);
		
	}
	public void draw(GraphicsContext gc) {
		gc.drawImage(bg.getImage(), bg.getPosX(), bg.getPosY(),bg.getWith(),bg.getHeight());
		
		gc.setFont(Game.font);
		gc.setFill(Color.FLORALWHITE);
		gc.fillText(title, bg.getPosX() + 50, bg.getPosY() + 25);
		
		gc.fillText(text.getCurrentFrame(), 50, bg.getPosY() + bg.getHeight()/2);
	}

}
