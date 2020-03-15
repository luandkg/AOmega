package Objetos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import AOmega.Estrutural.Rect;
import Componentes.Entidade;
import Componentes.FloatStatus;
import Componentes.Posicao;
import Componentes.Tamanho;
import Constantes.Constantes;
import Fisica.Componentes.Colisao;

public class Terreno extends Entidade {

	private Posicao mPosicao;
	private Tamanho mTamanho;
	private FloatStatus mRotacao;
	private Colisao mColisao;

	private float mEscala;
	private AOmega.Core.Camera mCamera;
	private Color mCor;

	public Terreno(String eNome, int eX, int eY, int eComprimento) {

		setTipo("Terreno");
		setNome(eNome);

		mCor = Color.black;

		mEscala = 1.0f;
		mCamera = new AOmega.Core.Camera(0, 0, 0, 0, 1);

		mPosicao = (Posicao) this.adicionarEObterComponente(new Posicao(eX, eY));
		mTamanho = (Tamanho) this.adicionarEObterComponente(new Tamanho(eComprimento, Constantes.QUADRANTE));
		mRotacao = (FloatStatus) this.adicionarEObterComponente(new FloatStatus("Rotacao", 0.0));
		mColisao = (Colisao) this.adicionarEObterComponente(new Colisao(mPosicao, mTamanho));

	}

	public Posicao getPos() {
		return mPosicao;
	}

	public Tamanho getTam() {
		return mTamanho;
	}

	public Color getCor() {
		return mCor;
	}

	public float getRotacao() {
		return (float) mRotacao.getConteudo();
	}

	public void setRotacao(float eRotacao) {
		while (eRotacao > 360) {
			eRotacao -= 360;
		}
		mRotacao.setConteudo((float) eRotacao);
	}

	public float getEscala() {
		return mEscala;
	}

	public void setCamera(AOmega.Core.Camera eCamera) {
		mCamera = eCamera;
	}

	public void setEscala(float eEscala) {
		mEscala = eEscala;
		mTamanho.setLargura((int) ((mTamanho.getLargura() * mEscala)));
		mTamanho.setAltura((int) ((mTamanho.getAltura() * mEscala)));
	}

	public void setPos(int eX, int eY) {
		mPosicao.setX(eX);
		mPosicao.setY(eY);
	}

	public void setCor(Color eCor) {
		mCor = eCor;

	}

	@Override
	public void update(double dt) {

	}

	public Rect getAreaCorpo() {
		return new Rect(mPosicao.getX() - mCamera.getX(), mPosicao.getY() - mCamera.getY(), mTamanho.getLargura(),
				mTamanho.getAltura());
	}

	@Override
	public void draw(Graphics g) {

		g.setColor(mCor);
		g.fillRect(mPosicao.getX() - mCamera.getX(), mPosicao.getY() - mCamera.getY(), mTamanho.getLargura(),
				mTamanho.getAltura());

	}

}
