package sprite;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import recursos.Sonidos;

public class TanqueProtagonista extends Sprite {

	private int dx;

	private int dy;
	private int vel = 2;
	private ArrayList<Misil> missiles;
	private int aninPos = 1;
	private ArrayList<Ladrillo> ladrillos;
	private ArrayList<SpriteAgua> agua;
	private ArrayList<LadrilloConcreto> concreto;
	private int vidas;
	private Sonidos sonidos;
	private boolean isProtegido = true;

	// 1 norte, 2 sur, 3, este, 4 oestes
	private int direccionActual;

	public TanqueProtagonista(int x, int y, ArrayList<Ladrillo> ladrillos, int vidas, Sonidos sonidos,
			ArrayList<SpriteAgua> agua, ArrayList<LadrilloConcreto> concreto) {
		super(x, y);

		this.ladrillos = ladrillos;
		this.concreto = concreto;
		this.agua = agua;
		this.setVidas(vidas);
		this.sonidos = sonidos;
		initCraft();
	}

	private void initCraft() {

		missiles = new ArrayList<Misil>();
		loadImage("src/img/tanquePPUp.png");
		getImageDimensions();
	}

	public void setDx(int dx) {
		this.dx = dx;
	}

	public void setDy(int dy) {
		this.dy = dy;
	}

	public boolean isProtegido() {
		return isProtegido;
	}

	public void setProtegido(boolean isProtegido) {
		this.isProtegido = isProtegido;
	}

	public void move() {
		if (!seEstrelloConAlgo(x, y)) {
			// if (direccionActual == 3 || direccionActual == 4)
			x += dx;
			// else if (direccionActual == 1 || direccionActual == 2)
			y += dy;
		} else {
			switch (direccionActual) {
			case 1:

				y += 1;
				break;
			case 2:
				y += -1;
				break;
			case 3:
				x += 1;
				break;
			case 4:

				x += -1;
				break;

			}
		}

		aninPos++;
		if (aninPos > 8) {
			aninPos = 1;
		}
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

		}

		return false;
	}

	public ArrayList<Misil> getMissiles() {
		return missiles;
	}

	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_SPACE) {
			fire();
		}

		if (key == KeyEvent.VK_LEFT) {
			direccionActual = 3;
			asignaAnimacion();
			dx = -vel;
		}

		if (key == KeyEvent.VK_RIGHT) {
			direccionActual = 4;
			asignaAnimacion();
			dx = vel;
		}

		if (key == KeyEvent.VK_UP) {
			direccionActual = 1;
			asignaAnimacion();
			dy = -vel;

		}

		if (key == KeyEvent.VK_DOWN) {
			direccionActual = 2;
			asignaAnimacion();
			dy = vel;

		}
	}

	public void fire() {

		if (missiles.size() < 2) {
			sonidos.reproducirDiparo();

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

	public void keyReleased(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			dx = 0;
		}

		if (key == KeyEvent.VK_RIGHT) {
			dx = 0;
		}

		if (key == KeyEvent.VK_UP) {
			dy = 0;
		}

		if (key == KeyEvent.VK_DOWN) {
			dy = 0;
		}
	}

	public void asignaAnimacion() {

		if (isProtegido) {
			if (direccionActual == 1) {
				loadImage("src/img/tanquePPUp.png");
			} else if (direccionActual == 2) {
				loadImage("src/img/tanquePPDown.png");
			} else if (direccionActual == 3) {
				loadImage("src/img/tanquePPLeft.png");
			} else if (direccionActual == 4) {
				loadImage("src/img/tanquePPRight.png");
			}
		} else {

			if (direccionActual == 1) {
				loadImage("src/img/tanquePUp.png");
			} else if (direccionActual == 2) {
				loadImage("src/img/tanquePDown.png");
			} else if (direccionActual == 3) {
				loadImage("src/img/tanquePLeft.png");
			} else if (direccionActual == 4) {
				loadImage("src/img/tanquePRight.png");
			}
		}
		
		getImageDimensions();
	}

	public int getDireccionActual() {
		return direccionActual;
	}

	public int getVidas() {
		return vidas;
	}

	public void setVidas(int vidas) {
		this.vidas = vidas;
	}

	public void setLocation(int initX, int initY) {
		x = initX;
		y = initY;
	}

}
