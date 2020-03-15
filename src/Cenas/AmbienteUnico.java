package Cenas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import AOmega.Windows;
import AOmega.Cenarios.Cena;
import AOmega.Core.Camera;
import AOmega.Core.Imagens;
import AOmega.Organizacao.SuperGrupo;
import AOmega.Utils.Cronometro;
import AOmega.Utils.Debugger;
import AOmega.Utils.Escritor;
import AOmega.Utils.Local;
import AssetContainer.AssetContainer;
import Componentes.Entidade;
import Componentes.Posicao;
import Componentes.Tamanho;
import Constantes.Constantes;
import Extras.Amplitude;
import Extras.InfoBoxer;
import Extras.InputController;
import Extras.PacoteDeImagens;
import Fisica.Fisica;
import Fisica.Componentes.Luz;
import Fisica.Componentes.RayCast;
import Fisica.Efeitos.Tempo.Fases;
import Fisica.Escalas.Azta;
import Fisica.Escalas.Deca;
import Objetos.Bloquinho;
import Objetos.Chave;
import Objetos.Itens;
import Objetos.Personagens;
import Objetos.Terrenos;

public class AmbienteUnico extends Cena {

	private Escritor mEscritorBranco;

	private Bloquinho B1;
	private Cronometro mCron;

	private ArrayList<Entidade> mLS_Entidades;
	private Camera mCamera;

	private int mLargura;
	private int mAltura;

	private Windows mWindows;
	private Fisica mFisica;

	private Terrenos mTerrenos;
	private Personagens mPersonagens;
	private Itens mItens;

	private Imagens mImagens;
	private InputController mInputController;

	private SuperGrupo mSuperGrupo;
	private InfoBoxer mInfoBoxerC;

	public RayCast mRayCast;
	public RayCast mRayCast2;

	public Amplitude AmpC = new Amplitude(0, 45);

