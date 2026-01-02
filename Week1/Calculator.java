/**
 * DAY 2 – Variables & Data Types
 * File 1 of 3: Calculator with 2 numbers
 *
 * Covers: int, double, String, type casting, overflow behaviour
 */
public class Calculator {

    public static void main(String[] args) {

        // ── Primitive declarations ────────────────────────────
        int    a = 2_000_000_000; // underscores are legal in numbers (readability)
        int    b = 2_000_000_000;
        double x = 15.75;
        double y = 4.20;

        // ── Basic arithmetic ─────────────────────────────────
        System.out.println("===== INTEGER CALCULATOR =====");
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println("a + b = " + (a + b));          // OVERFLOW!  wraps around
        System.out.println("a - b = " + (a - b));
        System.out.println("a * b (int) = " + (a * b));    // overflow again

        // Fix overflow by casting to long before the operation
        long safeSum = (long) a + b;
        System.out.println("a + b (long) = " + safeSum);   // correct result

        System.out.println("\n===== DOUBLE CALCULATOR =====");
        System.out.println("x = " + x);
        System.out.println("y = " + y);
        System.out.printf("x + y = %.2f%n", x + y);
        System.out.printf("x - y = %.2f%n", x - y);
        System.out.printf("x * y = %.2f%n", x * y);
        System.out.printf("x / y = %.4f%n", x / y);

        // ── Integer division vs double division ──────────────
        System.out.println("\n===== TYPE CASTING =====");
        int p = 7, q = 2;
        System.out.println("7 / 2  (int  / int)    = " + (p / q));       // 3  (truncates)
        System.out.println("7 / 2  (cast to double)= " + ((double) p / q)); // 3.5

        // Explicit narrowing cast: double → int (fractional part lost)
        double pi = 3.14159;
        int truncatedPi = (int) pi;
        System.out.println("\npi          = " + pi);
        System.out.println("(int) pi    = " + truncatedPi + "  ← fractional part DROPPED");

        // ── Overflow demonstration ────────────────────────────
        System.out.println("\n===== OVERFLOW DEMO =====");
        int maxInt = Integer.MAX_VALUE;                    // 2,147,483,647
        System.out.println("Integer.MAX_VALUE     = " + maxInt);
        System.out.println("MAX_VALUE + 1 (wraps) = " + (maxInt + 1)); // → -2147483648
        System.out.println("Integer.MIN_VALUE     = " + Integer.MIN_VALUE);
    }
}