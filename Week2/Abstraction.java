/**
 * ╔══════════════════════════════════════════════════════════╗
 *  DAY 13 – Abstraction
 * ╚══════════════════════════════════════════════════════════╝
 *
 *  Abstraction = hiding IMPLEMENTATION details and showing
 *                only WHAT something does, not HOW.
 *
 *  Two tools in Java:
 *
 *  ┌─────────────────┬────────────────────────────────────────┐
 *  │                 │  Abstract Class         │  Interface    │
 *  ├─────────────────┼─────────────────────────┼──────────────┤
 *  │ Keyword         │  abstract class         │  interface    │
 *  │ Extend/Impl     │  extends (1 only)        │  implements  │
 *  │                 │                          │  (multiple!) │
 *  │ Constructors    │  YES                     │  NO          │
 *  │ Instance fields │  YES                     │  NO (only    │
 *  │                 │                          │  constants)  │
 *  │ Concrete methods│  YES                     │  default/    │
 *  │                 │                          │  static only │
 *  │ Abstract methods│  YES                     │  YES         │
 *  │ Instantiate?    │  NO                      │  NO          │
 *  └─────────────────┴─────────────────────────┴──────────────┘
 *
 *  WHEN TO USE WHAT:
 *    Abstract class → when subclasses SHARE code and state
 *                     "is-a" relationship with common base
 *    Interface      → when unrelated classes share a CAPABILITY
 *                     "can-do" relationship (Flyable, Printable)
 *
 *  Examples here:
 *    abstract class Animal          → abstract class demo
 *    interface Flyable              → interface demo
 *    interface Swimmable            → multiple interface demo
 *    interface Drawable + Resizable → combined interfaces
 */

// ═══════════════════════════════════════════════════════════
//  ABSTRACT CLASS – Animal
//  Cannot be instantiated: new Animal() ← COMPILE ERROR
// ═══════════════════════════════════════════════════════════
abstract class Animal {

    // Abstract class CAN have fields
    protected String name;
    protected int    age;
    protected double weight;

    // Abstract class CAN have constructors
    public Animal(String name, int age, double weight) {
        this.name   = name;
        this.age    = age;
        this.weight = weight;
    }

    // ── Abstract methods – MUST be overridden by subclasses ──
    // No body! Subclass decides HOW it makes sound.
    public abstract String makeSound();

    // Subclass decides HOW it moves.
    public abstract String move();

    // ── Concrete methods – shared implementation ──────────────
    // Subclasses INHERIT this as-is (can still override if needed)
    public void eat(String food) {
        System.out.printf("  %s eats %s. Nom nom.%n", name, food);
    }

    public void sleep() {
        System.out.printf("  %s is sleeping... Zzz%n", name);
    }

    public void describe() {
        System.out.printf("  %s is a %d-year-old %s (%.1f kg). Says: \"%s\". Moves: %s.%n",
                name, age, this.getClass().getSimpleName(),
                weight, makeSound(), move());
    }
}


// ═══════════════════════════════════════════════════════════
//  INTERFACES – Capabilities (can-do relationships)
// ═══════════════════════════════════════════════════════════

interface Flyable {
    // Constants in interfaces are implicitly public static final
    double MAX_ALTITUDE = 10_000.0;   // metres

    // Abstract method (implicitly public abstract)
    void fly();
    double getFlightSpeed();

    // Default method (Java 8+) – optional to override
    default void land() {
        System.out.println("  Landing safely...");
    }
}

interface Swimmable {
    void swim();
    double getSwimSpeed();
}

interface Trainable {
    boolean learnTrick(String trick);
    void performTrick(String trick);
}


// ═══════════════════════════════════════════════════════════
//  CONCRETE CLASS – Dog  (extends Animal, implements Trainable)
// ═══════════════════════════════════════════════════════════
class Dog extends Animal implements Trainable, Swimmable {

