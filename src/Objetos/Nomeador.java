package Objetos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Nomeador {

	private String PREFIXO = "AT DE CO LA VE RUG NO CO BRA TRU GRI";
	private String RADICAIS = "OB OPL ID IKK EZZ IJ OF AV EOG IP UF AV AL ALH OLL";
	private String SUFIXOS = "OS UM ALL EG IS IZ OX UL ESH IR OR AZ";

	private ArrayList<String> P1;
	private ArrayList<String> P2;
	private ArrayList<String> P3;

	private int MAX_PREFIXO;
	private int MAX_RADICAIS;
	private int MAX_SUFIXOS;

	private Random Sorteia;

	public Nomeador() {

		P1 = new ArrayList<String>(Arrays.asList(PREFIXO.split(" ")));
		P2 = new ArrayList<String>(Arrays.asList(RADICAIS.split(" ")));
		P3 = new ArrayList<String>(Arrays.asList(SUFIXOS.split(" ")));

		MAX_PREFIXO = P1.size();
		MAX_RADICAIS = P2.size();
		MAX_SUFIXOS = P3.size();

		Sorteia = new Random();

	}

	public String Nome() {
		return P1.get(Sorteia.nextInt(MAX_PREFIXO)) + P2.get(Sorteia.nextInt(MAX_RADICAIS))
				+ P3.get(Sorteia.nextInt(MAX_SUFIXOS));
	}

}
