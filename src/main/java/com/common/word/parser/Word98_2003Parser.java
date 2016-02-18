package com.common.word.parser;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
public class Word98_2003Parser implements IParser
{
	private String[] fileTypes={"doc"};
	public String[] getFileTypes() {
		return fileTypes;
	}
	public String readText(File file,String charsetName) throws Exception{
		String text = "";
		BufferedInputStream fis2 = null;
		try
		{
  
			fis2 =new BufferedInputStream(new FileInputStream(file),2048);
			HWPFDocument document = new HWPFDocument(fis2);
			WordExtractor extractor = new WordExtractor(document);
			text = extractor.getText();

		} finally {
			if(fis2 != null)
				try
				{
					fis2.close();
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
			System.out.println(new Word98_2003Parser().readText(
//					new File("D:/work/pdss/论文检测结果.doc"), "gb2312"));
					new File("D:/桌面/惠今发标通知书0343.doc"), "GB2312"));
//			new File("d:/新员工转正申请表.doc"), "gb2312"));
			total += System.currentTimeMillis() - start;
			
		}
		System.out.print("耗时：" + total);
	}
}
