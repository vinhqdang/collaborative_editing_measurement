/**
 * 
 */
package fr.inria.coast.etherpad;

import fr.inria.coast.general.CollaborativeDummyWriter;

/**
 * @author "Quang-Vinh DANG"
 *
 */
public class EtherpadDummyWriter extends CollaborativeDummyWriter {

	public EtherpadDummyWriter(int n_user, int type_spd, String DOC_URL,
			int exp_id) {
		super(n_user, type_spd, DOC_URL, exp_id);
		// TODO Auto-generated constructor stub
		this.e = EtherpadHelper.getContentElement(driver);
	}

}
