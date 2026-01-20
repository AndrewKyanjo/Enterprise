/**
 * ╔══════════════════════════════════════════════════════════╗
 *  DAY 20 – Exception Handling
 * ╚══════════════════════════════════════════════════════════╝
 *
 *  An exception is an EVENT that disrupts the normal flow of a
 *  program. Java uses a "throw and catch" mechanism to handle them.
 *
 *  Exception hierarchy:
 *
 *         Throwable
 *        /         \
 *     Error       Exception
 *    (JVM)       /          \
 *         RuntimeException  Checked Exceptions
 *         (unchecked)       (must declare or catch)
 *
 *  Checked vs Unchecked:
 *    Checked   – compiler forces you to handle them (IOException, SQLException)
 *    Unchecked – RuntimeExceptions, don't have to catch (NullPointerException,
 *                ArithmeticException, IndexOutOfBoundsException)
 *    Error     – serious JVM problems, don't catch (StackOverflowError)
 *
 *  Keywords:
 *    try     – block where exception might occur
 *    catch   – handles the exception
 *    finally – ALWAYS runs, whether exception occurred or not
 *    throw   – manually throws an exception
 *    throws  – declares that a method might throw a checked exception
 *
 *  Topics covered:
 *    ✔ try/catch
 *    ✔ Multiple catch blocks
 *    ✔ finally block
 *    ✔ throw (manual throw)
 *    ✔ throws declaration
 *    ✔ Custom exceptions
 *    ✔ try-with-resources
 *    ✔ Exception chaining
 *    ✔ Refactored BankAccount with proper exception handling
 */

// ═══════════════════════════════════════════════════════════
//  CUSTOM EXCEPTIONS
// ═══════════════════════════════════════════════════════════

/** Base custom exception for banking operations */
class BankingException extends Exception {
    private final String errorCode;

    public BankingException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    // Exception chaining constructor
    public BankingException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() { return errorCode; }

    @Override
    public String toString() {
        return "[" + errorCode + "] " + getMessage();
    }
}

class InsufficientFundsException extends BankingException {
    private final double required;
    private final double available;

    public InsufficientFundsException(double required, double available) {
        super(String.format("Need ₹%.2f but only ₹%.2f available.",
                required, available), "ERR_INSUFFICIENT_FUNDS");
        this.required  = required;
        this.available = available;
    }

    public double getShortfall() { return required - available; }
}

class InvalidPinException extends BankingException {
    public InvalidPinException() {
        super("Incorrect PIN entered.", "ERR_INVALID_PIN");
    }
}

class AccountLockedException extends BankingException {
    public AccountLockedException(String accountNumber) {
        super("Account " + accountNumber + " is locked after 3 failed attempts.",
              "ERR_ACCOUNT_LOCKED");
    }
}

class InvalidAmountException extends BankingException {
    public InvalidAmountException(double amount) {
        super("Invalid amount: ₹" + amount + ". Must be positive.", "ERR_INVALID_AMOUNT");
    }
}

/** Custom runtime exception for validation errors */
class ValidationException extends RuntimeException {
    public ValidationException(String field, String reason) {
        super("Validation failed for '" + field + "': " + reason);
    }
}


// ═══════════════════════════════════════════════════════════
//  REFACTORED BankAccount with exceptions
// ═══════════════════════════════════════════════════════════
class SecureBankAccount {

    private final String accountNumber;
    private final String holderName;
    private       double balance;
    private       int    pin;
    private       int    failedAttempts;
    private       boolean locked;

    private static final int MAX_FAILED_ATTEMPTS = 3;

    public SecureBankAccount(String holder, int pin, double initialBalance)
            throws InvalidAmountException {
        if (initialBalance < 0) throw new InvalidAmountException(initialBalance);
        this.holderName    = holder;
        this.pin           = pin;
        this.balance       = initialBalance;
        this.accountNumber = "ACC" + (int)(Math.random() * 900_000 + 100_000);
        System.out.printf("[Account] Opened: %s for %s | Balance: ₹%.2f%n",
                accountNumber, holder, balance);
    }

    private void checkPin(int enteredPin)
            throws InvalidPinException, AccountLockedException {
        if (locked) throw new AccountLockedException(accountNumber);

        if (enteredPin != this.pin) {
            failedAttempts++;
            if (failedAttempts >= MAX_FAILED_ATTEMPTS) {
                locked = true;
                throw new AccountLockedException(accountNumber);
            }
            throw new InvalidPinException();
        }
        failedAttempts = 0;   // reset on success
    }

    public void deposit(double amount) throws InvalidAmountException {
        if (amount <= 0) throw new InvalidAmountException(amount);
        balance += amount;
        System.out.printf("  ✔ Deposited ₹%.2f | Balance: ₹%.2f%n", amount, balance);
    }

