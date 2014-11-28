/**
 * 
 */
package fr.inria.coast.cryptpad;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;

import fr.inria.coast.general.CollaborativeReader;

/**
 * @author qdang
 *
 */
public class CryptpadReader extends CollaborativeReader {

	public CryptpadReader(int n_user, int type_spd, String DOC_URL, int exp_id) {
		super(n_user, type_spd, DOC_URL, exp_id);
		// TODO Auto-generated constructor stub
		this.driver = new ChromeDriver();
		while (this.e == null) {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get(DOC_URL);
			this.e = CryptpadHelper.getWebElement(driver);
		}
	}

}
