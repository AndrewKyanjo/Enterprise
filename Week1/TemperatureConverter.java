/**
 * DAY 2 – Variables & Data Types
 * File 2 of 3: Temperature Converter
 *
 * Covers: double arithmetic, char, String, type usage
 *
 * Formulas:
 *   °C → °F :  F = (C × 9/5) + 32
 *   °F → °C :  C = (F − 32) × 5/9
 *   °C → K  :  K = C + 273.15
 */
public class TemperatureConverter {

    public static void main(String[] args) {

        // ── Starting values ───────────────────────────────────
        double celsius    = 100.0;   // boiling point of water
        double fahrenheit = 32.0;    // freezing point in °F
        double kelvin;

        // char used to label units (single character, single quotes)
        char cUnit = 'C';
        char fUnit = 'F';
        char kUnit = 'K';

        // ── Celsius → Fahrenheit ─────────────────────────────
        double cToF = (celsius * 9.0 / 5.0) + 32.0;
        System.out.printf("%.2f °%c  →  %.2f °%c%n",
                celsius, cUnit, cToF, fUnit);

        // ── Fahrenheit → Celsius ─────────────────────────────
        double fToC = (fahrenheit - 32.0) * 5.0 / 9.0;
        System.out.printf("%.2f °%c  →  %.2f °%c%n",
                fahrenheit, fUnit, fToC, cUnit);

        // ── Celsius → Kelvin ─────────────────────────────────
        kelvin = celsius + 273.15;
        System.out.printf("%.2f °%c  →  %.2f  %c%n",
                celsius, cUnit, kelvin, kUnit);

        // ── Quick reference table ─────────────────────────────
        System.out.println("\n--- Reference Table (°C to °F) ---");
        System.out.printf("%-10s %-10s %-10s%n", "Celsius", "Fahrenheit", "Kelvin");
        System.out.println("-------------------------------");

        double[] temps = {-40, 0, 20, 37, 100};
        for (double t : temps) {
            double f = (t * 9.0 / 5.0) + 32.0;
            double k = t + 273.15;
            System.out.printf("%-10.1f %-10.1f %-10.2f%n", t, f, k);
        }

        // ── Boolean flag to show type usage ──────────────────
        boolean isBoiling = (celsius == 100.0);
        System.out.println("\nIs the water boiling? " + isBoiling);

        // ── String concatenation vs + with numbers ───────────
        String label = "Body temperature in Fahrenheit: ";
        double bodyTemp = (37.0 * 9.0 / 5.0) + 32.0;
        System.out.println(label + bodyTemp);          // String + double → concat
    }
}