    private String breed;
    private java.util.List<String> tricks = new java.util.ArrayList<>();

    public Dog(String name, int age, double weight, String breed) {
        super(name, age, weight);      // call Animal constructor
        this.breed = breed;
    }

    // ── Required: implement abstract methods ──────────────────
    @Override
    public String makeSound() { return "Woof!"; }

    @Override
    public String move() { return "runs on four legs"; }

    // ── Required: implement Trainable interface ───────────────
    @Override
    public boolean learnTrick(String trick) {
        if (tricks.size() >= 5) {
            System.out.printf("  %s can't learn more tricks (max 5).%n", name);
            return false;
        }
        tricks.add(trick);
        System.out.printf("  %s learned: %s! 🐾%n", name, trick);
        return true;
    }

    @Override
    public void performTrick(String trick) {
        if (tricks.contains(trick)) {
            System.out.printf("  %s performs: %s! 🎉%n", name, trick);
        } else {
            System.out.printf("  %s doesn't know '%s'. Train first!%n", name, trick);
        }
    }

    // ── Required: implement Swimmable interface ───────────────
    @Override
    public void swim() {
        System.out.printf("  %s (%s) is swimming! Splish splash!%n", name, breed);
    }

    @Override
    public double getSwimSpeed() { return 5.0; }  // km/h
}


// ═══════════════════════════════════════════════════════════
//  CONCRETE CLASS – Eagle  (extends Animal, implements Flyable)
// ═══════════════════════════════════════════════════════════
class Eagle extends Animal implements Flyable {

    private double wingspan;

    public Eagle(String name, int age, double weight, double wingspan) {
        super(name, age, weight);
        this.wingspan = wingspan;
    }

    @Override public String makeSound() { return "Screech!"; }
    @Override public String move()      { return "soars through the air"; }

    @Override
    public void fly() {
        System.out.printf("  %s spreads %.1fm wings and soars at %.0f km/h!%n",
                name, wingspan, getFlightSpeed());
    }

    @Override
    public double getFlightSpeed() { return 160.0; }  // km/h

    // Override the default landing method
    @Override
    public void land() {
        System.out.printf("  %s dives and lands on its talons.%n", name);
    }
}


// ═══════════════════════════════════════════════════════════
//  CONCRETE CLASS – Duck  (both Flyable AND Swimmable)
//  Demonstrates implementing MULTIPLE interfaces
// ═══════════════════════════════════════════════════════════
class Duck extends Animal implements Flyable, Swimmable {

    public Duck(String name, int age, double weight) {
        super(name, age, weight);
    }

    @Override public String makeSound() { return "Quack!"; }
    @Override public String move()      { return "waddles, flies, and swims"; }

    @Override
    public void fly() {
        System.out.printf("  %s takes flight at %.0f km/h!%n", name, getFlightSpeed());
    }

    @Override public double getFlightSpeed() { return 80.0; }

    @Override
    public void swim() {
        System.out.printf("  %s glides on the water at %.0f km/h.%n", name, getSwimSpeed());
    }

    @Override public double getSwimSpeed() { return 3.0; }
}


// ═══════════════════════════════════════════════════════════
//  PAYMENT SYSTEM – another clean abstraction example
// ═══════════════════════════════════════════════════════════
abstract class PaymentProcessor {

    protected String processorName;

    public PaymentProcessor(String processorName) {
        this.processorName = processorName;
    }

    // Template method pattern: defines the FLOW, subclasses fill the steps
    public final void processPayment(double amount) {
        System.out.printf("\n[%s] Processing ₹%.2f...%n", processorName, amount);
        if (validateAmount(amount)) {
            String txnId = executeTransaction(amount);
            sendReceipt(txnId, amount);
        }
    }

    private boolean validateAmount(double amount) {
        if (amount <= 0) {
            System.out.println("  ✘ Invalid amount.");
            return false;
        }
        return true;
    }

    protected abstract String executeTransaction(double amount);  // HOW = subclass decides

