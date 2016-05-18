package tolerancia.falhas.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Cliente:
 * - o cliente se encarregará de enviar uma mensagem para o primary;
 *  
 * */
public class Client {
	public static void main(String args[]) throws Exception {
		System.out.print("Informe a mensagem a ser encaminhada");
		BufferedReader fraseIO = new BufferedReader(new InputStreamReader(System.in));
		DatagramSocket socket = new DatagramSocket();

		//Obtendo informações do cliente
		InetAddress ip = InetAddress.getByName("239.0.0.1");       
		
		//array de bytes que ira armazenar os dados do pacote a ser enviado
		byte[] enviado = new byte[1024];       
		
		//array de bytes que ira armazenar os dados do pacote a ser recebido como resposta do server		
		byte[] recebido = new byte[1024];       
		
		String frase = fraseIO.readLine();       
		enviado = frase.getBytes();
		
		//Obtendo o pacote a ser enviado
		DatagramPacket pacote = new DatagramPacket(enviado, enviado.length, ip, 1234);       
		socket.send(pacote); //enviando pacote para o server       
		
		//Montando pacote com o retorno do servidor
		DatagramPacket pacoteRecebido = new DatagramPacket(recebido, recebido.length);       
		
		//Recebendo o pacote com o retorno do servidor
		socket.receive(pacoteRecebido);       
		
		String fraseModificada = new String(pacoteRecebido.getData());       
		System.out.println("Retorno do servidor: " + fraseModificada);       
		socket.close();    
	}
}