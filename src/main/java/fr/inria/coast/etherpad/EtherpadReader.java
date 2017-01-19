/**
 * 
 */
package fr.inria.coast.etherpad;

import java.util.concurrent.TimeUnit;

import fr.inria.coast.general.CollaborativeReader;

/**
 * @author "Quang-Vinh DANG"
 *
 */
public class EtherpadReader extends CollaborativeReader {

	public EtherpadReader(int n_user, int type_spd, String DOC_URL, int exp_id) {
		super(n_user, type_spd, DOC_URL, exp_id);
		// TODO Auto-generated constructor stub
		while (this.inputElement == null) {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get(DOC_URL);
			this.inputElement = EtherpadHelper.getContentElement(driver);
		}
	}
}
