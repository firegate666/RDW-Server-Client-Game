package de.mb.rdw.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import de.mb.util.Utils;

public class GameServer {
	final static Logger log = Logger.getLogger(GameServer.class);

	DatagramSocket msgSocket;

	IncomingMessageThread _im;

	Properties properties;

	public String getProperty(String key) {
		if (properties != null)
			return properties.getProperty(key);
		else
			return "";
	}

	public Integer getPropertyAsInteger(String key) {
		return new Integer(getProperty(key));
	}

	public GameServer() {
		initialize();
	}

	public void sendNewMessage(String ip, String msg) {
		try {
			if (msgSocket == null)
				msgSocket = new DatagramSocket();
			msgSocket.setReuseAddress(true);

			byte[] sendData = new byte[1024];
			sendData = msg.getBytes();

			DatagramPacket sendPacket = new DatagramPacket(sendData,
					sendData.length, InetAddress.getByName(ip), getPropertyAsInteger("client.port.listen"));
			msgSocket.send(sendPacket);

		} catch (SocketException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	public void initialize() {
		properties = Utils.readProperties("resource.General");
		_im = new IncomingMessageThread(getProperty("server.ip"), getPropertyAsInteger("server.port.listen"), this);
		Thread messageReceiver = new Thread(_im);
		messageReceiver.start();
	}

	public static void main(String[] args) {
		LogManager.resetConfiguration();
		DOMConfigurator.configure(GameServer.class
				.getResource("/de/mb/rdw/server/log4j-config.xml"));
		GameServer server = new GameServer();
	}
}
