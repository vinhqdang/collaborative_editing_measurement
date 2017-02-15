/**
 * 
 */
package fr.inria.coast.formic;

import java.util.logging.Logger;

import fr.inria.coast.general.CollaborativeAutomator;

/**
 * @author qdang
 *
 */
public class FormicAutomator extends CollaborativeAutomator {

	//redefine setting
	//protected String REMOTE_ADDR[] = {"152.81.15.203","152.81.3.91","152.81.15.71","152.81.12.192"};
	//protected int REMOTE_THREAD[] = {15,15,10,10};

	public FormicAutomator(int n_user, int type_spd, int exp_id,
			String docUrl, int textSize, String resultFile) {
		super(n_user, type_spd, exp_id, docUrl, textSize, resultFile);
		n_LocalThread = (n_user < threshold)?n_user:threshold;

		this.reader = new FormicReader(n_user, type_spd, docUrl, exp_id, textSize);
		String formicStringId = ((FormicReader)reader).getStringId();
		this.writer = new FormicWriter(n_user, type_spd, docUrl, exp_id, formicStringId, textSize);
		if (this.n_user > 1) {
			this.dummies = new FormicDummyWriter [n_user - 1];
			for (int i = 0; i < n_LocalThread - 1; i++) {
				this.dummies [i] = new FormicDummyWriter(n_user, type_spd, docUrl, exp_id, formicStringId) ;
			}
			if (n_user > threshold) {
				this.remoteDummies = new FormicRemoteDummyWriter [n_user - threshold];
				for (int i = 0; i < n_user - threshold; i++) {
					//depends on the number of requested clients, request from different remote server
					String remoteURL = remoteLastAddress;
					int sum = 0;
					for (int j = 0; j < remoteThreads.length; j++) {
						sum += remoteThreads [j];
						if (i < sum) {
							remoteURL = remoteAddresses [j];
							break;
						}
					}
					this.remoteDummies [i] = new FormicRemoteDummyWriter(n_user, type_spd, docUrl, exp_id, remoteURL, formicStringId);
				}
			}
		}
	}

	@Override
	public void run () {
		Logger.getLogger(this.getClass().getName()).info("Automator starting");
		//start dummy writer if needed
		if (n_user > 1) {
			//start local dummy writer
			for (int i = 0; i < n_LocalThread - 1; i++) {
				dummies [i].start();
			}

			if (n_user > threshold) {
				for (int i = 0; i < n_user - threshold; i++) {
					Logger.getLogger(getClass().getName()).info("Remote Nr. " + i);
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
			System.out.println("wait for reader to finish");
			reader.join(120000);
		} catch (InterruptedException e) {
			System.out.println("Interrupted while waiting the reader finish");
			e.printStackTrace();
			//if reader stops, finish all other thread and start a new loop
			
		}
		stopWriter();
		reader.cancel();
	}

	private void stopWriter() {
		Logger.getLogger(this.getClass().getName()).info("Stopping writer");
		//stop dummy threads if needed
		if (n_user > 1) {
			for (int i = 0; i < n_LocalThread - 1; i++) {
				dummies [i].cancel();
			}
			if (n_user > threshold) {
				for (int i = 0; i < n_user - threshold; i++) {
					remoteDummies[i].cancel();
				}
			}
		}
		//after reader finish, stop writer
		writer.cancel();
	}

}
