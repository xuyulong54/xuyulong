package wusc.edu.pay.common.utils.rsa;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

//字节管理
public class ByteManager {

	public static final int BUFFER_SIZE = 100;

	public static final byte[] getData(InputStream is, int length,
			int bufferSize){
		int lenLoaded = 0;
		byte[] data = null;
		ByteBuffer buffer = ByteBuffer.allocate(length);
		byte[] dataTemp = null;

		while (true) {
			if (lenLoaded >= length) {
				break;
			}
			if (length - lenLoaded < bufferSize) {
				bufferSize = length - lenLoaded;
			}
			dataTemp = new byte[bufferSize];
			try {
				is.read(dataTemp);
				buffer.put(dataTemp);
				// Log.d("Test", "dataTemp Size = " + dataTemp.length);
			} catch (Exception e) {
//				Log.d("Test", "read Exception = " + e.toString());
			}
			lenLoaded += bufferSize;
		}
		buffer.flip();
		data = buffer.array();

		return data;
	}

	/**
	 * 从输入流中读取固定长度的数据
	 * 
	 * @param is
	 *            输入流
	 * @param length
	 *            数据长度
	 * @return 输入流中的数据
	 */
	public static final byte[] getBytes(InputStream is, int length){
		byte[] result = null;
		int lenRead = 0;
		int lenTemp = 0;
		// byte[] temp = new byte[length];
		byte[] temp = new byte[256];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		while (true) {
			try {
				lenTemp = is.read(temp);
				if (lenTemp > 0) {
					baos.write(temp, 0, lenTemp);
					lenRead += lenTemp;
					if (lenRead == length) {
						break;
					} else {
						// temp = new byte[length-lenRead];
						temp = new byte[256];
					}
				}
			} catch (Exception e) {
//				Log.e("Test", "getBytes Exception = " + e.toString());
				break;
			}
		}
		result = baos.toByteArray();
		return result;
	}

	// 获取二维字节数组从第二个字节数组到最后一个字节数组的字节长度总和,再转成字节数组
	public static final byte[] getLengthAll(byte[][] srcData){
		int length = 0;
		for (int i = 1; i < srcData.length; i++) {
			length = length + srcData[i].length;
		}
		return intToByte(length);
	}

	/**
	 * 从ByteBuffer读取出一个字节数组数据（数据之前有四个字节代表数据的长度）
	 * 
	 * @param buffer
	 *            源ByteBuffer
	 * @return 字节数组数据
	 */
	public static final byte[] getDataWithLength(ByteBuffer buffer){
		byte[] lengthData = new byte[4];
		int lengthTemp = 0;
		byte[] data = null;
		buffer.get(lengthData);
		lengthTemp = bytesToInt(lengthData);
		if (lengthTemp > 0) {
			data = new byte[lengthTemp];
			buffer.get(data);
		}
		return data;
	}

	/**
	 * 从ByteBuffer读取出一个字节数组数据（数据之前有四个字节代表数据的长度）
	 * 
	 * @param buffer
	 *            源ByteBuffer
	 * @return 字节数组数据
	 */
	public static final byte[] getDataWithLength(byte[] srcData){
		ByteBuffer buffer = ByteBuffer.wrap(srcData);
		byte[] lengthData = new byte[4];
		int lengthTemp = 0;
		byte[] data = null;
		buffer.get(lengthData);
		lengthTemp = bytesToInt(lengthData);
		if (lengthTemp > 0) {
			data = new byte[lengthTemp];
			buffer.get(data);
		}
		buffer.clear();
		return data;
	}

	/**
	 * 从ByteBuffer读取出一个UTF-8编码的字符串（字符串数据之前有四个字节代表字符串数据的长度）
	 * 
	 * @param buffer
	 *            源ByteBuffer
	 * @return 字符串
	 */
	public static final String getStringWithLength(ByteBuffer buffer){
		String result = null;
		byte[] data = getDataWithLength(buffer);

		try {
			if (data != null) {
				result = new String(data, "UTF-8").trim();
			}
		} catch (Exception e) {
//			Log.d("Test", "getStringWithLength Length = " + e.toString());
		}
		return result;
	}

