package Logica;

public class Linea {
	private String cantidad;
	private Ingrediente ingrediente;

	public Linea(String cantidad, Ingrediente ingrediente) {
		this.cantidad = cantidad;
		this.ingrediente = ingrediente;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public Ingrediente getIngrediente() {
		return ingrediente;
	}

	public void setIngrediente(Ingrediente ingrediente) {
		this.ingrediente = ingrediente;
	}
	
}
