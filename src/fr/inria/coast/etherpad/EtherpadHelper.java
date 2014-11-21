/**
 * 
 */
package fr.inria.coast.etherpad;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author "Quang-Vinh DANG"
 * utility functions for Etherpad
 */
public class EtherpadHelper {
	static public WebElement getContentElement (WebDriver driver) {
		WebElement innerDocBodyFrame = null;
		// Switch to the first frame
		try {
			//WebElement containerFrame = driver.findElement(By.id ("editorcontainer")).findElement(By.id("ace_outer"));
			WebElement containerFrame = driver.findElement(By.tagName("iframe"));
			driver.switchTo().frame(containerFrame);
		} catch (NoSuchElementException e) {
			System.out.println("Error: editorcontainer");
			//e.printStackTrace();
		} catch (NoSuchFrameException e) {
			System.out.println("Cannot switch to frame: editorcontainer");
			//e.printStackTrace();
		}
		// Switch to the nested frame.
		// Note that findElement is restricted to look within the currently "focused" frame.
		try {
			//WebElement outerDocBodyFrame = driver.findElement(By.id ("outerdocbody"));
			WebElement outerDocBodyFrame = driver.findElement(By.tagName("iframe"));
			driver.switchTo().frame(outerDocBodyFrame);
		} catch (NoSuchElementException e) {
			System.out.println("Error: outerdocbody");
			//e.printStackTrace();
		} catch (NoSuchFrameException e) {
			System.out.println("Cannot switch to frame: outerdocbody");
			//e.printStackTrace();
		}

		try {
			innerDocBodyFrame = driver.findElement(By.id ("innerdocbody"));
		} catch (NoSuchElementException e) {
			System.out.println("Error: innerdocbody");
			//e.printStackTrace();
		} catch (NoSuchFrameException e) {
			System.out.println("Cannot switch to frame: innerdocbody");
			//e.printStackTrace();
		}
		return innerDocBodyFrame;
	}
}
