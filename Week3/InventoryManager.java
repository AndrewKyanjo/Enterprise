import java.util.*;

/**
 * ╔══════════════════════════════════════════════════════════╗
 *  DAY 21 – Mini Project 3: CLI Inventory Manager
 *  Week 3 Capstone — full data-structures application
 * ╚══════════════════════════════════════════════════════════╝
 *
 *  Features:
 *    ✔ Add product
 *    ✔ Remove product
 *    ✔ Update quantity / price
 *    ✔ Search by name or category
 *    ✔ Display all products (sorted by name, price, or stock)
 *    ✔ Low-stock alert
 *    ✔ Inventory summary (total value, category breakdown)
 *    ✔ In-memory storage using HashMap (id → Product)
 *
 *  Week 3 concepts applied:
 *    ✔ Arrays           – int[] for batch operations
 *    ✔ ArrayList        – search results, sorted views
 *    ✔ HashMap          – primary in-memory store  (O(1) lookup)
 *    ✔ String methods   – search, formatting, validation
 *    ✔ Exception handling – custom exceptions, input validation
 *    ✔ StringBuilder    – report building
 */

// ═══════════════════════════════════════════════════════════
//  CUSTOM EXCEPTIONS
// ═══════════════════════════════════════════════════════════
class ProductNotFoundException extends Exception {
    public ProductNotFoundException(String identifier) {
        super("Product not found: " + identifier);
    }
}

class DuplicateProductException extends Exception {
    public DuplicateProductException(String name) {
        super("A product named '" + name + "' already exists.");
    }
}

class InvalidProductDataException extends RuntimeException {
    public InvalidProductDataException(String field, String reason) {
        super("Invalid " + field + ": " + reason);
    }
}


// ═══════════════════════════════════════════════════════════
//  PRODUCT  (encapsulated data class)
// ═══════════════════════════════════════════════════════════
class Product {

    private static int  nextId = 1000;  // auto-increment ID generator

    private final int    id;
    private       String name;
    private       String category;
    private       double price;
    private       int    quantity;
    private       int    lowStockThreshold;

    public Product(String name, String category, double price, int quantity) {
        validate(name, category, price, quantity);
        this.id                = nextId++;
        this.name              = name.trim();
        this.category          = category.trim();
        this.price             = price;
        this.quantity          = quantity;
        this.lowStockThreshold = 5;        // default alert threshold
    }

    private void validate(String name, String category, double price, int qty) {
        if (name == null || name.trim().isEmpty())
            throw new InvalidProductDataException("name", "cannot be empty");
        if (category == null || category.trim().isEmpty())
            throw new InvalidProductDataException("category", "cannot be empty");
        if (price < 0)
            throw new InvalidProductDataException("price", "cannot be negative (got " + price + ")");
        if (qty < 0)
            throw new InvalidProductDataException("quantity", "cannot be negative (got " + qty + ")");
    }

    // ── Getters ───────────────────────────────────────────────
    public int    getId()       { return id;       }
    public String getName()     { return name;     }
    public String getCategory() { return category; }
    public double getPrice()    { return price;    }
    public int    getQuantity() { return quantity; }
    public double getTotalValue() { return price * quantity; }
    public boolean isLowStock() { return quantity <= lowStockThreshold && quantity > 0; }
    public boolean isOutOfStock() { return quantity == 0; }

    // ── Setters with validation ───────────────────────────────
    public void setName(String name) {
        if (name == null || name.trim().isEmpty())
            throw new InvalidProductDataException("name", "cannot be empty");
        this.name = name.trim();
    }

    public void setCategory(String category) {
        if (category == null || category.trim().isEmpty())
            throw new InvalidProductDataException("category", "cannot be empty");
        this.category = category.trim();
    }

    public void setPrice(double price) {
        if (price < 0) throw new InvalidProductDataException("price", "cannot be negative");
        this.price = price;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) throw new InvalidProductDataException("quantity", "cannot be negative");
        this.quantity = quantity;
    }

    public void adjustQuantity(int delta) {
        int newQty = this.quantity + delta;
        if (newQty < 0)
            throw new InvalidProductDataException("quantity",
                    "adjustment would make stock negative (current: " + quantity + ", delta: " + delta + ")");
        this.quantity = newQty;
    }

    public void setLowStockThreshold(int t) { this.lowStockThreshold = t; }

    // ── Display ───────────────────────────────────────────────
    public String toTableRow() {
        String status = isOutOfStock() ? "OUT OF STOCK"
                      : isLowStock()   ? "⚠ LOW"
                      : "OK";
        return String.format("  %-6d %-22s %-14s %8.2f  %6d  %10.2f  %-12s",
                id, name, category, price, quantity, getTotalValue(), status);
    }

    @Override
    public String toString() {
        return String.format("Product{id=%d, name='%s', cat='%s', price=₹%.2f, qty=%d}",
                id, name, category, price, quantity);
    }
}


