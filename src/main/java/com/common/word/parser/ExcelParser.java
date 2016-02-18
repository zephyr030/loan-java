package com.common.word.parser;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2016/2/16.
 */
public class ExcelParser implements IParser {
    private String[] fileTypes={"xls", "xlsx"};
    public  String[] getFileTypes() {
        return fileTypes;
    }

    public String readText(File file, String charset) throws Exception {
        String name = file.getName();
        String fileType = name.substring(name.lastIndexOf(".") + 1, name.length());

        StringBuffer sb = new StringBuffer();
        InputStream stream = new FileInputStream(file);
        Workbook wb = null;
        if (fileType.equals("xls")) {
            wb = new HSSFWorkbook(stream);
        } else if (fileType.equals("xlsx")) {
            wb = new XSSFWorkbook(stream);
        } else {
            System.out.println("您输入的excel格式不正确");
        }
        Sheet sheet = wb.getSheetAt(0);
        for (Row row : sheet) {
            for (Cell cell : row) {
                String value = null;
                if(fileType.equals("xls")) {
                    value = getValue((HSSFCell)cell);
                } else if (fileType.equals("xlsx")) {
                    value = getValue((XSSFCell)cell);
                }
                sb.append(value + "  ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    private String getValue(XSSFCell xssfRow) {
        if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
            return String.valueOf(xssfRow.getBooleanCellValue());
        } else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
            double value = xssfRow.getNumericCellValue();
            if(DateUtil.isCellDateFormatted(xssfRow)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                return sdf.format(xssfRow.getDateCellValue()).toString();
            } else if(value > 10000) {
                DecimalFormat format = new DecimalFormat("#");
                String sMoney = format.format(value);
                return sMoney;
            } else {
                return  String.valueOf(value);
            }
        } else {
            return String.valueOf(xssfRow.getStringCellValue());
        }
    }

    private String getValue(HSSFCell hssfCell) {
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            double value = hssfCell.getNumericCellValue();
            if(HSSFDateUtil.isCellDateFormatted(hssfCell)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                return sdf.format(HSSFDateUtil.getJavaDate(hssfCell.getNumericCellValue())).toString();
            }else if(value > 10000) {
                DecimalFormat format = new DecimalFormat("#");
                String sMoney = format.format(value);
                return sMoney;
            } else {
                return  String.valueOf(value);
            }
        } else {
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }

    public static void main(String[] s) throws Exception {

    }
}
