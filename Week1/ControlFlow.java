
/**
 * DAY 4 – Control Flow
 *
 * Covers:
 *   ✔ if / else if / else
 *   ✔ switch statement (classic + arrow-style)
 *   ✔ Nested conditions
 *   ✔ Ternary operator   condition ? valueIfTrue : valueIfFalse
 *
 * Two programs:
 *   1) ATM Withdrawal Simulation
 *   2) Menu-Driven Calculator
 *
 * NOTE: Both programs demonstrate control flow with hardcoded
 *       inputs so they run without interactive console input.
 *       Scanner is shown but inputs are simulated via arrays.
 */
public class ControlFlow {

    // ─────────────────────────────────────────────────────────
    //  1. ATM WITHDRAWAL SIMULATION
    // ─────────────────────────────────────────────────────────
    static void atmSimulation() {
        System.out.println("╔══════════════════════════════╗");
        System.out.println("║    ATM WITHDRAWAL SIMULATION ║");
        System.out.println("╚══════════════════════════════╝");

        double balance = 15_000.0;
        int correctPin = 4872;
        double withdrawalLimit = 10_000.0;

        // Simulate three different ATM sessions
        int[] pins = {1234, 4872, 4872};
        double[] amounts = {500.0, 20_000.0, 4_500.0};

        for (int i = 0; i < pins.length; i++) {
            double currentBalance = balance; // reset per simulation
            int enteredPin = pins[i];
            double requestAmount = amounts[i];

            System.out.printf("%n-- Session %d  |  PIN: %d  |  Request: ₹%.0f --%n",
                    i + 1, enteredPin, requestAmount);

            // ── Nested if/else ────────────────────────────────
            if (enteredPin == correctPin) {
                System.out.println("✔  PIN accepted.");

                if (requestAmount <= 0) {
                    System.out.println("✘  Invalid amount. Must be > 0.");
                } else if (requestAmount > withdrawalLimit) {
                    // Ternary to choose singular/plural
                    System.out.printf("✘  Exceeds single-transaction limit of ₹%.0f.%n",
                            withdrawalLimit);
                } else if (requestAmount > currentBalance) {
                    System.out.println("✘  Insufficient funds.");
                    System.out.printf("   Available balance: ₹%.2f%n", currentBalance);
                } else {
                    // Successful withdrawal
                    currentBalance -= requestAmount;
                    String msg = (currentBalance < 2_000.0)
                            ? "⚠  Low balance warning!"
                            : "✔  Transaction successful.";
                    System.out.printf("   Dispensing ₹%.0f%n", requestAmount);
                    System.out.printf("   Remaining balance: ₹%.2f%n", currentBalance);
                    System.out.println("   " + msg);
                }
            } else {
                System.out.println("✘  Incorrect PIN. Card blocked after 3 failures.");
            }
        }
    }

    // ─────────────────────────────────────────────────────────
    //  2. MENU-DRIVEN CALCULATOR
    // ─────────────────────────────────────────────────────────
    static void menuCalculator() {
        System.out.println("\n╔══════════════════════════════╗");
        System.out.println("║    MENU-DRIVEN CALCULATOR    ║");
        System.out.println("╚══════════════════════════════╝");

        // Hardcoded test cases: {choice, num1, num2}
        double[][] testCases = {
            {1, 12.5, 7.3},
            {2, 100, 45},
            {3, 6, 9},
            {4, 22, 7},
            {5, 16, 4}, // modulo
            {9, 0, 0} // invalid choice
        };

        for (double[] tc : testCases) {
            int choice = (int) tc[0];
            double a = tc[1];
            double b = tc[2];

            System.out.println("\n  Menu:");
            System.out.println("  1) Addition    2) Subtraction");
            System.out.println("  3) Multiply    4) Division");
            System.out.println("  5) Modulo");
            System.out.printf("  Choice: %d  |  A: %.1f  |  B: %.1f%n", choice, a, b);

            double result;
            String operation;

            // ── Switch statement ─────────────────────────────
            switch (choice) {
                case 1:
                    result = a + b;
                    operation = "Addition";
                    System.out.printf("  %s: %.2f + %.2f = %.2f%n",
                            operation, a, b, result);
                    break;

                case 2:
                    result = a - b;
                    operation = "Subtraction";
                    System.out.printf("  %s: %.2f - %.2f = %.2f%n",
                            operation, a, b, result);
                    break;

                case 3:
                    result = a * b;
                    operation = "Multiplication";
                    System.out.printf("  %s: %.2f × %.2f = %.2f%n",
                            operation, a, b, result);
                    break;

                case 4:
                    operation = "Division";
                    // Nested condition inside switch
                    if (b == 0) {
                        System.out.println("  ✘ Cannot divide by zero!");
                    } else {
                        result = a / b;
                        System.out.printf("  %s: %.2f ÷ %.2f = %.4f%n",
                                operation, a, b, result);
                    }
                    break;

                case 5:
                    operation = "Modulo";
                    if (b == 0) {
                        System.out.println("  ✘ Modulo by zero is undefined!");
                    } else {
                        result = a % b;
                        System.out.printf("  %s: %.2f %% %.2f = %.2f%n",
                                operation, a, b, result);
                    }
                    break;

                default:
                    // Ternary inside default for extra messaging
                    String tip = (choice > 5) ? "Too high." : "Too low.";
                    System.out.println("  ✘ Invalid option. " + tip + " Choose 1–5.");
            }
        }
    }

    // ─────────────────────────────────────────────────────────
    //  TERNARY OPERATOR SHOWCASE
    // ─────────────────────────────────────────────────────────
    static void ternaryShowcase() {
        System.out.println("\n===== TERNARY OPERATOR SHOWCASE =====");

        int age = 20;
        String status = (age >= 18) ? "Adult" : "Minor";
        System.out.println("Age " + age + " → " + status);

        // Nested ternary (use sparingly – hurts readability)
        int score = 73;
        String grade = (score >= 90) ? "A"
                : (score >= 80) ? "B"
                        : (score >= 70) ? "C"
                                : (score >= 60) ? "D"
                                        : "F";
        System.out.println("Score " + score + " → Grade " + grade);

        // Ternary in print directly
        int x = 42;
        System.out.println(x + " is " + (x % 2 == 0 ? "even" : "odd"));
    }

    // ─────────────────────────────────────────────────────────
    //  ENTRY POINT
    // ─────────────────────────────────────────────────────────
    public static void main(String[] args) {
        atmSimulation();
        menuCalculator();
        ternaryShowcase();
    }
}
