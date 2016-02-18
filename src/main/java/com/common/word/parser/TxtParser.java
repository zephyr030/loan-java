package com.common.word.parser;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
/**
 * 功能描述：txt文件内容解析类
 * @author zhao
 * @version 2010-7-10 1.0
 */
public class TxtParser implements IParser {
	private String[] fileTypes={"txt"};
	public  String[] getFileTypes() {
		return fileTypes;
	}
	public String readText(File file, String charset) throws Exception {
		FileChannel inChannel = null;
		ByteBuffer bb = ByteBuffer.allocate(512);
		Charset cs = Charset.forName(charset);
		StringBuffer sb = new StringBuffer();
		FileInputStream inFile=null;
		CharBuffer cb = null;
		try{
		 inFile = new FileInputStream(file);
		 inChannel = inFile.getChannel();
			while(inChannel.read(bb) != -1) {
				bb.flip();
				cb=cs.decode(bb);
				sb.append(cb);
			    bb.clear();
			}
		}finally {
			if(inFile != null)
				 try
				 {
					inFile.close();
			    	} catch (IOException e)
				 {
					e.printStackTrace();
				 }
		}
        return sb.toString();
	}

	public static void main(String[] s) throws Exception {
		long total = 0L;
		for (int i = 0; i < 1; i++) {
			long start = System.currentTimeMillis();
			System.out.println(new TxtParser().readText(
					new File("d:/test2.txt"), "gb2312"));
			total += System.currentTimeMillis() - start;
			
		}
		System.out.print("耗时：" + total);
	}
}
