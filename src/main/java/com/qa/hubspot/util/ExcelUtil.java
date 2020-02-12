package com.qa.hubspot.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {
	
	public static Workbook book;
	public static Sheet sheet;
	
	public static String TESTDATA_SHEET_PATH = "C:\\Users\\Sireesha\\eclipse-workspace\\JavaTrainingNaveen\\POMSeries\\src\\main\\java\\com\\qa\\hubspot\\testdata\\HubSpotTestData.xlsx";
	
	public static Object[][] getTestData(String sheetName) {
		
		
			try {
				FileInputStream ip = new FileInputStream(TESTDATA_SHEET_PATH);
				
				book = WorkbookFactory.create(ip);
				sheet = book.getSheet(sheetName);
				
				Object data[][] = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
				
				for(int i =0;i<sheet.getLastRowNum();i++) {
					
					for(int k =0;k<sheet.getRow(0).getLastCellNum();k++) {
						
						data[i][k] = sheet.getRow(i+1).getCell(k).toString();
					}
				}
				
				return data;
				
			} catch (FileNotFoundException e) {
				System.out.println("File not Found Exception");
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidFormatException e) {
				System.out.println("InvalidFormatException");
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("IOException");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
					
		
	}

}
