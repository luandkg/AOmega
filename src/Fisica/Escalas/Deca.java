package Fisica.Escalas;

public class Deca {

	public static final float AZTA = 2.0f;
	
	public static final String SUFIXO_DC = "m";
	
	public static final String SUFIXO_MC = "MC";
	public static final String SUFIXO_IC = "IC";

	
	
	public static String get(float eValor) {
		
		return String.format("%.2f",eValor) + " " + SUFIXO_DC;
		
	}
}
