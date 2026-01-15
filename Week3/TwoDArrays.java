/**
 * ╔══════════════════════════════════════════════════════════╗
 *  DAY 16 – 2D Arrays
 * ╚══════════════════════════════════════════════════════════╝
 *
 *  A 2D array is an "array of arrays":
 *    int[][] grid = new int[rows][cols];
 *    grid[row][col]   ← access element at row r, column c
 *
 *  Memory layout: grid[0] is a reference to row-0 array,
 *                 grid[1] is a reference to row-1 array, etc.
 *
 *  Topics covered:
 *    ✔ Declaration & initialisation
 *    ✔ Nested loop traversal
 *    ✔ Matrix Addition       A + B
 *    ✔ Matrix Multiplication A × B  (bonus)
 *    ✔ Matrix Transpose      Aᵀ
 *    ✔ Jagged arrays         (rows of different lengths)
 *    ✔ Practical: grade book, game board
 */
import java.util.Arrays;

public class TwoDArrays {

    // ═══════════════════════════════════════════════════════
    //  HELPER – pretty print a matrix
    // ═══════════════════════════════════════════════════════
    static void printMatrix(String label, int[][] m) {
        System.out.println("  " + label + ":");
        for (int[] row : m) {
            System.out.print("  [ ");
            for (int v : row) System.out.printf("%4d ", v);
            System.out.println("]");
        }
    }

    static void printMatrix(String label, double[][] m) {
        System.out.println("  " + label + ":");
        for (double[] row : m) {
            System.out.print("  [ ");
            for (double v : row) System.out.printf("%7.2f ", v);
            System.out.println("]");
        }
    }

    // ═══════════════════════════════════════════════════════
    //  SECTION 1 – DECLARATION
    // ═══════════════════════════════════════════════════════
    static void declarationDemo() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║  1. DECLARATION & INITIALISATION     ║");
        System.out.println("╚══════════════════════════════════════╝");

        // Style 1: allocate then fill
        int[][] grid = new int[3][4];
        grid[0][0] = 1;  grid[1][1] = 5;  grid[2][3] = 9;
        printMatrix("Sparse grid (3×4)", grid);

        // Style 2: literal initialiser
        int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        printMatrix("3×3 Literal", matrix);

        // Dimensions
        System.out.println("  Rows    = " + matrix.length);
        System.out.println("  Cols    = " + matrix[0].length);
        System.out.println("  [1][2]  = " + matrix[1][2]);   // row 1, col 2 → 6

