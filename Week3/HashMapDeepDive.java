/**
 * ╔══════════════════════════════════════════════════════════╗
 *  DAY 19 – HashMap
 * ╚══════════════════════════════════════════════════════════╝
 *
 *  HashMap<K, V> stores KEY → VALUE pairs.
 *    • Keys are UNIQUE (each key maps to exactly one value)
 *    • Values CAN be duplicated
 *    • NO guaranteed insertion order (use LinkedHashMap for order)
 *    • get/put/remove in O(1) average time  ← the main advantage
 *    • Allows one null key, multiple null values
 *
 *  Internal mechanism (simplified):
 *    key.hashCode() → bucket index → linked list/tree in bucket
 *    If two keys hash to the same bucket → "collision" → chained
 *
 *  Map family:
 *    HashMap        – fastest, no order
 *    LinkedHashMap  – insertion order preserved
 *    TreeMap        – keys sorted (natural or custom Comparator)
 *
 *  Topics covered:
 *    ✔ put, get, remove, containsKey/Value, size
 *    ✔ Iteration (keySet, values, entrySet)
 *    ✔ getOrDefault, putIfAbsent, merge, compute
 *    ✔ LinkedHashMap & TreeMap
 *    ✔ Practical 1: Contact Book
 *    ✔ Practical 2: Word Frequency Counter
 */
import java.util.*;

public class HashMapDeepDive {

    // ═══════════════════════════════════════════════════════
    //  SECTION 1 – BASICS
    // ═══════════════════════════════════════════════════════
    static void basics() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║  1. HASHMAP BASICS                   ║");
        System.out.println("╚══════════════════════════════════════╝");

        HashMap<String, Integer> scores = new HashMap<>();

        // put(key, value) – adds or OVERWRITES
        scores.put("Alice", 92);
        scores.put("Bob", 78);
        scores.put("Carol", 85);
        scores.put("Dave", 91);
        scores.put("Alice", 95);  // overwrites Alice's old score
        System.out.println("Map      : " + scores);

        // get(key) – returns value, or null if key absent
        System.out.println("get Alice: " + scores.get("Alice"));
        System.out.println("get Eve  : " + scores.get("Eve"));   // null

        // getOrDefault – safe fallback
        System.out.println("getOrDefault(Eve, 0): " + scores.getOrDefault("Eve", 0));

        // containsKey / containsValue
        System.out.println("containsKey(Bob)   : " + scores.containsKey("Bob"));
        System.out.println("containsValue(85)  : " + scores.containsValue(85));

        // size
        System.out.println("size()             : " + scores.size());

        // remove(key)
        scores.remove("Dave");
        System.out.println("after remove Dave  : " + scores);

