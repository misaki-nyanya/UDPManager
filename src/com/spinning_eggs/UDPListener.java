package com.spinning_eggs;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class UDPListener extends Thread{
	private DatagramSocket socket;
	public int port;
	public UDPListener() {
		this.port = 8099;
		try {
			this.socket = new DatagramSocket(this.port);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		byte[] buffer = new byte[1024];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		System.out.println("Listener receiving on " + this.port);
		while(true) {
			try {
				socket.receive(packet);
				String signal = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8);
				System.out.println("Received signal: "+signal);
				UDPManager.stopWaitingSignal(signal);
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
	}
}
