/**
 * 
 */
package fr.inria.coast.formic;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import fr.inria.coast.general.CollaborativeDummyWriter;

/**
 * @author qdang
 *
 */
public class FormicDummyWriter extends CollaborativeDummyWriter {

	public FormicDummyWriter(int n_user, int type_spd, String docUrl, int exp_id, String formicStringId) {
		super(n_user, type_spd, docUrl, exp_id);
		this.driver = new ChromeDriver();
		LOG.info("Dummy opening webpage");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.get(docUrl);
		subscribeForFormicString(formicStringId);
		this.inputElement = driver.findElement(By.className("stringInput"));
	}

	private void subscribeForFormicString(String formicStringId) {
		WebElement subscribeInput = driver.findElement(By.id("subscribe-id"));
		subscribeInput.sendKeys(formicStringId);
		driver.findElement(By.id("subscribe-button")).click();
		LOG.info("Dummy subscribed for string");
	}

	@Override
	public void run() {
		while (shouldWrite) {
			this.inputElement.sendKeys("a");
			int nextStep = new Random().nextInt() / 100;
			if (nextStep % 10 == 0) {
				int j = nextStep / 20;
				while (j != 0) {
					this.inputElement.sendKeys(Keys.BACK_SPACE);
					j--;
				}
			}
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e1) {
				// do not need to handle because interrupt means main writing
				// and reading thread finish
				System.out.println("Interruped while writing dummy text");
				return;
			}
		}
	}
}
