package tolerancia.falhas.service;

import tolerancia.falhas.host.Host;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Servidor: - todo servidor pode vir a ser o primary; - o primary será sempre o servidor com o menor id do grupo - caso o servidor não seja o primary, este
 * deverá escutar as mensagens das replicas presente no grupo - o módulo monitor está presente em todas as replicas - implementar por camadas
 *
 *
 * - fazer um modulo monitor onde será responsavel por criar todas as replicas da rede - Enviar um objeto via socket - timeout adaptativo iniciar com zero e a
 * depender do tempo inicial e da quantidade de replicas assumir um tempo medio de envio
 */
class Service {
  public static void main(final String args[]) throws Exception {

    Host host;
    String frase = null;
    // definindo o endereco do grupo
    // InetAddress group = InetAddress.getByName("239.0.0.1");
    final InetAddress group = InetAddress.getByName("localhost");
    String folder = "server_";
    // inicianado a pasta do server
    File folderServer;
    String receivedMessage = "";

    System.out.print("Informe o ID do server: ");// Analisar se n fica melhor o ID ser o IP do server
    final int id =
        Integer.valueOf(new BufferedReader(new InputStreamReader(System.in)).readLine());
    host = new Host(id, true);
    System.out.println("Servidor: " + host.getId() + " Criado com Sucesso, na porta: "
        + host.getPort());
    frase = "ACK - Servidor " + host.getId() + " entrando no grupo";

    // TODO Ainda Não está funcionando mas o modulo monitor se encarregará de retornar a lista de servidores ativos
    final ArrayList<InetAddress> serversList = new ArrayList<InetAddress>();

    // Verificando se há outros servidores no grupo
    // Enviando a primeira mensagem
    host.setSendPacket(new DatagramPacket(frase.getBytes(), frase.length(), group, host
        .getPort()));
    host.getSocket().send(host.getSendPacket());

    if (host.isType()) {
      host.getSocket().setSoTimeout(5000);
      // / host.getSocket().joinGroup(group);
      folder += host.getId();
      folderServer = new File(folder);
      folderServer.mkdir();
      // incluindo o servidor ao grupo
    }

    while (true) {
      try {
        // inicializando o pacote a ser recebido
        host.setReceivedPacket(new DatagramPacket(host.getBufferReceived(), host
            .getBufferReceived().length));
        // aguardando o recebimento do pacote
        host.getSocket().receive(host.getReceivedPacket());
      } catch (final SocketTimeoutException ex) {
        host.setPrimaryBackup(true);
        host.getSocket().setSoTimeout(0);
        host.setReceivedPacket(new DatagramPacket(host.getBufferReceived(), host
            .getBufferReceived().length));
        host.getSocket().receive(host.getReceivedPacket());
      }

      receivedMessage = new String(host.getReceivedPacket().getData());

      if (!host.isType()) {
        System.out.println("Retornou a mensagem: " + receivedMessage);
        System.exit(0);
      }

      System.out.println("Chegou a mensagem: " + receivedMessage);

      if (receivedMessage.contains("ACK")) {
        // Definindo a resposta para o servidor
        host.setBufferSent("OK".getBytes());
      } else {
        // Criando o novo Arquivo
        final FileWriter fw =
            new FileWriter(folder + "/"
                + new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss").format(new Date()) + ".txt");

        // Escrevendo no novo Arquivo
        fw.write(receivedMessage);
        fw.close();

        // Definindo a resposta para o cliente
        host.setBufferSent("Mensagem salva com sucesso".getBytes());

        // TODO Implementar envio para os demais servidores a fim de sincronizar a mensagem
        // Primeira parte pronta: todos recebem a mensagem e criam um arquivo sincronizado
        if (host.isPrimaryBackup()) {
          // TODO Aqui ele deverá chamar a camada de comunicação em grupo e enviar para a lista de servidores ativos
          // Neste primeiro momento o primary está enviando a mensagem para o grupo por inteiro
          host.setSendPacket(new DatagramPacket(receivedMessage.getBytes(),
              receivedMessage.length()));
          host.getSocket().send(host.getSendPacket());
        }
      }

      // ja existe na lista de servidores?
      // Falta implementar a camada de comunicacao em grupo (desacoplar da camada de servico).
      // TODO Aqui será a chamada da camada de comunicacao em gupo
      if (!serversList.contains(host.getReceivedPacket().getAddress())) {
        serversList.add(host.getReceivedPacket().getAddress());
      }

      // iniciando o Pacote a ser enviado como retorno do servidor
      host.setSendPacket(new DatagramPacket(host.getBufferSent(),
          host.getBufferSent().length, host.getReceivedPacket().getAddress(), host
          .getReceivedPacket().getPort()));

      System.out.println("Enviando: " + new String(host.getBufferSent()) + " para "
          + host.getReceivedPacket().getAddress().getHostName());

      // limpando os pacotes recebidos
      host.setBufferSent(new byte[1024]);

      // Enviando retorno do servidor
      host.getSocket().send(host.getSendPacket());
    }
  }
}

// membership
// capitulo 3
// culoures - comunicacao em grupo
// assumir que uma replica ao entrar no grupo mandara um broadcast e caso seja solicitara a camada de deteccao de falhas qual a lista de servidores ativos