    public void withdraw(double amount, int pin)
            throws InvalidAmountException, InvalidPinException,
                   AccountLockedException, InsufficientFundsException {
        if (amount <= 0) throw new InvalidAmountException(amount);
        checkPin(pin);
        if (amount > balance) throw new InsufficientFundsException(amount, balance);
        balance -= amount;
        System.out.printf("  ✔ Withdrew ₹%.2f | Balance: ₹%.2f%n", amount, balance);
    }

    public double getBalance() { return balance; }
    public String getAccountNumber() { return accountNumber; }
    public boolean isLocked() { return locked; }
}


// ═══════════════════════════════════════════════════════════
//  MAIN DEMO
// ═══════════════════════════════════════════════════════════
public class ExceptionHandling {

    // ═══════════════════════════════════════════════════════
    //  SECTION 1 – BASIC TRY/CATCH
    // ═══════════════════════════════════════════════════════
    static void basicTryCatch() {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║  1. BASIC TRY / CATCH                ║");
        System.out.println("╚══════════════════════════════════════╝");

        // ArithmeticException
        try {
            int result = 10 / 0;         // throws ArithmeticException
            System.out.println("Result: " + result);  // never reached
        } catch (ArithmeticException e) {
            System.out.println("  Caught ArithmeticException: " + e.getMessage());
        }

        // NullPointerException
        try {
            String s = null;
            s.length();
        } catch (NullPointerException e) {
            System.out.println("  Caught NullPointerException: " + e.getClass().getSimpleName());
        }

        // ArrayIndexOutOfBoundsException
        try {
            int[] arr = {1, 2, 3};
            System.out.println(arr[10]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("  Caught ArrayIndexOutOfBounds: " + e.getMessage());
        }

        // NumberFormatException
        try {
            int n = Integer.parseInt("abc");
        } catch (NumberFormatException e) {
            System.out.println("  Caught NumberFormatException: " + e.getMessage());
        }

        System.out.println("  Program continues normally after all catches ✔");
    }

    // ═══════════════════════════════════════════════════════
    //  SECTION 2 – MULTIPLE CATCH & FINALLY
    // ═══════════════════════════════════════════════════════
    static void multipleCatchFinally() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║  2. MULTIPLE CATCH + FINALLY         ║");
        System.out.println("╚══════════════════════════════════════╝");

        // Simulate different inputs to show different exceptions
        Object[] inputs = {"42", null, "abc", new int[]{1,2,3}};

        for (Object input : inputs) {
            System.out.println("\n  Processing: " + input);
            try {
                String s = (String) input;           // could be ClassCastException
                int result = Integer.parseInt(s);    // could be NumberFormatException
                int div = 100 / result;              // could be ArithmeticException
                System.out.println("  Result: " + div);

            } catch (ClassCastException e) {
                System.out.println("  ✘ ClassCastException: wrong type");
            } catch (NumberFormatException e) {
                System.out.println("  ✘ NumberFormatException: not a number");
            } catch (ArithmeticException e) {
                System.out.println("  ✘ ArithmeticException: " + e.getMessage());
            } catch (NullPointerException e) {
                System.out.println("  ✘ NullPointerException: input was null");
            } finally {
                // This block ALWAYS executes – use for cleanup (close files/DB/etc)
                System.out.println("  [finally] Cleanup done.");
            }
        }

        // Multi-catch (Java 7+) – catch multiple types in one block
        System.out.println("\n  --- Multi-catch syntax (Java 7+) ---");
        try {
            String[] arr = {"Java", "123"};
            int x = Integer.parseInt(arr[5]);   // ArrayIndexOutOfBounds
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            System.out.println("  Caught via multi-catch: " + e.getClass().getSimpleName());
        }
    }

    // ═══════════════════════════════════════════════════════
    //  SECTION 3 – THROW & THROWS
    // ═══════════════════════════════════════════════════════

    // throws → declares this method may throw a checked exception
    static double safeDivide(double a, double b) throws ArithmeticException {
        if (b == 0) throw new ArithmeticException("Divisor cannot be zero.");
        return a / b;
    }

    static int parsePositive(String s) throws NumberFormatException, IllegalArgumentException {
        int n = Integer.parseInt(s);     // may throw NumberFormatException
        if (n <= 0) throw new IllegalArgumentException("Must be positive. Got: " + n);
        return n;
    }

    static void throwDemo() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║  3. THROW & THROWS                   ║");
        System.out.println("╚══════════════════════════════════════╝");

        // Test safeDivide
        double[] divisors = {4.0, 0.0, -2.0};
        for (double d : divisors) {
            try {
                System.out.printf("  100 / %.1f = %.2f%n", d, safeDivide(100, d));
            } catch (ArithmeticException e) {
                System.out.println("  ✘ " + e.getMessage());
            }
        }

