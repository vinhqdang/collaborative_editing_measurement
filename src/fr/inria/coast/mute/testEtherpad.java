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
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
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
		driver.get (URL);
		WebElement e = driver.findElement(By.id ("focusprotector"));
		//WebElement e = driver.findElements(By.className("ace-line")).get(0);
		while (true) {
			System.out.println("text:");
			System.out.println (e.getText());
		}
	}

}
