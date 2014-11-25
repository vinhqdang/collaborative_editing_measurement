/**
 * 
 */
package fr.inria.coast.mute2;

import fr.inria.coast.general.CollaborativeAutomator;

/**
 * @author qdang
 *
 */
public class MUTEAutomator extends CollaborativeAutomator {

	public MUTEAutomator(int n_user, int type_spd, int exp_id, String DOC_URL,
			int TEXT_SIZE, String RESULT_FILE) {
		super(n_user, type_spd, exp_id, DOC_URL, TEXT_SIZE, RESULT_FILE);
		// TODO Auto-generated constructor stub
		this.reader = new MUTEReader(n_user, type_spd, DOC_URL, exp_id);
		this.writer = new MUTEWriter(n_user, type_spd, DOC_URL, exp_id);
		if (this.n_user > 1) {
			this.dummies = new MUTEDummyWriter [n_user - 1];
			for (int i = 0; i < n_user - 1; i++) {
				this.dummies [i] = new MUTEDummyWriter (n_user, type_spd, DOC_URL, exp_id);
			}
		}
	}

}
