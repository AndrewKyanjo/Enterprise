
/**
 * ╔══════════════════════════════════════════════════════════╗
 *  DAY 15 – Arrays  (Deep Dive)
 * ╚══════════════════════════════════════════════════════════╝
 *
 *  An array is a FIXED-SIZE, ORDERED collection of elements
 *  of the SAME type stored in CONTIGUOUS memory.
 *
 *  Key facts:
 *    • Size is set at creation and CANNOT change
 *    • Index starts at 0, ends at length-1
 *    • Default values: int→0, double→0.0, boolean→false, Object→null
 *    • Arrays are OBJECTS in Java (stored on the heap)
 *
 *  Topics covered:
 *    ✔ Declaration & initialisation (4 styles)
 *    ✔ Iteration (for, enhanced-for, while)
 *    ✔ Linear Search
 *    ✔ Binary Search (requires sorted array)
 *    ✔ Bubble Sort
 *    ✔ Selection Sort
 *    ✔ Insertion Sort
 *    ✔ java.util.Arrays utility class
 *    ✔ varargs & array as method parameter/return
 */
import java.util.Arrays;

public class ArraysDeepDive {

    // ═══════════════════════════════════════════════════════
    //  SECTION 1 – DECLARATION & INITIALISATION
    // ═══════════════════════════════════════════════════════
    static void declarationDemo() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║  1. DECLARATION & INITIALISATION     ║");
        System.out.println("╚══════════════════════════════════════╝");

        // Style 1: declare then allocate (elements default to 0)
        int[] scores;
        scores = new int[5];
        System.out.println("Default int array  : " + Arrays.toString(scores));

        // Style 2: declare + allocate in one line
        double[] prices = new double[4];
        System.out.println("Default double array: " + Arrays.toString(prices));

        // Style 3: array literal (size inferred)
        int[] primes = {2, 3, 5, 7, 11, 13, 17, 19};
        System.out.println("Primes literal     : " + Arrays.toString(primes));

        // Style 4: new + initialiser
        String[] days = new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        System.out.println("Days array         : " + Arrays.toString(days));

        // Accessing elements
        System.out.println("\nprimes[0] = " + primes[0]);     // first
        System.out.println("primes[7] = " + primes[7]);       // last
        System.out.println("length    = " + primes.length);

        // Modify an element
        scores[2] = 99;
        System.out.println("After scores[2]=99: " + Arrays.toString(scores));

