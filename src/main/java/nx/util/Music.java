package nx.util;

import java.net.URISyntaxException;
import java.net.URL;

import javafx.animation.Transition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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

		player.setVolume(1);
		player.play();
		
		return this;

	}
	
	public Music setLoop(boolean a) {
		if(a)
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
	
	
	
	
}
