package vistas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BoxLayout;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import java.awt.FlowLayout;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import java.awt.Component;
import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaInstrucciones extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public VentanaInstrucciones() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(556, 516);
		setLocationRelativeTo(null);
		contentPane = new JPanel(null);
		contentPane.setBackground(Color.BLACK);
        contentPane.setPreferredSize(new Dimension(500, 400));
        setContentPane(contentPane);
        
        JLabel lblTitulo = new JLabel();
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(18, 11, 512, 56);
		ImageIcon imgenOrigin = new ImageIcon(VentanaDificultad.class.getResource("/img/logo1.png"));
		ImageIcon imagenReEscalada = new ImageIcon(
				imgenOrigin.getImage().getScaledInstance(434, 81, Image.SCALE_DEFAULT));
		lblTitulo.setIcon(imagenReEscalada);
		contentPane.add(lblTitulo);
		
		JLabel lblSeleccioneLaPartida = new JLabel("INSTRUCCIONES");
		lblSeleccioneLaPartida.setBounds(213, 84, 132, 17);
		lblSeleccioneLaPartida.setForeground(Color.WHITE);
		lblSeleccioneLaPartida.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(lblSeleccioneLaPartida);
		
		JButton btnEmpezarNuevo = new JButton("Atras");
		btnEmpezarNuevo.setBounds(449, 78, 81, 23);
		btnEmpezarNuevo.setForeground(Color.BLUE);
		btnEmpezarNuevo.setBackground(Color.YELLOW);
		btnEmpezarNuevo.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		contentPane.add(btnEmpezarNuevo);
        
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
        JScrollPane scrollPane = new JScrollPane(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JTextArea txtrelJuegoInicia = new JTextArea();
        txtrelJuegoInicia.setMargin(new Insets(20, 20, 20, 20));
        txtrelJuegoInicia.setForeground(Color.WHITE);
        txtrelJuegoInicia.setBackground(Color.BLACK);
        txtrelJuegoInicia.setEditable(false);
        txtrelJuegoInicia.setFont(new Font("SansSerif", Font.PLAIN, 17));
        txtrelJuegoInicia.setWrapStyleWord(true);
        txtrelJuegoInicia.setText(""
        		+ "1-El juego inicia en una ventana principal donde elige la opci\u00F3n\n"
        		+ "de iniciar juego nuevo.\n\n"
        		+ "2- Cuando ingrese a la ventana del juego nuevo, el jugador se \n"
        		+ "podr\u00E1 registrar con un nickname.\n\n"
        		+ "3- Una vez registrado el jugador, se habilitara una ventana \n"
        		+ "emergente, para que \u00E9l pueda elegir su nivel de juego \n"
        		+ "deseado.\n\n"
        		+ "4-Cuando el  nivel es elegido, se iniciara el juego.\n\n"
        		+ "5- Cuando se inicie el juego es necesario tener en cuenta las \n"
        		+ "siguientes instrucciones.\n\n"
        		+ "6- las flechas de direccion del teclado nos permite maniobrar \n"
        		+ "el tanque protagonista.\n");
        panel.add(txtrelJuegoInicia);
        
        JLabel lblNewLabel = new JLabel();
        lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setIcon(new ImageIcon(VentanaInstrucciones.class.getResource("/img/FlechasDireccion.png")));
        panel.add(lblNewLabel);
        
        JTextArea textArea = new JTextArea();
        textArea.setMargin(new Insets(20, 20, 20, 20));
        textArea.setWrapStyleWord(true);
        textArea.setColumns(2);
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.WHITE);
        textArea.setEditable(false);
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        textArea.setText( ""
        		+ "7-La barra espaciadora le da la opcion al tanque de disparar en la \n"
        		+ "direcion en la que se encuentre.\n");
        panel.add(textArea);
        
        JLabel label = new JLabel("");
        label.setIcon(new ImageIcon(VentanaInstrucciones.class.getResource("/img/tecladoespaciadora.jpg")));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);
        
        JTextArea textArea_1 = new JTextArea();
        textArea_1.setMargin(new Insets(20, 20, 20, 20));
        textArea_1.setForeground(Color.WHITE);
        textArea_1.setBackground(Color.BLACK);
        textArea_1.setEditable(false);
        textArea_1.setFont(new Font("SansSerif", Font.PLAIN, 14));
        textArea_1.setWrapStyleWord(true);
        textArea_1.setText( "8-Existen 3 tipos de tanques enemigos y uno protagonista\n");
        panel.add(textArea_1);
        
        JLabel label_1 = new JLabel("");
        label_1.setAlignmentX(Component.CENTER_ALIGNMENT);
        label_1.setIcon(new ImageIcon(VentanaInstrucciones.class.getResource("/img/tanquetodos.PNG")));
        panel.add(label_1);
        
        scrollPane.setBounds(10, 112, 520, 354);
        contentPane.add(scrollPane);
        
	}
}
