package vistas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import modelos.JugadorDao;
import modelos.JugadorVo;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;


public class VentanaRegistroJugador extends JFrame implements ActionListener {

	private modelos.JugadorDao mijugador;
	private JLabel labelTitulo;
	private JTextField txtNombre;
	private JLabel cod;
	private JButton botonGuardar;

	private int puntaje;

	/**
	 * constructor de la clase donde se inicializan todos los componentes de la
	 * ventana de registro
	 */
	public VentanaRegistroJugador(int puntaje) {
		getContentPane().setBackground(Color.BLACK);

		botonGuardar = new JButton();
		botonGuardar.setForeground(Color.BLUE);
		botonGuardar.setBackground(Color.YELLOW);
		botonGuardar.setBounds(56, 134, 120, 25);
		botonGuardar.setText("Registrar");

		labelTitulo = new JLabel();
		labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitulo.setForeground(Color.RED);
		labelTitulo.setText("Puntaje Alto");
		labelTitulo.setBounds(20, 22, 193, 30);
		labelTitulo.setFont(new Font("Lucida Bright", Font.BOLD | Font.ITALIC, 18));

		cod = new JLabel();
		cod.setFont(new Font("Lucida Bright", Font.PLAIN, 11));
		cod.setForeground(Color.WHITE);
		cod.setText("Nombre");
		cod.setBounds(20, 80, 80, 25);
		getContentPane().add(cod);

		txtNombre = new JTextField();
		txtNombre.setBounds(110, 80, 80, 25);
		getContentPane().add(txtNombre);

		botonGuardar.addActionListener(this);
		getContentPane().add(botonGuardar);
		getContentPane().add(labelTitulo);
		limpiar();
		setSize(260, 210);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);

		mijugador=new JugadorDao();
		this.puntaje = puntaje;

	}

	private void limpiar() {
		txtNombre.setText("");
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == botonGuardar) {
			try {
				JugadorVo miJugadorVo = new JugadorVo();
				miJugadorVo.setNombre(txtNombre.getText());
				miJugadorVo.setPuntaje(puntaje);

				mijugador.registrarJugador(miJugadorVo);
				this.dispose();
			} catch (Exception ex) {
				System.out.println("Error en el Ingreso de Datos");
			}
		}
	}
}
