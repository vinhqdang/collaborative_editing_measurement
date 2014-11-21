/**
 * 
 */
package fr.inria.coast.general;

/**
 * @author "Quang-Vinh DANG"
 *
 */
public class CollaborativeDummyWriter extends CollaborativeWriter {

	public CollaborativeDummyWriter(int n_user, int type_spd, String DOC_URL,
			int exp_id) {
		super(n_user, type_spd, DOC_URL, exp_id);
		// TODO Auto-generated constructor stub
	}
	public void run () {
		for (int i = 0; i < 2000; i++) {
			this.e.sendKeys("a");
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e1) {
				System.out.println("Interruped while writing dummy text");
				return;
			}
		}
	}
	
	public void canncel () {
		if (!driver.toString().contains("null")) {
			driver.quit ();
		}
		interrupt();
	}
}
