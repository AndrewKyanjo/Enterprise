/**
 * DAY 2 – Variables & Data Types
 * File 3 of 3: Area of Circle
 *
 * Covers: double, Math.PI constant, Math.pow(), printf formatting
 *
 * Formulas:
 *   Area        = π × r²
 *   Circumference = 2 × π × r
 */
public class AreaOfCircle {

    public static void main(String[] args) {

        // Math.PI is a built-in constant: 3.141592653589793
        double radius1 = 5.0;
        double radius2 = 7.5;
        double radius3 = 0.0;   // edge case: zero radius

        System.out.println("===== CIRCLE CALCULATOR =====");
        computeAndPrint(radius1);
        computeAndPrint(radius2);
        computeAndPrint(radius3);

        // ── Casting demo: store result as float ──────────────
        float areaAsFloat = (float)(Math.PI * radius1 * radius1);
        System.out.println("\nArea as float (less precision): " + areaAsFloat);
        System.out.println("Area as double (more precision): " +
                (Math.PI * radius1 * radius1));
    }

    // Helper method (preview of Day 6 concepts)
    static void computeAndPrint(double r) {
        double area          = Math.PI * r * r;          // π × r²
        double circumference = 2 * Math.PI * r;          // 2πr

        System.out.printf("%nRadius        : %.2f%n", r);
        System.out.printf("Area          : %.4f%n", area);
        System.out.printf("Circumference : %.4f%n", circumference);
    }
}