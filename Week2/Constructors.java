/**
 * ╔══════════════════════════════════════════════════════════╗
 *  DAY 9 – Constructors
 * ╚══════════════════════════════════════════════════════════╝
 *
 *  A constructor is a special method that runs AUTOMATICALLY
 *  when you write  new ClassName(...)
 *
 *  Rules:
 *    • Same name as the class
 *    • NO return type (not even void)
 *    • Can be overloaded (multiple constructors = overloading)
 *
 *  Types covered:
 *    ✔ Default constructor          – no parameters, Java provides one if
 *                                     you write none; sets fields to defaults
 *    ✔ No-arg constructor           – explicitly written, zero parameters
 *    ✔ Parameterized constructor    – takes arguments to set fields
 *    ✔ Constructor chaining         – one constructor calls another via this()
 *                                     MUST be the FIRST statement
 *
 *  Refactored from Day 8:  Car  and  Student  now use constructors
 *  instead of manual field assignment.
 */

// ═══════════════════════════════════════════════════════════
//  REFACTORED CLASS – CAR  (with constructors)
// ═══════════════════════════════════════════════════════════
class Car {

    String  make;
    String  model;
    int     year;
    String  color;
    double  speed;
    double  fuelLevel;
    boolean engineOn;

    // ── 1. No-arg constructor ─────────────────────────────────
    // Sets sensible defaults. Called when: new Car()
    Car() {
        this("Unknown", "Unknown", 2024, "White", 100.0);
        // ↑ Chains to the full parameterized constructor below (constructor chaining)
        System.out.println("[Car] No-arg constructor → chained to parameterized.");
    }

    // ── 2. Partial parameterized constructor ──────────────────
    // Caller provides make + model; rest gets defaults via chaining.
    Car(String make, String model) {
        this(make, model, 2024, "Black", 100.0);
        System.out.printf("[Car] 2-arg constructor → chained for %s %s%n", make, model);
    }

    // ── 3. Full parameterized constructor ─────────────────────
    // The "master" constructor all others chain to.
    Car(String make, String model, int year, String color, double fuelLevel) {
        // 'this.field' distinguishes the field from the parameter of the same name
        this.make      = make;
        this.model     = model;
        this.year      = year;
        this.color     = color;
        this.fuelLevel = fuelLevel;
        this.speed     = 0.0;
        this.engineOn  = false;
        System.out.printf("[Car] Full constructor called → %d %s %s%n",
                this.year, this.make, this.model);
    }

    void startEngine() {
        this.engineOn = true;
        System.out.printf("%s %s: Engine ON%n", make, model);
    }

    void accelerate(double amount) {
        if (!engineOn) { System.out.println("Start the engine first!"); return; }
        speed += amount;
        System.out.printf("%s %s: %.0f km/h%n", make, model, speed);
    }

    void displayInfo() {
        System.out.printf("  %d %s %s (%s) | Speed: %.0f km/h | Fuel: %.0f%%%n",
                year, make, model, color, speed, fuelLevel);
    }
}


// ═══════════════════════════════════════════════════════════
//  REFACTORED CLASS – STUDENT  (with constructors)
// ═══════════════════════════════════════════════════════════
class Student {

    String   name;
    int      rollNumber;
    int      age;
    String   major;
    double[] grades;

    // ── 1. No-arg constructor ─────────────────────────────────
    Student() {
        this("Unknown Student", 0, 18, "Undeclared");
    }

    // ── 2. Basic constructor (name + roll only) ───────────────
    Student(String name, int rollNumber) {
        this(name, rollNumber, 18, "Undeclared");
    }

    // ── 3. Full constructor ───────────────────────────────────
    Student(String name, int rollNumber, int age, String major) {
        this.name       = name;
        this.rollNumber = rollNumber;
        this.age        = age;
        this.major      = major;
        this.grades     = new double[]{};   // empty by default
        System.out.printf("[Student] Created: %s (Roll %d)%n", this.name, this.rollNumber);
    }

    // ── 4. Copy constructor ───────────────────────────────────
    // Creates a NEW Student object as a copy of an existing one.
    Student(Student other) {
        this(other.name + " (copy)", other.rollNumber + 1000,
             other.age, other.major);
        this.grades = other.grades.clone();  // deep copy of array
        System.out.printf("[Student] Copy constructor used for %s%n", other.name);
    }

