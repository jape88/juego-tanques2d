package sprite;

public class MisilJefe extends Sprite{

	private final int BOARD_WIDTH = 890;
	private final int BOARD_HEIGTH = 543;
	private final int MISSILE_SPEED = 5;
	private int direccion;

	public MisilJefe(int x, int y, int direccion) {
		super(x, y);
		this.direccion = direccion;
		initMissile();
	}

	private void initMissile() {

		if (direccion == 1) {
			loadImage("src/img/missileJefeUp.png");
		} else if (direccion == 2)
			loadImage("src/img/missileJefeDown.png");
		else if(direccion == 3){
			loadImage("src/img/missileJefeLeft.png");
		}else if(direccion == 4)
			loadImage("src/img/missileJefeRigth.png");
		getImageDimensions();
	}

	public void move() {

		if (direccion == 1) {
			y -= MISSILE_SPEED;
		} else if (direccion == 2) {
			y += MISSILE_SPEED;
		} else if (direccion == 3) {
			x -= MISSILE_SPEED;
		} else if (direccion == 4) {
			x += MISSILE_SPEED;
		}

		if (x > BOARD_WIDTH || y > BOARD_HEIGTH || x < 0 || y < 0) {
			vis = false;
		}
	}
	
}
