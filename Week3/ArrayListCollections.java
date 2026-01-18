/**
 * ╔══════════════════════════════════════════════════════════╗
 *  DAY 18 – ArrayList & Collections
 * ╚══════════════════════════════════════════════════════════╝
 *
 *  WHY ArrayList over arrays?
 *    • DYNAMIC SIZE – grows/shrinks automatically
 *    • Built-in methods for add, remove, search, sort
 *    • Works with the Collections framework
 *
 *  Array  vs  ArrayList:
 *  ┌─────────────────┬───────────────┬─────────────────────┐
 *  │                 │     Array     │     ArrayList       │
 *  ├─────────────────┼───────────────┼─────────────────────┤
 *  │ Size            │ Fixed         │ Dynamic             │
 *  │ Type            │ Primitive OK  │ Objects only*       │
 *  │ Syntax          │ int[]         │ ArrayList<Integer>  │
 *  │ Add/Remove      │ Manual shift  │ Built-in            │
 *  │ Null elements   │ Yes           │ Yes                 │
 *  │ Performance     │ Faster        │ Slight overhead     │
 *  └─────────────────┴───────────────┴─────────────────────┘
 *  * Autoboxing converts int → Integer automatically
 *
 *  Topics covered:
 *    ✔ ArrayList basics (add, get, set, remove, size)
 *    ✔ Generics <T>
 *    ✔ Iterators (3 ways to iterate)
 *    ✔ List interface
 *    ✔ Collections utility class (sort, reverse, shuffle, etc.)
 *    ✔ LinkedList vs ArrayList
 *    ✔ Stack and Queue via Deque
 *    ✔ Practical: to-do list, student roster
 */
import java.util.*;

public class ArrayListCollections {

    // ═══════════════════════════════════════════════════════
    //  SECTION 1 – ARRAYLIST BASICS
    // ═══════════════════════════════════════════════════════
    static void basics() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║  1. ARRAYLIST BASICS                 ║");
        System.out.println("╚══════════════════════════════════════╝");

        // Declaration – <Integer> is the generic type parameter
        ArrayList<String> fruits = new ArrayList<>();

        // add(element) – appends to end
        fruits.add("Mango");
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Cherry");
        System.out.println("After add    : " + fruits);

        // add(index, element) – inserts at position, shifts right
        fruits.add(1, "Grapes");
        System.out.println("add(1,Grapes): " + fruits);

        // get(index) – retrieve element
        System.out.println("get(0)       : " + fruits.get(0));
        System.out.println("get(2)       : " + fruits.get(2));

        // set(index, element) – replace
        fruits.set(2, "Papaya");
        System.out.println("set(2,Papaya): " + fruits);

        // remove(index) – removes by position
        String removed = fruits.remove(0);
        System.out.println("remove(0)    : removed=" + removed + " | list=" + fruits);

        // remove(Object) – removes first occurrence by VALUE
        fruits.remove("Cherry");
        System.out.println("remove(Cherry): " + fruits);

        // size, isEmpty, contains, indexOf
        System.out.println("size()       : " + fruits.size());
        System.out.println("isEmpty()    : " + fruits.isEmpty());
        System.out.println("contains(Apple): " + fruits.contains("Apple"));
        System.out.println("indexOf(Apple): " + fruits.indexOf("Apple"));

        // clear
        ArrayList<String> temp = new ArrayList<>(fruits);
        temp.clear();
        System.out.println("after clear  : " + temp + " isEmpty=" + temp.isEmpty());

