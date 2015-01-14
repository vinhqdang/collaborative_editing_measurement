/**
 * 
 */
package fr.inria.coast.googledocs;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionNotFoundException;

import fr.inria.coast.general.CollaborativeRemoteDummyWriter;

/**
 * @author qdang
 *
 */
public class GoogleDocsRemoteDummyWriter extends CollaborativeRemoteDummyWriter {
	char c = (char) ('a' + new Random ().nextInt(15));
	public GoogleDocsRemoteDummyWriter(int n_user, int type_spd,
			String DOC_URL, int exp_id, String serverAddr) {
		super(n_user, type_spd, DOC_URL, exp_id, serverAddr);
		try {
			this.remoteDriver = new RemoteWebDriver(new URL ("http://" + serverAddr + ":4444/wd/hub"), capabilities);
		} catch (MalformedURLException e) {
			System.out.println("Cannot get remoted web driver at URL: " + serverAddr);
		}
		
		remoteDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		remoteDriver.get(DOC_URL);
		this.e = remoteDriver.findElement(By.className("docs-texteventtarget-iframe"));
	}
	
	@Override
	public void run () {
		while (shouldWrite) {
			this.e.sendKeys("" + c);
			int nextStep = new Random().nextInt () / 100;
			if (nextStep % 10 == 0) {
				int j = nextStep / 5;
				while (j != 0) {
					try {
						this.e.sendKeys(Keys.DELETE);
						j--;
					} catch (SessionNotFoundException e1) {
						System.out.println("Remote dummy quited before");
						return;
					} catch (Exception e1) {
						System.out.println("Catch exception when delete randomly at remote writer");
						e1.printStackTrace();
					}
				}
			} 
			
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e1) {
				//do not need to handle because interrupt means main writing and reading thread finish
				System.out.println("Interruped while writing remote dummy text");
			} catch (WebDriverException e1) {
				System.out.println("Catch WebDriver exception at remote dummy");
			} catch (Exception e1) {
				System.out.println("General exception while waiting at remote dummy");
			}
		}
	}
}
