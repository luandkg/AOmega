package Fisica.Efeitos;

import Componentes.Entidade;
import Fisica.Fisica;
import Fisica.Componentes.Calor;
import Fisica.Componentes.Colisao;

public class Termico {

	private Fisica mFisica;

	public Termico(Fisica eFisica) {

		mFisica = eFisica;

	}

	public void Aplicar() {

		//if (mCron.Esperado()) {

			// ZERAR TEMPERATURA
			for (Entidade mEntidade : mFisica.getEntidades()) {

				if (mEntidade.existeComponente(Calor.ID)) {

					Calor mTemperatura = (Calor) mEntidade.obterComponente(Calor.ID);
					mTemperatura.QuantidadeDeCalor_Nivelar(mTemperatura.getEmissao());

				}

			}

			// CALCULAR TEMPERATURA CORRENTE
			for (Entidade mEntidade : mFisica.getEntidades()) {

				if (mEntidade.existeComponente(Calor.ID)) {

					Calorimetro(mEntidade);
					
				}

			}

		//}
	}

	public void Calorimetro(Entidade Aplicador) {

		String eNome = Aplicador.getNome();
		Calor AplicadorCalor = (Calor) Aplicador.obterComponente(Calor.ID);

		
		for (Entidade mBloquinho : mFisica.getEntidades()) {

			if (!mBloquinho.getNome().contentEquals(eNome)) {

				if (mBloquinho.existeComponente(Calor.ID)) {

					if (mBloquinho.existeComponente(Colisao.ID)) {
						Colisao AColisao = (Colisao) mBloquinho.obterComponente(Colisao.ID);

						if (AplicadorCalor.Temp_Interfere(AColisao.getArea())) {

						

								Calor mTemperatura = (Calor) mBloquinho.obterComponente(Calor.ID);
								mTemperatura.QuantidadeDeCalor_Equilibrar(Aplicador.getNome(),AplicadorCalor.getEmissao());


							

						}

						
						
					}

					

					
				}

			}

		}

	}

}
