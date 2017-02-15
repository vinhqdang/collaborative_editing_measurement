package fr.inria.coast.general;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import org.junit.Test;

public class HelperTest {

	@Test
	public void testSaveAndLoadLastExpInfo () throws IOException {
		int [] arr = new int[3];
		arr[0] = 10;
		arr[1] = 4;
		arr[2] = 2;
		Helper.saveLastExpInfo("helperTest.txt", arr);
		int [] read = Helper.loadLastExpInfo("helperTest.txt");
		assert (Arrays.equals(arr, read));
	}
	
	@Test
	public void testLoadNumUsers () throws IOException {
		String fileName = "helperTest2.txt";
		int[] write = {1, 5, 10, 30, 40, 32, 50, 4};
		File file = new File (fileName);
		PrintWriter writer = new PrintWriter(file);
		for (int i = 0; i < write.length; i++) {
			writer.print (write[i]);
			if (i < write.length - 1) writer.print (" ");
		}
		writer.close ();
		int [] read = Helper.loadNumUsers(fileName);
		assert (Arrays.equals(write, read));
	}

}
