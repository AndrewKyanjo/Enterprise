package DESIGN PATTERNS.Creational;

// Abstract products
interface Button {
    void render();
}
interface Checkbox {
    void check();
}

// Concrete products for Windows
class WinButton implements Button {
    public void render() { System.out.println("Render Windows button"); }
}
class WinCheckbox implements Checkbox {
    public void check() { System.out.println("Check Windows checkbox"); }
}

// Concrete products for Mac
class MacButton implements Button {
    public void render() { System.out.println("Render Mac button"); }
}
class MacCheckbox implements Checkbox {
    public void check() { System.out.println("Check Mac checkbox"); }
}

// Abstract factory
interface GUIFactory {
    Button createButton();
    Checkbox createCheckbox();
}

// Concrete factories
class WinFactory implements GUIFactory {
    public Button createButton() { return new WinButton(); }
    public Checkbox createCheckbox() { return new WinCheckbox(); }
}
class MacFactory implements GUIFactory {
    public Button createButton() { return new MacButton(); }
    public Checkbox createCheckbox() { return new MacCheckbox(); }
}

// Client
class Application {
    private Button button;
    private Checkbox checkbox;
    public Application(GUIFactory factory) {
        button = factory.createButton();
        checkbox = factory.createCheckbox();
    }
    public void paint() {
        button.render();
        checkbox.check();
    }
}

// Usage
public class AbstractFactoryDemo {
    public static void main(String[] args) {
        Application app = new Application(new WinFactory());
        app.paint();
    }
}