/**
 * 
 */
package fr.inria.coast.general;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Vinh DANG
 *
 */
public class Helper {
	/*
	 * loading the last experiment information, so we can start from the last time
	 */
	public static int[] loadLastExpInfo (String lastExpFileName) throws IOException {
		int num_user = 0;
		int type_spd = 0;
		int exp_id = 0;
		String line;
		File lastExpFile = new File (lastExpFileName);
		FileReader expFileReader = new FileReader (lastExpFile);
		BufferedReader expBufferedReader = new BufferedReader(expFileReader);

		try {
			while ((line = expBufferedReader.readLine()) != null) {
				String[] lines = line.split(" ");
				if (lines.length >= 2) {
					if (lines[0].toUpperCase().equals("NUM_USER") == true) {
						num_user = Integer.parseInt(lines[1]);
					}
					if (lines[0].toUpperCase().equals("TYPE_SPD") == true) {
						type_spd = Integer.parseInt(lines[1]);
					}
					if (lines[0].toUpperCase().equals("EXP_ID") == true) {
						exp_id = Integer.parseInt(lines[1]);
					}
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		expBufferedReader.close();
		int [] res = new int[3];
		res[0] = num_user;
		res[1] = type_spd;
		res[2] = exp_id;
		return res;
	}
	/*
	 * writing the last experiment info to the file, so we can load
	 */
	public static void saveLastExpInfo (String fileName, int[] info) throws IOException {
		if (info.length < 3) return;
		File file = new File (fileName);
		PrintWriter writer = new PrintWriter(file);
		writer.println("NUM_USER " + info[0]);
		writer.println("TYPE_SPD " + info[1]);
		writer.println("EXP_ID " + info[2]);
		writer.close ();
	}
	
	/*
	 * load number of users
	 */
	public static int[] loadNumUsers (String fileName) throws IOException {
		File file = new File (fileName);
		FileReader fileReader = new FileReader (file);
		BufferedReader bufferedReader = new BufferedReader (fileReader);
		String line;
		try {
			if ((line = bufferedReader.readLine()) != null) {
				String [] tmp_res = line.split (" ");
				int [] res = new int [tmp_res.length];
				for (int i = 0; i < tmp_res.length; i++) {
					res [i] = Integer.parseInt(tmp_res[i]);
				}
				bufferedReader.close ();
				return res;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
}
