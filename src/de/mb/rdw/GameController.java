package de.mb.rdw;

import java.awt.Frame;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Properties;

import org.apache.log4j.Logger;

import de.mb.rdw.client.IncomingMessageThread;
import de.mb.rdw.client.TCPServerHandler;
import de.mb.rdw.swing.ChildCharacterFrame;
import de.mb.util.Utils;

public class GameController {
	final static Logger log = Logger.getLogger(GameController.class);

	protected ChildCharacterFrame characterFrame;

	protected Properties properties;

	/**
	 * Server connection socket, used for userlist update
	 */
	private Socket _clientServerSocket;

	/**
	 * Output stream on Client-Server-Socket
	 */
	private DataOutputStream _outToServer;

	/**
	 * Input stream on Client-Server-Socket
	 */
	private DataInputStream _inFromServer;

	/**
	 * Application to hold server connection and update userlist
	 */
	private TCPServerHandler _sc;

	IncomingMessageThread _im;

	public GameController() {
		initialize();
	}

	public String getProperty(String key) {
		if (properties != null)
			return properties.getProperty(key);
		else
			return "";
	}

	public Integer getPropertyAsInteger(String key) {
		return new Integer(getProperty(key));
	}

	protected void initialize() {
		properties = Utils.readProperties("resource.General");

		/*
		 * init tcpip
		 */
		try {
			_clientServerSocket=new Socket(getProperty("server.ip") ,getPropertyAsInteger("server.port.tcpip"));
			log.info("BUILD INPUT STREAM TO SERVER");
			_inFromServer = new DataInputStream(_clientServerSocket.getInputStream() );
			//log.info("WAITING FOR FIRST SERVER RESPONSE");
			//String server_ack = _inFromServer.readLine(); // no response, no server
			//log.info(server_ack);
			_outToServer = new DataOutputStream(_clientServerSocket.getOutputStream());
			_outToServer.writeBytes("POS_ACK\n");

			_sc = new TCPServerHandler(this);
			Thread serverConnection = new Thread(_sc);
			serverConnection.start();
		} catch (UnknownHostException e) {
			// TODO Automatisch erstellter Catch-Block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Automatisch erstellter Catch-Block
			e.printStackTrace();
		}
	}

	public ChildCharacterFrame getCharacterFrame(Frame parentWindow) {
		if (characterFrame == null)
			characterFrame = new ChildCharacterFrame(parentWindow,
					"Charakterblatt");
		return characterFrame;
	}

	public void startMessageReceiver() {
		_im = new IncomingMessageThread(getProperty("client.ip"),
				getPropertyAsInteger("client.port.listen"));
		Thread messageReceiver = new Thread(_im);
		messageReceiver.start();
	}

	public void sendNewMessage(String msg) {
		try {
			DatagramSocket _msgSocket = new DatagramSocket();
			// _msgSocket.setBroadcast(true);
			_msgSocket.setReuseAddress(true);

			byte[] sendData = new byte[1024];
			sendData = msg.getBytes();

			DatagramPacket sendPacket;

			String nextIP = getProperty("server.ip");
			sendPacket = new DatagramPacket(sendData, sendData.length,
					InetAddress.getByName(nextIP),
					getPropertyAsInteger("server.port.listen"));
			_msgSocket.send(sendPacket);

		} catch (SocketException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * Returns output stream on Client-Server-Socket used by
	 * ServerConnectionThread
	 *
	 * @return Output stream
	 * @throws IOException
	 */
	public DataOutputStream getClientServerOutput() throws IOException {
		if (_outToServer == null)
			_outToServer = new DataOutputStream(getClientServerSocket()
					.getOutputStream());
		return _outToServer;
	}

	/**
	 * Returns input stream on Client-Server-Socket used by
	 * ServerConnectionThread
	 *
	 * @return Input stream
	 * @throws IOException
	 */
	public DataInputStream getClientServerInput() throws IOException {
		if (_inFromServer == null)
			_inFromServer = new DataInputStream(getClientServerSocket()
					.getInputStream());
		return _inFromServer;
	}

	/**
	 * Returns client-server socket used by ServerConnectionThread
	 *
	 * @return socket
	 */
	public Socket getClientServerSocket() {
		return _clientServerSocket;
	}
}