/**
 * 
 */
package fr.inria.coast.mute;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

/**
 * @author vinh
 *
 */
public class AutomaticMute {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		String BASE_URL = "http://152.81.3.91:8080";
		int n_users[] = {50};
		
		int random_number = new Random().nextInt();
		
		for (int i = 0; i < n_users.length; i++) {
			int n_user = n_users [i];
			for (int type_spd = 1; type_spd <=10; type_spd++) {
				for (int exp_id = 0; exp_id < 2; exp_id++) {
					//temporary
					//to continue broken exp
					//if (n_user == 40 && (type_spd <= 7 || (type_spd == 8 && exp_id <= 0))) continue;
					
					System.out.println ("n_user:" + n_user + " type_spd: " + type_spd + " exp_id:" + exp_id);
					
					//generate random document
					//to prevent a big operation history
					String URL = BASE_URL + "/doc/" + Integer.toString (random_number) + Integer.toString(n_user) + Integer.toString(type_spd) + Integer.toString(exp_id);
					
					//reader
					WebDriver reader = new FirefoxDriver ();
					reader.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					reader.get (URL);
					WebElement readText;
					try {
						readText = reader.findElement(By.className("ace_content"));
					} catch (NoSuchElementException e) {
						reader.quit();
						continue;
					}
					
					//list of writer
					WebDriver writers[] = new WebDriver [n_user];
					WebElement writeTexts[] = new WebElement [n_user];
					for (int j = 0; j < n_user; j++) {
						writers [j] = new FirefoxDriver ();
						writers [j].manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						writers [j].get (URL);
						try {
							writeTexts [j] = writers[j].findElement(By.className("ace_text-input"));
						} catch (NoSuchElementException e) {
							for (int k = 0; k <= j; k++) {
								writers [j].quit ();
							}
							continue;
						}
					}
					
					//start writing and reading
					//dummy writing
					DummyWriter ts[] = null;
					if (n_user > 1) {
						ts = new DummyWriter [n_user - 1];
						for (int j = 1; j < n_user; j++) {
							ts[j - 1] = new DummyWriter(writeTexts[j], type_spd, j);
							ts[j - 1].start();
						}
					}
					
					//reader
					Reader r = new Reader (readText, type_spd, exp_id, n_user);
					r.start ();
					
					//writer
					Writer w = new Writer (writeTexts[0], type_spd, n_user, exp_id);
					w.start ();
					
					w.join();
					r.join();
					
					if (n_user > 1) {
						for (int j = 0; j < n_user - 1; j++) {
							ts[j].cancel ();
						}
					}
					
					Thread.sleep (5000);
					
					for (int j = 0; j < 10; j++) {
						writeTexts[0].sendKeys(Keys.chord(Keys.CONTROL, "a"));					
						writeTexts[0].sendKeys(Keys.BACK_SPACE);
					}
					
					Thread.sleep (30000);
					
					reader.quit ();
					for (int j = 0; j < n_user; j++) writers[j].quit();
					
					Thread.sleep (30000); 
				}
			}	
		}
		
	}

}
