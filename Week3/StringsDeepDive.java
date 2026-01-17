/**
 * ╔══════════════════════════════════════════════════════════╗
 *  DAY 17 – Strings  (Important)
 * ╚══════════════════════════════════════════════════════════╝
 *
 *  Key facts about String in Java:
 *    • String is a CLASS, not a primitive
 *    • Strings are IMMUTABLE – once created, the char sequence
 *      CANNOT be changed. Every "modification" creates a new object.
 *    • String literals are stored in the String Pool (heap)
 *    • Use  equals()  to compare content, NOT  ==  (which compares references)
 *
 *  IMMUTABILITY explained:
 *    String s = "Hello";
 *    s = s + " World";      ← s now points to a NEW String object
 *                              "Hello" is abandoned (eligible for GC)
 *    Doing this in a loop → lots of garbage objects → use StringBuilder!
 *
 *  Topics covered:
 *    ✔ String methods (20+)
 *    ✔ String comparison pitfalls
 *    ✔ String.format() & printf
 *    ✔ StringBuilder – mutable string building
 *    ✔ StringBuffer   – thread-safe StringBuilder
 *    ✔ Practical exercises: palindrome, anagram, word count
 */
public class StringsDeepDive {

    // ═══════════════════════════════════════════════════════
    //  SECTION 1 – CORE METHODS
    // ═══════════════════════════════════════════════════════
    static void coreMethods() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║  1. CORE STRING METHODS              ║");
        System.out.println("╚══════════════════════════════════════╝");

        String s = "  Hello, Java World!  ";

        // Length & character access
        System.out.println("Original      : [" + s + "]");
        System.out.println("length()      : " + s.length());
        System.out.println("charAt(7)     : " + s.charAt(7));
        System.out.println("indexOf('J')  : " + s.indexOf('J'));
        System.out.println("indexOf(\"Java\"): " + s.indexOf("Java"));
        System.out.println("lastIndexOf('o'): " + s.lastIndexOf('o'));

        // Case
        System.out.println("\ntoUpperCase() : " + s.toUpperCase());
        System.out.println("toLowerCase() : " + s.toLowerCase());

        // Whitespace
        System.out.println("\ntrim()        : [" + s.trim() + "]");    // removes leading/trailing spaces
        System.out.println("strip()       : [" + s.strip() + "]");   // Unicode-aware (Java 11+)

        // Substrings
        String clean = s.trim();
        System.out.println("\nsubstring(7)  : " + clean.substring(7));       // from index 7 to end
        System.out.println("substring(7,11): " + clean.substring(7, 11));  // index 7 to 10

        // Contains / starts / ends
        System.out.println("\ncontains(\"Java\") : " + clean.contains("Java"));
        System.out.println("startsWith(\"Hello\") : " + clean.startsWith("Hello"));
        System.out.println("endsWith(\"!\")  : " + clean.endsWith("!"));

        // Replace
        System.out.println("\nreplace('l','L')  : " + clean.replace('l', 'L'));
        System.out.println("replace(\"Java\",\"Python\"): " + clean.replace("Java", "Python"));
        System.out.println("replaceAll(\"[aeiou]\",\"*\"): " + clean.replaceAll("[aeiou]", "*"));

        // Split
        String csv = "Alice,Bob,Carol,Dave";
        String[] parts = csv.split(",");
        System.out.println("\nSplit CSV → " + java.util.Arrays.toString(parts));

        // Join (Java 8+)
        String joined = String.join(" | ", parts);
        System.out.println("Joined back  → " + joined);

        // Char array conversion
        char[] chars = clean.toCharArray();
        System.out.println("toCharArray()[0] = " + chars[0]);

        // isEmpty / isBlank
        System.out.println("\n\"\"  isEmpty()  = " + "".isEmpty());
        System.out.println("\"   \" isBlank() = " + "   ".isBlank());  // Java 11+