	/**
	 * 获取数据里的所有字符串（每个数据前都有长度）
	 * 
	 * @param srcData
	 *            源数据
	 * @param length
	 *            数据里包含的字符串总数
	 * @return 数据里包含的所有字符
	 */
	public static final String[] getAllString(byte[] srcData, int length){
		String[] allString = new String[length];
		byte[] lengthData = new byte[4];
		int lengthTemp = 0;
		byte[] dataString = null;
		ByteBuffer buffer = ByteBuffer.wrap(srcData);
		for (int i = 0; i < length; i++) {
			buffer.get(lengthData);
			lengthTemp = bytesToInt(lengthData);
			if (lengthTemp > 0) {
				dataString = new byte[lengthTemp];
				buffer.get(dataString);
				try {
					allString[i] = new String(dataString, "UTF-8").trim();
					// Log.d("Test", "getAllString "+i+" = " + allString[i]);
				} catch (UnsupportedEncodingException e) {
//					Log.d("Test", "getAllString Exception = " + e.toString());
				}
			}
		}
		buffer.clear();
		return allString;
	}

	/**
	 * 获取数据里的所有字符串（每个数据前都有长度）
	 * 
	 * @param srcData
	 *            源数据
	 * @param length
	 *            数据里包含的字符串总数
	 * @return 数据里包含的所有字符
	 */
	public static final String[] getAllString(ByteBuffer buffer, int length){
		String[] allString = new String[length];
		byte[] lengthData = new byte[4];
		int lengthTemp = 0;
		byte[] dataString = null;
		for (int i = 0; i < length; i++) {
			buffer.get(lengthData);
			lengthTemp = bytesToInt(lengthData);
			if (lengthTemp > 0) {
				dataString = new byte[lengthTemp];
				buffer.get(dataString);
				try {
					allString[i] = new String(dataString, "UTF-8").trim();
					// Log.d("Test", "getAllString "+i+" = " + allString[i]);
				} catch (UnsupportedEncodingException e) {
//					Log.d("Test", "getAllString Exception = " + e.toString());
				}
			}
		}
		return allString;
	}

	/**
	 * 获取源数据里的所有子数据（每个数据前都有长度）
	 * 
	 * @param srcData
	 *            源数据
	 * @param length
	 *            源数据里包含的子数据总数
	 * @return 源数据里包含的所有字符
	 */
	public static final byte[][] getAllData(byte[] srcData, int length){
		byte[][] allData = new byte[length][];
		byte[] lengthData = new byte[4];
		int lengthTemp = 0;
		byte[] subData = null;
		ByteBuffer buffer = ByteBuffer.wrap(srcData);
		for (int i = 0; i < length; i++) {
			buffer.get(lengthData);
			lengthTemp = bytesToInt(lengthData);
			if (lengthTemp > 0) {
				subData = new byte[lengthTemp];
				buffer.get(subData);
				allData[i] = subData;
			}
		}
		buffer.clear();
		return allData;
	}

	// 根据缓冲大小分割字节数组
	public static final byte[][] spliteByteArray(byte[] srcData){
		int lengthDest = srcData.length % BUFFER_SIZE == 0 ? srcData.length
				/ BUFFER_SIZE : srcData.length / BUFFER_SIZE + 1;
		byte[][] destData = new byte[lengthDest][];
		int lengthTemp = 0;
		ByteBuffer buffer = ByteBuffer.wrap(srcData);
		for (int i = 0; i < lengthDest; i++) {
			if (i == lengthDest - 1) {
				lengthTemp = srcData.length % BUFFER_SIZE == 0 ? BUFFER_SIZE
						: srcData.length % BUFFER_SIZE;
			} else {
				lengthTemp = BUFFER_SIZE;
			}
			destData[i] = new byte[lengthTemp];
			buffer.get(destData[i]);
		}
		buffer.clear();
		return destData;
	}

	// 连接字节数组
	public static final byte[] getIntegrationBytes(byte[][] byteData){
		byte[] data = null;
		int lengthData = 0;

		for (int i = 0; i < byteData.length; i++) {
			if (byteData[i] != null) {
				lengthData += byteData[i].length;
			}
		}
		ByteBuffer buffer = ByteBuffer.allocate(lengthData);

		for (int i = 0; i < byteData.length; i++) {
			if (byteData[i] != null) {
				buffer.put(byteData[i]);
			}
		}
		buffer.flip();
		data = buffer.array();
		buffer.clear();

		return data;
	}

