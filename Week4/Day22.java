

// Day22.java
import java.io.*;
import java.util.*;

/**
 * Day 22: File Handling – Inventory Manager with File Persistence
 * 
 * Demonstrates:
 * - FileWriter  (writing product data to a file)
 * - FileReader + BufferedReader (reading the file)
 * - CRUD operations on a list of products
 * - Saving/Loading inventory to/from "inventory.txt"
 */
public class Day22 {

    // --- Inner Product class (simple POJO) ---
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

        // Getters and setters (optional, but used for updates)
        public int getId() { return id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }

        @Override
        public String toString() {
            return String.format("ID: %d | Name: %s | Price: %.2f | Qty: %d",
                                 id, name, price, quantity);
        }

        // Convert to CSV line for file storage
        public String toFileString() {
            return id + "," + name + "," + price + "," + quantity;
        }

        // Create a Product from a CSV line
        public static Product fromFileString(String line) {
            String[] parts = line.split(",");
            if (parts.length == 4) {
                try {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    double price = Double.parseDouble(parts[2].trim());
                    int qty = Integer.parseInt(parts[3].trim());
                    return new Product(id, name, price, qty);
                } catch (NumberFormatException e) {
                    System.err.println("Skipping malformed line: " + line);
                }
            }
            return null;
        }
    }

    // --- Inventory file name ---
    private static final String FILE_NAME = "inventory.txt";

    // --- In‑memory product list ---
    private static List<Product> inventory = new ArrayList<>();

    // --- Scanner for user input ---
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Load inventory from file at startup
        loadFromFile();

        int choice;
        do {
            printMenu();
            System.out.print("Enter your choice: ");
            choice = readInt();

            switch (choice) {
                case 1 -> addProduct();
                case 2 -> deleteProduct();
                case 3 -> updateProduct();
                case 4 -> listProducts();
                case 5 -> saveToFile();
                case 6 -> loadFromFile();
                case 7 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 7);
    }

    private static void printMenu() {
        System.out.println("\n--- INVENTORY MANAGER (File Handling Demo) ---");
        System.out.println("1. Add Product");
        System.out.println("2. Delete Product");
        System.out.println("3. Update Product");
        System.out.println("4. List All Products");
        System.out.println("5. Save to File");
        System.out.println("6. Load from File");
        System.out.println("7. Exit");
    }

    // --- CRUD Operations ---
    private static void addProduct() {
        System.out.print("Enter product ID: ");
        int id = readInt();
        // Check if ID already exists
        if (findProductById(id) != null) {
            System.out.println("Product with this ID already exists!");
            return;
        }
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter product price: ");
        double price = readDouble();
        System.out.print("Enter product quantity: ");
        int qty = readInt();

        Product p = new Product(id, name, price, qty);
        inventory.add(p);
        System.out.println("Product added successfully.");
    }

    private static void deleteProduct() {
        System.out.print("Enter product ID to delete: ");
        int id = readInt();
        Product p = findProductById(id);
        if (p != null) {
            inventory.remove(p);
            System.out.println("Product removed.");
        } else {
            System.out.println("Product not found.");
        }
    }

    private static void updateProduct() {
        System.out.print("Enter product ID to update: ");
        int id = readInt();
        Product p = findProductById(id);
        if (p == null) {
            System.out.println("Product not found.");
            return;
        }
        System.out.println("Current details: " + p);
        System.out.print("Enter new name (or press Enter to keep unchanged): ");
        String name = scanner.nextLine();
        if (!name.isBlank()) p.setName(name);

        System.out.print("Enter new price (or -1 to keep unchanged): ");
        double price = readDouble();
        if (price >= 0) p.setPrice(price);

        System.out.print("Enter new quantity (or -1 to keep unchanged): ");
        int qty = readInt();
        if (qty >= 0) p.setQuantity(qty);

        System.out.println("Product updated.");
    }

    private static void listProducts() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
        } else {
            System.out.println("\nCurrent Inventory:");
            for (Product p : inventory) {
                System.out.println(p);
            }
        }
    }

    // --- File I/O Methods ---
    private static void saveToFile() {
        // Using FileWriter (wrapped in BufferedWriter for efficiency, but FileWriter alone is fine)
        // Here we use try-with-resources to auto-close.
        try (FileWriter fw = new FileWriter(FILE_NAME);
             BufferedWriter bw = new BufferedWriter(fw)) {

            for (Product p : inventory) {
                bw.write(p.toFileString());
                bw.newLine();
            }
            System.out.println("Inventory saved to " + FILE_NAME);
        } catch (IOException e) {
            System.err.println("Error saving file: " + e.getMessage());
        }
    }

    private static void loadFromFile() {
        // Using FileReader and BufferedReader to read line by line
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No saved inventory file found. Starting fresh.");
            return;
        }

        List<Product> loaded = new ArrayList<>();
        try (FileReader fr = new FileReader(file);
             BufferedReader br = new BufferedReader(fr)) {

            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue; // skip empty lines
                Product p = Product.fromFileString(line);
                if (p != null) {
                    loaded.add(p);
                }
            }
            inventory = loaded; // replace current inventory
            System.out.println("Inventory loaded from " + FILE_NAME);
        } catch (IOException e) {
            System.err.println("Error loading file: " + e.getMessage());
        }
    }

    // --- Helper methods for input ---
    private static Product findProductById(int id) {
        for (Product p : inventory) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    private static int readInt() {
        while (true) {
            try {
                int val = Integer.parseInt(scanner.nextLine());
                return val;
            } catch (NumberFormatException e) {
                System.out.print("Invalid number. Please enter an integer: ");
            }
        }
    }

    private static double readDouble() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid number. Please enter a decimal number: ");
            }
        }
    }
}