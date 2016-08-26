package wusc.edu.pay.common.utils.rsa;

/** Utility class to perform HEX encoding/decoding of values. */
public class HexCodec {

	static final char[] HEX = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	public static String hexEncode(byte[] buffer) {
		if (buffer.length == 0) {
			return "";
		}
		int holder = 0;
		char[] chars = new char[buffer.length * 2];
		for (int i = 0; i < buffer.length; i++) {
			holder = (buffer[i] & 0xf0) >> 4;
			chars[i * 2] = HEX[holder];
			holder = buffer[i] & 0x0f;
			chars[(i * 2) + 1] = HEX[holder];
		}
		return new String(chars);
	}

	public static byte[] hexDecode(String hex) {
		// A null string returns an empty array
		if (hex == null || hex.length() == 0) {
			return new byte[0];
		} else if (hex.length() < 3) {
			return new byte[] { (byte) (Integer.parseInt(hex, 16) & 0xff) };
		}
		// Adjust accordingly for odd-length strings
		int count = hex.length();
		int nibble = 0;
		if (count % 2 != 0) {
			count++;
			nibble = 1;
		}
		byte[] buf = new byte[count / 2];
		char c = 0;
		int holder = 0;
		int pos = 0;
		for (int i = 0; i < buf.length; i++) {
			for (int z = 0; z < 2 && pos < hex.length(); z++) {
				c = hex.charAt(pos++);
				if (c >= 'A' && c <= 'F') {
					c -= 55;
				} else if (c >= '0' && c <= '9') {
					c -= 48;
				} else if (c >= 'a' && c <= 'f') {
					c -= 87;
				}
				if (nibble == 0) {
					holder = c << 4;
				} else {
					holder |= c;
					buf[i] = (byte) holder;
				}
				nibble = 1 - nibble;
			}
		}
		return buf;
	}

}
