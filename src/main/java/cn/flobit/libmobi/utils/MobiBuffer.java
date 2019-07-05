package cn.flobit.libmobi.utils;

import java.io.IOException;
import java.io.OutputStream;

public class MobiBuffer {
	byte rawData[];
	int offset = 0;

	public int offset() {
		return offset;
	}

	public void offset(int offset) {
		this.offset = offset;
	}

	public MobiBuffer(byte[] buff) {
		super();
		this.rawData = buff;
	}

	public int size() {
		return this.rawData.length;
	}

	public int getInt() {
		int result = 0;
		int i = 4;
		int bytesLeft = rawData.length - offset;
		int iterator = 0;
		while (i-- > 0 && bytesLeft-- > 0) {
			result |= (int) (0xff & rawData[offset + iterator++]) << (i * 8);
		}
		/* increase counter by 4 bytes only, 4 bytes overlap on each call */
		offset += 4;
		return result;
	}

	public String getString(int length) {
		String result = new String(rawData, offset, length);
		offset += length;
		return result;
	}

	public long fillLong() {
		long result = 0;
		int i = 8;
		int bytesLeft = rawData.length - offset;
		int iterator = 0;
		while (i-- > 0 && bytesLeft-- > 0) {
			result |= (long) (0x000000ff & rawData[offset + iterator++]) << (i * 8);
		}
		/* increase counter by 4 bytes only, 4 bytes overlap on each call */
		offset += 4;
		return result;
	}

	public byte[] getValidData(int extraFlags) {
		if (rawData.length - getRecordExtraSize(extraFlags) == 0)
			return rawData;
		byte[] buff = new byte[rawData.length - getRecordExtraSize(extraFlags)];
		System.arraycopy(rawData, 0, buff, 0, buff.length);
		return buff;
	}

	static final int STOP_FLAG = 0x80;
	static final int MASK = 0x7f;

	public class VarLengthAndLength {
		public int varLength;
		public int length;

		public VarLengthAndLength(int varLength, int length) {
			super();
			this.varLength = varLength;
			this.length = length;
		}
	}

	public VarLengthAndLength getVarLength(int length) {
		int varLength = 0;
		int byteCount = 0;
		byte buff;
		int shift = 0;
		do {
			buff = rawData[offset--];
			varLength = varLength | (int) (buff & MASK) << shift;
			shift += 7;
			length++;
		} while (0 == (buff & STOP_FLAG) && (byteCount < 4));
		return new VarLengthAndLength(varLength, length);
	}

	public int getRecordExtraSize(int extraFlags) {
		int extraSize = 0;
		/* set pointer at the end of the record data */
		this.offset = rawData.length - 1;
		int length = 0;
		int size = 0;
		for (int bit = 15; bit > 0; bit--) {
			if ((extraFlags & (1 << bit)) != 0) {
				/* bit is set */
				length = 0;
				/* size contains varlen itself and optional data */
				VarLengthAndLength item = getVarLength(length);
				length = item.length;
				size = item.varLength;
				/* skip data */
				/* TODO: read and store in record struct */
				this.offset -= (int) (size - length);
				extraSize += size;
			}
		}
		/* check bit 0 */
		if ((extraFlags & 1) != 0) {
			/* two first bits hold size */
			extraSize += (rawData[offset++] & 0x3) + 1;
		}
		return extraSize;
	}

	int getByte() {
		return Byte.toUnsignedInt(rawData[offset++]);
	}

	void getBytes(byte[] buff, int offset2, int length) {
		System.arraycopy(rawData, offset, buff, offset2, length);
		this.offset += length;
	}

	/**
	 * 从当前offset开始,往OutputStream中写入length个字节
	 * 
	 * @param os     要写入的OutputStream
	 * @param length 要写入的字节数
	 * @throws IOException
	 */
	int writeTo(OutputStream os, int length) throws IOException {
		int written = rawData.length - offset;
		written = written > length ? length : written;
		os.write(rawData, offset, written);
		return written;
	}
}