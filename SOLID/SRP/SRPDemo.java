package SOLID.SRP;

// SRP - Good example
class ReportGenerator {
    public String generateContent() {
        return "Report data: ...";
    }
}

class ReportSaver {
    public void saveToFile(String content, String filename) {
        // file writing logic
    }
}

// Usage
public class SRPDemo {
    public static void main(String[] args) {
        ReportGenerator gen = new ReportGenerator();
        String content = gen.generateContent();
        ReportSaver saver = new ReportSaver();
        saver.saveToFile(content, "report.txt");
    }
}