        // repeat (Java 11+)
        System.out.println("\"Ha\".repeat(3) = " + "Ha".repeat(3));
    }

    // ═══════════════════════════════════════════════════════
    //  SECTION 2 – COMPARISON PITFALLS
    // ═══════════════════════════════════════════════════════
    static void comparisonDemo() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║  2. STRING COMPARISON PITFALLS       ║");
        System.out.println("╚══════════════════════════════════════╝");

        String a = "Java";
        String b = "Java";              // same literal → same pool object
        String c = new String("Java");  // explicitly new → separate heap object

        // == compares REFERENCES (memory address)
        System.out.println("a == b              : " + (a == b));   // true  (same pool entry)
        System.out.println("a == c              : " + (a == c));   // false (different objects)

        // equals() compares CONTENT
        System.out.println("a.equals(b)         : " + a.equals(b));  // true
        System.out.println("a.equals(c)         : " + a.equals(c));  // true ← correct

        // equalsIgnoreCase
        System.out.println("\"java\".equalsIgnoreCase(\"JAVA\"): "
                + "java".equalsIgnoreCase("JAVA"));

        // compareTo – lexicographic ordering (for sorting)
        System.out.println("\n\"Apple\".compareTo(\"Banana\") = " + "Apple".compareTo("Banana")); // negative
        System.out.println("\"Mango\".compareTo(\"Mango\")  = " + "Mango".compareTo("Mango"));   // 0
        System.out.println("\"Zebra\".compareTo(\"Apple\")  = " + "Zebra".compareTo("Apple"));   // positive

        // GOLDEN RULE: ALWAYS use equals() for String content comparison, NEVER ==
        System.out.println("\n  ⚠  RULE: Use .equals(), never == for String content!");
    }

    // ═══════════════════════════════════════════════════════
    //  SECTION 3 – IMMUTABILITY
    // ═══════════════════════════════════════════════════════
    static void immutabilityDemo() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║  3. IMMUTABILITY                     ║");
        System.out.println("╚══════════════════════════════════════╝");

        String original = "Hello";
        System.out.println("original        : " + original);
        System.out.println("original hashCode: " + System.identityHashCode(original));

        // Each operation returns a NEW String; original is unchanged
        String upper = original.toUpperCase();
        System.out.println("After toUpperCase:");
        System.out.println("  original        : " + original + "  ← UNCHANGED");
        System.out.println("  upper           : " + upper);
        System.out.println("  upper hashCode  : " + System.identityHashCode(upper)
                + "  ← DIFFERENT object");

        // String concatenation in a loop (INEFFICIENT – creates n new objects)
        System.out.println("\n  ⚠  Bad: String concat in loop");
        long t1 = System.nanoTime();
        String result = "";
        for (int i = 0; i < 10_000; i++) result += "x";
        long t2 = System.nanoTime();
        System.out.printf("  10,000 concat  : %.2f ms%n", (t2 - t1) / 1_000_000.0);

        // StringBuilder is MUTABLE – no new object per append
        System.out.println("\n  ✔  Good: StringBuilder");
        long t3 = System.nanoTime();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10_000; i++) sb.append("x");
        long t4 = System.nanoTime();
        System.out.printf("  10,000 append  : %.2f ms  (much faster!)%n",
                (t4 - t3) / 1_000_000.0);
    }

    // ═══════════════════════════════════════════════════════
    //  SECTION 4 – StringBuilder
    // ═══════════════════════════════════════════════════════
    static void stringBuilderDemo() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║  4. StringBuilder                    ║");
        System.out.println("╚══════════════════════════════════════╝");

        StringBuilder sb = new StringBuilder("Hello");
        System.out.println("Initial        : " + sb);

        sb.append(", ").append("World").append("!");
        System.out.println("append         : " + sb);

        sb.insert(5, " Beautiful");
        System.out.println("insert(5,...)  : " + sb);

        sb.delete(5, 15);
        System.out.println("delete(5,15)   : " + sb);

        sb.replace(7, 12, "Java");
        System.out.println("replace(7,12,.): " + sb);

        sb.reverse();
        System.out.println("reverse()      : " + sb);
        sb.reverse();   // reverse back

        System.out.println("length()       : " + sb.length());
        System.out.println("charAt(0)      : " + sb.charAt(0));

        sb.setCharAt(0, 'h');
        System.out.println("setCharAt(0,'h'): " + sb);

        System.out.println("indexOf(\"World\"): " + sb.indexOf("World"));

        // Convert back to immutable String
        String result = sb.toString();
        System.out.println("toString()     : " + result);
        System.out.println("(result is now immutable String)");
    }

    // ═══════════════════════════════════════════════════════
    //  SECTION 5 – STRING FORMATTING
    // ═══════════════════════════════════════════════════════
    static void formattingDemo() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║  5. STRING FORMATTING                ║");
        System.out.println("╚══════════════════════════════════════╝");

        String name   = "Alice";
        double salary = 85_750.5;
        int    age    = 28;

        // String.format – returns a formatted String
        String formatted = String.format("Name: %-10s | Age: %3d | Salary: ₹%,.2f",
                name, age, salary);
        System.out.println(formatted);

        // Format specifiers quick reference:
        // %s  – String        %-10s  left-aligned in 10 chars
        // %d  – int           %5d    right-aligned in 5 chars
        // %f  – float/double  %.2f   2 decimal places
        // %,d – int with commas
        // %n  – platform newline
        // %b  – boolean
        // %c  – char

        System.out.printf("%-15s %5s %12s%n", "Product", "Qty", "Price");
        System.out.println("─".repeat(35));
        String[][] items = {
            {"Laptop", "2", "₹85000.00"},
            {"Mouse",  "5", "₹799.00"},
            {"Keyboard","3","₹1499.00"}
        };
        for (String[] it : items)
            System.out.printf("%-15s %5s %12s%n", it[0], it[1], it[2]);
    }

    // ═══════════════════════════════════════════════════════
    //  SECTION 6 – PRACTICAL EXERCISES
    // ═══════════════════════════════════════════════════════

    static boolean isPalindrome(String s) {
        String clean = s.toLowerCase().replaceAll("[^a-z0-9]", "");
        int left = 0, right = clean.length() - 1;
        while (left < right) {
            if (clean.charAt(left) != clean.charAt(right)) return false;
            left++; right--;
        }
        return true;
    }

    static boolean isAnagram(String a, String b) {
        char[] ca = a.toLowerCase().replaceAll("\\s", "").toCharArray();
        char[] cb = b.toLowerCase().replaceAll("\\s", "").toCharArray();
        java.util.Arrays.sort(ca);
        java.util.Arrays.sort(cb);
        return java.util.Arrays.equals(ca, cb);
    }

    static int countVowels(String s) {
        return s.toLowerCase().replaceAll("[^aeiou]", "").length();
    }

    static String reverseWords(String sentence) {
        String[] words = sentence.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = words.length - 1; i >= 0; i--) {
            sb.append(words[i]);
            if (i > 0) sb.append(" ");
        }
        return sb.toString();
    }

    static String titleCase(String sentence) {
        String[] words = sentence.trim().toLowerCase().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            sb.append(Character.toUpperCase(words[i].charAt(0)))
              .append(words[i].substring(1));
            if (i < words.length - 1) sb.append(" ");
        }
        return sb.toString();
    }

    static void practicalExercises() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║  6. PRACTICAL EXERCISES              ║");
        System.out.println("╚══════════════════════════════════════╝");

        // Palindrome checker
        System.out.println("--- Palindrome Checker ---");
        String[] tests = {"racecar", "hello", "A man a plan a canal Panama", "Java"};
        for (String t : tests)
            System.out.printf("  %-35s → %s%n", "\"" + t + "\"",
                    isPalindrome(t) ? "PALINDROME ✔" : "not palindrome");

        // Anagram checker
        System.out.println("\n--- Anagram Checker ---");
        System.out.printf("  %-15s & %-15s → %s%n",
                "listen", "silent", isAnagram("listen", "silent") ? "ANAGRAM ✔" : "not anagram");
        System.out.printf("  %-15s & %-15s → %s%n",
                "hello", "world", isAnagram("hello", "world") ? "ANAGRAM ✔" : "not anagram");
        System.out.printf("  %-15s & %-15s → %s%n",
                "Astronomer", "Moon starer", isAnagram("Astronomer", "Moon starer")
                        ? "ANAGRAM ✔" : "not anagram");

        // Vowel counter
        System.out.println("\n--- Vowel Counter ---");
        String phrase = "The quick brown fox jumps over the lazy dog";
        System.out.println("  \"" + phrase + "\"");
        System.out.println("  Vowels: " + countVowels(phrase));

        // Reverse words
        System.out.println("\n--- Reverse Words ---");
        String sentence = "Java is fun and powerful";
        System.out.println("  Original : " + sentence);
        System.out.println("  Reversed : " + reverseWords(sentence));

        // Title case
        System.out.println("\n--- Title Case ---");
        String lower = "the quick brown fox";
        System.out.println("  Input  : " + lower);
        System.out.println("  Output : " + titleCase(lower));
    }

    // ═══════════════════════════════════════════════════════
    //  ENTRY POINT
    // ═══════════════════════════════════════════════════════
    public static void main(String[] args) {
        coreMethods();
        comparisonDemo();
        immutabilityDemo();
        stringBuilderDemo();
        formattingDemo();
        practicalExercises();
    }
}
