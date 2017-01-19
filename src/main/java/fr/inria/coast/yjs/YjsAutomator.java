/**
 * 
 */
package fr.inria.coast.yjs;

import fr.inria.coast.general.CollaborativeAutomator;
import fr.inria.coast.mute2.MUTEDummyWriter;
import fr.inria.coast.mute2.MUTEReader;
import fr.inria.coast.mute2.MUTERemoteDummyWriter;
import fr.inria.coast.mute2.MUTEWriter;

/**
 * @author score
 *
 */
public class YjsAutomator extends CollaborativeAutomator {

	//redefine setting
		protected String REMOTE_ADDR[] = {"152.81.13.132","152.81.15.71","152.81.12.192","152.81.7.44"};
		protected int REMOTE_THREAD[] = {20,20,20,25};

		public YjsAutomator(int n_user, int type_spd, int exp_id, String DOC_URL,
				int TEXT_SIZE, String RESULT_FILE) {
			super(n_user, type_spd, exp_id, DOC_URL, TEXT_SIZE, RESULT_FILE);
			threshold = 5;
			n_LocalThread = (n_user < threshold)?n_user:threshold;
			
			this.reader = new YjsReader(n_user, type_spd, DOC_URL, exp_id);
			this.writer = new YjsWriter(n_user, type_spd, DOC_URL, exp_id);
			if (this.n_user > 1) {
				this.dummies = new YjsDummyWriter [n_user - 1];
				for (int i = 0; i < n_LocalThread - 1; i++) {
					this.dummies [i] = new YjsDummyWriter (n_user, type_spd, DOC_URL, exp_id);
				}
				if (n_user > threshold) {
					this.remoteDummies = new YjsRemoteDummyWriter [n_user - threshold];
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
						this.remoteDummies [i] = new YjsRemoteDummyWriter(n_user, type_spd, DOC_URL, exp_id, remoteURL);
					}
				}
			}
		}

}
