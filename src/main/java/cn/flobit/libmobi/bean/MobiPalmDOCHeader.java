package cn.flobit.libmobi.bean;

import cn.flobit.libmobi.utils.ByteUtils;

public class MobiPalmDOCHeader {
	CompressionType compressionType;
	int unused;
	int textLength;
	int recordCount;
	int recordSize;
	int currentPosition;
	int encryptionType;
	int unknown;

	public enum CompressionType {
		HUFF_CDIC_COMPRESSION(17480), NO_COMPRESSION(1), PALMDOC_COMPRESSION(2);
		public int value;

		private CompressionType(int value) {
			this.value = value;
		}

		public static CompressionType valueOf(int value) {
			if (17480 == value) {
				return HUFF_CDIC_COMPRESSION;
			} else if (value == 2) {
				return PALMDOC_COMPRESSION;
			} else {
				return NO_COMPRESSION;
			}
		}
	}

	public int getTextLength() {
		return textLength;
	}

	public void setTextLength(int textLength) {
		this.textLength = textLength;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public int getRecordSize() {
		return recordSize;
	}

	public void setRecordSize(int recordSize) {
		this.recordSize = recordSize;
	}

	public int getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(int currentPosition) {
		this.currentPosition = currentPosition;
	}

	public int getEncryptionType() {
		return encryptionType;
	}

	public void setEncryptionType(int encryptionType) {
		this.encryptionType = encryptionType;
	}
	

	public CompressionType getCompressionType() {
		return compressionType;
	}

	public void setCompressionType(CompressionType compressionType) {
		this.compressionType = compressionType;
	}

	/**
	 * 根据传入的数据的前16字节的数据初始化PalmDOCHeader,
	 * 
	 * 
	 * @param rawData 16 byte rawData
	 */
	public MobiPalmDOCHeader(byte[] rawData) {
		super();
		this.compressionType = CompressionType.valueOf(ByteUtils.byteToInt(rawData, 0, 2));
		this.textLength = ByteUtils.byteToInt(rawData, 4, 4);
		this.recordCount = ByteUtils.byteToInt(rawData, 8, 2);
		this.recordSize = ByteUtils.byteToInt(rawData, 10, 2);
		this.currentPosition = ByteUtils.byteToInt(rawData, 12, 4);
		this.encryptionType = ByteUtils.byteToInt(rawData, 12, 2);
	}
}
