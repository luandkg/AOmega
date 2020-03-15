package Fisica;

import java.util.ArrayList;
import java.util.Random;

import AOmega.Estrutural.Localizacao;
import AOmega.Estrutural.Rect;
import AOmega.Estrutural.Vetor2;
import AOmega.Organizacao.SuperGrupo;
import AOmega.Utils.Cronometro;
import Componentes.Entidade;
import Componentes.Posicao;
import Componentes.Tamanho;
import Constantes.Constantes;
import Fisica.Componentes.Colisao;
import Fisica.Efeitos.Atracao;
import Fisica.Efeitos.Gravidade;
import Fisica.Efeitos.Repulsao;
import Fisica.Efeitos.Tempo;
import Fisica.Efeitos.Termico;
import Fisica.Escalas.Deca;
import Objetos.Bloquinho;
import Objetos.Terreno;

public class Fisica {

	private SuperGrupo mSuperGrupo;
	
	private Tempo mTempo;
	private Gravidade mGravidade;
	private Atracao mAtracao;
	private Repulsao mRespulsao;
	private Termico mTermico;

	private ArrayList<String> Limitadores_Estruturais;
	private ArrayList<String> Limitadores_Corpos;

	private Cronometro Cron_Gravidade;

	public Fisica(SuperGrupo eSuperGrupo) {

		mSuperGrupo = eSuperGrupo;
		
		Cron_Gravidade = new Cronometro(Constantes.TEMPUM);

		
		mTempo = new Tempo(this,100);

		mGravidade = new Gravidade(this,0);
		mAtracao = new Atracao(this);
		mRespulsao = new Repulsao(this);
		mTermico = new Termico(this);

		
		Limitadores_Estruturais = new ArrayList<String>();
		Limitadores_Corpos = new ArrayList<String>();

	}

	
	public void setGravidade(int eGravidade) {
		mGravidade.setGravidade(eGravidade);
	}
	
	public int getGravidade() {
		return mGravidade.getGravidade();
	}
	
	public void update() {

		Cron_Gravidade.Esperar();
		if (Cron_Gravidade.Esperado()) {
			
			mAtracao.Aplicar();
			mRespulsao.Aplicar();
			mGravidade.Aplicar();
			mTermico.Aplicar();
			mTempo.Aplicar();
		}
		
		

	}
	
	
	public Tempo getTempo() {
		return mTempo;
	}

	public void adicionar_LimitadorEstrutural(String eLimitador) {
		Limitadores_Estruturais.add(eLimitador);
	}

	public void adicionar_LimitadorCorpo(String eLimitador) {
		Limitadores_Corpos.add(eLimitador);
	}

	public ArrayList<Entidade> getEntidades() {
		return mSuperGrupo.getEntidades();
	}

	public ArrayList<Entidade> getCorpos() {

		ArrayList<Entidade> Limitadores = new ArrayList<Entidade>();
		for (String eLimitador : Limitadores_Corpos) {
			Limitadores.addAll(mSuperGrupo.getGrupo(eLimitador).getEntidades());
		}

		return Limitadores;

	}

	public ArrayList<Entidade> getTerrenos() {

		ArrayList<Entidade> Limitadores = new ArrayList<Entidade>();
		for (String eLimitador : Limitadores_Estruturais) {
			Limitadores.addAll(mSuperGrupo.getGrupo(eLimitador).getEntidades());
		}

		return Limitadores;
	}

	public ArrayList<Entidade> EntidadesDoTipo(String eTipo) {

		ArrayList<Entidade> ret = new ArrayList<Entidade>();
		for (Entidade mEntidade : getEntidades()) {
			if (mEntidade.getTipo().contentEquals(eTipo)) {
				ret.add(mEntidade);
			}
		}
		return ret;
	}

	public boolean ColisaoComTerreno_Abaixo(Bloquinho BC) {
		boolean estaNoTerreno = false;

		Colisao BCColisao = (Colisao) BC.obterComponente(Colisao.ID);

		for (Entidade mEntidade : getTerrenos()) {

			Colisao mColisao = (Colisao) mEntidade.obterComponente(Colisao.ID);

			if (mColisao.ColideGrupo(BCColisao.OcupandoCom(new Vetor2(0, Constantes.UNIDADE)))) {
				estaNoTerreno = true;
				break;
			}

		}
		return estaNoTerreno;
	}
	
	
	public String ChaoStatus(Bloquinho BC,int eAlturaMax) {
		String ret = "";
		
		int eChao = this.ChaoProximo(BC, eAlturaMax);
		if (eChao == 0) {
			ret = "" + this.ChaoProximoNome(BC, 2);
		} else {
			ret = "" + Deca.get(Constantes.getEspacoPorUnidade(eChao)) + "";
		}
		return ret;
	}

