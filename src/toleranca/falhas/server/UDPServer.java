package toleranca.falhas.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

//TODO Implementar Multicast + Threads
class UDPServer {
	public static void main(String args[]) throws Exception {
		DatagramSocket socket = new DatagramSocket(9876); //Datagrama             
		byte[] recebido = new byte[1024]; //Variavel que ira armazenar os dados do pacote recebido
		byte[] enviado = new byte[1024]; //Variavel que ira armazenar os dados do pacote a ser enviado
		
		while(true){ //Mantendo as conexoes abera
			DatagramPacket pacoteRecebido = new DatagramPacket(recebido, recebido.length); //Pacote recebido                   
			socket.receive(pacoteRecebido); //Recebendo um pacote de um ou mais clientes                  
			
			//TODO Implementar validacao em pacoteRecebido.getData para validar o tipo de pacote recebido
			String frase = new String(pacoteRecebido.getData()); //transformo em String somente para ficar mais facil de trabalhar
			
			System.out.println("Recebendo: " + frase);   
			
			//Obtendo o endereco ip do client que enviou o pacote
			InetAddress ip = pacoteRecebido.getAddress(); 

			//porta para envio do retorno do servidor
			int porta = pacoteRecebido.getPort();                   
            
			//Convertendo o retorno do servidor para o client
			enviado = frase.toUpperCase().getBytes();  
			
			//Carregando o Pacote a ser enviado 
			DatagramPacket pacoteEnviado = new DatagramPacket(enviado, enviado.length, ip, porta);                   
			
			//Enviando retorno do servidor
			socket.send(pacoteEnviado);                
		}       
	}
}

