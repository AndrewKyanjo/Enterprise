package DESIGN PATTERNS.Behavioral;

abstract class Logger {
    public static int INFO = 1;
    public static int DEBUG = 2;
    public static int ERROR = 3;
    protected int level;
    protected Logger nextLogger;
    public void setNext(Logger next) { this.nextLogger = next; }
    public void logMessage(int level, String msg) {
        if (this.level <= level) {
            write(msg);
        }
        if (nextLogger != null) {
            nextLogger.logMessage(level, msg);
        }
    }
    abstract protected void write(String msg);
}

class ConsoleLogger extends Logger {
    public ConsoleLogger(int level) { this.level = level; }
    protected void write(String msg) { System.out.println("Console: " + msg); }
}

class FileLogger extends Logger {
    public FileLogger(int level) { this.level = level; }
    protected void write(String msg) { System.out.println("File: " + msg); }
}

class ErrorLogger extends Logger {
    public ErrorLogger(int level) { this.level = level; }
    protected void write(String msg) { System.out.println("Error: " + msg); }
}

// Usage
public class ChainOfResponsibilityDemo {
    public static void main(String[] args) {
        Logger console = new ConsoleLogger(Logger.INFO);
        Logger file = new FileLogger(Logger.DEBUG);
        Logger error = new ErrorLogger(Logger.ERROR);
        console.setNext(file);
        file.setNext(error);

        console.logMessage(Logger.INFO, "Information message");
        console.logMessage(Logger.DEBUG, "Debug message");
        console.logMessage(Logger.ERROR, "Error message");
    }
}