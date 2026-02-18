package DESIGN PATTERNS.Structural;

// Complex subsystems
class CPU {
    public void freeze() { System.out.println("CPU freeze"); }
    public void jump(long pos) { System.out.println("CPU jump to " + pos); }
    public void execute() { System.out.println("CPU execute"); }
}
class Memory {
    public void load(long pos, byte[] data) { System.out.println("Memory load at " + pos); }
}
class HardDrive {
    public byte[] read(long lba, int size) { return new byte[size]; }
}

// Facade
class ComputerFacade {
    private CPU cpu;
    private Memory memory;
    private HardDrive hardDrive;
    public ComputerFacade() {
        this.cpu = new CPU();
        this.memory = new Memory();
        this.hardDrive = new HardDrive();
    }
    public void start() {
        cpu.freeze();
        memory.load(0, hardDrive.read(0, 1024));
        cpu.jump(0);
        cpu.execute();
    }
}

// Usage
public class FacadeDemo {
    public static void main(String[] args) {
        ComputerFacade computer = new ComputerFacade();
        computer.start();
    }
}