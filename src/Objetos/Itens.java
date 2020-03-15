package Objetos;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import AOmega.Core.Camera;
import AOmega.Core.Imagens;
import AOmega.Core.SpriteSheet;
import AOmega.Organizacao.SuperGrupo;
import Componentes.Entidade;
import Constantes.Constantes;

public class Itens {

	private Camera mCamera;
	private ArrayList<Entidade> mLS_Entidades;
	private Nomeador NomeadorC;
	private Imagens mImageContainer;
	private SuperGrupo mSuperGrupo;

	public Itens(SuperGrupo eSuperGrupo,Camera eCamera, Imagens eImageContainer, ArrayList<Entidade> eLS_Entidades) {
		mSuperGrupo=eSuperGrupo;
		mCamera = eCamera;
		mLS_Entidades = eLS_Entidades;
		mImageContainer = eImageContainer;
		NomeadorC = new Nomeador();

	}

	public Item Criar_FogoDock(String eNome,int eX, int eY) {

		FireDock B2 = new FireDock(mSuperGrupo,mImageContainer, eNome, eX * Constantes.QUADRANTE,
				(eY - 1) * Constantes.QUADRANTE);
		B2.setCamera(mCamera);
		B2.setZIndex(10);
		B2.setTam(15, 15);
		B2.setEscala(2);
		B2.Ligar();

		mLS_Entidades.add(B2);
		return B2;
	}

	public Item Criar_Chave(String eNome,int eX, int eY) {

		Chave B2 = new Chave(mSuperGrupo,mImageContainer, eNome, eX * Constantes.QUADRANTE,
				(eY - 1) * Constantes.QUADRANTE);
		B2.setCamera(mCamera);
		B2.setZIndex(-10);
		B2.setTam(5, 5);
		B2.setEscala(4);

		mLS_Entidades.add(B2);
		return B2;
	}

}
