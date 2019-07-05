package test;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

import cn.flobit.libmobi.bean.MobiBook;

public class Test {
	public static void main(String[] args) throws Exception {
		URI uri = Thread.currentThread().getContextClassLoader().getResource("").toURI();
		String basePath = "D:\\Share\\Book\\传记";
		String suffix = ".azw3";
		boolean baseTest = true;
		//uri = Paths.get(basePath).toUri();
		Files.list(Paths.get(uri)).filter((file) -> {
			String name = file.toFile().getName();
			return name.endsWith(suffix) && !Files.isDirectory(file);
		}).forEach((file) -> {
			try {
				MobiBook book = new MobiBook(file.toFile().getAbsolutePath());
				if (!baseTest) {
					ByteArrayOutputStream baos = book.getDecrompressContent();
				}else {
					System.out.println(String.format("%s --- %s", file,book.getBookName()));
				}
			} catch (Exception e) {
				System.err.println(file);
				e.printStackTrace();
			}
		});
		;
	}
}
