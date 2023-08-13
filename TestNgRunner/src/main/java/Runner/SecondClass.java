package Runner;

import org.testng.annotations.Test;

public class SecondClass 
{
	@Test
	public void sample1()
	{
		System.out.print("the expected name is" + this.getClass().getCanonicalName());
	}

}
