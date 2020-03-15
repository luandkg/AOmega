package Fisica.Componentes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import AOmega.Core.Camera;
import Componentes.Componente;

public class Luz extends Componente {

	// DESENVOLVEDOR : LUAN FREITAS
	// DATA : 2020 03 13
	// COMPONENTE LUMINOSO

	public static final String ID = "Luz";

	private BufferedImage mLuz;

	private int mX;
	private int mY;
	private int mRaio;
	private float mLuminosidade;

	private int mR;
	private int mG;
	private int mB;

	public Luz(int eX, int eY, int eRaio, float eLuminosidade) {

		super(ID);

		mR = 0;
		mG = 0;
		mB = 0;

		this.mX = eX;
		this.mY = eY;
		this.mRaio = eRaio;

		Criar();

	}

	public void setCor(Color eCor) {

		mR = eCor.getRed();
		mG = eCor.getGreen();
		mB = eCor.getBlue();

		Criar();

	}

	public void setPos(int eX, int eY) {

		this.mX = eX;
		this.mY = eY;

		Criar();
	}

	public void setRaio(int eRaio) {
		this.mRaio = eRaio;
		Criar();
	}

	public void setLumen(float eLumen) {
		this.mLuminosidade = eLumen;
		Criar();
	}

	public void Criar() {

		mLuz = null;

		if (mRaio > 0) {
			mLuz = new BufferedImage(mRaio * 2, mRaio * 2, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = (Graphics2D) mLuz.getGraphics();

			for (int i = 0; i < mRaio; i++) {

				double luma = 1.0D - ((i + 0.001) / mRaio);
				int alpha = Math.min((int) (255.0D * luma * mLuminosidade), 255);

				g2.setColor(new Color(mR, mG, mB, alpha));

				g2.setStroke(new BasicStroke(2));
				g2.drawOval(mRaio - i, mRaio - i, i * 2, i * 2);

			}

		}

	}

	public void render(Graphics g2,Camera mCamera) {

		if (mLuz == null) {

		} else {

			g2.drawImage(mLuz, (mX - mLuz.getWidth() / 2)-mCamera.getX(),( mY - mLuz.getHeight() / 2)-mCamera.getY(), mLuz.getWidth(), mLuz.getHeight(),
					null);

		}

	}

}
