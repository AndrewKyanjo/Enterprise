/**
 * ╔══════════════════════════════════════════════════════════╗
 *  DAY 12 – Polymorphism
 * ╚══════════════════════════════════════════════════════════╝
 *
 *  Polymorphism = ONE interface, MANY behaviours.
 *  "poly" = many   "morph" = form
 *
 *  Two types:
 *    ✔ Compile-time (static)  → Method OVERLOADING  (Day 6)
 *    ✔ Runtime (dynamic)      → Method OVERRIDING   (TODAY)
 *
 *  KEY CONCEPTS:
 *
 *  1. Method Overriding
 *       Child class provides its OWN implementation of a parent method.
 *       Same name, same parameters, different body.
 *
 *  2. Dynamic Dispatch (Runtime polymorphism)
 *       Vehicle v = new Car();      ← reference type = Vehicle
 *                                     object type    = Car
 *       v.startEngine();            ← JVM calls Car's version AT RUNTIME
 *       The method called depends on the OBJECT TYPE, not the reference type.
 *
 *  3. Reference type vs Object type
 *       Vehicle v = new Car();
 *       v.honk();   ← COMPILE ERROR! Vehicle doesn't know about honk()
 *                     Must cast: ((Car) v).honk();
 *
 *  Hierarchy used:
 *       Shape (parent)
 *        ├── Circle
 *        ├── Rectangle
 *        └── Triangle
 */

// ═══════════════════════════════════════════════════════════
//  PARENT CLASS – Shape
// ═══════════════════════════════════════════════════════════
class Shape {

    protected String color;
    protected String name;

    public Shape(String name, String color) {
        this.name  = name;
        this.color = color;
    }

    // These will be OVERRIDDEN by every child
    public double area() {
        return 0.0;   // default meaningless value
    }

    public double perimeter() {
        return 0.0;
    }

    public void draw() {
        System.out.printf("Drawing a %s %s.%n", color, name);
    }

    @Override
    public String toString() {
        return String.format("%s[color=%s, area=%.2f, perimeter=%.2f]",
                name, color, area(), perimeter());
    }
}


// ═══════════════════════════════════════════════════════════
//  CHILD 1 – Circle
// ═══════════════════════════════════════════════════════════
class Circle extends Shape {

    private double radius;

    public Circle(String color, double radius) {
        super("Circle", color);
        this.radius = radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;    // π r²
    }

    @Override
    public double perimeter() {
        return 2 * Math.PI * radius;         // 2πr
    }

    @Override
    public void draw() {
        System.out.printf("  ◉  Drawing %s Circle (r=%.1f) | Area=%.2f | Perim=%.2f%n",
                color, radius, area(), perimeter());
    }

    public double getRadius() { return radius; }
}


// ═══════════════════════════════════════════════════════════
//  CHILD 2 – Rectangle
// ═══════════════════════════════════════════════════════════
class Rectangle extends Shape {

    protected double width;
    protected double height;

    public Rectangle(String color, double width, double height) {
        super("Rectangle", color);
        this.width  = width;
        this.height = height;
    }

    @Override
    public double area() {
        return width * height;
    }

    @Override
    public double perimeter() {
        return 2 * (width + height);
    }

    @Override
    public void draw() {
        System.out.printf("  ▬  Drawing %s Rectangle (%.1f × %.1f) | Area=%.2f | Perim=%.2f%n",
                color, width, height, area(), perimeter());
    }

    // Rectangle-specific method (not in parent)
    public boolean isSquare() {
        return width == height;
    }
}


// ═══════════════════════════════════════════════════════════
//  CHILD 3 – Triangle
// ═══════════════════════════════════════════════════════════
class Triangle extends Shape {

    private double a, b, c;   // three sides

    public Triangle(String color, double a, double b, double c) {
        super("Triangle", color);
        if (!isValid(a, b, c)) throw new IllegalArgumentException(
                "Invalid triangle: sides " + a + ", " + b + ", " + c);
        this.a = a; this.b = b; this.c = c;
    }

    private boolean isValid(double a, double b, double c) {
        return (a + b > c) && (b + c > a) && (a + c > b);
    }

    @Override
    public double perimeter() {
        return a + b + c;
    }

    @Override
    public double area() {
        // Heron's formula: √(s(s-a)(s-b)(s-c))  where s = semi-perimeter
        double s = perimeter() / 2;
        return Math.sqrt(s * (s-a) * (s-b) * (s-c));
    }

    @Override
    public void draw() {
        System.out.printf("  △  Drawing %s Triangle (%.1f, %.1f, %.1f) | Area=%.2f | Perim=%.2f%n",
                color, a, b, c, area(), perimeter());
    }

    public String getType() {
        if (a == b && b == c) return "Equilateral";
        if (a == b || b == c || a == c) return "Isosceles";
        return "Scalene";
    }
}


// ═══════════════════════════════════════════════════════════
//  EMPLOYEE HIERARCHY (polymorphism with pay calculation)
// ═══════════════════════════════════════════════════════════
class Employee {

    protected String name;
    protected int    id;