        // Autoboxing: int ↔ Integer
        ArrayList<Integer> nums = new ArrayList<>();
        nums.add(10);          // autoboxing: int 10 → Integer(10)
        nums.add(20);
        nums.add(30);
        int val = nums.get(1); // unboxing: Integer(20) → int 20
        System.out.println("\nIntegers list : " + nums + "  get(1)=" + val);
    }

    // ═══════════════════════════════════════════════════════
    //  SECTION 2 – THREE WAYS TO ITERATE
    // ═══════════════════════════════════════════════════════
    static void iterationDemo() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║  2. ITERATION (3 WAYS)               ║");
        System.out.println("╚══════════════════════════════════════╝");

        List<String> cities = new ArrayList<>(
                Arrays.asList("Delhi", "Mumbai", "Bangalore", "Chennai", "Kolkata"));

        // Way 1: Classic for loop (when you need the index)
        System.out.print("for(index)   : ");
        for (int i = 0; i < cities.size(); i++) {
            System.out.print(i + ":" + cities.get(i) + " ");
        }

        // Way 2: Enhanced for-each (cleanest, most common)
        System.out.print("\nfor-each     : ");
        for (String city : cities) System.out.print(city + " ");

        // Way 3: Iterator object (allows safe removal during iteration)
        System.out.print("\nIterator     : ");
        Iterator<String> it = cities.iterator();
        while (it.hasNext()) {
            String city = it.next();
            System.out.print(city + " ");
        }

        // Iterator removal (removing "Mumbai" safely)
        Iterator<String> remover = cities.iterator();
        while (remover.hasNext()) {
            if (remover.next().equals("Mumbai")) remover.remove(); // safe!
        }
        System.out.println("\nAfter removing Mumbai: " + cities);

        // ListIterator – can go backwards
        System.out.print("ListIterator (reverse): ");
        ListIterator<String> lit = cities.listIterator(cities.size());
        while (lit.hasPrevious()) System.out.print(lit.previous() + " ");
        System.out.println();
    }

    // ═══════════════════════════════════════════════════════
    //  SECTION 3 – Collections UTILITY CLASS
    // ═══════════════════════════════════════════════════════
    static void collectionsUtility() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║  3. Collections UTILITY CLASS        ║");
        System.out.println("╚══════════════════════════════════════╝");

        List<Integer> nums = new ArrayList<>(Arrays.asList(34, 7, 23, 32, 5, 62, 32, 18));
        System.out.println("Original   : " + nums);

        Collections.sort(nums);
        System.out.println("sort()     : " + nums);

        Collections.reverse(nums);
        System.out.println("reverse()  : " + nums);

        Collections.shuffle(nums, new Random(42));   // seeded for reproducibility
        System.out.println("shuffle()  : " + nums);

        Collections.sort(nums);    // sort again for min/max
        System.out.println("sort()     : " + nums);
        System.out.println("min()      : " + Collections.min(nums));
        System.out.println("max()      : " + Collections.max(nums));

        Collections.fill(new ArrayList<>(nums), 0);  // fill on copy
        int freq = Collections.frequency(nums, 32);
        System.out.println("frequency(32): " + freq);

        // binarySearch (must be sorted!)
        System.out.println("binarySearch(23): index " + Collections.binarySearch(nums, 23));

        // Unmodifiable list – any modification throws UnsupportedOperationException
        List<String> immutable = Collections.unmodifiableList(
                Arrays.asList("A", "B", "C"));
        System.out.println("\nUnmodifiable: " + immutable);
        try { immutable.add("D"); }
        catch (UnsupportedOperationException e) {
            System.out.println("  ✘ Cannot modify unmodifiable list!");
        }
    }

    // ═══════════════════════════════════════════════════════
    //  SECTION 4 – LIST INTERFACE & LINKEDLIST
    // ═══════════════════════════════════════════════════════
    static void listVsLinkedList() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║  4. ArrayList vs LinkedList          ║");
        System.out.println("╚══════════════════════════════════════╝");

        // Both implement the List interface → polymorphic usage
        List<String> arrayList  = new ArrayList<>();
        List<String> linkedList = new LinkedList<>();

        for (String s : new String[]{"A", "B", "C", "D"}) {
            arrayList.add(s);
            linkedList.add(s);
        }

        System.out.println("ArrayList   : " + arrayList);
        System.out.println("LinkedList  : " + linkedList);

        // LinkedList as Queue (FIFO: first in, first out)
        Deque<String> queue = new LinkedList<>();
        queue.offer("Task1");
        queue.offer("Task2");
        queue.offer("Task3");
        System.out.println("\nQueue (FIFO): " + queue);
        System.out.println("  poll()    : " + queue.poll() + " → " + queue);

        // LinkedList as Stack (LIFO: last in, first out)
        Deque<String> stack = new LinkedList<>();
        stack.push("Page1");
        stack.push("Page2");
        stack.push("Page3");
        System.out.println("\nStack (LIFO): " + stack);
        System.out.println("  pop()     : " + stack.pop() + " → " + stack);

        System.out.println("\n  When to use which:");
        System.out.println("  ArrayList  – frequent random access (get by index)");
        System.out.println("  LinkedList – frequent insert/delete at head/middle");
    }

    // ═══════════════════════════════════════════════════════
    //  SECTION 5 – PRACTICAL: TO-DO LIST
    // ═══════════════════════════════════════════════════════
    static class ToDoList {
        private final List<String> tasks   = new ArrayList<>();
        private final List<String> done    = new ArrayList<>();

        void add(String task) {
            tasks.add(task);
            System.out.println("  ✚ Added: " + task);
        }

        void complete(String task) {
            if (tasks.remove(task)) {
                done.add(task);
                System.out.println("  ✔ Completed: " + task);
            } else {
                System.out.println("  ✘ Task not found: " + task);
            }
        }

        void display() {
            System.out.println("  Pending (" + tasks.size() + "):");
            if (tasks.isEmpty()) System.out.println("    (none)");
            else tasks.forEach(t -> System.out.println("    [ ] " + t));

            System.out.println("  Done    (" + done.size() + "):");
            done.forEach(t -> System.out.println("    [✔] " + t));
        }
    }

    static void practicalDemo() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║  5. PRACTICAL: TO-DO LIST            ║");
        System.out.println("╚══════════════════════════════════════╝");

        ToDoList todo = new ToDoList();
        todo.add("Learn ArrayList");
        todo.add("Practice sorting");
        todo.add("Build mini project");
        todo.add("Revise exceptions");

        todo.complete("Learn ArrayList");
        todo.complete("Practice sorting");
        todo.complete("Write unit tests");  // not in list

        System.out.println();
        todo.display();

        // Student roster sorted
        System.out.println("\n--- Sorted Student Roster ---");
        ArrayList<String> roster = new ArrayList<>(
                Arrays.asList("Zara", "Alice", "Mike", "Bob", "Nina", "Charlie"));
        System.out.println("Original: " + roster);
        Collections.sort(roster);
        System.out.println("Sorted  : " + roster);
        System.out.println("Reversed: ");
        roster.sort(Comparator.reverseOrder());
        System.out.println("         " + roster);
    }

    // ═══════════════════════════════════════════════════════
    //  ENTRY POINT
    // ═══════════════════════════════════════════════════════
    public static void main(String[] args) {
        basics();
        iterationDemo();
        collectionsUtility();
        listVsLinkedList();
        practicalDemo();
    }
}
