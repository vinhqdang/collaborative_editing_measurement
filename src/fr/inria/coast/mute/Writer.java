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
public class Writer extends Thread {
	WebElement e;
	int type_spd;
	int delay;
	long writeTime [];
	int num_user;
	int exp_id;
	public Writer (WebElement e, int type_spd, int num_user, int exp_id) {
		this.e = e;
		this.type_spd = type_spd;
		this.delay = 1000 / type_spd;
		writeTime = new long [10];
		this.num_user = num_user;
		this.exp_id = exp_id;
	}
	@Override
	public void run () {
		for (int i = 0; i < 10; i++) {
			writeTime[i] = System.currentTimeMillis();
			e.sendKeys("00" + Integer.toString(i) + "x");
			try {
				sleep(delay);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("result.txt", true)))) {
			for (int i = 0; i < 10; i++) {
				String writeText = "00" + Integer.toString(i);
				out.print("W ");
				out.print(num_user);
				out.print(" ");
				out.print(type_spd);
				out.print(" ");
				out.print(exp_id);
				out.print(" ");
				out.print(writeText);
				out.print(" ");				
				out.print(writeTime[i]);
				out.print("\n");
			}		
			out.println("***");
		}catch (IOException e) {
			System.out.println ("Error while writing the write time");
			e.printStackTrace();
		}
	}
}
