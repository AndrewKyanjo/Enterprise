package DESIGN PATTERNS.Creational;

class DatabaseConnection {
    private static DatabaseConnection instance;
    private DatabaseConnection() { } // private constructor

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public void query(String sql) {
        System.out.println("Executing: " + sql);
    }
}

// Usage
public class SingletonDemo {
    public static void main(String[] args) {
        DatabaseConnection db1 = DatabaseConnection.getInstance();
        DatabaseConnection db2 = DatabaseConnection.getInstance();
        System.out.println(db1 == db2); // true
        db1.query("SELECT * FROM users");
    }
}