/**
 * 
 */
package fr.inria.coast.yjs;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import fr.inria.coast.general.CollaborativeWriter;

/**
 * @author Quang Vinh DANG
 *
 */
public class YjsWriter extends CollaborativeWriter {

	public YjsWriter(int n_user, int type_spd, String DOC_URL, int exp_id, int textSize) {
		super(n_user, type_spd, DOC_URL, exp_id, textSize);
		driver = new ChromeDriver();
		while (this.inputElement == null) {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get(DOC_URL);
			this.inputElement = driver.findElement(By.id("textfield"));
		}
	}

}