	public AmbienteUnico(String eNome, Windows eWindows) {
		super.setNome(eNome);

		mWindows = eWindows;
		mLargura = mWindows.getLargura();
		mAltura = mWindows.getAltura();

		mRayCast = new RayCast();
		mRayCast.setRaio(400);
		mRayCast.setCor(Color.GREEN, 0.3f);
		mRayCast.setCor(new Color(255, 235, 59), 0.3f);

		mRayCast2 = new RayCast();
		mRayCast2.setRaio(600);
		mRayCast2.setCor(new Color(255, 87, 34), 0.3f);


		mEscritorBranco = new Escritor(20, Color.BLACK);

		mCron = new Cronometro(310);

		mCamera = new Camera(0, 0, mLargura, mAltura, Constantes.QUADRANTE);

		mLS_Entidades = new ArrayList<Entidade>();

		mTerrenos = new Terrenos(mCamera, mLS_Entidades);

		mSuperGrupo = new SuperGrupo();

		mFisica = new Fisica(mSuperGrupo);

		mFisica.setGravidade(Constantes.GRAVIDADE);

		mFisica.adicionar_LimitadorCorpo("Bloquinho");

		mFisica.adicionar_LimitadorEstrutural("Terreno");
		mFisica.adicionar_LimitadorEstrutural("FogoDock");
		mFisica.adicionar_LimitadorEstrutural("Chave");

		mFisica.getTempo().setTamanhoDoCiclo(200);
		
		Debugger.DefinaBoolean("Corpo", false);

		Debugger.DefinaBoolean("Calor", false);
		Debugger.DefinaBoolean("Interferencia", false);
		Debugger.DefinaBoolean("Gravidade", false);

		AssetContainer mAssetContainer = new AssetContainer();
		mAssetContainer.Montar(Local.LocalAtualCom("res/Assets"));
		mAssetContainer.AplicarPrefixo("/res/", "Res::");

		mImagens = new PacoteDeImagens().CriarPacote(mAssetContainer);

		mInfoBoxerC = new InfoBoxer(mImagens);
		mInfoBoxerC.DefinaX(1000);

		mPersonagens = new Personagens(mCamera, mImagens, mLS_Entidades);
		mItens = new Itens(mSuperGrupo,mCamera, mImagens, mLS_Entidades);

		B1 = mPersonagens.Criar_Jogador(1, -2, Color.BLACK);

		mInputController = new InputController(mWindows, mFisica, B1);

		mPersonagens.Criar_Inko(15, -2);

		mPersonagens.Criar_Nock(3, -2);

		mPersonagens.Criar_Fluz(10, -2);

		mItens.Criar_FogoDock("FogoDock_Vermelho", 12, 14);

		Chave Chave_Verde = (Chave) mItens.Criar_Chave("Chave_Verde", 8, 9);
		Chave_Verde.Ligar();
		Chave_Verde.SincronizeCom("FogoDock_Verde");

		mItens.Criar_FogoDock("FogoDock_Verde", 23, 9);

		Chave Chave_Vermelha = (Chave) mItens.Criar_Chave("Chave_Vermelho", 8, 14);
		Chave_Vermelha.Ligar();
		Chave_Vermelha.SincronizeCom("FogoDock_Vermelho");

		mItens.Criar_FogoDock("FogoDock_Amarelo", 25, 19);
		mItens.Criar_FogoDock("FogoDock_Amarelo", 27, 19);
		mItens.Criar_FogoDock("FogoDock_Amarelo", 29, 19);

		Chave Chave_Amarelo = (Chave) mItens.Criar_Chave("Chave_Amarelo", 22, 17);
		Chave_Amarelo.SincronizeCom("FogoDock_Amarelo");
		Chave_Amarelo.Ligar();

		mTerrenos.Terreno_Horizontal_Criar("Terra Verde", 5, 10, 20, Color.GREEN);
		mTerrenos.Terreno_Vertical_Criar("Terra Verde", 15, 8, 2, Color.GREEN);
		mTerrenos.Terreno_Vertical_Criar("Terra Verde", 20, 7, 3, Color.GREEN);
		mTerrenos.Terreno_Horizontal_Criar("Terra Verde", 28, 10, 10, Color.GREEN);
		mTerrenos.Terreno_Vertical_Criar("Terra Verde", 30, 8, 2, Color.GREEN);

		mTerrenos.Terreno_Horizontal_Criar("Terra Vermelha", -5, 15, 20, Color.RED);
		mTerrenos.Terreno_Vertical_Criar("Terra Vermelha", 15, 13, 3, Color.RED);

		mTerrenos.Terreno_Horizontal_Criar("Terra Amarela", 13, 20, 20, Color.YELLOW);
		mTerrenos.Terreno_Vertical_Criar("Terra Amarela", 20, 17, 3, Color.YELLOW);

		mSuperGrupo.IndexarEntidades(mLS_Entidades);

		for (Entidade mEntidade : mSuperGrupo.getGrupo("Chave").getEntidades()) {
			Chave C1 = (Chave) mEntidade;
			C1.SincronizarFogos(mSuperGrupo.getGrupo("FogoDock").getEntidades());
		}

	}

	

	@Override
	public void update(double dt) {

		mCron.Esperar();

		mInputController.Inputs();

		if (mCron.Esperado()) {
		//	AmpC.Aumentar(10);

		}

		for (Entidade mEntidade : mLS_Entidades) {
			mEntidade.update(dt);

		}

		mSuperGrupo.IndexarEntidades(mLS_Entidades);

		for (Entidade mEntidade : mSuperGrupo.getGrupo("Chave").getEntidades()) {
			if (mCron.Esperado()) {

				if (mEntidade.getTipo().contentEquals("Chave")) {

					if (B1.ColideCom(mEntidade)) {

						Chave C1 = (Chave) mEntidade;
						C1.Trocar();

						C1.SincronizarFogos(mSuperGrupo.getGrupo("FogoDock").getEntidades());

					}

				}

			}

		}
		
		
	//	for(Entidade mEntidade:mRayCast.InterAmplitude(mSuperGrupo.getEntidades(), 100, 100, 45, 60)) {

		//for(Entidade mEntidade:mRayCast.InterAmplitude(mSuperGrupo.getEntidades(), 100, 100, AmpC.getInicio(), AmpC.getFim())) {
			
		//	System.out.println("Inter : " + mEntidade.getNome());
			
			
	//	}

		mFisica.update();

		// mCamera.Enquadrar(B1.getPos().getX(), B1.getPos().getY());

	}

