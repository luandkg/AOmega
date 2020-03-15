package Objetos;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import AOmega.Core.Imagens;
import AOmega.Core.SpriteSheet;
import AOmega.Organizacao.SuperGrupo;
import AOmega.Utils.Cronometro;
import Componentes.Entidade;

public class Chave extends Item {

	private Imagens mImageContainer;
	
	private Cronometro mCron;
	private boolean mCron_Esperado;

	private boolean mStatus;

	private BufferedImage mImagemLigada;
	private BufferedImage mImagemDesligada;

	private ArrayList<String> mFogoDocks;
	
	public Chave(SuperGrupo eSuperGrupo,Imagens eImageContainer, String eNome, int eX, int eY) {
		super(eSuperGrupo,eNome, eX, eY);

		mImageContainer = eImageContainer;

		mImagemLigada = mImageContainer.obter("Chave_Ligada");
		mImagemDesligada = mImageContainer.obter("Chave_Desligada");

		this.setTipo("Chave");

		this.RestringirCorpo(+10, 0);

		mCron = new Cronometro(500);
		mStatus = false;
		mCron_Esperado=false;
		mFogoDocks = new ArrayList<String>();
		
	}

	public void Ligar() {
		mStatus = true;
	}

	public void Desligar() {
		mStatus = false;

	}

	public boolean getStatus() {
		return mStatus;
	}

	public void Trocar() {
		
		if (mCron_Esperado) {
			mCron_Esperado=false;
			mCron.Zerar();
			mStatus = !mStatus;

		}
		
	}

	@Override
	public void update(double dt) {

		mCron.Esperar();
	
		if (mCron.Esperado()) {
			mCron_Esperado = true;
		}
		
		
		
		if (mStatus) {
			this.setImagem(mImagemLigada);
		} else {
			this.setImagem(mImagemDesligada);
		}

	}
	
	public void SincronizeCom(String eNome) {
		mFogoDocks.add(eNome);
	}
	
	public void SincronizarFogos(ArrayList<Entidade> eEntidades) {
		
		for (Entidade fEntidade : eEntidades) {

			if (mFogoDocks.contains(fEntidade.getNome())) {
				
				System.out.println("Chave : " + this.getNome() + " : " + this.getStatus() + " em : "
						+ fEntidade.getNome());

				FireDock Fogo = (FireDock) fEntidade;

				if (this.getStatus()) {
					Fogo.Ligar();
				} else {
					Fogo.Desligar();
				}
				
			}
		

		}
		
	}
}
