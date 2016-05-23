package tolerancia.falhas.service;

import java.net.InetAddress;
import java.util.List;

/**
 * Classe que representa o modulo de comunicação.
 *
 * @author eliseu.araujo
 *
 */
public class CommModule {

  private final List<InetAddress> group;

  public CommModule(final List<InetAddress> group) {
    this.group = group;
    this.sendKeepAliveToGroup();
  }

  private void sendKeepAliveToGroup() {
    for (final InetAddress inetAddress : this.group) {

    }
  }

}
