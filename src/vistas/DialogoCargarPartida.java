package vistas;

import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import modelos.JugadorDao;
import modelos.JugadorVo;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.border.BevelBorder;

public class DialogoCargarPartida extends JDialog {

	private final JPanel conten = new JPanel();

	private int aceptarDe0;
	private JTable table;
	private JugadorDao miJugadorDao;
	private JugadorVo jugadorSeleccionado;
	private ArrayList<JugadorVo> listPartidas;
	private DefaultTableModel modelList;

	public DialogoCargarPartida(JFrame parent, boolean modal) {
		super(parent, modal);

		miJugadorDao = new JugadorDao();
		listPartidas = miJugadorDao.getPartidas();
		setTitle("Cargar Partida");
		setSize(524, 415);
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
				lblTitulo.setBounds(10, 11, 478, 83);
				ImageIcon imgenOrigin = new ImageIcon(VentanaDificultad.class.getResource("/img/logo1.png"));
				ImageIcon imagenReEscalada = new ImageIcon(
						imgenOrigin.getImage().getScaledInstance(434, 81, Image.SCALE_DEFAULT));
				lblTitulo.setIcon(imagenReEscalada);

				panel.add(lblTitulo);
				panel.setLayout(null);

				JButton btnCargarPartida = new JButton("Cargar Partida");
				btnCargarPartida.setForeground(Color.BLUE);
				btnCargarPartida.setBackground(Color.YELLOW);
				btnCargarPartida.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						int selec = table.getSelectedRow();
						if (selec != -1) {
							String nombre = (String) table.getModel().getValueAt(selec, 0);
							int mapa = (int) table.getModel().getValueAt(selec, 1);
							String dificultad = (String) table.getModel().getValueAt(selec, 2);
							int puntaje = (int) table.getModel().getValueAt(selec, 3);
							int vidas = (int) table.getModel().getValueAt(selec, 4);
							jugadorSeleccionado = new JugadorVo();
							jugadorSeleccionado.setNombre(nombre);
							jugadorSeleccionado.setMapa(mapa);
							jugadorSeleccionado.setDificultad(dificultad);
							jugadorSeleccionado.setPuntaje(puntaje);
							jugadorSeleccionado.setVidas(vidas);
							aceptarDe0 = 0;
							dispose();
						} else
							JOptionPane.showMessageDialog(null, "Debe seleccionar una partida");
					}
				});
				btnCargarPartida.setBounds(103, 323, 135, 23);
				panel.add(btnCargarPartida);

				JButton btnEmpezarNuevo = new JButton("Empezar de Nuevo");
				btnEmpezarNuevo.setForeground(Color.BLUE);
				btnEmpezarNuevo.setBackground(Color.YELLOW);
				btnEmpezarNuevo.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent arg0) {
						int selec = table.getSelectedRow();
						if (selec != -1) {
							String nombre = (String) table.getModel().getValueAt(selec, 0);
							jugadorSeleccionado = new JugadorVo();
							jugadorSeleccionado.setNombre(nombre);
							jugadorSeleccionado.setMapa(1);
							jugadorSeleccionado.setPuntaje(0);
							jugadorSeleccionado.setVidas(0);
							aceptarDe0 = 1;
							dispose();
						} else
							JOptionPane.showMessageDialog(null, "Debe seleccionar una partida");
					}
				});
				btnEmpezarNuevo.setBounds(264, 323, 135, 23);
				panel.add(btnEmpezarNuevo);

				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setBackground(Color.WHITE);
				scrollPane.setBounds(103, 163, 296, 149);
				panel.add(scrollPane);

				table = new JTable();
				table.setForeground(Color.WHITE);
				table.setBackground(Color.BLACK);
				table.setBorder(null);
				table.setModel(new DefaultTableModel(new Object[][] {},
						new String[] { "Jugador", "Mapa", "Dificultad", "Puntaje", "Vidas" }) {
					Class[] columnTypes = new Class[] { Object.class, Object.class, Object.class, Integer.class,
							Integer.class };

					public Class getColumnClass(int columnIndex) {
						return columnTypes[columnIndex];
					}
				});
				table.getColumnModel().getColumn(0).setPreferredWidth(80);
				table.getColumnModel().getColumn(1).setPreferredWidth(58);
				scrollPane.setViewportView(table);

				cargarPartidas();

				JLabel lblSeleccioneLaPartida = new JLabel("Seleccione una partida");
				lblSeleccioneLaPartida.setHorizontalAlignment(SwingConstants.CENTER);
				lblSeleccioneLaPartida.setForeground(Color.WHITE);
				lblSeleccioneLaPartida.setFont(new Font("Tahoma", Font.PLAIN, 14));
				lblSeleccioneLaPartida.setBounds(10, 117, 478, 17);
				panel.add(lblSeleccioneLaPartida);
			}

		}
	}

	public void cargarPartidas() {
		modelList = (DefaultTableModel) table.getModel();

		for (JugadorVo unJugador : listPartidas) {

			String dificultad = unJugador.getDificultad();
			Object row[] = { unJugador.getNombre(), unJugador.getMapa(), dificultad, unJugador.getPuntaje(),
					unJugador.getVidas() };
			modelList.addRow(row);
		}
		table.setModel(modelList);
	}

	public int getAceptarDe0() {
		return aceptarDe0;
	}

	public void setAceptarDe0(int aceptarDe0) {
		this.aceptarDe0 = aceptarDe0;
	}

	public JugadorVo getJugadorSeleccionado() {
		return jugadorSeleccionado;
	}

}
