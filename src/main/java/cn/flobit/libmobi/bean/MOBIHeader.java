package cn.flobit.libmobi.bean;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cn.flobit.libmobi.utils.ByteUtils;

public class MOBIHeader {
	/** the characters M O B I */
	String identifier = "MOBI";
	/**
	 * <p>
	 * 偏移 20
	 * </p>
	 * MOBI Header的长度
	 */
	int headerLength;
	/**
	 * <p>
	 * 偏移 24
	 * </p>
	 * 文档类型
	 */
	int mobiType;
	/**
	 * <p>
	 * 偏移 28
	 * </p>
	 * 1252 = CP1252 (WinLatin1); 65001 = UTF-8
	 */
	int textEncoding;
	/**
	 * <p>
	 * 偏移 32
	 * </p>
	 * Some kind of unique ID number (random?)
	 */
	int uid;
	/**
	 * <p>
	 * 偏移 36
	 * </p>
	 * mobipocket版本号
	 */
	int fileVersion;
	/**
	 * <p>
	 * 偏移 40
	 * </p>
	 * orthIndex的PDB record偏移
	 */
	int orthIndexRecordNumber;
	/**
	 * <p>
	 * 偏移 44
	 * </p>
	 * inflIndex的PDB Record偏移.
	 */
	int inflIndexRecordNumber;
	/**
	 * <p>
	 * 偏移 48
	 * </p>
	 * 0xFFFFFFFF if index is not available.
	 */
	int namesIndexRecordNumber;
	/**
	 * <p>
	 * 偏移 52
	 * </p>
	 * 0xFFFFFFFF if index is not available.
	 */
	int keysIndexRecordNumber;
	/**
	 * <p>
	 * 偏移 56
	 * </p>
	 * Section number of extra 0 meta index. 0xFFFFFFFF if index is not available.
	 */
	int extra0IndexRecordNumber;
	/**
	 * <p>
	 * 偏移 60
	 * </p>
	 * Section number of extra 1 meta index. 0xFFFFFFFF if index is not available.
	 */
	int extra1IndexRecordNumber;
	/**
	 * <p>
	 * 偏移 64
	 * </p>
	 * Section number of extra 2 meta index. 0xFFFFFFFF if index is not available.
	 */
	int extra2IndexRecordNumber;
	/**
	 * <p>
	 * 偏移 68
	 * </p>
	 * Section number of extra 3 meta index. 0xFFFFFFFF if index is not available.
	 */
	int extra3IndexRecordNumber;
	/**
	 * <p>
	 * 偏移 72
	 * </p>
	 * Section number of extra 4 meta index. 0xFFFFFFFF if index is not available.
	 */
	int extra4IndexRecordNumber;
	/**
	 * <p>
	 * 偏移 76
	 * </p>
	 * Section number of extra 5 meta index. 0xFFFFFFFF if index is not available.
	 */
	int extra5IndexRecordNumber;
	/**
	 * <p>
	 * 偏移 80
	 * </p>
	 * 第一个不是文本的记录号
	 */
	int firstNoTextRecordNumber;
	/**
	 * <p>
	 * 偏移 84
	 * </p>
	 * 书名在第一个Record中的字节偏移量
	 */
	int fullNameOffset;
	/**
	 * <p>
	 * 偏移 88
	 * </p>
	 * 书名的字节数
	 */
	int fullNameLength;
	/**
	 * <p>
	 * 偏移 92
	 * </p>
	 * 数的区域码. Low byte is main language 09= English, next byte is dialect, 08 =
	 * British, 04 = US. Thus US English is 1033, UK English is 2057.
	 */
	int locale;
	/**
	 * <p>
	 * 偏移 96
	 * </p>
	 * 字典输入语言
	 */
	int inputLanguage;
	/**
	 * <p>
	 * 偏移 100
	 * </p>
	 * 字典输出语言
	 */
	int outputLanguage;
	/**
	 * <p>
	 * 偏移 104
	 * </p>
	 * 要支持本文件需要的最低mobipocket版本
	 */
	int minSupportVersion;
	/**
	 * <p>
	 * 偏移 108
	 * </p>
	 * 第一张图片的图片记录号
	 */
	int firstImageRecordNumber;
	/**
	 * <p>
	 * 偏移 112
	 * </p>
	 */
	int huffRecordNumber;
	/**
	 * <p>
	 * 偏移 116
	 * </p>
	 */
	int huffRecordCount;
	/**
	 * <p>
	 * 偏移 120
	 * </p>
	 */
	int huffmanTableRecordNumber;
	/**
	 * <p>
	 * 偏移 124
	 * </p>
	 */
	int huffmanTableLength;
	/**
	 * <p>
	 * 偏移 128
	 * </p>
	 * 如果exthFlag=0x40则说明在mobiheader之后有EXTH Header
	 */
	int exthFlag;
	/**
	 * <p>
	 * 偏移 132
	 * </p>
	 * 32 unknown bytes, if MOBI is long enough
	 */
	String unknown1;
	/**
	 * <p>
	 * 偏移 164
	 * </p>
	 */
	int unknown2 = 0xffffffff;
	/**
	 * <p>
	 * 偏移 168
	 * </p>
	 * Offset to DRM key info in DRMed files. 0xFFFFFFFF if no DRM
	 */
	int drmOffset;
	/**
	 * <p>
	 * 偏移 172
	 * </p>
	 * Number of entries in DRM info. 0xFFFFFFFF if no DRM
	 */
	int drmCount;
	/**
	 * <p>
	 * 偏移 176
	 * </p>
	 * Number of bytes in DRM info.
	 */
	int drmSize;
	/**
	 * <p>
	 * 偏移 180
	 * </p>
	 * Some flags concerning the DRM info.
	 */
	int drmFlag;
	/**
	 * <p>
	 * 偏移 184
	 * </p>
	 * Bytes to the end of the MOBI header, including the following if the header
	 * length >= 228 (244 from start of record). Use 0x0000000000000000.
	 */
	long unknown3 = 0x0000000000000000;
	int unknown4 = 0x00000000;
	/**
	 * <p>
	 * 偏移 192,2bit
	 * </p>
	 * number Number of first text record. Normally 1.
	 */
	int firstTextRecordNumber;
	/**
	 * <p>
	 * 偏移 192,2bit
	 * </p>
	 * Number of last image record or number of last text record if it contains no
	 * images. Includes Image, DATP, HUFF, DRM.
	 */
	int lastTextRecordNumber;
	/**
	 * <p>
	 * 偏移 192,4bit
	 * </p>
	 * 如果是KF8标准则是FDST
	 */
	int fdstRecordNumber;

