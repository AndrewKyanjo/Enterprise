package DESIGN PATTERNS.Behavioral;

interface State {
    void handle();
}

class OnState implements State {
    public void handle() { System.out.println("Device is ON"); }
}

class OffState implements State {
    public void handle() { System.out.println("Device is OFF"); }
}

class Device {
    private State state;
    public void setState(State state) { this.state = state; }
    public void perform() { state.handle(); }
}

// Usage
public class StateDemo {
    public static void main(String[] args) {
        Device device = new Device();
        device.setState(new OnState());
        device.perform();
        device.setState(new OffState());
        device.perform();
    }
}