import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * ╔══════════════════════════════════════════════════════════╗
 *  DAY 14 – Mini Project 2: CLI Banking System
 *  Week 2 Capstone — full OOP application
 * ╚══════════════════════════════════════════════════════════╝
 *
 *  Features:
 *    ✔ Create account (Savings / Current / Fixed Deposit)
 *    ✔ Deposit money
 *    ✔ Withdraw money
 *    ✔ Transfer between accounts
 *    ✔ Display balance & mini-statement
 *    ✔ Calculate interest
 *
 *  OOP concepts applied:
 *    ✔ Abstract class  (BankAccount – base class)
 *    ✔ Inheritance     (SavingsAccount, CurrentAccount, FDAccount)
 *    ✔ Encapsulation   (private fields + getters/setters + PIN)
 *    ✔ Polymorphism    (calculateInterest(), displayInfo() overridden)
 *    ✔ Interfaces      (Transferable)
 *    ✔ Classes & Objects  (Bank manages a collection of accounts)
 *
 *  Architecture:
 *    Transaction        → data class for each transaction
 *    BankAccount        → abstract base with all shared logic
 *    SavingsAccount     → 4% interest, min balance ₹500
 *    CurrentAccount     → no interest, overdraft allowed up to ₹10k
 *    FDAccount          → locked for a period, higher interest
 *    Bank               → manages all accounts, drives the menu
 */

// ═══════════════════════════════════════════════════════════
//  TRANSACTION RECORD
// ═══════════════════════════════════════════════════════════
class Transaction {

    public enum Type { DEPOSIT, WITHDRAWAL, TRANSFER_IN, TRANSFER_OUT, INTEREST }

    private final Type   type;
    private final double amount;
    private final String description;
    private final String timestamp;

    public Transaction(Type type, double amount, String description) {
        this.type        = type;
        this.amount      = amount;
        this.description = description;
        this.timestamp   = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    }

    @Override
    public String toString() {
        String sign = (type == Type.DEPOSIT || type == Type.TRANSFER_IN || type == Type.INTEREST)
                ? "+" : "-";
        return String.format("  %s  %-20s  %s₹%,.2f", timestamp, description, sign, amount);
    }
}


// ═══════════════════════════════════════════════════════════
//  INTERFACE – Transferable
// ═══════════════════════════════════════════════════════════
interface Transferable {
    boolean transferTo(BankAccount target, double amount, int pin);
}


// ═══════════════════════════════════════════════════════════
//  ABSTRACT BASE CLASS – BankAccount
// ═══════════════════════════════════════════════════════════
abstract class BankAccount implements Transferable {

    // ── Private fields (Encapsulation) ────────────────────────
    private final String accountNumber;
    private       String holderName;
    private       int    pin;
    private       double balance;
    private final List<Transaction> history = new ArrayList<>();

    // ── Protected (visible to subclasses) ────────────────────
    protected final String accountType;

    // ── Constructor ───────────────────────────────────────────
    public BankAccount(String holderName, int pin, double initialDeposit, String accountType) {
        this.holderName    = holderName;
        this.pin           = pin;
        this.accountType   = accountType;
        this.accountNumber = generateAccountNumber();

        if (initialDeposit >= getMinimumBalance()) {
            this.balance = initialDeposit;
            record(new Transaction(Transaction.Type.DEPOSIT, initialDeposit, "Initial deposit"));
        } else {
            throw new IllegalArgumentException(
                    "Initial deposit must be at least ₹" + getMinimumBalance());
        }
    }

    private String generateAccountNumber() {
        return accountType.substring(0, 3).toUpperCase()
                + (int)(Math.random() * 900_000 + 100_000);
    }

    // ── Abstract methods (subclasses define specifics) ────────
    public abstract double calculateInterest();      // each type has different rate
    public abstract double getMinimumBalance();      // each type has different minimum
    public abstract String getAccountSummary();      // type-specific info

    // ── Getters (controlled read access) ─────────────────────
    public String getAccountNumber() { return accountNumber; }
    public String getHolderName()    { return holderName;    }
    public double getBalance()       { return balance;       }
    public String getAccountType()   { return accountType;   }

    // ── Authentication ────────────────────────────────────────
    protected boolean authenticate(int enteredPin) {
        if (enteredPin != this.pin) {
            System.out.println("  ✘ Incorrect PIN.");
            return false;
        }
        return true;
    }

    // ── Core banking operations ───────────────────────────────

    public boolean deposit(double amount) {
        if (amount <= 0) {
            System.out.println("  ✘ Deposit amount must be positive.");
            return false;
        }
        balance += amount;
        record(new Transaction(Transaction.Type.DEPOSIT, amount, "Cash deposit"));
        System.out.printf("  ✔ Deposited ₹%,.2f | Balance: ₹%,.2f%n", amount, balance);
        return true;
    }

