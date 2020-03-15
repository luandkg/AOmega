package Objetos;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import AOmega.Core.Camera;
import AOmega.Core.Imagens;
import Componentes.Entidade;
import Constantes.Constantes;

public class Personagens {

	private Camera mCamera;
	private ArrayList<Entidade> mLS_Entidades;
	private Nomeador NomeadorC;
	private Imagens mImageContainer;

	public Personagens(Camera eCamera, Imagens eImageContainer, ArrayList<Entidade> eLS_Entidades) {
		mCamera = eCamera;
		mLS_Entidades = eLS_Entidades;
		mImageContainer = eImageContainer;
		NomeadorC = new Nomeador();

	}

	public Bloquinho Criar_Jogador(int eX, int eY, Color eCor) {

		Bloquinho B1 = new Bloquinho("A", eX * Constantes.QUADRANTE, eY * Constantes.QUADRANTE, Constantes.QUADRANTE);
		B1.setNome("Jogador");

		B1.setForca(+5);
		B1.setCampo(Constantes.CAMPO_MEDIO);

		B1.setTempCampo(Constantes.CAMPO_MEDIO);
		B1.setEmissao(50);

		B1.setCor(eCor);

		B1.setCamera(mCamera);
		B1.setEscala(1);

		B1.setImagem(mImageContainer.obter("SAPO"));

		mLS_Entidades.add(B1);

		return B1;
	}

	public void Criar_Fluz(int eX, int eY) {

		Bloquinho B2 = new Bloquinho("FLUZ " + mLS_Entidades.size(), eX * Constantes.QUADRANTE,
				eY * Constantes.QUADRANTE, Constantes.QUADRANTE);
		B2.setCamera(mCamera);
		B2.setEscala(1);
		B2.setCor(Color.GREEN);
		B2.setZIndex(-10);

		B2.setEmissao(30);
		B2.setTempCampo(Constantes.CAMPO_MEDIO);

		B2.setImagem(mImageContainer.obter("FRUZ"));

		mLS_Entidades.add(B2);
	}

	public void Criar_Nock(int eX, int eY) {

		Bloquinho B2 = new Bloquinho("NOCK " + mLS_Entidades.size(), eX * Constantes.QUADRANTE,
				eY * Constantes.QUADRANTE, Constantes.QUADRANTE);
		B2.setCamera(mCamera);
		B2.setEscala(1);
		B2.setCor(Color.RED);
		B2.setZIndex(-10);

		B2.setEmissao(+50);
		B2.setTempCampo( Constantes.CAMPO_PEQUENO);

		B2.setImagem(mImageContainer.obter("NOCK"));

		mLS_Entidades.add(B2);
	}

	public void Criar_Inko(int eX, int eY) {

		Bloquinho B2 = new Bloquinho("INKO " + mLS_Entidades.size(), eX * Constantes.QUADRANTE,
				eY * Constantes.QUADRANTE, Constantes.QUADRANTE);
		B2.setCamera(mCamera);
		B2.setEscala(1);
		B2.setCor(Color.YELLOW);
		B2.setZIndex(-10);

		B2.setEmissao(-10);
		B2.setTempCampo(Constantes.CAMPO_MEDIO);
		B2.setForca(+3);
		B2.setCampo(Constantes.CAMPO_MEDIO);
		
		B2.setImagem(mImageContainer.obter("INKO"));

		mLS_Entidades.add(B2);
	}

	public Bloquinho CriarBloquinho(int eX, int eY, Color eCor) {

		Bloquinho B2 = new Bloquinho("Bloco_" + mLS_Entidades.size(), eX * Constantes.QUADRANTE,
				eY * Constantes.QUADRANTE, Constantes.QUADRANTE);
		B2.setCamera(mCamera);
		B2.setEscala(1);
		B2.setCor(eCor);
		B2.setZIndex(-10);

		Random r = new Random();

		if (r.nextInt(10) > 5) {
			B2.setImagem(mImageContainer.obter("NOCK"));
		} else {
			B2.setImagem(mImageContainer.obter("FRUZ"));
		}

		mLS_Entidades.add(B2);
		return B2;
	}

}
