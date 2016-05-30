package tolerancia.falhas;

import tolerancia.falhas.client.Client;

/**
 * @author Alexandre Ferreira
 */
public class Main {

  public Main() {}

  public void main() {
    // Instanciando cliente...
    final Client client = new Client();
    // TODO: instaciar grupo
    // enviando msg ao grupo...
    try {
      client.sendMsgToGroup();
    } catch (final Exception e) {
      e.printStackTrace();
    }
  }
}
