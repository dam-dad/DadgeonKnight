package nx.documentation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nx.engine.scenes.FinalScene;

public class JuegosDataProvider {

	public static List<Partida> getMisJuegos() {

		List<Partida> juegos = new ArrayList<>();

		Partida juego = new Partida();
		juego.setNombre(FinalScene.username);
		juego.setTiempo(FinalScene.time);
		juegos.add(juego);

		return juegos;
	}

}
