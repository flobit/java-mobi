package cn.flobit.libmobi.bean;

import cn.flobit.libmobi.utils.ByteUtils;

public class MobiPalmDatabaseRecord {
	int recordDataOffset;
	int recordAttributes;
	int uniqueID;

	public int getRecordDataOffset() {
		return recordDataOffset;
	}

	public void setRecordDataOffset(int recordDataOffset) {
		this.recordDataOffset = recordDataOffset;
	}

	public int getRecordAttributes() {
		return recordAttributes;
	}

	public void setRecordAttributes(int recordAttributes) {
		this.recordAttributes = recordAttributes;
	}

	public int getUniqueID() {
		return uniqueID;
	}

	public void setUniqueID(int uniqueID) {
		this.uniqueID = uniqueID;
	}

	public MobiPalmDatabaseRecord(byte[] rawData) {
		super();
		this.recordDataOffset = ByteUtils.byteToInt(rawData, 0, 4);
		recordAttributes = ByteUtils.byteToInt(rawData, 4, 1);
		uniqueID = ByteUtils.byteToInt(rawData, 5, 3);
	}

	@Override
	public String toString() {
		return "PalmDatabaseRecord [recordDataOffset=" + recordDataOffset + ", recordAttributes=" + recordAttributes
				+ ", uniqueID=" + uniqueID + "]";
	}
}
