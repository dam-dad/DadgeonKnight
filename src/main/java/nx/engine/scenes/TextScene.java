package nx.engine.scenes;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import nx.engine.Animation;
import nx.engine.Game;
import nx.engine.TextAnimation;
import nx.game.App;

public class TextScene implements Scene {
	
	private final TextAnimation animation;
	
	private double time = 0;
	private double fadeTime = 2;
	
	private boolean ended = false;
	
	public TextScene(List<String> texts) {
		animation = new TextAnimation(texts);
		animation.play();
	}
	public TextScene(String texts) throws URISyntaxException, Exception {
		animation = new TextAnimation(texts);
		animation.play();
	}

	@Override
	public void update(double delta) {
		if(animation.hasEnded()) {
			time += delta;
			return;
		}

		animation.setSpeed(1);
		animation.play();
		
		Set<MouseButton> activeButtons = Game.inputHandler.getActiveButtons();
		if(activeButtons.contains(MouseButton.PRIMARY))
			animation.pause();
		else if(activeButtons.contains(MouseButton.SECONDARY))
			animation.setSpeed(5);

		
		animation.update(delta);
	}
	


	@Override
	public void draw(GraphicsContext gc) {
		if(animation.hasEnded()) {
			double alpha = (1.0 - (time / fadeTime));
			gc.setGlobalAlpha(alpha);
			if(alpha <= 0)
				this.ended = true;
		}
		gc.setFont(Game.font);
		gc.setFill(Color.WHITESMOKE);
		gc.fillText(animation.getCurrentFrame(), Game.SCREEN_CENTER_X - ((animation.getCurrentLine().length()/2) * Game.font.getSize()), Game.SCREEN_CENTER_Y + Game.font.getSize());
		gc.setGlobalAlpha(1);
	}
	
	public TextAnimation getAnimation() {
		return this.animation;
	}
	
	public boolean hasEnded() {
		return this.ended;
	}

}
