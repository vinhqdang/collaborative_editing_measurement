/**
 * 
 */
package fr.inria.coast.mute;

import org.openqa.selenium.WebElement;

/**
 * @author vinh
 *
 */
public class DummyWriter extends Thread
{
	int delay;
	int id;
	WebElement e;
	
	public DummyWriter (WebElement e, int type_spd, int id) {
		this.e = e;
		this.delay = 1000 / type_spd;
		this.id = id;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 2000; i++) {
			e.sendKeys("a");
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
				System.out.println ("Thread " + id + " quit");
				return;
			}
		}
	}
	
	//quit thread
	public void cancel() { interrupt(); }	
}