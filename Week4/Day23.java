
/**
 * // Day23.java
 * Day 23: Modularization – Separation of Concerns
 *
 * This single file illustrates how a project would be organised into packages.
 * In a real application, each class would reside in its own file under appropriate folders:
 *
 *   src/
 *     ├── com.example.model/
 *     │      └── Product.java
 *     ├── com.example.dao/
 *     │      └── ProductDAO.java
 *     └── com.example.service/
 *            └── InventoryService.java
 *
 * Here we keep everything in one file for simplicity, but the comments show the intended package.
 */

// ========== Package: com.example.model ==========
import java.util.*;

class Product {

    private int id;
    private String name;
    private double price;

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    // getters and setters (omitted for brevity)
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("Product[%d: %s, $%.2f]", id, name, price);
    }
}

// ========== Package: com.example.dao ==========


class ProductDAO {

    private List<Product> products = new ArrayList<>();

    public void add(Product p) {
        products.add(p);
    }
    
    public void remove(int id) {
        products.removeIf(p -> p.getId() == id);
    }

    public Product findById(int id) {
        return products.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    public List<Product> getAll() {
        return new ArrayList<>(products);
    }
}

// ========== Package: com.example.service ==========
class InventoryService {

    private ProductDAO dao = new ProductDAO();

    public void registerProduct(Product p) {
        if (p.getName() == null || p.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        dao.add(p);
    }

    public List<Product> listProducts() {
        return dao.getAll();
    }
    // other business methods...
}

// ========== Main class to demonstrate ==========
public class Day23 {

    public static void main(String[] args) {
        InventoryService service = new InventoryService();
        service.registerProduct(new Product(1, "Laptop", 899.99));
        service.registerProduct(new Product(2, "Mouse", 12.50));

        System.out.println("Products in inventory:");
        service.listProducts().forEach(System.out::println);
    }
}
