/**
 * 
 */
package fr.inria.coast.etherpad;

import java.util.UUID;

/**
 * @author "Quang-Vinh DANG"
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
		int last_user = 30;
		int last_type = 0;
		int last_exp = 0;

		for (int i = 0; i < n_users.length; i++) {
			int n_user = n_users [i];
			for (int type_spd = 1; type_spd <= 10; type_spd++) {
				for (int exp_id = 1; exp_id <= 10; exp_id += 1) {
					//continue from last time
					if (n_user < last_user || (type_spd < last_type || (type_spd == last_type && exp_id <= last_exp))) continue;
					
					System.out.println ("Running Etherpad: " + n_user + " " + type_spd + " " + exp_id);
					
					String DOC_URL = "http://152.81.3.91:9001/p/" +UUID.randomUUID().toString();

					EtherpadAutomator automator = new EtherpadAutomator(n_user, type_spd, exp_id, DOC_URL, 10, "etherpad.txt");
					automator. run();
					try {
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
