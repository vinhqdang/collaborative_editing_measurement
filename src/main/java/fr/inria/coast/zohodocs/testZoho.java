/**
 * 
 */
package fr.inria.coast.zohodocs;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * @author qdang
 *
 */
public class testZoho {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		WebDriver driver = new ChromeDriver ();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get ("https://docs.zoho.com/writer/ropen.do?rid=0eq2s176d9e236ea74353a09c8c0a68faa6b3");
		WebElement content = driver.findElement(By.className("zw-portion"));
		System.out.println("found");
		
		while (true) {
			System.out.println(content.getText());
			Thread.sleep (3000);
			content.sendKeys("abc");
		}

	}

}
