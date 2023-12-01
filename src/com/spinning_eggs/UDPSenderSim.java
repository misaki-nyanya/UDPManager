package com.spinning_eggs;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

import com.spinning_eggs.navigation.NavigationLoad;

public class UDPSenderSim extends UDPSender{
	public static int POSITION = 0;
	public static int NAVI_ROUTE = 1;
	public static int ENV_ROUTE = 2;
	
	public String targetIP;
	public int port;
	public int targetPort;
	public NavigationLoad payload;	
	public boolean received;
	public String expectedSignal;
			
	public UDPSenderSim(String targetIP, NavigationLoad payload, String expectedSignal)  {
		super(targetIP,payload.getJson(),expectedSignal);
		this.targetIP = targetIP;		
		this.payload = payload;
		this.received = false;
		this.expectedSignal = expectedSignal;
		this.port = UDPManager.getUnusedPort();
		this.targetPort = UDPManager.getListenerPort();
	}
	
	public void run() {
		byte[] payload = this.payload.getJson().getBytes(StandardCharsets.UTF_8);
		
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket(this.port);
		} catch (SocketException e) {
			UDPManager.stopWaitingSignal(this);
			e.printStackTrace();
		}
		
		DatagramPacket packet = null;
		try {
			packet = new DatagramPacket(payload, payload.length, InetAddress.getByName(this.targetIP), this.targetPort);
		} catch (UnknownHostException e) {
			UDPManager.stopWaitingSignal(this);
			e.printStackTrace();			
		}
		
		while(!received) {
			this.payload.step();
			payload = this.payload.getJson().getBytes(StandardCharsets.UTF_8);
			
			try {
				packet = new DatagramPacket(payload, payload.length, InetAddress.getByName(this.targetIP), this.targetPort);
			} catch (UnknownHostException e) {
				UDPManager.stopWaitingSignal(this);
				e.printStackTrace();
			}
			
			try {
				socket.send(packet);
				System.out.println("Sending to "+this.targetPort+" : "+this.payload.getJson()+" @ "+this.port);
				Thread.sleep(2000);
			} catch (IOException e) {
				UDPManager.stopWaitingSignal(this);
				e.printStackTrace();
			} catch (InterruptedException i) {
				i.printStackTrace();
			}
		}
		socket.close();		
	}
	
	public String toString() {
		return "【Sender "+this.payload+" @ "+this.port+" ,waiting for "+this.expectedSignal+", Received:" + this.received + "】";
	}
}