package cn.flobit.libmobi.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import cn.flobit.libmobi.bean.MobiHuffCdicRecord;

public class MobiDecompressTool {
	static int count = 0;

	public static boolean mobiDecompres(byte[] input, OutputStream oos, MobiHuffCdicRecord mobiHuffCdicRecord, int deep)
			throws IOException {
		boolean decompressed = true;
		// ByteBuffer inputBuffer = ByteBuffer.wrap(ios.toByteArray());
		MobiBuffer inputBuffer = new MobiBuffer(input);
		try {
			int bitCount = 0;
			int codeLength = 0;
			long buffer = 0;
			int bitsLeft = input.length * 8;
			int maxCode = 0;
			int index;
			int cdicIndex;
			int offset;
			int symbolLength;
			int code;
			int t1;
			int iSdecompressed;
			while (decompressed) {
				if (bitCount <= 0) {
					count++;
					bitCount += 32;
					buffer = inputBuffer.fillLong();

					/*
					 * Files.write(Paths.get("C:/Users/flobit/Desktop", "javaout.log"),
					 * String.valueOf(buffer).concat("\r\n").getBytes(), StandardOpenOption.APPEND);
					 */
				}

				code = (int) ((buffer) >> bitCount);
				// 在table1中查找code
				t1 = mobiHuffCdicRecord.table1[(int) (Integer.toUnsignedLong(code) >> 24)];
				codeLength = t1 & 0x1f;
				maxCode = (((t1 >> 8) + 1) << (32 - codeLength)) - 1;
				if (!((t1 & 0x80) != 0)) {
					/* get offset from mincode, maxcode tables */
					while (code < mobiHuffCdicRecord.minCodeTable[codeLength]) {
						codeLength++;
					}
					maxCode = mobiHuffCdicRecord.maxCodeTable[codeLength];
				}
				bitCount -= codeLength;
				bitsLeft -= codeLength;
				if (bitsLeft < 0) {
					break;
				}
				/* get index for symbol offset */
				index = (maxCode - code) >> (32 - codeLength);
				/* check which part of cdic to use */
				/* 计算使用哪个字典 */
				cdicIndex = (index >> mobiHuffCdicRecord.getCodeLength());
				/* get offset */
				/* 计算偏移量 */
				offset = mobiHuffCdicRecord.symbolOffsets[index];
				// int data1 = 0x00ff & mobiHuffCdicRecord.symbols[cdicIndex][offset];
				// int data2 = 0x00ff & mobiHuffCdicRecord.symbols[cdicIndex][offset + 1];
				symbolLength = (0x00ff & mobiHuffCdicRecord.symbols[cdicIndex][offset]) << 8
						| (0x00ff & mobiHuffCdicRecord.symbols[cdicIndex][offset + 1]);
				/* 1st bit is is_decompressed flag */
				iSdecompressed = symbolLength >> 15;
				symbolLength &= 0x7fff;
				if (iSdecompressed != 0) {
					oos.write(mobiHuffCdicRecord.symbols[cdicIndex], offset + 2, symbolLength);
					decompressed = true;
				} else {
					// ByteArrayOutputStream baos = new ByteArrayOutputStream(symbolLength);
					// baos.write(mobiHuffCdicRecord.symbols[cdicIndex], offset + 2, symbolLength);
					byte iinput[] = new byte[symbolLength];
					System.arraycopy(mobiHuffCdicRecord.symbols[cdicIndex], offset + 2, iinput, 0, symbolLength);
					decompressed = mobiDecompres(iinput, oos, mobiHuffCdicRecord, deep + 1);
				}
			}
		} catch (Exception e) {
			System.err.println(inputBuffer.offset());
			e.printStackTrace();
		}
		return decompressed;
	}

	public static OutputStream os;
	static {
		try {
			os = new FileOutputStream("C:/Users/flobit/Desktop/javalz77.out");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void logOutput(String log) {
		try {
			os.write(log.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int mobiLz77Decompress(byte[] input, byte[] output, int outputIndex) throws IOException {
		// byte[] output = new byte[input.length * 8];
		// int outputIndex = 0;
		MobiBuffer buffer = new MobiBuffer(input);
		int buff = 0, next = 0, distance = 0, length = 0;
		while (buffer.offset() < 100) {
			buff = buffer.getByte();
			// System.out.printf("standard output:%d offset:%d\n", buff, buffer.offset);
			logOutput(String.format("standard output:%d offset:%d\n", buff, buffer.offset));
			if (buff >= 0xc0) {// byte pair: space + char
				output[outputIndex++] = ' ';
				output[outputIndex++] = (byte) (buff ^ 0x80);
			} else if (buff >= 0x80) {
				/* length, distance pair */
				/* 0x8000 + (distance << 3) + ((length-3) & 0x07) */
				next = buffer.getByte();
				distance = ((((buff << 8) | (next)) >> 3) & 0x7ff);
				length = (next & 0x7) + 3;
				// System.out.printf("distance output:%d %d %d %d\n", buff, next, distance,
				// length);
				logOutput(String.format("distance output:%d %d %d %d\n", buff, next, distance, length));
				for (int j = 0; j < length; j++) {
					output[outputIndex++] = output[outputIndex - distance];
				}
			} else if (buff >= 0x09) {
				/* single char, not modified */
				output[outputIndex++] = (byte) buff;
			} else if (buff >= 0x01) {
				/* val chars not modified */
				// buffer.writeTo(oos, buff);
				buffer.getBytes(output, outputIndex, buff);
				outputIndex += buff;
			} else {/* char '\0', not modified */
				output[outputIndex++] = (byte) buff;
			}
		}
		return outputIndex;
		// oos.write(output, 0, outputIndex);
	}
	public static byte[] lz77Decode(byte[] input) {
		byte[] out = new byte[input.length * 8];
		int i = 0, o = 0;
		while (i < input.length) {
			int c = input[i++] & 0x00FF;
			if (c >= 0x01 && c <= 0x08) {
				for (int j = 0; j < c && i + j < input.length; j++) {
					out[o++] = input[i + j];
				}
				i += c;
			} else if (c <= 0x7f) {
				out[o++] = (byte) c;
			} else if (c >= 0xC0) {
				out[o++] = ' ';
				out[o++] = (byte) (c ^ 0x80);
			} else if (c <= 0xbf) {
				if (i < input.length) {
					c = c << 8 | input[i++] & 0xFF;
					int length = (c & 0x0007) + 3;
					int location = (c >> 3) & 0x7FF;

					if (location > 0 && location <= o) {
						for (int j = 0; j < length; j++) {
							int idx = o - location;
							out[o++] = out[idx];
						}
					} else {
						// invalid idx
					}
				}
			} else {
				// unknown input
			}
		}
		byte[] result = new byte[o];
		System.arraycopy(out, 0, result, 0, o);
		return result;
	}
}
