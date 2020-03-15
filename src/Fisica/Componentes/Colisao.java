package Fisica.Componentes;

import java.util.ArrayList;

import AOmega.Core.Camera;
import AOmega.Core.Contato;
import AOmega.Estrutural.Rect;
import AOmega.Estrutural.Vetor2;
import Componentes.Componente;
import Componentes.Posicao;
import Componentes.Tamanho;
import Constantes.Constantes;

public class Colisao extends Componente {

	public static final String ID = "Colisao";

	private Posicao mPosicao;
	private Tamanho mTamanho;
	private Contato mContato;
	private int mRestringirCorpoX;
	private int mRestringirCorpoY;

	public Colisao(Posicao ePosicao, Tamanho eTamanho) {
		super(ID);

		mPosicao = ePosicao;
		mTamanho = eTamanho;
		mContato = new Contato();
		mRestringirCorpoX = 0;
		mRestringirCorpoY = 0;
	}

	public String toString() {
		return " - " + "Colisao { X = " + mPosicao.getX()+ " , Y = " + mPosicao.getY() + " , Largura = " + mTamanho.getLargura()+ " , Altura = " + mTamanho.getAltura()+ " } ";
	}
	public void RestringirCorpo(int eX, int eY) {

		mRestringirCorpoX = eX;
		mRestringirCorpoY = eY;
	}

	public Rect getArea() {
		return new Rect(mPosicao.getX() + mRestringirCorpoX, mPosicao.getY() + mRestringirCorpoY, mTamanho.getLargura(),
				mTamanho.getAltura());
	}

	public void setPos(int eX, int eY) {
		mPosicao.setX(eX);
		mPosicao.setY(eY);
	}

	public Posicao getPos() {
		return mPosicao;
	}

	public ArrayList<Vetor2> Ocupando() {
		return mContato.Ocupando(getArea());
	}

	public ArrayList<Vetor2> OcupandoCom(Vetor2 SomarLocal) {
		return mContato.OcupandoCom(getArea(), SomarLocal);
	}

	public boolean ColideArea(Rect eArea) {
		return mContato.ColideArea(getArea(), eArea);
	}

	public boolean Colide(Vetor2 eV) {
		return mContato.Colide(getArea(), eV);
	}

	public boolean ColideGrupo(ArrayList<Vetor2> LS_2) {
		return mContato.ColideGrupo(getArea(), LS_2);
	}
}
