/**
 * 
 */
package fr.inria.coast.mute;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

/**
 * @author vinh
 *
 */
public class testEtherpad {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		final String URL = "http://152.81.3.91:9001/p/" + Integer.toString(new Random().nextInt());
		WebDriver driver = new FirefoxDriver ();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get (URL);
		
		// Switch to the first frame
		try {
			WebElement containerFrame = driver.findElement(By.id ("editorcontainer"));
			//driver.switchTo().frame(2);
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
		WebElement outerDocBodyFrame = driver.findElement(By.id ("outerdocbody"));
		driver.switchTo().frame(outerDocBodyFrame);
		} catch (NoSuchElementException e) {
			System.out.println("Error: outerdocbody");
			//e.printStackTrace();
		} catch (NoSuchFrameException e) {
			System.out.println("Cannot switch to frame: outerdocbody");
			//e.printStackTrace();
		}
		
		try {
			WebElement innerDocBodyFrame = driver.findElement(By.id ("innerdocbody"));
			driver.switchTo().frame (innerDocBodyFrame);
		} catch (NoSuchElementException e) {
			System.out.println("Error: innerdocbody");
			//e.printStackTrace();
		} catch (NoSuchFrameException e) {
			System.out.println("Cannot switch to frame: innerdocbody");
			//e.printStackTrace();
		}



		// Get the innerHTML property of the documentElement
		// Note that likewise, JavaScript is executed in the context of the currently "focused" frame.
		//String content = (String)((JavascriptExecutor)driver).executeScript("return document.documentElement.innerHTML;");

		// Return back to the top-level document
		//driver.switchTo().defaultContent();
		while (true) {
			String content = (String)((JavascriptExecutor)driver).executeScript("return document.documentElement.innerHTML;");
			System.out.println("text:");
			String text = (String)((JavascriptExecutor)driver).executeScript("return document.getElementById('editorcontainer').children[0].contentDocument.getElementById('outerdocbody').children[0].contentDocument.getElementById('innerdocbody').innerHTML");
			//System.out.println (text);
			//System.out.println(content);
			break;
		}
	}

}