        // ArrayIndexOutOfBoundsException demo (caught safely)
        try {
            int bad = primes[99];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("\n⚠  Caught: " + e.getClass().getSimpleName()
                    + " — index 99 out of bounds for length " + primes.length);
        }
    }

    // ═══════════════════════════════════════════════════════
    //  SECTION 2 – ITERATION
    // ═══════════════════════════════════════════════════════
    static void iterationDemo() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║  2. ITERATION                        ║");
        System.out.println("╚══════════════════════════════════════╝");

        int[] data = {84, 62, 91, 45, 78, 55, 97, 33};

        // Classic for loop (when you need the index)
        System.out.print("for-loop index  : ");
        for (int i = 0; i < data.length; i++) {
            System.out.printf("[%d]%d ", i, data[i]);
        }

        // Enhanced for-each (when you don't need the index)
        System.out.print("\nfor-each        : ");
        for (int val : data) {
            System.out.print(val + " ");
        }

        // While loop (useful for unknown-length traversal)
        System.out.print("\nwhile-loop      : ");
        int i = data.length - 1;
        while (i >= 0) {
            System.out.print(data[i--] + " ");
        }  // reverse

        // Statistical pass
        int sum = 0, max = data[0], min = data[0];
        for (int v : data) {
            sum += v;
            if (v > max) {
                max = v;
            }
            if (v < min) {
                min = v;
            }
        }
        System.out.printf("%n%nSum=%-5d  Max=%-5d  Min=%-5d  Avg=%.2f%n",
                sum, max, min, (double) sum / data.length);
    }

    // ═══════════════════════════════════════════════════════
    //  SECTION 3 – SEARCHING
    // ═══════════════════════════════════════════════════════
    /**
     * LINEAR SEARCH – O(n) Checks every element until target is found or array
     * exhausted. Works on UNSORTED arrays.
     *
     * @return index of target, or -1 if not found
     */
    static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }

    /**
     * BINARY SEARCH – O(log n) Repeatedly halves the search space. Requires a
     * SORTED array.
     *
     * @return index of target, or -1 if not found
     */
    static int binarySearch(int[] arr, int target) {
        int low = 0, high = arr.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;   // avoids overflow vs (low+high)/2

            if (arr[mid] == target) {
                return mid; 
            }else if (arr[mid] < target) {
                low = mid + 1;  // target is in right half
             }else {
                high = mid - 1;  // target is in left half

                    }}
        return -1;
    }

    static void searchDemo() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║  3. SEARCHING                        ║");
        System.out.println("╚══════════════════════════════════════╝");

        int[] unsorted = {84, 62, 91, 45, 78, 55, 97, 33};
        int[] sorted = {10, 23, 35, 47, 56, 68, 79, 82, 91, 100};

        System.out.println("Array (unsorted): " + Arrays.toString(unsorted));
        int[] targets = {78, 45, 200};
        for (int t : targets) {
            int idx = linearSearch(unsorted, t);
            System.out.printf("  Linear search for %3d → %s%n",
                    t, idx >= 0 ? "found at index " + idx : "not found");
        }

        System.out.println("\nArray (sorted): " + Arrays.toString(sorted));
        int[] binTargets = {47, 100, 1, 79};
        for (int t : binTargets) {
            int idx = binarySearch(sorted, t);
            System.out.printf("  Binary search for %3d → %s%n",
                    t, idx >= 0 ? "found at index " + idx : "not found");
        }

        // Built-in
        System.out.println("\n  Arrays.binarySearch(sorted, 56) = "
                + Arrays.binarySearch(sorted, 56));
    }

    // ═══════════════════════════════════════════════════════
    //  SECTION 4 – SORTING
    // ═══════════════════════════════════════════════════════
    /**
     * Utility: clone so original is preserved for each sort demo
     */
    static int[] copy(int[] src) {
        return Arrays.copyOf(src, src.length);
    }

    /**
     * BUBBLE SORT – O(n²) Repeatedly swaps adjacent elements that are out of
     * order. Each pass "bubbles" the largest unsorted element to its place.
     * Optimised: early exit if no swaps in a pass (already sorted).
     */
    static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int pass = 0; pass < n - 1; pass++) {
            boolean swapped = false;
            for (int j = 0; j < n - 1 - pass; j++) {
                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                    swapped = true;
                }
            }
            if (!swapped) {
                break;  // already sorted → exit early

                    }}
    }

    /**
     * SELECTION SORT – O(n²) Finds the minimum element in the unsorted portion
     * and swaps it to the front. Fewest swaps of the three.
     */
    static void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            // swap arr[i] with arr[minIdx]
            int tmp = arr[minIdx];
            arr[minIdx] = arr[i];
            arr[i] = tmp;
        }
    }

    /**
     * INSERTION SORT – O(n²) worst, O(n) best Builds the sorted portion one
     * element at a time by inserting each new element into its correct
     * position. Excellent for nearly-sorted data.
     */
    static void insertionSort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int key = arr[i];   // element to insert
            int j = i - 1;
            // shift elements right until correct slot found
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    static void sortingDemo() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║  4. SORTING                          ║");
        System.out.println("╚══════════════════════════════════════╝");

        int[] original = {64, 25, 12, 22, 11, 90, 37, 54};
        System.out.println("Original : " + Arrays.toString(original));

        int[] b = copy(original);
        bubbleSort(b);
        System.out.println("Bubble   : " + Arrays.toString(b));

        int[] s = copy(original);
        selectionSort(s);
        System.out.println("Selection: " + Arrays.toString(s));

        int[] ins = copy(original);
        insertionSort(ins);
        System.out.println("Insertion: " + Arrays.toString(ins));

        // Built-in (TimSort – O(n log n))
        int[] built = copy(original);
        Arrays.sort(built);
        System.out.println("Arrays.sort (built-in): " + Arrays.toString(built));

        // Complexity summary
        System.out.println("\n  Complexity Summary:");
        System.out.println("  ┌─────────────┬──────────┬──────────┬─────────┐");
        System.out.println("  │ Algorithm   │   Best   │ Average  │  Worst  │");
        System.out.println("  ├─────────────┼──────────┼──────────┼─────────┤");
        System.out.println("  │ Bubble      │   O(n)   │  O(n²)   │  O(n²)  │");
        System.out.println("  │ Selection   │  O(n²)   │  O(n²)   │  O(n²)  │");
        System.out.println("  │ Insertion   │   O(n)   │  O(n²)   │  O(n²)  │");
        System.out.println("  │ Arrays.sort │ O(n logn)│ O(n logn)│ O(nlogn)│");
        System.out.println("  └─────────────┴──────────┴──────────┴─────────┘");
    }

    // ═══════════════════════════════════════════════════════
    //  SECTION 5 – UTILITY METHODS
    // ═══════════════════════════════════════════════════════
    static void utilityDemo() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║  5. java.util.Arrays UTILITIES       ║");
        System.out.println("╚══════════════════════════════════════╝");

        int[] a = {5, 3, 8, 1, 9, 2};
        System.out.println("Original        : " + Arrays.toString(a));

        Arrays.sort(a);
        System.out.println("sorted          : " + Arrays.toString(a));

        int[] b = Arrays.copyOf(a, 4);          // first 4 elements
        System.out.println("copyOf(a, 4)    : " + Arrays.toString(b));

        int[] c = Arrays.copyOfRange(a, 2, 5);  // index 2 to 4
        System.out.println("copyOfRange(2,5): " + Arrays.toString(c));

        int[] filled = new int[5];
        Arrays.fill(filled, 7);
        System.out.println("fill(arr, 7)    : " + Arrays.toString(filled));

        int[] x = {1, 2, 3};
        int[] y = {1, 2, 3};
        int[] z = {1, 2, 4};
        System.out.println("equals([1,2,3],[1,2,3]) : " + Arrays.equals(x, y));
        System.out.println("equals([1,2,3],[1,2,4]) : " + Arrays.equals(x, z));
    }

    // ═══════════════════════════════════════════════════════
    //  ENTRY POINT
    // ═══════════════════════════════════════════════════════
    public static void main(String[] args) {
        declarationDemo();
        iterationDemo();
        searchDemo();
        sortingDemo();
        utilityDemo();
    }
}
