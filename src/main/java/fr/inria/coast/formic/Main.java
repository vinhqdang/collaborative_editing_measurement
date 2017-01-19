/**
 * 
 */
package fr.inria.coast.formic;

import java.io.IOException;

import fr.inria.coast.general.Helper;


/**
 * @author qdang
 *
 */
public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		int numberOfUsersPerRun[] = Helper.loadNumUsers("num_user_setting.txt");

		int [] lastExpInfo = Helper.loadLastExpInfo("last_exp_info.txt"); 
		
		int last_user = lastExpInfo[0];
		int last_type = lastExpInfo[1];
		int last_exp = lastExpInfo[2];
		
		String out_file = "googledocs.txt";
		
		for (int i = 0; i < numberOfUsersPerRun.length; i++) {
			//int n_user = n_users [n_users.length - 1 - i];
			int usersThisRun = numberOfUsersPerRun [i];
			for (int type_spd = 1; type_spd <= 10; type_spd++) {
				for (int exp_id = 1; exp_id < 5; exp_id++) {
					if (type_spd != 1 && type_spd % 2 != 0 && type_spd != 5) {
						System.out.println("Skipping type speed " + type_spd);
						continue;
					}
					//only test with type speed 1 with 40 and 50 users
					if (usersThisRun >= 40 && type_spd != 1) continue;
					
					//continue from last time
					if (usersThisRun < last_user || (usersThisRun == last_user && type_spd < last_type || (usersThisRun == last_user && type_spd == last_type && exp_id <= last_exp))) continue;

					System.out.println ("Running Google Docs: " + usersThisRun + " " + type_spd + " " + exp_id);
					
					String DOC_URLS[] = new String[5];

					DOC_URLS[0] = "https://docs.google.com/document/d/18zd6xh4uKT8NTaPoRndhONkJmT2Mo6-SLl1kYOp3G24/edit?usp=sharing";
					DOC_URLS[1] = "https://docs.google.com/document/d/1jraa1yiFzROSKP6SruMxuJvE4sbuGcmv9gnNOGGb2xU/edit?usp=sharing";
					DOC_URLS[2] = "https://docs.google.com/document/d/1_5WbMAy1DxZUop2-KE3vR0pNii4STZ_E_U6XOmK2CME/edit?usp=sharing";
					DOC_URLS[3] = "https://docs.google.com/document/d/11lKlnwp_EFebu8Yim1b38hkBcPtCx7sH2QsxrilvPIM/edit?usp=sharing";
					DOC_URLS[4] = "https://docs.google.com/document/d/1eRtWpIjEJ9FFnK4YFCeNCqA1_KVB_xldTAY5CI5yy5s/edit?usp=sharing";

					FormicAutomator automator = new FormicAutomator(usersThisRun, type_spd, exp_id, DOC_URLS[exp_id%DOC_URLS.length], 10, out_file);
					automator. run();
					try {
						int [] info = new int [3];
						info [0] = usersThisRun;
						info [1] = type_spd;
						info [2] = exp_id;
						Helper.saveLastExpInfo("last_exp_info.txt", info);
						System.out.println("Finished Google: " + usersThisRun + " " + type_spd + " " + exp_id);
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
