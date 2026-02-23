package DESIGN PATTERNS.Behavioral;

class Memento {
    private String state;
    public Memento(String state) { this.state = state; }
    public String getState() { return state; }
}

class Originator {
    private String state;
    public void setState(String state) { this.state = state; }
    public String getState() { return state; }
    public Memento saveStateToMemento() { return new Memento(state); }
    public void getStateFromMemento(Memento m) { state = m.getState(); }
}

class Caretaker {
    private List<Memento> history = new ArrayList<>();
    public void add(Memento m) { history.add(m); }
    public Memento get(int index) { return history.get(index); }
}

// Usage
public class MementoDemo {
    public static void main(String[] args) {
        Originator originator = new Originator();
        Caretaker caretaker = new Caretaker();

        originator.setState("State #1");
        caretaker.add(originator.saveStateToMemento());

        originator.setState("State #2");
        caretaker.add(originator.saveStateToMemento());

        originator.setState("State #3");
        System.out.println("Current: " + originator.getState());

        originator.getStateFromMemento(caretaker.get(0));
        System.out.println("Restored to: " + originator.getState());
    }
}