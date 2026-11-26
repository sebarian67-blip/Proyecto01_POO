package Logica;

import java.awt.Image;
import java.util.LinkedList;
import java.util.List;


public class Receta {
	private String nombre;
	private Double tiempo;
	private Integer porciones;
	private String procedimiento;
	private List<Image> fotos;
	private Dificultad dificultad;
	private List<Utensilio> utensilios;
	private List<Etiqueta> etiquetas;
	private List<Linea> lineas;
	public Receta(String nombre, Double tiempo, Integer porciones, String procedimiento, List<Image> fotos,
			Dificultad dificultad, List<Utensilio> utensilios, List<Etiqueta> etiquetas, List<Linea> lineas) {
		this.nombre = nombre;
		this.tiempo = tiempo;
		this.porciones = porciones;
		this.procedimiento = procedimiento;
		this.fotos = new LinkedList<>();
		this.dificultad = dificultad;
		this.utensilios = new LinkedList<>();
		this.etiquetas = new LinkedList<>();
		this.lineas = new LinkedList<>();
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Double getTiempo() {
		return tiempo;
	}
	public void setTiempo(Double tiempo) {
		this.tiempo = tiempo;
	}
	public Integer getPorciones() {
		return porciones;
	}
	public void setPorciones(Integer porciones) {
		this.porciones = porciones;
	}
	public String getProcedimiento() {
		return procedimiento;
	}
	public void setProcedimiento(String procedimiento) {
		this.procedimiento = procedimiento;
	}
	public Dificultad getDificultad() {
		return dificultad;
	}
	public void setDificultad(Dificultad dificultad) {
		this.dificultad = dificultad;
	}
	
	public List<Image> getFotos() {
		return fotos;
	}
	public void setFoto(Image foto) {
		fotos.add(foto);
	}
	public void removeFoto(int pos) {
		fotos.remove(pos);
	}
	public List<Utensilio> getUtensilios() {
		return utensilios;
	}
	public void setUntesilio(Utensilio utensilio) {
		utensilios.add(utensilio);
	}
	public void removeUtensilio(int pos) {
		utensilios.remove(pos);
	}
	public List<Etiqueta> getEtiquetas() {
		return etiquetas;
	}
	public void setEtiqueta(Etiqueta etiqueta) {
		etiquetas.add(etiqueta);
	}
	public void remveEtiqueta(int pos) {
		etiquetas.remove(pos);
	}
	public List<Linea> getLineas() {
		return lineas;
	}
	public void setLinea(String cantidad, Ingrediente ingrediente) {
		Linea linea = new Linea(cantidad, ingrediente);
		lineas.add(linea);
	}
	public void removeLinea(int pos) {
		lineas.remove(pos);
	}
}
