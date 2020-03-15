package AOmega.Core;

import java.util.ArrayList;

import AOmega.Estrutural.Rect;
import AOmega.Estrutural.Vetor2;
import Constantes.Constantes;

public class Contato {

	public ArrayList<Vetor2> OcupandoCom(Rect mAreaDoCampo, Vetor2 SomarLocal) {
		ArrayList<Vetor2> LS_1 = Ocupando(mAreaDoCampo);

		for (Vetor2 V1 : LS_1) {
			V1.setX(V1.getX() + SomarLocal.getX());
			V1.setY(V1.getY() + SomarLocal.getY());

		}

		return LS_1;
	}

	public ArrayList<Vetor2> Ocupando(Rect mAreaDoCampo) {
		ArrayList<Vetor2> ret = new ArrayList<Vetor2>();

		int PX = ((mAreaDoCampo.getX() + mAreaDoCampo.getLargura()));
		while (PX > ((mAreaDoCampo.getX()))) {
			PX -= Constantes.UNIDADE;

			int PY = (mAreaDoCampo.getY() + mAreaDoCampo.getAltura());

			while (PY > ((mAreaDoCampo.getY()))) {
				PY -= Constantes.UNIDADE;

				ret.add(new Vetor2(PX, PY));

			}

		}

		return ret;
	}

	public ArrayList<Rect> OcupandoAreas(Rect mAreaDoCampo) {
		ArrayList<Rect> ret = new ArrayList<Rect>();

		int PX = ((mAreaDoCampo.getX() + mAreaDoCampo.getLargura()));
		while (PX > ((mAreaDoCampo.getX()))) {
			PX -= Constantes.UNIDADE;

			int PY = (mAreaDoCampo.getY() + mAreaDoCampo.getAltura());

			while (PY > ((mAreaDoCampo.getY()))) {
				PY -= Constantes.UNIDADE;
				ret.add(new Rect(PX, PY, Constantes.UNIDADE, Constantes.UNIDADE));

			}

		}

		return ret;
	}

	public ArrayList<Vetor2> OcupandoDaArea(Rect eArea) {
		ArrayList<Vetor2> ret = new ArrayList<Vetor2>();

		int PX = ((eArea.getX() + eArea.getLargura()));
		while (PX > ((eArea.getX()))) {
			PX -= Constantes.UNIDADE;

			int PY = (eArea.getY() + eArea.getAltura());

			while (PY > ((eArea.getY()))) {
				PY -= Constantes.UNIDADE;

				ret.add(new Vetor2(PX, PY));

			}

		}

		return ret;
	}

	public boolean ColideArea(Rect mAreaDoCampo, Rect eArea) {
		ArrayList<Vetor2> ret = OcupandoDaArea(eArea);
		return ColideGrupo(mAreaDoCampo, ret);
	}

	public boolean Colide(Rect mAreaDoCampo, Vetor2 eV) {
		boolean ret = false;

		for (Vetor2 v : OcupandoDaArea(mAreaDoCampo)) {

			if (v.Igual(eV)) {
				ret = true;
				break;
			}

		}

		return ret;
	}

	public boolean ColideGrupo(Rect mAreaDoCampo, ArrayList<Vetor2> LS_2) {
		boolean ret = false;

		ArrayList<Vetor2> LS_1 = this.Ocupando(mAreaDoCampo);

		for (Vetor2 V1 : LS_1) {

			for (Vetor2 V2 : LS_2) {

				if (V1.Igual(V2)) {
					ret = true;
					break;
				}

			}

			if (ret == true) {
				break;
			}
		}

		return ret;
	}

}
