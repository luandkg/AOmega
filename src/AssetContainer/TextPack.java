package AssetContainer;

import java.nio.charset.StandardCharsets;

public class TextPack {

	private String mText;

	public TextPack(ItemPack mItemPack) {

		mText = new String(mItemPack.LerByteBuffer().array(), StandardCharsets.UTF_8);

	}

	public String getText() {
		return mText;
	}

}
