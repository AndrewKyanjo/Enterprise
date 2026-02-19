package DESIGN PATTERNS.Structural;

import java.util.*;

interface Graphic {
    void draw();
}

class Circle implements Graphic {
    public void draw() { System.out.println("Draw Circle"); }
}

class Square implements Graphic {
    public void draw() { System.out.println("Draw Square"); }
}

class CompositeGraphic implements Graphic {
    private List<Graphic> children = new ArrayList<>();
    public void add(Graphic graphic) { children.add(graphic); }
    public void remove(Graphic graphic) { children.remove(graphic); }
    public void draw() {
        for (Graphic g : children) {
            g.draw();
        }
    }
}

// Usage
public class CompositeDemo {
    public static void main(String[] args) {
        Circle c1 = new Circle();
        Square s1 = new Square();
        CompositeGraphic group = new CompositeGraphic();
        group.add(c1);
        group.add(s1);
        group.draw(); // draws both
    }
}