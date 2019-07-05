package cn.flobit.libmobi.bean;

public class MobiEXTHRecord {
	public MobiEXTHRecord() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MobiEXTHRecord(int recordType, int recordLength, byte[] recordData) {
		super();
		this.recordType = recordType;
		this.recordLength = recordLength;
		this.recordData = recordData;
	}

	int recordType;
	int recordLength;
	byte[] recordData;

	public int getRecordType() {
		return recordType;
	}

	public void setRecordType(int recordType) {
		this.recordType = recordType;
	}

	public int getRecordLength() {
		return recordLength;
	}

	public void setRecordLength(int recordLength) {
		this.recordLength = recordLength;
	}

	public byte[] getRecordData() {
		return recordData;
	}

	public void setRecordData(byte[] recordData) {
		this.recordData = recordData;
	}
}
