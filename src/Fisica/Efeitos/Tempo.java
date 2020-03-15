package Fisica.Efeitos;

import Fisica.Fisica;

public class Tempo {

	private Fisica mFisica;

	private int mCiclo;
	private int mTamanhoDoCiclo;

	private long mDia;

	public Tempo(Fisica eFisica, int eTamanhoDoCiclo) {

		mFisica = eFisica;
		mTamanhoDoCiclo = eTamanhoDoCiclo;
		mDia = 0;
		mCiclo = 0;

	}

	public void Aplicar() {

		mCiclo += 1;

		if (mCiclo >= mTamanhoDoCiclo) {
			mDia += 1;
			mCiclo = 0;
		}
	}

	public long getDia() {
		return mDia;
	}

	public int getCiclo() {
		return mCiclo;
	}

	public int getTamanhoDoCiclo() {
		return mTamanhoDoCiclo;
	}

	public void setTamanhoDoCiclo(int eTamanhoDoCiclo) {
		mTamanhoDoCiclo = eTamanhoDoCiclo;
	}
	
	public Fases getFase() {
		if (mCiclo>(mTamanhoDoCiclo/2)) {
			return Fases.NOITE;
		} else {
			return Fases.DIA;
		}
	}

	public enum Fases {

		DIA(0), NOITE(1);

		private int mValor;

		Fases(int eValor) {
			mValor = eValor;
		}

		public int getValor() {
			return mValor;
		}
	}
}
