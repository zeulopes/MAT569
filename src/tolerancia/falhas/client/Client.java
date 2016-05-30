package tolerancia.falhas.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Cliente: - o cliente se encarregará de enviar uma mensagem para o primary;
 *
 * */
public class Client {

  public Client() {}

  /**
   * Metodo que envia uma msg ao grupo de rélicas. Somente a réplica primária deve processar a msg (e encaminhar ao grupo).
   *
   * @throws Exception
   */
  public void sendMsgToGroup() throws Exception {
    // TODO Validar como o cliente enviara a mensagem apenas para o primary
    // Atualmente ele está enviando para o grupo e o grupo está carregando as mensagens.
    System.out.print("Informe a mensagem a ser encaminhada");
    final BufferedReader fraseIO = new BufferedReader(new InputStreamReader(System.in));
    final DatagramSocket socket = new DatagramSocket();

    // Obtendo informações do cliente
    final InetAddress ip = InetAddress.getByName("239.0.0.1");

    // array de bytes que ira armazenar os dados do pacote a ser enviado
    byte[] enviado = new byte[1024];

    // array de bytes que ira armazenar os dados do pacote a ser recebido como resposta do server
    final byte[] recebido = new byte[1024];

    final String frase = fraseIO.readLine();
    enviado = frase.getBytes();

    // Obtendo o pacote a ser enviado
    final DatagramPacket pacote = new DatagramPacket(enviado, enviado.length, ip, 1234);
    socket.send(pacote); // enviando pacote para o server

    // Montando pacote com o retorno do servidor
    final DatagramPacket pacoteRecebido = new DatagramPacket(recebido, recebido.length);

    // Recebendo o pacote com o retorno do servidor
    socket.receive(pacoteRecebido);

    final String fraseModificada = new String(pacoteRecebido.getData());
    System.out.println("Retorno do servidor: " + fraseModificada);
    socket.close();
  }

}
