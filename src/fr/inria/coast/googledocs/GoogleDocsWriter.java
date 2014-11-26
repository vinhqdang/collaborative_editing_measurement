/**
 * 
 */
package fr.inria.coast.googledocs;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;

import fr.inria.coast.general.CollaborativeWriter;

/**
 * @author qdang
 *
 */
public class GoogleDocsWriter extends CollaborativeWriter {

	public GoogleDocsWriter(int n_user, int type_spd, String DOC_URL, int exp_id) {
		super(n_user, type_spd, DOC_URL, exp_id);
		// TODO Auto-generated constructor stub
		driver = new ChromeDriver();
		while (e == null) {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get (DOC_URL);
			e = driver.findElement(By.className("docs-texteventtarget-iframe"));
		}
	}
	
	@Override
	public void cancel () {
		super.cancel();
	}

}
