/**
 * 
 */
package fr.inria.coast.cryptpad;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;

import fr.inria.coast.general.CollaborativeWriter;

/**
 * @author qdang
 *
 */
public class CryptpadWriter extends CollaborativeWriter{

	public CryptpadWriter(int n_user, int type_spd, String DOC_URL, int exp_id, int textSize) {
		super(n_user, type_spd, DOC_URL, exp_id, textSize);
		// TODO Auto-generated constructor stub
		this.driver = new ChromeDriver();
		while (this.inputElement == null) {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get(DOC_URL);
			this.inputElement = CryptpadHelper.getWebElement(driver);
		}
	}
}