	public int ChaoProximo(Bloquinho BC, int QuantasVezes) {

		boolean estaNoTerreno = false;

		int ret = 0;
		for (int r = 1; r < QuantasVezes; r++) {

			Colisao BCColisao = (Colisao) BC.obterComponente(Colisao.ID);

			for (Entidade mEntidade : getEntidades()) {

				if (mEntidade.existeComponente(Colisao.ID)) {

					if (!mEntidade.getNome().contentEquals(BC.getNome())) {
						Colisao mColisao = (Colisao) mEntidade.obterComponente(Colisao.ID);
						Posicao mPosicao = (Posicao) mEntidade.obterComponente(Posicao.ID);

						if (mPosicao.getY() > BC.getPos().getY()) {

							if (mColisao.ColideGrupo(BCColisao.OcupandoCom(new Vetor2(0, +Constantes.UNIDADE * r)))) {
								estaNoTerreno = true;
								break;
							}

						}

					}

				}

			}
			ret = r - 1;
			if (estaNoTerreno) {
				break;
			}
		}

		if (!estaNoTerreno) {
			ret = QuantasVezes;
		}

		return ret;
	}

	public String ChaoProximoNome(Bloquinho BC, int QuantasVezes) {

		boolean estaNoTerreno = false;
		String retnome = "";

		int ret = 0;
		for (int r = 1; r < QuantasVezes; r++) {

			Colisao BCColisao = (Colisao) BC.obterComponente(Colisao.ID);

			for (Entidade mEntidade : getEntidades()) {

				if (mEntidade.existeComponente(Colisao.ID)) {

					if (!mEntidade.getNome().contentEquals(BC.getNome())) {
						Colisao mColisao = (Colisao) mEntidade.obterComponente(Colisao.ID);
						Posicao mPosicao = (Posicao) mEntidade.obterComponente(Posicao.ID);

						if (mPosicao.getY() > BC.getPos().getY()) {

							if (mColisao.ColideGrupo(BCColisao.OcupandoCom(new Vetor2(0, +Constantes.UNIDADE * r)))) {
								estaNoTerreno = true;
								retnome = mEntidade.getNome();
								break;
							}

						}

					}

				}

			}
			ret = r + 1;
			if (estaNoTerreno) {
				break;
			}
		}

		if (!estaNoTerreno) {
			ret = QuantasVezes + 1;
		}

		return retnome;
	}

	public boolean ColisaoComBloquinho_ABAIXO(Bloquinho BC) {
		boolean estaNoTerreno = false;
		Colisao BCColisao = (Colisao) BC.obterComponente(Colisao.ID);

		for (Entidade mEntidade : getEntidades()) {

			if (mEntidade.getTipo().contentEquals("Bloquinho")) {
				Colisao mColisao = (Colisao) mEntidade.obterComponente(Colisao.ID);

				if (!mEntidade.getNome().contentEquals(BC.getNome())) {

					if (mColisao.ColideGrupo(BCColisao.OcupandoCom(new Vetor2(0, Constantes.UNIDADE)))) {
						estaNoTerreno = true;
						break;
					}

				}

			}
		}
		return estaNoTerreno;
	}

	public boolean ColisaoComBloquinho_ESQUERDA(Bloquinho BC) {
		boolean estaNoTerreno = false;

		Colisao BCColisao = (Colisao) BC.obterComponente(Colisao.ID);

		for (Entidade mEntidade : getEntidades()) {

			if (mEntidade.getTipo().contentEquals("Bloquinho")) {
				Colisao mColisao = (Colisao) mEntidade.obterComponente(Colisao.ID);
				if (!mEntidade.getNome().contentEquals(BC.getNome())) {

					if (mColisao.ColideGrupo(BCColisao.OcupandoCom(new Vetor2(-Constantes.UNIDADE, 0)))) {
						estaNoTerreno = true;
						break;
					}

				}

			}
		}
		return estaNoTerreno;
	}

	public boolean ColisaoComBloquinho_DIREITA(Bloquinho BC) {
		boolean estaNoTerreno = false;

		Colisao BCColisao = (Colisao) BC.obterComponente(Colisao.ID);

		for (Entidade mEntidade : getEntidades()) {

			if (mEntidade.getTipo().contentEquals("Bloquinho")) {
				Colisao mColisao = (Colisao) mEntidade.obterComponente(Colisao.ID);
				if (!mEntidade.getNome().contentEquals(BC.getNome())) {

					if (mColisao.ColideGrupo(BCColisao.OcupandoCom(new Vetor2(+Constantes.UNIDADE, 0)))) {
						estaNoTerreno = true;
						break;
					}

				}

			}
		}
		return estaNoTerreno;
	}

