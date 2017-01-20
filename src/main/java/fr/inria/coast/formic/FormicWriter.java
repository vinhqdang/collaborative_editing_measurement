/**
 * 
 */
package fr.inria.coast.formic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import fr.inria.coast.general.CollaborativeAutomator;
import fr.inria.coast.general.CollaborativeWriter;

/**
 * @author qdang
 *
 */
public class FormicWriter extends CollaborativeWriter {

	public FormicWriter(int n_user, int type_spd, String docUrl, int exp_id, String formicStringId, int textSize) {
		super(n_user, type_spd, docUrl, exp_id, textSize);
		driver = new ChromeDriver();
		while (inputElement == null) {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get (docUrl);
			subscribeForFormicString(formicStringId);
			inputElement = driver.findElement(By.className("stringInput"));
		}
	}
	
	private void subscribeForFormicString(String formicStringId) {
		WebElement subscribeInput = driver.findElement(By.id("subscribe-id"));
		subscribeInput.sendKeys(formicStringId);
		driver.findElement(By.id("subscribe-button")).click();
	}

	@Override
	public void run () {
		for (counter = 0; counter < textSize; counter++) {
			writeTime[counter] = System.currentTimeMillis();
			String textToType = String.format ("%03d", counter) + "x";
			inputElement.sendKeys(textToType);
			try {
				sleep(delay);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(CollaborativeAutomator.resultFile, true)))) {
			for (int i = 0; i < textSize; i++) {
				out.print("W ");
				out.print(n_user);
				out.print(" ");
				out.print(type_spd);
				out.print(" ");
				out.print(exp_id);
				out.print(" ");
				out.print(String.format ("%03d", i));
				out.print(" ");				
				out.print(writeTime[i]);
				out.print("\n");
			}		
			out.println("***");
		}catch (IOException e) {
			System.out.println ("Error while writing the write time");
			e.printStackTrace();
		}
	}
	
	@Override
	public void cancel () {
		super.cancel();
	}

}
