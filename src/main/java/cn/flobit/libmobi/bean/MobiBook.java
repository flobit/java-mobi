package cn.flobit.libmobi.bean;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import cn.flobit.libmobi.bean.MobiPalmDOCHeader.CompressionType;
import cn.flobit.libmobi.utils.ByteUtils;
import cn.flobit.libmobi.utils.MobiBuffer;
import cn.flobit.libmobi.utils.MobiDecompressTool;

public class MobiBook {
	private String filePath;
	String bookName;
	MOBIHeader mobiHeader;
	MobiPalmDatabase palmDatabase;
	MobiPalmDOCHeader palmDOCHeader;
	MobiHuffCdicRecord mobiHuffCdicRecord;
	int kf8Offset;

	public String getBookName() {
		return bookName;
	}

	public MobiPalmDatabase getPalmDatabase() {
		return palmDatabase;
	}

	public MobiPalmDOCHeader getPalmDOCHeader() {
		return palmDOCHeader;
	}

	public MOBIHeader getMobiHeader() {
		return mobiHeader;
	}

	public byte[] getRecordData(int index) throws IOException {
		return getRecordData(index, 1);
	}

	public ByteArrayOutputStream getDecrompressContent() throws IOException {
		return getDecrompressContent(1, palmDOCHeader.getRecordCount());
	}

	public byte[] getValidTextContentData(int recordIndex) throws IOException {
		byte[] data = null;
		data = new MobiBuffer(getRecordData(recordIndex)).getValidData(mobiHeader.getExtraRecordDataFlag());
		return data;
	}

	public ByteArrayOutputStream getDecrompressContent(int start, int count) throws IOException {
		ByteArrayOutputStream oos = new ByteArrayOutputStream();
		if (palmDOCHeader.getCompressionType() == CompressionType.NO_COMPRESSION) {// 处理未压缩
			for (int i = start; i < start + count; i++) {
				oos.write(getValidTextContentData(i));
			}
		} else if (palmDOCHeader.getCompressionType() == CompressionType.PALMDOC_COMPRESSION) {// 处理lz77压缩
			for (int i = start; i < start + count; i++) {
				oos.write(MobiDecompressTool.lz77Decode(getValidTextContentData(i)));
			}
		} else if (palmDOCHeader.getCompressionType() == CompressionType.HUFF_CDIC_COMPRESSION) {// 处理Huff/Cdic
			for (int i = start; i < start + count; i++) {
				MobiDecompressTool.mobiDecompres(getValidTextContentData(i), oos, mobiHuffCdicRecord, 0);
			}
		}

		return oos;
	}

	/**
	 * 从文件中获取PalmdocRecord
	 * 
	 * @param index 起始位置
	 * @param count 读取的长度
	 * @return
	 * @throws IOException
	 */
	public byte[] getRecordData(int index, int count) throws IOException {
		byte data[] = null;
		int offset = palmDatabase.getRecord(index).getRecordDataOffset();
		try (RandomAccessFile raf = new RandomAccessFile(filePath, "r");) {
			long length = ((index + count) >= palmDatabase.recordCount ? raf.length()
					: palmDatabase.getRecord(index + count).getRecordDataOffset()) - offset;
			raf.seek(offset);
			data = new byte[(int) length];
			raf.read(data, 0, data.length);
		}
		return data;
	}

	private void parseRecord0(byte buff[]) throws IOException {
		// 处理PalmDOCHeader
		this.palmDOCHeader = new MobiPalmDOCHeader(buff);
		// 处理MOBIHeader
		this.mobiHeader = new MOBIHeader(buff);
		// KF8
		this.kf8Offset = ByteUtils.byteToInt(mobiHeader.getExthData(121));
		if (kf8Offset != 0 && "BOUNDARY".equals(new String(getRecordData(kf8Offset), 0, 8))) {
			kf8Offset += 1;
			parseRecord0(getRecordData(kf8Offset + 1));
		} else {
			kf8Offset = 0;
		}
		this.bookName = new String(buff, mobiHeader.getFullNameOffset(), mobiHeader.getFullNameLength());
	}

	private void parseHuffCdic() throws IOException {
		// 处理huffCdicRecord
		int huffRecordPdbRecordOffset = mobiHeader.getHuffRecordNumber() + kf8Offset;
		int huffRecordCount = mobiHeader.getHuffRecordCount();
		mobiHuffCdicRecord = new MobiHuffCdicRecord(getRecordData(huffRecordPdbRecordOffset), huffRecordCount - 1);
		for (int i = 1; i < huffRecordCount; i++) {
			mobiHuffCdicRecord.addHuffCdicRecord(getRecordData(huffRecordPdbRecordOffset + i));
		}
	}

	public String getCompresssType() {
		return this.palmDOCHeader.getCompressionType().toString();
	}

	public MobiBook(String filePath) throws IOException {
		super();
		this.filePath = filePath;
		try (InputStream inputStream = new FileInputStream(filePath);) {
			// 处理palmDatabase
			this.palmDatabase = MobiPalmDatabase.parseFromStream(inputStream);
		}
		// 开始处理record0
		parseRecord0(getRecordData(0));
		// 处理HuffCdic(如果文件使用的是HuffCdic)
		if (palmDOCHeader.getCompressionType() == CompressionType.HUFF_CDIC_COMPRESSION) {
			parseHuffCdic();
		}
	}
}
