package fr.inria.coast.general;


public class CollaborativeAutomator {
	public static int TEXT_SIZE;
	public static String RESULT_FILE = "result.txt";

	protected CollaborativeWriter writer;
	protected CollaborativeReader reader;
	protected CollaborativeDummyWriter dummies[];
	protected CollaborativeRemoteDummyWriter remoteDummies[];
	int type_spd;
	protected int n_user;
	int exp_id; //experimence ID
	String docURL;
	
	//limit number of thread can run in a host
	//if more, need to switch to remote driver
	protected int THRESHOLD;
	
	//maximum number of threads run on each remote machine
	protected int THRESHOLD_REMOTE = 15;
	//number of thread running in local
	//other will run remotely
	protected int n_LocalThread;
	
	//remote settings
	//from first to last: HP Z400, HP Z400, Dell Workstation Core 2 Duo
	protected String REMOTE_ADDR[] = {"152.81.2.28","152.81.15.203","152.81.15.71","152.81.12.192"};
	protected int REMOTE_THREAD[] = {10,15,5,10};
	
	//in case the number of requested exceed the preparation: this server will take care all the remaining request
	protected final String REMOTE_LAST_ADDR = "152.81.12.192";

	public CollaborativeAutomator (int n_user, int type_spd, int exp_id, String DOC_URL, int TEXT_SIZE, String RESULT_FILE) {
		if (OSValidator.isWindows()) {
			System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		}
		
		this.n_user = n_user;
		this.type_spd = type_spd;
		this.exp_id = exp_id;
		this.docURL = DOC_URL;
		CollaborativeAutomator.TEXT_SIZE = TEXT_SIZE;
		CollaborativeAutomator.RESULT_FILE = RESULT_FILE;
		n_LocalThread = (n_user < THRESHOLD)?n_user:THRESHOLD;
	}

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
		reader.setPriority(Thread.MAX_PRIORITY);
		reader.start ();
		
		//wait for synchronization
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			System.out.println("Interrupted while waiting for synchronization before start");
			e1.printStackTrace();
		}
		//start writer
		writer.setPriority(Thread.MAX_PRIORITY - 1);
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
