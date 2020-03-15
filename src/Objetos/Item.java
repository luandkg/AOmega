package Objetos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import AOmega.Core.Camera;
import AOmega.Core.Contato;
import AOmega.Estrutural.Rect;
import AOmega.Estrutural.Vetor2;
import AOmega.Organizacao.SuperGrupo;
import AOmega.Utils.Debugger;
import AOmega.Utils.Escritor;
import Componentes.Entidade;
import Componentes.FloatStatus;
import Componentes.Posicao;
import Componentes.Tamanho;
import Constantes.Constantes;
import Fisica.Componentes.Colisao;
import Fisica.Componentes.Interferencia;
import Fisica.Componentes.Luz;
import Fisica.Componentes.RayCast;
import Fisica.Escalas.Azta;
import Fisica.Componentes.Calor;

public class Item extends Entidade {

	private Posicao mPosicao;
	private Tamanho mTamanho;
	private FloatStatus mRotacao;
	private Colisao mColisao;
	private Interferencia mInterferencia;
	private Calor mCalor;
	private Luz mLuz;

	public RayCast mRayCast;

	
	private float mEscala;
	private AOmega.Core.Camera mCamera;
	private Color mCor;

	private BufferedImage mImagem;

	private Escritor mEscritorBranco;

	private int mQueda;
	private boolean mComGravidade;
private SuperGrupo mSuperGrupo;

	public Item(SuperGrupo eSuperGrupo,String eNome, int eX, int eY) {

		setNome(eNome);
		setTipo("Item");
		mSuperGrupo=eSuperGrupo;
		
		mCor = Color.black;

		mEscritorBranco = new Escritor(15, Color.BLACK);

		mEscala = 1.0f;
		mCamera = new AOmega.Core.Camera(0, 0, 0, 0, 1);

		mPosicao = (Posicao) this.adicionarEObterComponente(new Posicao(eX, eY));
		mTamanho = (Tamanho) this.adicionarEObterComponente(new Tamanho(0, 0));
		mRotacao = (FloatStatus) this.adicionarEObterComponente(new FloatStatus("Rotacao", 0.0));
		mColisao = (Colisao) this.adicionarEObterComponente(new Colisao(mPosicao, mTamanho));
		mInterferencia = (Interferencia) this.adicionarEObterComponente(new Interferencia(mPosicao, mTamanho));
		mCalor = (Calor) this.adicionarEObterComponente(new Calor(mPosicao, mTamanho));
		mLuz = (Luz) this.adicionarEObterComponente(new Luz(eX,eY,0,0.8f));

		mImagem = null;
		mComGravidade = true;
		mQueda = 0;
		
		
		mRayCast = new RayCast();
		mRayCast.setRaio(0);
		mRayCast.setCor(new Color(255, 235, 59), 0.3f);

		
		
	}

	public void RestringirCorpo(int eX, int eY) {

		mColisao.RestringirCorpo(eX, eY);

	}

	public void setImagem(BufferedImage eImagem) {
		mImagem = eImagem;

	}

	public Luz getLuz() {return mLuz;}
	public RayCast getRayCast() {return mRayCast;}

	
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

	public void setTam(int eLargura, int eAltura) {
		mTamanho.setLargura(eLargura);
		mTamanho.setAltura(eAltura);
	}

	@Override
	public void update(double dt) {

	}

	public Rect getAreaCorpo() {
		return mColisao.getArea();
	}

	@Override
	public void draw(Graphics g) {

		g.setColor(mCor);

		if (mImagem == null) {

			g.fillRect(mPosicao.getX() - mCamera.getX(), mPosicao.getY() - mCamera.getY(), mTamanho.getLargura(),
					mTamanho.getAltura());

		} else {

			Graphics2D g2 = (Graphics2D) g;

			AffineTransform T = new AffineTransform();
			T.setToIdentity();
			T.translate(mPosicao.getX() - mCamera.getX(), mPosicao.getY() - mCamera.getY());
			T.rotate(mRotacao.getConteudo(), ((mTamanho.getLargura()) / 2), ((mTamanho.getAltura()) / 2));
			T.scale(mEscala, mEscala);

			g2.drawImage(mImagem, T, null);

			
			if (Debugger.ObterBoolean("Calor")) {
				mCalor.Mostrar_Regiao(g, mCamera, Color.RED);
			}
			if (Debugger.ObterBoolean("Interferencia")) {
				mInterferencia.Mostrar_Regiao(g, mCamera, Color.YELLOW);
			}
			if (Debugger.ObterBoolean("Corpo")) {
				Mostrar_Regiao(g, mCamera, Color.YELLOW);
			}
			
		}

		mRayCast.renderCalcRays(g, mSuperGrupo.getLimitadores(), mPosicao.getX() - mCamera.getX() + (mTamanho.getLargura()/2), mPosicao.getY() - mCamera.getY()+20);

		//mLuz.render(g,mCamera);
	}

	public void Mostrar_Regiao(Graphics g, Camera mCamera, Color eRegiaoCor) {

		Contato mContato = new Contato();

		g.setColor(eRegiaoCor);

		for (Rect v : mContato.OcupandoAreas(getAreaCorpo())) {

			g.drawRect(v.getX() - mCamera.getX(), v.getY() - mCamera.getY(), v.getLargura(), v.getAltura());

		}

	}

	public String getSubTipo() {
		String ret = "";

		int i = 0;
		int o = getNome().length();

		while (i < o) {
			char l = getNome().charAt(i);
			if (l == ' ') {
				break;
			}
			ret += String.valueOf(l);
			i += 1;
		}

		return ret;
	}

	// TEMPERATURA
	public void setEmissao(int eCalor) {
		mCalor.setEmissao(eCalor);
	}

	public float getEmissao() {
		return mCalor.getEmissao();
	}

	public float getQuantidadeDeCalor() {
		return mCalor.getQuantidadeDeCalor();
	}

	public void setTempCampo(int eCampo) {
		mCalor.setCampo(eCampo);
	}

	public int getTempCampo() {
		return mCalor.getCampo();
	}

	public Rect getAreaDoTempCampo() {
		return mCalor.getAreaDoCampo();
	}

	public boolean Temp_Interfere(Rect eAreaDeObjeto) {
		return mCalor.Temp_Interfere(eAreaDeObjeto);
	}

}
