package ProjectRunner;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;


public class base {

	public WebDriver driver;
	public String xmlTestID;
	protected String xmlSheetName;
	protected String path="C:\\Users\\003KT8744\\eclipse-workspace\\TestNgRunner\\src\\main\\java\\TestData\\runnertestdata.xlsx";

	
//	@BeforeSuite
//	public void BeforeSuite() throws IOException {
//		// ExtentManager.setExtent();
//	}
//
//	@AfterSuite
//	public void AfterSuite() {
//		// ExtentManager.endReport();
//	}

	@BeforeMethod
	public void setup(ITestContext testContext) {
		xmlTestID=testContext.getName();
		xmlSheetName=testContext.getName().substring(0,xmlTestID.indexOf("_"));
		// System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\003KT8744\\Downloads\\chromedriver_win32 (6)\\chromedriver.exe");
		driver = new ChromeDriver();
	    System.out.println("iam started driver");
		driver.manage().window().maximize();
	    System.out.println("iam maximized");
		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
	    System.out.println("iam opened");
	}

	@AfterMethod
	public void tearDown() throws IOException {
		// driver.close();
	}

	public static String screenShot(WebDriver driver, String filename) {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "\\ScreenShot\\" + filename + "_" + dateName + ".png";
		File finalDestination = new File(destination);
		try {
			FileUtils.copyFile(source, finalDestination);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		return destination;
	}

	public static String getCurrentTime() 
	{
		String currentDate = new SimpleDateFormat("yyyy-MM-dd-hhmmss").format(new Date());
		return currentDate;
	}

}