	public boolean ColisaoComQualquer_ACIMA(Entidade BC) {
		boolean estaNoTerreno = false;

		Colisao BCColisao = (Colisao) BC.obterComponente(Colisao.ID);

		for (Entidade mEntidade : getEntidades()) {

			if (mEntidade.existeComponente(Colisao.ID)) {

				if (!mEntidade.getNome().contentEquals(BC.getNome())) {
					Colisao mColisao = (Colisao) mEntidade.obterComponente(Colisao.ID);

					if (mColisao.ColideGrupo(BCColisao.OcupandoCom(new Vetor2(0, -Constantes.UNIDADE)))) {
						estaNoTerreno = true;
						break;
					}

				}

			}

		}
		return estaNoTerreno;
	}

	public boolean ColisaoComQualquer_DIREITA(Entidade BC) {
		boolean estaNoTerreno = false;

		Colisao BCColisao = (Colisao) BC.obterComponente(Colisao.ID);

		for (Entidade mEntidade : getEntidades()) {

			if (mEntidade.existeComponente(Colisao.ID)) {

				if (!mEntidade.getNome().contentEquals(BC.getNome())) {
					Colisao mColisao = (Colisao) mEntidade.obterComponente(Colisao.ID);

					if (mColisao.ColideGrupo(BCColisao.OcupandoCom(new Vetor2(+Constantes.UNIDADE, 0)))) {
						estaNoTerreno = true;
						break;
					}

				}

			}

		}
		return estaNoTerreno;
	}

	public boolean ColisaoComQualquer_ESQUERDA(Entidade BC) {
		boolean estaNoTerreno = false;

		Colisao BCColisao = (Colisao) BC.obterComponente(Colisao.ID);

		for (Entidade mEntidade : getEntidades()) {

			if (mEntidade.existeComponente(Colisao.ID)) {

				if (!mEntidade.getNome().contentEquals(BC.getNome())) {
					Colisao mColisao = (Colisao) mEntidade.obterComponente(Colisao.ID);

					if (mColisao.ColideGrupo(BCColisao.OcupandoCom(new Vetor2(-Constantes.UNIDADE, 0)))) {
						estaNoTerreno = true;
						break;
					}

				}

			}

		}
		return estaNoTerreno;
	}

	public boolean ColisaoComQualquer_ABAIXO(Entidade BC) {
		boolean estaNoTerreno = false;

		Colisao BCColisao = (Colisao) BC.obterComponente(Colisao.ID);

		for (Entidade mEntidade : getEntidades()) {

			if (mEntidade.existeComponente(Colisao.ID)) {

				if (!mEntidade.getNome().contentEquals(BC.getNome())) {
					Colisao mColisao = (Colisao) mEntidade.obterComponente(Colisao.ID);

					if (mColisao.ColideGrupo(BCColisao.OcupandoCom(new Vetor2(0, +Constantes.UNIDADE)))) {
						estaNoTerreno = true;
						break;
					}

				}

			}

		}
		return estaNoTerreno;
	}

	public Localizacao Referencia(Entidade Referenciador, Entidade Objeto) {
		Localizacao ret = Localizacao.NENHUMA;

		Posicao Pos_Referenciador = (Posicao) Referenciador.obterComponente(Posicao.ID);
		Posicao Pos_Objeto = (Posicao) Objeto.obterComponente(Posicao.ID);

		if (Pos_Objeto.getX() == Pos_Referenciador.getX()) {

			if (Pos_Objeto.getY() < Pos_Referenciador.getY()) {

				ret = Localizacao.ACIMA;

			} else if (Pos_Objeto.getY() > Pos_Referenciador.getY()) {

				ret = Localizacao.ABAIXO;

			} else if (Pos_Objeto.getY() == Pos_Referenciador.getY()) {

				ret = Localizacao.IGUAL;

			}

		} else if (Pos_Objeto.getX() < Pos_Referenciador.getX()) {

			if (Pos_Objeto.getY() < Pos_Referenciador.getY()) {

				ret = Localizacao.ACIMA_ESQUERDA;

			} else if (Pos_Objeto.getY() > Pos_Referenciador.getY()) {

				ret = Localizacao.ABAIXO_ESQUERDA;

			} else if (Pos_Objeto.getY() == Pos_Referenciador.getY()) {

				ret = Localizacao.ESQUERDA;

			}

		} else if (Pos_Objeto.getX() > Pos_Referenciador.getX()) {

			if (Pos_Objeto.getY() < Pos_Referenciador.getY()) {

				ret = Localizacao.ACIMA_DIREITA;

			} else if (Pos_Objeto.getY() > Pos_Referenciador.getY()) {

				ret = Localizacao.ABAIXO_DIREITA;

			} else if (Pos_Objeto.getY() == Pos_Referenciador.getY()) {

				ret = Localizacao.DIREITA;

			}

		}

		return ret;
	}

}
