package SOLID.LSP;

// Good example - using abstraction
interface Shape {

    int getArea();
}

class Rectangle implements Shape {

    protected int width, height;

    public Rectangle(int w, int h) {
        width = w;
        height = h;
    }

    public int getArea() {
        return width * height;
    }
}

class Square implements Shape {

    private int side;

    public Square(int s) {
        side = s;
    }

    public int getArea() {
        return side * side;
    }
}

// Both can be used interchangeably as Shape
public class LSPDemo {

    public static void printArea(Shape s) {
        System.out.println("Area: " + s.getArea());
    }

    public static void main(String[] args) {
        printArea(new Rectangle(4, 5));
        printArea(new Square(4));
    }
}
