/**
 * 
 */
package com.comdosoft.financial.manage.utils;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author gookin.wu
 *
 * Email: gookin.wu@gmail.com
 * Date: 2015年5月12日 下午1:41:40
 */
public class ExcelUtil {
	
	public static void create(String[][] datas,OutputStream outStream) throws IOException{
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet();
		writeSheet(datas, sheet);
		wb.write(outStream);
	}
	
	private static void writeSheet(String[][] datas,Sheet sheet) {
		for(int i=0;i<datas.length;++i){
			Row row = sheet.createRow(i);
			String[] data = datas[i];
			writeRow(data,row);
		}
	}

	private static void writeRow(String[] data,Row row){
		for(int i=0;i<data.length;++i){
			String s = data[i];
			Cell cell = row.createCell(i);
			cell.setCellValue(s);
		}
	}
}
