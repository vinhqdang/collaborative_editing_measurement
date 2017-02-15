/**
 * 
 */
package fr.inria.coast.cryptpad;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author qdang
 *
 */
public class CryptpadHelper {
	public static WebElement getWebElement (WebDriver driver) {
		WebElement e = driver.findElement(By.tagName("iframe"));
		driver.switchTo().frame(e);
		try {
			return driver.findElement(By.xpath("/html/body"));
		} catch (Exception e1) {
			System.out.println("Cannot get element of Cryptpad");
			return null;
		}
	}
}