    private void sendReceipt(String txnId, double amount) {
        System.out.printf("  ✔ Receipt sent. TXN: %s | ₹%.2f%n", txnId, amount);
    }
}

class CreditCardProcessor extends PaymentProcessor {
    public CreditCardProcessor() { super("Credit Card"); }

    @Override
    protected String executeTransaction(double amount) {
        System.out.println("  Charging credit card...");
        return "CC" + (int)(Math.random() * 1_000_000);
    }
}

class UPIProcessor extends PaymentProcessor {
    private String upiId;
    public UPIProcessor(String upiId) {
        super("UPI");
        this.upiId = upiId;
    }

    @Override
    protected String executeTransaction(double amount) {
        System.out.printf("  Sending to UPI: %s%n", upiId);
        return "UPI" + System.currentTimeMillis() % 100_000;
    }
}


// ═══════════════════════════════════════════════════════════
//  MAIN
// ═══════════════════════════════════════════════════════════
public class Abstraction {

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║    DAY 13 – ABSTRACTION DEMO             ║");
        System.out.println("╚══════════════════════════════════════════╝");

        // ── Abstract class demo ───────────────────────────────
        System.out.println("\n=== ABSTRACT CLASS: Animal ===");

        // new Animal() ← CANNOT do this; Animal is abstract
        Dog   rex    = new Dog("Rex", 3, 30.0, "German Shepherd");
        Eagle eagle  = new Eagle("Aquila", 5, 4.5, 2.3);
        Duck  donald = new Duck("Donald", 2, 1.2);

        Animal[] animals = {rex, eagle, donald};
        for (Animal a : animals) {
            a.describe();    // polymorphic – each calls its OWN makeSound() + move()
        }

        // ── Concrete method inherited ─────────────────────────
        System.out.println("\n--- Shared inherited methods ---");
        rex.eat("kibble");
        eagle.sleep();
        donald.eat("bread");

        // ── Interface capabilities ────────────────────────────
        System.out.println("\n=== INTERFACE: Flyable & Swimmable ===");
        eagle.fly();
        eagle.land();    // overridden version

        donald.fly();
        donald.swim();
        donald.land();   // default version from Flyable

        rex.swim();      // Dogs can swim (Swimmable interface)

        // ── Trainable interface ───────────────────────────────
        System.out.println("\n=== INTERFACE: Trainable ===");
        rex.learnTrick("Sit");
        rex.learnTrick("Shake");
        rex.learnTrick("Roll Over");
        rex.performTrick("Sit");
        rex.performTrick("Fetch");   // not yet learned

        // ── Multiple interfaces on one reference ──────────────
        System.out.println("\n=== MULTIPLE INTERFACES ===");
        Flyable  flyer   = donald;    // Duck AS Flyable
        Swimmable swimmer = donald;   // Duck AS Swimmable
        flyer.fly();
        swimmer.swim();

        // ── Payment abstraction ───────────────────────────────
        System.out.println("\n=== ABSTRACT CLASS: PaymentProcessor ===");
        PaymentProcessor cc  = new CreditCardProcessor();
        PaymentProcessor upi = new UPIProcessor("user@ybl");

        cc.processPayment(1_500.0);
        upi.processPayment(750.0);
        cc.processPayment(-100.0);   // invalid

        // ── Key differences summary ───────────────────────────
        System.out.println("\n=== ABSTRACT CLASS vs INTERFACE ===");
        System.out.println("Animal  (abstract class) → Dog, Eagle, Duck  share fields + common code");
        System.out.println("Flyable (interface)      → Eagle, Duck can fly;  Dog cannot");
        System.out.println("Swimmable (interface)    → Duck, Dog can swim;   Eagle cannot");
        System.out.println("Duck implements BOTH Flyable and Swimmable  ← multiple interface power");
        System.out.println("Java class can extend only ONE class, but implement MANY interfaces.");
    }
}
