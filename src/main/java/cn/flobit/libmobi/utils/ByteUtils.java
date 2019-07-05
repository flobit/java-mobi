package cn.flobit.libmobi.utils;

import java.io.UnsupportedEncodingException;

public class ByteUtils {

	/**
	 * byte数组转换成integer
	 * 
	 * @param buff   要转换的字符数组
	 * @param offset 偏移
	 * @param length 长度
	 * @return
	 */
	public static int byteToInt(byte[] buff, int offset, int length) {
		length = length > 4 ? 4 : length;
		int number = 0;
		int index = length - 1;
		int dest = offset + length;
		for (int i = offset; i < dest; i++) {
			number += (buff[i] & 0x000000ff) << ((index--) * 8);
		}
		return number;
	}

	public static int byteToInt(byte[] buff) {
		return buff == null ? 0
				: byteToInt(buff, 0, buff.length <= 4 ? buff.length : buff.length > 4 ? 4 : buff.length);
	}

	public static String readBytesAsString(byte[] buff, int offset, int length, String charSet) {
		String result = null;
		try {
			result = new String(buff, offset, length, charSet);
		} catch (UnsupportedEncodingException e) {
			result = new String(buff, offset, length);
		}
		return result;
	}

	/**
	 * 将integer转换为byte数组
	 * 
	 * @param src  要装换的数据
	 * @param size 返回的字节数
	 * @return
	 */
	public static byte[] intToBytes(int src, int size) {
		byte[] buff = new byte[4];
		byte[] result = new byte[size];
		for (int i = 0; i < 4; i++) {
			buff[i] = (byte) ((src & (0x000000ff << (8 * i))) >> (8 * i));
		}
		for (int i = 0; i < size; i++) {
			result[i] = buff[size - 1 - i];
		}
		return result;
	}

	public static byte[] intToBytes(int src) {
		return intToBytes(src, 4);
	}

	public static void main(String[] args) {
		byte[] data = intToBytes(123321);
		System.out.println(byteToInt(data));
	}
}
