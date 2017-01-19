/**
 * 
 */
package fr.inria.coast.yjs;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

import fr.inria.coast.general.CollaborativeRemoteDummyWriter;

/**
 * @author score
 *
 */
public class YjsRemoteDummyWriter extends CollaborativeRemoteDummyWriter {

	public YjsRemoteDummyWriter(int n_user, int type_spd, String DOC_URL,
			int exp_id, String serverAddr) {
		super(n_user, type_spd, DOC_URL, exp_id, serverAddr);
		try {
			this.remoteDriver = new RemoteWebDriver(new URL ("http://" + serverAddr + ":4444/wd/hub"), capabilities);
		} catch (MalformedURLException e) {
			System.out.println("Cannot get remoted web driver at URL: " + serverAddr);
		}
		
		remoteDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		remoteDriver.get(DOC_URL);
		this.inputElement = remoteDriver.findElement(By.id("textfield"));
	}

}
