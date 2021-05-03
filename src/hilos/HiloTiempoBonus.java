package hilos;

import vistas.Board;

public class HiloTiempoBonus implements Runnable{

	private int multiplicador;
	private Board board;
	
	public HiloTiempoBonus(int multiplicador,Board board) {
		super();
		this.multiplicador = multiplicador;
		this.board=board;
	}

	

	public int getMultiplicador() {
		return multiplicador;
	}



	@Override
	public void run() {
		for (int i = 0; i < 5; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		multiplicador=1;
		board.setMultiplicador(multiplicador);
	}

	
	
}
