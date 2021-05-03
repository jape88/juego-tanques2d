package sprite;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class TanqueJefe extends Sprite implements Runnable{

	private final int BOARD_WIDTH = 890;
	private final int BOARD_HEIGTH = 543;
	private int vel = 2;
	private ArrayList<MisilJefe> missiles;
	private String rutaImgUp, rutaimgDown, rutaLeft, rutaRight;
	private int vidas = 10;

	private ArrayList<Ladrillo> ladrillos;
	private ArrayList<SpriteAgua> agua;
	private ArrayList<LadrilloConcreto> concreto;
	private Random al;

	public TanqueJefe(int x, int y, ArrayList<Ladrillo> ladrillos, ArrayList<SpriteAgua> agua,
			ArrayList<LadrilloConcreto> concreto) {
		super(x, y);
		missiles = new ArrayList<MisilJefe>();
		al = new Random();
		this.ladrillos = ladrillos;
		this.agua = agua;
		this.concreto = concreto;
		initTanque();
	}

	private void configurarTanque() {

		rutaimgDown = "src/img/tanqueEnemigoJefeDown.png";
		rutaImgUp = "src/img/tanqueEnemigoJefeUp.png";
		rutaLeft = "src/img/tanqueEnemigoJefeLeft.png";
		rutaRight = "src/img/tanqueEnemigoJefeRight.png";

	}

	private void initTanque() {
		configurarTanque();
		loadImage(rutaimgDown);
		getImageDimensions();
	}
	
	public int getVidas() {
		return vidas;
	}

	public void setVidas(int vidas) {
		this.vidas = vidas;
	}
	
	public ArrayList<MisilJefe> getMissiles() {
		return missiles;
	}

	public void setMissiles(ArrayList<MisilJefe> missiles) {
		this.missiles = missiles;
	}

	public boolean seEstrelloConAlgo(int x, int y) {
		Rectangle tanque = this.getBounds();
		try {
			for (Ladrillo lad : ladrillos) {
				Rectangle ladRec = lad.getBounds();

				if (tanque.intersects(ladRec)) {
					return true;
				}
			}
			for (SpriteAgua agu : agua) {
				Rectangle aguRec = agu.getBounds();

				if (tanque.intersects(aguRec)) {
					return true;
				}
			}
			for (LadrilloConcreto conc : concreto) {
				Rectangle concRec = conc.getBounds();

				if (tanque.intersects(concRec)) {
					return true;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return false;
	}

	public int moverUp(int pasos) throws InterruptedException {
		if ((x >= 0)) {
			x -= vel;
		} else {
			pasos = 0;
		}
		return pasos;

	}

	public int moverDown(int pasos) throws InterruptedException {
		if (x <= BOARD_WIDTH - width) {
			x += vel;
		} else {
			pasos = 0;
		}
		return pasos;
	}

	public int moverLetf(int pasos) throws InterruptedException {
		if ((y >= 0)) {
			y -= vel;
		} else {
			pasos = 0;
		}
		return pasos;
	}

	public int moverRight(int pasos) throws InterruptedException {
		if (y <= BOARD_HEIGTH - height) {
			y += vel;
		} else {
			pasos = 0;
		}
		return pasos;
	}

	public void move() throws InterruptedException {
		int pos;// direccion 1 este, 2 oeste, 3, , 4 oestes

		int dispara = al.nextInt(4);

		int pasos = al.nextInt(300) + 1;

		pos = al.nextInt(4) + 1;
		asignaAnimacion(pos);
		if (pos == 3) {
			for (int i = 0; i < pasos; i++) {
				if (!seEstrelloConAlgo(x, y)) {
					pasos = moverUp(pasos);
					Thread.sleep(10);
				} else {
					x += vel;
					break;
				}

			}
		} else if (pos == 4) {
			for (int i = 0; i < pasos; i++) {
				if (!seEstrelloConAlgo(x, y)) {
					pasos = moverDown(pasos);
					Thread.sleep(10);
				} else {
					x -= vel;
					break;
				}

			}
		} else if (pos == 1) {
			for (int i = 0; i < pasos; i++) {
				if (!seEstrelloConAlgo(x, y)) {
					pasos = moverLetf(pasos);
					Thread.sleep(10);
				} else {
					y += vel;
					break;
				}
			}
		} else if (pos == 2) {
			for (int i = 0; i < pasos; i++) {
				if (!seEstrelloConAlgo(x, y)) {
					pasos = moverRight(pasos);
					Thread.sleep(10);
				} else {
					y -= vel;
					break;
				}
			}
		}
		if (dispara != 0) {
			fire(pos);
		}

	}

	public void fire(int direccionActual) {

		if (missiles.size() < 2) {
			if (direccionActual == 1) {
				missiles.add(new MisilJefe(x + (width / 2), y, direccionActual));
			} else if (direccionActual == 2) {
				missiles.add(new MisilJefe(x + width / 2, y + height, direccionActual));
			} else if (direccionActual == 3) {
				missiles.add(new MisilJefe(x, y + height / 2, direccionActual));
			} else if (direccionActual == 4) {
				missiles.add(new MisilJefe(x + width, y + height / 2, direccionActual));
			}
		}

	}

	public void asignaAnimacion(int direccionActual) {

		if (direccionActual == 1) {
			loadImage(rutaImgUp);
			getImageDimensions();
		} else if (direccionActual == 2) {
			loadImage(rutaimgDown);
			getImageDimensions();
		} else if (direccionActual == 3) {
			loadImage(rutaLeft);
			getImageDimensions();
		} else if (direccionActual == 4) {
			loadImage(rutaRight);
			getImageDimensions();
		}

	}

	@Override
	public void run() {
		while (true) {
			try {
				move();
				// Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void setLadrillos(ArrayList<Ladrillo> ladrillos) {
		this.ladrillos = ladrillos;
	}

	public void setLocation(int i, int j) {
		x=i;
		y=j;
	}
}
