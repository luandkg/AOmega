package Objetos;

import java.awt.Color;
import java.awt.image.BufferedImage;

import AOmega.Core.Animacao;
import AOmega.Core.Imagens;
import AOmega.Core.SpriteSheet;
import AOmega.Organizacao.SuperGrupo;
import AOmega.Utils.Cronometro;
import Constantes.Constantes;
import Fisica.Componentes.Luz;

public class FireDock extends Item {

	private Imagens mImageContainer;

	private SpriteSheet SS;
	private boolean mStatus;
	private BufferedImage mImagemDesligada;
	private Animacao mAnimacao;

	public FireDock(SuperGrupo eSuperGrupo,Imagens eImageContainer, String eNome, int eX, int eY) {
		super(eSuperGrupo,eNome, eX, eY);

		mImageContainer = eImageContainer;

		SS = new SpriteSheet(mImageContainer.obter("FireDock_Animation"), 16, 32);
		mImagemDesligada = mImageContainer.obter("FireDock_Off");

		mAnimacao = new Animacao(80);
		mAnimacao.addSprite(SS.getSprite(0, 0));
		mAnimacao.addSprite(SS.getSprite(1, 0));
		mAnimacao.addSprite(SS.getSprite(2, 0));

		this.setTipo("FogoDock");
		this.setImagem(SS.getSprite(2, 0));

		this.RestringirCorpo(0, 30);
		this.getLuz().setCor(new Color(255, 0, 0));

		if (eNome.contains("Amarelo")) {

			this.getLuz().setCor(new Color(255, 255, 0));

		}
		
		if (eNome.contains("Vermelho")) {

			this.getLuz().setCor(new Color(255, 0, 0));

		}
		
		if (eNome.contains("Verde")) {

			this.getLuz().setCor(new Color(0, 255, 0));

		}
		
		
		this.getRayCast().setResolucao(400);
		
		mStatus = false;
	}

	public void Ligar() {
		mStatus = true;

		this.setTempCampo(Constantes.CAMPO_GIGANTE);
		this.setEmissao(100);
		float eRaioLuz = 1.5f * (this.getTempCampo() * Constantes.UNIDADE);

		this.getLuz().setRaio((int) eRaioLuz);
		this.getLuz().setLumen(0.5f);
		this.getRayCast().setRaio((int)eRaioLuz);

	}

	public void Desligar() {
		mStatus = false;
		this.setTempCampo(0);
		this.setEmissao(0);
		this.getLuz().setRaio(0);
		this.getRayCast().setRaio(0);

	}

	public boolean getStatus() {
		return mStatus;
	}

	public void Trocar() {
		mStatus = !mStatus;
	}

	@Override
	public void update(double dt) {

		mAnimacao.update(dt);

		this.getLuz().setPos(this.getPos().getX() + 15, this.getPos().getY() + 5);
		
		
		if (mStatus) {

			this.setImagem(mAnimacao.getSpriteCorrente());

		} else {
			this.setImagem(mImagemDesligada);
		}

	}
}