        // Jagged array (rows of different lengths)
        int[][] jagged = new int[3][];
        jagged[0] = new int[]{1};
        jagged[1] = new int[]{2, 3};
        jagged[2] = new int[]{4, 5, 6};
        System.out.println("\n  Jagged array:");
        for (int r = 0; r < jagged.length; r++) {
            System.out.print("  Row " + r + ": ");
            for (int v : jagged[r]) System.out.print(v + " ");
            System.out.println();
        }
    }

    // ═══════════════════════════════════════════════════════
    //  SECTION 2 – MATRIX ADDITION
    //  Rule: A and B must have the SAME dimensions.
    //  C[i][j] = A[i][j] + B[i][j]
    // ═══════════════════════════════════════════════════════
    static int[][] matrixAdd(int[][] A, int[][] B) {
        int rows = A.length, cols = A[0].length;
        if (B.length != rows || B[0].length != cols)
            throw new IllegalArgumentException("Matrices must have identical dimensions.");

        int[][] C = new int[rows][cols];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                C[i][j] = A[i][j] + B[i][j];
        return C;
    }

    static int[][] matrixSubtract(int[][] A, int[][] B) {
        int rows = A.length, cols = A[0].length;
        int[][] C = new int[rows][cols];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                C[i][j] = A[i][j] - B[i][j];
        return C;
    }

    static void matrixAddDemo() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║  2. MATRIX ADDITION & SUBTRACTION    ║");
        System.out.println("╚══════════════════════════════════════╝");

        int[][] A = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[][] B = {{9, 8, 7}, {6, 5, 4}, {3, 2, 1}};

        printMatrix("A", A);
        printMatrix("B", B);
        printMatrix("A + B", matrixAdd(A, B));
        printMatrix("A - B", matrixSubtract(A, B));
    }

    // ═══════════════════════════════════════════════════════
    //  SECTION 3 – MATRIX TRANSPOSE
    //  Transpose flips a matrix over its main diagonal.
    //  Aᵀ[i][j] = A[j][i]
    //  An m×n matrix becomes an n×m matrix.
    // ═══════════════════════════════════════════════════════
    static int[][] transpose(int[][] A) {
        int rows = A.length, cols = A[0].length;
        int[][] T = new int[cols][rows];   // NOTE: dimensions are swapped
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                T[j][i] = A[i][j];
        return T;
    }

    static void transposeDemo() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║  3. MATRIX TRANSPOSE                 ║");
        System.out.println("╚══════════════════════════════════════╝");

        // Square matrix
        int[][] sq = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        printMatrix("Original (3×3)", sq);
        printMatrix("Transposed (3×3)", transpose(sq));

        // Non-square: 2 rows × 4 cols → becomes 4 rows × 2 cols
        int[][] rect = {{1, 2, 3, 4}, {5, 6, 7, 8}};
        printMatrix("\nOriginal (2×4)", rect);
        printMatrix("Transposed (4×2)", transpose(rect));
    }

    // ═══════════════════════════════════════════════════════
    //  SECTION 4 – MATRIX MULTIPLICATION (Bonus)
    //  A(m×n) × B(n×p) = C(m×p)
    //  C[i][j] = sum of A[i][k] * B[k][j] for all k
    // ═══════════════════════════════════════════════════════
    static int[][] matrixMultiply(int[][] A, int[][] B) {
        int m = A.length, n = A[0].length, p = B[0].length;
        if (B.length != n)
            throw new IllegalArgumentException(
                    "A cols (" + n + ") must equal B rows (" + B.length + ")");

        int[][] C = new int[m][p];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < p; j++)
                for (int k = 0; k < n; k++)
                    C[i][j] += A[i][k] * B[k][j];
        return C;
    }

    static void multiplyDemo() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║  4. MATRIX MULTIPLICATION (Bonus)    ║");
        System.out.println("╚══════════════════════════════════════╝");

        int[][] A = {{1, 2}, {3, 4}};
        int[][] B = {{5, 6}, {7, 8}};
        printMatrix("A (2×2)", A);
        printMatrix("B (2×2)", B);
        printMatrix("A × B", matrixMultiply(A, B));

        // Verify: [1,2]·[5,7] = 5+14=19, [1,2]·[6,8] = 6+16=22
        System.out.println("  Check: C[0][0] = 1×5 + 2×7 = 19  ✔");
    }

    // ═══════════════════════════════════════════════════════
    //  SECTION 5 – PRACTICAL: GRADE BOOK
    // ═══════════════════════════════════════════════════════
    static void gradeBook() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║  5. PRACTICAL: GRADE BOOK            ║");
        System.out.println("╚══════════════════════════════════════╝");

        String[] students = {"Alice", "Bob", "Carol", "Dave"};
        String[] subjects = {"Math", "Science", "English", "History"};

        int[][] grades = {
            {92, 88, 79, 95},   // Alice
            {74, 81, 65, 70},   // Bob
            {85, 90, 88, 92},   // Carol
            {55, 62, 70, 48}    // Dave
        };

        // Header
        System.out.printf("  %-10s", "Student");
        for (String sub : subjects) System.out.printf(" %-10s", sub);
        System.out.printf(" %-8s %s%n", "Average", "Grade");
        System.out.println("  " + "─".repeat(65));

        for (int i = 0; i < students.length; i++) {
            int sum = 0;
            for (int j = 0; j < subjects.length; j++) sum += grades[i][j];
            double avg = (double) sum / subjects.length;
            char   g   = avg >= 90 ? 'A' : avg >= 80 ? 'B' : avg >= 70 ? 'C'
                        : avg >= 60 ? 'D' : 'F';

            System.out.printf("  %-10s", students[i]);
            for (int score : grades[i]) System.out.printf(" %-10d", score);
            System.out.printf(" %-8.1f %c%n", avg, g);
        }

        // Subject averages (column traversal)
        System.out.println("  " + "─".repeat(65));
        System.out.printf("  %-10s", "Avg");
        for (int j = 0; j < subjects.length; j++) {
            int sum = 0;
            for (int i = 0; i < students.length; i++) sum += grades[i][j];
            System.out.printf(" %-10.1f", (double) sum / students.length);
        }
        System.out.println();
    }

    // ═══════════════════════════════════════════════════════
    //  SECTION 6 – GAME BOARD (Tic-Tac-Toe grid)
    // ═══════════════════════════════════════════════════════
    static void gameBoardDemo() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║  6. GAME BOARD – Tic-Tac-Toe         ║");
        System.out.println("╚══════════════════════════════════════╝");

        char[][] board = {
            {'X', 'O', 'X'},
            {'O', 'X', 'O'},
            {'O', 'X', 'X'}
        };

        System.out.println("  Board:");
        for (int r = 0; r < 3; r++) {
            System.out.print("  ");
            for (int c = 0; c < 3; c++) {
                System.out.print(" " + board[r][c]);
                if (c < 2) System.out.print(" │");
            }
            System.out.println();
            if (r < 2) System.out.println("  ───┼───┼───");
        }

        // Check winner (X wins if any row/col/diag is all X)
        System.out.println("\n  " + checkWinner(board));
    }

    static String checkWinner(char[][] b) {
        // Rows
        for (int r = 0; r < 3; r++)
            if (b[r][0] != ' ' && b[r][0] == b[r][1] && b[r][1] == b[r][2])
                return "Winner: " + b[r][0] + " (row " + r + ")";
        // Columns
        for (int c = 0; c < 3; c++)
            if (b[0][c] != ' ' && b[0][c] == b[1][c] && b[1][c] == b[2][c])
                return "Winner: " + b[0][c] + " (col " + c + ")";
        // Diagonals
        if (b[0][0] != ' ' && b[0][0] == b[1][1] && b[1][1] == b[2][2])
            return "Winner: " + b[0][0] + " (diagonal \\)";
        if (b[0][2] != ' ' && b[0][2] == b[1][1] && b[1][1] == b[2][0])
            return "Winner: " + b[0][2] + " (diagonal /)";
        return "No winner yet.";
    }

    // ═══════════════════════════════════════════════════════
    //  ENTRY POINT
    // ═══════════════════════════════════════════════════════
    public static void main(String[] args) {
        declarationDemo();
        matrixAddDemo();
        transposeDemo();
        multiplyDemo();
        gradeBook();
        gameBoardDemo();
    }
}
