/**
 * 
 */
package fr.inria.coast.googledocs;

import java.util.UUID;

import fr.inria.coast.mute2.MUTEAutomator;

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
		int n_users[] = {1,2,5,10,20,30,40,50};

		// TODO fill the last experimental information here
		int last_user = 0;
		int last_type = 0;
		int last_exp = 0;

		for (int i = 0; i < n_users.length; i++) {
			int n_user = n_users [i];
			for (int type_spd = 1; type_spd <= 10; type_spd++) {
				for (int exp_id = 1; exp_id <= 5; exp_id += 1) {
					//continue from last time
					if (n_user < last_user || (n_user == last_user && type_spd < last_type || (type_spd == last_type && exp_id <= last_exp))) continue;

					System.out.println ("Running Google Docs: " + n_user + " " + type_spd + " " + exp_id);

					String DOC_URL = "https://docs.google.com/document/d/1UmEc42z6bAD95fvdU5zTdv4ODcgu3LDpC9lDNJgjzGg/edit?usp=sharing";

					GoogleDocsAutomator automator = new GoogleDocsAutomator(n_user, type_spd, exp_id, DOC_URL, 10, "googleDocs.txt");
					automator. run();
					try {
						System.out.println("Finished");
						System.gc();
						Thread.sleep(45000);
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