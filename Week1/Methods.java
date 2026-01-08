
/**
 * DAY 6 – Methods  (CRITICAL DAY)
 *
 * Covers:
 *   ✔ Parameters & return values
 *   ✔ Method overloading          – same name, different signatures
 *   ✔ void vs non-void methods
 *   ✔ Stack memory concept
 *   ✔ Refactored versions of Day 2–5 programs
 *
 * ─── STACK MEMORY CONCEPT ────────────────────────────────────
 *
 *  Every time a method is CALLED, Java pushes a new "frame"
 *  onto the call stack.  This frame holds:
 *    • The method's local variables
 *    • The return address (where to go after the method ends)
 *
 *  When the method RETURNS, its frame is POPPED (destroyed).
 *
 *  main() calls factorial(5)
 *    Stack:  [ factorial(5) ]  ← top
 *            [ main()       ]
 *
 *  factorial(5) returns → frame popped
 *    Stack:  [ main() ]
 *
 *  If methods call themselves infinitely → StackOverflowError!
 * ─────────────────────────────────────────────────────────────
 */
public class Methods {

    // ═══════════════════════════════════════════════════════
    //  SECTION 1 – BASIC METHOD ANATOMY
    // ═══════════════════════════════════════════════════════
    /**
     * No parameters, no return value (void). Demonstrates the simplest possible
     * method.
     */
    static void greet() {
        System.out.println("Hello from a void method with no parameters!");
    }

    /**
     * One parameter, no return value.
     */
    static void greetByName(String name) {
        System.out.println("Hello, " + name + "!");
    }

    /**
     * Two parameters, returns a value.
     *
     * @param a first integer
     * @param b second integer
     * @return their sum
     */
    static int add(int a, int b) {
        return a + b;   // value travels back to the caller
    }

    // ═══════════════════════════════════════════════════════
    //  SECTION 2 – METHOD OVERLOADING
    //  Same method name, different parameter type or count.
    //  Java decides which version to call at COMPILE TIME.
    // ═══════════════════════════════════════════════════════
    /**
     * Overload 1: two ints
     */
    static int multiply(int a, int b) {
        System.out.println("  [overload] multiply(int, int)");
        return a * b;
    }

    /**
     * Overload 2: three ints
     */
    static int multiply(int a, int b, int c) {
        System.out.println("  [overload] multiply(int, int, int)");
        return a * b * c;
    }

    /**
     * Overload 3: two doubles
     */
    static double multiply(double a, double b) {
        System.out.println("  [overload] multiply(double, double)");
        return a * b;
    }

    /**
     * Overload 4: String repeat
     */
    static String multiply(String s, int times) {
        System.out.println("  [overload] multiply(String, int)");
        return s.repeat(times);
    }

    // ═══════════════════════════════════════════════════════
    //  SECTION 3 – REFACTORED DAY 2: Calculator
    // ═══════════════════════════════════════════════════════
    static double add(double a, double b) {
        return a + b;
    }

    static double subtract(double a, double b) {
        return a - b;
    }

