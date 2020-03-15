package AOmega;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import AOmega.Cenarios.Cena;
import AOmega.Cenarios.Cenarios;
import AOmega.Utils.Tempo;

public class Windows extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	private Tempo mTempo;
	private boolean mExecutando;

	private int mLargura;
	private int mAltura;

	private int mCenaID;
	private Cena mCena;
	private Cenarios mCenarios;

	private Image mImagem;
	private Graphics2D mGraficos;

	public AOmega.Input.Mouse mMouse;
	public AOmega.Input.Teclado mTeclado;

	public Windows(String eTitulo, int eLargura, int eAltura) {

		mLargura = eLargura;
		mAltura = eAltura;

		this.setSize(eLargura, eAltura);
		this.setTitle(eTitulo);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		mMouse = new AOmega.Input.Mouse();
		mTeclado = new AOmega.Input.Teclado();

		this.addMouseListener(mMouse);
		this.addMouseMotionListener(mMouse);
		this.addMouseWheelListener(mMouse);
		this.addKeyListener(mTeclado);

		mTempo = new Tempo();
		mExecutando = false;
		mCenarios = new Cenarios();
		mCenaID = -1;
		mCena = null;

	}

	public int CriarCenario(Cena eCena) {
		return mCenarios.CriarCenario(eCena);
	}

	public void setCenario(int eCenaID) {
		mCena = mCenarios.getCenario(eCenaID).getCena();
		mCenaID = eCenaID;
		mCena.iniciar();
	}

	@Override
	public void run() {
		mExecutando = true;
		double Passado = 0.0;

		while (mExecutando) {
			try {
				double Agora = mTempo.getAgora();
				double delta = Agora - Passado;
				Passado = Agora;

				update(delta);

			} catch (Exception e) {

			}

		}

		mExecutando = false;
	}

	public int getLargura() {
		return mLargura;
	}

	public int getAltura() {
		return mAltura;
	}

	public AOmega.Input.Mouse getMouse() {
		return mMouse;
	}

	public AOmega.Input.Teclado getTeclado() {
		return mTeclado;
	}

	private int mDilatacao=0;
	
	public int getDilatacao() {return mDilatacao;}
	
	public void update(double delta) {
		// System.out.println("Delta : " + delta + " :: " + mCena.getNome());
		
		mDilatacao+=1;
		if (mDilatacao>500) {
			mDilatacao=0;
		}
		
		
		mCena.update(delta);
		draw(getGraphics());
	}

	public void draw(Graphics g) {
		if (mImagem == null) {
			mImagem = createImage(this.getLargura(), this.getAltura());
			// mImagem = new BufferedImage(mLargura, mAltura, BufferedImage.TYPE_INT_ARGB);

			mGraficos = (Graphics2D) mImagem.getGraphics();
		}

		mCena.draw(mGraficos);
		g.drawImage(mImagem, 0, 7, getLargura(), getAltura(), null);

	}

	public void Limpar(Graphics g) {
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getLargura(), this.getAltura());

	}
}