	/**
	 * <p>
	 * 偏移 196
	 * </p>
	 * Use 0x00000001.
	 */
	int unknown5 = 0x00000001;

	/**
	 * <p>
	 * 偏移 196
	 * </p>
	 * 如果是KF8标准则为FDST Section Count
	 */
	int fdstSectionCount;
	/**
	 * <p>
	 * 偏移 200
	 * </p>
	 * FCIS记录号
	 */
	int fcisRecordNumber;
	/**
	 * <p>
	 * 偏移 204
	 * </p>
	 * FCIS记录数
	 */
	int fcisRecordCount;
	/**
	 * <p>
	 * 偏移 208
	 * </p>
	 * FLIS记录号
	 */
	int flisRecordNumber;
	/**
	 * <p>
	 * 偏移 212
	 * </p>
	 * FLIS记录数
	 */
	int flisRecordCount;

	/**
	 * <p>
	 * 偏移 216
	 * </p>
	 * Use 0x00000001.
	 */
	int unknown6 = 0x00000001;
	/**
	 * <p>
	 * 偏移 220
	 * </p>
	 */
	int unknown7 = 0x00000000;
	/**
	 * <p>
	 * 偏移 224
	 * </p>
	 * SRCS PDB Record记录号
	 */
	int srcsRecordNumber;
	/**
	 * <p>
	 * 偏移 228
	 * </p>
	 */
	int srcsCount;

