package wusc.edu.pay.common.utils.rsa;


/**
 * 密码转pin
 * @author Allan
 *
 */
public class PinTool {
	
	public static synchronized byte[] pin2Block(String aPin) {

		byte[] pinBlock = new byte[8];
		pinBlock[0] = (byte) aPin.length();
		int intValue = Integer.valueOf(aPin);
		int k = 1000000;
		for (int i = 0; i < aPin.length(); i += 2) {
			byte b1 = (byte) (((intValue / (k / 10)) << 4) & 0xf0);
			byte b2 = (byte) ((intValue / (k / 100)) % 10);
			pinBlock[i / 2 + 1] = (byte) (b1 + b2);
			intValue = intValue % (k / 100);
			k = k / 100;

		}
		pinBlock[4] = (byte) 0xff;
		pinBlock[5] = (byte) 0xff;
		pinBlock[6] = (byte) 0xff;
		pinBlock[7] = (byte) 0xff;

		return pinBlock;
	}

}
