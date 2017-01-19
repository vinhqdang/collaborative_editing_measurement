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
	 * loading the last experiment information, so we can start from the last
	 * time
	 */
	public static int[] loadLastExpInfo(String lastExpFileName) throws IOException {
		int num_user = 0;
		int type_spd = 0;
		int exp_id = 0;
		String line;
		BufferedReader expBufferedReader = null;
		try {
		File lastExpFile = new File(Helper.class.getResource("/"+lastExpFileName).toURI());
		FileReader expFileReader = new FileReader(lastExpFile);
		expBufferedReader = new BufferedReader(expFileReader);

		
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
			throw new RuntimeException(e);
		} finally {
			if(expBufferedReader != null) expBufferedReader.close();
		}
		int[] res = new int[3];
		res[0] = num_user;
		res[1] = type_spd;
		res[2] = exp_id;
		return res;
	}

	/*
	 * writing the last experiment info to the file, so we can load
	 */
	public static void saveLastExpInfo(String fileName, int[] info) throws IOException {
		if (info.length < 3)
			return;
		File file = new File(fileName);
		PrintWriter writer = new PrintWriter(file);
		writer.println("NUM_USER " + info[0]);
		writer.println("TYPE_SPD " + info[1]);
		writer.println("EXP_ID " + info[2]);
		writer.close();
	}

	/*
	 * load number of users
	 */
	public static int[] loadNumUsers(String fileName) throws IOException {
		File file;
		BufferedReader bufferedReader = null;
		try {
			file = new File(Helper.class.getResource("/"+fileName).toURI());
			FileReader fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			String line;
			if ((line = bufferedReader.readLine()) != null) {
				String[] tmp_res = line.split(" ");
				int[] res = new int[tmp_res.length];
				for (int i = 0; i < tmp_res.length; i++) {
					res[i] = Integer.parseInt(tmp_res[i]);
				}
				bufferedReader.close();
				return res;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if(bufferedReader != null) bufferedReader.close();
		}
		return null;

	}
}
