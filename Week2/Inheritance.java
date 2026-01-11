/**
 * ╔══════════════════════════════════════════════════════════╗
 *  DAY 11 – Inheritance
 * ╚══════════════════════════════════════════════════════════╝
 *
 *  Inheritance = a child class INHERITS fields and methods
 *                from a parent class, and can add/override them.
 *
 *  Keyword summary:
 *    extends   → "is-a" relationship   (Car extends Vehicle)
 *    super     → refers to the PARENT class
 *                  super(args)    → call parent constructor (must be first)
 *                  super.method() → call parent method
 *    @Override → annotation confirming you're intentionally overriding
 *
 *  Inheritance hierarchy built here:
 *
 *         Vehicle         ← parent / superclass
 *        /       \
 *      Car       Bike     ← children / subclasses
 *       |
 *    ElectricCar          ← grandchild (multi-level inheritance)
 *
 *  IS-A check:
 *    Car      IS-A Vehicle  ✔
 *    Bike     IS-A Vehicle  ✔
 *    ElectricCar IS-A Car   ✔  AND IS-A Vehicle  ✔
 */

// ═══════════════════════════════════════════════════════════
//  PARENT CLASS – Vehicle
// ═══════════════════════════════════════════════════════════
class Vehicle {

    // Fields shared by ALL vehicles
    protected String  brand;          // protected: visible to subclasses
    protected String  model;
    protected int     year;
    protected double  speed;          // current speed (km/h)
    protected boolean engineRunning;

    // Constructor
    public Vehicle(String brand, String model, int year) {
        this.brand         = brand;
        this.model         = model;
        this.year          = year;
        this.speed         = 0;
        this.engineRunning = false;
        System.out.printf("[Vehicle] Base constructed: %d %s %s%n", year, brand, model);
    }

    // Methods shared by ALL vehicles (can be overridden)
    public void startEngine() {
        engineRunning = true;
        System.out.printf("%s %s: Engine started.%n", brand, model);
    }

    public void stopEngine() {
        engineRunning = false;
        speed = 0;
        System.out.printf("%s %s: Engine stopped.%n", brand, model);
    }

    public void accelerate(double amount) {
        if (!engineRunning) { System.out.println("Start the engine first!"); return; }
        speed += amount;
        System.out.printf("%s %s: %+.0f km/h → now %.0f km/h%n",
                brand, model, amount, speed);
    }

    public void brake(double amount) {
        speed = Math.max(0, speed - amount);
        System.out.printf("%s %s: Braking → %.0f km/h%n", brand, model, speed);
    }

    // toString for easy printing
    @Override
    public String toString() {
        return String.format("Vehicle[%d %s %s | speed=%.0f | engine=%s]",
                year, brand, model, speed, engineRunning ? "ON" : "OFF");
    }

    // General info – subclasses will override this
    public void displayInfo() {
        System.out.printf("  %d %s %s | Speed: %.0f km/h%n", year, brand, model, speed);
    }
}


// ═══════════════════════════════════════════════════════════
//  CHILD CLASS – Car  (extends Vehicle)
// ═══════════════════════════════════════════════════════════
class Car extends Vehicle {

    // Car-specific extra fields
    private int    doors;
    private String fuelType;
    private double fuelLevel;

    public Car(String brand, String model, int year, int doors, String fuelType) {
        super(brand, model, year);      // MUST be first – calls Vehicle constructor
        this.doors     = doors;
        this.fuelType  = fuelType;
        this.fuelLevel = 100.0;
        System.out.printf("[Car] Extended: %d-door %s%n", doors, fuelType);
    }

    // ── Override parent method ────────────────────────────────
    @Override
    public void accelerate(double amount) {
        fuelLevel -= amount * 0.05;     // cars consume fuel when accelerating
        if (fuelLevel <= 0) {
            fuelLevel = 0;
            System.out.println(brand + " " + model + ": Out of fuel! Cannot accelerate.");
            return;
        }
        super.accelerate(amount);       // reuse parent logic then add our own output
        System.out.printf("  [Car] Fuel remaining: %.1f%%%n", fuelLevel);
    }

    // ── Car-specific method ───────────────────────────────────
    public void honk() {
        System.out.println(brand + " " + model + ": BEEP BEEP!");
    }

    public void refuel(double amount) {
        fuelLevel = Math.min(100, fuelLevel + amount);
        System.out.printf("%s %s: Refuelled → %.1f%%%n", brand, model, fuelLevel);
    }

    @Override
    public void displayInfo() {
        super.displayInfo();    // call parent version first
        System.out.printf("  └─ Doors: %d | Fuel type: %s | Fuel: %.0f%%%n",
                doors, fuelType, fuelLevel);
    }
}


// ═══════════════════════════════════════════════════════════
//  CHILD CLASS – Bike  (extends Vehicle)
// ═══════════════════════════════════════════════════════════
class Bike extends Vehicle {

    private String bikeType;    // Road / Mountain / Sports
    private boolean hasHelmet;

