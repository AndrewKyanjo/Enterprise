/**
 * ╔══════════════════════════════════════════════════════════╗
 *  DAY 8 – Classes & Objects
 * ╚══════════════════════════════════════════════════════════╝
 *
 *  Core concepts:
 *    ✔ Fields (instance variables)       – the DATA an object holds
 *    ✔ Methods                           – the BEHAVIOUR of an object
 *    ✔ Object creation with  new
 *    ✔ The  this  keyword                – refers to the CURRENT object
 *
 *  MENTAL MODEL:
 *    Class  = Blueprint / Cookie-cutter
 *    Object = Actual instance made from that blueprint
 *
 *    Class Car  →  myCar, yourCar, raceCar  (all separate objects)
 *    Each object has its OWN copy of every field.
 *
 *  Two classes defined here:
 *    1) Car
 *    2) Student
 *  Main class  ClassesAndObjects  drives all demos.
 */

// ═══════════════════════════════════════════════════════════
//  CLASS 1 – CAR
// ═══════════════════════════════════════════════════════════
class Car {

    // ── Fields (instance variables) ──────────────────────────
    // These belong to each individual Car OBJECT, not the class.
    String make;
    String model;
    int    year;
    String color;
    double speed;          // current speed in km/h
    double fuelLevel;      // percentage 0.0 – 100.0
    boolean engineOn;

    // ── Methods ──────────────────────────────────────────────

    /**
     * Start the engine.
     * 'this' refers to the specific Car object calling this method.
     */
    void startEngine() {
        if (this.engineOn) {
            System.out.println(this.make + " " + this.model + ": Engine is already running.");
        } else if (this.fuelLevel <= 0) {
            System.out.println(this.make + " " + this.model + ": Cannot start — no fuel!");
        } else {
            this.engineOn = true;
            System.out.println(this.make + " " + this.model + ": Engine started. Vroom!");
        }
    }

    void stopEngine() {
        if (!this.engineOn) {
            System.out.println(this.make + " " + this.model + ": Engine is already off.");
        } else {
            this.engineOn = false;
            this.speed    = 0;
            System.out.println(this.make + " " + this.model + ": Engine stopped.");
        }
    }

    /**
     * Accelerate by a given amount.
     * @param amount km/h to add
     */
    void accelerate(double amount) {
        if (!this.engineOn) {
            System.out.println("Start the engine first!");
            return;
        }
        this.speed += amount;
        // Fuel consumption: 0.5% per 10 km/h acceleration
        this.fuelLevel -= (amount / 10) * 0.5;
        if (this.fuelLevel < 0) this.fuelLevel = 0;
        System.out.printf("%s %s: Speed → %.1f km/h  |  Fuel: %.1f%%%n",
                this.make, this.model, this.speed, this.fuelLevel);
    }

    void brake(double amount) {
        this.speed = Math.max(0, this.speed - amount);
        System.out.printf("%s %s: Speed → %.1f km/h (braking)%n",
                this.make, this.model, this.speed);
    }

    void refuel(double amount) {
        this.fuelLevel = Math.min(100, this.fuelLevel + amount);
        System.out.printf("%s %s: Refuelled → %.1f%%%n",
                this.make, this.model, this.fuelLevel);
    }

    /** Display all car info in a formatted block */
    void displayInfo() {
        System.out.println("  ┌─────────────────────────────┐");
        System.out.printf ("  │  %d %s %s%n", this.year, this.make, this.model);
        System.out.printf ("  │  Color     : %s%n", this.color);
        System.out.printf ("  │  Speed     : %.1f km/h%n", this.speed);
        System.out.printf ("  │  Fuel      : %.1f%%%n", this.fuelLevel);
        System.out.printf ("  │  Engine    : %s%n", this.engineOn ? "ON" : "OFF");
        System.out.println("  └─────────────────────────────┘");
    }
}


// ═══════════════════════════════════════════════════════════
//  CLASS 2 – STUDENT
// ═══════════════════════════════════════════════════════════
class Student {

    // ── Fields ───────────────────────────────────────────────
    String   name;
    int      rollNumber;
    int      age;
    String   major;
    double[] grades;       // array of exam scores

    // ── Methods ──────────────────────────────────────────────

