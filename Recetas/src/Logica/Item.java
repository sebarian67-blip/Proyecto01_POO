package Logica;

public abstract class Item {
	protected String nombre;
	protected Boolean disponible;
	public Item(String nombre) {
		this.nombre = nombre;
		this.disponible = true;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Boolean getDisponible() {
		return disponible;
	}
	public void setDisponible() {
		this.disponible = !this.disponible;
		//Este setDisponible es como un botón, si se usa solo pasa a lo contrario de su estado actual.
	}
}