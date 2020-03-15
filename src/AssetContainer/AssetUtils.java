package AssetContainer;

import java.awt.image.BufferedImage;

public class AssetUtils {

	public static BufferedImage getImagem(AssetContainer mAssetContainer, String eLocal) {
		return new ImagePack(mAssetContainer.getRes(eLocal)).getImage();
	}
}
