package DESIGN PATTERNS.Behavioral;

import java.util.*;

class NameCollection implements Iterable<String> {
    private String[] names;
    public NameCollection(String[] names) { this.names = names; }
    public Iterator<String> iterator() {
        return new Iterator<String>() {
            private int index = 0;
            public boolean hasNext() { return index < names.length; }
            public String next() { return names[index++]; }
        };
    }
}

// Usage
public class IteratorDemo {
    public static void main(String[] args) {
        NameCollection nc = new NameCollection(new String[]{"Alice", "Bob", "Charlie"});
        for (String name : nc) {
            System.out.println(name);
        }
    }
}