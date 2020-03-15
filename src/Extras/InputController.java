package Extras;

import java.awt.event.KeyEvent;

import AOmega.Windows;
import AOmega.Utils.Debugger;
import Constantes.Constantes;
import Fisica.Fisica;
import Fisica.Escalas.Deca;
import Objetos.Bloquinho;

public class InputController {

	private Fisica mFisica;
	private Windows mWindows;
	private Bloquinho B1;

	public InputController(Windows eWindows, Fisica eFisica, Bloquinho eJogador) {

		mWindows = eWindows;
		mFisica = eFisica;
		B1 = eJogador;

	}

	public void Inputs() {

		int vel = Constantes.UNIDADE;

		// mWindows.getTeclado().update();

		// if (mWindows.getTeclado().getObservar()) {

		if (!mWindows.getTeclado().iskeyPressedELibere(KeyEvent.VK_CONTROL)
				&& mWindows.getTeclado().iskeyPressedELibere(KeyEvent.VK_LEFT)) {
			// mWindows.getTeclado().Observe();

			for (int e = 0; e < 3; e++) {
				if (!mFisica.ColisaoComQualquer_ESQUERDA(B1)) {
					B1.setPos(B1.getPos().getX() - (int) (vel), B1.getPos().getY());
				}
			}

		}
		if (!mWindows.getTeclado().iskeyPressedELibere(KeyEvent.VK_CONTROL)
				&& mWindows.getTeclado().iskeyPressedELibere(KeyEvent.VK_RIGHT)) {
			// mWindows.getTeclado().Observe();

			for (int e = 0; e < 3; e++) {
				if (!mFisica.ColisaoComQualquer_DIREITA(B1)) {
					B1.setPos(B1.getPos().getX() + (int) (vel), B1.getPos().getY());
				}
			}
		}

		if (!mWindows.getTeclado().iskeyPressedELibere(KeyEvent.VK_CONTROL)
				&& mWindows.getTeclado().iskeyPressedELibere(KeyEvent.VK_UP)) {
			// mWindows.getTeclado().Observe();

			System.out.println("UP");

			for (int e = 0; e < 3; e++) {
				int eMax = 25;
				int eChao = mFisica.ChaoProximo(B1, eMax);

				// Debugger.Alerta("Chao mais Proximo : " + eChao + " "+ ((eChao-1)*10) + "
				// metros");

				B1.LimparQueda();
				B1.setStatus("Subindo : " + Deca.get( Constantes.getEspacoPorUnidade(eChao + 1)).toString());

				if (eChao < eMax) {

					if (!mFisica.ColisaoComQualquer_ACIMA(B1)) {
						B1.PararGravidade();
						B1.Queda_Zerar();
						B1.setPos(B1.getPos().getX(), B1.getPos().getY() - (int) (vel));
					}

				}
			}

		}
		if (!mWindows.getTeclado().iskeyPressedELibere(KeyEvent.VK_CONTROL)
				&& mWindows.getTeclado().iskeyPressedELibere(KeyEvent.VK_DOWN)) {
			// mWindows.getTeclado().Observe();

			if (!mFisica.ColisaoComQualquer_ABAIXO(B1)) {
				B1.setPos(B1.getPos().getX(), B1.getPos().getY() + (int) (vel));
			}

		}
		// }

		// System.out.println("ROT : " + B1.getRotacao());

		if (mWindows.getMouse().wheel < 0) {
			B1.setRotacao((B1.getRotacao() + (float) (36)));
			mWindows.getMouse().ZerarWheel();
		}
		if (mWindows.getMouse().wheel > 0) {
			B1.setRotacao((B1.getRotacao() - (float) (36)));
			mWindows.getMouse().ZerarWheel();
		}

	}

}