    void setGrades(double... scores) { // varargs – accepts any number of doubles
        this.grades = scores;
    }

    double average() {
        if (grades.length == 0) return 0;
        double sum = 0;
        for (double g : grades) sum += g;
        return sum / grades.length;
    }

    void displayReport() {
        System.out.printf("  %-20s | Roll: %3d | Major: %-18s | Avg: %5.1f%n",
                name, rollNumber, major, average());
    }
}


// ═══════════════════════════════════════════════════════════
//  BONUS – BankAccount to show constructor chaining clearly
// ═══════════════════════════════════════════════════════════
class BankAccount {

    String accountHolder;
    String accountNumber;
    double balance;
    String accountType;

    // No-arg → 1-arg → 2-arg → full  (chain of responsibility pattern)
    BankAccount() {
        this("Anonymous");
    }

    BankAccount(String holder) {
        this(holder, "SAVINGS");
    }

    BankAccount(String holder, String type) {
        this(holder, type, 0.0);
    }

    BankAccount(String holder, String type, double initialBalance) {
        this.accountHolder = holder;
        this.accountType   = type;
        this.balance       = initialBalance;
        this.accountNumber = "ACC" + (int)(Math.random() * 900_000 + 100_000);
        System.out.printf("[BankAccount] Created %s account for %s with ₹%.2f%n",
                type, holder, initialBalance);
    }

    void deposit(double amount) {
        balance += amount;
        System.out.printf("  Deposited ₹%.2f | New balance: ₹%.2f%n", amount, balance);
    }

    void displayInfo() {
        System.out.printf("  %s | %s | %-10s | ₹%.2f%n",
                accountNumber, accountHolder, accountType, balance);
    }
}


// ═══════════════════════════════════════════════════════════
//  MAIN
// ═══════════════════════════════════════════════════════════
public class Constructors {

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║    DAY 9 – CONSTRUCTORS DEMO         ║");
        System.out.println("╚══════════════════════════════════════╝");

        // ── Car constructors ─────────────────────────────────
        System.out.println("\n--- CAR CONSTRUCTOR OVERLOADS ---");
        Car c1 = new Car();                                     // no-arg
        Car c2 = new Car("Honda", "Civic");                    // 2-arg
        Car c3 = new Car("BMW", "M3", 2023, "Blue", 95.0);    // full

        System.out.println("\nAll cars:");
        c1.displayInfo();
        c2.displayInfo();
        c3.displayInfo();

        // ── Student constructors + copy constructor ──────────
        System.out.println("\n--- STUDENT CONSTRUCTOR OVERLOADS ---");
        Student s1 = new Student();
        Student s2 = new Student("Alice", 101, 20, "Computer Science");
        s2.setGrades(88, 92, 76, 95, 84);

        Student s3 = new Student(s2);   // copy constructor
        s3.setGrades(70, 75, 80);       // different grades

        System.out.println("\nStudent reports:");
        s1.displayReport();
        s2.displayReport();
        s3.displayReport();

        // ── BankAccount chaining ──────────────────────────────
        System.out.println("\n--- BANK ACCOUNT CONSTRUCTOR CHAINING ---");
        BankAccount acc1 = new BankAccount();
        BankAccount acc2 = new BankAccount("Raj Kumar");
        BankAccount acc3 = new BankAccount("Priya Singh", "CURRENT");
        BankAccount acc4 = new BankAccount("Rohan Mehta", "SAVINGS", 5000.0);

        acc4.deposit(2500);

        System.out.println("\nAll accounts:");
        acc1.displayInfo();
        acc2.displayInfo();
        acc3.displayInfo();
        acc4.displayInfo();

        // ── Constructor chaining summary ─────────────────────
        System.out.println("\n--- CONSTRUCTOR CHAINING FLOW ---");
        System.out.println("new BankAccount()");
        System.out.println("  → this(\"Anonymous\")");
        System.out.println("    → this(\"Anonymous\", \"SAVINGS\")");
        System.out.println("      → this(\"Anonymous\", \"SAVINGS\", 0.0)");
        System.out.println("        → sets all fields  [ONLY this last one runs the actual code]");
        System.out.println("\nKey rule: this() MUST be the FIRST statement in a constructor.");
    }
}
