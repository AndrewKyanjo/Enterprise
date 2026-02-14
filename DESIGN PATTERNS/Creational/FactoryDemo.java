package DESIGN PATTERNS.Creational;

interface Transport {
    void deliver();
}

class Truck implements Transport {
    public void deliver() { System.out.println("Deliver by land in a truck."); }
}

class Ship implements Transport {
    public void deliver() { System.out.println("Deliver by sea in a ship."); }
}

abstract class Logistics {
    abstract Transport createTransport();
    public void planDelivery() {
        Transport t = createTransport();
        t.deliver();
    }
}

class RoadLogistics extends Logistics {
    Transport createTransport() { return new Truck(); }
}

class SeaLogistics extends Logistics {
    Transport createTransport() { return new Ship(); }
}

// Usage
public class FactoryDemo {
    public static void main(String[] args) {
        Logistics logistics = new RoadLogistics();
        logistics.planDelivery(); // Deliver by land...
    }
}