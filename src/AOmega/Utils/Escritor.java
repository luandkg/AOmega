package AOmega.Utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Escritor {

	private int mTamanho;
	private Color mCor;

	public Escritor(int eTamanho, Color eCor) {
		mTamanho = eTamanho;
		mCor = eCor;
	}

	public int getTamanho() {
		return mTamanho;
	}

	public Color getCor() {
		return mCor;
	}

	public void Escreve(Graphics g, String eTexto, int eX, int eY) {

		g.setFont(new Font("Tahoma", Font.PLAIN, mTamanho));

		g.setColor(mCor);

		g.drawString(eTexto, eX, eY);

	}
}
