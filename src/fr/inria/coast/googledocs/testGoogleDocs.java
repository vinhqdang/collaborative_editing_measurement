package fr.inria.coast.googledocs;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class testGoogleDocs {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		WebDriver driver = new FirefoxDriver ();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get ("https://docs.google.com/document/d/1UmEc42z6bAD95fvdU5zTdv4ODcgu3LDpC9lDNJgjzGg/edit?usp=sharing");
		WebElement content = driver.findElement(By.className("kix-lineview"));
		WebElement c2 = driver.findElement(By.className("kix-lineview-content"));
		System.out.println("found");
		
		while (true) {
			//c2.click();
			//Thread.sleep (300);
			c2.click();
			Thread.sleep (3000);
			content.sendKeys("abc");
		}
	}

}
