package DESIGN PATTERNS.Behavioral;

import java.util.*;

interface ChatMediator {
    void sendMessage(String msg, User user);
    void addUser(User user);
}

class ChatRoom implements ChatMediator {
    private List<User> users = new ArrayList<>();
    public void addUser(User user) { users.add(user); }
    public void sendMessage(String msg, User sender) {
        for (User u : users) {
            if (u != sender) {
                u.receive(msg);
            }
        }
    }
}

abstract class User {
    protected ChatMediator mediator;
    protected String name;
    public User(ChatMediator m, String n) { mediator = m; name = n; }
    public abstract void send(String msg);
    public abstract void receive(String msg);
}

class ChatUser extends User {
    public ChatUser(ChatMediator m, String n) { super(m, n); }
    public void send(String msg) {
        System.out.println(name + " sends: " + msg);
        mediator.sendMessage(msg, this);
    }
    public void receive(String msg) {
        System.out.println(name + " receives: " + msg);
    }
}

// Usage
public class MediatorDemo {
    public static void main(String[] args) {
        ChatMediator chat = new ChatRoom();
        User alice = new ChatUser(chat, "Alice");
        User bob   = new ChatUser(chat, "Bob");
        chat.addUser(alice);
        chat.addUser(bob);
        alice.send("Hello, Bob!");
        bob.send("Hi, Alice!");
    }
}