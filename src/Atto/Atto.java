package Atto;

public class Atto {

	private int mAttos;
	private int mUggos;
	private int mEttos;

	public Atto(int eAtto, int eUggos, int eEttos) {
		mAttos = eAtto;
		mUggos = eUggos;
		mEttos = eEttos;
	}

	public int getAttos() {
		return mAttos;
	}

	public int getUggos() {
		return mUggos;
	}

	public int getEttos() {
		return mEttos;
	}

	public int getTotal() {

		return (mAttos * 100 * 100) + (mUggos * 100) + (mEttos);

	}

	public String toString() {
		String S1 = String.valueOf(mAttos);
		String S2 = String.valueOf(mUggos);
		String S3 = String.valueOf(mEttos);

		if (S1.length() == 1) {
			S1 = "0" + S1;
		}
		if (S2.length() == 1) {
			S2 = "0" + S2;
		}
		if (S3.length() == 1) {
			S3 = "0" + S3;
		}
		return S1 + ":" + S2 + ":" + S3;
	}
}