// ═══════════════════════════════════════════════════════════
//  INVENTORY  (manages the product collection)
// ═══════════════════════════════════════════════════════════
class Inventory {

    // Primary store: id → Product  (HashMap for O(1) access)
    private final Map<Integer, Product> store = new LinkedHashMap<>();

    // ── CRUD ──────────────────────────────────────────────────

    public Product addProduct(String name, String category, double price, int qty)
            throws DuplicateProductException {
        // Check for duplicate name (case-insensitive)
        for (Product p : store.values()) {
            if (p.getName().equalsIgnoreCase(name.trim())) {
                throw new DuplicateProductException(name);
            }
        }
        Product p = new Product(name, category, price, qty);
        store.put(p.getId(), p);
        return p;
    }

    public Product getById(int id) throws ProductNotFoundException {
        Product p = store.get(id);
        if (p == null) throw new ProductNotFoundException("ID " + id);
        return p;
    }

    public void removeProduct(int id) throws ProductNotFoundException {
        if (store.remove(id) == null) throw new ProductNotFoundException("ID " + id);
    }

    public void updatePrice(int id, double newPrice) throws ProductNotFoundException {
        getById(id).setPrice(newPrice);
    }

    public void updateQuantity(int id, int newQty) throws ProductNotFoundException {
        getById(id).setQuantity(newQty);
    }

    public void adjustStock(int id, int delta) throws ProductNotFoundException {
        getById(id).adjustQuantity(delta);
    }

    // ── SEARCH ───────────────────────────────────────────────

    public List<Product> searchByName(String keyword) {
        List<Product> results = new ArrayList<>();
        String kw = keyword.toLowerCase().trim();
        for (Product p : store.values()) {
            if (p.getName().toLowerCase().contains(kw)) results.add(p);
        }
        return results;
    }

    public List<Product> searchByCategory(String category) {
        List<Product> results = new ArrayList<>();
        String cat = category.toLowerCase().trim();
        for (Product p : store.values()) {
            if (p.getCategory().toLowerCase().contains(cat)) results.add(p);
        }
        return results;
    }

    public List<Product> getLowStockProducts() {
        List<Product> results = new ArrayList<>();
        for (Product p : store.values()) {
            if (p.isLowStock() || p.isOutOfStock()) results.add(p);
        }
        return results;
    }

    public List<Product> getAllSorted(String by) {
        List<Product> list = new ArrayList<>(store.values());
        switch (by.toLowerCase()) {
            case "name":     list.sort(Comparator.comparing(Product::getName)); break;
            case "price":    list.sort(Comparator.comparingDouble(Product::getPrice)); break;
            case "quantity": list.sort(Comparator.comparingInt(Product::getQuantity)); break;
            case "value":    list.sort(Comparator.comparingDouble(Product::getTotalValue).reversed()); break;
            default: break;   // insertion order (LinkedHashMap)
        }
        return list;
    }

    // ── REPORTS ──────────────────────────────────────────────

    public void printTable(List<Product> products, String title) {
        System.out.println("\n  ── " + title + " ──");
        if (products.isEmpty()) {
            System.out.println("  (no products)");
            return;
        }
        System.out.printf("  %-6s %-22s %-14s %8s  %6s  %10s  %-12s%n",
                "ID", "Name", "Category", "Price(₹)", "Qty", "Value(₹)", "Status");
        System.out.println("  " + "─".repeat(85));
        for (Product p : products) System.out.println(p.toTableRow());
        System.out.println("  " + "─".repeat(85));
    }

