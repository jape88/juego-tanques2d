package sprite;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class TanqueEnemigo extends Sprite implements Runnable {

	private final int BOARD_WIDTH = 890;
	private final int BOARD_HEIGTH = 543;
	private int vel = 2;
	private ArrayList<Misil> missiles;
	private int tipo;
	private String rutaImgUp, rutaimgDown, rutaLeft, rutaRight;
	private int vidas;

	

	private ArrayList<Ladrillo> ladrillos;
	private ArrayList<SpriteAgua> agua;
	private ArrayList<LadrilloConcreto> concreto;
	private Random al;

	public TanqueEnemigo(int x, int y, ArrayList<Ladrillo> ladrillos, ArrayList<SpriteAgua> agua,
			ArrayList<LadrilloConcreto> concreto) {
		super(x, y);
		missiles = new ArrayList<Misil>();
		al = new Random();
		this.ladrillos = ladrillos;
		this.agua = agua;
		this.concreto = concreto;
		this.tipo = al.nextInt(3) + 1;
		initTanque();

	}

	private void configurarTanque() {
		if (tipo == 1) {
			rutaimgDown = "src/img/tanqueEnemigo1Down.png";
			rutaImgUp = "src/img/tanqueEnemigo1Up.png";
			rutaLeft = "src/img/tanqueEnemigo1Left.png";
			rutaRight = "src/img/tanqueEnemigo1Right.png";
			this.vidas = 1;
		} else if (tipo == 2) {
			rutaimgDown = "src/img/tanqueEnemigo2Down.png";
			rutaImgUp = "src/img/tanqueEnemigo2Up.png";
			rutaLeft = "src/img/tanqueEnemigo2Left.png";
			rutaRight = "src/img/tanqueEnemigo2Right.png";
			this.vidas = 1;
			vel = 3;
		} else if (tipo == 3) {
			vidas = 2;
			rutaimgDown = "src/img/tanqueEnemigo3Down.png";
			rutaImgUp = "src/img/tanqueEnemigo3Up.png";
			rutaLeft = "src/img/tanqueEnemigo3Left.png";
			rutaRight = "src/img/tanqueEnemigo3Right.png";
		}

	}

	private void initTanque() {
		configurarTanque();
		loadImage(rutaimgDown);
		getImageDimensions();
	}

	public ArrayList<Misil> getMissiles() {
		return missiles;
	}

	public int getTipo() {
		return tipo;
	}
	
	public int getVidas() {
		return vidas;
	}

	public void setVidas(int vidas) {
		this.vidas = vidas;
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
				missiles.add(new Misil(x + (width / 2), y, direccionActual));
			} else if (direccionActual == 2) {
				missiles.add(new Misil(x + width / 2, y + height, direccionActual));
			} else if (direccionActual == 3) {
				missiles.add(new Misil(x, y + height / 2, direccionActual));
			} else if (direccionActual == 4) {
				missiles.add(new Misil(x + width, y + height / 2, direccionActual));
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
}
