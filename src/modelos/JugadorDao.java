package modelos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import controladores.Conexion;

public class JugadorDao {

	public void registrarJugador(JugadorVo miJugador) {
		Conexion conex = new Conexion();

		try {
			Statement sentencia = conex.getConnection().createStatement();

			String sql = "INSERT INTO usuarios VALUES (0,'" + miJugador.getNombre() + "', '" + miJugador.getPuntaje()
					+ "','" + miJugador.getMapa() + "','" + miJugador.getDificultad() + "','" + miJugador.getVidas()
					+ "')";

			sentencia.executeUpdate(sql);
			sentencia.close();
			conex.desconectar();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("No se Registro");
		}
	}

	public ArrayList<JugadorVo> getMejoresPuntajes() {
		ArrayList<JugadorVo> listJugadores = new ArrayList<JugadorVo>();
		Conexion conex = new Conexion();
		JugadorVo unJugador;
		try {
			PreparedStatement query = conex.getConnection().prepareStatement(
					"SELECT b.nombre,a.dificultad,a.puntaje "
					+ "FROM puntajesfinales AS a "
					+ "LEFT JOIN usuarios AS b ON a.idUsuario=b.id "
					+ "WHERE b.id IS NOT NULL "
					+ "ORDER BY a.puntaje DESC "
					+ "LIMIT 10");
			ResultSet rs = query.executeQuery();
			while (rs.next()) {
				unJugador = new JugadorVo();
				unJugador.setNombre(rs.getString("nombre"));
				unJugador.setDificultad(rs.getString("dificultad"));
				unJugador.setPuntaje(rs.getInt("puntaje"));
				listJugadores.add(unJugador);
			}

		} catch (SQLException e) {
			// TODO: handle exception
		}

		return listJugadores;
	}

	public ArrayList<JugadorVo> getPartidas() {
		ArrayList<JugadorVo> listJugadores = new ArrayList<JugadorVo>();
		Conexion conex = new Conexion();
		JugadorVo unJugador;
		try {
			PreparedStatement query = conex.getConnection()
					.prepareStatement("SELECT a.* FROM usuarios  a ORDER BY a.nombre");
			ResultSet rs = query.executeQuery();
			while (rs.next()) {
				unJugador = new JugadorVo();
				unJugador.setId(rs.getInt("id"));
				unJugador.setNombre(rs.getString("nombre"));
				unJugador.setMapa(rs.getInt("mapa"));
				unJugador.setDificultad(rs.getString("dificultad"));
				unJugador.setPuntaje(rs.getInt("puntaje"));
				unJugador.setVidas(rs.getInt("Vidas"));
				listJugadores.add(unJugador);
			}

		} catch (SQLException e) {
			// TODO: handle exception
		}

		return listJugadores;
	}

	public boolean existeJugador(String nombre) {

		Conexion conex = new Conexion();
		boolean existe = false;

		try {
			PreparedStatement consulta = conex.getConnection()
					.prepareStatement("SELECT * FROM usuarios WHERE nombre = ? ");
			consulta.setString(1, nombre);
			ResultSet res = consulta.executeQuery();
			while (res.next()) {
				existe = true;
			}
			res.close();
			conex.desconectar();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error, no se conecto");
			System.out.println(e);
		}

		return existe;
	}

	public void uctualizarUsuario(JugadorVo miJugador) {
		Conexion conex = new Conexion();
		try {
			String consulta = "UPDATE usuarios SET puntaje=?, mapa=?, dificultad=?, vidas=? WHERE  nombre=?;";
			PreparedStatement estatuto = conex.getConnection().prepareStatement(consulta);

			estatuto.setInt(1, miJugador.getPuntaje());
			estatuto.setInt(2, miJugador.getMapa());
			estatuto.setString(3, miJugador.getDificultad());
			estatuto.setInt(4, miJugador.getVidas());
			estatuto.setString(5, miJugador.getNombre());
			estatuto.executeUpdate();

		} catch (SQLException e) {

			System.out.println(e);

		}
	}

	public void registrarPuntaje(String nombre,String dificultad, int puntajeFinal) {
		// INSERT INTO puntajesfinales (idUsuario , puntaje) VALUES ((select id
		// from usuarios WHERE nombre='Andres'), '20000000');

		Conexion conex = new Conexion();

		try {
			Statement sentencia = conex.getConnection().createStatement();

			String sql = "INSERT INTO puntajesfinales (idUsuario ,dificultad, puntaje) "
					+ "VALUES ((select id from usuarios WHERE nombre='" + nombre + "'),'"+dificultad+"' ,'" + puntajeFinal + "');";

			sentencia.executeUpdate(sql);
			sentencia.close();
			conex.desconectar();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("No se Registro");
		}

	}

}
