package de.mb.rdw.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;

import de.mb.rdw.GameController;

/**
 * Application that holds server connection and updates chat user ip-list
 *
 * @author Marco Behnke & Nur Jawad
 */
public class TCPServerHandler implements Runnable {

	final static Logger log = Logger.getLogger(TCPServerHandler.class);

	/**
	 * Output stream on socket
	 */
	private DataOutputStream _outToServer;
	/**
	 * Input stream on socket
	 */
	private DataInputStream  _inFromServer;
	/**
	 * Server port for socket connection
	 */
	private int _serverPort = 7000;
	/**
	 * Poll intervall for userlist update
	 */
	private int _pollIntervallMs=5;
	/**
	 * flag to see if client is still connected
	 */
	private boolean _running;
	/**
	 * chat client application pointer
	 */
	private GameController _application;

	/**
	 * Public constructor
	 *
	 * @param app chat client application
	 */
	public TCPServerHandler(GameController app){
		_application = app;
		_running=true;
	}

	/**
	 * Set disconnect flag
	 */
	public void disconnect(){
		_running=false;
	}

	/**
	 * Send EXIT to server and close all streams and sockets
	 */
	public void cleanUp() {
		try {
			_application.getClientServerOutput().writeBytes("EXIT\n");
			_application.getClientServerInput().close();
			_application.getClientServerOutput().close();
			_application.getClientServerSocket().close();
		} catch(IOException e){
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * Program start.
	 * Retrieves every five second list with current connected chat users
	 */
	public void run() {
			// retrieve list of users every 5 seconds
			while(_running) {
				try {
					log.info("send gibinfo");
					_application.getClientServerOutput().writeBytes("GIBINFO\n");
					log.info(_application.getClientServerInput().readLine());
				} catch(IOException e){
					log.error(e.getMessage(), e);
				}
				try {
					Thread.sleep( 5000);
				} catch(InterruptedException e) {}
			}
			cleanUp();
	}
}
