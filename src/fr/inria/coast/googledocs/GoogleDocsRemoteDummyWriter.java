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
import org.openqa.selenium.remote.RemoteWebDriver;

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
		//this.e.click();
		while (shouldWrite) {
			this.e.sendKeys("" + c);
			int nextStep = new Random().nextInt () / 100;
			if (nextStep % 10 == 0) {
				int j = nextStep / 20;
				while (j != 0) {
					this.e.sendKeys(Keys.BACK_SPACE);
					j--;
				}
			} 
			/*
			else if (nextStep > 0) {
				for (int i = 0; i < nextStep; i++) {
					this.e.sendKeys(Keys.ARROW_RIGHT);
				}
				this.e.sendKeys("" + c);
			} else if (nextStep < 0) {
				for (int i = nextStep; i < 0; i++) {
					this.e.sendKeys(Keys.ARROW_LEFT);
				}
				this.e.sendKeys("" + c);
			}
			*/
			
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e1) {
				//do not need to handle because interrupt means main writing and reading thread finish
				//System.out.println("Interruped while writing dummy text");
				return;
			}
		}
	}
}
