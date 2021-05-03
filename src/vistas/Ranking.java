package vistas;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import modelos.JugadorDao;
import modelos.JugadorVo;

import javax.swing.SwingConstants;

public class Ranking extends JDialog {

	private final JPanel conten = new JPanel();

	private JTable table;
	private JugadorDao miJugadorDao;
	private ArrayList<JugadorVo> listPuntajes;
	private DefaultTableModel modelList;

	public Ranking(JFrame parent, boolean modal) {
		super(parent, modal);
		miJugadorDao = new JugadorDao();
		listPuntajes = miJugadorDao.getMejoresPuntajes();

		setTitle("Mejores puntajes");
		setSize(478, 415);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		conten.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(conten, BorderLayout.CENTER);
		conten.setBackground(Color.BLACK);
		conten.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBackground(Color.BLACK);
			conten.add(panel, BorderLayout.CENTER);

			{
				JLabel lblTitulo = new JLabel();
				lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
				lblTitulo.setBounds(10, 11, 432, 83);
				ImageIcon imgenOrigin = new ImageIcon(VentanaDificultad.class.getResource("/img/logo1.png"));
				ImageIcon imagenReEscalada = new ImageIcon(
						imgenOrigin.getImage().getScaledInstance(434, 81, Image.SCALE_DEFAULT));
				lblTitulo.setIcon(imagenReEscalada);

				panel.add(lblTitulo);
				panel.setLayout(null);

				JButton btnEmpezarNuevo = new JButton("Regresar");
				btnEmpezarNuevo.setForeground(Color.BLUE);
				btnEmpezarNuevo.setBackground(Color.YELLOW);
				btnEmpezarNuevo.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				btnEmpezarNuevo.setBounds(159, 325, 135, 23);
				panel.add(btnEmpezarNuevo);

				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setBackground(Color.WHITE);
				scrollPane.setBounds(75, 165, 296, 149);
				panel.add(scrollPane);

				table = new JTable();
				table.setForeground(Color.WHITE);
				table.setBackground(Color.BLACK);
				table.setModel(
						new DefaultTableModel(
					new Object[][] {
					},
					new String[] {
						"Jugador", "Dificultad", "Puntaje"
					}
				) {
					Class[] columnTypes = new Class[] {
						Object.class, Object.class, Integer.class
					};
					public Class getColumnClass(int columnIndex) {
						return columnTypes[columnIndex];
					}
				});
				scrollPane.setViewportView(table);
				cargarPuntajes();

				JLabel lblSeleccioneLaPartida = new JLabel("Ranking de mejores puntajes");
				lblSeleccioneLaPartida.setHorizontalAlignment(SwingConstants.CENTER);
				lblSeleccioneLaPartida.setForeground(Color.WHITE);
				lblSeleccioneLaPartida.setFont(new Font("Tahoma", Font.PLAIN, 14));
				lblSeleccioneLaPartida.setBounds(10, 117, 432, 17);
				panel.add(lblSeleccioneLaPartida);
			}

		}
	}

	public void cargarPuntajes() {
		modelList = (DefaultTableModel) table.getModel();

		for (JugadorVo unJugador : listPuntajes) {

			Object row[] = { unJugador.getNombre(), unJugador.getDificultad(), unJugador.getPuntaje() };
			modelList.addRow(row);
		}
		table.setModel(modelList);
	}

}