	/**
	 * <p>
	 * 偏移 232
	 * </p>
	 * Use 0x00000000.
	 */
	int unknown8 = 0x00000000;
	/**
	 * <p>
	 * 偏移 236
	 * </p>
	 * Use 0xFFFFFFFF.
	 */
	int unknown9 = 0xffffffff;
	/**
	 * <p>
	 * 偏移 240,2 bits
	 * </p>
	 * Use 0xFFFFFFFF.
	 */
	int unknown10 = 0x0000;
	/**
	 * <p>
	 * 偏移 242,2bit
	 * </p>
	 * A set of binary flags, some of which indicate extra data at the end of each
	 * text block. This only seems to be valid for Mobipocket format version 5 and 6
	 * (and higher?), when the header length is 228 (0xE4) or 232 (0xE8).
	 * 
	 * <pre>
	 *bit 1 (0x1): <extra multibyte bytes><size>
	 *bit 2 (0x2): <TBS indexing description of this HTML record><size>
	 *bit 3 (0x4): <uncrossable breaks><size>
	 * </pre>
	 * 
	 * Setting bit 2 (0x2) disables <guide><reference type="start"> functionality.
	 */
	int extraRecordDataFlag;
	/**
	 * <p>
	 * 偏移 244
	 * </p>
	 * (If not 0xFFFFFFFF)The record number of the first INDX record created from an
	 * ncx file.
	 */
	int ncxRecordNumber;
	/**
	 * <p>
	 * 偏移 248
	 * </p>
	 * 0xFFFFFFFF In new MOBI file, the MOBI header length is 256, skip this to EXTH
	 * header.
	 */
	int unknown11 = 0xffffffff;
	/**
	 * <p>
	 * 偏移 248 KF8
	 * </p>
	 */
	int fragmentRecordNumber;
	/**
	 * <p>
	 * 偏移 252
	 * </p>
	 * 0xFFFFFFFF In new MOBI file, the MOBI header length is 256, skip this to EXTH
	 * header.
	 */
	int unknown12 = 0xffffffff;
	/**
	 * <p>
	 * 偏移 252
	 * </p>
	 * KF8 skeleton PDB Record记录号
	 */

	int skeletonIndex;

	/***
	 * <p>
	 * 偏移 256
	 * </p>
	 */
	int datpRecordNumber;
	/***
	 * <p>
	 * 偏移 260
	 * </p>
	 */
	int unknown13;
	/**
	 * <p>
	 * offset=260
	 * </p>
	 * KF8 ,guide 的PDB Record记录号
	 */
	int guideRecordNumber;
	/***
	 * <p>
	 * 偏移 264
	 * </p>
	 */
	int unknown14;
	/***
	 * <p>
	 * 偏移 268
	 * </p>
	 */
	int unknown15;
	/***
	 * <p>
	 * 偏移 272
	 * </p>
	 */
	int unknown16;
	/***
	 * <p>
	 * 偏移 276
	 * </p>
	 */
	int unknown17;

	private String bookName;

	Map<Integer, byte[]> exthHeader;

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

	public int getMobiType() {
		return mobiType;
	}

	public void setMobiType(int mobiType) {
		this.mobiType = mobiType;
	}

	public int getTextEncoding() {
		return textEncoding;
	}

