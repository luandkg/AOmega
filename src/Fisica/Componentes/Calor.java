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

public class Calor extends Componente {

	public static final String ID = "Calor";

	private Posicao mPosicao;
	private Tamanho mTamanho;

	private float mEmissor;
	private int mCampo;

	private float mQuantidadeDeCalor;
	private Contato mContato;
private ArrayList<String> mAplicadores;

	public Calor(Posicao ePosicao, Tamanho eTamanho) {
		super(ID);

		mPosicao = ePosicao;
		mTamanho = eTamanho;

		mEmissor = 0;
		mCampo = 0;
		mQuantidadeDeCalor = 0;
		mContato = new Contato();
		mAplicadores =new ArrayList<String>();
		
	}

	public void Mostrar_Regiao(Graphics g, Camera mCamera, Color eRegiaoCor) {

		//if (getCampo() > 0) {

			g.setColor(eRegiaoCor);

			for (Rect v : OcupandoAreas()) {

				g.drawRect(v.getX() - mCamera.getX(), v.getY() - mCamera.getY(), v.getLargura(), v.getAltura());

			}

	//	}

	}

	public void Mostrar_RegiaoOriginal(Graphics g, Camera mCamera, Color eRegiaoCor) {

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

	public void setEmissao(float eEmissor) {
		mEmissor = eEmissor;
	}

	public float getEmissao() {
		return mEmissor;
	}

	public void setCampo(int eCampo) {
		mCampo = eCampo;

	}

	public int getCampo() {
		return mCampo;
	}

	public float getQuantidadeDeCalor() {
		return mQuantidadeDeCalor;
	}

	public void QuantidadeDeCalor_Nivelar(float eValor) {
		mQuantidadeDeCalor = eValor;
		mAplicadores.clear();
	}

	public void QuantidadeDeCalor_Equilibrar(String eAplicador,float eValor) {
		if (!mAplicadores.contains(eAplicador)) {
		
			mQuantidadeDeCalor += eValor;

			
		}
	}

	public ArrayList<Vetor2> Ocupando() {
		return mContato.Ocupando(getAreaDoCampo());
	}

	public ArrayList<Rect> OcupandoAreas() {
		return mContato.OcupandoAreas(getAreaDoCampo());
	}

	public ArrayList<Vetor2> OcupandoCom(Vetor2 SomarLocal) {
		return mContato.OcupandoCom(getAreaDoCampo(), SomarLocal);
	}

	public boolean Temp_Interfere(Rect eArea) {

		return mContato.ColideArea(getAreaDoCampo(), eArea);
	}

	public String toString() {
		return " - " + "Calor { Campo = " + mCampo + " , Emissao = " + mEmissor + " } ";
	}
	
	
}