    public boolean withdraw(double amount, int enteredPin) {
        if (!authenticate(enteredPin)) return false;
        if (amount <= 0) {
            System.out.println("  ✘ Withdrawal amount must be positive.");
            return false;
        }
        if (!canWithdraw(amount)) {
            System.out.printf("  ✘ Cannot withdraw ₹%,.2f. Available: ₹%,.2f%n",
                    amount, getWithdrawableAmount());
            return false;
        }
        balance -= amount;
        record(new Transaction(Transaction.Type.WITHDRAWAL, amount, "Cash withdrawal"));
        System.out.printf("  ✔ Withdrew ₹%,.2f | Balance: ₹%,.2f%n", amount, balance);
        return true;
    }

    // Subclasses may override withdrawal rules (e.g. overdraft)
    protected boolean canWithdraw(double amount) {
        return (balance - amount) >= getMinimumBalance();
    }

    protected double getWithdrawableAmount() {
        return balance - getMinimumBalance();
    }

    @Override
    public boolean transferTo(BankAccount target, double amount, int pin) {
        if (!authenticate(pin)) return false;
        if (!canWithdraw(amount)) {
            System.out.printf("  ✘ Insufficient funds for transfer. Available: ₹%,.2f%n",
                    getWithdrawableAmount());
            return false;
        }
        balance -= amount;
        target.balance += amount;
        record(new Transaction(Transaction.Type.TRANSFER_OUT, amount,
                "Transfer to " + target.getAccountNumber()));
        target.record(new Transaction(Transaction.Type.TRANSFER_IN, amount,
                "Transfer from " + this.accountNumber));
        System.out.printf("  ✔ Transferred ₹%,.2f to %s (%s)%n",
                amount, target.holderName, target.accountNumber);
        return true;
    }

    public void applyInterest() {
        double interest = calculateInterest();
        if (interest > 0) {
            balance += interest;
            record(new Transaction(Transaction.Type.INTEREST, interest,
                    "Interest credited (" + getAccountType() + ")"));
            System.out.printf("  ✔ Interest ₹%,.2f credited | New balance: ₹%,.2f%n",
                    interest, balance);
        }
    }

    private void record(Transaction t) {
        history.add(t);
    }

    // ── Display methods ───────────────────────────────────────

    public void displayInfo() {
        System.out.println("  ┌────────────────────────────────────────┐");
        System.out.printf ("  │  Account  : %-27s│%n", accountNumber);
        System.out.printf ("  │  Holder   : %-27s│%n", holderName);
        System.out.printf ("  │  Type     : %-27s│%n", accountType);
        System.out.printf ("  │  Balance  : ₹%-26,.2f│%n", balance);
        System.out.println("  │  " + getAccountSummary());
        System.out.println("  └────────────────────────────────────────┘");
    }

    public void printMiniStatement(int lastN) {
        System.out.println("  --- Mini Statement: " + accountNumber + " ---");
        int start = Math.max(0, history.size() - lastN);
        for (int i = start; i < history.size(); i++) {
            System.out.println(history.get(i));
        }
        System.out.printf("  Current Balance: ₹%,.2f%n", balance);
    }
}


// ═══════════════════════════════════════════════════════════
//  SAVINGS ACCOUNT
// ═══════════════════════════════════════════════════════════
class SavingsAccount extends BankAccount {

    private static final double ANNUAL_INTEREST_RATE = 4.0;   // 4% per annum
    private static final double MINIMUM_BALANCE      = 500.0;

    public SavingsAccount(String holderName, int pin, double initialDeposit) {
        super(holderName, pin, initialDeposit, "Savings");
    }

    @Override
    public double calculateInterest() {
        return getBalance() * ANNUAL_INTEREST_RATE / 100.0 / 12;  // monthly
    }

    @Override
    public double getMinimumBalance() { return MINIMUM_BALANCE; }

    @Override
    public String getAccountSummary() {
        return String.format("Interest: %.1f%% p.a. | Min Bal: ₹%.0f             │",
                ANNUAL_INTEREST_RATE, MINIMUM_BALANCE);
    }
}


// ═══════════════════════════════════════════════════════════
//  CURRENT ACCOUNT  (overdraft allowed)
// ═══════════════════════════════════════════════════════════
class CurrentAccount extends BankAccount {

    private static final double OVERDRAFT_LIMIT = 10_000.0;

    public CurrentAccount(String holderName, int pin, double initialDeposit) {
        super(holderName, pin, initialDeposit, "Current");
    }

    @Override
    public double calculateInterest() { return 0.0; }  // no interest on current

    @Override
    public double getMinimumBalance() { return 0.0; }

    @Override
    protected boolean canWithdraw(double amount) {
        return (getBalance() - amount) >= -OVERDRAFT_LIMIT;   // can go negative
    }

