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

	private static final int TEXT_SIZE = 10;

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		int numberOfUsersPerRun[] = Helper.loadNumUsers("num_user_setting.txt");

		int[] lastExpInfo = Helper.loadLastExpInfo("last_exp_info.txt");

		int last_user = lastExpInfo[0];
		int last_type = lastExpInfo[1];
		int last_exp = lastExpInfo[2];

		String out_file = "formic.txt";

		for (int i = 0; i < numberOfUsersPerRun.length; i++) {
			// int n_user = n_users [n_users.length - 1 - i];
			int usersThisRun = numberOfUsersPerRun[i];
			for (int type_spd = 1; type_spd <= 10; type_spd++) {
				for (int exp_id = 1; exp_id < 5; exp_id++) {
					if (type_spd != 1 && type_spd % 2 != 0 && type_spd != 5) {
						System.out.println("Skipping type speed " + type_spd);
						continue;
					}
					// only test with type speed 1 with > 40 users
					if (usersThisRun > 40 && type_spd != 1)
						continue;

					// continue from last time
					if (usersThisRun < last_user || (usersThisRun == last_user && type_spd < last_type
							|| (usersThisRun == last_user && type_spd == last_type && exp_id <= last_exp)))
						continue;

					System.out.println("Running formic: " + usersThisRun + " " + type_spd + " " + exp_id);

					String documentUrl = "http://10.200.1.67:80/index"; // FIXME

					FormicAutomator automator = new FormicAutomator(usersThisRun, type_spd, exp_id, documentUrl,
							TEXT_SIZE, out_file);
					automator.run();
					try {
						int[] info = new int[3];
						info[0] = usersThisRun;
						info[1] = type_spd;
						info[2] = exp_id;
						Helper.saveLastExpInfo("last_exp_info.txt", info);
						System.out.println("Finished formic: " + usersThisRun + " " + type_spd + " " + exp_id);
						System.gc();
						Thread.sleep(30000);
					} catch (InterruptedException e) {
						System.out.println("Interrupted while sleeping in main thread");
						e.printStackTrace();
					}
				}
			}
		}

	}

}
