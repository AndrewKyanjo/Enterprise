package SOLID.OCP;

interface Discount {
    double apply(double price);
}

class RegularDiscount implements Discount {
    public double apply(double price) { return price * 0.9; }
}

class PremiumDiscount implements Discount {
    public double apply(double price) { return price * 0.8; }
}

class DiscountCalculator {
    public double calculate(double price, Discount discount) {
        return discount.apply(price);
    }
}

// Usage
public class OCPDemo {
    public static void main(String[] args) {
        DiscountCalculator calc = new DiscountCalculator();
        System.out.println(calc.calculate(100, new RegularDiscount())); // 90.0
        System.out.println(calc.calculate(100, new PremiumDiscount())); // 80.0
    }
}