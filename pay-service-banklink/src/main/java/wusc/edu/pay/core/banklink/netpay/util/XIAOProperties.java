package wusc.edu.pay.core.banklink.netpay.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class XIAOProperties extends Properties {
	private static final long serialVersionUID = -4776807166451805513L;

	public XIAOProperties() {
	}

	public XIAOProperties(Properties properties) {
		defaults = properties;
	}

	public synchronized void load(InputStreamReader inputStreamReader) throws IOException {
		BufferedReader bufferedreader = new BufferedReader(inputStreamReader);
		do {
			String s;
			int i;
			int j;
			do {
				char c;
				do {
					do {
						s = bufferedreader.readLine();
						if (s == null)
							return;
					} while (s.length() <= 0);
					c = s.charAt(0);
				} while (c == '#' || c == '!');
				String s1;
				String s2;
				for (; continueLine(s); s = new String(s2 + s1)) {
					s1 = bufferedreader.readLine();
					if (s1 == null)
						s1 = "";
					s2 = s.substring(0, s.length() - 1);
					int k = 0;
					for (k = 0; k < s1.length(); k++)
						if (" \t\r\n\f".indexOf(s1.charAt(k)) == -1)
							break;

					s1 = s1.substring(k, s1.length());
				}

				i = s.length();
				for (j = 0; j < i; j++)
					if (" \t\r\n\f".indexOf(s.charAt(j)) == -1)
						break;

			} while (j == i);
			int l;
			for (l = j; l < i; l++) {
				char c1 = s.charAt(l);
				if (c1 == '\\') {
					l++;
					continue;
				}
				if ("=: \t\r\n\f".indexOf(c1) != -1)
					break;
			}

			int i1;
			for (i1 = l; i1 < i; i1++)
				if (" \t\r\n\f".indexOf(s.charAt(i1)) == -1)
					break;

			if (i1 < i && "=:".indexOf(s.charAt(i1)) != -1)
				i1++;
			for (; i1 < i; i1++)
				if (" \t\r\n\f".indexOf(s.charAt(i1)) == -1)
					break;

			String s3 = s.substring(j, l);
			String s4 = l >= i ? "" : s.substring(i1, i);
			s3 = loadConvert(s3);
			s4 = loadConvert(s4);
			put(s3, s4);
		} while (true);
	}

	private boolean continueLine(String s) {
		int i = 0;
		for (int j = s.length() - 1; j >= 0 && s.charAt(j--) == '\\';)
			i++;

		return i % 2 == 1;
	}

	private String loadConvert(String s) {
		int i = s.length();
		StringBuffer stringbuffer = new StringBuffer(i);
		for (int j = 0; j < i;) {
			char c = s.charAt(j++);
			if (c == '\\') {
				c = s.charAt(j++);
				if (c == 'u') {
					int k = 0;
					for (int l = 0; l < 4; l++) {
						c = s.charAt(j++);
						switch (c) {
						case 48: // '0'
						case 49: // '1'
						case 50: // '2'
						case 51: // '3'
						case 52: // '4'
						case 53: // '5'
						case 54: // '6'
						case 55: // '7'
						case 56: // '8'
						case 57: // '9'
							k = ((k << 4) + c) - 48;
							break;

						case 97: // 'a'
						case 98: // 'b'
						case 99: // 'c'
						case 100: // 'd'
						case 101: // 'e'
						case 102: // 'f'
							k = ((k << 4) + 10 + c) - 97;
							break;

						case 65: // 'A'
						case 66: // 'B'
						case 67: // 'C'
						case 68: // 'D'
						case 69: // 'E'
						case 70: // 'F'
							k = ((k << 4) + 10 + c) - 65;
							break;

						case 58: // ':'
						case 59: // ';'
						case 60: // '<'
						case 61: // '='
						case 62: // '>'
						case 63: // '?'
						case 64: // '@'
						case 71: // 'G'
						case 72: // 'H'
						case 73: // 'I'
						case 74: // 'J'
						case 75: // 'K'
						case 76: // 'L'
						case 77: // 'M'
						case 78: // 'N'
						case 79: // 'O'
						case 80: // 'P'
						case 81: // 'Q'
						case 82: // 'R'
						case 83: // 'S'
						case 84: // 'T'
						case 85: // 'U'
						case 86: // 'V'
						case 87: // 'W'
						case 88: // 'X'
						case 89: // 'Y'
						case 90: // 'Z'
						case 91: // '['
						case 92: // '\\'
						case 93: // ']'
						case 94: // '^'
						case 95: // '_'
						case 96: // '`'
						default:
							throw new IllegalArgumentException("Malformed \\uxxxx encoding.");
						}
					}

					stringbuffer.append((char) k);
				} else {
					if (c == 't')
						c = '\t';
					else if (c == 'r')
						c = '\r';
					else if (c == 'n')
						c = '\n';
					else if (c == 'f')
						c = '\f';
					stringbuffer.append(c);
				}
			} else {
				stringbuffer.append(c);
			}
		}

		return stringbuffer.toString();
	}
}
