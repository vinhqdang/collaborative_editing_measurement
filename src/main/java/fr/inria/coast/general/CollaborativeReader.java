/**
 * 
 */
package fr.inria.coast.general;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.SessionNotFoundException;

/**
 * @author "Quang-Vinh DANG"
 *
 */
public class CollaborativeReader extends Thread {
	
	protected WebDriver driver;
	protected int type_spd;
	protected int n_user;
	protected String docURL;
	protected int exp_id;

	// store the time of reading the modification
	protected long readTime[];
	protected boolean getChar[];
	protected int counter;

	protected WebElement inputElement;

	// to indicate write the result to file or not
	protected boolean didWrite = false;
	protected final int textSize;

	protected static final Logger LOG = Logger.getLogger(CollaborativeReader.class.getName());

	public CollaborativeReader(int n_user, int type_spd, String docUrl, int exp_id, int textSize) {
		this.n_user = n_user;
		this.type_spd = type_spd;
		this.docURL = docUrl;
		this.exp_id = exp_id;
		this.inputElement = null;
		this.textSize = textSize;

		readTime = new long[textSize];
		getChar = new boolean[textSize];
	}

	@Override
	public void run() {
		LOG.info("Reader starting");
		while (true) {
			if (counter >= textSize) {
				try (PrintWriter out = new PrintWriter(
						new BufferedWriter(new FileWriter(CollaborativeAutomator.resultFile, true)))) {
					for (int i = 0; i < textSize; i++) {
						if (getChar[i] == true) {
							out.print("R ");
							out.print(n_user);
							out.print(" ");
							out.print(type_spd);
							out.print(" ");
							out.print(exp_id);
							out.print(" ");
							out.print(String.format("%03d", i));
							out.print(" ");
							out.print(readTime[i]);
							out.print("\n");
						}
					}
					out.print("---\n");
					didWrite = true;
				} catch (IOException e) {
					System.out.println("Error while writing readTime");
					e.printStackTrace();
				}
				try {
					driver.quit();
				} catch (SessionNotFoundException e) {
					System.out.println("Reader already quit");
				}
				return;
			}
			String content = null;
			try {
				content = inputElement.getAttribute("value");
			} catch (StaleElementReferenceException e) {
				System.out.println("StaleElementReferenceException at Reader");
				e.printStackTrace();
				this.cancel();
			}
			long currentTime = System.currentTimeMillis();
			for (int i = 0; i < textSize; i++) {
				if (getChar[i] == false) {
					String findText = Integer.toString(i);
					LOG.info("Searching for: " + findText + " in text: " + content);
					if (content.indexOf(findText) >= 0) {
						LOG.info("Found " + findText);
						readTime[i] = currentTime;
						getChar[i] = true;
						counter += 1;
					}
				}
			}
		}
	}

	public void cancel() {
		try {
			LOG.info("Stopping reader");
			if (didWrite == false) {
				try (PrintWriter out = new PrintWriter(
						new BufferedWriter(new FileWriter(CollaborativeAutomator.resultFile, true)))) {
					for (int i = 0; i < textSize; i++) {
						if (getChar[i] == true) {
							out.print("R ");
							out.print(n_user);
							out.print(" ");
							out.print(type_spd);
							out.print(" ");
							out.print(exp_id);
							out.print(" ");
							out.print(String.format("%03d", i));
							out.print(" ");
							out.print(readTime[i]);
							out.print("\n");
						}
					}
					out.print("---\n");
					didWrite = true;
				} catch (IOException e) {
					System.out.println("Error while writing readTime");
					e.printStackTrace();
				}
			}
			driver.quit();
		} catch (SessionNotFoundException e) {
			System.out.println("Reader already quit");
		}
		interrupt();
	}
}
