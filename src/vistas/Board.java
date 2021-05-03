package vistas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

import hilos.HiloTiempoBonus;
import modelos.JugadorDao;
import modelos.JugadorVo;
import recursos.Mapa;
import recursos.Sonidos;

import sprite.Aguila;
import sprite.TanqueEnemigo;
import sprite.TanqueJefe;
import sprite.TanqueProtagonista;
import sprite.Ladrillo;
import sprite.LadrilloConcreto;
import sprite.Misil;
import sprite.MisilJefe;
import sprite.Poder;
import sprite.SpriteAgua;
import sprite.SpritePasto;

public class Board extends JPanel implements ActionListener {

	private final int ICRAFT_X = 550;
	private final int ICRAFT_Y = 498;
	private final int B_WIDTH = 890;
	private final int B_HEIGHT = 600;
	private final int MAPA_FINAL = 5;
	private final int DELAY = 10;
	private int tiempoTrancurrido;

	private JButton btnContinuar;
	private JButton btnSalir;

	private boolean ingame;
	private boolean gano;
	private Thread hiloBonus;
	private int multiplicador;

	private Timer timer;
	private TanqueProtagonista tankPro;
	private ArrayList<TanqueEnemigo> tanquesEnemigos;

	private ArrayList<Ladrillo> ladrillos;
	private ArrayList<LadrilloConcreto> concreto;
	private ArrayList<SpriteAgua> agua;
	private ArrayList<SpritePasto> pasto;
	private Aguila miAguila;
	private Poder unPoder;
	private TanqueJefe boss;
	private Thread hiloJefe;
	// variables para el poder
	int px;
	int py;
	int tipo;

	// Variables de configuracion
	private int mapa;
	private int numTanques;
	private int numTanquesIniciales;
	private int numVidas;
	private int bonificacionDeNivel;
	private String dificultad;
	private HiloTiempoBonus hilo;
	private int cantidad = 0;

	private String exlocionImagen = "src/img/explocion.png";
	private Sonidos sonidos;
	private JugadorDao miJugador;
	private JugadorVo usuario;

	private int puntaje = 0;

	private String[] mesajesPerdio = { "¿Sera mejor que te dediques a otra cosa?", "No seas chavo!!!",
			"Existen deportes extremos como el domino!!!", "Sigue intentando, pero aguantaras mucha hambre!!!" };

	private final int[][] posEnemigPrincipiate = { { 30, 30 }, { 445, 30 }, { 830, 30 } };
	private final int[][] posEnemigMedio = { { 30, 30 }, { 300, 30 }, { 570, 30 }, { 830, 30 } };
	private final int[][] posEnemigAvanzado = { { 30, 30 }, { 300, 30 }, { 445, 30 }, { 570, 30 }, { 830, 30 } };

	private int[][] posEnemig;

	private Mapa miMapa;
	private int[][] posBlocks;
	private int[][] posConcreto;
	private int[][] posAgua;
	private int[][] posPasto;

	private VentanaJuego ventana;

	private int imgFondo;
	private Random al = new Random();

	public Board(int numTanques, int bonificacionDeNivel, Sonidos sonidos, VentanaJuego ventana, JugadorDao miJugador,
			JugadorVo usuario) {

		this.usuario = usuario;
		this.puntaje = usuario.getPuntaje();
		this.numTanques = numTanques;
		this.numTanquesIniciales = numTanques;
		this.numVidas = usuario.getVidas();
		this.bonificacionDeNivel = bonificacionDeNivel;
		this.sonidos = sonidos;
		this.mapa = usuario.getMapa();
		this.dificultad = usuario.getDificultad();
		this.miJugador = miJugador;
		this.ventana = ventana;

		imgFondo = al.nextInt(6) + 1;
		miMapa = new Mapa(this.mapa);

		posBlocks = miMapa.getPosBlocks();
		posConcreto = miMapa.getPosConcreto();
		posAgua = miMapa.getPosAgua();
		posPasto = miMapa.getPosPasto();
		seleccionarPosEnemig();
		initBoard();

		setLayout(null);

	}

