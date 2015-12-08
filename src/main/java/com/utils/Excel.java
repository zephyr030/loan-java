package com.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xuan.utils.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class Excel {
	//日志
    public static Log log = LogFactory.getLog(Excel.class);
	
    /**
     * EXCEL导出
     * @param request
     * @param response
     * @param map---------->excel每一列的头部
     * @param mapKey------->List数据集合的字段名称
     * @param list -------->数据集合
     * @throws Exception
     */
	public static void ExportExcel(HttpServletRequest request,
								   HttpServletResponse response,
								   Map<String,String> map,
								   Map<String,String> mapKey,
								   List<Map<String,Object>> list)throws Exception{
		//输出流
		OutputStream os = null;
		WritableWorkbook wwb = null;
		WritableSheet ws = null;
		try{
			os = response.getOutputStream();
			String fileName = DateUtils.currentDate2StringByDay()+".xls";
			//生成文件
	        response.reset();
	        response.setHeader("Content-Disposition", "attachment; filename="+ new String(fileName.getBytes(), "ISO-8859-1"));
	        response.setContentType("application/msexcel");
	        //创建excel
		    wwb = Workbook.createWorkbook(os);
		    ws = wwb.createSheet("统计",0);
		    ws.getSettings().setDefaultColumnWidth(10);
		    //创建表头
		    WritableFont wfc = new WritableFont(WritableFont.ARIAL,10,WritableFont.NO_BOLD,false,UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.RED);
		    WritableCellFormat wcfFC = new WritableCellFormat(wfc);
		    int index = 0;
		    for(Map.Entry<String, String> entry:map.entrySet()){    
		    	Label label = new Label(index,0,entry.getValue(),wcfFC);
		    	//将列名添加到单元格
				ws.addCell(label);
				//设置列宽度
				ws.setColumnView(index, 15);
	            index++;
			}
		    
		    //设置单元格数据
		    for(int i=0;i<list.size();i++){
		    	Map<String,Object> model = list.get(i);
		    	//填充单元格数据
		    	int map_index = 0;
		    	for(Map.Entry<String, String> entry:mapKey.entrySet()){
		    		Label label = new Label(map_index,i+1,model.get(entry.getValue())+"");
		    		ws.addCell(label);
		    		map_index++;
		    	}
		    }
		    wwb.write();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(wwb != null){
					wwb.close();
				}
				if(os != null){
					os.close();
				}
			} catch (WriteException e) {
				e.printStackTrace();
				throw e;
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
}