    @Override
    protected double getWithdrawableAmount() {
        return getBalance() + OVERDRAFT_LIMIT;
    }

    @Override
    public String getAccountSummary() {
        return String.format("No interest | Overdraft: ₹%.0f                │", OVERDRAFT_LIMIT);
    }
}


// ═══════════════════════════════════════════════════════════
//  FIXED DEPOSIT ACCOUNT
// ═══════════════════════════════════════════════════════════
class FDAccount extends BankAccount {

    private static final double ANNUAL_INTEREST_RATE = 7.5;
    private final int tenureMonths;
    private int monthsCompleted;

    public FDAccount(String holderName, int pin, double principal, int tenureMonths) {
        super(holderName, pin, principal, "Fixed Deposit");
        this.tenureMonths    = tenureMonths;
        this.monthsCompleted = 0;
    }

    @Override
    public double calculateInterest() {
        return getBalance() * ANNUAL_INTEREST_RATE / 100.0 / 12;
    }

    @Override
    public double getMinimumBalance() { return 1_000.0; }

    // FD cannot be withdrawn before maturity
    @Override
    protected boolean canWithdraw(double amount) {
        if (monthsCompleted < tenureMonths) {
            System.out.println("  ✘ FD locked until maturity (" +
                    (tenureMonths - monthsCompleted) + " months remaining).");
            return false;
        }
        return true;
    }

    public void completeTenure() {
        monthsCompleted = tenureMonths;
        System.out.println("  ✔ FD matured! You may now withdraw.");
    }

    @Override
    public String getAccountSummary() {
        return String.format("Rate: %.1f%% | Tenure: %d months (%d done)     │",
                ANNUAL_INTEREST_RATE, tenureMonths, monthsCompleted);
    }
}


// ═══════════════════════════════════════════════════════════
//  BANK – manages all accounts
// ═══════════════════════════════════════════════════════════
class Bank {

    private final String name;
    private final Map<String, BankAccount> accounts = new HashMap<>();

    public Bank(String name) {
        this.name = name;
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.printf ("║  Welcome to %-28s ║%n", name + " Bank");
        System.out.println("╚══════════════════════════════════════════╝");
    }

    // ── Account management ────────────────────────────────────

    public SavingsAccount openSavings(String holder, int pin, double deposit) {
        SavingsAccount acc = new SavingsAccount(holder, pin, deposit);
        accounts.put(acc.getAccountNumber(), acc);
        System.out.printf("  ✔ Savings account opened: %s for %s%n",
                acc.getAccountNumber(), holder);
        return acc;
    }

    public CurrentAccount openCurrent(String holder, int pin, double deposit) {
        CurrentAccount acc = new CurrentAccount(holder, pin, deposit);
        accounts.put(acc.getAccountNumber(), acc);
        System.out.printf("  ✔ Current account opened: %s for %s%n",
                acc.getAccountNumber(), holder);
        return acc;
    }

    public FDAccount openFD(String holder, int pin, double deposit, int months) {
        FDAccount acc = new FDAccount(holder, pin, deposit, months);
        accounts.put(acc.getAccountNumber(), acc);
        System.out.printf("  ✔ FD account opened: %s for %s (%d months)%n",
                acc.getAccountNumber(), holder, months);
        return acc;
    }

    public BankAccount findAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    // Apply monthly interest to ALL interest-bearing accounts
    public void processMonthlyInterest() {
        System.out.println("\n  --- Monthly Interest Processing ---");
        for (BankAccount acc : accounts.values()) {
            double interest = acc.calculateInterest();
            if (interest > 0) {
                System.out.printf("  %s (%s): ", acc.getAccountNumber(), acc.getHolderName());
                acc.applyInterest();
            }
        }
    }

    public void listAllAccounts() {
        System.out.printf("%n  --- All Accounts at %s Bank ---%n", name);
        System.out.printf("  %-12s %-20s %-15s %s%n",
                "Account No", "Holder", "Type", "Balance");
        System.out.println("  " + "─".repeat(62));
        for (BankAccount acc : accounts.values()) {
            System.out.printf("  %-12s %-20s %-15s ₹%,.2f%n",
                    acc.getAccountNumber(), acc.getHolderName(),
                    acc.getAccountType(), acc.getBalance());
        }
    }
}


// ═══════════════════════════════════════════════════════════
//  MAIN – CLI  Menu
// ═══════════════════════════════════════════════════════════
public class BankingSystem {

    static Scanner sc   = new Scanner(System.in);
    static Bank    bank = new Bank("Java National");

    // Seed some accounts for demonstration
    static SavingsAccount alice;
    static CurrentAccount company;
    static FDAccount       fd;

