
/**
 * DAY 5 – Loops  (VERY IMPORTANT)
 *
 * Covers:
 *   ✔ for loop          – when you know the iteration count
 *   ✔ while loop        – when you loop until a condition changes
 *   ✔ do-while loop     – executes body AT LEAST once
 *   ✔ break             – exit loop immediately
 *   ✔ continue          – skip current iteration, go to next
 *   ✔ nested loops      – loop inside a loop
 *
 * Three programs:
 *   1) Multiplication Table Generator
 *   2) Prime Number Checker
 *   3) Factorial Calculator
 */
public class Loops {

    // ─────────────────────────────────────────────────────────
    //  1. MULTIPLICATION TABLE GENERATOR
    //     Uses: for loop, nested for loop
    // ─────────────────────────────────────────────────────────
    static void multiplicationTable() {
        System.out.println("===== MULTIPLICATION TABLE (1–10) =====\n");

        // Print header row
        System.out.print("    ");
        for (int col = 1; col <= 10; col++) {
            System.out.printf("%5d", col);
        }
        System.out.println();
        System.out.println("    " + "─────".repeat(10));

        // Nested for: outer = row, inner = column
        for (int row = 1; row <= 10; row++) {
            System.out.printf("%2d |", row);               // row label
            for (int col = 1; col <= 10; col++) {
                System.out.printf("%5d", row * col);       // product
            }
            System.out.println();
        }

        // Single table for a specific number using while loop
        System.out.println("\n--- Table of 7 (while loop) ---");
        int n = 7;
        int i = 1;
        while (i <= 12) {
            System.out.printf("%2d × %2d = %3d%n", n, i, n * i);
            i++;   // don't forget – missing this = infinite loop!
        }
    }

    // ─────────────────────────────────────────────────────────
    //  2. PRIME NUMBER CHECKER
    //     Uses: for loop, break, continue
    //
    //  A prime number is divisible ONLY by 1 and itself.
    //  Algorithm: check divisors from 2 up to √n.
    //  If any divides evenly → NOT prime. Break early.
    // ─────────────────────────────────────────────────────────
    static boolean isPrime(int num) {
        if (num < 2) {
            return false;           // 0 and 1 are NOT prime

                }if (num == 2) {
            return true;           // 2 is the only even prime

                }if (num % 2 == 0) {
            return false;      // all other even numbers → not prime
        }
        // Only check odd divisors up to √num  (optimisation)
        for (int d = 3; d <= Math.sqrt(num); d += 2) {
            if (num % d == 0) {
                return false;   // found a divisor → break early via return
            }
        }
        return true;
    }

    static void primesUpTo(int limit) {
        System.out.printf("\n===== PRIME NUMBERS UP TO %d =====%n", limit);

        int count = 0;
        // for loop + continue: skip non-primes, print primes
        for (int num = 2; num <= limit; num++) {
            if (!isPrime(num)) {
                continue;    // skip non-prime, go to next num

                        }System.out.printf("%4d", num);
            count++;
            if (count % 10 == 0) {
                System.out.println(); // newline every 10 primes

                    }}
        System.out.printf("%n%nTotal primes found: %d%n", count);

        // Quick look-ups
        int[] checkList = {1, 2, 17, 49, 97, 100, 101};
        System.out.println("\n--- Prime check results ---");
        for (int val : checkList) {
            System.out.printf("%4d  →  %s%n", val, isPrime(val) ? "PRIME" : "Not prime");
        }
    }

    // ─────────────────────────────────────────────────────────
    //  3. FACTORIAL CALCULATOR
    //     n! = n × (n-1) × (n-2) × … × 1
    //     Uses: for loop, while, do-while
    //
    //  Note: 0! = 1  (by mathematical convention)
    //        long overflows at 21!, use BigInteger for larger
    // ─────────────────────────────────────────────────────────
    static long factorialFor(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be >= 0");
        }
        long result = 1;
        for (int i = 2; i <= n; i++) {   // start at 2 (multiplying by 1 changes nothing)
            result *= i;
        }
        return result;
    }

    static long factorialWhile(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be >= 0");
        }
        long result = 1;
        int i = n;
        while (i > 1) {
            result *= i;
            i--;
        }
        return result;
    }

    static long factorialDoWhile(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be >= 0");
        }
        if (n == 0) {
            return 1;
        }
        long result = 1;
        int i = n;
        // do-while guarantees the body runs AT LEAST once
        do {
            result *= i;
            i--;
        } while (i > 1);
        return result;
    }

    static void factorialShowcase() {
        System.out.println("\n===== FACTORIAL CALCULATOR =====");
        System.out.printf("%-6s %-20s %-20s %-20s%n",
                "n", "for-loop", "while-loop", "do-while-loop");
        System.out.println("─".repeat(66));

        for (int n = 0; n <= 15; n++) {
            System.out.printf("%-6d %-20d %-20d %-20d%n",
                    n,
                    factorialFor(n),
                    factorialWhile(n),
                    factorialDoWhile(n));
        }

        // Overflow demonstration
        System.out.println("\n--- Overflow at 21! ---");
        System.out.println("20! = " + factorialFor(20) + "  (correct)");
        System.out.println("21! = " + factorialFor(21) + "  ← OVERFLOW: long exceeded!");
    }

    // ─────────────────────────────────────────────────────────
    //  BREAK & CONTINUE SHOWCASE
    // ─────────────────────────────────────────────────────────
    static void breakContinueShowcase() {
        System.out.println("\n===== BREAK & CONTINUE =====");

        // break: stop when we find first number divisible by both 3 and 7
        System.out.print("Looking for first multiple of 3 AND 7 between 1-100: ");
        for (int i = 1; i <= 100; i++) {
            if (i % 3 == 0 && i % 7 == 0) {
                System.out.println(i);
                break;   // ← exits the loop entirely
            }
        }

        // continue: print only numbers NOT divisible by 3
        System.out.print("1-20 skipping multiples of 3: ");
        for (int i = 1; i <= 20; i++) {
            if (i % 3 == 0) {
                continue;   // ← skips to next iteration

                        }System.out.print(i + " ");
        }
        System.out.println();

        // Labeled break (break out of an outer loop from inside a nested loop)
        System.out.println("\nLabeled break (stop outer loop when inner product > 20):");
        outer:                                    // label on the outer loop
        for (int x = 1; x <= 5; x++) {
            for (int y = 1; y <= 5; y++) {
                if (x * y > 20) {
                    System.out.printf("  Breaking at x=%d, y=%d (product=%d)%n",
                            x, y, x * y);
                    break outer;               // ← breaks the OUTER loop
                }
                System.out.printf("  %d×%d=%d  ", x, y, x * y);
            }
            System.out.println();
        }
    }

    // ─────────────────────────────────────────────────────────
    //  ENTRY POINT
    // ─────────────────────────────────────────────────────────
    public static void main(String[] args) {
        multiplicationTable();
        primesUpTo(100);
        factorialShowcase();
        breakContinueShowcase();
    }
}
