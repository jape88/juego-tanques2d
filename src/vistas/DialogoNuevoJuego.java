package vistas;

import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import modelos.JugadorDao;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class DialogoNuevoJuego extends JDialog {

	private final JPanel contentPane = new JPanel();
	private JTextField textJugador;
	private int aceptar;
	private String nombreUsuario;

	public DialogoNuevoJuego(JFrame parent, boolean modal) {
		super(parent, modal);
		setTitle("Nuevo Jugador");
		setSize(513, 250);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPane, BorderLayout.CENTER);
		contentPane.setBackground(Color.BLACK);
		contentPane.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setLayout(null);
			panel.setBackground(Color.BLACK);
			contentPane.add(panel, BorderLayout.CENTER);

			{
				JLabel lblTitulo = new JLabel();
				lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
				lblTitulo.setBounds(10, 11, 467, 83);
				ImageIcon imgenOrigin = new ImageIcon(VentanaDificultad.class.getResource("/img/logo1.png"));
				ImageIcon imagenReEscalada = new ImageIcon(
						imgenOrigin.getImage().getScaledInstance(434, 81, Image.SCALE_DEFAULT));
				lblTitulo.setIcon(imagenReEscalada);
				panel.add(lblTitulo);

				JLabel lblJugador = new JLabel("Jugador");
				lblJugador.setForeground(Color.WHITE);
				lblJugador.setFont(new Font("Tahoma", Font.PLAIN, 14));
				lblJugador.setBounds(31, 23, 434, 81);
				panel.add(lblJugador);

				textJugador = new JTextField();
				textJugador.setColumns(10);
				textJugador.setBounds(31, 133, 426, 20);
				panel.add(textJugador);

				JButton btnJugar = new JButton("CONTINUAR");
				btnJugar.setForeground(Color.BLUE);
				btnJugar.setBackground(Color.YELLOW);
				btnJugar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						nombreUsuario = textJugador.getText();
						JugadorDao jd = new JugadorDao();
						if (!jd.existeJugador(nombreUsuario)) {
							aceptar = 1;
							dispose();
						} else
							JOptionPane.showMessageDialog(null,
									"El jugador ya exite, intente con otro nombre o ingrese a cargar partida");
					}

				});
				btnJugar.setBounds(134, 164, 108, 23);
				panel.add(btnJugar);

				JLabel lblIngreseSuJugador = new JLabel("Nombre del Jugador");
				lblIngreseSuJugador.setHorizontalAlignment(SwingConstants.CENTER);
				lblIngreseSuJugador.setForeground(Color.WHITE);
				lblIngreseSuJugador.setFont(new Font("Tahoma", Font.PLAIN, 14));
				lblIngreseSuJugador.setBounds(20, 105, 455, 17);
				panel.add(lblIngreseSuJugador);

				JButton btnAtras = new JButton("ATRAS");
				btnAtras.setForeground(Color.BLUE);
				btnAtras.setBackground(Color.YELLOW);
				btnAtras.setBounds(241, 164, 108, 23);
				panel.add(btnAtras);
			}

		}
	}

	public int getAceptar() {
		return aceptar;
	}

	public void setAceptar(int aceptar) {
		this.aceptar = aceptar;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
}
