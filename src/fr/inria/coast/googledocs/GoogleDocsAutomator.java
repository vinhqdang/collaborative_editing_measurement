/**
 * 
 */
package fr.inria.coast.googledocs;

import fr.inria.coast.general.CollaborativeAutomator;

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
			this.dummies = new GoogleDocsDummyWriter [n_LocalThread - 1];
			for (int i = 0; i < n_LocalThread - 1; i++) {
				this.dummies [i] = new GoogleDocsDummyWriter(n_user, type_spd, DOC_URL, exp_id) ;
			}
			if (n_user > THRESHOLD) {
				this.remoteDummies = new GoogleDocsRemoteDummyWriter [n_user - THRESHOLD];
				for (int i = 0; i < n_user - THRESHOLD; i++) {
					//depends on the number of requested clients, request from different remote server
					String remoteURL = REMOTE_LAST_ADDR;
					int sum = 0;
					for (int j = 0; j < REMOTE_THREAD.length; j++) {
						sum += REMOTE_THREAD [j];
						if (i < sum) {
							remoteURL = REMOTE_ADDR [j];
							break;
						}
					}
					this.remoteDummies [i] = new GoogleDocsRemoteDummyWriter(n_user, type_spd, DOC_URL, exp_id, remoteURL);
				}
			}
		}
	}

}
