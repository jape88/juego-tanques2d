package modelos;

public class JugadorVo {
	
	private int id;
	private String nombre;
	private int puntaje;
	private int mapa;
	private String dificultad;
	private int vidas;
	
	public JugadorVo(int id,String nombre, int puntaje, int mapa, String dificultad) {
		super();
		this.setId(id);
		this.nombre = nombre;
		this.puntaje = puntaje;
		this.mapa = mapa;
		this.dificultad = dificultad;
	}

	public JugadorVo(){
		super();
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getPuntaje() {
		return puntaje;
	}
	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}

	public int getMapa() {
		return mapa;
	}

	public void setMapa(int mapa) {
		this.mapa = mapa;
	}

	public String getDificultad() {
		return dificultad;
	}

	public void setDificultad(String dificultad) {
		this.dificultad = dificultad;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVidas() {
		return vidas;
	}

	public void setVidas(int vidas) {
		this.vidas = vidas;
	}

	

	
}
