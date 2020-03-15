package Constantes;

public class Constantes {

	public static int QUADRANTE = 30;
	public static int CAIXA_QUADRANTE_SIMPLES = 30;
	public static int UNIDADE = 10;
	public static int GRAVIDADE = 3;

	public static float ESPACUM = 0.10f;
	public static int TEMPUM = 300;
	
	
	public static int CAMPO_PEQUENO = 1;
	public static int CAMPO_MEDIO = 3;
	public static int CAMPO_GRANDE = 5;
	public static int CAMPO_GIGANTE = 8;
	public static int HIPER_CAMPO = 12;

	
	
	public static float getEspacoPorTempoDeQueda(int eTempo) {
		
		float Tempo = (((float) eTempo) * ((float) eTempo)) / 2;
		
		return ( Tempo * GRAVIDADE )*ESPACUM;
		
	}
	
	public static float getEspacoPorUnidade(int eQuantidade) {
		
		return eQuantidade*ESPACUM;
		
	}
	
	
	
}
