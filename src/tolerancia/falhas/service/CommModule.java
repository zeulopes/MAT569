package tolerancia.falhas.service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Classe que representa o modulo de comunicação.
 *
 * @author eliseu.araujo
 *
 */
public class CommModule {

  private final List<InetAddress> group;
  private final int commUdpPort = 5000;
  private final Timer keepAliveScheduler;
  private final int period = 15000; // 15 seg.
  private final int timeout = 5000; // total de 6 seg (delay + timeout)
  private final MulticastSocket socket;


  public CommModule(final List<InetAddress> group) throws IOException {
    this.socket = new MulticastSocket();
    this.group = group;
    this.keepAliveScheduler = new Timer();
    this.keepAliveScheduler.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        CommModule.this.sendKeepAliveToGroup();
      }
    }, 1000, this.period);// delay - delay in milliseconds before task is to be executed, period - time in milliseconds between successive task executions.
  }

  private void sendKeepAliveToGroup() {
    for (final InetAddress inetAddress : this.group) {
      System.out.println("sending message to host: " + inetAddress.toString());
      final byte[] msg = inetAddress.toString().getBytes();
      try {
        this.sendMsg(new DatagramPacket(msg, msg.length, inetAddress, this.commUdpPort));
      } catch (final SocketTimeoutException e) {
        // TODO Add host na lista de suspeitos
        e.printStackTrace();
      }
    }
  }

  private void sendMsg(final DatagramPacket msg) throws SocketTimeoutException {
    try {
      this.socket.setSoTimeout(this.timeout);
      this.socket.send(msg);
    } catch (final IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public static void main(final String[] args) {
    final List<InetAddress> group = new ArrayList<InetAddress>();
    try {
      group.add(InetAddress.getByName("127.0.0.1"));
      group.add(InetAddress.getByName("127.0.0.2"));
      group.add(InetAddress.getByName("127.0.0.3"));
      final CommModule comm = new CommModule(group);
    } catch (final UnknownHostException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (final IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

}
