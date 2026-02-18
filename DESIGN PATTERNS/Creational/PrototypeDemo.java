package DESIGN PATTERNS.Creational;

class Shape implements Cloneable {
    private String type;
    public Shape(String type) { this.type = type; }
    public void setType(String type) { this.type = type; }
    public String getType() { return type; }
    public Shape clone() {
        try {
            return (Shape) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}

// Usage
public class PrototypeDemo {
    public static void main(String[] args) {
        Shape original = new Shape("Circle");
        Shape copy = original.clone();
        copy.setType("Square");
        System.out.println(original.getType()); // Circle
        System.out.println(copy.getType());     // Square
    }
}