package DESIGN PATTERNS.Structural;

// Existing service (incompatible interface)
class EuropeanSocket {
    public void provide220V() { System.out.println("220V power"); }
}

// Target interface
interface USASocket {
    void provide110V();
}

// Adapter
class SocketAdapter implements USASocket {
    private EuropeanSocket euroSocket;
    public SocketAdapter(EuropeanSocket euroSocket) { this.euroSocket = euroSocket; }
    public void provide110V() {
        euroSocket.provide220V(); // adapts to 110V by transformation
        System.out.println("Adapter converts to 110V");
    }
}

// Client
public class AdapterDemo {
    public static void main(String[] args) {
        EuropeanSocket euro = new EuropeanSocket();
        USASocket usa = new SocketAdapter(euro);
        usa.provide110V();
    }
}