    static double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return a / b;
    }

    // ═══════════════════════════════════════════════════════
    //  SECTION 4 – REFACTORED DAY 2: Temperature Converter
    // ═══════════════════════════════════════════════════════
    static double celsiusToFahrenheit(double c) {
        return (c * 9.0 / 5.0) + 32.0;
    }

    static double fahrenheitToCelsius(double f) {
        return (f - 32.0) * 5.0 / 9.0;
    }

    static double celsiusToKelvin(double c) {
        return c + 273.15;
    }

    // ═══════════════════════════════════════════════════════
    //  SECTION 5 – REFACTORED DAY 2: Area of Circle
    // ═══════════════════════════════════════════════════════
    static double circleArea(double radius) {
        if (radius < 0) {
            throw new IllegalArgumentException("Radius cannot be negative");
        }
        return Math.PI * radius * radius;
    }

    static double circleCircumference(double radius) {
        return 2 * Math.PI * radius;
    }

    // ═══════════════════════════════════════════════════════
    //  SECTION 6 – REFACTORED DAY 3: Even/Odd & Grade
    // ═══════════════════════════════════════════════════════
    static boolean isEven(int n) {
        return n % 2 == 0;
    }

    static double simpleInterest(double principal, double rate, double time) {
        return (principal * rate * time) / 100.0;
    }

    /**
     * Returns grade character based on score
     */
    static char getGrade(int score) {
        if (score >= 90) {
            return 'A'; 
        }else if (score >= 80) {
            return 'B'; 
        }else if (score >= 70) {
            return 'C'; 
        }else if (score >= 60) {
            return 'D'; 
        }else {
            return 'F';
        }
    }

    /**
     * Overloaded: accepts double score
     */
    static char getGrade(double score) {
        return getGrade((int) score);    // delegate to int version
    }

    // ═══════════════════════════════════════════════════════
    //  SECTION 7 – REFACTORED DAY 5: Factorial & Primes
    // ═══════════════════════════════════════════════════════
    /**
     * Computes n! iteratively.
     *
     * @param n non-negative integer
     * @return n factorial as a long
     */
    public static long factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be >= 0");
        }
        if (n == 0) {
            return 1L;
        }
        long result = 1L;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    /**
     * Checks if a number is prime.
     *
     * @param num the number to check
     * @return true if prime, false otherwise
     */
    public static boolean isPrime(int num) {
        if (num < 2) {
            return false;
        }
        if (num == 2) {
            return true;
        }
        if (num % 2 == 0) {
            return false;
        }
        for (int d = 3; d <= Math.sqrt(num); d += 2) {
            if (num % d == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Generates multiplication table for n.
     *
     * @param n the number whose table to generate
     * @param upTo print n × 1 through n × upTo
     */
    static void printMultiplicationTable(int n, int upTo) {
        System.out.printf("%n--- Multiplication table of %d (1 to %d) ---%n", n, upTo);
        for (int i = 1; i <= upTo; i++) {
            System.out.printf("%2d × %2d = %4d%n", n, i, n * i);
        }
    }

    // ═══════════════════════════════════════════════════════
    //  SECTION 8 – STACK MEMORY DEMONSTRATION
    //  Trace the call stack with a small recursive helper
    //  (recursion is a Day 8+ topic, shown here only to
    //   illustrate the stack clearly)
    // ═══════════════════════════════════════════════════════
    static void showStack(int depth) {
        System.out.println("  ".repeat(depth) + "→ showStack(" + depth + ") pushed onto stack");
        if (depth > 0) {
            showStack(depth - 1);
        }
        System.out.println("  ".repeat(depth) + "← showStack(" + depth + ") popped from stack");
    }

    // ═══════════════════════════════════════════════════════
    //  ENTRY POINT
    // ═══════════════════════════════════════════════════════
    public static void main(String[] args) {

        // ── Basic methods ─────────────────────────────────
        System.out.println("===== BASIC METHODS =====");
        greet();
        greetByName("Java Learner");
        System.out.println("add(3, 4) = " + add(3, 4));

        // ── Method overloading ────────────────────────────
        System.out.println("\n===== METHOD OVERLOADING =====");
        System.out.println("Result: " + multiply(3, 4));
        System.out.println("Result: " + multiply(2, 3, 4));
        System.out.println("Result: " + multiply(2.5, 4.0));
        System.out.println("Result: " + multiply("Ha", 3));

        // ── Refactored Calculator ─────────────────────────
        System.out.println("\n===== REFACTORED CALCULATOR =====");
        double a = 18.0, b = 6.0;
        System.out.printf("add(%.0f, %.0f)      = %.2f%n", a, b, add(a, b));
        System.out.printf("subtract(%.0f, %.0f) = %.2f%n", a, b, subtract(a, b));
        System.out.printf("multiply(%.0f, %.0f) = %.2f%n", a, b, multiply(a, b));
        System.out.printf("divide(%.0f, %.0f)   = %.2f%n", a, b, divide(a, b));

        // ── Refactored Temperature Converter ─────────────
        System.out.println("\n===== REFACTORED TEMPERATURE CONVERTER =====");
        double boiling = 100.0;
        System.out.printf("%.1f°C → %.2f°F%n", boiling, celsiusToFahrenheit(boiling));
        System.out.printf("%.1f°C → %.2f K%n", boiling, celsiusToKelvin(boiling));
        System.out.printf("212.0°F → %.2f°C%n", fahrenheitToCelsius(212.0));

        // ── Refactored Circle ─────────────────────────────
        System.out.println("\n===== REFACTORED CIRCLE =====");
        for (double r : new double[]{1, 5, 7.5}) {
            System.out.printf("r=%.1f  area=%.4f  circumference=%.4f%n",
                    r, circleArea(r), circleCircumference(r));
        }

        // ── Refactored Grade Evaluator ────────────────────
        System.out.println("\n===== REFACTORED GRADE EVALUATOR =====");
        int[] scores = {95, 83, 74, 61, 45};
        for (int s : scores) {
            System.out.printf("Score %3d → Grade %c  isEven=%b%n",
                    s, getGrade(s), isEven(s));
        }

        // ── Refactored Simple Interest ────────────────────
        System.out.println("\n===== REFACTORED SIMPLE INTEREST =====");
        double si = simpleInterest(50_000, 8.5, 3);
        System.out.printf("SI = ₹%.2f  |  Total = ₹%.2f%n", si, 50_000 + si);

        // ── Refactored Factorial ──────────────────────────
        System.out.println("\n===== REFACTORED FACTORIAL =====");
        for (int n = 0; n <= 12; n++) {
            System.out.printf("%2d! = %,d%n", n, factorial(n));
        }

        // ── Refactored Prime Checker ──────────────────────
        System.out.println("\n===== REFACTORED PRIME CHECKER =====");
        for (int n : new int[]{2, 7, 10, 13, 97, 100}) {
            System.out.printf("%4d → %s%n", n, isPrime(n) ? "PRIME" : "Not prime");
        }

        // ── Multiplication Table via method ───────────────
        printMultiplicationTable(9, 10);

        // ── Stack Memory Demo ─────────────────────────────
        System.out.println("\n===== STACK MEMORY DEMO =====");
        System.out.println("Calling showStack(3) — watch frames push & pop:\n");
        showStack(3);
    }
}
