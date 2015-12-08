package server;

import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;

import message.HelloWorldMessage;
import utils.ClientUDP;
import utils.Serializer;

public class MessageHandler implements Runnable {

	DatagramPacket _receivedData;
	
	public MessageHandler(DatagramPacket data) {
		_receivedData = data;
	}

	@Override
	public void run() {
		try {
			Object receivedMessage = Serializer.deserialize(_receivedData.getData());
			System.out.println(receivedMessage);
			
			Serializable reply = null;
			
			// TODO Handle message based on type and call module to handle the request
			// TODO HANDLE SHOULD RETURN AN OBJECT TO REPLY, unless you got nothing
			
			if(receivedMessage instanceof HelloWorldMessage) {
				System.out.println("Got a HelloWorldMessage, how sweet!");
				System.out.println("Replying :)");
				reply = new HelloWorldMessage();
			} else {
				System.out.println("Unknown message type received");
			}

			if(reply != null) {
				ClientUDP cl = new ClientUDP();
				cl.connect(_receivedData.getSocketAddress());
				cl.send(Serializer.serialize(reply));
				System.out.println("REPLY SENT");
			}
			
		} catch (ClassNotFoundException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