    public void printSummary() {
        if (store.isEmpty()) { System.out.println("  Inventory is empty."); return; }

        int    totalProducts = store.size();
        double totalValue    = 0;
        int    totalItems    = 0;
        int    lowStockCount = 0;
        int    outOfStock    = 0;

        // Category breakdown using HashMap
        Map<String, double[]> catStats = new LinkedHashMap<>(); // cat → [count, value]

        for (Product p : store.values()) {
            totalValue  += p.getTotalValue();
            totalItems  += p.getQuantity();
            if (p.isLowStock())   lowStockCount++;
            if (p.isOutOfStock()) outOfStock++;

            catStats.computeIfAbsent(p.getCategory(), k -> new double[]{0, 0});
            catStats.get(p.getCategory())[0]++;
            catStats.get(p.getCategory())[1] += p.getTotalValue();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("\n  ╔══════════════════════════════════════════╗\n");
        sb.append("  ║           INVENTORY SUMMARY              ║\n");
        sb.append("  ╠══════════════════════════════════════════╣\n");
        sb.append(String.format("  ║  Total Products  : %-22d║%n", totalProducts));
        sb.append(String.format("  ║  Total Items     : %-22d║%n", totalItems));
        sb.append(String.format("  ║  Total Value     : ₹%-21,.2f║%n", totalValue));
        sb.append(String.format("  ║  Low Stock Items : %-22d║%n", lowStockCount));
        sb.append(String.format("  ║  Out of Stock    : %-22d║%n", outOfStock));
        sb.append("  ╠══════════════════════════════════════════╣\n");
        sb.append("  ║  Category Breakdown                      ║\n");
        sb.append("  ╠══════════════════════════════════════════╣\n");
        for (Map.Entry<String, double[]> e : catStats.entrySet()) {
            sb.append(String.format("  ║  %-12s  %2.0f items  ₹%,11.2f ║%n",
                    e.getKey(), e.getValue()[0], e.getValue()[1]));
        }
        sb.append("  ╚══════════════════════════════════════════╝");
        System.out.println(sb);
    }

    public int size() { return store.size(); }
}


// ═══════════════════════════════════════════════════════════
//  CLI MENU + ENTRY POINT
// ═══════════════════════════════════════════════════════════
public class InventoryManager {

    static Scanner   sc        = new Scanner(System.in);
    static Inventory inventory = new Inventory();

    // ── Seed demo data ────────────────────────────────────────
    static void seedData() {
        System.out.println("  [System] Loading demo inventory...");
        try {
            inventory.addProduct("MacBook Pro 14\"",  "Electronics",  1_99_999.0, 8);
            inventory.addProduct("Logitech MX Keys",  "Electronics",     8_499.0, 3);  // low
            inventory.addProduct("Dell Monitor 27\"", "Electronics",    32_000.0, 12);
            inventory.addProduct("Cotton T-Shirt",    "Clothing",          799.0, 50);
            inventory.addProduct("Denim Jeans",       "Clothing",        2_499.0, 20);
            inventory.addProduct("Running Shoes",     "Footwear",        4_999.0, 0);  // out of stock
            inventory.addProduct("Office Chair",      "Furniture",      18_500.0, 7);
            inventory.addProduct("Desk Lamp",         "Furniture",       1_299.0, 2);  // low
            inventory.addProduct("Python Crash Course","Books",            799.0, 25);
            inventory.addProduct("Clean Code",        "Books",           1_499.0, 15);
        } catch (DuplicateProductException e) {
            System.out.println("  Seed error: " + e.getMessage());
        }
        System.out.println("  ✔ " + inventory.size() + " products loaded.");
    }

    // ── UI helpers ────────────────────────────────────────────
    static void printMenu() {
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║       CLI INVENTORY MANAGER  v1.0        ║");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.println("║  1.  View all products                    ║");
        System.out.println("║  2.  Search by name                       ║");
        System.out.println("║  3.  Search by category                   ║");
        System.out.println("║  4.  Add new product                      ║");
        System.out.println("║  5.  Remove product                       ║");
        System.out.println("║  6.  Update price                         ║");
        System.out.println("║  7.  Update quantity (set)                ║");
        System.out.println("║  8.  Adjust stock (+ or -)                ║");
        System.out.println("║  9.  Low stock alerts                     ║");
        System.out.println("║  10. Inventory summary                    ║");
        System.out.println("║  0.  Exit                                 ║");
        System.out.println("╚══════════════════════════════════════════╝");
        System.out.print("  Choice: ");
    }

