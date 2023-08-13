package ProjectRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import org.junit.runner.RunWith;

//import io.cucumber.junit.CucumberOptions;

//import cucumber.api.CucumberOptions;
//import cucumber.api.junit.Cucumber;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = {"pretty",
				  "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
				  "timeline:test-output-thread/"			  
		})
public class TestRunner 
{
	public static void main(String[] args) throws IOException {
		List<XmlSuite> listOfSuites = new ArrayList<XmlSuite>();
		XmlSuite xmlsuite = new XmlSuite();
		xmlsuite.setName("Active Project Execution");
		String epath = System.getProperty("user.dir");

		System.out.println(epath + "is epath");
		//
		// adding listeners
		//
		String Path = "C:\\Users\\003KT8744\\eclipse-workspace\\TestNgRunner\\src\\main\\java\\TestData\\runnertestdata.xlsx";
		//
		File f = new File(Path);
		FileInputStream fis = new FileInputStream(f);
		Workbook wb = new XSSFWorkbook(fis);
		int shCount = wb.getNumberOfSheets();
		//
		for (int i = 0; i < shCount; i++) 
		{
			String sheetName = wb.getSheetName(i);
			createXML(xmlsuite, Path, sheetName, listOfSuites);
		}
		//
		boolean runflag = false;
		for (XmlSuite suite1 : listOfSuites) 
		{
			dynamicXml(xmlsuite, "myExcelXML.xml");
			runflag = true;
		}
		//
		if (runflag) 
		{
			TestNG testng = new TestNG();
			List<String> newSuite = new ArrayList<String>();
			String xmlName = "C:\\Users\\003KT8744\\eclipse-workspace\\TestNgRunner\\Reports\\myBasicReport\\myExcelXML.xml";
			newSuite.add(xmlName);
			testng.setTestSuites(newSuite);
			testng.run();
		}
	}

	public static void dynamicXml(XmlSuite xmlsuite, String fileName) throws IOException 
	{
		directoryCreate("./Reports/myBasicReport");
		FileWriter writer = new FileWriter("./Reports/myBasicReport/" + fileName);
		writer.write(xmlsuite.toXml());
		writer.flush();
		writer.close();
	}

	public static void directoryCreate(String path) 
	{
		File f = new File(path);
		if (!f.exists()) {
			if (f.mkdir()) {
				System.out.println("dir got created just now");
			} else {
				System.out.println("dir not created just now");
				f.mkdirs();
				if (f.exists()) {
					System.out.println("sub dir got created just now");
				}
			}
		}
	}

	public static void createXML(XmlSuite xmlsuite, String Path, String sheetname, List<XmlSuite> listOfSuites) throws IOException
	{
		XmlTest xmlTest = null;
		String prevxmlTestID = null;
		Object[][] data = getData(Path, sheetname);
		String rowcolcount = getRowAndColCount(Path, sheetname);
		int rowCount = Integer.parseInt(rowcolcount.split(",")[0]);
		//
		for (int i = 0; i < rowCount; i++) 
		{
			String xmlTestID = data[i][0].toString();
			String xmlTestName = data[i][1].toString();
			String flag = data[i][3].toString();
			//
			if (flag.equalsIgnoreCase("Y")) 
			{
				xmlTest = new XmlTest(xmlsuite);
				xmlTest.setName(xmlTestID);
				//
				String classname = "ProjectRunner." + xmlTestName;
				if (!xmlTestID.equalsIgnoreCase(prevxmlTestID)) 
				{
					System.out.println(classname + " is classname");
					XmlClass classes = new XmlClass(classname);
					List<XmlClass> list = new ArrayList<>();
					list.add(classes);
					xmlTest.setXmlClasses(list);
				}
			}
			prevxmlTestID = xmlTestID;
			listOfSuites.add(xmlsuite);
		}
	}

	public static String getRowAndColCount(String Path, String SheetName) throws IOException {
		File f = new File(Path);
		FileInputStream fis = new FileInputStream(f);
		Workbook wb = new XSSFWorkbook(fis);
		Sheet sh = wb.getSheet(SheetName);
		int rows = sh.getLastRowNum();
		int cols = sh.getRow(0).getLastCellNum();
		//
		String rowAndColCount = rows + "," + cols;
		//
		return rowAndColCount;
	}

	public static Object[][] getData(String Path, String sheetname) throws IOException {

		File f = new File(Path);
		FileInputStream fis = new FileInputStream(f);
		Workbook wb = new XSSFWorkbook(fis);
		Sheet sh = wb.getSheet(sheetname);
		int rows = sh.getLastRowNum();
		int cols = sh.getRow(0).getLastCellNum();
		//
		Object[][] data = new Object[rows][cols];

		for (int i = 1; i <= rows; i++) {
			for (int j = 0; j < cols; j++) {
				data[i - 1][j] = getCellData(Path, sheetname, i, j);
			}
		}
		return data;
	}

	public static String getCellData(String Path, String SheetName, int rowNum, int cellNum) throws IOException {
		File f = new File(Path);
		FileInputStream fis = new FileInputStream(f);
		Workbook wb = new XSSFWorkbook(fis);
		Sheet sh = wb.getSheet(SheetName);
		Row row = sh.getRow(rowNum);
		Cell cell = row.getCell(cellNum);
		// String cellvalue=cell.getStringCellValue();
		if (cell.getCellType() == CellType.STRING) {
			return cell.getStringCellValue();
		} else if (cell.getCellType() == CellType.NUMERIC) {
			String cellvalue = String.valueOf(cell.getStringCellValue());
			//
			if (DateUtil.isCellDateFormatted(cell)) {
				DateFormat df = new SimpleDateFormat("yy/dd/mm");
				Date date = cell.getDateCellValue();
				cellvalue = df.format(date);
			}
			return cellvalue;
		} else {
			return String.valueOf(cell.getBooleanCellValue());
		}
		//
	}


}
