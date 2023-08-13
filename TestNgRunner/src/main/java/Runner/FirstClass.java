package Runner;

import org.testng.annotations.Test;

public class FirstClass
{
	@Test
	public void sample()
	{
		System.out.print("the expected name is" + this.getClass().getCanonicalName());
	}
	@Test
	public void anotherPublicMethodTest() {
		System.out.println("anotherPublicMethodTest");
		//System.out.println("Class Name " + this.getClass().getCanonicalName());
	}
}
