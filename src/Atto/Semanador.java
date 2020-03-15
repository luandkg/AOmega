package Atto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Semanador {

	private double acumuladoDia;
	private double Semana;
	private double TaxaG1;
	private double TaxaG2;
	private double TaxaG3;

	private ArrayList<Faixa> mManhas;
	private ArrayList<Faixa> mTardes;
	private ArrayList<Faixa> mNoites;

	public Semanador() {

		acumuladoDia = 24 * 60 * 60;
		Semana = 7 * acumuladoDia;

		TaxaG1 = Semana / 10;
		TaxaG2 = TaxaG1 / 100;
		TaxaG3 = TaxaG2 / 100.0;

		mManhas = new ArrayList<Faixa>();
		mTardes = new ArrayList<Faixa>();
		mNoites = new ArrayList<Faixa>();

		for (int Dia = 1; Dia <= 7; Dia++) {

			mManhas.add(new Faixa(getAtto(Dia, 6, 0, 0).getTotal(), getAtto(Dia, 12, 59, 59).getTotal()));

			mTardes.add(new Faixa(getAtto(Dia, 13, 0, 0).getTotal(), getAtto(Dia, 18, 59, 59).getTotal()));

			mNoites.add(new Faixa(getAtto(Dia, 19, 0, 0).getTotal(), getAtto(Dia, 23, 59, 59).getTotal()));
			mNoites.add(new Faixa(getAtto(Dia, 0, 0, 0).getTotal(), getAtto(Dia, 5, 59, 59).getTotal()));

		}

	}

	public void AgoraLog() {

		System.out.println("DIA : " + acumuladoDia);
		System.out.println("SEMANA : " + Semana);

		System.out.println("TAXA G1 : " + TaxaG1);
		System.out.println("TAXA G2 : " + TaxaG2);
		System.out.println("TAXA G3 : " + TaxaG3);

		System.out.println("");

		Calendar c = Calendar.getInstance();
		c.setTime(new Date());

		String nome = "";
		int dia = c.get(GregorianCalendar.DAY_OF_WEEK);

		int eSegundo = c.getTime().getSeconds();
		int eMinuto = c.getTime().getMinutes();
		int eHora = c.getTime().getHours();
		double acumulado = ((dia - 1) * acumuladoDia) + ((eHora * 60 * 60) + (eMinuto * 60) + eSegundo);

		System.out.println("HOJE : " + acumulado);

		Atto A = getAtto(dia, eHora, eMinuto, eSegundo);

		System.out.println("");

		System.out.println("G1 : " + A.getAttos());
		System.out.println("G2 : " + A.getUggos());
		System.out.println("G3 : " + A.getEttos());

	}

	public static Atto Agora() {

		Calendar c = Calendar.getInstance();
		c.setTime(new Date());

		String nome = "";
		int dia = c.get(GregorianCalendar.DAY_OF_WEEK);

		int eSegundo = c.getTime().getSeconds();
		int eMinuto = c.getTime().getMinutes();
		int eHora = c.getTime().getHours();

		return new Semanador().getAtto(dia, eHora, eMinuto, eSegundo);
	}

	public Atto getAgora() {

		Calendar c = Calendar.getInstance();
		c.setTime(new Date());

		String nome = "";
		int dia = c.get(GregorianCalendar.DAY_OF_WEEK);

		int eSegundo = c.getTime().getSeconds();
		int eMinuto = c.getTime().getMinutes();
		int eHora = c.getTime().getHours();

		return getAtto(dia, eHora, eMinuto, eSegundo);
	}

	public double getAcumulado(int dia, int eHora, int eMinuto, int eSegundo) {
		return ((dia - 1) * acumuladoDia) + ((eHora * 60 * 60) + (eMinuto * 60) + eSegundo);
	}

	public Atto getAtto(int dia, int eHora, int eMinuto, int eSegundo) {

		double acumulado = ((dia - 1) * acumuladoDia) + ((eHora * 60 * 60) + (eMinuto * 60) + eSegundo);

		int G1 = 1;
		int G2 = 0;
		int G3 = 0;

		while (acumulado > TaxaG1) {
			acumulado -= TaxaG1;
			G1 += 1;
		}

		while (acumulado > TaxaG2) {
			acumulado -= TaxaG2;
			G2 += 1;
		}
		while (acumulado > TaxaG3) {
			acumulado -= TaxaG3;
			G3 += 1;
		}

		return new Atto(G1, G2, G3);
	}

	public String getFaixa(Atto eAtto) {
		String ret = "";
		int eTotal = eAtto.getTotal();

		for (Faixa FaixaC : mManhas) {

			if (eTotal >= FaixaC.getInicio() && eTotal <= FaixaC.getFim()) {
				ret = "Manhã";
			}

		}

		for (Faixa FaixaC : mTardes) {

			if (eTotal >= FaixaC.getInicio() && eTotal <= FaixaC.getFim()) {
				ret = "Tarde";
			}

		}

		for (Faixa FaixaC : mNoites) {

			if (eTotal >= FaixaC.getInicio() && eTotal <= FaixaC.getFim()) {
				ret = "Noite";
			}

		}

		return ret;
	}

	public static void Manhas() {

		System.out.println("ATTO Contador : " + Agora().getTotal());

		Semanador S = new Semanador();

		for (Faixa FaixaC : S.mManhas) {

			System.out.println("INICIO : " + FaixaC.getInicio());
			System.out.println("FIM " + FaixaC.getFim());
			System.out.println("");
		}

	}

	public static void Tardes() {

	}

	public static void Noites() {

	}
}
