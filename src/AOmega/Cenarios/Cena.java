package AOmega.Cenarios;

import java.awt.Graphics;

public abstract class Cena {

	String mNome;

	public void setNome(String eNome) {
		this.mNome = eNome;
	}

	public String getNome() {
		return mNome;
	}
	
	
	public abstract void iniciar();

	public abstract void update(double dt);

	public abstract void draw(Graphics g);

}
