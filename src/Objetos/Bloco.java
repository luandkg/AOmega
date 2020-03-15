package Objetos;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import AOmega.Core.Camera;
import Componentes.Entidade;
import Componentes.FloatStatus;
import Componentes.Posicao;
import Componentes.Tamanho;

public class Bloco extends Entidade {

	private Posicao mPosicao;
	private Tamanho mTamanho;
	private FloatStatus mRotacao;

	private BufferedImage mLayer1;
	private BufferedImage mLayer2;
	private BufferedImage mLayer3;
	private float mEscala;
	private AOmega.Core.Camera mCamera;

	public Bloco(int eX, int eY, int eTamanho) {

		mPosicao = (Posicao) this.adicionarEObterComponente(new Posicao(eX, eY));
		mTamanho = (Tamanho) this.adicionarEObterComponente(new Tamanho(eTamanho, eTamanho));
		mRotacao = (FloatStatus) this.adicionarEObterComponente(new FloatStatus("Rotacao", 0.0));

		mLayer1 = null;
		mLayer2 = null;
		mLayer3 = null;
		mEscala = 1.0f;
		mCamera = new Camera(0, 0, 0, 0, 1);
	}

	public Posicao getPos() {
		return mPosicao;
	}

	public float getRotacao() {
		return (float) mRotacao.getConteudo();
	}

	public void setRotacao(float eRotacao) {
		while (eRotacao > 360) {
			eRotacao -= 360;
		}

		while (eRotacao < -360) {
			eRotacao += 360;
		}

		mRotacao.setConteudo((float) eRotacao);
	}

	public float getEscala() {
		return mEscala;
	}

	public void setCamera(Camera eCamera) {
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

	public void setImagem(BufferedImage eLayer1, BufferedImage eLayer2, BufferedImage eLayer3) {
		mLayer1 = eLayer1;
		mLayer2 = eLayer2;
		mLayer3 = eLayer3;

	}

	@Override
	public void update(double dt) {

		// System.out.println(this.toString());

	}

	@Override
	public void draw(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;

		AffineTransform T = new AffineTransform();
		T.setToIdentity();
		T.translate(mPosicao.getX() - mCamera.getX(), mPosicao.getY() - mCamera.getY());
		T.rotate(mRotacao.getConteudo(), ((mTamanho.getLargura()) / 2), ((mTamanho.getAltura()) / 2));
		T.scale(mEscala, mEscala);

		g2.drawImage(mLayer1, T, null);
		g2.drawImage(mLayer2, T, null);
		g2.drawImage(mLayer3, T, null);

	}

}