	public void seleccionarPosEnemig() {
		if (dificultad.equals("PRINCIPIANTE")) {
			posEnemig = posEnemigPrincipiate;
		} else if (dificultad.equals("INTERMEDIO")) {
			posEnemig = posEnemigMedio;
		} else if (dificultad.equals("AVANZADO")) {
			posEnemig = posEnemigAvanzado;
		}
	}

	public void inBonus() {
		hiloBonus.stop();
		multiplicador++;
		HiloTiempoBonus hilo = new HiloTiempoBonus(multiplicador, this);
		hiloBonus = new Thread(hilo);
		hiloBonus.start();
	}

	private void initBoard() {

		addKeyListener(new TAdapter());
		setFocusable(true);

		setDoubleBuffered(true);

		ingame = true;
		gano = false;
		multiplicador = 1;
		hilo = new HiloTiempoBonus(multiplicador, this);
		hiloBonus = new Thread(hilo);

		initBlocks();
		initConcreto();
		initAgua();
		initPasto();
		setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

		tankPro = new TanqueProtagonista(ICRAFT_X, ICRAFT_Y, ladrillos, numVidas, sonidos, agua, concreto);
		miAguila = new Aguila(427, 516);

		if (mapa == MAPA_FINAL) {
			boss = new TanqueJefe(30, 30, ladrillos, agua, concreto);
			int vidaBoss;
			if (dificultad.equals("PRINCIPIANTE"))
				vidaBoss = 40;
			else if (dificultad.equals("AVANZADO"))
				vidaBoss = 70;
			else
				vidaBoss = 100;
			boss.setVidas(vidaBoss);
		}

		initTanques();
		timer = new Timer(DELAY, this);
		timer.start();
	}

	private void initBlocks() {
		ladrillos = new ArrayList<>();
		for (int[] p : posBlocks) {
			ladrillos.add(new Ladrillo(p[0], p[1]));
		}
	}

	private void initConcreto() {
		concreto = new ArrayList<>();
		for (int[] p : posConcreto) {
			concreto.add(new LadrilloConcreto(p[0], p[1]));
		}
	}

	private void initAgua() {
		agua = new ArrayList<>();
		for (int[] p : posAgua) {
			agua.add(new SpriteAgua(p[0], p[1]));
		}
	}

	private void initPasto() {
		pasto = new ArrayList<>();
		for (int[] p : posPasto) {
			pasto.add(new SpritePasto(p[0], p[1]));
		}
	}

