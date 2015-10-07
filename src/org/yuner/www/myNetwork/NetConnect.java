
package org.yuner.www.myNetwork;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class NetConnect extends Thread {

	/* information about Server, ip address and portal number */
	private String mHostIp = "115.68.13.59";
	// private String mHostIp="192.168.208.128";

	private int mHostPort = 8080;

	/* socket */
	private Socket mSocket0 = null;

	private boolean connectedAlready = false;

	/*
	 * singleton retrieval public static NetConnect getnetConnect() {
	 * if(netConnect == null) { netConnect = new NetConnect(); } return
	 * netConnect; }
	 */

	public NetConnect() {

	}

	public void run() {
		try {
			sleep(100); // to wait for the connection to be stable
		} catch (Exception e) {
		}
		try {
			// initialization and establish connection
			InetAddress ia0 = InetAddress.getByName(mHostIp);
			InetSocketAddress ist0 = new InetSocketAddress(ia0, mHostPort);
			mSocket0 = new Socket();
			mSocket0.connect(ist0, 2000);

			if (mSocket0.isConnected()) {
				connectedAlready = true;
			} else {
				connectedAlready = false;
			}
		} catch (IOException e) {
			System.out.println("error occured");
		}
	}

	public Socket getSocket() {
		return mSocket0;
	}

	public boolean connectedOrNot() {
		return connectedAlready;
	}

}