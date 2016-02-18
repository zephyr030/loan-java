package com.common.word.parser;

import java.io.File;


/**
 * 文件内容解析接口
 * @author zgd
 * @version 2010-7-12 1.0
 *
 */
public interface IParser{
	/**
	 *  功能描述：返回能够解析的文件类型，后缀名
	 *  例如{doc,txt,html}
	 * @return
	 */
	public String[] getFileTypes();
	/**
	 * @param file 源文件
	 * @param charset 文件编码
	 * @return 文件内容
	 * @throws Exception
	 */
	public  String readText(File file, String charset)throws Exception;
}
