package com.common.word.parser;

import java.io.File;
import java.io.IOException;


public class PdfParser //implements IParser
{

	/*private String[] fileTypes={"pdf"};
	public String[] getFileTypes() {
		return fileTypes;
	}
	@Override
	public String readText(File file, String charsetName)
			throws Exception
	{
		String text = "";
		PDDocument document = null;
		try
		{
			document = PDDocument.load(file);
			PDFTextStripper stripper = new PDFTextStripper();
			text = stripper.getText(document);
		} catch (IOException e)
		{
			e.printStackTrace();
			throw e;
		} 
		finally{
			if
			(document != null)
			try
			{
				document.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return text;
	}
	public static void main(String[] s) throws Exception
	{
		String result = new PdfParser().readText(new File("d:\\ITextTest.pdf"),"GBK");
		System.out.println(result);
	}*/
}
