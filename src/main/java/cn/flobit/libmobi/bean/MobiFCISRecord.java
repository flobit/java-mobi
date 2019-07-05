package cn.flobit.libmobi.bean;

import java.util.ArrayList;
import java.util.List;

import cn.flobit.libmobi.utils.ByteUtils;

public class MobiFCISRecord {
	String identifier = "FCIS";
	byte[] data = new byte[44];
	int dataLength = 0;
	List<Byte> datas = new ArrayList<>();

	public MobiFCISRecord(int textLength) {
		super();
		addBytes(identifier.getBytes());
		addBytes(ByteUtils.intToBytes(20, 4));
		addBytes(ByteUtils.intToBytes(16, 4));
		addBytes(ByteUtils.intToBytes(1, 4));
		addBytes(ByteUtils.intToBytes(0, 4));
		addBytes(ByteUtils.intToBytes(textLength, 4));
		addBytes(ByteUtils.intToBytes(0, 4));
		addBytes(ByteUtils.intToBytes(32, 4));
		addBytes(ByteUtils.intToBytes(8, 4));
		addBytes(ByteUtils.intToBytes(1, 2));
		addBytes(ByteUtils.intToBytes(1, 2));
		addBytes(ByteUtils.intToBytes(0, 4));
	}

	private void addBytes(byte[] buff) {
		for (int i = 0; i < buff.length && dataLength < data.length; i++) {
			data[dataLength++] = buff[i];
		}
	}
}
