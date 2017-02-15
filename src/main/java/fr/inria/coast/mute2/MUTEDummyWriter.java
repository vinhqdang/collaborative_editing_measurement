/**
 * 
 */
package fr.inria.coast.mute2;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import fr.inria.coast.general.CollaborativeDummyWriter;

/**
 * @author qdang
 *
 */
public class MUTEDummyWriter extends CollaborativeDummyWriter {

	public MUTEDummyWriter(int n_user, int type_spd, String DOC_URL, int exp_id) {
		super(n_user, type_spd, DOC_URL, exp_id);
		// TODO Auto-generated constructor stub
		this.driver = new ChromeDriver();
		while (this.inputElement == null) {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get(DOC_URL);
			this.inputElement = driver.findElement(By.className("ace_text-input"));
		}
	}

}
