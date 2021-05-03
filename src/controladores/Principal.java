package controladores;

import java.awt.EventQueue;

import recursos.Sonidos;
import vistas.VentanaPrincipal;

public class Principal {

	public static void main(String[] args) {
		Sonidos misSonidos = new Sonidos();
		
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				VentanaPrincipal ventana = new VentanaPrincipal(misSonidos);
				ventana.setVisible(true);
			}
		});
	}
}
