package test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import cn.flobit.libmobi.bean.MobiIndexMeta;

public class IndexRecordAnalyze {
	public static void main(String[] args) throws IOException {
		Path path = Paths.get("C:\\Users\\flobit\\Desktop\\jobs_records\\record_395_uid_790");
		byte[] data = Files.readAllBytes(path);
		MobiIndexMeta mobiIndexMeta = new MobiIndexMeta(data);
		// System.out.println(mobiIndexMeta);
		System.out.println(new String(data));

		Paths.get("C:\\Users\\flobit\\Desktop\\jobs_records\\record_854_uid_1710");
		data = Files.readAllBytes(path);
		mobiIndexMeta = new MobiIndexMeta(data);
		System.out.println(data.length);
		System.out.println(mobiIndexMeta);
		System.out.println(new String(data));

	}
}
