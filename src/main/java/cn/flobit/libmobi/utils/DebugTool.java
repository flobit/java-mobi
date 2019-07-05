package cn.flobit.libmobi.utils;

import java.io.IOException;

import cn.flobit.libmobi.bean.MobiBook;

public class DebugTool {
	public static void getTags(MobiBook mobiFile) throws IOException {
		for (int i = 1; i < mobiFile.getPalmDatabase().getRecordCount(); i++) {
			byte[] data = mobiFile.getRecordData(i);
			if (data.length >= 4) {
				if (data[0] >= 'A' && data[0] <= 'Z' && data[1] >= 'A' && data[1] <= 'Z' && data[2] >= 'A'
						&& data[2] <= 'Z' && data[3] >= 'A' && data[3] <= 'Z') {
					System.out.println(i + " : " + new String(data, 0, 4));
				}
			}
		}
	}

	public static void getCompressionType(int compression) {
		String compressionType = null;
		if (compression == 1) {
			compressionType = "No Compression";
		} else if (compression == 2) {
			compressionType = "LZ77 Compression";
		} else {
			compressionType = "HUFF/DIC";
		}
		System.out.println(compressionType);
	}
}
