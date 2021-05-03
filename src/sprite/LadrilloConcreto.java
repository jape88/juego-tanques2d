package sprite;

public class LadrilloConcreto extends Sprite {

	public LadrilloConcreto(int x, int y) {
		super(x, y);
		iniciarLadrillo();
	}
	
	public void iniciarLadrillo() {

		loadImage("src/img/concreto1.jpg");
		getImageDimensions();

	}

}
