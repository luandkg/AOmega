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
import AOmega.Utils.Debugger;
import AOmega.Utils.Escritor;
import Componentes.Entidade;
import Componentes.FloatStatus;
import Componentes.Posicao;
import Componentes.Tamanho;
import Constantes.Constantes;
import Fisica.Componentes.Colisao;
import Fisica.Componentes.Interferencia;
import Fisica.Escalas.Azta;
import Fisica.Escalas.Deca;
import Fisica.Componentes.Calor;

public class Bloquinho extends Entidade {

	private Posicao mPosicao;
	private Tamanho mTamanho;
	private FloatStatus mRotacao;
	private Colisao mColisao;
	private Interferencia mInterferencia;
	private Calor mCalor;

	private float mEscala;
	private AOmega.Core.Camera mCamera;
	private Color mCor;

	private BufferedImage mImagem;

	private Escritor mEscritorBranco;

	private int mQueda;
	private boolean mComGravidade;

	public Bloquinho(String eNome, int eX, int eY, int eTamanho) {

		setNome(eNome);
		setTipo("Bloquinho");

		mCor = Color.black;

		mEscritorBranco = new Escritor(15, Color.BLACK);

		mEscala = 1.0f;
		mCamera = new AOmega.Core.Camera(0, 0, 0, 0, 1);

		mPosicao = (Posicao) this.adicionarEObterComponente(new Posicao(eX, eY));
		mTamanho = (Tamanho) this.adicionarEObterComponente(new Tamanho(eTamanho, eTamanho));
		mRotacao = (FloatStatus) this.adicionarEObterComponente(new FloatStatus("Rotacao", 0.0));
		mColisao = (Colisao) this.adicionarEObterComponente(new Colisao(mPosicao, mTamanho));
		mInterferencia = (Interferencia) this.adicionarEObterComponente(new Interferencia(mPosicao, mTamanho));
		mCalor = (Calor) this.adicionarEObterComponente(new Calor(mPosicao, mTamanho));

		mImagem = null;
		mComGravidade = true;
		mQueda = 0;
	}

	public void PararGravidade() {
		mComGravidade = false;
	}

	public void AplicarGravidade() {
		mComGravidade = true;
	}

	public boolean ComGravidade() {
		return mComGravidade;
	}

	public int getQueda() {
		return mQueda;
	}

	public void Queda_Zerar() {
		mQueda = 0;
	}

	public void Cair() {
		mQueda += 1;
	}

	public void setImagem(BufferedImage eImagem) {
		mImagem = eImagem;
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

	// INTERFERENCIA
	public void setForca(int eForca) {
		mInterferencia.setForca(eForca);
	}

	public int getForca() {
		return mInterferencia.getForca();
	}

	public void setCampo(int eCampo) {
		mInterferencia.setCampo(eCampo);
	}

	public int getCampo() {
		return mInterferencia.getCampo();
	}

	public Rect getAreaDoCampo() {
		return mInterferencia.getAreaDoCampo();
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

		}

		// mEscritorBranco.Escreve(g, mCalor.getQuantidadeDeCalor() + " QC",
		// mPosicao.getX() - 10,mPosicao.getY() - 70);
		// mEscritorBranco.Escreve(g, getTemperatura() + " C", mPosicao.getX() - 10,
		// mPosicao.getY() - 30);

		
		 if (mQueda>0) {
				Caindo = "T=" + mQueda + " s" + "-> V=" + Deca.get( Constantes.getEspacoPorTempoDeQueda(mQueda)).toString() + "/s";
		 }
		 
		if (Debugger.ObterBoolean("Gravidade")) {
			
			
			 if (mQueda>0) {


				mEscritorBranco.Escreve(g, Caindo, mPosicao.getX() - 10, mPosicao.getY() - 30);

					 } else {

					 mEscritorBranco.Escreve(g, Caindo , mPosicao.getX() - 10, mPosicao.getY() -30);

				 }
			 
		}
		
		
		
	

		// mInterferencia.Mostrar_Regiao(g, mCamera, Color.BLUE);

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
	
	public void LimparQueda() {
		Caindo="";
	}

	public void setStatus(String eStatus) {
		Caindo=eStatus;
	}
	
	public void Mostrar_Regiao(Graphics g, Camera mCamera, Color eRegiaoCor) {

		Contato mContato = new Contato();

		g.setColor(eRegiaoCor);

		for (Rect v : mContato.OcupandoAreas(getAreaCorpo())) {

			g.drawRect(v.getX() - mCamera.getX(), v.getY() - mCamera.getY(), v.getLargura(), v.getAltura());

		}

	}
	private String Caindo = "";

	public String getVelocidadeDeQueda() {
		return Caindo;
	}

	public boolean Interfere(Rect eAreaDeObjeto) {
		return mInterferencia.ColideArea(eAreaDeObjeto);
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

	public float getTemperatura() {
		String eSubTipo = getSubTipo();

		float ret = Azta.CalorToTemperatura(mCalor.getQuantidadeDeCalor());

		mCalor.getQuantidadeDeCalor();

		if (eSubTipo.contentEquals("NOCK")) {
			ret = Azta.TemperaturaDoMaterial(ret, 2.0f);
		}

		return ret;
	}

	public boolean ColideCom(Entidade eEntidade) {
		boolean ret = false;

		if (eEntidade.existeComponente(Colisao.ID)) {

			Colisao C = (Colisao) eEntidade.obterComponente(Colisao.ID);

			ret = mColisao.ColideGrupo(C.OcupandoCom(new Vetor2(0, -Constantes.UNIDADE)));
			if (!ret) {
				ret = mColisao.ColideGrupo(C.OcupandoCom(new Vetor2(0, +Constantes.UNIDADE)));
			}
			if (!ret) {
				ret = mColisao.ColideGrupo(C.OcupandoCom(new Vetor2(+Constantes.UNIDADE, 0)));
			}
			if (!ret) {
				ret = mColisao.ColideGrupo(C.OcupandoCom(new Vetor2(-Constantes.UNIDADE, 0)));
			}
		}

		return ret;

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
}
