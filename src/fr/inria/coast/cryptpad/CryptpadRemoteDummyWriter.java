/**
 * 
 */
package fr.inria.coast.cryptpad;

import java.util.concurrent.TimeUnit;

import fr.inria.coast.general.CollaborativeRemoteDummyWriter;

/**
 * @author qdang
 *
 */
public class CryptpadRemoteDummyWriter extends CollaborativeRemoteDummyWriter {

	public CryptpadRemoteDummyWriter(int n_user, int type_spd, String DOC_URL,
			int exp_id, String serverAddr) {
		super(n_user, type_spd, DOC_URL, exp_id, serverAddr);
		// TODO Auto-generated constructor stub
		while (this.e == null) {
			remoteDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			remoteDriver.get(DOC_URL);
			this.e = CryptpadHelper.getWebElement(remoteDriver);
		}
	}

}
