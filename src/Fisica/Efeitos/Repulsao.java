package Fisica.Efeitos;

import AOmega.Estrutural.Localizacao;
import AOmega.Utils.Debugger;
import Componentes.Entidade;
import Constantes.Constantes;
import Fisica.Fisica;
import Fisica.Componentes.Colisao;
import Fisica.Componentes.Interferencia;

public class Repulsao {

	private Fisica mFisica;

	public Repulsao(Fisica eFisica) {

		mFisica = eFisica;

	}

	public void Aplicar() {

		for (Entidade mEntidade : mFisica.getCorpos()) {

			if (mEntidade.existeComponente(Interferencia.ID)) {
				Interferencia mInterferencia = (Interferencia) mEntidade.obterComponente(Interferencia.ID);

				if (mInterferencia.getForca() > 0) {

					AplicarForca_Respulsao(mEntidade);

				}
			}

		}
	}

	public void AplicarForca_Respulsao(Entidade Aplicador) {

		Interferencia mInterferenciaAplicadora = (Interferencia) Aplicador.obterComponente(Interferencia.ID);

		String eNome = Aplicador.getNome();
		int eValor = mInterferenciaAplicadora.getForca();

		for (Entidade mBloquinho : mFisica.getCorpos()) {

			if (!mBloquinho.getNome().contentEquals(eNome)) {

				Colisao mColisao = (Colisao) mBloquinho.obterComponente(Colisao.ID);

				if (mInterferenciaAplicadora.ColideArea(mColisao.getArea())) {

					Localizacao mLocalizacao = mFisica.Referencia(Aplicador, mBloquinho);

					// System.out.println("Repulsando I : " + mBloquinho.getNome() + " :: " +
					// mLocalizacao.toString());

					switch (mLocalizacao) {

					case ACIMA_DIREITA:

						for (int f = 0; f < eValor; f++) {
							if (!mFisica.ColisaoComQualquer_DIREITA(mBloquinho)) {

								mColisao.setPos(mColisao.getPos().getX() + Constantes.UNIDADE,
										mColisao.getPos().getY());

							}
						}

						break;

					case ACIMA_ESQUERDA:

						for (int f = 0; f < eValor; f++) {
							if (!mFisica.ColisaoComQualquer_ESQUERDA(mBloquinho)) {
								mColisao.setPos(mColisao.getPos().getX() - Constantes.UNIDADE,
										mColisao.getPos().getY());
							}
						}
						break;
					case ABAIXO_DIREITA:

						for (int f = 0; f < eValor; f++) {
							if (!mFisica.ColisaoComQualquer_DIREITA(mBloquinho)) {

								mColisao.setPos(mColisao.getPos().getX() + Constantes.UNIDADE,
										mColisao.getPos().getY());

							}
						}

						break;

					case ABAIXO_ESQUERDA:

						for (int f = 0; f < eValor; f++) {
							if (!mFisica.ColisaoComQualquer_ESQUERDA(mBloquinho)) {
								mColisao.setPos(mColisao.getPos().getX() - Constantes.UNIDADE,
										mColisao.getPos().getY());
							}
						}
						break;
					case ESQUERDA:

						Debugger.Info("Bloco " + Aplicador.getNome() + " aplicando força de : " + eValor + " N em "
								+ mBloquinho.getNome() + " - Esquerda !");
						for (int f = 0; f < eValor; f++) {
							if (!mFisica.ColisaoComQualquer_ESQUERDA(mBloquinho)) {
								mColisao.setPos(mColisao.getPos().getX() - Constantes.UNIDADE,
										mColisao.getPos().getY());
							}
						}

						break;
					case DIREITA:

						Debugger.Info("Bloco " + Aplicador.getNome() + " aplicando força de : " + eValor + " N em "
								+ mBloquinho.getNome() + " - Direita !");
						for (int f = 0; f < eValor; f++) {
							if (!mFisica.ColisaoComQualquer_DIREITA(mBloquinho)) {

								mColisao.setPos(mColisao.getPos().getX() + Constantes.UNIDADE,
										mColisao.getPos().getY());

							}
						}

						break;

					case ACIMA:

						Debugger.Info("Bloco " + Aplicador.getNome() + " aplicando força de : " + eValor + " N em "
								+ mBloquinho.getNome() + " - Subindo !");
						for (int f = 0; f < eValor; f++) {
							if (!mFisica.ColisaoComQualquer_ACIMA(mBloquinho)) {
								mColisao.setPos(mColisao.getPos().getX(),
										mColisao.getPos().getY() - Constantes.UNIDADE);
							}
						}
						break;

					case ABAIXO:

						if (mFisica.ColisaoComQualquer_ABAIXO(mBloquinho) == false) {
							Debugger.Info("Bloco " + Aplicador.getNome() + " aplicando força de : " + eValor + " N em "
									+ mBloquinho.getNome() + " - Descendo !");
							for (int f = 0; f < eValor; f++) {
								if (!mFisica.ColisaoComQualquer_ABAIXO(mBloquinho)) {
									mColisao.setPos(mColisao.getPos().getX(),
											mColisao.getPos().getY() + Constantes.UNIDADE);
								}
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
