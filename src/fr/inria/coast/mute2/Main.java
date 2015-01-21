/**
 * 
 */
package fr.inria.coast.mute2;

import java.util.UUID;

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
		//int n_users[] = {50,45,40,38,36,34,32,30,29,28,27,26,25,24,23,22,21,20,19,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1};
		int n_users[] = {50,45,40,38,36,34,32,30,25,20,15,10,5,1};
		//int n_users[] = {3};
		//int n_users[] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,32,34,36,38,40,45,50};
		// TODO fill the last experimental information here
		int last_user = 30;
		int last_type = 4;
		int last_exp = 1;

		for (int i = 0; i < n_users.length; i++) {
			int n_user = n_users [i];
			for (int type_spd = 1; type_spd <= 10; type_spd++) {
				if (type_spd % 2 != 0 && type_spd != 1 && type_spd != 5) continue;
				if (n_user >= 50 && type_spd != 1) continue;
				for (int exp_id = 1; exp_id <= 1; exp_id += 1) {
					//continue from last time
					//if (n_user < last_user || (n_user == last_user && type_spd < last_type || (n_user == last_user && type_spd == last_type && exp_id <= last_exp))) continue;
					if (n_user > last_user || (n_user == last_user && type_spd < last_type || (n_user == last_user && type_spd == last_type && exp_id <= last_exp))) continue;
					
					System.out.println ("Running MUTE: " + n_user + " " + type_spd + " " + exp_id);

					String DOC_URL = "http://152.81.3.91:8080/doc/" +UUID.randomUUID().toString();

					MUTEAutomator automator = new MUTEAutomator(n_user, type_spd, exp_id, DOC_URL, 10, "mute_with_deletion_full_02.txt");
					automator. run();
					try {
						System.out.println("Finished MUTE: " + n_user + " " + type_spd + " " + exp_id);
						System.gc();
						Thread.sleep(15000);
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
