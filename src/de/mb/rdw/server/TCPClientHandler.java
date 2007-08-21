package de.mb.rdw.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.apache.log4j.Logger;

/**
 * Chatter connection handler. Will be started in a single thread for each
 * connected chatter.
 *
 * @author Marco Behnke & Nur Jawad
 */
public class TCPClientHandler implements Runnable {

	final static Logger log = Logger.getLogger(TCPClientHandler.class);

	/**
	 * Connection socket
	 */
	private Socket connectionSocket;

	/**
	 * Output stream on connection socket
	 */
	private DataOutputStream outToClient;

	/**
	 * Input stream on connection socket
	 */
	private DataInputStream inFromClient;

	/**
	 * chat server application pointer
	 */
	private GameServer gameServer;

	protected String ip;

	/**
	 * Public constructor
	 *
	 * @param socket
	 *            Connection socket created by welcome socket
	 * @param app
	 *            ChatServer application
	 */
	public TCPClientHandler(Socket socket, GameServer app) {
		connectionSocket = socket;
		gameServer = app;
	}

	/**
	 * Closes all streams and sockets and removes chatname from list of active
	 * chatters.
	 */
	private void cleanUp() {
		try {
			outToClient.close();
			inFromClient.close();
			connectionSocket.close();
			gameServer.removeClient(ip);
		} catch (IOException e) {
			log.error(e);
		}
	}

	/**
	 * Program start. Listenes for incoming client requests and sends updated
	 * list of chat users or closes connection
	 */
	public void run() {
		log.info("Client-Server Connection initialized");
		try {
			outToClient = new DataOutputStream(connectionSocket
					.getOutputStream());
			inFromClient = new DataInputStream(connectionSocket
					.getInputStream());

			log.info("SEND FIRST MESSAGE TO CLIENT");
			/*
			 * welcome incoming client
			 */
			outToClient.writeBytes("WELCOME CLIENT");
			log.info("FIRST MESSAGE TO CLIENT SENT");

			/*
			 * receive his answer and add to list of active chatters
			 */
			log.info("WAITING FOR FIRST RESPONSE");
			String rcvmsg = inFromClient.readLine();
			ip = gameServer.addClient(connectionSocket.getInetAddress().getHostAddress());
			log.info("FIRST RESPONSE RECEIVED: "+rcvmsg);

			/*
			 * Message loop
			 */
			boolean connected = true;
			while (connected) {
				log.info("Constant waiting for messages");
				rcvmsg = inFromClient.readLine();
				log.info("Message received: " + rcvmsg);
				/*
				 * hier switch über empfangene Nachricht
				 */
				outToClient.writeBytes("ACK to "+ip+"\n");
			}
		} catch (IOException e) {
			/*
			 * if this for some reason crashes. chatter will be removed from
			 * list of active chatters and all open streams and sockets will be
			 * closed
			 */
			log.error(e);
		} finally {
			cleanUp();
		}
	}
}