    public Employee(String name, int id) {
        this.name = name;
        this.id   = id;
    }

    // Will be overridden by each employee type
    public double calculatePay() {
        return 0.0;
    }

    public void displayPayslip() {
        System.out.printf("  %-20s (ID: %04d) | Pay: ₹%,.2f%n",
                name, id, calculatePay());
    }
}

class FullTimeEmployee extends Employee {
    private double monthlySalary;

    public FullTimeEmployee(String name, int id, double monthlySalary) {
        super(name, id);
        this.monthlySalary = monthlySalary;
    }

    @Override
    public double calculatePay() {
        return monthlySalary;           // fixed monthly salary
    }
}

class PartTimeEmployee extends Employee {
    private double hourlyRate;
    private int    hoursWorked;

    public PartTimeEmployee(String name, int id, double hourlyRate, int hoursWorked) {
        super(name, id);
        this.hourlyRate   = hourlyRate;
        this.hoursWorked  = hoursWorked;
    }

    @Override
    public double calculatePay() {
        return hourlyRate * hoursWorked;
    }
}

class Contractor extends Employee {
    private double projectFee;
    private double taxRate;   // contractors pay their own taxes

    public Contractor(String name, int id, double projectFee, double taxRate) {
        super(name, id);
        this.projectFee = projectFee;
        this.taxRate    = taxRate;
    }

    @Override
    public double calculatePay() {
        return projectFee * (1 - taxRate);    // fee minus tax
    }
}


// ═══════════════════════════════════════════════════════════
//  MAIN
// ═══════════════════════════════════════════════════════════
public class Polymorphism {

    /**
     * This method takes a SHAPE reference.
     * It doesn't know (or care) if it's a Circle, Rectangle, or Triangle.
     * The JVM dispatches to the correct draw() at RUNTIME.
     * This is Dynamic Dispatch.
     */
    static void printShapeDetails(Shape s) {
        s.draw();    // ← which draw() runs? Decided at RUNTIME based on object type
    }

    /**
     * Process ANY type of employee — dynamic dispatch on calculatePay()
     */
    static double processPay(Employee e) {
        e.displayPayslip();
        return e.calculatePay();
    }

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║    DAY 12 – POLYMORPHISM DEMO            ║");
        System.out.println("╚══════════════════════════════════════════╝");

        // ── Shape Polymorphism ────────────────────────────────
        System.out.println("\n=== SHAPE POLYMORPHISM ===");

        // Reference type = Shape  |  Object type = Circle/Rectangle/Triangle
        Shape[] shapes = {
            new Circle("Red", 5),
            new Rectangle("Blue", 6, 4),
            new Triangle("Green", 3, 4, 5),
            new Circle("Yellow", 2.5),
            new Rectangle("Purple", 7, 7)    // this one is a square
        };

        System.out.println("Drawing all shapes (dynamic dispatch):");
        for (Shape s : shapes) {
            printShapeDetails(s);    // JVM picks correct draw() at runtime
        }

        // ── Reference type vs Object type ────────────────────
        System.out.println("\n=== REFERENCE TYPE vs OBJECT TYPE ===");

        Shape s1 = new Circle("Orange", 4.0);   // Reference: Shape | Object: Circle
        System.out.println("s1.area()      = " + s1.area());      // Circle's area
        System.out.println("s1.toString()  = " + s1);

        // s1.getRadius();   ← COMPILE ERROR: Shape doesn't have getRadius()
        // Need to downcast to access Circle-specific methods
        if (s1 instanceof Circle) {
            Circle c = (Circle) s1;           // safe downcast
            System.out.println("After cast, radius = " + c.getRadius());
        }

        Rectangle r = new Rectangle("Teal", 5, 5);
        System.out.println("isSquare() = " + r.isSquare());    // Rectangle-specific method

        // ── Employee Polymorphism ─────────────────────────────
        System.out.println("\n=== EMPLOYEE PAYROLL (Polymorphism) ===");

        Employee[] staff = {
            new FullTimeEmployee("Alice Johnson",  1001, 85_000),
            new FullTimeEmployee("Bob Sharma",     1002, 72_000),
            new PartTimeEmployee("Carol White",    1003, 450, 80),
            new Contractor      ("Dev Agency",     1004, 1_50_000, 0.18),
            new PartTimeEmployee("Eve Nair",       1005, 600, 120)
        };

        System.out.println("Monthly payroll run:");
        double totalPayroll = 0;
        for (Employee e : staff) {
            totalPayroll += processPay(e);    // same call, different calculation each time
        }
        System.out.printf("\n  Total payroll: ₹%,.2f%n", totalPayroll);

        // ── Compile-time vs Runtime reminder ─────────────────
        System.out.println("\n=== SUMMARY ===");
        System.out.println("Compile-time (overloading):  resolved by COMPILER based on parameter types.");
        System.out.println("Runtime      (overriding):   resolved by JVM    based on OBJECT type.");
        System.out.println("Shape s = new Circle();  →  s.area() calls Circle.area()  [NOT Shape.area()]");
    }
}
