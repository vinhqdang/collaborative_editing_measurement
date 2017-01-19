/**
 * 
 */
package fr.inria.coast.general;

/**
 * @author "Quang-Vinh DANG"
 *
 */
public class CollaborativeDistributedDummyWriter extends
		CollaborativeDummyWriter {

	//address of remoted server to run Selenium remotely
	String serverAddr;
	
	public CollaborativeDistributedDummyWriter(int n_user, int type_spd,
			String docUrl, int exp_id, String serverAddr) {
		super(n_user, type_spd, docUrl, exp_id);
		this.serverAddr = serverAddr;
	}

}