	public void initTanques() {
		tanquesEnemigos = new ArrayList<>();

		for (int[] p : posEnemig) {
			tanquesEnemigos.add(new TanqueEnemigo(p[0], p[1], ladrillos, agua, concreto));
			numTanques--;

		}

		for (int i = 0; i < tanquesEnemigos.size(); i++) {

			TanqueEnemigo a = tanquesEnemigos.get(i);
			if (a.isVisible()) {
				Thread alienMove = new Thread(a);
				alienMove.start();
			}
		}

		if (boss != null) {
			hiloJefe = new Thread(boss);
			hiloJefe.start();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (ingame) {
			doDrawing(g);
		} else {
			drawGameOver(g, gano, cantidad);
			cantidad++;
		}

		Toolkit.getDefaultToolkit().sync();
	}

	private void doDrawing(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		Image image = new ImageIcon(getClass().getResource("/img/fondo" + imgFondo + ".jpg")).getImage();
		g2d.drawImage(image, 0, 0, getWidth(), getHeight(), this);

		g2d.drawImage(tankPro.getImage(), tankPro.getX(), tankPro.getY(), this);
		if (boss != null && boss.isVisible())
			g2d.drawImage(boss.getImage(), boss.getX(), boss.getY(), this);

		if (unPoder != null && unPoder.isVisible()) {
			g2d.drawImage(unPoder.getImage(), unPoder.getX(), unPoder.getY(), this);
		}

		for (Ladrillo lad : ladrillos) {
			g2d.drawImage(lad.getImage(), lad.getX(), lad.getY(), this);
		}

		for (LadrilloConcreto concre : concreto) {
			g2d.drawImage(concre.getImage(), concre.getX(), concre.getY(), this);
		}

		for (SpriteAgua aguaIm : agua) {
			g2d.drawImage(aguaIm.getImage(), aguaIm.getX(), aguaIm.getY(), this);
		}

		for (SpritePasto pastoIm : pasto) {
			g2d.drawImage(pastoIm.getImage(), pastoIm.getX(), pastoIm.getY(), this);
		}

		if (miAguila.isVisible()) {
			g2d.drawImage(miAguila.getImage(), miAguila.getX(), miAguila.getY(), this);
		}

		ArrayList<Misil> ms = tankPro.getMissiles();
		for (Object m1 : ms) {
			Misil m = (Misil) m1;
			// reproducirDiparo();
			g2d.drawImage(m.getImage(), m.getX(), m.getY(), this);
		}

		if (boss != null && boss.isVisible()) {
			ArrayList<MisilJefe> msJ = boss.getMissiles();
			for (Object m1 : msJ) {
				MisilJefe m = (MisilJefe) m1;
				// reproducirDiparo();
				g2d.drawImage(m.getImage(), m.getX(), m.getY(), this);
			}
		}

		for (TanqueEnemigo tanqueEnemin : tanquesEnemigos) {
			ArrayList<Misil> msEne = tanqueEnemin.getMissiles();
			for (Object m1 : msEne) {
				Misil m = (Misil) m1;
				// reproducirDiparo();
				g2d.drawImage(m.getImage(), m.getX(), m.getY(), this);
			}
		}

		for (TanqueEnemigo a : tanquesEnemigos) {
			g2d.drawImage(a.getImage(), a.getX(), a.getY(), this);
		}

		drawEstadisticas(g2d);
	}

	private void drawEstadisticas(Graphics2D g) {
		Font small = new Font("Century Gothic", Font.BOLD, 18);

		g.setColor(Color.BLACK);
		g.setFont(small);

		String vidas = "Vidas Restantes: " + tankPro.getVidas();

		String tanques = "Tanques restantes: " + numTanques;
		String puntuacion = "Puntuación: " + puntaje;
		g.drawString(vidas, 5, 15);
		g.drawString(tanques, 5, 38);
		g.drawString(puntuacion, 5, 61);

		if (boss != null && boss.isVisible()) {
			String vidaJefe = "Tanque Jefe: " + boss.getVidas();
			g.drawString(vidaJefe, 740, 15);
		}

		if (hiloBonus.isAlive())
			g.drawString("Multiplicador de bonus: " + multiplicador, 180, 15);
		else
			g.drawString("", 5, 75);

	}

	private void inGame() {

		if (!ingame) {
			timer.stop();
		} else {
			tiempoTrancurrido += DELAY;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		inGame();

		updatePoder();
		updateMissiles();
		updateTanques();
		updateTanqueBoss();
		updateBlocks();
		checkCollisions();
		updateCraft();
		repaint();
	}

	public void updatePoder() {
		if (tiempoTrancurrido % 15000 == 0) {
			px = al.nextInt(B_WIDTH);
			py = al.nextInt(B_HEIGHT);
			tipo = al.nextInt(2) + 1;
			unPoder = new Poder(px, py, tipo);
		} else if (unPoder != null && (tiempoTrancurrido % 6000 == 0 && unPoder.isVisible())) {
			unPoder.setVisible(false);
		}
	}

	private void updateBlocks() {
		for (int i = 0; i < ladrillos.size(); i++) {

			Ladrillo a = ladrillos.get(i);
			if (!a.isVisible()) {
				ladrillos.remove(i);
			}
		}

		for (TanqueEnemigo tanque : tanquesEnemigos) {
			tanque.setLadrillos(ladrillos);
		}
	}

	public void setMultiplicador(int multiplicador) {
		this.multiplicador = multiplicador;
	}

	private void checkCollisions() {
		Rectangle recTankPro = tankPro.getBounds();

		if (unPoder != null && unPoder.isVisible()) {
			Rectangle recPoder = unPoder.getBounds();
			if (recPoder.intersects(recTankPro)) {
				int tipo = unPoder.getTipo();
				if (tipo == 1) {
					tankPro.setVidas(tankPro.getVidas() + 1);
				} else if (tipo == 2) {
					sonidos.reproducirBomba();
					arrazarConLosTanques();
				}
				unPoder.setVisible(false);
			}
		}

		ArrayList<Misil> ms = tankPro.getMissiles();

		for (Misil m : ms) {

			Rectangle bala = m.getBounds();

			for (TanqueEnemigo alien : tanquesEnemigos) {

				Rectangle extraterrestre = alien.getBounds();
				if (bala.intersects(extraterrestre)) {

					sonidos.reproducirExplo();
					m.setVisible(false);
					int vidasActuales = alien.getVidas() - 1;
					alien.setVidas(vidasActuales);

					if (alien.getVidas() <= 0) {

						alien.loadImage(exlocionImagen);

						alien.setVisible(false);
						int puntos = 100;

						if (hiloBonus.isAlive()) {
							puntos *= multiplicador;
						}
						puntaje += puntos;
						inBonus();
					}
				}

			}
			for (Ladrillo lad : ladrillos) {
				Rectangle ladRec = lad.getBounds();

				// Dectecta colision
				if (bala.intersects(ladRec)) {
					sonidos.reproducirExplo();
					m.setVisible(false);
					lad.setVisible(false);
				}
			}
			Rectangle flag = miAguila.getBounds();
			if (bala.intersects(flag)) {
				sonidos.reproducirExplo();
				m.setVisible(false);
				miAguila.setVisible(false);
				ingame = false;
				tankPro.setVidas(0);
			}
			if (boss != null) {
				Rectangle RBoss = boss.getBounds();
				if (bala.intersects(RBoss)) {
					sonidos.reproducirExplo();
					m.setVisible(false);
					int vidasActuales = boss.getVidas() - 1;
					boss.setVidas(vidasActuales);

					if (boss.getVidas() <= 0) {

						boss.loadImage(exlocionImagen);
						boss.setVisible(false);
						hiloJefe.stop();
						boss.setLocation(-300, -300);
						int puntos = 5000;
						puntaje += puntos;
						inBonus();
					}
				}
			}
			for (LadrilloConcreto ladCont : concreto) {
				Rectangle ladRec = ladCont.getBounds();

				// Dectecta colision
				if (bala.intersects(ladRec)) {
					sonidos.reproducirExplo();
					m.setVisible(false);
				}
			}

		}

		if (boss != null) {
			try {
				ArrayList<MisilJefe> msJEne = boss.getMissiles();

				for (MisilJefe mJefe : msJEne) {
					Rectangle balaJefe = mJefe.getBounds();
					for (Ladrillo lad : ladrillos) {
						Rectangle ladRec = lad.getBounds();

						// Dectecta colision
						if (balaJefe.intersects(ladRec)) {
							mJefe.setVisible(false);
							lad.setVisible(false);
						}
					}
					Rectangle flag = miAguila.getBounds();
					if (balaJefe.intersects(flag)) {
						mJefe.setVisible(false);
						miAguila.setVisible(false);
						sonidos.reproducirExplo();
						ingame = false;
						tankPro.setVidas(0);
					}
					for (LadrilloConcreto ladCont : concreto) {
						Rectangle ladRec = ladCont.getBounds();

						// Dectecta colision
						if (balaJefe.intersects(ladRec)) {
							mJefe.setVisible(false);
						}
					}
					if (balaJefe.intersects(recTankPro)) {
						sonidos.reproducirExplo();
						mJefe.setVisible(false);
						if (!tankPro.isProtegido()) {
							int vidasActuales = tankPro.getVidas() - 2;
							tankPro.setVidas(vidasActuales);
							tankPro.setLocation(ICRAFT_X, ICRAFT_Y);
							tankPro.setProtegido(true);
						}
						if (tankPro.getVidas() <= 0) {
							ingame = false;
						}
					}
				}
			} catch (ConcurrentModificationException e) {
				// TODO: handle exception
			}
		}

		for (TanqueEnemigo tanqueEnemin : tanquesEnemigos) {

			try {
				ArrayList<Misil> msEne = tanqueEnemin.getMissiles();
				for (Misil m1 : msEne) {
					Rectangle bala = m1.getBounds();
					for (Ladrillo lad : ladrillos) {
						Rectangle ladRec = lad.getBounds();

						// Dectecta colision
						if (bala.intersects(ladRec)) {
							m1.setVisible(false);
							lad.setVisible(false);
						}
					}
					Rectangle flag = miAguila.getBounds();
					if (bala.intersects(flag)) {
						m1.setVisible(false);
						miAguila.setVisible(false);
						sonidos.reproducirExplo();
						ingame = false;
						tankPro.setVidas(0);
					}
					for (LadrilloConcreto ladCont : concreto) {
						Rectangle ladRec = ladCont.getBounds();

						// Dectecta colision
						if (bala.intersects(ladRec)) {
							m1.setVisible(false);
						}
					}
					if (bala.intersects(recTankPro)) {
						sonidos.reproducirExplo();
						m1.setVisible(false);
						if (!tankPro.isProtegido()) {
							int vidasActuales = tankPro.getVidas() - 1;
							tankPro.setVidas(vidasActuales);
							tankPro.setLocation(ICRAFT_X, ICRAFT_Y);
							tankPro.setProtegido(true);
						}
						if (tankPro.getVidas() <= 0) {
							ingame = false;
						}
					}
				}
			} catch (ConcurrentModificationException e) {
				System.out.println("ConcurrentModificationException");
			}

		}

	}

	private void arrazarConLosTanques() {
		for (TanqueEnemigo tankEnem : tanquesEnemigos) {
			tankEnem.loadImage(exlocionImagen);

			tankEnem.setVisible(false);
			int puntos = 100;

			puntaje += puntos;
		}
		if (boss != null) {
			int vidas = boss.getVidas();
			boss.setVidas(vidas - 5);
		}

	}

	private void updateTanqueBoss() {
		if (boss != null) {
			if (boss.isVisible()) {
				ArrayList<MisilJefe> msEne = boss.getMissiles();
				for (int j = 0; j < msEne.size(); j++) {
					MisilJefe m = msEne.get(j);
					if (m.isVisible()) {
						m.move();
					} else {
						msEne.remove(j);
					}
				}
			}
			if (boss.isVisible() && boss.getVidas() <= 0) {
				boss.setVisible(false);
				hiloJefe.stop();
				boss.setLocation(-300, -300);
			}
		}
	}

	private void updateTanques() {

		if (tanquesEnemigos.isEmpty() && (boss == null || !boss.isVisible())) {

			ingame = false;
			gano = true;
			return;
		}

		for (int i = 0; i < tanquesEnemigos.size(); i++) {
			TanqueEnemigo a = tanquesEnemigos.get(i);
			if (!a.isVisible()) {
				tanquesEnemigos.remove(i);
				lanzarTanque();
			}
			ArrayList<Misil> msEne = a.getMissiles();
			for (int j = 0; j < msEne.size(); j++) {
				Misil m = msEne.get(j);
				if (m.isVisible()) {
					m.move();
				} else {
					msEne.remove(j);
				}
			}
		}

	}

	private void lanzarTanque() {

		if (numTanques > 0) {

			int pos = al.nextInt(3);
			TanqueEnemigo t = new TanqueEnemigo(posEnemig[pos][0], posEnemig[pos][1], ladrillos, agua, concreto);
			Thread t1 = new Thread(t);
			t1.start();
			tanquesEnemigos.add(t);
			numTanques--;
		}
	}

	private void updateMissiles() {

		ArrayList<Misil> ms = tankPro.getMissiles();
		for (int i = 0; i < ms.size(); i++) {
			Misil m = (Misil) ms.get(i);
			if (m.isVisible()) {
				m.move();
			} else {
				ms.remove(i);
			}
		}
	}

	private void updateCraft() {

		if ((tankPro.isProtegido()) && (tiempoTrancurrido % 5000 == 0)) {
			tankPro.setProtegido(false);
		}
		tankPro.move();

	}

	private void drawGameOver(Graphics g, boolean gano, int cantidad) {

		String msg = "Game Over";
		if (gano) {
			msg = "Game Won";
		}

		int fila = 0;
		for (int rojo = 0; rojo <= 255; rojo++) {
			Color col = new Color(rojo, 0, 0);
			g.setColor(col);
			g.drawLine(0, fila, B_WIDTH, fila);
			g.drawLine(0, fila + 1, B_WIDTH, fila + 1);
			g.drawLine(0, fila + 2, B_WIDTH, fila + 2);
			fila += 3;
		}

		Font small = new Font("Century Gothic", Font.BOLD, 30);
		FontMetrics fm = getFontMetrics(small);

		g.setColor(new Color(255, 195, 0));
		g.setFont(small);
		g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2, 80);

		small = new Font("Century Gothic", Font.BOLD, 20);
		g.setFont(small);
		g.setColor(Color.WHITE);
		g.drawString("Puntos", 530, 160);

		String mensajeEnemigos = "Puntuacion acumulada .... " + puntaje + " P";
		g.drawString(mensajeEnemigos, 270, 205);
		int puntosVidas = (tankPro.getVidas() * 1000) * bonificacionDeNivel;
		String vidasRestantes = "Vidas Restantes ................. " + puntosVidas + " P";
		g.drawString(vidasRestantes, 270, 255);
		int puntajeFinal = puntaje + puntosVidas;
		g.drawString("Total Puntos ........................ " + puntajeFinal + " P", 270, 305);

		small = new Font("Century Gothic", Font.BOLD, 30);
		g.setColor(new Color(255, 195, 0));
		g.setFont(small);
		if (!gano) {
			sonidos.reproducirFail();
			String mensaje = "Sigue intentando";
			if (puntajeFinal <= 300) {

				int nunMensaje = al.nextInt(mesajesPerdio.length);
				mensaje = mesajesPerdio[nunMensaje];
				g.drawString(mensaje, (B_WIDTH - fm.stringWidth(mensaje)) / 2, 400);
			} else {
				g.drawString(mensaje, (B_WIDTH - fm.stringWidth(mensaje)) / 2, 400);
			}
		} else {
			sonidos.reproducirWin();
			if (mapa < MAPA_FINAL) {
				btnContinuar = new JButton("Guardar y Continuar");
				btnContinuar.setBackground(Color.YELLOW);
				btnContinuar.setForeground(Color.BLUE);
				btnContinuar.setSize(150, 30);
				btnContinuar.setLocation(305, 425);

				btnContinuar.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {

						usuario.setPuntaje(puntajeFinal);
						usuario.setMapa(mapa + 1);
						usuario.setVidas(tankPro.getVidas());
						miJugador.uctualizarUsuario(usuario);
						VentanaJuego ex = new VentanaJuego(numTanquesIniciales, bonificacionDeNivel, sonidos, miJugador,
								usuario);
						ex.setVisible(true);
						ventana.dispose();
						ventana = null;
					}
				});
				add(btnContinuar);
				String mensaje = "Felicidades, ganaste!!! Pasas al siguiente nivel";
				g.drawString(mensaje, (B_WIDTH - fm.stringWidth(mensaje)) / 2, 400);
			} else {
				small = new Font("Century Gothic", Font.BOLD, 25);
				g.setFont(small);
				fm = getFontMetrics(small);
				String mensaje = "Felicidades, has completado el juego!!!";
				g.drawString(mensaje, (B_WIDTH - fm.stringWidth(mensaje)) / 2, 400);
				mensaje = "Deberias ponerte a estudiar";
				g.drawString(mensaje, (B_WIDTH - fm.stringWidth(mensaje)) / 2, 430);
			}
		}

