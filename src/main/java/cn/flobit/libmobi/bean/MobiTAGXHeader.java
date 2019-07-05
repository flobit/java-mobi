package cn.flobit.libmobi.bean;

import java.util.ArrayList;
import java.util.List;

import cn.flobit.libmobi.utils.ByteUtils;

public class MobiTAGXHeader {
	/** 标识符 TAGX */
	static final String identifier = "TAGX";
	/** offset:4,TAGX记录的长度 */
	int headerLength;
	/** offset:8,TAGXEntry数量 */
	int entryCount;

	List<MobiTAGXEntry> entries = new ArrayList<>(10);

	public MobiTAGXHeader(byte rawData[], int offset) {
		super();
		this.headerLength = ByteUtils.byteToInt(rawData, 4 + offset, 4);
		this.entryCount = ByteUtils.byteToInt(rawData, 8 + offset, 4);
		for (int i = 0; i < this.entryCount; i++) {
			this.entries.add(new MobiTAGXEntry(rawData, offset + 12 + 4 * i));
		}
	}

	public MobiTAGXHeader() {
		super();
	}

	@Override
	public String toString() {
		return "TAGXHeader [headerLength=" + headerLength + ", entryCount=" + entryCount + ", entries=" + entries + "]";
	}
}