        // Test parsePositive
        String[] inputs = {"42", "-5", "hello", "0"};
        for (String s : inputs) {
            try {
                System.out.println("  parsePositive(\"" + s + "\") = " + parsePositive(s));
            } catch (NumberFormatException e) {
                System.out.println("  ✘ NumberFormatException: \"" + s + "\" is not a number");
            } catch (IllegalArgumentException e) {
                System.out.println("  ✘ IllegalArgumentException: " + e.getMessage());
            }
        }
    }

    // ═══════════════════════════════════════════════════════
    //  SECTION 4 – CUSTOM EXCEPTIONS + CHAINING
    // ═══════════════════════════════════════════════════════
    static void customExceptionDemo() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║  4. CUSTOM EXCEPTIONS & CHAINING     ║");
        System.out.println("╚══════════════════════════════════════╝");

        SecureBankAccount account = null;

        try {
            account = new SecureBankAccount("Alice Johnson", 1234, 10_000.0);
        } catch (InvalidAmountException e) {
            System.out.println("  ✘ Failed to create account: " + e);
            return;
        }

        // Test various failure scenarios
        System.out.println("\n  --- Deposit tests ---");
        try { account.deposit(5_000); }
        catch (InvalidAmountException e) { System.out.println("  ✘ " + e); }

        try { account.deposit(-100); }
        catch (InvalidAmountException e) { System.out.println("  ✘ " + e); }

        System.out.println("\n  --- Withdrawal tests ---");
        // Wrong PIN attempts
        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                account.withdraw(1000, 9999);   // wrong PIN
            } catch (InvalidPinException e) {
                System.out.println("  Attempt " + attempt + ": " + e);
            } catch (AccountLockedException e) {
                System.out.println("  ✘ " + e);
            } catch (InsufficientFundsException | InvalidAmountException e) {
                System.out.println("  ✘ " + e);
            }
        }

        // Try a new account for insufficient funds demo
        try {
            SecureBankAccount acc2 = new SecureBankAccount("Bob", 0000, 500.0);
            acc2.withdraw(2000, 0000);   // insufficient
        } catch (InsufficientFundsException e) {
            System.out.println("\n  ✘ " + e);
            System.out.printf("  Shortfall: ₹%.2f%n", e.getShortfall());
        } catch (BankingException e) {
            System.out.println("  ✘ Banking error [" + e.getErrorCode() + "]: " + e.getMessage());
        }

        // ValidationException (unchecked – no catch required, shown for demo)
        System.out.println("\n  --- ValidationException (unchecked) ---");
        try {
            String email = "bad-email";
            if (!email.contains("@")) throw new ValidationException("email", "must contain @");
        } catch (ValidationException e) {
            System.out.println("  ✘ " + e.getMessage());
        }
    }

    // ═══════════════════════════════════════════════════════
    //  SECTION 5 – TRY-WITH-RESOURCES (Java 7+)
    // ═══════════════════════════════════════════════════════

    // A fake "resource" that implements AutoCloseable
    static class MockDatabaseConnection implements AutoCloseable {
        private final String url;
        private boolean open;

        MockDatabaseConnection(String url) {
            this.url  = url;
            this.open = true;
            System.out.println("  [DB] Connection opened: " + url);
        }

        String query(String sql) throws Exception {
            if (!open) throw new Exception("Connection is closed!");
            return "Result of: " + sql;
        }

        @Override
        public void close() {
            open = false;
            System.out.println("  [DB] Connection closed: " + url + "  ← auto by try-with-resources");
        }
    }

    static void tryWithResourcesDemo() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║  5. TRY-WITH-RESOURCES               ║");
        System.out.println("╚══════════════════════════════════════╝");

        System.out.println("  close() is called AUTOMATICALLY, even if an exception occurs:");

        // The resource is automatically closed when the try block exits
        try (MockDatabaseConnection db = new MockDatabaseConnection("jdbc:mysql://localhost/app")) {
            System.out.println("  " + db.query("SELECT * FROM users LIMIT 5"));
            System.out.println("  " + db.query("SELECT COUNT(*) FROM orders"));
        } catch (Exception e) {
            System.out.println("  ✘ DB Error: " + e.getMessage());
        }
        // No finally { db.close(); } needed!

        System.out.println("\n  Compared to old way (error-prone):");
        System.out.println("  MockDatabaseConnection db = null;");
        System.out.println("  try { db = new MockDatabaseConnection(...); db.query(...); }");
        System.out.println("  catch (Exception e) { ... }");
        System.out.println("  finally { if (db != null) db.close(); }  ← verbose & risky");
    }

    // ═══════════════════════════════════════════════════════
    //  ENTRY POINT
    // ═══════════════════════════════════════════════════════
    public static void main(String[] args) {
        basicTryCatch();
        multipleCatchFinally();
        throwDemo();
        customExceptionDemo();
        tryWithResourcesDemo();
    }
}
