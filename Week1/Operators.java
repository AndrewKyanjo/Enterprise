/**
 * DAY 3 – Operators & Expressions
 *
 * Covers:
 *   ✔ Arithmetic operators   + - * / % 
 *   ✔ Relational operators   == != > < >= <=
 *   ✔ Logical operators      && || !
 *   ✔ Increment / Decrement  ++ --  (prefix vs postfix)
 *   ✔ Short-circuit behaviour
 *
 * Three programs in one file for convenience:
 *   1) EvenOddChecker
 *   2) SimpleInterestCalculator
 *   3) GradeEvaluator
 */
public class Operators {

    // ─────────────────────────────────────────────────────────
    //  1. EVEN / ODD CHECKER
    // ─────────────────────────────────────────────────────────
    static void evenOddChecker() {
        System.out.println("===== EVEN / ODD CHECKER =====");

        int[] numbers = {0, 1, 7, 42, -3, -8, 100};

        for (int n : numbers) {
            // Modulus (%) gives the remainder of division
            // n % 2 == 0  →  no remainder  →  even
            boolean isEven = (n % 2 == 0);
            System.out.printf("%4d  is  %s%n", n, isEven ? "EVEN" : "ODD");
        }
    }

    // ─────────────────────────────────────────────────────────
    //  2. SIMPLE INTEREST CALCULATOR
    //     SI = (Principal × Rate × Time) / 100
    // ─────────────────────────────────────────────────────────
    static void simpleInterestCalculator() {
        System.out.println("\n===== SIMPLE INTEREST CALCULATOR =====");

        double principal = 50_000.0;   // ₹ 50,000 loan
        double rate      = 8.5;        // 8.5% per annum
        double time      = 3.0;        // 3 years

        // Arithmetic
        double simpleInterest = (principal * rate * time) / 100.0;
        double totalAmount    = principal + simpleInterest;

        System.out.printf("Principal : ₹ %.2f%n",  principal);
        System.out.printf("Rate      : %.2f %%%n", rate);
        System.out.printf("Time      : %.0f years%n", time);
        System.out.printf("SI        : ₹ %.2f%n",  simpleInterest);
        System.out.printf("Total     : ₹ %.2f%n",  totalAmount);

        // Relational + logical: is the interest more than ₹10k AND rate > 5%?
        boolean highInterest = (simpleInterest > 10_000) && (rate > 5.0);
        System.out.println("High-interest loan? " + highInterest);
    }

    // ─────────────────────────────────────────────────────────
    //  3. GRADE EVALUATOR
    // ─────────────────────────────────────────────────────────
    static void gradeEvaluator() {
        System.out.println("\n===== GRADE EVALUATOR =====");

        int[] scores = {95, 82, 74, 65, 55, 40, 100, 0};

        for (int score : scores) {
            char grade;

            // Relational operators chained with logical &&
            if      (score >= 90)               grade = 'A';
            else if (score >= 80 && score < 90) grade = 'B';
            else if (score >= 70 && score < 80) grade = 'C';
            else if (score >= 60 && score < 70) grade = 'D';
            else                                grade = 'F';

            // ! (NOT) operator
            boolean passed = !(grade == 'F');

            System.out.printf("Score: %3d  →  Grade: %c  |  Passed: %b%n",
                    score, grade, passed);
        }
    }

    // ─────────────────────────────────────────────────────────
    //  OPERATORS DEEP DIVE (increment, short-circuit)
    // ─────────────────────────────────────────────────────────
    static void operatorsDeepDive() {
        System.out.println("\n===== INCREMENT / DECREMENT =====");

        int a = 5;
        System.out.println("a            = " + a);
        System.out.println("a++ (post)   = " + a++); // uses 5, THEN increments
        System.out.println("a  after     = " + a);   // now 6
        System.out.println("++a (pre)    = " + ++a); // increments to 7, THEN uses 7
        System.out.println("a  after     = " + a);   // still 7

        System.out.println("\n===== SHORT-CIRCUIT EVALUATION =====");
        int x = 10;

        // && short-circuits: if left is false, right is NEVER evaluated
        // This is safe: no division by zero because x>20 is false first
        boolean r1 = (x > 20) && (100 / x > 2);
        System.out.println("(x>20) && (100/x>2) = " + r1 + "  ← right side skipped");

        // || short-circuits: if left is true, right is NEVER evaluated
        boolean r2 = (x > 5) || (100 / 0 > 1); // 100/0 never executes!
        System.out.println("(x>5)  || (100/0>1) = " + r2 + "  ← right side skipped");

        System.out.println("\n===== COMPOUND ASSIGNMENT =====");
        int n = 20;
        n += 5;  System.out.println("n += 5  → " + n);  // 25
        n -= 3;  System.out.println("n -= 3  → " + n);  // 22
        n *= 2;  System.out.println("n *= 2  → " + n);  // 44
        n /= 4;  System.out.println("n /= 4  → " + n);  // 11
        n %= 3;  System.out.println("n %= 3  → " + n);  // 2
    }

    // ─────────────────────────────────────────────────────────
    //  ENTRY POINT
    // ─────────────────────────────────────────────────────────
    public static void main(String[] args) {
        evenOddChecker();
        simpleInterestCalculator();
        gradeEvaluator();
        operatorsDeepDive();
    }
}