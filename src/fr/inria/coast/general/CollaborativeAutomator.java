package fr.inria.coast.general;


public class CollaborativeAutomator {
	public static int TEXT_SIZE;
	public static String RESULT_FILE = "result.txt";

	protected CollaborativeWriter writer;
	protected CollaborativeReader reader;
	protected CollaborativeDummyWriter dummies[];
	int type_spd;
	protected int n_user;
	int exp_id; //experimence ID
	String docURL;

	public CollaborativeAutomator (int n_user, int type_spd, int exp_id, String DOC_URL, int TEXT_SIZE, String RESULT_FILE) {
		this.n_user = n_user;
		this.type_spd = type_spd;
		this.exp_id = exp_id;
		this.docURL = DOC_URL;
		CollaborativeAutomator.TEXT_SIZE = TEXT_SIZE;
		CollaborativeAutomator.RESULT_FILE = RESULT_FILE;
		// TODO need to initialize reader and writer at subclass
	}

	public void run () {
		//start dummy writer if needed
		if (n_user > 1) {
			for (int i = 0; i <= n_user - 1; i++) {
				dummies [i].start();
			}
		}
		//start reader
		reader.start ();
		//start writer
		writer.start();

		//wait for reader finish
		//i.e, read all modification
		try {
			reader.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Interrupted while waiting the reader finish");
			e.printStackTrace();
		}

		//after reader finish, stop writer
		try {
			writer.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Interruped while waiting the writer finish");
			e.printStackTrace();
		}
		//stop dummy threads if needed
		if (n_user > 1) {
			for (int i = 0; i < n_user - 1; i++) {
				dummies [i].cancel();
			}
		}
	}
}
