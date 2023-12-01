package com.spinning_eggs;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.spinning_eggs.navigation.NavigationLoad;

public class UDPManager {
	private static ArrayList<Integer> usedPorts = new ArrayList<Integer>();
	private static LinkedHashMap<UDPSender, String> senders = new LinkedHashMap<UDPSender, String>();
	private static UDPListener listener = null;
	private static ExecutorService executor = Executors.newFixedThreadPool(10);
	private static Lock portListLock = new ReentrantLock(true);
	private static Lock sendersListLock = new ReentrantLock(true);
	
	public static int getUnusedPort() {
		SecureRandom  random = new SecureRandom ();
		random.setSeed(System.currentTimeMillis());
		int challengePort = random.nextInt(8001, 8098);
		portListLock.lock();
		while(UDPManager.usedPorts.contains(challengePort)) {
			challengePort = random.nextInt(8001, 8098);
		}
		UDPManager.usedPorts.add(challengePort);
		portListLock.unlock();
		return challengePort;
	}
	public static void releasePort(int port) {
		portListLock.lock();
		if(UDPManager.usedPorts.indexOf(port) != -1) {
			UDPManager.usedPorts.remove(UDPManager.usedPorts.indexOf(port));
		}
		portListLock.unlock();
	}
	
	public static int getListenerPort() {
		if(UDPManager.listener != null) {
			return UDPManager.listener.port;
		}else {
			return 8099;
		}
	}
	
	public static void addSender(UDPSender sender, String signal) {
		sendersListLock.lock();
		UDPManager.senders.put(sender, signal);
		sendersListLock.unlock();
	}
	public static void removeSender(UDPSender sender) {
		sendersListLock.lock();
		sender.received = true;
		UDPManager.releasePort(sender.port);
		UDPManager.senders.remove(sender);
		sendersListLock.unlock();
	}
	
	public static void createListener() {
		UDPManager.listener = new UDPListener();
		UDPManager.executor.execute(listener);
	}
	public static void createSender(String ip, String msg, String signal) {
		UDPSender sender = new UDPSender(ip, msg, signal);		
		UDPManager.addSender(sender, signal);
		UDPManager.executor.execute(sender);
	}
	
	public static void stopWaitingSignal(UDPSender sender) {
		UDPManager.removeSender(sender);
	}
	public static void stopWaitingSignal(String signal) {
		sendersListLock.lock();
		Set<UDPSender> senderSet = UDPManager.senders.keySet();
		ArrayList<UDPSender> senderToDelte = new ArrayList<UDPSender>();
		System.out.print("Current Senders: ");	
		System.out.println(UDPManager.senders);		
		for(UDPSender member: senderSet) {
			//如果等到了需要的回信就设置为已收到
			if(UDPManager.senders.get(member).equals(signal)) {
				senderToDelte.add(member);
			}
		}	
		for(UDPSender member: senderToDelte) {
			System.out.print("Deleting...");
			System.out.println(member);
			UDPManager.removeSender(member);
		}
		sendersListLock.unlock();
	}
	
	public static void main(String[] args) {
		NavigationLoad cur_usr = new NavigationLoad();
		System.out.println("Sending==============");
		System.out.println(cur_usr.getJson().replace("\\", ""));
		System.out.println();
		
		//UDPManager.createListener();
//		UDPManager.createSender("127.0.0.1", cur_usr.getJson(), "!!!!");
		UDPSenderSim u = new UDPSenderSim("127.0.0.1", cur_usr, "!!!");
		u.run();
		
	}
}
