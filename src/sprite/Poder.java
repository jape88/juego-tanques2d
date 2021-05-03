package sprite;

public class Poder extends Sprite {

	// El tipo de poder, de momento 1 para ganar una vida extra;
	private int tipo;

	public Poder(int x, int y, int tipo) {
		super(x, y);
		this.tipo = tipo;
		initPoder();
	}

	private void initPoder() {
		if (tipo == 1) {
			loadImage("src/img/TankVida.png");
		} else if (tipo == 2) {
			loadImage("src/img/bomba.png");
		}
		getImageDimensions();
	}

	public int getTipo() {
		return tipo;
	}

}
