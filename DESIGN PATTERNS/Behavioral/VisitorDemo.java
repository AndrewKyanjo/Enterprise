package DESIGN PATTERNS.Behavioral;

interface Element {
    void accept(Visitor visitor);
}

class Book implements Element {
    public void accept(Visitor v) { v.visit(this); }
    public double getPrice() { return 20.0; }
}

class Fruit implements Element {
    public void accept(Visitor v) { v.visit(this); }
    public double getPricePerKg() { return 5.0; }
    public int getWeight() { return 3; }
}

interface Visitor {
    void visit(Book book);
    void visit(Fruit fruit);
}

class PriceVisitor implements Visitor {
    private double total = 0;
    public void visit(Book book) { total += book.getPrice(); }
    public void visit(Fruit fruit) { total += fruit.getPricePerKg() * fruit.getWeight(); }
    public double getTotal() { return total; }
}

// Usage
public class VisitorDemo {
    public static void main(String[] args) {
        Element[] items = { new Book(), new Fruit() };
        PriceVisitor visitor = new PriceVisitor();
        for (Element e : items) {
            e.accept(visitor);
        }
        System.out.println("Total price: $" + visitor.getTotal());
    }
}
