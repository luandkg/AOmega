package Fisica.Efeitos;

import AOmega.Estrutural.Localizacao;
import AOmega.Utils.Debugger;
import Componentes.Entidade;
import Constantes.Constantes;
import Fisica.Fisica;
import Fisica.Componentes.Colisao;
import Fisica.Componentes.Interferencia;

public class Atracao {

	private Fisica mFisica;

	public Atracao(Fisica eFisica) {

		mFisica = eFisica;
	}

	public void Aplicar() {

		for (Entidade mEntidade : mFisica.getCorpos()) {

			if (mEntidade.existeComponente(Interferencia.ID)) {

				Interferencia mInterferencia = (Interferencia) mEntidade.obterComponente(Interferencia.ID);

				if (mInterferencia.getForca() < 0) {

					AplicarForca_Atracao(mEntidade);

				}

			}
		}

	}

	public void AplicarForca_Atracao(Entidade mEntidadeAplicador) {

		Interferencia mInterferenciaAplicadora = (Interferencia) mEntidadeAplicador.obterComponente(Interferencia.ID);

		String eNome = mEntidadeAplicador.getNome();
		int eValor = mInterferenciaAplicadora.getForca() * (-1);

		for (Entidade mBloquinho : mFisica.getCorpos()) {

			if (!mBloquinho.getNome().contentEquals(eNome)) {

				Colisao mColisao = (Colisao) mBloquinho.obterComponente(Colisao.ID);

				if (mInterferenciaAplicadora.ColideArea(mColisao.getArea())) {

					Localizacao mLocalizacao = mFisica.Referencia(mEntidadeAplicador, mBloquinho);

					Debugger.Info("Atraindo I : " + mBloquinho.getNome() + " :: " + mLocalizacao.toString());

					switch (mLocalizacao) {
					case ESQUERDA:

						Debugger.Info("Bloco " + mEntidadeAplicador.getNome() + " aplicando força de : " + eValor
								+ " N em " + mBloquinho.getNome() + " - Esquerda !");
						for (int f = 0; f < eValor; f++) {
							if (!mFisica.ColisaoComQualquer_DIREITA(mBloquinho)) {
								mColisao.setPos(mColisao.getPos().getX() + Constantes.UNIDADE,
										mColisao.getPos().getY());
							}
						}

						break;

					case DIREITA:

						Debugger.Info("Bloco " + mEntidadeAplicador.getNome() + " aplicando força de : " + eValor
								+ " N em " + mBloquinho.getNome() + " - Direita !");
						for (int f = 0; f < eValor; f++) {
							if (!mFisica.ColisaoComQualquer_ESQUERDA(mBloquinho)) {

								mColisao.setPos(mColisao.getPos().getX() - Constantes.UNIDADE,
										mColisao.getPos().getY());

							}
						}

						break;

					default:
						break;
					}

				}

			}

		}

	}

}
