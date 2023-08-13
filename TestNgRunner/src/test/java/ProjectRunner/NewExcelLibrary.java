package ProjectRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public final class NewExcelLibrary {

	File f;
	FileOutputStream fos;
	FileInputStream fis;
	Sheet sh;
	Row row;
	Cell cell;
	Workbook wb;
	String path = null;

	public NewExcelLibrary(String path) {
		this.path = path;

	}
//	public static List<Map<String,String>> getDetails() 
//	{
//		List<Map<String,String>> list = null;
//		FileInputStream fis;
//		try {
//			fis= new FileInputStream("C:\\Users\\003KT8744\\Desktop\\sai.xlsx");
//			XSSFWorkbook wb= new XSSFWorkbook(fis);
//			String sheetname="sheet1";
//			XSSFSheet sh= wb.getSheet(sheetname);
//			//
//			Map<String,String> map=null;
//			 list= new ArrayList<>();
//			//
//			int rowCount = sh.getLastRowNum();
//			int colnum=sh.getRow(0).getLastCellNum();
//			//
//			for(int i=1;i<rowCount;i++)
//			{
//				map= new HashedMap<>();
//				for(int j=0;j<colnum;j++)
//				{
//					String key= sh.getRow(0).getCell(j).getStringCellValue();
//					String value= sh.getRow(i).getCell(j).getStringCellValue();
//					map.put(key, value);
//				}
//				list.add(map);
//			}
//		}
//		catch(FileNotFoundException e)
//		{
//			e.printStackTrace();
//		}
//		catch(IOException e1)
//		{
//			e1.printStackTrace();
//		}
//		return list;
//				
//		
//	}

	public int getRowCount(String SheetName) throws IOException {
		f = new File(path);
		fis = new FileInputStream(f);
		wb = new XSSFWorkbook(fis);
		sh = wb.getSheet(SheetName);
		int rowCount = sh.getLastRowNum();
		//
		wb.close();
		fis.close();
		return rowCount;
	}

	public int getCellCount(String SheetName, int rowNum) throws IOException {
		f = new File(path);
		fis = new FileInputStream(f);
		wb = new XSSFWorkbook(fis);
		sh = wb.getSheet(SheetName);
		row = sh.getRow(rowNum);
		int cellCount = row.getLastCellNum();
		//
		wb.close();
		fis.close();
		return cellCount;
	}

	public String getCellData(String SheetName, int rowNum, int cellNum) throws IOException {
		f = new File(path);
		fis = new FileInputStream(f);
		wb = new XSSFWorkbook(fis);
		sh = wb.getSheet(SheetName);
		row = sh.getRow(rowNum);
		cell = row.getCell(cellNum);
		// String cellvalue=cell.getStringCellValue();
		
		if(cell.getCellType()==CellType.STRING)
		{
			return cell.getStringCellValue();
		}
		else if(cell.getCellType()==CellType.NUMERIC)
		{
			String cellvalue=String.valueOf(cell.getStringCellValue());
			//
			if(DateUtil.isCellDateFormatted(cell))
			{
				DateFormat df= new SimpleDateFormat("yy/dd/mm");
				Date date=cell.getDateCellValue();
				cellvalue=df.format(date);
			}
			return cellvalue;
		}
		else
		{
			return String.valueOf(cell.getBooleanCellValue());
		}

//		DataFormatter formatter = new DataFormatter();
//		String data;
//		try {
//			data = formatter.formatCellValue(cell); // returns the formatted cell value as string regardless of value.
//		} catch (Exception e) {
//			data = "";
//		}
		//
	}
	
	public String getDataByRowColName(String Sheetname,String Rowname,String colname) throws IOException
	{
		int colnum=0;
		int rownum=-1;
		//
		f = new File(path);
		fis = new FileInputStream(f);
		wb = new XSSFWorkbook(fis);
		sh = wb.getSheet(Sheetname);
		//
		for(int i=0;i<=sh.getLastRowNum();i++)
		{
		    row=sh.getRow(i);
			String value=row.getCell(0).getStringCellValue();
			//
			if(value.equalsIgnoreCase(Rowname))
			{
				rownum=i;
			}
		}
		
		for(int i=0;i<row.getLastCellNum();i++)
		{
			row=sh.getRow(0);
			
			if(row.getCell(i).getStringCellValue().equalsIgnoreCase(colname))
			{
			colnum=i;
			}
		}
		
		row=sh.getRow(rownum);
		cell=row.getCell(colnum);
		//
		if(cell.getCellType()==CellType.STRING)
		{
			return cell.getStringCellValue();
		}
		else if(cell.getCellType()==CellType.NUMERIC)
		{
			String cellvalue=String.valueOf(cell.getStringCellValue());
			//
			if(DateUtil.isCellDateFormatted(cell))
			{
				DateFormat df= new SimpleDateFormat("yy/dd/mm");
				Date date=cell.getDateCellValue();
				cellvalue=df.format(date);
			}
			return cellvalue;
		}
		else
		{
			return String.valueOf(cell.getBooleanCellValue());
		}
		
	}

}
