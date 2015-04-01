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

	//redefine setting
	//protected String REMOTE_ADDR[] = {"152.81.15.203","152.81.3.91","152.81.15.71","152.81.12.192"};
	//protected int REMOTE_THREAD[] = {15,15,10,10};

	public GoogleDocsAutomator(int n_user, int type_spd, int exp_id,
			String DOC_URL, int TEXT_SIZE, String RESULT_FILE) {
		super(n_user, type_spd, exp_id, DOC_URL, TEXT_SIZE, RESULT_FILE);
		THRESHOLD = 0;
		n_LocalThread = (n_user < THRESHOLD)?n_user:THRESHOLD;

		this.reader = new GoogleDocsReader(n_user, type_spd, DOC_URL, exp_id);
		this.writer = new GoogleDocsWriter(n_user, type_spd, DOC_URL, exp_id);
		if (this.n_user > 1) {
			this.dummies = new GoogleDocsDummyWriter [n_user - 1];
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

	@Override
	public void run () {
		//start dummy writer if needed
		if (n_user > 1) {
			//start local dummy writer
			for (int i = 0; i < n_LocalThread - 1; i++) {
				dummies [i].start();
			}

			if (n_user > THRESHOLD) {
				for (int i = 0; i < n_user - THRESHOLD; i++) {
					remoteDummies[i].start();
				}
			}
		}
		//start reader
		//reader.setPriority(Thread.MAX_PRIORITY);
		reader.start ();

		//wait for synchronization
		/*
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			System.out.println("Interrupted while waiting for synchronization before start");
			e1.printStackTrace();
		}
		 */
		//start writer
		//writer.setPriority(Thread.MAX_PRIORITY - 1);
		writer.start();

		//wait for reader finish
		//i.e, read all modification
		try {
			reader.join(120000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Interrupted while waiting the reader finish");
			e.printStackTrace();
			//if reader stops, finish all other thread and start a new loop
			writer.cancel();
			if (n_user > 1) {
				for (int i = 0; i < n_user - 1; i++) {
					dummies [i].cancel();
				}
				if (n_user > THRESHOLD) {
					for (int i = 0; i < n_user - THRESHOLD; i++) {
						remoteDummies[i].cancel();
					}
				}
			}
			return;
		}

		//stop dummy threads if needed
		if (n_user > 1) {
			for (int i = 0; i < n_LocalThread - 1; i++) {
				dummies [i].cancel();
			}
			if (n_user > THRESHOLD) {
				for (int i = 0; i < n_user - THRESHOLD; i++) {
					remoteDummies[i].cancel();
				}
			}
		}

		//after reader finish, stop writer
		writer.cancel();
	}

}
