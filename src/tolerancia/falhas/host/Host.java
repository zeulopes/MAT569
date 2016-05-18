package tolerancia.falhas.host;

import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

public class Host {
	
	private Integer id, port;
	private DatagramPacket receivedPacket, sendPacket;
	byte[] bufferReceived, bufferSent;
	boolean type, primaryBackup;
	private MulticastSocket socket;
	
	public Host(){}
	
	public Host(Integer id, boolean type) throws IOException{
		this.id = id;
		this.type = type;
		this.bufferReceived = new byte [1024];
		this.bufferSent = new byte[1024];
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
}
