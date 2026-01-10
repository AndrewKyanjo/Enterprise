/**
 * ╔══════════════════════════════════════════════════════════╗
 *  DAY 10 – Encapsulation
 * ╚══════════════════════════════════════════════════════════╝
 *
 *  Encapsulation = BUNDLING data + behaviour AND
 *                  RESTRICTING direct access to data.
 *
 *  Why it matters:
 *    • Protects fields from invalid values (age = -5 is impossible)
 *    • Hides implementation details (callers don't care HOW it works)
 *    • Lets you change internals without breaking outside code
 *    • Classic principle: "Tell, don't ask"
 *
 *  Access modifiers:
 *    private    → only THIS class can access it
 *    (default)  → same package
 *    protected  → same package + subclasses
 *    public     → everyone
 *
 *  Pattern:
 *    private <type> fieldName;          // hidden
 *    public  <type> getFieldName() {}   // controlled READ
 *    public  void   setFieldName() {}   // controlled WRITE + validation
 *
 *  Classes in this file:
 *    1) BankAccount  – encapsulated with full validation
 *    2) Person       – shows why setters beat direct access
 *    3) Product      – inventory with business-rule enforcement
 */

// ═══════════════════════════════════════════════════════════
//  CLASS 1 – BankAccount
// ═══════════════════════════════════════════════════════════
class BankAccount {

    // ── private fields ────────────────────────────────────────
    private String accountHolder;
    private String accountNumber;
    private double balance;
    private int    pin;
    private boolean locked;

    // ── Constructor ───────────────────────────────────────────
    public BankAccount(String holder, int pin, double initialDeposit) {
        this.accountHolder = holder;
        this.pin           = pin;
        this.accountNumber = "ACC" + (int)(Math.random() * 900_000 + 100_000);
        this.locked        = false;

        // Use setter so validation runs even at construction
        setBalance(initialDeposit);
        System.out.printf("[BankAccount] Opened for %s | Acc: %s%n",
                holder, accountNumber);
    }

    // ── Getters (READ) ────────────────────────────────────────

    public String getAccountHolder() { return accountHolder; }
    public String getAccountNumber() { return accountNumber; }
    public boolean isLocked()        { return locked; }

    // Balance has NO direct setter – only modified via deposit/withdraw
    public double getBalance() { return balance; }

    // PIN is NEVER exposed via getter (security!)
    // No getPin() method

    // ── Private helper setter ─────────────────────────────────
    private void setBalance(double amount) {
        if (amount < 0) {
            System.out.println("  ✘ Balance cannot be negative. Setting to 0.");
            this.balance = 0;
        } else {
            this.balance = amount;
        }
    }

    // ── Setters with validation (WRITE) ──────────────────────

    public void setAccountHolder(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("  ✘ Account holder name cannot be empty.");
            return;
        }
        this.accountHolder = name.trim();
        System.out.println("  ✔ Name updated to: " + this.accountHolder);
    }

    public boolean changePin(int oldPin, int newPin) {
        if (oldPin != this.pin) {
            System.out.println("  ✘ Incorrect current PIN.");
            return false;
        }
        if (newPin < 1000 || newPin > 9999) {
            System.out.println("  ✘ PIN must be exactly 4 digits.");
            return false;
        }
        this.pin = newPin;
        System.out.println("  ✔ PIN changed successfully.");
        return true;
    }

    // ── Business methods ──────────────────────────────────────

    public boolean deposit(double amount, int enteredPin) {
        if (!authenticate(enteredPin)) return false;
        if (amount <= 0) {
            System.out.println("  ✘ Deposit amount must be positive.");
            return false;
        }
        this.balance += amount;
        System.out.printf("  ✔ Deposited ₹%.2f | New balance: ₹%.2f%n", amount, balance);
        return true;
    }

    public boolean withdraw(double amount, int enteredPin) {
        if (!authenticate(enteredPin)) return false;
        if (amount <= 0) {
            System.out.println("  ✘ Withdrawal amount must be positive.");
            return false;
        }
        if (amount > this.balance) {
            System.out.printf("  ✘ Insufficient funds. Available: ₹%.2f%n", balance);
            return false;
        }
        this.balance -= amount;
        System.out.printf("  ✔ Withdrew ₹%.2f | Remaining: ₹%.2f%n", amount, balance);
        return true;
    }

    private boolean authenticate(int enteredPin) {
        if (locked) {
            System.out.println("  ✘ Account is locked.");
            return false;
        }
        if (enteredPin != this.pin) {
            System.out.println("  ✘ Wrong PIN.");
            return false;
        }
        return true;
    }

    public void displayInfo() {
        System.out.printf("  Account: %s | Holder: %-15s | Balance: ₹%,.2f | %s%n",
                accountNumber, accountHolder, balance,
                locked ? "🔒 LOCKED" : "✔ Active");
    }
}


// ═══════════════════════════════════════════════════════════
//  CLASS 2 – Person (age + email validation)
// ═══════════════════════════════════════════════════════════
class Person {

    private String name;
    private int    age;
    private String email;

    public Person(String name, int age, String email) {
        // All go through setters so validation always runs
        setName(name);
        setAge(age);
        setEmail(email);
    }

    // ── Getters ───────────────────────────────────────────────
    public String getName()  { return name; }
    public int    getAge()   { return age; }
    public String getEmail() { return email; }

