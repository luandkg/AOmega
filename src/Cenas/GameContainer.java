package Cenas;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import AOmega.Windows;
import AOmega.Cenarios.Cena;
import AOmega.Core.Camera;
import AOmega.Core.Imagens;
import AOmega.Core.SpriteSheet;
import AOmega.Organizacao.SuperGrupo;
import AOmega.Utils.Cronometro;
import AOmega.Utils.Debugger;
import AOmega.Utils.Escritor;
import AOmega.Utils.Local;
import AssetContainer.AssetContainer;
import AssetContainer.AssetUtils;
import AssetContainer.ItemPack;
import Componentes.Entidade;
import Componentes.Posicao;
import Componentes.Tamanho;
import Constantes.Constantes;
import Empacotador.Empacotador;
import Empacotador.Objeto;
import Empacotador.Pacote;
import Extras.InputController;
import Fisica.Fisica;
import Fisica.Componentes.Luz;
import Fisica.Efeitos.Atracao;
import Fisica.Efeitos.Gravidade;
import Fisica.Efeitos.Repulsao;
import Fisica.Efeitos.Termico;
import Objetos.Bloquinho;
import Objetos.Personagens;
import Objetos.Terreno;
import Objetos.Terrenos;

public class GameContainer extends Cena {

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

	private Imagens mImageContainer;
	private InputController mInputController;

	private SuperGrupo mSuperGrupo;



	@Override
	public void iniciar() {

		mWindows.setTitle(this.getNome());

	}

	public GameContainer(String eNome, Windows eWindows) {
		super.setNome(eNome);

		mWindows = eWindows;
		mLargura = mWindows.getLargura();
		mAltura = mWindows.getAltura();

		
		
		mWindows.setTitle(eNome);
		
		
		mSuperGrupo = new SuperGrupo();

		mEscritorBranco = new Escritor(20, Color.BLACK);

		mCron = new Cronometro(100);

		mCamera = new Camera(0, 0, mLargura, mAltura, Constantes.QUADRANTE);

		mLS_Entidades = new ArrayList<Entidade>();

		mTerrenos = new Terrenos(mCamera, mLS_Entidades);

		mFisica = new Fisica(mSuperGrupo);
		mFisica.adicionar_LimitadorCorpo("Bloquinho");
		mFisica.adicionar_LimitadorEstrutural("Terreno");
		mFisica.setGravidade(2);

		AssetContainer mAssetContainer = new AssetContainer();
		mAssetContainer.Montar(Local.LocalAtualCom("res/Assets"));
		mAssetContainer.AplicarPrefixo("/res/", "Res::");

		mImageContainer = new Imagens();

		mImageContainer.adicionarUnico("SAPO", AssetUtils.getImagem(mAssetContainer, ("Res::Sprites/Sapo.png")));
		mImageContainer.adicionarUnico("NOCK", AssetUtils.getImagem(mAssetContainer, ("Res::Sprites/Nock.png")));
		mImageContainer.adicionarUnico("FRUZ", AssetUtils.getImagem(mAssetContainer, ("Res::Sprites/Fruz.png")));
		mImageContainer.adicionarUnico("INKO", AssetUtils.getImagem(mAssetContainer, ("Res::Sprites/Inko.png")));

		mPersonagens = new Personagens(mCamera, mImageContainer, mLS_Entidades);

		B1 = mPersonagens.Criar_Jogador(4, -2, Color.BLACK);

		mInputController = new InputController(mWindows, mFisica, B1);

		mPersonagens.Criar_Inko(8, -2);

		mPersonagens.Criar_Nock(2, -1);

		mPersonagens.Criar_Fluz(7, -3);

		mPersonagens.Criar_Nock(4, -1);

		mPersonagens.Criar_Fluz(12, -3);

		mPersonagens.Criar_Fluz(-1, -3);

		mPersonagens.Criar_Fluz(-6, -3);

		mTerrenos.Aleatorio();

		mSuperGrupo.IndexarEntidades(mLS_Entidades);

		Empacotador NivelCentral = new Empacotador();
		Pacote Nivel = NivelCentral.UnicoPacote("Nivel");
		Nivel.Identifique("Autor", "Luan Alves");
		Nivel.Identifique("DDC", "2020 02 04");
		Nivel.Identifique("DDF", "2020 02 22");
		Nivel.Identifique("DDG", "FGMN.DFX.78C-13T");

		Pacote PCT_Entidades = Nivel.UnicoPacote("Entidades");

		for (Entidade T1 : mSuperGrupo.getGrupo("Terreno").getEntidades()) {

			Terreno T = (Terreno) T1;
			Objeto OBJ_Terreno = PCT_Entidades.CriarObjeto("Terreno");

			OBJ_Terreno.Identifique("Nome", T.getNome());

			OBJ_Terreno.IdentifiqueInteiro("X", T.getPos().getX());
			OBJ_Terreno.IdentifiqueInteiro("Y", T.getPos().getY());
			OBJ_Terreno.IdentifiqueInteiro("Largura", T.getTam().getLargura());
			OBJ_Terreno.IdentifiqueInteiro("Altura", T.getTam().getAltura());
			OBJ_Terreno.IdentifiqueInteiro("Cor", T.getCor().getRGB());

		}

		for (Entidade T1 : mSuperGrupo.getGrupo("Bloquinho").getEntidades()) {
			Objeto OBJ_Terreno = PCT_Entidades.CriarObjeto("Bloquinho");

			Bloquinho T = (Bloquinho) T1;

			OBJ_Terreno.Identifique("Nome", T.getNome());

			OBJ_Terreno.IdentifiqueInteiro("X", T.getPos().getX());
			OBJ_Terreno.IdentifiqueInteiro("Y", T.getPos().getY());
			OBJ_Terreno.IdentifiqueInteiro("Largura", Constantes.QUADRANTE);
			OBJ_Terreno.IdentifiqueInteiro("Altura", Constantes.QUADRANTE);
			OBJ_Terreno.Identifique("Tipo", T.getTipo());

		}

		NivelCentral.Salvar(Local.LocalAtualCom("Nivel.txt"));

	}

