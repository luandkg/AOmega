package Main;

import AOmega.Windows;
import AOmega.Utils.Debugger;
import AOmega.Utils.Local;
import AssetContainer.AssetContainer;
import AssetContainer.ImagePack;
import Atto.Semanador;
import Cenas.AmbienteUnico;
import Cenas.GameContainer;

public class Main {

	public static void main(String[] args) {

		Debugger.NaoDebugar();
		Debugger.Debugar();

		Debugger.Info("Game Engine - AOmega 1.0  ::  DESENVOLVEDOR : LUAN FREITAS ");
		Debugger.Info("");
		Debugger.Info("");

		AssetContainer mAssetContainer = new AssetContainer();
		mAssetContainer.Montar(Local.LocalAtualCom("res/Assets"));
		mAssetContainer.AplicarPrefixo("/res/", "Res::");

		ImagePack Aplicativo = new ImagePack(mAssetContainer.getRes("Res::Aplicativo.png"));

		Windows mWindows = new Windows("AOmega 1.0", 1300, 730);
		mWindows.setIconImage(Aplicativo.getImage());

		int CenaUID = mWindows.CriarCenario(new GameContainer("GameContainer UI", mWindows));
		int AmbienteID = mWindows.CriarCenario(new AmbienteUnico("Ambiente Unico", mWindows));

		mWindows.setCenario(AmbienteID);

		Thread mThread = new Thread(mWindows);
		mThread.start();

		Semanador S = new Semanador();

		System.out.println("ATTO : " + Atto.Semanador.Agora().toString());
		System.out.println("Faixa : " + S.getFaixa(Atto.Semanador.Agora()));

	}

}
