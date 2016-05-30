package tolerancia.falhas.client;

/**
 * Classe que define o escopo de uma mensagem enviada ao grupo
 * @author Alexandre Ferreira
 */
public class Message {
    private String _message;
    private boolean _isClient;

    public Message(String message, boolean isClient) {
        this._message = message;
        this._isClient = isClient;
    }

    public String getMessage() {
        return this._message;
    }

    public boolean isClient() {
        return this._isClient;
    }
}