	@Override
	public void draw(Graphics g) {

		mWindows.Limpar(g);

		// Gradear(g);

		for (Entidade mEntidade : mLS_Entidades) {
			mEntidade.draw(g);
		}
		
		if (mFisica.getTempo().getFase()==Fases.NOITE) {
			AmbienteColor(g,Color.GRAY,0.4f);
		}

		mInfoBoxerC.DefinaY(100);
		mInfoBoxerC.InfoTag(g, "CICLO : " + mFisica.getTempo().getCiclo());
		mInfoBoxerC.InfoTag(g, "DIA : " + mFisica.getTempo().getDia());

		mInfoBoxerC.InfoTag(g, "GRAVIDADE : " + Deca.get(mFisica.getGravidade()) + "/s²");

		
		mInfoBoxerC.DefinaY(400);
		mInfoBoxerC.Caixa(g, 280, 310);

		mInfoBoxerC.InfoTag(g, "Camera : " + mCamera.getX() + "," + mCamera.getY());
		mInfoBoxerC.InfoTag(g, "Pos : " + B1.getPos().getX() + "," + B1.getPos().getY());
		mInfoBoxerC.InfoTag(g, "Temp : " + B1.getTemperatura() + " " + Azta.SUFIXO);
		mInfoBoxerC.InfoTag(g, "Chao : " + mFisica.ChaoStatus(B1, 18));
		mInfoBoxerC.InfoTag(g, B1.getVelocidadeDeQueda());
		mInfoBoxerC.InfoTag(g, "Objetos : " + mLS_Entidades.size());

		

		// mRayCast.renderLimitadores(g,getLimitadores());

		// mRayCast.renderRays(g,mRayCast.calcRays(getLimitadores(), 100, 100));
		// mRayCast.renderCalcRays(g,getLimitadores(), 100, 100);

	//	mRayCast.renderCalc_Amplitude(g, mSuperGrupo.getLimitadores(), 100, 100, AmpC.getInicio(), AmpC.getFim());
			mRayCast.renderCalc_Amplitude(g, mSuperGrupo.getLimitadores(), 100, 100, 45,60);

	//	mRayCast2.renderCalc_Amplitude(g, mSuperGrupo.getLimitadores(), 300, 100, AmpC.getInicio(), AmpC.getFim());

	}
	
	public void AmbienteColor(Graphics g,Color eCor,float eLumem) {
		
		int alpha = (int) (255.0f  * eLumem);

		if (alpha<0) {alpha=0;}
		if (alpha>255) {alpha=255;}

		
		Color mCor = (new Color(eCor.getRed(), eCor.getGreen(), eCor.getBlue(), alpha));
		g.setColor(mCor);
		
		g.fillRect(0, 0, mWindows.getLargura(), mWindows.getAltura());
	}

	public void Gradear(Graphics g) {

		g.setColor(Color.BLACK);

		for (int c = 0; c < 50; c++) {
			g.drawLine((c * Constantes.QUADRANTE), 0, (c * Constantes.QUADRANTE), mWindows.getAltura());
		}

		for (int l = 0; l < 30; l++) {
			g.drawLine(0, (l * Constantes.QUADRANTE), mWindows.getLargura(), (l * Constantes.QUADRANTE));
		}

	}



	@Override
	public void iniciar() {

		mWindows.setTitle(this.getNome());

	}

}
