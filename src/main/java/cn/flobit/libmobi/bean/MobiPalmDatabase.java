package cn.flobit.libmobi.bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.flobit.libmobi.utils.ByteUtils;

public class MobiPalmDatabase {
	String name;
	int attributes;
	int version;
	int creationDate;
	int modificationDate;
	int lastBackupDate;
	int modificationNumber;
	int appInfoID;
	int sortInfoID;
	String type;
	String creator;
	int uniquekDseed;
	int nextRecordListID;
	int recordCount;
	List<MobiPalmDatabaseRecord> records = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAttributes() {
		return attributes;
	}

	public void setAttributes(int attributes) {
		this.attributes = attributes;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(int creationDate) {
		this.creationDate = creationDate;
	}

	public int getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(int modificationDate) {
		this.modificationDate = modificationDate;
	}

	public int getLastBackupDate() {
		return lastBackupDate;
	}

	public void setLastBackupDate(int lastBackupDate) {
		this.lastBackupDate = lastBackupDate;
	}

	public int getModificationNumber() {
		return modificationNumber;
	}

	public void setModificationNumber(int modificationNumber) {
		this.modificationNumber = modificationNumber;
	}

	public int getAppInfoID() {
		return appInfoID;
	}

	public void setAppInfoID(int appInfoID) {
		this.appInfoID = appInfoID;
	}

	public int getSortInfoID() {
		return sortInfoID;
	}

	public void setSortInfoID(int sortInfoID) {
		this.sortInfoID = sortInfoID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public int getUniquekDseed() {
		return uniquekDseed;
	}

	public void setUniquekDseed(int uniquekDseed) {
		this.uniquekDseed = uniquekDseed;
	}

	public int getNextRecordListID() {
		return nextRecordListID;
	}

	public void setNextRecordListID(int nextRecordListID) {
		this.nextRecordListID = nextRecordListID;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public List<MobiPalmDatabaseRecord> getRecords() {
		return records;
	}

	public MobiPalmDatabaseRecord getRecord(int index) {
		return records.get(index);
	}

	public void setRecords(List<MobiPalmDatabaseRecord> records) {
		this.records = records;
	}

	public static MobiPalmDatabase parseFromStream(InputStream inputStream) throws IOException {
		byte[] buff = new byte[78];
		inputStream.read(buff);
		MobiPalmDatabase database = new MobiPalmDatabase();
		database.name = new String(buff, 0, 32);
		database.attributes = ByteUtils.byteToInt(buff, 32, 2);
		database.version = ByteUtils.byteToInt(buff, 34, 2);
		database.creationDate = ByteUtils.byteToInt(buff, 36, 4);
		database.modificationDate = ByteUtils.byteToInt(buff, 40, 4);
		database.modificationNumber = ByteUtils.byteToInt(buff, 44, 4);
		database.lastBackupDate = ByteUtils.byteToInt(buff, 48, 4);
		database.appInfoID = ByteUtils.byteToInt(buff, 52, 4);
		database.sortInfoID = ByteUtils.byteToInt(buff, 56, 4);
		database.type = new String(buff, 60, 4);
		database.creator = new String(buff, 4, 4);
		database.uniquekDseed = ByteUtils.byteToInt(buff, 68, 4);
		database.nextRecordListID = ByteUtils.byteToInt(buff, 72, 4);
		database.recordCount = ByteUtils.byteToInt(buff, 76, 2);
		for (int i = 0; i < database.recordCount; i++) {
			inputStream.read(buff, 0, 8);
			database.records.add(new MobiPalmDatabaseRecord(buff));
		}
		inputStream.read(buff, 0, 2);
		return database;
	}

	@Override
	public String toString() {
		return "PalmDatabase [name=" + name + ", attributes=" + attributes + ", version=" + version + ", creationDate="
				+ creationDate + ", modificationDate=" + modificationDate + ", lastBackupDate=" + lastBackupDate
				+ ", modificationNumber=" + modificationNumber + ", appInfoID=" + appInfoID + ", sortInfoID="
				+ sortInfoID + ", type=" + type + ", creator=" + creator + ", uniquekDseed=" + uniquekDseed
				+ ", nextRecordListID=" + nextRecordListID + ", recordCount=" + recordCount + ", records=" + records
				+ "]";
	}
}
