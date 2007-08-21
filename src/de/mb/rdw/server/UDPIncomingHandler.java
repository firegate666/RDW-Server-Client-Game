package de.mb.rdw.server;

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
public class UDPIncomingHandler implements Runnable {

	final static Logger log = Logger.getLogger(UDPIncomingHandler.class);
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

	private GameServer server;

	/**
	 * Public constructor
	 *
	 * @param app chat client application
	 */
	public UDPIncomingHandler(String myip, int port, GameServer server) {
		_myip = myip;
		this.port = port;
		this.server = server;
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
					server.sendNewMessage(_msg.getAddress().getHostAddress(), "Message received");
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