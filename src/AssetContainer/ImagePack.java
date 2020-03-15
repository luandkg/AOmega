package AssetContainer;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

public class ImagePack {

	private int mWidth;
	private int mHeight;
	private BufferedImage mImage;

	public ImagePack(ItemPack mItemPack) {

		ByteBuffer mData = mItemPack.LerByteBuffer();
		InputStream in = new ByteArrayInputStream(mData.array());
		mImage = null;

		try {
			mImage = ImageIO.read(in);

			mWidth = mImage.getWidth();
			mHeight = mImage.getHeight();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public BufferedImage getImage() {
		return mImage;
	}

	public int getWidth() {
		return mWidth;
	}

	public int getHeight() {
		return mHeight;
	}

}
