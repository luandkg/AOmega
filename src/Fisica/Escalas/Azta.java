package Fisica.Escalas;

public class Azta {

	public static final float AZTA = 2.0f;
	public static final String SUFIXO = "AZ";

	public static float CalorToTemperatura(float eQuantidadedeDeCalor) {

		return eQuantidadedeDeCalor / AZTA;

	}

	public static float TemperaturaToCalor(float eTemperatura) {

		return eTemperatura * AZTA;

	}

	public static float TemperaturaDoMaterial(float eTemperatura, float eTaxaCalorimetroMaterial) {

		return eTemperatura / eTaxaCalorimetroMaterial;

	}

	public static String AZTA_Escala(float eTemperatura_AZTA) {
		String ret = "FRIO ABSOLUTO";

		if (eTemperatura_AZTA >= 0 && eTemperatura_AZTA < 30) {
			ret = "FRIO";
		}
		if (eTemperatura_AZTA >= 30 && eTemperatura_AZTA < 60) {
			ret = "NORMAL";
		}

		if (eTemperatura_AZTA >= 60 && eTemperatura_AZTA < 100) {
			ret = "QUENTE";
		}

		if (eTemperatura_AZTA > 100) {
			ret = "QUENTE ABSOLUTO";
		}

		return ret;
	}

	public static String AZTA_SubEscala(float eTemperatura_AZTA) {
		String ret = "FRIO ABSOLUTO";

		if (eTemperatura_AZTA >= 0 && eTemperatura_AZTA < 30) {
			ret = "FRIO";

			if (eTemperatura_AZTA >= 0 && eTemperatura_AZTA <= 10) {
				ret = "MUITO FRIO";
			}

		}

		if (eTemperatura_AZTA >= 30 && eTemperatura_AZTA < 60) {
			ret = "NORMAL";
		}

		if (eTemperatura_AZTA >= 60 && eTemperatura_AZTA <= 100) {
			ret = "QUENTE";

			if (eTemperatura_AZTA >= 90 && eTemperatura_AZTA <= 100) {
				ret = "MUITO QUENTE";
			}

		}

		if (eTemperatura_AZTA > 100) {
			ret = "QUENTE ABSOLUTO";
		}

		return ret;
	}

}
