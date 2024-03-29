package utils;

import java.io.*;
import java.net.*;

public class ClientUDP {
	public static final int DEFAULT_BUFFER_SIZE = 1024;
	
	private DatagramSocket _socket = null;
	private byte[] _buffer;
	
	// For connection/sending only
	private SocketAddress _addrPort = null;
	
	public ClientUDP(int receiveTimeout) throws SocketException {
		_socket = new DatagramSocket(null);
		_socket.setSoTimeout(receiveTimeout); // 0 -> Infinite
		_buffer = new byte[DEFAULT_BUFFER_SIZE];
    }

    public void bind(int port) throws SocketException, UnknownHostException {
    	_socket.bind(new InetSocketAddress(port));
    }
    
    public void connect(String hostname, int port) throws SocketException, UnknownHostException {
    	connect(new InetSocketAddress(hostname, port));
    }
    
    public void connect(SocketAddress addr) throws SocketException, UnknownHostException {
    	_addrPort = addr;
    }
    
    public void send(byte[] msg) throws IOException {
    	_socket.send(new DatagramPacket(msg, msg.length, _addrPort));
    }
    
    public DatagramPacket receive() throws IOException {
        DatagramPacket p = new DatagramPacket(_buffer, _buffer.length);
        _socket.receive(p);        
        return p;
    }

    public void close() {
        _socket.close();
    }

}
