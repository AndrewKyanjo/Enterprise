// Day25.java
import java.io.*;
import java.util.*;

/**
 * Day 25: Debugging & Refactoring – Cleaned up Inventory Manager from Day 22
 * 
 * Changes made:
 * - Extracted duplicate input handling into reusable methods.
 * - Renamed variables for clarity.
 * - Separated file operations into a dedicated FileHandler class.
 * - Added simple debug prints (can be toggled).
 */
public class Day25 {

    // ---------- Product class (unchanged) ----------
    public static class Product {
        private int id;
        private String name;
        private double price;
        private int quantity;

        public Product(int id, String name, double price, int quantity) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public double getPrice() { return price; }
        public int getQuantity() { return quantity; }
        public void setName(String name) { this.name = name; }
        public void setPrice(double price) { this.price = price; }
        public void setQuantity(int quantity) { this.quantity = quantity; }

        @Override
        public String toString() {
            return String.format("ID: %d | %s | $%.2f | Qty: %d", id, name, price, quantity);
        }

        public String toCsv() {
            return id + "," + name + "," + price + "," + quantity;
        }

        public static Product fromCsv(String line) {
            String[] parts = line.split(",");
            if (parts.length == 4) {
                try {
                    return new Product(
                        Integer.parseInt(parts[0].trim()),
                        parts[1].trim(),
                        Double.parseDouble(parts[2].trim()),
                        Integer.parseInt(parts[3].trim())
                    );
                } catch (NumberFormatException e) {
                    System.err.println("Malformed line: " + line);
                }
            }
            return null;
        }
    }

    // ---------- FileHandler class (new, separates file I/O) ----------
    public static class FileHandler {
        private final String filename;

        public FileHandler(String filename) {
            this.filename = filename;
        }

        public void save(List<Product> products) throws IOException {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
                for (Product p : products) {
                    writer.write(p.toCsv());
                    writer.newLine();
                }
            }
            System.out.println("Saved " + products.size() + " products to " + filename);
        }

        public List<Product> load() throws IOException {
            List<Product> result = new ArrayList<>();
            File file = new File(filename);
            if (!file.exists()) {
                return result; // empty list
            }
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.isBlank()) {
                        Product p = Product.fromCsv(line);
                        if (p != null) result.add(p);
                    }
                }
            }
            System.out.println("Loaded " + result.size() + " products from " + filename);
            return result;
        }
    }

    // ---------- Main application ----------
    private static final String FILENAME = "inventory_refactored.txt";
    private static final Scanner SCANNER = new Scanner(System.in);
    private static List<Product> inventory = new ArrayList<>();
    private static final FileHandler fileHandler = new FileHandler(FILENAME);

    public static void main(String[] args) {
        // Load initial data
        try {
            inventory = fileHandler.load();
        } catch (IOException e) {
            System.err.println("Could not load inventory: " + e.getMessage());
        }

        int choice;
        do {
            printMenu();
            choice = readInt("Choose an option: ");
            switch (choice) {
                case 1 -> addProduct();
                case 2 -> deleteProduct();
                case 3 -> updateProduct();
                case 4 -> listProducts();
                case 5 -> saveToFile();
                case 6 -> loadFromFile();
                case 7 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 7);
    }

    private static void printMenu() {
        System.out.println("\n--- REFACTORED INVENTORY MANAGER ---");
        System.out.println("1. Add Product");
        System.out.println("2. Delete Product");
        System.out.println("3. Update Product");
        System.out.println("4. List All Products");
        System.out.println("5. Save to File");
        System.out.println("6. Load from File");
        System.out.println("7. Exit");
    }

    // Refactored input methods
    private static int readInt(String prompt) {
        System.out.print(prompt);
        while (!SCANNER.hasNextInt()) {
            SCANNER.next(); // discard
            System.out.print("Invalid input. " + prompt);
        }
        int value = SCANNER.nextInt();
        SCANNER.nextLine(); // consume newline
        return value;
    }

    private static double readDouble(String prompt) {
        System.out.print(prompt);
        while (!SCANNER.hasNextDouble()) {
            SCANNER.next();
            System.out.print("Invalid input. " + prompt);
        }
        double value = SCANNER.nextDouble();
        SCANNER.nextLine();
        return value;
    }

    private static String readString(String prompt) {
        System.out.print(prompt);
        return SCANNER.nextLine();
    }

    // CRUD operations
    private static void addProduct() {
        int id = readInt("Enter product ID: ");
        if (findById(id) != null) {
            System.out.println("ID already exists.");
            return;
        }
        String name = readString("Enter name: ");
        double price = readDouble("Enter price: ");
        int qty = readInt("Enter quantity: ");
        inventory.add(new Product(id, name, price, qty));
        System.out.println("Product added.");
    }

    private static void deleteProduct() {
        int id = readInt("Enter ID to delete: ");
        if (inventory.removeIf(p -> p.getId() == id)) {
            System.out.println("Product removed.");
        } else {
            System.out.println("ID not found.");
        }
    }

    private static void updateProduct() {
        int id = readInt("Enter ID to update: ");
        Product p = findById(id);
        if (p == null) {
            System.out.println("Product not found.");
            return;
        }
        System.out.println("Current: " + p);
        String name = readString("Enter new name (or press Enter to keep): ");
        if (!name.isBlank()) p.setName(name);

        double price = readDouble("Enter new price (or -1 to keep): ");
        if (price >= 0) p.setPrice(price);

        int qty = readInt("Enter new quantity (or -1 to keep): ");
        if (qty >= 0) p.setQuantity(qty);

        System.out.println("Product updated.");
    }

    private static void listProducts() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
        } else {
            inventory.forEach(System.out::println);
        }
    }

    private static void saveToFile() {
        try {
            fileHandler.save(inventory);
        } catch (IOException e) {
            System.err.println("Save failed: " + e.getMessage());
        }
    }

    private static void loadFromFile() {
        try {
            inventory = fileHandler.load();
        } catch (IOException e) {
            System.err.println("Load failed: " + e.getMessage());
        }
    }

    private static Product findById(int id) {
        for (Product p : inventory) {
            if (p.getId() == id) return p;
        }
        return null;
    }
}