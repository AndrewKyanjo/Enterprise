package DESIGN PATTERNS.Structural;

import java.util.*;

// Flyweight
interface TreeModel {
    void display(int x, int y);
}

// Concrete flyweight (shared)
class TreeType implements TreeModel {
    private String name;
    private String color;
    public TreeType(String name, String color) {
        this.name = name;
        this.color = color;
    }
    public void display(int x, int y) {
        System.out.println(name + " tree of color " + color + " at (" + x + "," + y + ")");
    }
}

// Flyweight factory
class TreeFactory {
    private static Map<String, TreeType> types = new HashMap<>();
    public static TreeType getTreeType(String name, String color) {
        String key = name + ":" + color;
        if (!types.containsKey(key)) {
            types.put(key, new TreeType(name, color));
        }
        return types.get(key);
    }
}

// Context
class Tree {
    private int x, y;
    private TreeType type;
    public Tree(int x, int y, TreeType type) {
        this.x = x; this.y = y; this.type = type;
    }
    public void display() { type.display(x, y); }
}

// Usage
public class FlyweightDemo {
    public static void main(String[] args) {
        TreeType oak = TreeFactory.getTreeType("Oak", "Green");
        TreeType pine = TreeFactory.getTreeType("Pine", "Dark Green");
        Tree t1 = new Tree(1, 2, oak);
        Tree t2 = new Tree(3, 4, oak); // same type reused
        Tree t3 = new Tree(5, 6, pine);
        t1.display();
        t2.display();
        t3.display();
    }
}