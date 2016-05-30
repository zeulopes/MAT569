package tolerancia.falhas.host;

import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.IntSummaryStatistics;

public class Host {
	
	private Integer id, port;
	private DatagramPacket receivedPacket, sendPacket;
	byte[] bufferReceived, bufferSent;
	boolean type, primaryBackup;
	private MulticastSocket socket;
	private ArrayList<Host> servers;
	private ArrayList<Host> blackList;
	
	public Host(){}
	
	public Host(Integer id, boolean type) throws IOException{
		this.id = id;
		this.type = type;
		this.bufferReceived = new byte [1024];
		this.bufferSent = new byte[1024];
		this.servers = new ArrayList<Host>();
		this.blackList = new ArrayList<Host>();

		try{
			this.socket = new MulticastSocket(this.port = random());
		} catch (BindException ex){
			System.out.println(this.port);
			this.socket = new MulticastSocket(this.port = random());
		}
	}
	
	public int random(){
		int random = (int) ((int) 1000 + Math.random() * 100);
		
		if (random != 1013 && random != 1022 && random != 1024)
			return random;
		else 
			return random();
	}

	public void voting() {
		Collections.sort(this.servers, (Host h1, Host h2) -> (h1.getId() < h2.getId()) ? h1.getId() : h2.getId());
		Host primary = this.servers.get(0);
		primary.setPrimaryBackup(true);
		this.servers.set(0, primary);
		// TODO: enviar uma mensagem para os outros hosts
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public DatagramPacket getReceivedPacket() {
		return receivedPacket;
	}
	public void setReceivedPacket(DatagramPacket receivedPacket) {
		this.receivedPacket = receivedPacket;
	}
	public DatagramPacket getSendPacket() {
		return sendPacket;
	}
	public void setSendPacket(DatagramPacket sendPacket) {
		this.sendPacket = sendPacket;
	}
	public byte[] getBufferReceived() {
		return bufferReceived;
	}
	public void setBufferReceived(byte[] bufferReceived) {
		this.bufferReceived = bufferReceived;
	}
	public byte[] getBufferSent() {
		return bufferSent;
	}
	public void setBufferSent(byte[] bufferSent) {
		this.bufferSent = bufferSent;
	}
	public boolean isType() {
		return type;
	}
	public void setType(boolean type) {
		this.type = type;
	}
	public boolean isPrimaryBackup() {
		return primaryBackup;
	}
	public void setPrimaryBackup(boolean primaryBackup) {
		this.primaryBackup = primaryBackup;
	}
	public MulticastSocket getSocket() {
		return socket;
	}
	public void setSocket(MulticastSocket socket) {
		this.socket = socket;
	}
	public void appendServer(Host host) { this.servers.add(host); }
	public Host removeServer(int index) { return this.servers.remove(index); }
	public void appendBlackList(Host host) { this.blackList.add(host); }
	public Host removeBlackList(int index) { return this.blackList.remove(index); }
}
