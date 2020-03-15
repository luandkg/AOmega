package AssetContainer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class ItemPack {

	private String mArquivo;
	private String mNome;
	private long mInicio;
	private long mFim;

	public ItemPack(String eArquivo, String eNome, long eInicio, long eFim) {
		mArquivo = eArquivo;
		mNome = eNome;
		mInicio = eInicio;
		mFim = eFim;
	}

	public String getNome() {
		return mNome;
	}

	public long getInicio() {
		return mInicio;
	}

	public long getFim() {
		return mFim;
	}

	public long getTamanho() {
		return mFim - mInicio;
	}

	public void setNome(String eNome) {
		mNome = eNome;
	}

	public String getTamanhoFormatado() {

		// System.out.println("Analisando : " + (int)getTamanho());

		int numero = 0;
		int dec = 0;
		String sufixo = "Bytes";

		int total = (int) getTamanho();

		if (total >= 1024) {

			sufixo = "KB";
			while (total >= 1024) {
				numero += 1;
				total -= 1024;
			}

			if (numero >= 1024) {
				total = numero;
				numero = 0;
				sufixo = "MB";
				while (total >= 1024) {
					numero += 1;
					total -= 1024;
				}
			}

			while (total > 10) {
				dec += 1;
				total -= 10;
			}

		} else {
			numero = total;
		}

		return numero + "." + dec + " " + sufixo;

	}

	public String getExtensao() {
		String ret = "";
		int i = getNome().length() - 1;

		while (i > 0) {
			String l = String.valueOf(getNome().charAt(i));

			if (l.contentEquals(".")) {
				break;
			} else {
				ret = l + ret;
			}
			i -= 1;
		}
		return ret;
	}

	public ByteBuffer LerByteBuffer() {

		ByteBuffer ret = ByteBuffer.allocate((int) getTamanho());

		int BUFFER_SIZE = 1;
		int l = 0;

		// byte[] bytes = new byte[(int) getTamanho()];

		RandomAccessFile inputStream;
		try {
			inputStream = new RandomAccessFile(mArquivo, "rw");

			inputStream.seek(mInicio);

			byte[] buffer = new byte[BUFFER_SIZE];

			while (inputStream.read(buffer) != -1) {
				// bytes[l] = buffer[0];

				ret.put(buffer[0]);

				l += 1;
				if (l >= (int) getTamanho()) {
					break;
				}
			}

			inputStream.close();

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return ret;

	}

	public void Exportar(String eLocalDeExportacao) {

		System.out.println("----- Exportando : " + mNome + " para : " + eLocalDeExportacao);

		int BUFFER_SIZE = 1;

		RandomAccessFile inputStream;
		try {
			inputStream = new RandomAccessFile(mArquivo, "rw");

			inputStream.seek(mInicio);
			int l = 0;

			RandomAccessFile outputStream;
			try {
				outputStream = new RandomAccessFile(eLocalDeExportacao, "rw");

				byte[] buffer = new byte[BUFFER_SIZE];

				while (inputStream.read(buffer) != -1) {
					outputStream.write(buffer);

					l += 1;
					if (l >= (int) getTamanho() - 1) {
						break;
					}

				}

				outputStream.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			inputStream.close();

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
