package vistas;

import java.awt.Toolkit;

import javax.swing.JFrame;

import modelos.JugadorDao;
import modelos.JugadorVo;
import recursos.Sonidos;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VentanaJuego extends JFrame {

	private int numTanques;
	private int bonificacionDeNivel;
	private Sonidos sonidos;
	private Board miBoard;
	private JugadorDao miJugador;
	private JugadorVo usuario;
	

	public VentanaJuego(int numTanques, int bonificacionDeNivel, Sonidos sonidos,
			JugadorDao miJugador,JugadorVo usuario) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				dispose();
				VentanaPrincipal vp=new VentanaPrincipal(sonidos);
				vp.setVisible(true);
			}
		});

		this.miJugador = miJugador;
		this.numTanques = numTanques;
		this.bonificacionDeNivel = bonificacionDeNivel;
		this.sonidos = sonidos;

		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaDificultad.class.getResource("/img/aguila.png")));

		miBoard = new Board(numTanques, bonificacionDeNivel, sonidos,  this, this.miJugador,usuario);
		getContentPane().add(miBoard);
		setSize(890, 580);
		setResizable(false);

		setTitle("Battle City: " + usuario.getDificultad());
		setLocationRelativeTo(null);
	}
}
