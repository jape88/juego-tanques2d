package sprite;

public class Aguila extends Sprite {

	public Aguila(int x, int y) {
		super(x, y);
		iniciarAguila();
	}
	
	public void iniciarAguila() {

		loadImage("src/img/aguila.png");
		getImageDimensions();

	}

}
