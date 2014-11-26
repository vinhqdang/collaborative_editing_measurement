/**
 * 
 */
package fr.inria.coast.googledocs;

import fr.inria.coast.general.CollaborativeAutomator;
import fr.inria.coast.mute2.MUTEDummyWriter;

/**
 * @author qdang
 *
 */
public class GoogleDocsAutomator extends CollaborativeAutomator {

	public GoogleDocsAutomator(int n_user, int type_spd, int exp_id,
			String DOC_URL, int TEXT_SIZE, String RESULT_FILE) {
		super(n_user, type_spd, exp_id, DOC_URL, TEXT_SIZE, RESULT_FILE);
		// TODO Auto-generated constructor stub
		this.reader = new GoogleDocsReader(n_user, type_spd, DOC_URL, exp_id);
		this.writer = new GoogleDocsWriter(n_user, type_spd, DOC_URL, exp_id);
		if (this.n_user > 1) {
			this.dummies = new MUTEDummyWriter [n_user - 1];
			for (int i = 0; i < n_user - 1; i++) {
				this.dummies [i] = new GoogleDocsDummyWriter(n_user, type_spd, DOC_URL, exp_id) ;
			}
		}
	}

}
