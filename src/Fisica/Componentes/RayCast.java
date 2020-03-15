package Fisica.Componentes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.LinkedList;

import Componentes.Entidade;
import Componentes.Posicao;
import Componentes.Tamanho;

public class RayCast {

	private float mLuminosidade;

	private int mR;
	private int mG;
	private int mB;
	private int mRaio;
	private int lineResolution = 2100;

	public RayCast() {

		mRaio = 0;
		setCor(Color.BLACK, 0.5f);

	}

	public void setRaio(int eRaio) {
		mRaio = eRaio;
	}

	public void setResolucao(int eResolucao) {
		lineResolution = eResolucao;
	}

	public LinkedList<Line2D.Float> calcRays(LinkedList<Line2D.Float> lines, int x, int y) {
		LinkedList<Line2D.Float> rays = new LinkedList<Line2D.Float>();
		for (int i = 0; i < lineResolution; i++) {
			double dir = (Math.PI * 2) * ((double) i / lineResolution);
			float minDist = mRaio;
			for (Line2D.Float l : lines) {
				float dist = getLinhaRay(x, y, x + (float) Math.cos(dir) * mRaio, y + (float) Math.sin(dir) * mRaio,
						l.x1, l.y1, l.x2, l.y2);
				if (dist < minDist && dist > 0) {
					minDist = dist;
				}
			}
			rays.add(new Line2D.Float(x, y, x + (float) Math.cos(dir) * minDist, y + (float) Math.sin(dir) * minDist));
		}
		return rays;
	}

	public static float getDistancia(float x1, float y1, float x2, float y2) {
		return (float) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
	}

	public static float getLinhaRay(float p0_x, float p0_y, float p1_x, float p1_y, float p2_x, float p2_y, float p3_x,
			float p3_y) {
		float s1_x, s1_y, s2_x, s2_y;
		s1_x = p1_x - p0_x;
		s1_y = p1_y - p0_y;
		s2_x = p3_x - p2_x;
		s2_y = p3_y - p2_y;

		float s, t;
		s = (-s1_y * (p0_x - p2_x) + s1_x * (p0_y - p2_y)) / (-s2_x * s1_y + s1_x * s2_y);
		t = (s2_x * (p0_y - p2_y) - s2_y * (p0_x - p2_x)) / (-s2_x * s1_y + s1_x * s2_y);

		if (s >= 0 && s <= 1 && t >= 0 && t <= 1) {
			// Collision detected
			float x = p0_x + (t * s1_x);
			float y = p0_y + (t * s1_y);

			return getDistancia(p0_x, p0_y, x, y);
		}

		return -1; // No collision
	}

	public void setCor(Color eCor, float eLuminosidade) {

		mR = eCor.getRed();
		mG = eCor.getGreen();
		mB = eCor.getBlue();
		mLuminosidade = eLuminosidade;

	}

	public Color getCor() {

		int i = 10;

		double luma = 1.0D - ((i + 0.001) / 100);
		int alpha = Math.min((int) (255.0D * luma * mLuminosidade), 255);

		Color mCor = (new Color(mR, mG, mB, alpha));
		return mCor;
	}

	public void renderLimitadores(Graphics g, LinkedList<Line2D.Float> eLimitadores) {

		g.setColor(Color.DARK_GRAY);
		for (Line2D.Float l : eLimitadores) {
			g.drawLine((int) l.x1, (int) l.y1, (int) l.x2, (int) l.y2);
		}

	}

	public void renderCalcRays(Graphics g, LinkedList<Line2D.Float> lines, int x, int y) {

		g.setColor(getCor());
		for (Line2D.Float l : calcRays(lines, x, y)) {
			g.drawLine((int) l.x1, (int) l.y1, (int) l.x2, (int) l.y2);
		}

	}

	public void renderRays(Graphics g, LinkedList<Line2D.Float> eRays) {

		g.setColor(getCor());
		for (Line2D.Float l : eRays) {
			g.drawLine((int) l.x1, (int) l.y1, (int) l.x2, (int) l.y2);
		}

	}

	public void renderLine(Graphics g, Line2D.Float l) {
		g.setColor(getCor());
		g.drawLine((int) l.x1, (int) l.y1, (int) l.x2, (int) l.y2);

	}

	/// AMPLITUDE

	public void renderCalc_Amplitude(Graphics g, LinkedList<Line2D.Float> lines, int x, int y, int a1, int a2) {

		g.setColor(getCor());
		for (Line2D.Float l : calcRays_Amplitude(lines, x, y, a1, a2)) {
			g.drawLine((int) l.x1, (int) l.y1, (int) l.x2, (int) l.y2);
		}

	}

	public LinkedList<Line2D.Float> calcRays_Amplitude(LinkedList<Line2D.Float> lines, int x, int y, int a1, int a2) {

		float taxa = (float) 360 / (float) lineResolution;
		double acu = 0;

		double D1 = (double) a1;
		double D2 = (double) a2;

		LinkedList<Line2D.Float> Raios = new LinkedList<Line2D.Float>();

		if (D2 < D1) {

			for (int i = 0; i < lineResolution; i++) {

				if (acu >= D1 || acu <= D2) {

					double dir = (Math.PI * 2) * ((double) i / lineResolution);
					float minDist = mRaio;
					for (Line2D.Float l : lines) {
						float dist = getLinhaRay(x, y, x + (float) Math.cos(dir) * mRaio,
								y + (float) Math.sin(dir) * mRaio, l.x1, l.y1, l.x2, l.y2);
						if (dist < minDist && dist > 0) {
							minDist = dist;
						}
					}

					Raios.add(new Line2D.Float(x, y, x + (float) Math.cos(dir) * minDist,
							y + (float) Math.sin(dir) * minDist));

				}

				acu += taxa;

			}

		} else {

			for (int i = 0; i < lineResolution; i++) {

				if (acu >= D1 && acu <= D2) {

					double dir = (Math.PI * 2) * ((double) i / lineResolution);
					float minDist = mRaio;
					for (Line2D.Float l : lines) {
						float dist = getLinhaRay(x, y, x + (float) Math.cos(dir) * mRaio,
								y + (float) Math.sin(dir) * mRaio, l.x1, l.y1, l.x2, l.y2);
						if (dist < minDist && dist > 0) {
							minDist = dist;
						}
					}

					Raios.add(new Line2D.Float(x, y, x + (float) Math.cos(dir) * minDist,
							y + (float) Math.sin(dir) * minDist));

				}

				acu += taxa;

			}

		}

		// System.out.println("Taxa : " + taxa + " D1 : " + D1 + " D2 : " + D2);

		return Raios;
	}

