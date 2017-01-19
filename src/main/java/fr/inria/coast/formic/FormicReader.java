/**
 * 
 */
package fr.inria.coast.formic;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import fr.inria.coast.general.CollaborativeReader;

/**
 * @author qdang
 *
 */
public class FormicReader extends CollaborativeReader {

	public FormicReader(int n_user, int type_spd, String DOC_URL, int exp_id) {
		super(n_user, type_spd, DOC_URL, exp_id);
		// TODO Auto-generated constructor stub
		driver = new ChromeDriver();
		while (this.e == null) {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get(DOC_URL);
			this.e = driver.findElement(By.className("kix-lineview"));
		}
	}

}
