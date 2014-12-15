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
		// TODO Auto-generated method stub
		//int n_users[] = {1,2,5,10,15,20,25,30,40,50};
		int n_users[] = {5};

		// TODO fill the last experimental information here
		int last_user = 0;
		int last_type = 0;
		int last_exp = 0;

		for (int i = 0; i < n_users.length; i++) {
			int n_user = n_users [i];
			for (int type_spd = 1; type_spd <= 10; type_spd++) {
				for (int exp_id = 1; exp_id <= 3; exp_id += 1) {
					if (type_spd != 1 && type_spd % 2 != 0) {
						continue;
					}
					//only test with type speed 1 with 40 and 50 users
					if (n_user >= 40 && type_spd != 1) continue;
					//continue from last time
					if (n_user < last_user || (n_user == last_user && type_spd < last_type || (n_user == last_user && type_spd == last_type && exp_id <= last_exp))) continue;

					System.out.println ("Running Google Docs: " + n_user + " " + type_spd + " " + exp_id);
					
					String DOC_URLS[] = new String[3];

					DOC_URLS[0] = "https://docs.google.com/document/d/18zd6xh4uKT8NTaPoRndhONkJmT2Mo6-SLl1kYOp3G24/edit?usp=sharing";
					DOC_URLS[1] = "https://docs.google.com/document/d/1jraa1yiFzROSKP6SruMxuJvE4sbuGcmv9gnNOGGb2xU/edit?usp=sharing";
					DOC_URLS[2] = "https://docs.google.com/document/d/1_5WbMAy1DxZUop2-KE3vR0pNii4STZ_E_U6XOmK2CME/edit?usp=sharing";

					GoogleDocsAutomator automator = new GoogleDocsAutomator(n_user, type_spd, exp_id, DOC_URLS[exp_id%DOC_URLS.length], 10, "google_with_deletion.txt");
					automator. run();
					try {
						System.out.println("Finished Google: " + n_user + " " + type_spd + " " + exp_id);
						System.gc();
						if (exp_id == 5) {
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
