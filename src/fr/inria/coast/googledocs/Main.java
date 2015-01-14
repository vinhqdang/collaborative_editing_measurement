/**
 * 
 */
package fr.inria.coast.googledocs;


/**
 * @author qdang
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int n_users[] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,32,34,36,38,40,45,50};

		// TODO fill the last experimental information here
		int last_user = 10;
		int last_type = 1;
		int last_exp = 2;
		
		String out_file = "google_full_1_thread.txt";

		for (int i = 0; i < n_users.length; i++) {
			//int n_user = n_users [n_users.length - 1 - i];
			int n_user = n_users [i];
			for (int type_spd = 1; type_spd <= 10; type_spd++) {
				for (int exp_id = 1; exp_id <= 10; exp_id += 1) {
					if (type_spd != 1 && type_spd % 2 != 0 && type_spd != 5) {
						continue;
					}
					//only test with type speed 1 with 40 and 50 users
					if (n_user >= 40 && type_spd != 1) continue;
					
					//continue from last time
					if (n_user < last_user || (n_user == last_user && type_spd < last_type || (n_user == last_user && type_spd == last_type && exp_id <= last_exp))) continue;

					System.out.println ("Running Google Docs: " + n_user + " " + type_spd + " " + exp_id);
					
					String DOC_URLS[] = new String[5];

					DOC_URLS[0] = "https://docs.google.com/document/d/18zd6xh4uKT8NTaPoRndhONkJmT2Mo6-SLl1kYOp3G24/edit?usp=sharing";
					DOC_URLS[1] = "https://docs.google.com/document/d/1jraa1yiFzROSKP6SruMxuJvE4sbuGcmv9gnNOGGb2xU/edit?usp=sharing";
					DOC_URLS[2] = "https://docs.google.com/document/d/1_5WbMAy1DxZUop2-KE3vR0pNii4STZ_E_U6XOmK2CME/edit?usp=sharing";
					DOC_URLS[3] = "https://docs.google.com/document/d/11lKlnwp_EFebu8Yim1b38hkBcPtCx7sH2QsxrilvPIM/edit?usp=sharing";
					DOC_URLS[4] = "https://docs.google.com/document/d/1eRtWpIjEJ9FFnK4YFCeNCqA1_KVB_xldTAY5CI5yy5s/edit?usp=sharing";

					GoogleDocsAutomator automator = new GoogleDocsAutomator(n_user, type_spd, exp_id, DOC_URLS[exp_id%DOC_URLS.length], 10, out_file);
					automator. run();
					try {
						System.out.println("Finished Google: " + n_user + " " + type_spd + " " + exp_id);
						System.gc();
						if (exp_id % 5 == 0) {
							Thread.sleep(150000);
						} else {
							Thread.sleep(60000);
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						System.out.println("Interrupted while sleeping in main thread");
						e.printStackTrace();
					}
				}
			}
		}
		
		
	}

}
