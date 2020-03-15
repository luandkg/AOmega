package Extras;

import AOmega.Core.Imagens;
import AssetContainer.AssetContainer;
import AssetContainer.AssetUtils;

public class PacoteDeImagens {

public PacoteDeImagens() {
	
}
	public Imagens CriarPacote(AssetContainer mAssetContainer) {
		
		
		Imagens	mImageContainer = new Imagens();

		mImageContainer.adicionarUnico("SAPO", AssetUtils.getImagem(mAssetContainer, ("Res::Sprites/Sapo.png")));
		mImageContainer.adicionarUnico("NOCK", AssetUtils.getImagem(mAssetContainer, ("Res::Sprites/Nock.png")));
		mImageContainer.adicionarUnico("FRUZ", AssetUtils.getImagem(mAssetContainer, ("Res::Sprites/Fruz.png")));
		mImageContainer.adicionarUnico("INKO", AssetUtils.getImagem(mAssetContainer, ("Res::Sprites/Inko.png")));

		mImageContainer.adicionarUnico("FireDock_Off",
				AssetUtils.getImagem(mAssetContainer, ("Res::Items/FireDock_Off.png")));
		mImageContainer.adicionarUnico("FireDock_On",
				AssetUtils.getImagem(mAssetContainer, ("Res::Items/FireDock_On.png")));
		mImageContainer.adicionarUnico("FireDock_Animation",
				AssetUtils.getImagem(mAssetContainer, ("Res::Items/FireDock_Animation.png")));

		mImageContainer.adicionarUnico("Chave_Ligada",
				AssetUtils.getImagem(mAssetContainer, ("Res::Items/Chave_Ligada.png")));
		mImageContainer.adicionarUnico("Chave_Desligada",
				AssetUtils.getImagem(mAssetContainer, ("Res::Items/Chave_Desligada.png")));
		
		
		
		// UI
		
		mImageContainer.adicionarUnico("dotGreen", AssetUtils.getImagem(mAssetContainer, ("Res::UI/dotGreen.png")));
		mImageContainer.adicionarUnico("glassPanel", AssetUtils.getImagem(mAssetContainer, ("Res::UI/glassPanel.png")));

		
		
		
		return mImageContainer;
	}
}