    static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  ⚠  Please enter a whole number.");
            }
        }
    }

    static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  ⚠  Please enter a valid number.");
            }
        }
    }

    static String readString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    // ── Menu handlers ─────────────────────────────────────────

    static void handleAdd() {
        System.out.println("\n  --- Add New Product ---");
        String name     = readString("  Name       : ");
        String category = readString("  Category   : ");
        double price    = readDouble("  Price (₹)  : ");
        int    qty      = readInt   ("  Quantity   : ");

        try {
            Product p = inventory.addProduct(name, category, price, qty);
            System.out.println("  ✔ Added: " + p);
        } catch (DuplicateProductException e) {
            System.out.println("  ✘ " + e.getMessage());
        } catch (InvalidProductDataException e) {
            System.out.println("  ✘ Validation error: " + e.getMessage());
        }
    }

    static void handleRemove() {
        int id = readInt("\n  Enter Product ID to remove: ");
        try {
            Product p = inventory.getById(id);
            System.out.println("  Removing: " + p.getName() + " (ID " + id + ")");
            System.out.print("  Confirm (yes/no)? ");
            if (sc.nextLine().trim().equalsIgnoreCase("yes")) {
                inventory.removeProduct(id);
                System.out.println("  ✔ Removed.");
            } else {
                System.out.println("  Cancelled.");
            }
        } catch (ProductNotFoundException e) {
            System.out.println("  ✘ " + e.getMessage());
        }
    }

    static void handleUpdatePrice() {
        int    id    = readInt   ("\n  Product ID  : ");
        double price = readDouble("  New price (₹): ");
        try {
            inventory.updatePrice(id, price);
            System.out.println("  ✔ Price updated.");
        } catch (ProductNotFoundException e) {
            System.out.println("  ✘ " + e.getMessage());
        } catch (InvalidProductDataException e) {
            System.out.println("  ✘ " + e.getMessage());
        }
    }

    static void handleUpdateQty() {
        int id  = readInt("\n  Product ID     : ");
        int qty = readInt("  New quantity   : ");
        try {
            inventory.updateQuantity(id, qty);
            System.out.println("  ✔ Quantity updated.");
        } catch (ProductNotFoundException e) {
            System.out.println("  ✘ " + e.getMessage());
        } catch (InvalidProductDataException e) {
            System.out.println("  ✘ " + e.getMessage());
        }
    }

    static void handleAdjustStock() {
        int id    = readInt("\n  Product ID     : ");
        int delta = readInt("  Adjust by (+/-): ");
        try {
            inventory.adjustStock(id, delta);
            Product p = inventory.getById(id);
            System.out.printf("  ✔ New stock for '%s': %d%n", p.getName(), p.getQuantity());
        } catch (ProductNotFoundException e) {
            System.out.println("  ✘ " + e.getMessage());
        } catch (InvalidProductDataException e) {
            System.out.println("  ✘ " + e.getMessage());
        }
    }

    // ═══════════════════════════════════════════════════════
    //  ENTRY POINT
    // ═══════════════════════════════════════════════════════
    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║  CLI INVENTORY MANAGER  –  Day 21        ║");
        System.out.println("║  Week 3 Capstone                         ║");
        System.out.println("╚══════════════════════════════════════════╝");

        seedData();

        boolean running = true;
        while (running) {
            printMenu();
            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  ⚠  Enter a number 0–10.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("  Sort by: 1) Name  2) Price  3) Quantity  4) Value");
                    System.out.print("  Choice (or Enter for default): ");
                    String sortInput = sc.nextLine().trim();
                    String sortBy = sortInput.equals("1") ? "name"
                                  : sortInput.equals("2") ? "price"
                                  : sortInput.equals("3") ? "quantity"
                                  : sortInput.equals("4") ? "value" : "default";
                    inventory.printTable(inventory.getAllSorted(sortBy),
                            "All Products (sorted by " + sortBy + ")");
                    break;

                case 2:
                    String nameKw = readString("\n  Name keyword: ");
                    List<Product> nameResults = inventory.searchByName(nameKw);
                    inventory.printTable(nameResults, "Search: \"" + nameKw + "\"");
                    break;

                case 3:
                    String catKw = readString("\n  Category keyword: ");
                    List<Product> catResults = inventory.searchByCategory(catKw);
                    inventory.printTable(catResults, "Category: \"" + catKw + "\"");
                    break;

                case 4:  handleAdd();          break;
                case 5:  handleRemove();       break;
                case 6:  handleUpdatePrice();  break;
                case 7:  handleUpdateQty();    break;
                case 8:  handleAdjustStock();  break;

                case 9:
                    List<Product> lowStock = inventory.getLowStockProducts();
                    inventory.printTable(lowStock, "⚠  Low / Out-of-Stock Alerts");
                    break;

                case 10:
                    inventory.printSummary();
                    break;

                case 0:
                    running = false;
                    System.out.println("\n  Inventory Manager closed. Goodbye!");
                    break;

                default:
                    System.out.println("  ⚠  Choose 0–10.");
            }
        }
        sc.close();
    }
}
