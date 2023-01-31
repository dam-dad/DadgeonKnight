package nx.util;

import java.util.ArrayList;
import java.util.List;

public class SoundMixer {
	
	public static double GENERAL_VOLUME = 1;
	public static double GAME_VOLUME = 1;
	public static double MUSIC_VOLUME = 1;
	
	
	private Music music;
	private List<Music> gameSounds;
	
	public SoundMixer() {
		gameSounds = new ArrayList<Music>();
	}

	public Music getMusic() {
		return music;
	}

	public Music setMusic(String uri) {
		this.music = new Music(uri);
		return music;
	}

	public List<Music> getGameSounds() {
		return gameSounds;
	}
	
	public Music addGameSound(String uri) {
		Music music = new Music(uri);
		this.gameSounds.add(music);
		music.getPlayer().setOnEndOfMedia(() -> removeMusic(music));
		return music;
		
	}
	
	public void removeMusic(Music m) {
		this.gameSounds.remove(m);
	}
	
	
}
