
/**
 * =============================================================
 *  DAY 1 – Hello World & Deep Understanding
 * =============================================================
 *
 *  JDK  (Java Development Kit)
 *       The full toolkit for DEVELOPERS. Includes the compiler
 *       (javac), the JRE, debugger, and other dev tools.
 *       You write code → JDK compiles it.
 *
 *  JRE  (Java Runtime Environment)
 *       The environment needed to RUN already-compiled Java
 *       programs. Contains the JVM + standard class libraries.
 *       End-users typically only need the JRE.
 *
 *  JVM  (Java Virtual Machine)
 *       The engine that actually EXECUTES bytecode.
 *       It reads the .class file and translates bytecode into
 *       native machine instructions for the host OS.
 *       This is what makes Java "write once, run anywhere".
 *
 *  COMPILATION FLOW:
 *       HelloWorld.java  ──(javac)──►  HelloWorld.class  ──(JVM)──►  Output
 *       [Source code]                  [Bytecode]                   [Result]
 *
 *  WHAT IS main()?
 *       It is the entry point – the very first method the JVM
 *       looks for when launching a program.
 *       Signature breakdown:
 *         public  → JVM must be able to call it from outside
 *         static  → JVM calls it WITHOUT creating an object first
 *         void    → returns nothing to the JVM
 *         main    → the agreed-upon name the JVM searches for
 *         String[] args → command-line arguments passed at launch
 * =============================================================
 */
public class HelloWorld {

    public static void main(String[] args) {

        // ── Task 1: Print your name ──────────────────────────
        System.out.println("My name is Andrew Kyanjo");

        // ── Task 2: Print today's date ───────────────────────
        // java.time.LocalDate.now() reads the system clock
        java.time.LocalDate today = java.time.LocalDate.now();
        System.out.println("Today's date: " + today);

        // ── Task 3: Print multiple lines ─────────────────────
        System.out.println("----------------------------------");
        System.out.println("Welcome to my Java learning journey!");
        System.out.println("Day 1: Hello World");
        System.out.println("Goal : Understand how Java compiles");
        System.out.println("       and runs a program.");
        System.out.println("----------------------------------");

        /*
         * Multi-line comment example:
         * System.out.print()   → prints WITHOUT a newline
         * System.out.println() → prints WITH a newline at the end
         * System.out.printf()  → formatted print (like C's printf)
         */
        System.out.printf("Name: %-15s Date: %s%n", "Alex Johnson", today);
    }
}
