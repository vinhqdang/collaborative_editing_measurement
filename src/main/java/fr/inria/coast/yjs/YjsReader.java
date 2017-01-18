/**
 * 
 */
package fr.inria.coast.yjs;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.SessionNotFoundException;

import fr.inria.coast.general.CollaborativeAutomator;
import fr.inria.coast.general.CollaborativeReader;

/**
 * @author Quang Vinh DANG
 *
 */
public class YjsReader extends CollaborativeReader {

	public YjsReader(int n_user, int type_spd, String DOC_URL, int exp_id) {
		super(n_user, type_spd, DOC_URL, exp_id);
		driver = new ChromeDriver();
		while (this.e == null) {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get(DOC_URL);
			this.e = driver.findElement(By.id("textfield"));
		}
	}
	
	@Override
	public void run () {
		while (true) {
			if (counter >= CollaborativeAutomator.TEXT_SIZE / 2) {
				try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(CollaborativeAutomator.RESULT_FILE, true)))) {
					for (int i = 0; i < CollaborativeAutomator.TEXT_SIZE; i++) {
						if (getChar[i] == true) {
							out.print("R ");
							out.print(n_user);
							out.print(" ");
							out.print(type_spd);
							out.print(" ");
							out.print(exp_id);
							out.print(" ");
							out.print(String.format ("%03d", i));
							out.print(" ");
							out.print(readTime[i]);
							out.print("\n");
						}
					}
					out.print("---\n");
					didWrite = true;
				}catch (IOException e) {
					System.out.println("Error while writing readTime");;
					e.printStackTrace();
				}
				try {
					driver.quit ();
				} catch (SessionNotFoundException e) {
					System.out.println("Reader already quit");
				}
				return;
			}
			String content = null;
			try {
				content = e.getAttribute("value");
			} catch (StaleElementReferenceException e) {
				System.out.println("StaleElementReferenceException at Reader");
				e.printStackTrace();
				this.cancel();
			}
			long currentTime = System.currentTimeMillis();
			for (int i = 0; i < 10; i++) {
				if (getChar[i] == false) {
					String findText = "00" + Integer.toString(i);
					if (content.indexOf(findText) >= 0) {
						readTime [i] = currentTime;
						getChar [i] = true;
						counter += 1;
					}
				}
			}
		}
	}

}
