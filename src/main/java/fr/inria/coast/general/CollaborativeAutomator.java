package fr.inria.coast.general;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CollaborativeAutomator {
	public static String resultFile = "result.txt";

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
	protected int threshold;

	//maximum number of threads run on each remote machine
	protected int thresholdRemote = 15;
	//number of thread running in local
	//other will run remotely
	protected int n_LocalThread;

	//remote settings
	//protected String REMOTE_ADDR[] = {"152.81.2.28","152.81.15.203","152.81.15.71","152.81.12.192"};
	//protected int REMOTE_THREAD[] = {10,15,5,10};
	protected String remoteAddresses [];
	protected int remoteThreads [];

	//in case the number of requested exceed the preparation: this server will take care all the remaining request
	//protected final String REMOTE_LAST_ADDR = "152.81.12.192";
	protected String remoteLastAddress;

	protected final int textSize;

	public CollaborativeAutomator (int n_user, int type_spd, int exp_id, String docUrl, int textSize, String resultFile) {
		if (OSValidator.isWindows()) {
			System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		}

		try {
			this.readConfigFile("selenium_config.txt");
		} catch (IOException e) {
			System.out.println("Error when reading the config file.");
		}
		this.n_user = n_user;
		this.type_spd = type_spd;
		this.exp_id = exp_id;
		this.docURL = docUrl;
		this.textSize = textSize;
		CollaborativeAutomator.resultFile = resultFile;				
		n_LocalThread = (n_user < threshold)?n_user:threshold;
	}

	public void run () {
		//start dummy writer if needed
		if (n_user > 1) {
			//start local dummy writer
			for (int i = 0; i < n_LocalThread - 1; i++) {
				dummies [i].start();
			}

			if (n_user > threshold) {
				for (int i = 0; i < n_user - threshold; i++) {
					System.out.println("Start remote dummy: " + i);
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
			System.out.println("Interrupted while waiting the reader finish");
			e.printStackTrace();
			//if reader stops, finish all other thread and start a new loop
			writer.cancel();
			if (n_user > 1) {
				for (int i = 0; i < n_user - 1; i++) {
					dummies [i].cancel();
				}
				if (n_user > threshold) {
					for (int i = 0; i < n_user - threshold; i++) {
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
			if (n_user > threshold) {
				for (int i = 0; i < n_user - threshold; i++) {
					remoteDummies[i].cancel();
				}
			}
		}

		//after reader finish, stop writer
		writer.cancel();
	}

	public void readConfigFile (String configFileName) throws IOException {
		//read the settings from file
		String configLine;
		int numOfLines = 1024;
		String[] remoteAddressesTmp = new String [numOfLines];
		int[] remoteThreadsTmp = new int [numOfLines];
		BufferedReader configBufferedReader = null;
		int count = 0;
		try {

			InputStreamReader s = new InputStreamReader(Helper.class.getResourceAsStream("/"+configFileName));
			configBufferedReader = new BufferedReader(s);
	
			
			remoteAddresses = new String [numOfLines];
			remoteThreads = new int [numOfLines];
	
			while ((configLine = configBufferedReader.readLine()) != null) {
				String[] lines = configLine.split(" ");
				if (lines.length >= 2) {
					if (lines[0].toUpperCase().equals("LOCAL") == true) {
						threshold = Integer.parseInt(lines[1]);
					}
					else {
						remoteAddressesTmp [count] = lines [0];
						int _numThread = Integer.parseInt(lines[1]);
						remoteThreadsTmp [count] = _numThread;
						count++;
					}
				}
				else if (lines.length == 1) {
					remoteLastAddress = lines [0];
				}

			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if(configBufferedReader != null) configBufferedReader.close();
		}
		for (int i = 0; i < count; i++) {
			remoteAddresses [i] = remoteAddressesTmp[i];
			remoteThreads [i] = remoteThreadsTmp [i];
		}
	}
}
