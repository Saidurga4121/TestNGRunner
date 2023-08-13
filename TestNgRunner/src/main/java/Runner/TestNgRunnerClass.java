package Runner;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;



public class TestNgRunnerClass 
{
	
	public static void main(String[] args) throws IOException
	{
		XmlSuite xmlsuite= new XmlSuite();
		xmlsuite.setName("CodeMapper");
		xmlsuite.setVerbose(1);
//
		XmlTest xmlTest1 = new XmlTest(xmlsuite);
		xmlTest1.setName("Test - 1");
		
		
		XmlTest xmlTest2 = new XmlTest(xmlsuite);
		xmlTest2.setName("Test - 2");
		// xmlTest.setPreserveOrder("true");
		
		 XmlInclude includeMethod = new XmlInclude("anotherPublicMethodTest");
			
			XmlClass firstClass = new XmlClass(FirstClass.class);
			firstClass.setIncludedMethods(Arrays.asList(includeMethod));
			
		
		XmlClass secondClass= new XmlClass(SecondClass.class);
		
		
		List<XmlClass> list1= new ArrayList<>();
		list1.add(firstClass);
		
		List<XmlClass> list2= new ArrayList<>();
		list2.add(secondClass);
		
       
		
		
		xmlTest1.setXmlClasses(list1);
		xmlTest2.setXmlClasses(list2);
		
		TestNG testng= new TestNG();
		List<XmlSuite> suites= new ArrayList<>();
	    
		suites.add(xmlsuite);
		testng.setXmlSuites(suites);
		testng.run();
		
		FileWriter writer= new FileWriter("C:\\Users\\003KT8744\\eclipse-workspace\\TestNgRunner\\Reports\\myBasicXML");
		writer.write(xmlsuite.toXml());
		writer.flush();
		writer.close();
		
	}

}





