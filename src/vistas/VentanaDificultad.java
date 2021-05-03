package vistas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import modelos.JugadorDao;
import modelos.JugadorVo;
import recursos.Sonidos;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Toolkit;

public class VentanaDificultad extends JFrame {

	private JPanel contentPane;
	private Sonidos sonidos;
	private JugadorDao miJugadorDao;
	
	/**
	 * Launch the application.
	 */

	// Variables de configuracion
	int numTanques;
	int numVidas;
	int bonificacionDeNivel;
	int mapa;

	/**
	 * Create the frame.
	 */
	public VentanaDificultad(Sonidos sonidos,String usuario,int accion) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaDificultad.class.getResource("/img/aguila.png")));
		
		this.sonidos=sonidos;
		miJugadorDao=new JugadorDao();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(513, 300);
		setLocationRelativeTo(null);
		setResizable(false);
		
		setTitle("Battle City");
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblLogo = new JLabel();
		ImageIcon imgenOrigin = new ImageIcon(VentanaDificultad.class.getResource("/img/logo1.png"));
		ImageIcon imagenReEscalada = new ImageIcon(
				imgenOrigin.getImage().getScaledInstance(434, 81, Image.SCALE_DEFAULT));
		lblLogo.setIcon(imagenReEscalada);
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogo.setBounds(31, 23, 434, 81);
		contentPane.add(lblLogo);
		
		JButton btnNivel = new JButton("PRINCIPIANTE");
		btnNivel.setBackground(Color.YELLOW);
		btnNivel.setForeground(Color.BLUE);
		btnNivel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				sonidos.reproducirIntro();
				numTanques = 5;
				numVidas=3;
				bonificacionDeNivel=1;
				JugadorVo jugador=new JugadorVo();
				jugador.setNombre(usuario);
				jugador.setPuntaje(0);
				jugador.setMapa(1);
				jugador.setDificultad("PRINCIPIANTE");
				jugador.setVidas(numVidas);
				if(accion==1){
					miJugadorDao.registrarJugador(jugador);
				}else miJugadorDao.uctualizarUsuario(jugador);
				
				VentanaJuego ex = new VentanaJuego(numTanques,bonificacionDeNivel,VentanaDificultad.this.sonidos,miJugadorDao,jugador);
				ex.setVisible(true);
				dispose();

			}
		});
		btnNivel.setBounds(69, 203, 113, 23);
		contentPane.add(btnNivel);

		JButton btnNivel2 = new JButton("INTERMEDIO");
		btnNivel2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sonidos.reproducirIntro();
				numTanques = 10;
				numVidas=2;
				bonificacionDeNivel=2;
				JugadorVo jugador=new JugadorVo();
				jugador.setNombre(usuario);
				jugador.setPuntaje(0);
				jugador.setMapa(1);
				jugador.setDificultad("INTERMEDIO");
				jugador.setVidas(numVidas);
				if(accion==1){
					miJugadorDao.registrarJugador(jugador);
				}else miJugadorDao.uctualizarUsuario(jugador);
				
				VentanaJuego ex = new VentanaJuego(numTanques,bonificacionDeNivel,VentanaDificultad.this.sonidos,miJugadorDao,jugador);
				ex.setVisible(true);
				dispose();

			}
		});
		btnNivel2.setBackground(Color.YELLOW);
		btnNivel2.setForeground(Color.BLUE);
		btnNivel2.setBounds(192, 203, 113, 23);
		contentPane.add(btnNivel2);

		JButton btnNivel3 = new JButton("AVANZADO");
		btnNivel3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sonidos.reproducirIntro();
				numTanques = 20;
				numVidas=1;
				bonificacionDeNivel=5;
				JugadorVo jugador=new JugadorVo();
				jugador.setNombre(usuario);
				jugador.setPuntaje(0);
				jugador.setMapa(1);
				jugador.setDificultad("AVANZADO");
				jugador.setVidas(numVidas);
				if(accion==1){
					miJugadorDao.registrarJugador(jugador);
				}else miJugadorDao.uctualizarUsuario(jugador);
				
				VentanaJuego ex = new VentanaJuego(numTanques,bonificacionDeNivel,VentanaDificultad.this.sonidos,miJugadorDao,jugador);
				ex.setVisible(true);
				dispose();
			}
		});
		btnNivel3.setBackground(Color.YELLOW);
		btnNivel3.setForeground(Color.BLUE);
		btnNivel3.setBounds(315, 203, 113, 23);
		contentPane.add(btnNivel3);

		JLabel lblSeleccioneUnNivel = new JLabel("SELECCIONE UNA DIFICULTAD PARA EMPEZAR");
		lblSeleccioneUnNivel.setHorizontalAlignment(SwingConstants.CENTER);
		lblSeleccioneUnNivel.setForeground(Color.WHITE);
		lblSeleccioneUnNivel.setFont(new Font("Lucida Bright", Font.PLAIN, 16));
		lblSeleccioneUnNivel.setBounds(10, 139, 487, 29);
		contentPane.add(lblSeleccioneUnNivel);
	}


}
