package vistas;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import modelos.JugadorDao;
import modelos.JugadorVo;
import recursos.Sonidos;

import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaPrincipal extends JFrame {

	private JPanel contentPane;
	private VentanaDificultad escogerNivel;

	public VentanaPrincipal(Sonidos sonidos) {
		sonidos.reproducirIntro();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(451, 330);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitulo = new JLabel();
		lblTitulo.setBounds(62, 11, 319, 83);
		ImageIcon imgenOrigin = new ImageIcon(VentanaDificultad.class.getResource("/img/logo1.png"));
		ImageIcon imagenReEscalada = new ImageIcon(
				imgenOrigin.getImage().getScaledInstance(434, 81, Image.SCALE_DEFAULT));
		lblTitulo.setIcon(imagenReEscalada);

		contentPane.add(lblTitulo);

		JButton btnNuevoPartida = new JButton("Nueva Partida");
		btnNuevoPartida.setForeground(Color.BLUE);
		btnNuevoPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				DialogoNuevoJuego nj = new DialogoNuevoJuego(VentanaPrincipal.this, true);
				nj.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				nj.setVisible(true);
				if (nj.getAceptar() == 1) {
					dispose();
					String usuario=nj.getNombreUsuario();
					escogerNivel = new VentanaDificultad(sonidos,usuario,1);
					escogerNivel.setVisible(true);
				}
			}
		});
		btnNuevoPartida.setBackground(Color.YELLOW);
		btnNuevoPartida.setBounds(138, 115, 150, 23);
		contentPane.add(btnNuevoPartida);

		JButton btnCargarPartida = new JButton("Cargar Partida");
		btnCargarPartida.setForeground(Color.BLUE);
		btnCargarPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				DialogoCargarPartida cp = new DialogoCargarPartida(VentanaPrincipal.this, true);
				cp.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				cp.setVisible(true);
				JugadorVo jugador=cp.getJugadorSeleccionado();
				//Va a cargar el juego.
				if (cp.getAceptarDe0() == 0) {
					String dificultad=jugador.getDificultad();
					int numTanques=0,bonificacionDeNivel=0;
					if(dificultad.equals("PRINCIPIANTE")){
						numTanques=5;
						bonificacionDeNivel=1;
					}else if(dificultad.equals("INTERMEDIO")){
						numTanques=10;
						bonificacionDeNivel=2;
					}else if(dificultad.equals("AVANZADO")){
						numTanques=20;
						bonificacionDeNivel=5;
					}
					VentanaJuego venJuego=new VentanaJuego(numTanques, bonificacionDeNivel, sonidos, new JugadorDao(), jugador);
					venJuego.setVisible(true);
					
				}else {
					String usuario=jugador.getNombre();
					escogerNivel = new VentanaDificultad(sonidos,usuario,2);
					escogerNivel.setVisible(true);
				}
				dispose();
				
			}
		});
		btnCargarPartida.setBackground(Color.YELLOW);
		btnCargarPartida.setBounds(138, 149, 150, 23);
		contentPane.add(btnCargarPartida);

		JButton btnRanking = new JButton("Ranking");
		btnRanking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Ranking r=new Ranking(VentanaPrincipal.this, true);
				r.setVisible(true);
			}
		});
		btnRanking.setForeground(Color.BLUE);
		btnRanking.setBackground(Color.YELLOW);
		btnRanking.setBounds(138, 183, 150, 23);
		contentPane.add(btnRanking);

		JButton btnInstrucciones = new JButton("Instrucciones");
		btnInstrucciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentanaInstrucciones vI=new VentanaInstrucciones();
				vI.setVisible(true);
			}
		});
		btnInstrucciones.setForeground(Color.BLUE);
		btnInstrucciones.setBackground(Color.YELLOW);
		btnInstrucciones.setBounds(138, 217, 150, 23);
		contentPane.add(btnInstrucciones);

		JButton btnAcerca = new JButton("Acerca de");
		btnAcerca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DialogoAcercaDe dc=new DialogoAcercaDe();
				dc.setVisible(true);
			}
		});
		btnAcerca.setForeground(Color.BLUE);
		btnAcerca.setBackground(Color.YELLOW);
		btnAcerca.setBounds(138, 251, 150, 23);
		contentPane.add(btnAcerca);
	}

}