		if ((mapa == MAPA_FINAL && gano) || !gano) {

			btnContinuar = new JButton("Salir");
			btnContinuar.setBackground(Color.YELLOW);
			btnContinuar.setForeground(Color.BLUE);
			btnContinuar.setSize(150, 30);
			btnContinuar.setLocation((B_WIDTH - 150) / 2, 450);

			btnContinuar.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {

					miJugador.registrarPuntaje(usuario.getNombre(), usuario.getDificultad(), puntajeFinal);

					int vidas = 0;
					if (dificultad.equals("PRINCIPIANTE")) {
						vidas = 3;
					} else if (dificultad.equals("INTERMEDIO")) {
						vidas = 2;
					} else {
						vidas = 1;
					}
					if (gano) {
						usuario.setMapa(1);
						
					} else {
						usuario.setMapa(mapa);
						miJugador.uctualizarUsuario(usuario);
					}
					usuario.setPuntaje(0);
					usuario.setVidas(vidas);
					miJugador.uctualizarUsuario(usuario);

					ventana.dispose();
					VentanaPrincipal vp = new VentanaPrincipal(sonidos);
					vp.setVisible(true);
				}
			});
			add(btnContinuar);

		}
	}

	private class TAdapter extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent e) {
			tankPro.keyReleased(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			tankPro.keyPressed(e);
		}
	}
}
