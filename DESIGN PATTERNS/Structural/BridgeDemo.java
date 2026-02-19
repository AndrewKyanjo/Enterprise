package DESIGN PATTERNS.Structural;

// Implementor
interface Device {
    void turnOn();
    void turnOff();
}

// Concrete implementors
class TV implements Device {
    public void turnOn() { System.out.println("TV on"); }
    public void turnOff() { System.out.println("TV off"); }
}
class Radio implements Device {
    public void turnOn() { System.out.println("Radio on"); }
    public void turnOff() { System.out.println("Radio off"); }
}

// Abstraction
abstract class RemoteControl {
    protected Device device;
    public RemoteControl(Device device) { this.device = device; }
    abstract void togglePower();
}

// Refined abstraction
class BasicRemote extends RemoteControl {
    private boolean isOn = false;
    public BasicRemote(Device device) { super(device); }
    public void togglePower() {
        if (isOn) {
            device.turnOff();
            isOn = false;
        } else {
            device.turnOn();
            isOn = true;
        }
    }
}

// Usage
public class BridgeDemo {
    public static void main(String[] args) {
        RemoteControl remote = new BasicRemote(new TV());
        remote.togglePower(); // TV on
        remote.togglePower(); // TV off
    }
}