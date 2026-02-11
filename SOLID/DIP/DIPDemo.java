package SOLID.DIP;

interface MessageSender {

    void send(String to, String message);
}

class SmtpServer implements MessageSender {

    public void send(String to, String message) {
        System.out.println("Sending via SMTP to " + to + ": " + message);
    }
}

class EmailService {

    private MessageSender sender;

    public EmailService(MessageSender sender) {
        this.sender = sender;
    }

    public void sendEmail(String to, String msg) {
        sender.send(to, msg);
    }
}

// Usage
public class DIPDemo {

    public static void main(String[] args) {
        EmailService service = new EmailService(new SmtpServer());
        service.sendEmail("john@example.com", "Hello!");
    }
}
