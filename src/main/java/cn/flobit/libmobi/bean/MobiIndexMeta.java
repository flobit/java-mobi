package cn.flobit.libmobi.bean;

import cn.flobit.libmobi.utils.ByteUtils;

public class MobiIndexMeta {
	/** 标识符 偏移 0 */
	static final String identifier = "INDX";
	/** INDX的长度 偏移:4字节 长度:4字节 */
	int headerLength;
	/**
	 * offset 8 : the type of the index. Known values: 0 - normal index, 2 -
	 * inflections
	 */
	int indexType;
	/** offset 12 */
	int unknown1 = 0xffffffff;
	/** offset 16 */
	int unknown2 = 0xffffffff;
	/** offset 20 : the offset to the IDXT section */
	int idxtOffset;
	/** offset 24 : the number of index records */
	int indexCount;
	/** offset 28 : 1252 = CP1252 (WinLatin1); 65001 = UTF-8 */
	int indexEncoding;
	/** offset 32 : the language code of the index */
	int indexLanguage;
	/** offset 36 : the number of index entries */
	int totalIndexCount;
	/** offset 40 : the offset to the ORDT section */
	int ordtOffset;
	/** offset 44 : the offset to the LIGT section */
	int ligtOffset;
	/** offset 48 :LIGT的记录数 */
	int ligtEntryCount;
	/** offset 52 :CNCX的记录数 */
	int cncxEntryCount;
	/** offset 164 */
	int ordtType;
	/** offset 168 */
	int ordtEntryCount;
	/** offset 172 */
	int ordt1Offset;
	/** offset 176 */
	int ordt2Offset;
	/** offset 180 */
	int indexNameOffset;
	/** offset 184 */
	int indexNameLength;
	
	public String getIdentifier() {
		return identifier;
	}
	

	public int getHeaderLength() {
		return headerLength;
	}


	public void setHeaderLength(int headerLength) {
		this.headerLength = headerLength;
	}


	public int getIndexType() {
		return indexType;
	}


	public void setIndexType(int indexType) {
		this.indexType = indexType;
	}


	public int getIdxtOffset() {
		return idxtOffset;
	}


	public void setIdxtOffset(int idxtOffset) {
		this.idxtOffset = idxtOffset;
	}


	public int getIndexCount() {
		return indexCount;
	}


	public void setIndexCount(int indexCount) {
		this.indexCount = indexCount;
	}


	public int getIndexEncoding() {
		return indexEncoding;
	}


	public void setIndexEncoding(int indexEncoding) {
		this.indexEncoding = indexEncoding;
	}


	public int getIndexLanguage() {
		return indexLanguage;
	}


	public void setIndexLanguage(int indexLanguage) {
		this.indexLanguage = indexLanguage;
	}


	public int getTotalIndexCount() {
		return totalIndexCount;
	}


	public void setTotalIndexCount(int totalIndexCount) {
		this.totalIndexCount = totalIndexCount;
	}


	public int getOrdtOffset() {
		return ordtOffset;
	}


	public void setOrdtOffset(int ordtOffset) {
		this.ordtOffset = ordtOffset;
	}


	public int getLigtOffset() {
		return ligtOffset;
	}


	public void setLigtOffset(int ligtOffset) {
		this.ligtOffset = ligtOffset;
	}


	public int getLigtEntryCount() {
		return ligtEntryCount;
	}


	public void setLigtEntryCount(int ligtEntryCount) {
		this.ligtEntryCount = ligtEntryCount;
	}


	public int getCncxEntryCount() {
		return cncxEntryCount;
	}


	public void setCncxEntryCount(int cncxEntryCount) {
		this.cncxEntryCount = cncxEntryCount;
	}


	public int getOrdtType() {
		return ordtType;
	}


	public void setOrdtType(int ordtType) {
		this.ordtType = ordtType;
	}


	public int getOrdtEntryCount() {
		return ordtEntryCount;
	}


	public void setOrdtEntryCount(int ordtEntryCount) {
		this.ordtEntryCount = ordtEntryCount;
	}


	public int getOrdt1Offset() {
		return ordt1Offset;
	}


	public void setOrdt1Offset(int ordt1Offset) {
		this.ordt1Offset = ordt1Offset;
	}


	public int getOrdt2Offset() {
		return ordt2Offset;
	}


	public void setOrdt2Offset(int ordt2Offset) {
		this.ordt2Offset = ordt2Offset;
	}


	public int getIndexNameOffset() {
		return indexNameOffset;
	}


	public void setIndexNameOffset(int indexNameOffset) {
		this.indexNameOffset = indexNameOffset;
	}


	public int getIndexNameLength() {
		return indexNameLength;
	}


	public void setIndexNameLength(int indexNameLength) {
		this.indexNameLength = indexNameLength;
	}


	public MobiIndexMeta(byte[] rawData) {
		super();
		this.headerLength = ByteUtils.byteToInt(rawData, 4, 4);
		this.indexType = ByteUtils.byteToInt(rawData, 8, 4);
		this.idxtOffset = ByteUtils.byteToInt(rawData, 20, 4);
		this.indexCount = ByteUtils.byteToInt(rawData, 24, 4);
		this.indexEncoding = ByteUtils.byteToInt(rawData, 28, 4);
		this.indexLanguage = ByteUtils.byteToInt(rawData, 32, 4);
		this.totalIndexCount = ByteUtils.byteToInt(rawData, 36, 4);
		this.ordtOffset = ByteUtils.byteToInt(rawData, 40, 4);
		this.ligtOffset = ByteUtils.byteToInt(rawData, 44, 4);
		this.ordtType = ByteUtils.byteToInt(rawData, 164, 4);
		this.ordtEntryCount = ByteUtils.byteToInt(rawData, 168, 4);
		this.ordt1Offset = ByteUtils.byteToInt(rawData, 172, 4);
		this.ordt2Offset = ByteUtils.byteToInt(rawData, 176, 4);
		this.indexNameOffset = ByteUtils.byteToInt(rawData, 180, 4);
		this.indexNameLength = ByteUtils.byteToInt(rawData, 184, 4);
	}

	@Override
	public String toString() {
		return "MobiIndexMeta:\nheaderLength=" + headerLength + "\nindexType=" + indexType + "\nunknown1=" + unknown1
				+ "\nunknown2=" + unknown2 + "\nidxtOffset=" + idxtOffset + "\nindexCount=" + indexCount
				+ "\nindexEncoding=" + indexEncoding + "\nindexLanguage=" + indexLanguage + "\ntotalIndexCount="
				+ totalIndexCount + "\nordtOffset=" + ordtOffset + "\nligtOffset=" + ligtOffset + "\nligtEntryCount="
				+ ligtEntryCount + "\ncncxEntryCount=" + cncxEntryCount + "\nordtType=" + ordtType
				+ "\nordtEntryCount=" + ordtEntryCount + "\nordt1Offset=" + ordt1Offset + "\nordt2Offset="
				+ ordt2Offset + "\nindexNameOffset=" + indexNameOffset + "\nindexNameLength=" + indexNameLength;
	}

	public MobiIndexMeta() {
		super();
	}

}
