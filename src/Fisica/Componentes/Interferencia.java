package Fisica.Componentes;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import AOmega.Core.Camera;
import AOmega.Core.Contato;
import AOmega.Estrutural.Rect;
import AOmega.Estrutural.Vetor2;
import Componentes.Componente;
import Componentes.Posicao;
import Componentes.Tamanho;
import Constantes.Constantes;

public class Interferencia extends Componente {

	public static final String ID = "Interferencia";

	private Posicao mPosicao;
	private Tamanho mTamanho;

	private int mForca;
	private int mCampo;
	private Contato mContato;

	public Interferencia(Posicao ePosicao, Tamanho eTamanho) {
		super(ID);

		mPosicao = ePosicao;
		mTamanho = eTamanho;

		mForca = 0;
		mCampo = -1;
		mContato = new Contato();

	}

	public String toString() {
		return " - " + "Interferencia { Campo = " + mCampo + " , Força = " + mForca + " , X = " + mPosicao.getX()+ " , Y = " + mPosicao.getY() + " , Largura = " + mTamanho.getLargura()+ " , Altura = " + mTamanho.getAltura()+ " } ";
	}
	
	public void Mostrar_Regiao(Graphics g, Camera mCamera, Color eRegiaoCor) {

		if (getCampo() > 0) {

			g.setColor(eRegiaoCor);

			Rect area = getAreaDoCampo();
			g.drawRect(area.getX() - mCamera.getX(), area.getY() - mCamera.getY(), area.getLargura(), area.getAltura());

		}

	}

	public Rect getAreaDoCampo() {
		return new Rect(mPosicao.getX() - (mCampo*Constantes.UNIDADE), mPosicao.getY() - (mCampo*Constantes.UNIDADE), mTamanho.getLargura() + (2 * (mCampo*Constantes.UNIDADE)),
				mTamanho.getAltura() + (2 * (mCampo*Constantes.UNIDADE)));
	}

	public void setForca(int eForca) {
		mForca = eForca;

	}

	public int getForca() {
		return mForca;
	}

	public void setCampo(int eCampo) {
		mCampo = eCampo;

	}

	public int getCampo() {
		return mCampo;
	}

	public ArrayList<Vetor2> Ocupando() {
		return mContato.Ocupando(getAreaDoCampo());
	}

	public ArrayList<Vetor2> OcupandoCom(Vetor2 SomarLocal) {
		return mContato.OcupandoCom(getAreaDoCampo(), SomarLocal);
	}

	public boolean ColideArea(Rect eArea) {
		return mContato.ColideArea(getAreaDoCampo(), eArea);
	}

}
