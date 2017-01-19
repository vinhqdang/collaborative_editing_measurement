/**
 * 
 */
package fr.inria.coast.formic;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import fr.inria.coast.general.CollaborativeReader;

/**
 * @author qdang
 *
 */
public class FormicReader extends CollaborativeReader {

	private String stringId;

	public FormicReader(int n_user, int type_spd, String docUrl, int exp_id) {
		super(n_user, type_spd, docUrl, exp_id);
		// TODO Auto-generated constructor stub
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(docUrl);
		createNewFormicString();
		this.inputElement = driver.findElement(By.className("stringInput"));
	}

	/**
	 * Because the reader is the first one that is initialized, it has to create the formic string.
	 */
	private void createNewFormicString() {
		driver.findElement(By.id("new-string-button")).click();
		WebElement input = driver.findElement(By.className("stringInput"));
		this.stringId = input.getAttribute("id");
	}
	
	public String getStringId() {
		return stringId;
	}

}
