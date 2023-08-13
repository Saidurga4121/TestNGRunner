package ProjectRunner;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class OrangeHRMTest extends base{

	NewExcelLibrary obj= new NewExcelLibrary(path);

//	@Test
//	public void loginPageTest() {
//		
//		WebElement imgElement = driver.findElement(By.xpath("//*[@id=\"divLogo\"]/img"));
//		Assert.assertTrue(imgElement.isDisplayed());
//		System.out.println("passed");
//	}

	@Test
	public void loginTest() throws IOException, InterruptedException {
        String uname=obj.getDataByName(xmlSheetName, xmlTestID, "uname");
        String pwd= obj.getDataByName(xmlSheetName, xmlTestID, "pwd");
        System.out.println(pwd+ " is password");
        Thread.sleep(10000);
		driver.findElement(By.xpath("//input[@name='username']")).sendKeys(uname);
		Thread.sleep(1000);
		driver.findElement(By.name("password")).sendKeys(pwd);
		Thread.sleep(1000);
		driver.findElement(By.id("btnLogin")).click();
		String actualTitle = driver.getTitle();
		String expectedTitle = "OrangeHRM1";
		Assert.assertEquals(actualTitle, expectedTitle);
	}

}
