/**
 * 
 */
package fr.inria.coast.general;

import java.util.Random;

import org.openqa.selenium.Keys;
import org.openqa.selenium.remote.SessionNotFoundException;
import org.openqa.selenium.remote.UnreachableBrowserException;

/**
 * @author "Quang-Vinh DANG"
 *
 */
public class CollaborativeDummyWriter extends CollaborativeWriter {
	public boolean shouldWrite;

	public CollaborativeDummyWriter(int n_user, int type_spd, String docUrl,
			int exp_id) {
		super(n_user, type_spd, docUrl, exp_id);
		// TODO Auto-generated constructor stub
		shouldWrite = true;
		this.e = null;
	}
	char c = (char) ('a' + new Random ().nextInt(10));
	public void run () {
		while (shouldWrite) {
			dummyType ();
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e1) {
				//do not need to handle because interrupt means main writing and reading thread finish
				//System.out.println("Interruped while writing dummy text");
				return;
			}
		}
	}

	public void cancel () {
		try {
			shouldWrite = false;
			driver.quit ();
		} catch (SessionNotFoundException e) {
			System.out.println("Dummy Writer already quit");
		} catch (UnreachableBrowserException e) {
			System.out.println("Unreachable Browser expcetion at Dummy Writer");
		} catch (Exception e) {
			System.out.println("General exception at Dummy Writer");
		}
		interrupt();
	}
	
	public void dummyType () {
		try {
			int nextStep = new Random().nextInt () % 10;
			if (nextStep % 5 == 0) {
				this.e.sendKeys(Keys.DELETE);
			} else if (nextStep > 0) {
				for (int i = 0; i < nextStep; i++) {
					this.e.sendKeys(Keys.RIGHT);
				}
				this.e.sendKeys("" + c);
			} else if (nextStep < 0) {
				for (int i = nextStep; i < 0; i++) {
					this.e.sendKeys(Keys.LEFT);
				}
				this.e.sendKeys("" + c);
			}
		} catch (Exception e1) {
			System.out.println("Exception while sending Keys at Dummy Writer");
			e1.printStackTrace();
		}
		
	}
}
