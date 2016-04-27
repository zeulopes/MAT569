package toleranca.falhas.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
	public static void main(String args[]) throws Exception {       
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		DatagramSocket socket = new DatagramSocket();

		//ip/informacoes sobre o cliente
		InetAddress ip = InetAddress.getByName("localhost");       
		
		//array de bytes que ira armazenar os dados do pacote a ser enviado
		byte[] enviado = new byte[1024];       
		
		//array de bytes que ira armazenar os dados do pacote a ser recebido como retodno do server		
		byte[] recebido = new byte[1024];       
		
		String frase = inFromUser.readLine();       
		enviado = frase.getBytes();
		//Obtendo o pacote a ser enviado
		DatagramPacket pacote = new DatagramPacket(enviado, enviado.length, ip, 9876);       
		socket.send(pacote); //enviando pacote para o server       
		
		//Montando pacote com o retorno do servidor
		DatagramPacket pacoteRecebido = new DatagramPacket(recebido, recebido.length);       
		
		//Recebendo o pacote com o retorno do servidor
		socket.receive(pacoteRecebido);       
		
		String fraseModificada = new String(pacoteRecebido.getData());       
		System.out.println("Cliente Recebeu: " + fraseModificada);       
		socket.close();    
	}
}
