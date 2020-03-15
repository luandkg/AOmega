package Objetos;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import AOmega.Core.Camera;
import Componentes.Entidade;
import Constantes.Constantes;

public class Terrenos {

	private Camera mCamera;
	private ArrayList<Entidade> mLS_Entidades;
	private Nomeador NomeadorC;

	public Terrenos(Camera eCamera, ArrayList<Entidade> eLS_Entidades) {
		mCamera = eCamera;
		mLS_Entidades = eLS_Entidades;

		NomeadorC = new Nomeador();

	}

	public Terreno Terrenos_Criar(String eNome, int eX, int eY, int eTamLargura) {

		Terreno T2 = new Terreno(eNome, eX * Constantes.QUADRANTE, eY * Constantes.QUADRANTE,
				eTamLargura * Constantes.QUADRANTE);
		T2.setCor(Cor_Aletoaria());
		T2.setCamera(mCamera);
		mLS_Entidades.add(T2);

		return T2;
	}

	public Color Cor_Aletoaria() {
		Color ret = Color.GRAY;

		Random R = new Random();

		int e = R.nextInt(200);

		if (e < 30) {
			ret = new Color(44, 50, 160);
		} else if (e > 30 && e < 50) {
			ret = new Color(255, 87, 34);
		} else if (e > 50 && e < 75) {
			ret = new Color(139, 195, 74);
		} else if (e > 75 && e < 100) {
			ret = new Color(255, 235, 59);
		} else if (e > 100 && e < 125) {
			ret = new Color(103, 58, 183);
		} else {
			ret = new Color(44, 156, 112);

		}

		return ret;
	}

	public void Aleatorio() {

		int ty = 5;
		Random R = new Random();
		for (int tx = 0; tx < 50; tx++) {

			int SX = -100 + R.nextInt(10) + (-4);
			int TX = 10 + R.nextInt(25);

			String eNome = NomeadorC.Nome();

			for (int s = 0; s < 10; s++) {

				SX += R.nextInt(5) + 5;

				int SX2 = R.nextInt(10) + (-4);
				int TX2 = 10 + R.nextInt(25);

				SX += SX2;

				Terreno TC = Terrenos_Criar(eNome, SX, ty, TX2);

				int QDTP = R.nextInt(5);
				for (int iP = 0; iP < QDTP; iP++) {

					int TXA = R.nextInt(TX2 - 1);

					Terreno TCA = Terrenos_Criar(eNome, SX + TXA, ty - 1, 1);
					TCA.setCor(TC.getCor());

				}

				int QDTP2 = R.nextInt(5);
				for (int iP = 0; iP < QDTP2; iP++) {

					int TXA = R.nextInt(TX2 - 1);

					Terreno TCA = Terrenos_Criar(eNome, SX + TXA, ty + 1, 1);
					TCA.setCor(TC.getCor());

				}

				SX += TX2;
			}

			ty += 5;
		}

	}

	public void Terreno_Vertical_Criar(String eNome, int eX, int eY, int eAltura, Color eCor) {

		for (int ea = eY; ea < (eAltura + eY); ea++) {

			Terreno T2 = new Terreno(eNome, eX * Constantes.QUADRANTE, ea * Constantes.QUADRANTE,
					1 * Constantes.QUADRANTE);
			T2.setCor(eCor);
			T2.setCamera(mCamera);
			mLS_Entidades.add(T2);

		}

	}

	public void Terreno_Horizontal_Criar(String eNome, int eX, int eY, int eTam, Color eCor) {

		Terreno T2 = new Terreno(eNome, eX * Constantes.QUADRANTE, eY * Constantes.QUADRANTE,
				eTam * Constantes.QUADRANTE);
		T2.setCor(eCor);
		T2.setCamera(mCamera);
		mLS_Entidades.add(T2);

	}

}
