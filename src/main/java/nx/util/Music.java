package nx.util;

import java.net.URISyntaxException;
import java.net.URL;

import javafx.animation.Transition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * Clase que controla la musica
 *
 */
public class Music {
	
	private MediaPlayer player;
	private Media media;
	
	/**
	 * Constructor que recibe el nombre del archivo de musica
	 * @param el fichero de musica
	 */
	public Music(String file) {
		try { 
			URL path = getClass().getResource("/assets/sound/" + file);
			this.media = new Media(path.toURI().toString());
			player = new MediaPlayer(media);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
		

	}
	
	/**
	 * Método para iniciar musica
	 */
	public Music play() {
		player.play();
		
		return this;

	}
	
	public Music setLoop(boolean loop) {
		if(loop)
			player.setCycleCount(Integer.MAX_VALUE);
		return this;
	}
	
	/**
	 * Método para pausar la música
	 */
	public void pause() {
		try {
			player.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Music setVolume(double i) {
		player.setVolume(i);
		return this;
	}

	public MediaPlayer getPlayer() {
		return player;
	}

	public void setPlayer(MediaPlayer player) {
		this.player = player;
	}

	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}
	
	public void fadeOut(double fadeTime) {
		MediaPlayer player = this.player;
	     Task<Void> task = new Task<Void>() {
			@Override protected Void call() throws Exception {
	 			long lastTime = System.nanoTime();
				double time = 0.0;
				double delta = 0;
				double volume = player.getVolume();
				double initialVolume = volume;
				while (volume > 0) {
					long currentTime = System.nanoTime();
					double deltaTime = (currentTime - lastTime)/1000000000.0;
					delta += (currentTime - lastTime) / (1000000000.0 / 60);
					
					if(delta >= 1) {
						volume = (initialVolume - (time / fadeTime));
						player.setVolume(volume);
						
						delta--;
					}

					
					time += deltaTime;
					lastTime = currentTime;
				}
				player.setVolume(0);
				player.stop();
	            return null;
	         }
	     };
	     if(!task.isRunning())
	    	 new Thread(task).start();
	}
	public void fadeIn(double min,double max, double fadeTime) {
		MediaPlayer player = this.player;
	     Task<Void> task = new Task<Void>() {
			@Override protected Void call() throws Exception {
	 			long lastTime = System.nanoTime();
				double time = 0.0;
				double delta = 0;
				double volume = 0;
				player.setVolume(min);
				player.play();
				while (volume < max) {
					long currentTime = System.nanoTime();
					double deltaTime = (currentTime - lastTime)/1000000000.0;
					delta += (currentTime - lastTime) / (1000000000.0 / 60);
					
					if(delta >= 1) {
						volume = (time / fadeTime) * max;
						player.setVolume(volume);
						
						delta--;
					}

					
					time += deltaTime;
					lastTime = currentTime;
				}
	             return null;
	         }
	     };
	     if(!task.isRunning())
	    	 new Thread(task).start();
	}
}
