package sprite;

public class SpritePasto extends Sprite{

	public SpritePasto(int x, int y) {
		super(x, y);
		iniciarPasto();
	}
	
	public void iniciarPasto() {

		loadImage("src/img/pasto.png");
		getImageDimensions();

	}

}
