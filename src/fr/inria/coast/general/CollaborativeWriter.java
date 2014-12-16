/**
 * 
 */
package fr.inria.coast.general;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.SessionNotFoundException;

/**
 * @author "Quang-Vinh DANG"
 *
 */
public class CollaborativeWriter extends Thread {
	protected WebDriver driver;
	protected int type_spd;
	protected int n_user;
	String docURL;
	protected int exp_id;
	protected int delay;

	//store the time of writing the modification
	protected long writeTime [];
	protected int counter;

	protected WebElement e;

	public CollaborativeWriter(int n_user, int type_spd, String DOC_URL,
			int exp_id) {
		// TODO Auto-generated constructor stub
		this.n_user = n_user;
		this.type_spd = type_spd;
		this.delay = 1000 / this.type_spd;
		this.docURL = DOC_URL;
		this.exp_id = exp_id;
		this.e = null;

		writeTime = new long [CollaborativeAutomator.TEXT_SIZE];
	}

	@Override
	public void run () {
		for (counter = 0; counter < CollaborativeAutomator.TEXT_SIZE; counter++) {
			writeTime[counter] = System.currentTimeMillis();
			e.sendKeys(String.format ("%03d", counter) + "x");
			try {
				sleep(delay);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(CollaborativeAutomator.RESULT_FILE, true)))) {
			for (int i = 0; i < CollaborativeAutomator.TEXT_SIZE; i++) {
				out.print("W ");
				out.print(n_user);
				out.print(" ");
				out.print(type_spd);
				out.print(" ");
				out.print(exp_id);
				out.print(" ");
				out.print(String.format ("%03d", i));
				out.print(" ");				
				out.print(writeTime[i]);
				out.print("\n");
			}		
			out.println("***");
		}catch (IOException e) {
			System.out.println ("Error while writing the write time");
			e.printStackTrace();
		}

		try {
			//clear the content
			System.out.println("Clear content");
			e.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			e.sendKeys(Keys.DELETE);
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				System.out.println("Interrupted while waiting to clear content");
			}
			driver.quit ();
		} catch (SessionNotFoundException e) {
			System.out.println("Writer already quit");
		} catch (Exception e) {
			//TODO add exception handler
		}
	}

	public void cancel () {
		//stop writing
		if (counter < CollaborativeAutomator.TEXT_SIZE - 1) counter = CollaborativeAutomator.TEXT_SIZE - 1;
		//clear the content
		try {
			e.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			e.sendKeys(Keys.DELETE);
		} catch (SessionNotFoundException e) {
			System.out.println("Writer already quit");
		}
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			System.out.println("Interrupted while waiting to clear content");
		}
		try {
			driver.quit ();
		} catch (SessionNotFoundException e) {
			System.out.println("Writer already quit");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Quit writer");
		}
		interrupt();
	}
}
