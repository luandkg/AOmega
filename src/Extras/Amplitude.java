package Extras;

public class Amplitude {

	private int mInicio;
	private int mFim;
	
	public Amplitude(int eInicio,int eFim) {
		
		mInicio=eInicio;
		mFim=eFim;
		
	}
	
	
	public int getInicio() {return mInicio;}
	public int getFim() {return mFim;}

	
	public void Aumentar(int eValor) {
		
		mInicio +=eValor;
		mFim+=eValor;
		
		if (mInicio>360) {
			mInicio-=360;
		}
		if (mFim>360) {
			mFim-=360;
		}
		
		if (mInicio<0) {
			mInicio+=360;
		}

		if (mFim<0) {
			mFim+=360;
		}
		
		
	}
}
