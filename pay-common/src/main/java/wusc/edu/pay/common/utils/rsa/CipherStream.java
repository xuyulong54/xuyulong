package wusc.edu.pay.common.utils.rsa;

import java.io.IOException;
import java.io.InputStream;

public class CipherStream extends InputStream {
	private InputStream source;
	private byte[] key;
	int len;
	int cur;

	public CipherStream(InputStream source, byte[] keyS){
		this.source = source;
		if (keyS == null || keyS.length < 5) {
			throw new IllegalArgumentException("The key is null or too short.");
		}

		this.key = new byte[keyS.length * 2];
		for (int i = 0; i < keyS.length; i++) {
			key[i] = (byte) (keyS[i] - i);
			key[i + keyS.length] = (byte) (keyS[i] + i);
		}

		len = key.length;
		cur = 0;
	}

	@Override
	public int read() throws IOException{
		int i = source.read();
		if (i < 0)
			return i;

		int k = key[cur];

		int n = (cur + 1) % len;

		int nk = k + key[n];

		key[cur] = (byte) nk;

		int r = (i ^ k) & 0x00ff;

		cur = n;

		return r;
	}
}
