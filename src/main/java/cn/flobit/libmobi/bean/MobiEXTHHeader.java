package cn.flobit.libmobi.bean;

import java.util.ArrayList;
import java.util.List;

public class MobiEXTHHeader {
	/** offset = 4,the characters E X T H */
	String identifier = "EXTH";
	int headerLength;
	int recordCount;
	int recordType;
	int recordLength;
	List<MobiEXTHRecord> records = new ArrayList<>(10);

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public int getHeaderLength() {
		return headerLength;
	}

	public void setHeaderLength(int headerLength) {
		this.headerLength = headerLength;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public int getRecordType() {
		return recordType;
	}

	public void setRecordType(int recordType) {
		this.recordType = recordType;
	}

	public int getRecordLength() {
		return recordLength;
	}

	public void addRecord(MobiEXTHRecord record) {
		this.records.add(record);
	}

	public void setRecordLength(int recordLength) {
		this.recordLength = recordLength;
	}

	public List<MobiEXTHRecord> getRecords() {
		return records;
	}

	public void setRecords(List<MobiEXTHRecord> records) {
		this.records = records;
	}
}
