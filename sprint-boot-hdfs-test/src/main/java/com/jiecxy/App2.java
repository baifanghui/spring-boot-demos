package com.jiecxy;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

public class App2 {

	public static void main(String[] args) {
		// readAndWrite();
		try {
			readFromHdfs();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("ok");
	}

	private static void uploadToHdfs() throws FileNotFoundException, IOException {
		String localSrc = "C:/Users/lenovo/Desktop/test.txt";
		String dst = "hdfs://hd1:9000/test/" + System.currentTimeMillis() + ".txt";
		InputStream in = new BufferedInputStream(new FileInputStream(localSrc));
		Configuration conf = new Configuration();

		FileSystem fs = FileSystem.get(URI.create(dst), conf);
		OutputStream out = fs.create(new Path(dst), new Progressable() {
			public void progress() {
				System.out.print(".");
			}
		});
		IOUtils.copyBytes(in, out, 4096, true);
	}

	/** 从HDFS上读取文件 */
	private static void readFromHdfs() throws FileNotFoundException, IOException {
		String dst = "hdfs://hd1:8020/test/test.txt";
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(dst), conf);
		FSDataInputStream hdfsInStream = fs.open(new Path(dst));

		OutputStream out = new FileOutputStream("d:/qq-hdfs.txt");
		byte[] ioBuffer = new byte[1024];
		int readLen = hdfsInStream.read(ioBuffer);

		while (-1 != readLen) {
			out.write(ioBuffer, 0, readLen);
			readLen = hdfsInStream.read(ioBuffer);
		}
		out.close();
		hdfsInStream.close();
		fs.close();
	}

	private static void readAndWrite() {
		try {
			Configuration conf = new Configuration();

			// 不设置该代码会出现错误：java.io.IOException: No FileSystem for scheme: hdfs
			conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");

			String filePath = "hdfs://hd1:9000/test/test.txt";
			Path path = new Path(filePath);

			// 这里需要设置URI，否则出现错误：java.lang.IllegalArgumentException: Wrong FS:
			// hdfs://127.0.0.1:9000/test/test.txt, expected: file:///
			FileSystem fs = FileSystem.get(URI.create(filePath), conf);

			System.out.println("READING ============================");
			FSDataInputStream is = fs.open(path);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			// 示例仅读取一行
			String content = br.readLine();
			System.out.println(content);
			br.close();

			System.out.println("WRITING ============================");
			byte[] buff = "this is helloworld from java api!\n".getBytes();
			FSDataOutputStream os = fs.create(path);
			os.write(buff, 0, buff.length);
			os.close();
			fs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
