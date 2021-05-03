package sprite;

public class SpriteAgua extends Sprite {

	public SpriteAgua(int x, int y) {
		super(x, y);
		iniciarAgua();
	}

	public void iniciarAgua() {

		loadImage("src/img/agua.png");
		getImageDimensions();

	}

}
