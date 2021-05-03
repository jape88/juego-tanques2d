package recursos;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class Sonidos {
	private Clip sonidoBomb;
	private Clip sonidoExplocion;
	private Clip sonidoIntro;
	private Clip sonidoDisparo;
	private Clip sonidoGameOver;
	private Clip sonidoWin;

	public Sonidos() {
		sonidoBomb=cargarSonido("src/sounds/bomb.wav");
		sonidoExplocion = cargarSonido("src/sounds/Explosion3.wav");
		sonidoIntro = cargarSonido("src/sounds/intro2.wav");
		sonidoDisparo = cargarSonido("src/sounds/disparo.wav");
		sonidoGameOver = cargarSonido("src/sounds/fail.wav");
		sonidoWin = cargarSonido("src/sounds/win.wav");
	}

	private static Clip cargarSonido(final String ruta) {
		Clip clip = null;

		try {

			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(ruta).getAbsoluteFile());
			clip = AudioSystem.getClip();

			//InputStream is = ClassLoader.class.getResourceAsStream(ruta);
			//AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
			//DataLine.Info info = new DataLine.Info(Clip.class, ais.getFormat());
			//clip = (Clip) AudioSystem.getLine(info);
			clip.open(audioInputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return clip;
	}

	public void reproducirSonido(Clip sonido) {
		sonido.stop();
		sonido.flush();
		sonido.setMicrosecondPosition(0);
		sonido.start();
	}

	public void reproducirIntro() {
		reproducirSonido(sonidoIntro);
	}
	
	public void reproducirBomba() {
		reproducirSonido(sonidoBomb);
	}

	public void reproducirExplo() {
		reproducirSonido(sonidoExplocion);
	}

	public void reproducirDiparo() {
		reproducirSonido(sonidoDisparo);
	}

	public void reproducirFail() {
		reproducirSonido(sonidoGameOver);
	}

	public void reproducirWin() {
		reproducirSonido(sonidoWin);
	}
}
