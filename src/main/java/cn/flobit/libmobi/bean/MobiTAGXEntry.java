package cn.flobit.libmobi.bean;

public class MobiTAGXEntry {
	/** tag */
	byte tag;
	/***/
	byte valueCount;
	/** BitMask */
	byte bitMask;
	/** 如果这个值是0那么其他三个字节也为0 */
	byte controlByte;

	public MobiTAGXEntry(byte rawData[], int offset) {
		super();
		tag = rawData[0 + offset];
		valueCount = rawData[1 + offset];
		bitMask = rawData[2 + offset];
		controlByte = rawData[3 + offset];
	}

	public MobiTAGXEntry() {
		super();
	}

	@Override
	public String toString() {
		return "TAGXEntry [tag=" + tag + ", valueCount=" + valueCount + ", bitMask=" + bitMask + ", controlByte="
				+ controlByte + "]";
	}

}
