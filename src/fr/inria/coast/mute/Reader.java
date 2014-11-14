/**
 * 
 */
package fr.inria.coast.mute;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.openqa.selenium.WebElement;

/**
 * @author vinh
 *
 */
public class Reader extends Thread {
	
	long readTime[];
	WebElement e;
	boolean getChar [];
	int counter;
	int type_spd;
	int exp_id;
	int num_user;
	
	public Reader (WebElement e, int type_spd, int exp_id, int num_user) {
		this.e = e;
		readTime = new long [10];
		getChar = new boolean [10];
		for (int i = 0; i < 10; i++) {
			getChar [i] = false;
		}
		counter = 0;
		this.type_spd = type_spd;
		this.exp_id = exp_id;
		this.num_user = num_user;
	}
	@Override
	public void run () {
		while (true) {
			if (counter >= 5) {
				try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("result.txt", true)))) {
					for (int i = 0; i < 10; i++) {
						if (getChar[i] == true) {
							out.print("R ");
							out.print(num_user);
							out.print(" ");
							out.print(type_spd);
							out.print(" ");
							out.print(exp_id);
							out.print(" ");
							out.print("00" + Integer.toString(i));
							out.print(" ");
							out.print(readTime[i]);
							out.print("\n");
						}
					}
					out.print("---\n");
				}catch (IOException e) {
					System.out.println("Error while writing readTime");;
					e.printStackTrace();
				}
				return;
			}
			String content = e.getText();
			for (int i = 0; i < 10; i++) {
				if (getChar[i] == false) {
					String findText = "00" + Integer.toString(i);
					if (content.indexOf(findText) >= 0) {
						readTime [i] = System.currentTimeMillis();
						getChar [i] = true;
						counter += 1;
					}
				}
			}
		}
	}
}
