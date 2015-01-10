/**
 * 
 */
package fr.inria.coast.general;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.SessionNotFoundException;

/**
 * @author "Quang-Vinh DANG"
 *
 */
public class CollaborativeReader extends Thread {
	protected
	WebDriver driver;
	int type_spd;
	int n_user;
	String docURL;
	int exp_id;

	//store the time of reading the modification
	long readTime [];
	boolean getChar [];
	int counter;

	protected WebElement e;
	
	//to indicate write the result to file or not
	protected boolean didWrite = false;

	public CollaborativeReader (int n_user, int type_spd, String DOC_URL, int exp_id) {
		this.n_user = n_user;
		this.type_spd = type_spd;
		this.docURL = DOC_URL;
		this.exp_id = exp_id;
		this.e = null;

		readTime = new long [CollaborativeAutomator.TEXT_SIZE];
		getChar = new boolean [CollaborativeAutomator.TEXT_SIZE];
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
				content = e.getText();
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

	public void cancel () {
		try {
			if (didWrite == false) {
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
			}
			driver.quit ();
		} catch (SessionNotFoundException e) {
			System.out.println("Reader already quit");
		}
		interrupt();
	}
}