        // putIfAbsent – only puts if key is NOT already present
        scores.putIfAbsent("Alice", 0);   // won't overwrite Alice=95
        scores.putIfAbsent("Eve",  88);   // Eve is new → inserted
        System.out.println("after putIfAbsent  : " + scores);
    }

    // ═══════════════════════════════════════════════════════
    //  SECTION 2 – ITERATION
    // ═══════════════════════════════════════════════════════
    static void iterationDemo() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║  2. ITERATION                        ║");
        System.out.println("╚══════════════════════════════════════╝");

        Map<String, String> capitals = new HashMap<>();
        capitals.put("India", "New Delhi");
        capitals.put("Japan", "Tokyo");
        capitals.put("France", "Paris");
        capitals.put("Brazil", "Brasilia");
        capitals.put("Australia", "Canberra");

        // Way 1: keySet() – iterate over keys only
        System.out.print("keySet()   : ");
        for (String country : capitals.keySet()) {
            System.out.print(country + " ");
        }

        // Way 2: values() – iterate over values only
        System.out.print("\nvalues()   : ");
        for (String capital : capitals.values()) {
            System.out.print(capital + " ");
        }

        // Way 3: entrySet() – best when you need BOTH key AND value
        System.out.println("\nentrySet() (best practice):");
        for (Map.Entry<String, String> entry : capitals.entrySet()) {
            System.out.printf("  %-12s → %s%n", entry.getKey(), entry.getValue());
        }

        // Way 4: forEach lambda (Java 8+, most concise)
        System.out.println("forEach():");
        capitals.forEach((country, capital) ->
                System.out.printf("  %-12s → %s%n", country, capital));
    }

    // ═══════════════════════════════════════════════════════
    //  SECTION 3 – ADVANCED OPERATIONS
    // ═══════════════════════════════════════════════════════
    static void advancedOps() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║  3. ADVANCED OPERATIONS              ║");
        System.out.println("╚══════════════════════════════════════╝");

        // merge – great for aggregation / word counting
        Map<String, Integer> wordCount = new HashMap<>();
        String[] words = {"apple", "banana", "apple", "cherry", "banana", "apple"};
        for (String word : words) {
            // If key absent, put 1. Otherwise, add 1 to existing value.
            wordCount.merge(word, 1, Integer::sum);
        }
        System.out.println("merge (word count): " + wordCount);

        // compute – transform the value with a function
        Map<String, Integer> stock = new HashMap<>();
        stock.put("shirt", 10);
        stock.put("pants", 5);
        // Sell 3 shirts
        stock.compute("shirt", (k, v) -> v == null ? 0 : v - 3);
        System.out.println("compute (stock)   : " + stock);

        // replaceAll – apply a function to every value
        Map<String, Integer> prices = new HashMap<>();
        prices.put("Coffee", 100);
        prices.put("Tea",    50);
        prices.put("Juice",  80);
        prices.replaceAll((item, price) -> (int)(price * 1.1)); // 10% price hike
        System.out.println("replaceAll +10%   : " + prices);
    }

    // ═══════════════════════════════════════════════════════
    //  SECTION 4 – MAP VARIANTS
    // ═══════════════════════════════════════════════════════
    static void mapVariants() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║  4. LinkedHashMap & TreeMap           ║");
        System.out.println("╚══════════════════════════════════════╝");

        // LinkedHashMap – preserves INSERTION ORDER
        Map<String, Integer> linked = new LinkedHashMap<>();
        linked.put("Banana", 3);
        linked.put("Apple",  1);
        linked.put("Cherry", 2);
        System.out.println("LinkedHashMap (insertion order): " + linked);

        // TreeMap – keys in SORTED (natural) order
        Map<String, Integer> tree = new TreeMap<>(linked);  // initialise from linked
        System.out.println("TreeMap     (sorted by key)    : " + tree);

        // TreeMap with reverse order comparator
        Map<String, Integer> reversed = new TreeMap<>(Comparator.reverseOrder());
        reversed.putAll(linked);
        System.out.println("TreeMap     (reverse sorted)   : " + reversed);

        // TreeMap navigation methods
        TreeMap<Integer, String> ranks = new TreeMap<>();
        ranks.put(1, "Gold");
        ranks.put(3, "Bronze");
        ranks.put(2, "Silver");
        ranks.put(5, "Participant");
        ranks.put(4, "Honourable");
        System.out.println("\nTreeMap ranks: " + ranks);
        System.out.println("firstKey()  : " + ranks.firstKey());
        System.out.println("lastKey()   : " + ranks.lastKey());
        System.out.println("floorKey(3) : " + ranks.floorKey(3));   // ≤ 3
        System.out.println("ceilingKey(3): " + ranks.ceilingKey(3)); // ≥ 3
    }

    // ═══════════════════════════════════════════════════════
    //  SECTION 5 – PRACTICAL 1: CONTACT BOOK
    // ═══════════════════════════════════════════════════════
    static class ContactBook {

        // HashMap: name → phone number
        private final Map<String, String> contacts = new LinkedHashMap<>();

        void add(String name, String phone) {
            String old = contacts.put(name, phone);
            if (old != null) System.out.printf("  Updated %s: %s → %s%n", name, old, phone);
            else             System.out.printf("  Added   %s: %s%n", name, phone);
        }

        void remove(String name) {
            if (contacts.remove(name) != null) System.out.println("  Deleted: " + name);
            else System.out.println("  Not found: " + name);
        }

        void search(String name) {
            String phone = contacts.get(name);
            if (phone != null) System.out.printf("  Found: %s → %s%n", name, phone);
            else               System.out.println("  Contact not found: " + name);
        }

        void display() {
            System.out.println("  ┌───────────────────────────────┐");
            System.out.printf ("  │  Contact Book (%2d contacts)   │%n", contacts.size());
            System.out.println("  ├───────────────────────────────┤");
            contacts.forEach((name, phone) ->
                    System.out.printf("  │  %-15s %13s │%n", name, phone));
            System.out.println("  └───────────────────────────────┘");
        }
    }

    // ═══════════════════════════════════════════════════════
    //  SECTION 6 – PRACTICAL 2: WORD FREQUENCY COUNTER
    // ═══════════════════════════════════════════════════════
    static void wordFrequencyCounter(String text) {
        System.out.println("\n--- Word Frequency Counter ---");
        System.out.println("  Text: \"" + text.substring(0, Math.min(60, text.length())) + "...\"");

        // Clean and split
        String[] words = text.toLowerCase()
                .replaceAll("[^a-z\\s]", "")   // remove punctuation
                .trim().split("\\s+");

        // Count using merge
        Map<String, Integer> freq = new HashMap<>();
        for (String word : words) freq.merge(word, 1, Integer::sum);

        // Sort by frequency (descending) using TreeMap + custom sort
        List<Map.Entry<String, Integer>> sorted = new ArrayList<>(freq.entrySet());
        sorted.sort((a, b) -> b.getValue() - a.getValue());   // descending by count

        System.out.printf("  %-15s %s%n", "Word", "Count");
        System.out.println("  " + "─".repeat(25));
        int shown = 0;
        for (Map.Entry<String, Integer> e : sorted) {
            System.out.printf("  %-15s %d%n", e.getKey(), e.getValue());
            if (++shown == 10) { System.out.println("  ... (top 10 shown)"); break; }
        }
        System.out.println("  Unique words: " + freq.size() + " | Total words: " + words.length);
    }

    // ═══════════════════════════════════════════════════════
    //  ENTRY POINT
    // ═══════════════════════════════════════════════════════
    public static void main(String[] args) {
        basics();
        iterationDemo();
        advancedOps();
        mapVariants();

        // Contact book demo
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║  5. PRACTICAL: CONTACT BOOK          ║");
        System.out.println("╚══════════════════════════════════════╝");
        ContactBook cb = new ContactBook();
        cb.add("Alice Johnson",  "+91-9876543210");
        cb.add("Bob Sharma",     "+91-9123456789");
        cb.add("Carol White",    "+1-555-0101");
        cb.add("Dave Patel",     "+91-9988776655");
        cb.add("Alice Johnson",  "+91-9876543999");  // update
        cb.search("Bob Sharma");
        cb.search("Eve");
        cb.remove("Carol White");
        cb.display();

        // Word frequency counter
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║  6. PRACTICAL: WORD FREQUENCY        ║");
        System.out.println("╚══════════════════════════════════════╝");
        String sampleText = "To be or not to be that is the question "
                + "Whether tis nobler in the mind to suffer "
                + "the slings and arrows of outrageous fortune "
                + "or to take arms against a sea of troubles "
                + "and by opposing end them to die to sleep "
                + "no more and by a sleep to say we end "
                + "the heartache and the thousand natural shocks "
                + "that flesh is heir to tis a consummation devoutly to be wished";
        wordFrequencyCounter(sampleText);
    }
}