    static void seedAccounts() {
        System.out.println("\n  [System] Creating demo accounts...");
        alice   = bank.openSavings("Alice Johnson",  1111, 10_000);
        company = bank.openCurrent("TechCorp Ltd",   2222, 50_000);
        fd      = bank.openFD("Bob Sharma",          3333, 25_000, 6);
    }

    // ── UI helpers ────────────────────────────────────────────

    static void printMainMenu() {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.printf ("║  %s Bank – Main Menu         ║%n", "Java National");
        System.out.println("╠════════════════════════════════════╣");
        System.out.println("║  1. View all accounts               ║");
        System.out.println("║  2. Account details & statement     ║");
        System.out.println("║  3. Deposit                         ║");
        System.out.println("║  4. Withdraw                        ║");
        System.out.println("║  5. Transfer                        ║");
        System.out.println("║  6. Apply monthly interest          ║");
        System.out.println("║  7. Open new account                ║");
        System.out.println("║  8. Exit                            ║");
        System.out.println("╚════════════════════════════════════╝");
        System.out.print("  Choice: ");
    }

    static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextInt()) { int v = sc.nextInt(); sc.nextLine(); return v; }
            sc.nextLine();
            System.out.println("  ⚠  Enter a whole number.");
        }
    }

    static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextDouble()) { double v = sc.nextDouble(); sc.nextLine(); return v; }
            sc.nextLine();
            System.out.println("  ⚠  Enter a valid amount.");
        }
    }

    static BankAccount selectAccount() {
        System.out.print("  Enter account number: ");
        String num = sc.nextLine().trim().toUpperCase();
        BankAccount acc = bank.findAccount(num);
        if (acc == null) System.out.println("  ✘ Account not found: " + num);
        return acc;
    }

    // ── Menu handlers ─────────────────────────────────────────

    static void handleDeposit() {
        BankAccount acc = selectAccount();
        if (acc == null) return;
        double amount = readDouble("  Amount to deposit: ₹");
        acc.deposit(amount);
    }

    static void handleWithdraw() {
        BankAccount acc = selectAccount();
        if (acc == null) return;
        int    pin    = readInt("  Enter PIN: ");
        double amount = readDouble("  Amount to withdraw: ₹");
        acc.withdraw(amount, pin);
    }

    static void handleTransfer() {
        System.out.println("  FROM:");
        BankAccount from = selectAccount();
        if (from == null) return;
        System.out.println("  TO:");
        BankAccount to   = selectAccount();
        if (to == null) return;
        int    pin    = readInt("  Enter FROM account PIN: ");
        double amount = readDouble("  Transfer amount: ₹");
        from.transferTo(to, amount, pin);
    }

    static void handleNewAccount() {
        System.out.println("  1) Savings  2) Current  3) Fixed Deposit");
        int type = readInt("  Choose: ");
        System.out.print("  Holder name: ");
        String name = sc.nextLine().trim();
        int    pin  = readInt("  Set 4-digit PIN: ");
        double dep  = readDouble("  Initial deposit: ₹");

        switch (type) {
            case 1: bank.openSavings(name, pin, dep);     break;
            case 2: bank.openCurrent(name, pin, dep);     break;
            case 3:
                int months = readInt("  Tenure (months): ");
                bank.openFD(name, pin, dep, months);
                break;
            default: System.out.println("  ✘ Invalid type.");
        }
    }

    // ═══════════════════════════════════════════════════════
    //  ENTRY POINT
    // ═══════════════════════════════════════════════════════
    public static void main(String[] args) {

        seedAccounts();

        // Quick demo transactions so statement has entries
        System.out.println("\n  [Demo] Running some transactions...");
        alice.deposit(5_000);
        alice.withdraw(2_000, 1111);
        company.deposit(20_000);
        alice.transferTo(company, 1_500, 1111);
        bank.processMonthlyInterest();

        // ── Interactive menu ──────────────────────────────────
        boolean running = true;
        while (running) {
            printMainMenu();
            int choice;
            if (sc.hasNextInt()) { choice = sc.nextInt(); sc.nextLine(); }
            else { sc.nextLine(); continue; }

            switch (choice) {
                case 1:
                    bank.listAllAccounts();
                    break;
                case 2:
                    BankAccount acc = selectAccount();
                    if (acc != null) {
                        acc.displayInfo();
                        acc.printMiniStatement(5);
                    }
                    break;
                case 3: handleDeposit();   break;
                case 4: handleWithdraw();  break;
                case 5: handleTransfer();  break;
                case 6: bank.processMonthlyInterest(); break;
                case 7: handleNewAccount(); break;
                case 8:
                    running = false;
                    System.out.println("\n  Thank you for banking with Java National. Goodbye!");
                    break;
                default:
                    System.out.println("  ⚠  Choose 1–8.");
            }
        }
        sc.close();
    }
}
