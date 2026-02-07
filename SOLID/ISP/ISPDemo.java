package SOLID.ISP;

interface Workable {
    void work();
}

interface Eatable {
    void eat();
}

class Human implements Workable, Eatable {
    public void work() { System.out.println("Human working"); }
    public void eat()  { System.out.println("Human eating"); }
}

class Robot implements Workable {
    public void work() { System.out.println("Robot working"); }
    // no eat() method – not forced to implement
}

public class ISPDemo {
    public static void main(String[] args) {
        Workable w = new Robot();
        w.work();
    }
}