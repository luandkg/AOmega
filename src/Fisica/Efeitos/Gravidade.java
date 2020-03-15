package Fisica.Efeitos;

import AOmega.Utils.Debugger;
import Componentes.Entidade;
import Constantes.Constantes;
import Fisica.Fisica;
import Objetos.Bloquinho;

public class Gravidade {

	private Fisica mFisica;
	private  int mGravidade;

	
	public Gravidade(Fisica eFisica,int eGravidade) {

		mFisica = eFisica;

		
		setGravidade(eGravidade);

	}

	public void setGravidade(int eGravidade) {
		mGravidade=eGravidade;
	}
	public int getGravidade() {
		return mGravidade;
	}
	
	
	public void Aplicar() {


			for (Entidade mEntidade : mFisica.getCorpos()) {

				Bloquinho BC = (Bloquinho) mEntidade;
				boolean gra = true;

				if (BC.getNome().contentEquals("Jogador")) {

					if (BC.ComGravidade() == false) {

						gra = false;
						BC.AplicarGravidade();
					}
				}

				if (gra) {

					if (mFisica.ColisaoComTerreno_Abaixo(BC) || mFisica.ColisaoComBloquinho_ABAIXO(BC)) {

						if (Debugger.ObterBoolean("Gravidade")) {
							if (BC.getQueda()>0) {
								if (BC.getNome().contentEquals("Jogador")) {
									System.out.println("Jogador Caindo : " + BC.getQueda() + "s com Velocidade : " +  String.format("%.2f", Constantes.getEspacoPorTempoDeQueda(BC.getQueda())).toString() + " m/s");
								}
							}
						}
						
						
						
					
						
						BC.Queda_Zerar();

					} else {
						
						if (Debugger.ObterBoolean("Gravidade")) {
							if (BC.getNome().contentEquals("Jogador")) {
								System.out.println("Jogador Caindo : " + BC.getQueda() + "s com Velocidade : " +  String.format("%.2f", Constantes.getEspacoPorTempoDeQueda(BC.getQueda())).toString() + " m/s");
							}
						}
						
						
						
						
						BC.Cair();

						// APLICAR ACALERACAO A DA GRAVIDADE
						int caindo = 0;
						for(int g=0;g<mGravidade;g++) {
							
							for (int n = 0; n < (BC.getQueda() ) + 1; n++) {
								if (!mFisica.ColisaoComTerreno_Abaixo(BC) && !mFisica.ColisaoComBloquinho_ABAIXO(BC)) {
									BC.setPos(BC.getPos().getX(), BC.getPos().getY() + Constantes.UNIDADE);
									caindo +=1;
								}
							}
							
						}
						
						
					
						

					}

				}

			}

	
	}

}
