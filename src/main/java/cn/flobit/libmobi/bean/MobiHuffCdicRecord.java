package cn.flobit.libmobi.bean;

import java.nio.ByteBuffer;

public class MobiHuffCdicRecord {
	int indexCount;
	int indexRead;
	int codeLength;
	public int table1[];
	public int minCodeTable[];
	public int maxCodeTable[];
	public int symbolOffsets[];
	public byte symbols[][];
	int cdicCount = 0;

	public int getCodeLength() {
		return codeLength;
	}

	public void setCodeLength(int codeLength) {
		this.codeLength = codeLength;
	}

	public MobiHuffCdicRecord(byte[] huffData,int cdicRecordCount) {
		super();
		ByteBuffer buffer = ByteBuffer.wrap(huffData);
		// 处理HUFF Record
		byte buff[] = new byte[4];
		buffer.get(buff);
		//System.out.println(new String(buff));
		buffer.getInt();
		int offset1 = buffer.getInt();
		int offset2 = buffer.getInt();
		table1 = new int[256];
		minCodeTable = new int[33];
		maxCodeTable = new int[33];
		symbols = new byte[cdicRecordCount][];
		buffer.position(offset1);
		/* read 256 indices from data1 big-endian */
		for (int i = 0; i < 256; i++) {
			table1[i] = buffer.getInt();
		}
		buffer.position(offset2);
		/* read 32 mincode-maxcode pairs from data2 big-endian */
		minCodeTable[0] = 0;
		maxCodeTable[0] = 0xFFFFFFFF;
		for (int i = 1; i < 33; i++) {
			minCodeTable[i] = buffer.getInt() << (32 - i);
			maxCodeTable[i] = ((buffer.getInt() + 1) << (32 - i)) - 1;
		}
	}

	public void addHuffCdicRecord(byte[] cdicData) {
		ByteBuffer buffer = ByteBuffer.wrap(cdicData);
		byte buff[] = new byte[4];
		buffer.get(buff);
		//System.out.println(new String(buff));
		int headerLength = buffer.getInt();
		this.indexCount = buffer.getInt();
		this.codeLength = buffer.getInt();
		if (this.symbolOffsets == null)
			this.symbolOffsets = new int[indexCount];
		indexCount -= indexRead;
		/* limit number of records read to code_length bits */
		if (indexCount >> codeLength != 0) {
			indexCount = (1 << codeLength);
		}
		for (int i = 0; i < indexCount; i++) {
			this.symbolOffsets[indexRead++] = Short.toUnsignedInt(buffer.getShort());
		}

		buffer.position(headerLength);
		int symbolLength = cdicData.length - headerLength;
		this.symbols[cdicCount] = new byte[symbolLength];
		buffer.get(this.symbols[cdicCount], 0, symbolLength);
		cdicCount += 1;
	}
}
