/**
 * 
 */
package fr.inria.coast.general;

import org.openqa.selenium.remote.SessionNotFoundException;
import org.openqa.selenium.remote.UnreachableBrowserException;

/**
 * @author "Quang-Vinh DANG"
 *
 */
public class CollaborativeDummyWriter extends CollaborativeWriter {
	boolean shouldWrite;

	public CollaborativeDummyWriter(int n_user, int type_spd, String DOC_URL,
			int exp_id) {
		super(n_user, type_spd, DOC_URL, exp_id);
		// TODO Auto-generated constructor stub
		shouldWrite = true;
		this.e = null;
	}
	public void run () {
		while (shouldWrite) {
			this.e.sendKeys("a");
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
}
