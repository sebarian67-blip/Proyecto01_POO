package Control;

import java.util.List;
import Logica.Etiqueta;
import Logica.Ingrediente;
import Logica.Item;
import Logica.Linea;
import Logica.Receta;
import Logica.Utensilio;
import Logica.Dificultad;
import Excepciones.RecetaNoExisteException;
import Excepciones.RecetaNoValidaException;
import java.awt.Image;

public class ControlRecetas {

	private List<Ingrediente> ingredientes;
	private List<Receta> recetas;
	private List<Etiqueta> etiquetas;
	private List<Utensilio> utensilios;
	
	public ControlRecetas(List<Ingrediente> ingredientes, List<Receta> recetas, List<Etiqueta> etiquetas,
			List<Utensilio> utensilios) {
		super();
		this.ingredientes = ingredientes;
		this.recetas = recetas;
		this.etiquetas = etiquetas;
		this.utensilios = utensilios;
	}
	
	public void addReceta(String nombre, Double tiempo, Integer porciones, String procedimiento,
			List<Image> fotos, Dificultad dificultad, List<Utensilio> utensilios,
			List<Etiqueta> etiquetas, List<Linea> lineas) throws RecetaNoValidaException{
		StringBuilder errores = new StringBuilder();
		if (nombre == null || nombre.trim().isEmpty()) {
			errores.append("El nombre está vacío.\n");
		} else {
			for (Receta receta : recetas) {
				if (receta.getNombre().trim().equalsIgnoreCase(nombre.trim())) {
					errores.append("Ya existe una receta con este nombre.\n");
				}
			}
		}
		if (lineas == null || lineas.isEmpty()) {
			errores.append("La receta debe tener al menos un ingrediente. \n");
		}
		if (procedimiento == null || procedimiento.trim().isEmpty()) {
			errores.append("La receta no tiene procedimiento. \n");
		}
		if (errores.length() > 0) {
			throw new RecetaNoValidaException(errores.toString());
		}
		Receta nuevaReceta = new Receta(nombre.trim(), tiempo, porciones, procedimiento,
				fotos, dificultad, utensilios, etiquetas, lineas);
		
		recetas.add(nuevaReceta);
		recetas.sort((r1, r2) -> r1.getNombre().compareToIgnoreCase(r2.getNombre()));
	}
	
	public Receta verReceta(int pos) throws RecetaNoExisteException{
		if (verificarExistenciaReceta(pos)) {  //Este método no está creado aún.
			return recetas.get(pos);
		} else throw new RecetaNoExisteException("La receta no existe.");
		//RecetaNoExisteException es una excepcion propia que está en el package excepciones
	}
	
	public List<Ingrediente> verListaIngredientes() {
		return ingredientes;
	}
	public void addIngrediente(String nombre) throws Exception {
		if (revisarNull(nombre))
			throw new Exception("Nombre no valido");
		Ingrediente ingredientre = new Ingrediente(nombre);
		ingredientes.add(ingredientre);
	}
	
	public void editarIngrediente(int pos, String nombre) throws Exception {
		if (!verificarExistenciaIngrediente(pos))
			throw new Exception("Posicion no valida");
		Ingrediente temp = ingredientes.get(pos);
		temp.setNombre(nombre);
	}
	
	public void eliminarIngrediente(int pos) throws Exception {
		if (!verificarExistenciaIngrediente(pos)) {  //para borrar hay que revisar que el indice sea correcto
			throw new Exception("Posicion no valida");
		}
		if (verificarItemUsado(ingredientes.get(pos), 1)) {
			throw new Exception ("No se puede eliminar un ingrediente en uso"); //y que el item no sea usado en una receta
		}
		ingredientes.remove(pos);
	}
	
	public void cambiarDisponibleIngrediente(int pos) throws Exception {
		if (!verificarExistenciaIngrediente(pos)) {
			throw new Exception("Posicion no valida");
		}
		ingredientes.get(pos).setDisponible();
	}
	
	public List<Utensilio> verListaUtensilios() {
		return utensilios;
	}
	
	public void addUtensilio(String nombre) throws Exception {
		if (revisarNull(nombre))
			throw new Exception ("Nombre del Utensilio no valido");
		Utensilio temp = new Utensilio(nombre);
		utensilios.add(temp);
	}
	
	public void editUtensilio(int pos, String nombre) throws Exception {
		if (!verificarExisnteciaUtensilios(pos))
			throw new Exception ("Posicion de utensilios no valida");
		utensilios.get(pos).setNombre(nombre);
	}
	
	public void eliminarUtensilio(int pos) throws Exception {
		if (!verificarExisnteciaUtensilios(pos))
			throw new Exception ("Posicion de utensilios no valida"); //para borrar hay que revisar que el indice sea correcto
		if (verificarItemUsado(utensilios.get(pos), 2)) {
			throw new Exception ("No se puede borrar un utensilio usado"); //y que el item no sea usado en una receta
		}
		utensilios.remove(pos);
	}
	
	public void marcarDisponibleUtensilio(int pos) throws Exception {
		if (!verificarExisnteciaUtensilios(pos))
			throw new Exception ("Posicion de utensilios no valida");
		utensilios.get(pos).setDisponible();
	}
	
	//AQUI VAN PAR REVISAR LA VALIDES DE LAS COSAS CON BOOLEANOS
	private boolean revisarNull(String texto) {
		return texto == null || texto == "" || texto == " ";
	}
	
	private boolean verificarExistenciaIngrediente(int pos) {
		return pos >= 0 && pos < ingredientes.size();
	}
	
	private boolean verificarExisnteciaUtensilios(int pos) {
		return pos >= 0 && pos < ingredientes.size();
	}
	
	private boolean verificarItemUsado(Item item, int caso) { //como ingrediente y utensilio son Item, se hace solo un metodo
															  //para revisar que son usados o no
		if (caso == 1) {
			for (Receta r : recetas) {
				List<Linea> temp = r.getLineas();
				for (Linea l : temp) {
					if (l.getIngrediente() == item)
						return true;
				}
			}
			
		}
		else if (caso == 2) {
			for (Receta r : recetas) {
				List<Utensilio> tempU = r.getUtensilios();
				if (tempU.contains(item))
				return true;
			}
		}
		return false;
	}
	
	private boolean verificarExistenciaReceta(int pos) {
		return true;
	}
}
