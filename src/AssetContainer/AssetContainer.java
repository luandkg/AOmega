package AssetContainer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class AssetContainer {

	public final String DESENVOLVEDOR = "LUAN FREITAS";
	public final String DDC = "2020 02 27";

	public final String DDA_01 = "2020 02 28";

	private static final String ALFABETO = " ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_./";

	private String mLocalArquivo;
	private ArrayList<ItemPack> mls;

	public AssetContainer() {
		mls = new ArrayList<ItemPack>();
		mLocalArquivo = "";
	}

	public int getQuantidade() {
		return mls.size();
	}

	public ArrayList<ItemPack> Listar() {
		return mls;
	}

	public ItemPack getRes(String eLocal) {
		ItemPack ret = null;
		for (ItemPack ArchiveAssetC : mls) {
			if ((ArchiveAssetC.getNome()).contentEquals(eLocal)) {
				ret = ArchiveAssetC;
				break;
			}
		}
		return ret;
	}

	public boolean Existe(String eLocal) {
		boolean ret = false;
		for (ItemPack ArchiveAssetC : mls) {
			if (ArchiveAssetC.getNome().contentEquals(eLocal)) {
				ret = true;
				break;
			}
		}
		return ret;
	}

	public void Associar(String eLocalRes, String eNovoLocalRes) {

		if (Existe(eLocalRes) && !Existe(eNovoLocalRes)) {

			ItemPack p = getRes(eLocalRes);

			ItemPack p2 = new ItemPack(mLocalArquivo, eNovoLocalRes, p.getInicio(), p.getFim());
			mls.add(p2);

		}
	}

	public String LogDeMontagem() {

		String ret = "";

		ret += "\n ------------------ MONTANDO ASSET CONTAINER ----------------- ";
		ret += "\n";

		for (ItemPack ArquivoCorrente : Listar()) {
			// ret +="\n - " + ArquivoCorrente.getNome() + "\t : " +
			// NovoNome(ArquivoCorrente.getNome());
			ret += "\n - " + (ArquivoCorrente.getNome());

		}

		ret += "\n";
		ret += "\n";
		ret += " ------------------ ------------------------- ----------------- ";

		return ret;

	}

	public void AplicarPrefixo(String ePrefixo, String eNovoPrefixo) {
		for (ItemPack ArquivoCorrente : Listar()) {

			String eNovoNome = ArquivoCorrente.getNome().replace(ePrefixo, eNovoPrefixo);

			ArquivoCorrente.setNome(eNovoNome);

			// if ( NovoNome(ArquivoCorrente.getNome()).startsWith(ePrefixo)) {
			// i+=1;
			// }

		}

	}

	public int Contagem(String ePrefixo) {
		int i = 0;

		for (ItemPack ArquivoCorrente : Listar()) {
			if (ArquivoCorrente.getNome().startsWith(ePrefixo)) {
				i += 1;
			}

		}

		return i;
	}

	public void Montar(String eLocalArquivo) {

		mLocalArquivo = eLocalArquivo;
		mls.clear();

		try {
			RandomAccessFile outputStream = new RandomAccessFile(eLocalArquivo, "r");

			outputStream.seek(0);

			long tam = outputStream.length();
			long index = 0;

			int c = (int) outputStream.readByte();
			index += 1;

			while (index < tam) {

				int b = (int) outputStream.readByte();
				index += 1;
				// System.out.println(b);

				if (b == 11) {

					// NOME
					int nome[] = new int[1024];
					byte inicio[] = new byte[10];
					byte fim[] = new byte[8];

					for (int i = 0; i < 1024; i++) {
						int s = (int) outputStream.readByte();
						nome[i] = s;
						index += 1;

					}

					String Nome = BytesToString(nome);

					// INICIO
					b = (int) outputStream.readByte();

					String temp = "";

					index += 1;
					for (int i = 0; i < 10; i++) {
						byte bc = outputStream.readByte();
						if (i < 8) {
							inicio[i] = bc;
							temp += " " + bc;
						}
						index += 1;

					}

					// System.out.println(b + " : " + temp);

					String tempf = "";

					// FIM
					b = (int) outputStream.readByte();
					index += 1;
					for (int i = 0; i < 10 && index < tam; i++) {
						byte bc = outputStream.readByte();
						if (i < 8) {
							fim[i] = bc;
							tempf += " " + bc;

						}
						index += 1;

					}

					// System.out.println(b + " : " + tempf);

					b = (int) outputStream.readByte();
					index += 1;
					// System.out.println(b);

					long linicio = bytesToLong(inicio);
					long lfim = bytesToLong(fim);

					mls.add(new ItemPack(eLocalArquivo, Nome, linicio, lfim));
				} else if (b == 15) {
					break;
				}
				index += 1;
			}

			outputStream.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	public static void Criar(String eLocalArquivo, String eLocalDiretorio) {

		ArrayList<String> mArquivos = new ArrayList<String>();

		File Local = new File(eLocalDiretorio);

		for (int i = 0; i < Local.listFiles().length; i++) {
			File C = Local.listFiles()[i];

			if (C.isDirectory()) {
				subDir(C.getAbsolutePath(), mArquivos);
			} else if (C.isFile()) {
				mArquivos.add(C.getAbsolutePath());
			}

		}

		for (String a : mArquivos) {

			a = a.replace(eLocalDiretorio, "/");
			System.out.println(" - " + a);

		}

		File Arquivo = new File(eLocalArquivo);

		if (Arquivo.exists()) {
			Arquivo.delete();
		}

		int BUFFER_SIZE = 1;

		try {
			RandomAccessFile outputStream = new RandomAccessFile(eLocalArquivo, "rw");

			outputStream.write(10);

			for (String a : mArquivos) {

				outputStream.write(11);

				for (int i = 0; i < 1024; i++) {
					outputStream.write(0);
				}

				outputStream.write(12);
				for (int i = 0; i < 10; i++) {
					outputStream.write(0);
				}
				outputStream.write(13);
				for (int i = 0; i < 10; i++) {
					outputStream.write(0);
				}
				outputStream.write(14);

			}

			outputStream.write(15);

			outputStream.seek(0);

			int n = 0;

			for (String a : mArquivos) {

				String NomeCorrente = a.replace(eLocalDiretorio, "/");

				int formula = 1 + n * (1 + 1024 + 1 + 10 + 1 + 10 + 1);

				outputStream.seek(formula + 1);

				outputStream.write(StringToBytes(NomeCorrente));

				// ESCREVE CONTEUDO
				outputStream.seek(outputStream.length());

				long inicio = outputStream.length();

				InputStream inputStream = new FileInputStream(a);

				byte[] buffer = new byte[BUFFER_SIZE];

				while (inputStream.read(buffer) != -1) {
					outputStream.write(buffer);
				}
				inputStream.close();

				long fim = outputStream.length();

				outputStream.seek(formula + 1 + 1024 + 1);
				outputStream.write(longToBytes(inicio));

				outputStream.seek(formula + 1 + 1024 + 1 + 10 + 1);
				outputStream.write(longToBytes(fim));

				byte lb[] = longToBytes(inicio);
				String temp = "";
				for (int t = 0; t < 8; t++) {
					temp += " " + lb[t];

				}

				byte lf[] = longToBytes(fim);
				String tempf = "";
				for (int t = 0; t < 8; t++) {
					tempf += " " + lf[t];

				}

				// System.out.println("Inicio : " + inicio + " :: " + temp);
				// System.out.println("Fim : " + fim + " :: " + tempf);

				n += 1;
			}

			outputStream.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	public static byte[] StringToBytes(String eString) {
		ByteBuffer buffer = ByteBuffer.allocate(1024);

		int i = 0;
		int o = eString.length();
		while (i < o) {
			String l = String.valueOf(eString.charAt(i));

			int procurador = 10;
			boolean enc = false;

			for (int j = 0; j < ALFABETO.length(); j++) {
				String k = String.valueOf(ALFABETO.charAt(j));

				if (l.contentEquals(k)) {
					enc = true;
					break;
				}
				procurador += 1;
			}

			if (enc == false) {
				procurador = 10;
			}

			buffer.put((byte) procurador);

			i += 1;
		}

		return buffer.array();
	}

	private static String BytesToString(int eBytes[]) {

		int i = 0;
		int o = eBytes.length;

		String ret = "";

		while (i < o) {
			int l = eBytes[i];

			if (l < 10) {
				break;
			}
			// System.out.println("MAX : " + l);

			int procurador = 10;
			boolean enc = false;

			for (int j = 0; j < ALFABETO.length(); j++) {
				String k = String.valueOf(ALFABETO.charAt(j));

				if (procurador == l) {
					enc = true;
					ret += k;
					break;
				}
				procurador += 1;
			}

			i += 1;
		}

		return ret;
	}

	private static byte[] longToBytes(long x) {
		ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
		buffer.putLong(x);
		return buffer.array();
	}

	private static long bytesToLong(byte[] bytes) {
		ByteBuffer buffer = ByteBuffer.wrap(bytes);
		return buffer.getLong();
	}

	private static void subDir(String eLocalSubDir, ArrayList<String> mArquivos) {

		File Local = new File(eLocalSubDir);

		for (int i = 0; i < Local.listFiles().length; i++) {
			File C = Local.listFiles()[i];

			if (C.isDirectory()) {
				subDir(C.getAbsolutePath(), mArquivos);
			} else if (C.isFile()) {
				mArquivos.add(C.getAbsolutePath());
			}

		}

	}
}