	public void setTextEncoding(int textEncoding) {
		this.textEncoding = textEncoding;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getFileVersion() {
		return fileVersion;
	}

	public void setFileVersion(int fileVersion) {
		this.fileVersion = fileVersion;
	}

	public int getOrthIndexRecordNumber() {
		return orthIndexRecordNumber;
	}

	public void setOrthIndexRecordNumber(int orthIndexRecordNumber) {
		this.orthIndexRecordNumber = orthIndexRecordNumber;
	}

	public int getInflIndexRecordNumber() {
		return inflIndexRecordNumber;
	}

	public void setInflIndexRecordNumber(int inflIndexRecordNumber) {
		this.inflIndexRecordNumber = inflIndexRecordNumber;
	}

	public int getNamesIndexRecordNumber() {
		return namesIndexRecordNumber;
	}

	public void setNamesIndexRecordNumber(int namesIndexRecordNumber) {
		this.namesIndexRecordNumber = namesIndexRecordNumber;
	}

	public int getKeysIndexRecordNumber() {
		return keysIndexRecordNumber;
	}

	public void setKeysIndexRecordNumber(int keysIndexRecordNumber) {
		this.keysIndexRecordNumber = keysIndexRecordNumber;
	}

	public int getExtra0IndexRecordNumber() {
		return extra0IndexRecordNumber;
	}

	public void setExtra0IndexRecordNumber(int extra0IndexRecordNumber) {
		this.extra0IndexRecordNumber = extra0IndexRecordNumber;
	}

	public int getExtra1IndexRecordNumber() {
		return extra1IndexRecordNumber;
	}

	public void setExtra1IndexRecordNumber(int extra1IndexRecordNumber) {
		this.extra1IndexRecordNumber = extra1IndexRecordNumber;
	}

	public int getExtra2IndexRecordNumber() {
		return extra2IndexRecordNumber;
	}

	public void setExtra2IndexRecordNumber(int extra2IndexRecordNumber) {
		this.extra2IndexRecordNumber = extra2IndexRecordNumber;
	}

	public int getExtra3IndexRecordNumber() {
		return extra3IndexRecordNumber;
	}

	public void setExtra3IndexRecordNumber(int extra3IndexRecordNumber) {
		this.extra3IndexRecordNumber = extra3IndexRecordNumber;
	}

	public int getExtra4IndexRecordNumber() {
		return extra4IndexRecordNumber;
	}

	public void setExtra4IndexRecordNumber(int extra4IndexRecordNumber) {
		this.extra4IndexRecordNumber = extra4IndexRecordNumber;
	}

	public int getExtra5IndexRecordNumber() {
		return extra5IndexRecordNumber;
	}

	public void setExtra5IndexRecordNumber(int extra5IndexRecordNumber) {
		this.extra5IndexRecordNumber = extra5IndexRecordNumber;
	}

	public int getFirstNoTextRecordNumber() {
		return firstNoTextRecordNumber;
	}

	public void setFirstNoTextRecordNumber(int firstNoTextRecordNumber) {
		this.firstNoTextRecordNumber = firstNoTextRecordNumber;
	}

	public int getFullNameOffset() {
		return fullNameOffset;
	}

	public void setFullNameOffset(int fullNameOffset) {
		this.fullNameOffset = fullNameOffset;
	}

	public int getFullNameLength() {
		return fullNameLength;
	}

	public void setFullNameLength(int fullNameLength) {
		this.fullNameLength = fullNameLength;
	}

	public int getLocale() {
		return locale;
	}

	public void setLocale(int locale) {
		this.locale = locale;
	}

	public int getInputLanguage() {
		return inputLanguage;
	}

	public void setInputLanguage(int inputLanguage) {
		this.inputLanguage = inputLanguage;
	}

	public int getOutputLanguage() {
		return outputLanguage;
	}

	public void setOutputLanguage(int outputLanguage) {
		this.outputLanguage = outputLanguage;
	}

	public int getMinSupportVersion() {
		return minSupportVersion;
	}

	public void setMinSupportVersion(int minSupportVersion) {
		this.minSupportVersion = minSupportVersion;
	}

	public int getFirstImageRecordNumber() {
		return firstImageRecordNumber;
	}

	public void setFirstImageRecordNumber(int firstImageRecordNumber) {
		this.firstImageRecordNumber = firstImageRecordNumber;
	}

	public int getHuffRecordNumber() {
		return huffRecordNumber;
	}

	public void setHuffRecordNumber(int huffmanRecordNumber) {
		this.huffRecordNumber = huffmanRecordNumber;
	}

	public int getHuffRecordCount() {
		return huffRecordCount;
	}

	public void setHuffmanRecordCount(int huffmanRecordCount) {
		this.huffRecordCount = huffmanRecordCount;
	}

	public int getHuffmanTableRecordNumber() {
		return huffmanTableRecordNumber;
	}

	public void setHuffmanTableRecordNumber(int huffmanTableRecordNumber) {
		this.huffmanTableRecordNumber = huffmanTableRecordNumber;
	}

	public int getHuffmanTableLength() {
		return huffmanTableLength;
	}

	public void setHuffmanTableLength(int huffmanTableLength) {
		this.huffmanTableLength = huffmanTableLength;
	}

	public int getExthFlag() {
		return exthFlag;
	}

	public void setExthFlag(int exthFlag) {
		this.exthFlag = exthFlag;
	}

	public int getDrmOffset() {
		return drmOffset;
	}

	public void setDrmOffset(int drmOffset) {
		this.drmOffset = drmOffset;
	}

	public int getDrmCount() {
		return drmCount;
	}

	public void setDrmCount(int drmCount) {
		this.drmCount = drmCount;
	}

	public int getDrmSize() {
		return drmSize;
	}

	public void setDrmSize(int drmSize) {
		this.drmSize = drmSize;
	}

	public int getDrmFlag() {
		return drmFlag;
	}

	public void setDrmFlag(int drmFlag) {
		this.drmFlag = drmFlag;
	}

	public int getFirstTextRecordNumber() {
		return firstTextRecordNumber;
	}

	public void setFirstTextRecordNumber(int firstTextRecordNumber) {
		this.firstTextRecordNumber = firstTextRecordNumber;
	}

	public int getLastTextRecordNumber() {
		return lastTextRecordNumber;
	}

	public void setLastTextRecordNumber(int lastTextRecordNumber) {
		this.lastTextRecordNumber = lastTextRecordNumber;
	}

	public int getFdstRecordNumber() {
		return fdstRecordNumber;
	}

	public void setFdstRecordNumber(int fdstRecordNumber) {
		this.fdstRecordNumber = fdstRecordNumber;
	}

	public int getFdstSectionCount() {
		return fdstSectionCount;
	}

	public void setFdstSectionCount(int fdstSectionCount) {
		this.fdstSectionCount = fdstSectionCount;
	}

	public int getFcisRecordNumber() {
		return fcisRecordNumber;
	}

	public void setFcisRecordNumber(int fcisRecordNumber) {
		this.fcisRecordNumber = fcisRecordNumber;
	}

	public int getFcisRecordCount() {
		return fcisRecordCount;
	}

	public void setFcisRecordCount(int fcisRecordCount) {
		this.fcisRecordCount = fcisRecordCount;
	}

	public int getFlisRecordNumber() {
		return flisRecordNumber;
	}

	public void setFlisRecordNumber(int flisRecordNumber) {
		this.flisRecordNumber = flisRecordNumber;
	}

	public int getFlisRecordCount() {
		return flisRecordCount;
	}

	public void setFlisRecordCount(int flisRecordCount) {
		this.flisRecordCount = flisRecordCount;
	}

	public int getSrcsRecordNumber() {
		return srcsRecordNumber;
	}

	public void setSrcsRecordNumber(int srcsRecordNumber) {
		this.srcsRecordNumber = srcsRecordNumber;
	}

	public int getSrcsCount() {
		return srcsCount;
	}

	public void setSrcsCount(int srcsCount) {
		this.srcsCount = srcsCount;
	}

	public int getExtraRecordDataFlag() {
		return extraRecordDataFlag;
	}

	public void setExtraRecordDataFlag(int extraRecordDataFlag) {
		this.extraRecordDataFlag = extraRecordDataFlag;
	}

	public int getNcxRecordNumber() {
		return ncxRecordNumber;
	}

	public void setNcxRecordNumber(int ncxRecordNumber) {
		this.ncxRecordNumber = ncxRecordNumber;
	}

	public int getFragmentRecordNumber() {
		return fragmentRecordNumber;
	}

	public void setFragmentRecordNumber(int fragmentRecordNumber) {
		this.fragmentRecordNumber = fragmentRecordNumber;
	}

	public int getSkeletonIndex() {
		return skeletonIndex;
	}

	public void setSkeletonIndex(int skeletonIndex) {
		this.skeletonIndex = skeletonIndex;
	}

	public int getDatpRecordNumber() {
		return datpRecordNumber;
	}

	public void setDatpRecordNumber(int datpRecordNumber) {
		this.datpRecordNumber = datpRecordNumber;
	}

	public int getGuideRecordNumber() {
		return guideRecordNumber;
	}

	public void setGuideRecordNumber(int guideRecordNumber) {
		this.guideRecordNumber = guideRecordNumber;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public byte[] getExthData(Integer key) {
		return this.exthHeader == null ? null : this.exthHeader.get(key);
	}

	@Override
	public String toString() {
		return "MOBIHeader [identifier=" + identifier + "\nheaderLength=" + headerLength + "\nmobiType=" + mobiType
				+ "\ntextEncoding=" + textEncoding + "\nuid=" + uid + "\nfileVersion=" + fileVersion
				+ "\northIndexRecordNumber=" + orthIndexRecordNumber + "\ninflIndexRecordNumber="
				+ inflIndexRecordNumber + "\nnamesIndexRecordNumber=" + namesIndexRecordNumber
				+ "\nkeysIndexRecordNumber=" + keysIndexRecordNumber + "\nextra0IndexRecordNumber="
				+ extra0IndexRecordNumber + "\nextra1IndexRecordNumber=" + extra1IndexRecordNumber
				+ "\nextra2IndexRecordNumber=" + extra2IndexRecordNumber + "\nextra3IndexRecordNumber="
				+ extra3IndexRecordNumber + "\nextra4IndexRecordNumber=" + extra4IndexRecordNumber
				+ "\nextra5IndexRecordNumber=" + extra5IndexRecordNumber + "\nfirstNoTextRecordNumber="
				+ firstNoTextRecordNumber + "\nfullNameOffset=" + fullNameOffset + "\nfullNameLength=" + fullNameLength
				+ "\nlocale=" + locale + "\ninputLanguage=" + inputLanguage + "\noutputLanguage=" + outputLanguage
				+ "\nminSupportVersion=" + minSupportVersion + "\nfirstImageRecordNumber=" + firstImageRecordNumber
				+ "\nhuffmanRecordNumber=" + huffRecordNumber + "\nhuffmanRecordCount=" + huffRecordCount
				+ "\nhuffmanTableRecordNumber=" + huffmanTableRecordNumber + "\nhuffmanTableLength="
				+ huffmanTableLength + "\nexthFlag=" + exthFlag + "\ndrmOffset=" + drmOffset + "\ndrmCount=" + drmCount
				+ "\ndrmSize=" + drmSize + "\ndrmFlag=" + drmFlag + "\nfirstTextRecordNumber=" + firstTextRecordNumber
				+ "\nlastTextRecordNumber=" + lastTextRecordNumber + "\nfdstRecordNumber=" + fdstRecordNumber
				+ "\nfdstSectionCount=" + fdstSectionCount + "\nfcisRecordNumber=" + fcisRecordNumber
				+ "\nfcisRecordCount=" + fcisRecordCount + "\nflisRecordNumber=" + flisRecordNumber
				+ "\nflisRecordCount=" + flisRecordCount + "\nsrcsRecordNumber=" + srcsRecordNumber + "\nsrcsCount="
				+ srcsCount + "\nextraRecordDataFlag=" + extraRecordDataFlag + "\nncxRecordNumber=" + ncxRecordNumber
				+ "\nfragmentRecordNumber=" + fragmentRecordNumber + "\nskeletonIndex=" + skeletonIndex
				+ "\ndatpRecordNumber=" + datpRecordNumber + "\nguideRecordNumber=" + guideRecordNumber
				+ "\nexthHeader=" + exthHeader + "]";
	}

	/**
	 * 应该传入第一个record的数据
	 * 
	 * @param rawData 第一个record中的所有数据
	 * @throws IOException
	 */
	public MOBIHeader(byte[] rawData) throws IOException {
		super();
		this.headerLength = ByteUtils.byteToInt(rawData, 20, 4);
		this.mobiType = ByteUtils.byteToInt(rawData, 24, 4);
		this.textEncoding = ByteUtils.byteToInt(rawData, 28, 4);
		this.fileVersion = ByteUtils.byteToInt(rawData, 36, 4);
		this.orthIndexRecordNumber = ByteUtils.byteToInt(rawData, 40, 4);
		this.inflIndexRecordNumber = ByteUtils.byteToInt(rawData, 44, 4);
		this.firstNoTextRecordNumber = ByteUtils.byteToInt(rawData, 80, 4);
		this.fullNameOffset = ByteUtils.byteToInt(rawData, 84, 4);
		this.fullNameLength = ByteUtils.byteToInt(rawData, 88, 4);
		this.locale = ByteUtils.byteToInt(rawData, 92, 4);
		this.exthFlag = ByteUtils.byteToInt(rawData, 128, 4);
		this.firstTextRecordNumber = ByteUtils.byteToInt(rawData, 192, 2);
		this.lastTextRecordNumber = ByteUtils.byteToInt(rawData, 194, 2);
		this.extraRecordDataFlag = ByteUtils.byteToInt(rawData, 242, 2);
		this.ncxRecordNumber = ByteUtils.byteToInt(rawData, 244, 4);
		this.firstImageRecordNumber = ByteUtils.byteToInt(rawData, 108, 4);
		this.fdstRecordNumber = ByteUtils.byteToInt(rawData, 192, 4);
		this.fdstSectionCount = ByteUtils.byteToInt(rawData, 196, 4);
		this.huffRecordNumber = ByteUtils.byteToInt(rawData, 112, 4);
		this.huffRecordCount = ByteUtils.byteToInt(rawData, 116, 4);
		// 判断是否有EXTHHeader
		if ((this.exthFlag & 0x40) == 0x40) {
			this.exthHeader = new HashMap<>();
			int exthRecordCount = (ByteUtils.byteToInt(rawData, this.headerLength + 16 + 8, 4));
			byte[] buff = new byte[8];
			byte[] recordData;
			int recordType, recordLength;
			// 开始处理EXTHRecord
			try (ByteArrayInputStream bis = new ByteArrayInputStream(rawData, this.headerLength + 28,
					rawData.length - this.headerLength - 28);) {
				for (int i = 0; i < exthRecordCount; i++) {
					// 获取8个字节的数据
					bis.read(buff, 0, 8);
					// 获取EXTHRecord的类型
					recordType = ByteUtils.byteToInt(buff, 0, 4);
					// 获取EXTHRecord的长度
					recordLength = ByteUtils.byteToInt(buff, 4, 4);
					recordData = new byte[recordLength - 8];
					bis.read(recordData, 0, recordData.length);
					this.exthHeader.put(recordType, recordData);
				}
			}
		}
	}
}