    /**
     * Compute the average of all grades.
     * Uses 'this.grades' to access the current student's grades array.
     */
    double calculateAverage() {
        if (this.grades == null || this.grades.length == 0) return 0;
        double sum = 0;
        for (double g : this.grades) sum += g;
        return sum / this.grades.length;
    }

    /**
     * Returns the letter grade for a given percentage.
     */
    char getLetterGrade() {
        double avg = this.calculateAverage();
        if (avg >= 90) return 'A';
        if (avg >= 80) return 'B';
        if (avg >= 70) return 'C';
        if (avg >= 60) return 'D';
        return 'F';
    }

    boolean hasPassed() {
        return this.calculateAverage() >= 50.0;
    }

    void displayReport() {
        System.out.println("  ┌─────────────────────────────┐");
        System.out.printf ("  │  Student: %-18s│%n", this.name);
        System.out.printf ("  │  Roll No : %-17d│%n", this.rollNumber);
        System.out.printf ("  │  Age     : %-17d│%n", this.age);
        System.out.printf ("  │  Major   : %-18s│%n", this.major);
        System.out.printf ("  │  Average : %-14.2f %c    │%n",
                this.calculateAverage(), this.getLetterGrade());
        System.out.printf ("  │  Status  : %-18s│%n",
                this.hasPassed() ? "PASS ✔" : "FAIL ✘");
        System.out.println("  └─────────────────────────────┘");
    }
}


// ═══════════════════════════════════════════════════════════
//  MAIN – Object creation demos
// ═══════════════════════════════════════════════════════════
public class ClassesAndObjects {

    public static void main(String[] args) {

        // ── Creating Car objects ─────────────────────────────
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║         CAR DEMO             ║");
        System.out.println("╚══════════════════════════════╝");

        // Object 1 – field assignment via dot notation
        Car myCar = new Car();          // new allocates memory on the heap
        myCar.make      = "Toyota";
        myCar.model     = "Corolla";
        myCar.year      = 2022;
        myCar.color     = "Silver";
        myCar.speed     = 0;
        myCar.fuelLevel = 80.0;
        myCar.engineOn  = false;

        // Object 2
        Car sportsCar = new Car();
        sportsCar.make      = "Ferrari";
        sportsCar.model     = "488";
        sportsCar.year      = 2023;
        sportsCar.color     = "Red";
        sportsCar.speed     = 0;
        sportsCar.fuelLevel = 100.0;
        sportsCar.engineOn  = false;

        System.out.println("\nInitial state:");
        myCar.displayInfo();
        sportsCar.displayInfo();

        System.out.println("\nDriving myCar:");
        myCar.startEngine();
        myCar.accelerate(60);
        myCar.accelerate(40);
        myCar.brake(20);
        myCar.stopEngine();

        System.out.println("\nDriving sportsCar:");
        sportsCar.startEngine();
        sportsCar.accelerate(120);
        sportsCar.accelerate(80);
        sportsCar.brake(50);

        System.out.println("\n-- Proof objects are INDEPENDENT --");
        System.out.println("myCar speed: "     + myCar.speed);       // 80
        System.out.println("sportsCar speed: " + sportsCar.speed);   // 150

        // ── Creating Student objects ─────────────────────────
        System.out.println("\n╔══════════════════════════════╗");
        System.out.println("║       STUDENT DEMO           ║");
        System.out.println("╚══════════════════════════════╝");

        Student alice = new Student();
        alice.name       = "Alice Sharma";
        alice.rollNumber = 101;
        alice.age        = 20;
        alice.major      = "Computer Science";
        alice.grades     = new double[]{88, 92, 76, 95, 84};

        Student bob = new Student();
        bob.name       = "Bob Patel";
        bob.rollNumber = 102;
        bob.age        = 21;
        bob.major      = "Mathematics";
        bob.grades     = new double[]{45, 52, 38, 60, 41};

        alice.displayReport();
        bob.displayReport();

        // ── THIS keyword illustration ────────────────────────
        System.out.println("\n-- 'this' keyword in action --");
        System.out.println("Each object's method uses 'this' to refer to ITSELF.");
        System.out.println("alice.calculateAverage() uses alice's grades: "
                + alice.calculateAverage());
        System.out.println("bob.calculateAverage()   uses bob's grades:   "
                + bob.calculateAverage());
        System.out.println("Same method code, different 'this' reference → different results.");
    }
}
