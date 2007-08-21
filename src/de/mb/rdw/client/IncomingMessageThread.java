package de.mb.rdw.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

/**
 * Started by client. Is listening for incoming messages on port 7001 and submits content to Chat Client.
 *
 * @author Marco Behnke & Nur Jawad
 */
public class IncomingMessageThread implements Runnable {

	final static Logger log = Logger.getLogger(IncomingMessageThread.class);
	/**
	 * Socket listening for incoming messages
	 */
	private DatagramSocket _msgSocket;
	/**
	 * Default datagram packet used for sending packets
	 */
	private DatagramPacket _msg;

	private String _myip = "";

	private int port;

	/**
	 * Public constructor
	 *
	 * @param app chat client application
	 */
	public IncomingMessageThread(String myip, int port) {
		_myip = myip;
		this.port = port;
		log.info("set my ip "+myip);
	}

	/**
	 * Program start. Thread is listening for incoming messages here.
	 */
	public void run() {
		// while-loop to let the listener continue even after
		// shutdown after IOException
		boolean running = true;
		while(running) {
			try {
				log.info("Start receiver thread");
				log.info("Listening to " + port);
				_msgSocket = new DatagramSocket(port);
				_msgSocket.setReuseAddress(true);
				byte[] receiveData;
				while(true){
					receiveData = new byte[1024];
					_msg =	new DatagramPacket(receiveData, receiveData.length);
					_msgSocket.receive(_msg );
					log.info(_msg.getAddress().getHostAddress()+":"+(new String(_msg.getData() )).trim());
				}
			} catch(SocketException e){
				running=false;
				e.printStackTrace();
			} catch(IOException e){
				// not that evil to handled.
				e.printStackTrace();
			}
		}
	}
}