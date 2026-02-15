package DESIGN PATTERNS.Creational;

class Pizza {
    private String dough;
    private String sauce;
    private String topping;

    private Pizza(Builder builder) {
        this.dough = builder.dough;
        this.sauce = builder.sauce;
        this.topping = builder.topping;
    }

    public static class Builder {
        private String dough;
        private String sauce;
        private String topping;

        public Builder dough(String dough) { this.dough = dough; return this; }
        public Builder sauce(String sauce) { this.sauce = sauce; return this; }
        public Builder topping(String topping) { this.topping = topping; return this; }
        public Pizza build() { return new Pizza(this); }
    }

    @Override
    public String toString() {
        return "Pizza [dough=" + dough + ", sauce=" + sauce + ", topping=" + topping + "]";
    }
}

// Usage
public class BuilderDemo {
    public static void main(String[] args) {
        Pizza pizza = new Pizza.Builder()
                .dough("thin crust")
                .sauce("tomato")
                .topping("cheese")
                .build();
        System.out.println(pizza);
    }
}