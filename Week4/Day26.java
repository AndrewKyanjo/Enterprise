// Day26.java

/**
 * Day 26: Basic Testing (Manual)
 * 
 * This file contains a simple Calculator class and a manual test harness.
 * It demonstrates testing edge cases like division by zero, empty input, etc.
 */
public class Day26 {

    // ---------- Class under test ----------
    public static class Calculator {
        public int add(int a, int b) { return a + b; }
        public int subtract(int a, int b) { return a - b; }
        public int multiply(int a, int b) { return a * b; }
        public double divide(int a, int b) {
            if (b == 0) throw new ArithmeticException("Division by zero");
            return (double) a / b;
        }
        public double average(int[] numbers) {
            if (numbers == null || numbers.length == 0) {
                throw new IllegalArgumentException("Array cannot be null or empty");
            }
            int sum = 0;
            for (int n : numbers) sum += n;
            return (double) sum / numbers.length;
        }
    }

    // ---------- Manual test methods ----------
    public static void testAdd() {
        Calculator calc = new Calculator();
        int result = calc.add(2, 3);
        System.out.println("testAdd: " + (result == 5 ? "PASS" : "FAIL (expected 5, got " + result + ")"));
    }

    public static void testDivideByZero() {
        Calculator calc = new Calculator();
        try {
            calc.divide(10, 0);
            System.out.println("testDivideByZero: FAIL (exception not thrown)");
        } catch (ArithmeticException e) {
            System.out.println("testDivideByZero: PASS (exception caught: " + e.getMessage() + ")");
        }
    }

    public static void testAverageNormal() {
        Calculator calc = new Calculator();
        double avg = calc.average(new int[]{1, 2, 3, 4, 5});
        System.out.println("testAverageNormal: " + (Math.abs(avg - 3.0) < 0.0001 ? "PASS" : "FAIL (expected 3.0, got " + avg + ")"));
    }

    public static void testAverageEmptyArray() {
        Calculator calc = new Calculator();
        try {
            calc.average(new int[0]);
            System.out.println("testAverageEmptyArray: FAIL");
        } catch (IllegalArgumentException e) {
            System.out.println("testAverageEmptyArray: PASS");
        }
    }

    public static void testAverageNullArray() {
        Calculator calc = new Calculator();
        try {
            calc.average(null);
            System.out.println("testAverageNullArray: FAIL");
        } catch (IllegalArgumentException e) {
            System.out.println("testAverageNullArray: PASS");
        }
    }

    // ---------- Main test runner ----------
    public static void main(String[] args) {
        System.out.println("Running manual tests for Calculator...\n");
        testAdd();
        testDivideByZero();
        testAverageNormal();
        testAverageEmptyArray();
        testAverageNullArray();
    }
}