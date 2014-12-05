/**
 * 
 */
package fr.inria.coast.cryptpad;


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
		int n_users[] = {1,2,5,10,15,20,25,30,40,50};

		// TODO fill the last experimental information here
		int last_user = 20;
		int last_type = 10;
		int last_exp = 3;
		
		int doc_name = 2000;

		for (int i = 0; i < n_users.length; i++) {
			int n_user = n_users [i];
			for (int type_spd = 1; type_spd <= 10; type_spd++) {
				if (type_spd % 2 != 0 && type_spd != 1) continue;
				//if (n_user >= 40 && type_spd != 1) continue;
				for (int exp_id = 1; exp_id <= 3; exp_id += 1) {
					//continue from last time
					if (n_user < last_user || (n_user == last_user && type_spd < last_type || (n_user == last_user && type_spd == last_type && exp_id <= last_exp))) continue;

					System.out.println ("Running Cryptpad: " + n_user + " " + type_spd + " " + exp_id);

					String DOC_URL = "http://152.81.3.91:3000/#" + Integer.toString(doc_name++);

					CryptpadAutomator automator = new CryptpadAutomator(n_user, type_spd, exp_id, DOC_URL, 10, "cryptpad_deletion.txt");
					automator. run();
					try {
						System.out.println("Finished cryptpad:" + n_user + " " + type_spd + " " + exp_id + "" + doc_name);
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