    // ── Setters with validation ───────────────────────────────
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        this.name = name.trim();
    }

    public void setAge(int age) {
        if (age < 0 || age > 150) {
            throw new IllegalArgumentException("Age must be between 0 and 150. Got: " + age);
        }
        this.age = age;
    }

    public void setEmail(String email) {
        // Simple validation: must contain @ and a dot after @
        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("Invalid email: " + email);
        }
        this.email = email.toLowerCase().trim();
    }

    @Override
    public String toString() {
        return String.format("Person{name='%s', age=%d, email='%s'}", name, age, email);
    }
}


// ═══════════════════════════════════════════════════════════
//  CLASS 3 – Product (inventory business rules)
// ═══════════════════════════════════════════════════════════
class Product {

    private String  name;
    private double  price;
    private int     stockQuantity;
    private String  category;
    private boolean available;

    public Product(String name, String category, double price, int stock) {
        this.name     = name;
        this.category = category;
        setPrice(price);
        setStockQuantity(stock);
    }

    // ── Getters ───────────────────────────────────────────────
    public String  getName()          { return name; }
    public double  getPrice()         { return price; }
    public int     getStockQuantity() { return stockQuantity; }
    public String  getCategory()      { return category; }
    public boolean isAvailable()      { return available; }

    // ── Setters ───────────────────────────────────────────────
    public void setPrice(double price) {
        if (price < 0) {
            System.out.println("  ✘ Price cannot be negative. Keeping old value.");
            return;
        }
        this.price = price;
    }

    public void setStockQuantity(int qty) {
        if (qty < 0) {
            System.out.println("  ✘ Stock cannot be negative.");
            return;
        }
        this.stockQuantity = qty;
        // Availability auto-updates whenever stock changes
        this.available = (qty > 0);
    }

    // ── Business methods ──────────────────────────────────────
    public boolean sell(int quantity) {
        if (!available) {
            System.out.printf("  ✘ '%s' is out of stock.%n", name);
            return false;
        }
        if (quantity > stockQuantity) {
            System.out.printf("  ✘ Only %d units available.%n", stockQuantity);
            return false;
        }
        setStockQuantity(stockQuantity - quantity);
        System.out.printf("  ✔ Sold %d × '%s' for ₹%.2f%n",
                quantity, name, quantity * price);
        return true;
    }

    public void restock(int quantity) {
        setStockQuantity(stockQuantity + quantity);
        System.out.printf("  ✔ Restocked '%s' by %d. New stock: %d%n",
                name, quantity, stockQuantity);
    }

    public void displayInfo() {
        System.out.printf("  %-20s | %-12s | ₹%7.2f | Stock: %3d | %s%n",
                name, category, price, stockQuantity,
                available ? "In Stock" : "Out of Stock");
    }
}


// ═══════════════════════════════════════════════════════════
//  MAIN
// ═══════════════════════════════════════════════════════════
public class Encapsulation {

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║    DAY 10 – ENCAPSULATION DEMO       ║");
        System.out.println("╚══════════════════════════════════════╝");

        // ── BankAccount: secured by private + PIN ────────────
        System.out.println("\n=== BANK ACCOUNT ===");
        BankAccount acc = new BankAccount("Alice Johnson", 1234, 10_000.0);

        System.out.println("\nAttempting transactions:");
        acc.deposit(5_000, 1234);       // correct PIN
        acc.withdraw(3_000, 9999);      // wrong PIN
        acc.withdraw(3_000, 1234);      // correct PIN
        acc.withdraw(500_000, 1234);    // insufficient funds

        System.out.println("\nChanging PIN:");
        acc.changePin(0000, 5678);   // wrong old PIN
        acc.changePin(1234, 99);     // invalid new PIN (not 4-digit)
        acc.changePin(1234, 5678);   // success

        acc.displayInfo();

        // ── Person: validated setters ────────────────────────
        System.out.println("\n=== PERSON (validated setters) ===");
        Person p = new Person("Bob Smith", 25, "bob@example.com");
        System.out.println("Created: " + p);

        System.out.println("\nTrying invalid updates:");
        try { p.setAge(-5); }
        catch (IllegalArgumentException e) { System.out.println("  ✘ " + e.getMessage()); }

        try { p.setEmail("not-an-email"); }
        catch (IllegalArgumentException e) { System.out.println("  ✘ " + e.getMessage()); }

        p.setAge(26);    // valid
        System.out.println("Updated: " + p);

        // ── Product: inventory business rules ────────────────
        System.out.println("\n=== PRODUCT INVENTORY ===");
        Product phone  = new Product("iPhone 15", "Electronics", 79_999, 10);
        Product laptop = new Product("Dell XPS",  "Electronics", 1_29_999, 0);
        Product shirt  = new Product("Cotton Tee", "Clothing",   599, 50);

        System.out.println("\nInitial stock:");
        phone.displayInfo();
        laptop.displayInfo();
        shirt.displayInfo();

        System.out.println("\nSales:");
        phone.sell(3);
        laptop.sell(1);    // out of stock
        shirt.sell(5);
        phone.sell(20);    // exceeds stock

        System.out.println("\nAfter restocking laptop:");
        laptop.restock(15);

        System.out.println("\nFinal inventory:");
        phone.displayInfo();
        laptop.displayInfo();
        shirt.displayInfo();

        // ── Key takeaway ──────────────────────────────────────
        System.out.println("\n=== KEY TAKEAWAY ===");
        System.out.println("Direct access (BAD):   product.stockQuantity = -10;   ← no validation!");
        System.out.println("Via setter    (GOOD):  product.setStockQuantity(-10);  ← caught & rejected.");
        System.out.println("Private fields enforce CLASS INVARIANTS — the rules that must always hold.");
    }
}
