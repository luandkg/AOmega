package Extras;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import AOmega.Core.Imagens;
import AOmega.Utils.Escritor;

public class InfoBoxer {

	private int mEixoX;
	private int mEixoY;

	private Escritor mEscritorBranco;
private Imagens mImagens;

	public InfoBoxer(Imagens eImagens) {
		mEixoX = 0;
		mEixoY = 0;

		mImagens = eImagens;
		
		mEscritorBranco = new Escritor(20, Color.BLACK);

	}

	public void Caixa(Graphics g,int eLargura,int eAltura) {
		
		BufferedImage mImg = mImagens.obter("glassPanel");
		g.drawImage(mImg, mEixoX,mEixoY-15,eLargura,eAltura,null);

	}
	
	
	public void InfoTag(Graphics g, String eTexto) {


		BufferedImage mImg = mImagens.obter("dotGreen");

		g.drawImage(mImg, mEixoX+15,mEixoY-15 + 20,15,15,null);
		
		mEscritorBranco.Escreve(g, eTexto, mEixoX + 25+15, mEixoY+20);

		
		mEixoY += 50;
	}

	public void Defina(int eEixoX,int eEixoY) {
		mEixoX = eEixoX;
		mEixoY = eEixoY;
	}
	
	
	public void DefinaY(int eEixoY) {

		mEixoY = eEixoY;

	}

	public void DefinaX(int eEixoX) {

		mEixoX = eEixoX;

	}

}