	// 连接字节数组后将总长度加在前面
	public static final byte[] getIntegrationBytesWithLength(byte[][] byteData){
		byte[] data = null;
		int lengthData = 0;

		for (int i = 0; i < byteData.length; i++) {
			if (byteData[i] != null) {
				lengthData += byteData[i].length;
			}
		}

		ByteBuffer buffer = ByteBuffer.allocate(4 + lengthData);

		byte[] len = intToByte(lengthData);

		buffer.put(len);

		for (int i = 0; i < byteData.length; i++) {
			if (byteData[i] != null) {
				buffer.put(byteData[i]);
			}
		}
		buffer.flip();
		data = buffer.array();
		buffer.clear();

		return data;
	}

	// 将字节数组的长度加在前面
	public static final byte[] getIntegrationBytesWithLength(byte[] byteData){
		byte[] data = null;
		int lengthData = byteData.length;
		ByteBuffer buffer = ByteBuffer.allocate(lengthData + 4);
		byte[] len = intToByte(lengthData);
		buffer.put(len);
		buffer.put(byteData);
		buffer.flip();
		data = buffer.array();
		buffer.clear();
		return data;
	}

	// 将字节数组的长度加在前面
	public static final byte[] getIntegrationBytesWithLength(String info){
		byte[] byteData = null;
		try {
			byteData = info.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
//			LogUtil.d("getIntegrationBytesWithLength UnsupportedEncodingException = "
//					+ e.toString());
		}
		byte[] data = null;
		int lengthData = byteData.length;
		ByteBuffer buffer = ByteBuffer.allocate(lengthData + 4);
		byte[] len = intToByte(lengthData);
		buffer.put(len);
		buffer.put(byteData);
		buffer.flip();
		data = buffer.array();
		buffer.clear();
		return data;
	}

	// 将字符串拼成带数据长度的字节数组
	public static final byte[] getIntegrationBytes(String[] strData){
		byte[] data = null;
		int lengthData = 0;
		int lengthTemp = 0;
		byte[][] integrationLength = new byte[strData.length][];
		byte[][] integrationBytes = new byte[strData.length][];
		for (int i = 0; i < integrationBytes.length; i++) {
			if (strData[i] != null) {
				try {
					integrationBytes[i] = strData[i].getBytes("UTF-8");

				} catch (UnsupportedEncodingException e) {
//					Log.d("Test",
//							"getIntegrationBytes Exception = " + e.toString());
				}
				lengthTemp = integrationBytes[i].length;
				lengthData += lengthTemp;
				integrationLength[i] = intToByte(lengthTemp);
				lengthData += integrationLength[i].length;
			} else {
				integrationBytes[i] = null;
				lengthTemp = 0;
				integrationLength[i] = intToByte(lengthTemp);
				lengthData += integrationLength[i].length;
			}

		}
		ByteBuffer buffer = ByteBuffer.allocate(lengthData);
		for (int i = 0; i < integrationBytes.length; i++) {
			if (integrationLength[i] != null) {
				buffer.put(integrationLength[i]);
			}
			if (integrationBytes[i] != null) {
				buffer.put(integrationBytes[i]);
			}
		}
		buffer.flip();
		data = buffer.array();
		buffer.clear();
		return data;
	}

	// INT 转成 字节数组
	public static byte[] intToByte(int i){
		byte[] bt = new byte[4];
		bt[0] = (byte) (0xff & i);
		bt[1] = (byte) ((0xff00 & i) >> 8);
		bt[2] = (byte) ((0xff0000 & i) >> 16);
		bt[3] = (byte) ((0xff000000 & i) >> 24);
		return bt;
	}

	// 字节数组 转成 INT
	public static int bytesToInt(byte[] bytes){
		int num = bytes[0] & 0xFF;
		num |= ((bytes[1] << 8) & 0xFF00);
		num |= ((bytes[2] << 16) & 0xFF0000);
		num |= ((bytes[3] << 24) & 0xFF000000);
		return num;
	}
}
