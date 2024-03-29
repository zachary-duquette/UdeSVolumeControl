package server;

import java.io.IOException;
import java.io.Serializable;
import message.DeleteVolumeConfigRequest;
import message.ExistsUserRequest;
import message.GetVolumeConfigsRequest;
import message.HelloWorldMessage;
import message.PostNewUserRequest;
import message.PutVolumeConfigRequest;
import utils.ClientTCP;
import utils.Serializer;

public class MessageHandler implements Runnable {

	ClientTCP _cl;
	
	public MessageHandler(ClientTCP cl) {
		_cl = cl;
	}

	@Override
	public void run() {
		try {
			Object receivedMessage = _cl.receive();
			System.out.println(receivedMessage);
			
			Serializable reply = null;
			
			if(receivedMessage instanceof HelloWorldMessage) {
				System.out.println("Got a HelloWorldMessage, how sweet!");
				System.out.println("Replying :)");
				reply = new HelloWorldMessage();
			} else if(receivedMessage instanceof PostNewUserRequest) {
				System.out.println("User registration request");
				reply = new PostNewUserHandler().handle((PostNewUserRequest) receivedMessage);
			} else if(receivedMessage instanceof ExistsUserRequest) {
				System.out.println("User exists request");
				reply = new ExistsUserHandler().handle((ExistsUserRequest) receivedMessage);
			} else if(receivedMessage instanceof GetVolumeConfigsRequest) {
				System.out.println("Get user configs request");
				reply = new GetVolumeConfigsHandler().handle((GetVolumeConfigsRequest) receivedMessage);
			} else if(receivedMessage instanceof PutVolumeConfigRequest) {
				System.out.println("Put config request");
				reply = new PutVolumeConfigHandler().handle((PutVolumeConfigRequest) receivedMessage);
			} else if(receivedMessage instanceof DeleteVolumeConfigRequest) {
				System.out.println("Delete config request");
				reply = new DeleteVolumeConfigHandler().handle((DeleteVolumeConfigRequest) receivedMessage);
			} else {
				System.err.println("Unknown message type received");
			}

			if(reply != null) {
				_cl.send(Serializer.serialize(reply));
				System.out.println("REPLY SENT");
			}
			_cl.close();
			
		} catch (ClassNotFoundException | IOException e1) {
			e1.printStackTrace();
		}
	}
}