	@Override
	public void update(double dt) {

		mCron.Esperar();

		mInputController.Inputs();

		for (Entidade mEntidade : mLS_Entidades) {
			mEntidade.update(dt);
		}

		mSuperGrupo.IndexarEntidades(mLS_Entidades);

		mFisica.update();

		

		mCamera.Enquadrar(B1.getPos().getX(), B1.getPos().getY());

	}

	@Override
	public void draw(Graphics g) {
		// Graphics2D g2d = (Graphics2D) g.create();

		// g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
		// RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		// g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
		// RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		// g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		// RenderingHints.VALUE_ANTIALIAS_ON);

		// g.setColor(Color.RED);
		// g.fillRect(0, 0, 100, 100);

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, mWindows.getLargura(), mWindows.getAltura());

		//ZIndexador.Indexar(mLS_Entidades);

		for (Entidade mEntidade : mLS_Entidades) {
			mEntidade.draw(g);

		}

		// Gradear(g);

		// lightMap = new BufferedImage(mLargura, mAltura, BufferedImage.TYPE_INT_ARGB);

		// gl.setColor(new Color(0, 0, 0, 255));
		// gl.fillRect(0, 0, mLargura, mAltura);
		// AlphaComposite oldComp = (AlphaComposite) g2d.getComposite();
		// g2d.setComposite(AlphaComposite.DstOut);

		// for(Light light : lights) light.render(g2d);

		// g2d.setComposite(oldComp);
		// g2d.dispose();

		mEscritorBranco.Escreve(g, "Camera : " + mCamera.getX() + "," + mCamera.getY(), 1000, 550);
		mEscritorBranco.Escreve(g, "B1 : " + B1.getPos().getX() + "," + B1.getPos().getY(), 1000, 600);
		mEscritorBranco.Escreve(g, "B1 Temp : " + B1.getQuantidadeDeCalor() + " QC", 1000, 650);

		if (mFisica.ChaoProximo(B1, 10) > 2) {
			mEscritorBranco.Escreve(g, "Chao : " + mFisica.ChaoProximo(B1, 10) + " m", 1000, 700);
		} else {
			mEscritorBranco.Escreve(g, "Chao : " + mFisica.ChaoProximoNome(B1, 2), 1000, 700);
		}

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

}
