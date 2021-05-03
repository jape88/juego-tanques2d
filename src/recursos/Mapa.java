package recursos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Mapa {

	private int numMapa;
	private String rutaMapa;
	private int[][] posBlocks, posConcreto, posAgua, posPasto;

	public Mapa(int numMapa) {
		super();
		this.numMapa = numMapa;
		rutaMapa = "src/mapas/mapa" + this.numMapa + ".txt";

		try {
			cargarMapa(rutaMapa);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int[][] getPosBlocks() {
		return posBlocks;
	}

	public int[][] getPosConcreto() {
		return posConcreto;
	}

	public int[][] getPosAgua() {
		return posAgua;
	}

	public int[][] getPosPasto() {
		return posPasto;
	}

	private void cargarMapa(String archivo) throws FileNotFoundException, IOException {
		String cadena;

		FileReader f = new FileReader(archivo);
		BufferedReader b = new BufferedReader(f);
		ArrayList<String> lineasXY = new ArrayList<>();
		String contActual = "";
		while ((cadena = b.readLine()) != null) {

			String caracter1 = cadena.substring(0, 1);
			if (!isNumber(caracter1)) {
				if (contActual.equals("ladrillos")) {
					llenarMatrizLadrillos(lineasXY);
				} else if (contActual.equals("concreto")) {
					llenarMatrizConcreto(lineasXY);
				} else if (contActual.equals("agua")) {
					llenarMatrizAgua(lineasXY);
				} else if (contActual.equals("pasto")) {
					llenarMatrizPasto(lineasXY);
				}
				contActual = cadena;
				lineasXY.clear();

			} else {
				lineasXY.add(cadena);
			}
		}
		b.close();
	}

	private void llenarMatrizLadrillos(ArrayList<String> lineasXY) {
		posBlocks = new int[lineasXY.size()][2];
		posBlocks = llenarMatriz(lineasXY, posBlocks);
	}

	private void llenarMatrizPasto(ArrayList<String> lineasXY) {
		posPasto = new int[lineasXY.size()][2];
		posPasto = llenarMatriz(lineasXY, posPasto);
	}

	private void llenarMatrizAgua(ArrayList<String> lineasXY) {
		posAgua = new int[lineasXY.size()][2];
		posAgua = llenarMatriz(lineasXY, posAgua);
	}

	private void llenarMatrizConcreto(ArrayList<String> lineasXY) {
		posConcreto = new int[lineasXY.size()][2];
		posConcreto = llenarMatriz(lineasXY, posConcreto);
	}

	private int[][] llenarMatriz(ArrayList<String> lineasXY, int[][] matriz) {
		int posSepador;
		String palActual;
		int x;
		int y;
		for (int i = 0; i < lineasXY.size(); i++) {
			palActual = lineasXY.get(i);
			posSepador = getSeparador(palActual);
			x = Integer.parseInt(palActual.substring(0, posSepador));
			y = Integer.parseInt(palActual.substring(posSepador + 1, palActual.length()));
			matriz[i][0] = x;
			matriz[i][1] = y;
		}
		return matriz;
	}

	private int getSeparador(String cadena) {
		char[] array = cadena.toCharArray();
		for (int i = 0; i < array.length; i++) {
			if (array[i] == ',')
				return i;
		}
		return 0;
	}

	private boolean isNumber(String palabra) {
		try {
			int num = Integer.parseInt(palabra);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
