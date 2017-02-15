/**
 * 
 */
package fr.inria.coast.mute2;

import fr.inria.coast.general.CollaborativeAutomator;
import fr.inria.coast.googledocs.GoogleDocsRemoteDummyWriter;

/**
 * @author qdang
 *
 */
public class MUTEAutomator extends CollaborativeAutomator {
	
	//redefine setting
	protected String REMOTE_ADDR[] = {"152.81.15.203","152.81.15.71","152.81.12.192","152.81.7.44"};
	protected int REMOTE_THREAD[] = {10,10,15,25};

	public MUTEAutomator(int n_user, int type_spd, int exp_id, String DOC_URL,
			int TEXT_SIZE, String RESULT_FILE) {
		super(n_user, type_spd, exp_id, DOC_URL, TEXT_SIZE, RESULT_FILE);
		threshold = 0;
		n_LocalThread = (n_user < threshold)?n_user:threshold;
		
		this.reader = new MUTEReader(n_user, type_spd, DOC_URL, exp_id, TEXT_SIZE);
		this.writer = new MUTEWriter(n_user, type_spd, DOC_URL, exp_id, TEXT_SIZE);
		if (this.n_user > 1) {
			this.dummies = new MUTEDummyWriter [n_user - 1];
			for (int i = 0; i < n_LocalThread - 1; i++) {
				this.dummies [i] = new MUTEDummyWriter (n_user, type_spd, DOC_URL, exp_id);
			}
			if (n_user > threshold) {
				this.remoteDummies = new MUTERemoteDummyWriter [n_user - threshold];
				for (int i = 0; i < n_user - threshold; i++) {
					//depends on the number of requested clients, request from different remote server
					String remoteURL = remoteLastAddress;
					int sum = 0;
					for (int j = 0; j < REMOTE_THREAD.length; j++) {
						sum += REMOTE_THREAD [j];
						if (i < sum) {
							remoteURL = REMOTE_ADDR [j];
							break;
						}
					}
					this.remoteDummies [i] = new MUTERemoteDummyWriter(n_user, type_spd, DOC_URL, exp_id, remoteURL);
				}
			}
		}
	}

}
