package sprite;

public class Ladrillo extends Sprite {

	public Ladrillo(int x, int y) {
		super(x, y);
		iniciarLadrillo();
	}

	public void iniciarLadrillo() {

		loadImage("src/img/ladrillo.png");
		getImageDimensions();

	}
	
	

}
