/**
 * 
 */
package fr.inria.coast.googledocs;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;

import fr.inria.coast.general.CollaborativeDummyWriter;

/**
 * @author qdang
 *
 */
public class GoogleDocsDummyWriter extends CollaborativeDummyWriter {
	public GoogleDocsDummyWriter(int n_user, int type_spd, String DOC_URL,
			int exp_id) {
		super(n_user, type_spd, DOC_URL, exp_id);
		// TODO Auto-generated constructor stub
		this.driver = new ChromeDriver();
		while (this.e == null) {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get(DOC_URL);
			this.e = driver.findElement(By.className("docs-texteventtarget-iframe"));
		}
	}
	
	@Override
	public void run () {
		while (shouldWrite) {
			this.e.sendKeys("a");
			/*
			int nextStep = new Random().nextInt () / 100;
			if (nextStep % 10 == 0) {
				this.e.sendKeys(Keys.DELETE);
			} else if (nextStep > 0) {
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
