
import java.util.Scanner;

/**
 * ╔══════════════════════════════════════════════════════════╗ DAY 7 – Mini
 * Project 1: CLI Number Utility Tool Week 1 Capstone — combines Loops + Methods
 * + Conditions ╚══════════════════════════════════════════════════════════╝
 *
 * Menu: 1. Check if a number is prime 2. Calculate factorial 3. Reverse a
 * number 4. Exit
 *
 * Design decisions: • Every feature lives in its own static method • The main
 * loop drives the menu (do-while keeps it alive) • Input validation handled
 * with while-loops • All logic reuses patterns from Days 3–6
 */
public class NumberUtility {

    // ═══════════════════════════════════════════════════════
    //  FEATURE 1 – PRIME CHECKER
    // ═══════════════════════════════════════════════════════
    /**
     * Returns true if num is a prime number. Algorithm: trial division up to
     * √num (most efficient basic approach).
     */
    static boolean isPrime(int num) {
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
     * Prints a detailed prime-check result with explanation.
     */
    static void checkPrime(int num) {
        System.out.println("\n--- Prime Check ---");
        if (num < 2) {
            System.out.printf("  %d is NOT prime. (Prime numbers must be ≥ 2)%n", num);
            return;
        }

        if (isPrime(num)) {
            System.out.printf("  ✔  %d is PRIME%n", num);
            System.out.printf("     Divisible only by 1 and %d.%n", num);
        } else {
            System.out.printf("  ✘  %d is NOT prime%n", num);
            // Find and print the factors to explain why
            System.out.print("     Factors: ");
            for (int i = 1; i <= num; i++) {
                if (num % i == 0) {
                    System.out.print(i + " ");
                }
            }
            System.out.println();
        }

        // Bonus: show next prime after num
        int next = num + 1;
        while (!isPrime(next)) {
            next++;
        }
        System.out.printf("     Next prime after %d → %d%n", num, next);
    }

    // ═══════════════════════════════════════════════════════
    //  FEATURE 2 – FACTORIAL CALCULATOR
    // ═══════════════════════════════════════════════════════
    /**
     * Computes n! iteratively using a for-loop. Returns long to handle larger
     * values (up to 20!).
     *
     * @throws IllegalArgumentException for negative input
     */
    static long factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial undefined for negatives.");
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
     * Prints factorial with the full expansion string. e.g. 5! = 5 × 4 × 3 × 2
     * × 1 = 120
     */
    static void calculateFactorial(int n) {
        System.out.println("\n--- Factorial ---");

        if (n < 0) {
            System.out.println("  ✘  Factorial is not defined for negative numbers.");
            return;
        }
        if (n > 20) {
            System.out.println("  ⚠  Input too large. Maximum supported: 20 (long overflow beyond that).");
            return;
        }

        // Build expansion string  "5 × 4 × 3 × 2 × 1"
        StringBuilder expansion = new StringBuilder();
        if (n == 0) {
            expansion.append("1  (by convention)");
        } else {
            for (int i = n; i >= 1; i--) {
                expansion.append(i);
                if (i > 1) {
                    expansion.append(" × ");
                }
            }
        }

        long result = factorial(n);
        System.out.printf("  %d! = %s = %,d%n", n, expansion, result);

        // Bonus: table from 0! to n!
        if (n > 1) {
            System.out.println("\n  Full table:");
            for (int i = 0; i <= n; i++) {
                System.out.printf("    %2d! = %,d%n", i, factorial(i));
            }
        }
    }

    // ═══════════════════════════════════════════════════════
    //  FEATURE 3 – NUMBER REVERSER
    // ═══════════════════════════════════════════════════════
    /**
     * Reverses the digits of an integer. Works for negatives too (preserves the
     * minus sign).
     *
     * Algorithm: Take last digit via % 10 Build reversed number by reversed =
     * reversed * 10 + digit Remove last digit via / 10 Repeat until number = 0
     */
    static long reverseNumber(int num) {
        boolean negative = (num < 0);
        long n = Math.abs((long) num);
        long reversed = 0;

        while (n != 0) {
            long digit = n % 10;          // extract rightmost digit
            reversed = reversed * 10 + digit;   // shift left and append
            n = n / 10;          // remove rightmost digit
        }
        return negative ? -reversed : reversed;
    }

    /**
     * Prints the reversal with digit-by-digit trace.
     */
    static void reverseNum(int num) {
        System.out.println("\n--- Number Reversal ---");

        // Show digit breakdown
        String digits = String.valueOf(Math.abs(num));
        System.out.printf("  Original : %d%n", num);
        System.out.print("  Digits   : ");
        for (char c : digits.toCharArray()) {
            System.out.print(c + " ");
        }
        System.out.println();

        long reversed = reverseNumber(num);
        System.out.printf("  Reversed : %d%n", reversed);

        // Is it a palindrome?
        boolean palindrome = (num == reversed || (long) num == reversed);
        if (palindrome && num >= 0) {
            System.out.println("  ★  This number is a PALINDROME!");
        }

        // Bonus: check if reversed is also prime
        if (reversed > 0 && reversed <= Integer.MAX_VALUE) {
            boolean revPrime = isPrime((int) reversed);
            if (revPrime) {
                System.out.printf("  ★  %d is also PRIME!%n", reversed);
            }
        }
    }

    // ═══════════════════════════════════════════════════════
    //  UI HELPERS
    // ═══════════════════════════════════════════════════════
    /**
     * Prints the main menu banner
     */
    static void printMenu() {
        System.out.println();
        System.out.println("╔═════════════════════════════════╗");
        System.out.println("║    NUMBER UTILITY TOOL  v1.0    ║");
        System.out.println("╠═════════════════════════════════╣");
        System.out.println("║  1.  Check if number is Prime   ║");
        System.out.println("║  2.  Calculate Factorial        ║");
        System.out.println("║  3.  Reverse a Number           ║");
        System.out.println("║  4.  Exit                       ║");
        System.out.println("╚═════════════════════════════════╝");
        System.out.print("  Choose an option (1-4): ");
    }

    /**
     * Safely reads an integer from the user. Loops until valid input is
     * entered.
     */
    static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextInt()) {
                int value = sc.nextInt();
                sc.nextLine(); // consume newline
                return value;
            } else {
                System.out.println("  ⚠  Invalid input. Please enter a whole number.");
                sc.nextLine(); // discard bad input
            }
        }
    }

    // ═══════════════════════════════════════════════════════
    //  ENTRY POINT
    // ═══════════════════════════════════════════════════════
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        System.out.println("Welcome to the Number Utility Tool!");
        System.out.println("Built as Week 1 capstone – Day 7.");

        // do-while: menu shows at least once, then repeats until user exits
        do {
            printMenu();

            int choice;
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine();
            } else {
                sc.nextLine();
                System.out.println("  ⚠  Please enter a number between 1 and 4.");
                continue;
            }

            // switch drives the menu
            switch (choice) {

                case 1: // Prime check
                    int n1 = readInt(sc, "\n  Enter a number to check: ");
                    checkPrime(n1);
                    break;

                case 2: // Factorial
                    int n2 = readInt(sc, "\n  Enter n to compute n! (0–20): ");
                    calculateFactorial(n2);
                    break;

                case 3: // Reverse
                    int n3 = readInt(sc, "\n  Enter a number to reverse: ");
                    reverseNum(n3);
                    break;

                case 4: // Exit
                    running = false;
                    System.out.println("\n  Thanks for using Number Utility Tool. Goodbye!");
                    break;

                default:
                    System.out.println("\n  ⚠  Invalid option. Please choose 1–4.");
            }

            // Pause before re-showing menu (improves readability in terminal)
            if (running) {
                System.out.print("\n  Press ENTER to continue...");
                sc.nextLine();
            }

        } while (running);

        sc.close();
    }
}
