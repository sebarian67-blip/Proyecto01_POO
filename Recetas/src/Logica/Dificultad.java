package Logica;

public enum Dificultad {
	FACIL,
	INTERMEDIO,
	DIFICIL,
	EXPERTO,
	LEGENDARIO;
	
	public String toString() {
		switch (this) {
			case FACIL:
				return "Facil";
			case INTERMEDIO:
				return "Intermedio";
			case DIFICIL:
				return "Dificil";
			case EXPERTO:
				return "Experto";
			case LEGENDARIO:
				return "Legendario";
			default:
				return "Dificultad no puesta";
		}
	}
}
