package org.yuner.www.myNetwork;

import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientSendThread {

	private Socket mSocket;
	private String mStrToSend;

	public synchronized void start(Socket socket, String str0) {
		this.mSocket = socket;
		this.mStrToSend = str0;

		try {
			OutputStreamWriter ost0 = new OutputStreamWriter(mSocket.getOutputStream(), "UTF-8");
			ost0.write(mStrToSend + "\n");
			ost0.flush();

		} catch (Exception e) {
		}
	}
}
