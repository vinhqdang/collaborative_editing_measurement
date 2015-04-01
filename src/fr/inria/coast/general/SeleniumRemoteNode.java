/**
 * 
 */
package fr.inria.coast.general;

/**
 * @author qdang
 *
 */
public class SeleniumRemoteNode {
	//address of node, maybe IP address
	String nodeAddr;
	//number of thread can run in this node
	int numThread;
	
	public void setNodeAddr (String nodeAddr) {this.nodeAddr = nodeAddr;}
	public void setNumThread (int numThread) {this.numThread = numThread;}
	public String getNodeAddr () {return this.nodeAddr;}
	public int getNumThread () {return this.numThread;}
}
