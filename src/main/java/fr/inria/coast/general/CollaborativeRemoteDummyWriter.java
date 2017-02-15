/**
 * 
 */
package fr.inria.coast.general;

import java.util.logging.Logger;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionNotFoundException;
import org.openqa.selenium.remote.UnreachableBrowserException;

/**
 * @author qdang
 *
 */
public class CollaborativeRemoteDummyWriter extends CollaborativeDummyWriter{
	
	protected static final Logger LOG = Logger.getLogger(CollaborativeRemoteDummyWriter.class.getName());
	
	//address of Selenium Server
	protected String serverAddr;
	protected DesiredCapabilities capabilities;
	protected RemoteWebDriver remoteDriver;
	
	public CollaborativeRemoteDummyWriter(int n_user, int type_spd,
			String docUrl, int exp_id, String serverAddr) {
		super(n_user, type_spd, docUrl, exp_id);
		// TODO Auto-generated constructor stub
		this.serverAddr = serverAddr;
		this.inputElement = null;
		this.capabilities = new DesiredCapabilities();
		this.capabilities.setBrowserName("chrome");
		this.remoteDriver = null;
		this.shouldWrite = true;
	}
	
	public void cancel () {
		try {
			LOG.info("Cancalling remote writer");
			shouldWrite = false;
			remoteDriver.quit ();
		} catch (SessionNotFoundException e) {
			System.out.println("Remote Dummy Writer already quit");
		} catch (UnreachableBrowserException e) {
			System.out.println("Unreachable Browser expcetion at Remote Dummy Writer");
		} catch (Exception e) {
			System.out.println("General exception at Remote Dummy Writer");
		}
		interrupt();
	}
}