	/// AMPLITUDE

	public ArrayList<Entidade> InterAmplitude(ArrayList<Entidade> Objetos, int x, int y, int a1, int a2) {

		ArrayList<Entidade> Saida = new ArrayList<Entidade>();

		for (Entidade mEntidade : Objetos) {

			if (mEntidade.existeComponente(Posicao.ID) && (mEntidade.existeComponente(Tamanho.ID))) {

				Posicao P = (Posicao) mEntidade.obterComponente(Posicao.ID);
				Tamanho T = (Tamanho) mEntidade.obterComponente(Tamanho.ID);

				Line2D.Float Linha_1 = new Line2D.Float(P.getX(), P.getY(), P.getX() + T.getLargura(), P.getY());

				Line2D.Float Linha_2 = new Line2D.Float(P.getX(), P.getY() + T.getAltura(), P.getX() + T.getLargura(),
						P.getY() + T.getAltura());

				Line2D.Float Linha_3 = new Line2D.Float(P.getX(), P.getY(), P.getX(), P.getY() + T.getAltura());

				Line2D.Float Linha_4 = new Line2D.Float(P.getX() + T.getLargura(), P.getY(), P.getX() + T.getLargura(),
						P.getY() + T.getAltura());

				if (InterRays_Amplitude(Linha_1, x, y, a1, a2)) {
					
					if (y+mRaio<P.getY() ){
						

					}
					
					System.out.println("OBJV : " + mEntidade.getNome() + " :: " + (P.getY()));

					
					
					if (!Saida.contains(mEntidade)) {
						Saida.add(mEntidade);
					}
				} else {

					if (InterRays_Amplitude(Linha_2, x, y, a1, a2)) {
						if (!Saida.contains(mEntidade)) {
							Saida.add(mEntidade);
						}
					} else {

						if (InterRays_Amplitude(Linha_3, x, y, a1, a2)) {
							if (!Saida.contains(mEntidade)) {
								Saida.add(mEntidade);
							}
						} else {

							if (InterRays_Amplitude(Linha_4, x, y, a1, a2)) {
								if (!Saida.contains(mEntidade)) {
									Saida.add(mEntidade);
								}
							}

						}
					}

				}

			}

		}
		return Saida;

	}

	public boolean InterRays_Amplitude(Line2D.Float l, int x, int y, int a1, int a2) {

		float taxa = (float) 360 / (float) lineResolution;
		double acu = 0;

		double D1 = (double) a1;
		double D2 = (double) a2;
		boolean ret = false;

		if (D2 < D1) {

			for (int i = 0; i < lineResolution; i++) {

				if (acu >= D1 || acu <= D2) {

					double dir = (Math.PI * 2) * ((double) i / lineResolution);
					boolean dist = getLinhaRayColisao(x, y, x + (float) Math.cos(dir) * mRaio,
							y + (float) Math.sin(dir) * mRaio, l.x1, l.y1, l.x2, l.y2);

					if (dist) {
						ret = true;
					}
				}

				acu += taxa;
				if (ret) {
					break;
				}
			}

		} else {

			for (int i = 0; i < lineResolution; i++) {

				if (acu >= D1 && acu <= D2) {

					double dir = (Math.PI * 2) * ((double) i / lineResolution);
					boolean dist = getLinhaRayColisao(x, y, x + (float) Math.cos(dir) * mRaio,
							y + (float) Math.sin(dir) * mRaio, l.x1, l.y1, l.x2, l.y2);

					if (dist) {
						ret = true;
					}
				}

				acu += taxa;
				if (ret) {
					break;
				}

			}

		}

		// System.out.println("Taxa : " + taxa + " D1 : " + D1 + " D2 : " + D2);

		return ret;
	}

	public  boolean getLinhaRayColisao(float p0_x, float p0_y, float p1_x, float p1_y, float p2_x, float p2_y,
			float p3_x, float p3_y) {
		float s1_x, s1_y, s2_x, s2_y;
		s1_x = p1_x - p0_x;
		s1_y = p1_y - p0_y;
		s2_x = p3_x - p2_x;
		s2_y = p3_y - p2_y;
		boolean ret = false;

		float s, t;
		s = (-s1_y * (p0_x - p2_x) + s1_x * (p0_y - p2_y)) / (-s2_x * s1_y + s1_x * s2_y);
		t = (s2_x * (p0_y - p2_y) - s2_y * (p0_x - p2_x)) / (-s2_x * s1_y + s1_x * s2_y);

		if (s >= 0 && s <= 1 && t >= 0 && t <= 1) {
			// Collision detected
			float x = p0_x + (t * s1_x);
			float y = p0_y + (t * s1_y);

			if( getDistancia(p0_x, p0_y, x, y)<mRaio) {
				ret=true;
			}

		}

		return ret;
	}
}
