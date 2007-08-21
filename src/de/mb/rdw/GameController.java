package de.mb.rdw;

import java.awt.Frame;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Properties;

import org.apache.log4j.Logger;

import de.mb.rdw.client.IncomingMessageThread;
import de.mb.rdw.swing.ChildCharacterFrame;
import de.mb.util.Utils;

public class GameController {
	final static Logger log = Logger.getLogger(GameController.class);

	protected ChildCharacterFrame characterFrame;
	protected Properties properties;

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
	}

	public ChildCharacterFrame getCharacterFrame(Frame parentWindow) {
		if (characterFrame == null)
			characterFrame = new ChildCharacterFrame(parentWindow,
					"Charakterblatt");
		return characterFrame;
	}

	public void startMessageReceiver() {
		_im = new IncomingMessageThread(getProperty("client.ip"), getPropertyAsInteger("client.port.listen"));
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
					InetAddress.getByName(nextIP), getPropertyAsInteger("server.port.listen"));
			_msgSocket.send(sendPacket);

		} catch (SocketException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
}