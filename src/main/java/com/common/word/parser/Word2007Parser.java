package com.common.word.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
public class Word2007Parser implements IParser
{
	private String[] fileTypes={"docx"};
	public String[] getFileTypes() {
		return fileTypes;
	}
	public String readText(File file,String charsetName) throws Exception{
		String text = "";
		FileInputStream fis = null;
		try
		{
			fis =new FileInputStream(file);
			XWPFDocument document = new XWPFDocument(fis);
			XWPFWordExtractor extractor = new XWPFWordExtractor(document);
			text = extractor.getText();

		}catch(Exception e){
			e.printStackTrace();
		}
		 finally {
			if(fis != null)
				try
				{
					fis.close();
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
		}
		return text;
	}


	public static void main(String[] s) throws Exception {
		long total = 0L;
		for (int i = 0; i < 1; i++) {
			long start = System.currentTimeMillis();
			 
			System.out.println(new Word2007Parser().readText(new File("D:\\A股交收核对流程_生产环境_20130123.docx"),"gbk"));

			total += System.currentTimeMillis() - start;
			
		}
		System.out.print("耗时：" + total);
	}
}