    public Bike(String brand, String model, int year, String bikeType) {
        super(brand, model, year);
        this.bikeType  = bikeType;
        this.hasHelmet = false;
        System.out.printf("[Bike] Extended: %s bike%n", bikeType);
    }

    // Bikes don't have a conventional engine – override startEngine
    @Override
    public void startEngine() {
        // Bikes just need to pedal or kickstart
        engineRunning = true;
        System.out.printf("%s %s: Ready to ride!%n", brand, model);
    }

    @Override
    public void accelerate(double amount) {
        // Bikes reach top speed faster but have a lower cap
        double cappedAmount = Math.min(amount, 150 - speed);
        super.accelerate(cappedAmount);
        if (cappedAmount < amount) {
            System.out.printf("  [Bike] Top speed capped at 150 km/h%n");
        }
    }

    public void wearHelmet() {
        hasHelmet = true;
        System.out.println(brand + " " + model + ": Helmet on. Ride safe!");
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.printf("  └─ Type: %s | Helmet: %s%n",
                bikeType, hasHelmet ? "Yes ✔" : "No ✘");
    }
}


// ═══════════════════════════════════════════════════════════
//  GRANDCHILD CLASS – ElectricCar  (extends Car)
//  Multi-level inheritance: ElectricCar → Car → Vehicle
// ═══════════════════════════════════════════════════════════
class ElectricCar extends Car {

    private double batteryLevel;    // 0–100%
    private int    rangeKm;

    public ElectricCar(String brand, String model, int year, int rangeKm) {
        super(brand, model, year, 4, "Electric");  // calls Car constructor
        this.batteryLevel = 100.0;
        this.rangeKm      = rangeKm;
        System.out.printf("[ElectricCar] Extended: %d km range%n", rangeKm);
    }

    @Override
    public void startEngine() {
        engineRunning = true;
        System.out.printf("%s %s: Silently powered on. 🔋 %.0f%%%n",
                brand, model, batteryLevel);
    }

    public void chargeBattery(double amount) {
        batteryLevel = Math.min(100, batteryLevel + amount);
        System.out.printf("%s %s: Charging → %.1f%%%n", brand, model, batteryLevel);
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.printf("  └─ Battery: %.0f%% | Range: %d km%n",
                batteryLevel, rangeKm);
    }
}


// ═══════════════════════════════════════════════════════════
//  MAIN
// ═══════════════════════════════════════════════════════════
public class Inheritance {

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║    DAY 11 – INHERITANCE DEMO             ║");
        System.out.println("╚══════════════════════════════════════════╝");

        // ── Creating objects ─────────────────────────────────
        System.out.println("\n--- Creating Vehicles ---");
        Vehicle generic  = new Vehicle("Generic", "Transport", 2020);
        Car     myCar    = new Car("Toyota", "Camry", 2022, 4, "Petrol");
        Bike    myBike   = new Bike("Royal Enfield", "Meteor", 2023, "Cruiser");
        ElectricCar ev   = new ElectricCar("Tesla", "Model 3", 2023, 500);

        // ── Driving demos ─────────────────────────────────────
        System.out.println("\n--- Driving Car ---");
        myCar.startEngine();
        myCar.accelerate(60);
        myCar.honk();
        myCar.brake(20);
        myCar.stopEngine();

        System.out.println("\n--- Riding Bike ---");
        myBike.wearHelmet();
        myBike.startEngine();
        myBike.accelerate(80);
        myBike.accelerate(100);   // will be capped

        System.out.println("\n--- Driving Electric Car ---");
        ev.startEngine();
        ev.accelerate(90);
        ev.chargeBattery(10);

        // ── displayInfo shows inheritance chain ───────────────
        System.out.println("\n--- Full Info (overridden displayInfo) ---");
        myCar.displayInfo();
        System.out.println();
        myBike.displayInfo();
        System.out.println();
        ev.displayInfo();

        // ── instanceof checks ─────────────────────────────────
        System.out.println("\n--- instanceof (IS-A relationship) ---");
        System.out.println("myCar  instanceof Car:        " + (myCar     instanceof Car));
        System.out.println("myCar  instanceof Vehicle:    " + (myCar     instanceof Vehicle));
        System.out.println("ev     instanceof Car:        " + (ev        instanceof Car));
        System.out.println("ev     instanceof Vehicle:    " + (ev        instanceof Vehicle));
        System.out.println("myBike instanceof Bike:       " + (myBike    instanceof Bike));    // true!

        // ── super keyword visible in constructor chain output ─
        System.out.println("\n--- Constructor call chain for  new ElectricCar(...) ---");
        System.out.println("1. ElectricCar() calls  super(...)  → Car()");
        System.out.println("2. Car()         calls  super(...)  → Vehicle()");
        System.out.println("3. Vehicle() runs its body first.");
        System.out.println("4. Then Car() body runs.");
        System.out.println("5. Then ElectricCar() body runs.");
        System.out.println("(You can see this in the output above ↑)");
    }
}
