package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import utils.ClientUDP;

public class Main {
	public static final int DEFAULT_POOL_SIZE = 10;

	public static void main(String[] args) {
		String port = args[0];
		
		System.out.println("Starting server");
		System.out.println("port: " + port);
		
		ExecutorService pool = Executors.newFixedThreadPool(DEFAULT_POOL_SIZE);
		
		try {
			ClientUDP cl = new ClientUDP(0);
			cl.bind(Integer.valueOf(port));
			
			while(true) {
				DatagramPacket data = cl.receive();
				
				System.out.println("Datagram received");
				
				pool.execute(new MessageHandler(data));
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}