package tolerancia.falhas.service;

import java.net.InetAddress;
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
  private final Timer keepAliveScheduler;
  private final int timeout = 5000; // total de 6 seg (delay + timeout)


  public CommModule(final List<InetAddress> group) {
    this.group = group;
    this.keepAliveScheduler = new Timer();
    this.keepAliveScheduler.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        // colocar tarefas aqui ...
        CommModule.this.sendKeepAliveToGroup();
      }
    }, 1000, this.timeout);// delay - delay in milliseconds before task is to be executed, period - time in milliseconds between successive task executions.
  }

  private void sendKeepAliveToGroup() {
    for (final InetAddress inetAddress : this.group) {
      System.out.println("sending message to host: " + inetAddress.toString());
    }
  }

  public static void main(final String[] args) {
    final List<InetAddress> group = new ArrayList<InetAddress>();
    try {
      group.add(InetAddress.getByName("127.0.0.1"));
      group.add(InetAddress.getByName("127.0.0.2"));
      group.add(InetAddress.getByName("127.0.0.3"));
    } catch (final UnknownHostException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    final CommModule comm = new CommModule(group);

  }

}
