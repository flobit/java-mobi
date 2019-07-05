package test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import cn.flobit.libmobi.bean.MOBIHeader;

public class Record0Analyze {
	public static void main(String[] args) throws IOException {
		Path path = Paths.get("C:\\Users\\flobit\\Desktop\\jobs_records\\record_483_uid_968");
		byte[] data = Files.readAllBytes(path);
		MOBIHeader mobiHeader = new MOBIHeader(data);
		
	}
}
