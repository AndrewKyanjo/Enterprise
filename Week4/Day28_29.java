// Day28_29.java
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Day 28-29: Final Project – CLI Task Manager with File Persistence
 * 
 * Features:
 * - Add, delete, mark complete, list tasks
 * - Save/load tasks to/from "tasks.txt"
 * - Filter by status and priority
 */
public class Day28_29 {

    // ---------- Task class with nested Priority enum ----------
    public static class Task {
        public enum Priority { LOW, MEDIUM, HIGH }

        private static int idCounter = 1; // will be overwritten when loading
        private int id;
        private String description;
        private LocalDate dueDate;
        private Priority priority;
        private boolean completed;

        // Constructor for new tasks (id auto-assigned)
        public Task(String description, LocalDate dueDate, Priority priority) {
            this.id = idCounter++;
            this.description = description;
            this.dueDate = dueDate;
            this.priority = priority;
            this.completed = false;
        }

        // Constructor for recreating from file (with known id)
        public Task(int id, String description, LocalDate dueDate, Priority priority, boolean completed) {
            this.id = id;
            this.description = description;
            this.dueDate = dueDate;
            this.priority = priority;
            this.completed = completed;
            // update idCounter if necessary
            if (id >= idCounter) idCounter = id + 1;
        }

        // Getters / Setters
        public int getId() { return id; }
        public String getDescription() { return description; }
        public LocalDate getDueDate() { return dueDate; }
        public Priority getPriority() { return priority; }
        public boolean isCompleted() { return completed; }
        public void setCompleted(boolean completed) { this.completed = completed; }

        @Override
        public String toString() {
            return String.format("[%d] %s | Due: %s | Priority: %s | %s",
                    id, description, dueDate, priority, (completed ? "✓ Done" : "◻ Pending"));
        }

        // Convert to CSV line for file storage
        public String toCsv() {
            return id + "," + description + "," + dueDate + "," + priority + "," + completed;
        }

        // Create a Task from a CSV line
        public static Task fromCsv(String line) {
            String[] parts = line.split(",", -1); // -1 to keep empty trailing fields
            if (parts.length == 5) {
                try {
                    int id = Integer.parseInt(parts[0].trim());
                    String desc = parts[1].trim();
                    LocalDate date = LocalDate.parse(parts[2].trim());
                    Priority prio = Priority.valueOf(parts[3].trim().toUpperCase());
                    boolean done = Boolean.parseBoolean(parts[4].trim());
                    return new Task(id, desc, date, prio, done);
                } catch (IllegalArgumentException e) {
                    System.err.println("Skipping malformed line: " + line);
                }
            }
            return null;
        }
    }

    // ---------- TaskManager: business logic + file I/O ----------
    public static class TaskManager {
        private final List<Task> tasks = new ArrayList<>();
        private final String filename;

        public TaskManager(String filename) {
            this.filename = filename;
            loadFromFile();
        }

        // Load tasks from file, update idCounter
        private void loadFromFile() {
            File file = new File(filename);
            if (!file.exists()) {
                System.out.println("No existing task file. Starting fresh.");
                return;
            }
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.isBlank()) continue;
                    Task t = Task.fromCsv(line);
                    if (t != null) tasks.add(t);
                }
                System.out.println("Loaded " + tasks.size() + " tasks from " + filename);
            } catch (IOException e) {
                System.err.println("Error loading tasks: " + e.getMessage());
            }
        }

        // Save all tasks to file
        public void saveToFile() {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
                for (Task t : tasks) {
                    bw.write(t.toCsv());
                    bw.newLine();
                }
                System.out.println("Saved " + tasks.size() + " tasks to " + filename);
            } catch (IOException e) {
                System.err.println("Error saving tasks: " + e.getMessage());
            }
        }

        // CRUD operations
        public void addTask(String description, LocalDate dueDate, Task.Priority priority) {
            Task t = new Task(description, dueDate, priority);
            tasks.add(t);
            saveToFile();
            System.out.println("Task added with ID: " + t.getId());
        }

        public boolean deleteTask(int id) {
            boolean removed = tasks.removeIf(t -> t.getId() == id);
            if (removed) saveToFile();
            return removed;
        }

        public boolean markCompleted(int id) {
            for (Task t : tasks) {
                if (t.getId() == id) {
                    t.setCompleted(true);
                    saveToFile();
                    return true;
                }
            }
            return false;
        }

        public List<Task> getAllTasks() {
            return new ArrayList<>(tasks);
        }

        public List<Task> getTasks(boolean completed) {
            return tasks.stream()
                    .filter(t -> t.isCompleted() == completed)
                    .collect(Collectors.toList());
        }

        public List<Task> getTasksByPriority(Task.Priority priority) {
            return tasks.stream()
                    .filter(t -> t.getPriority() == priority)
                    .collect(Collectors.toList());
        }
    }

    // ---------- Main CLI ----------
    private static final Scanner scanner = new Scanner(System.in);
    private static TaskManager manager;

    public static void main(String[] args) {
        manager = new TaskManager("tasks.txt");
        int choice;
        do {
            printMenu();
            choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1 -> addTask();
                case 2 -> deleteTask();
                case 3 -> markTaskComplete();
                case 4 -> listAllTasks();
                case 5 -> listTasksByStatus(true);   // completed
                case 6 -> listTasksByStatus(false);  // pending
                case 7 -> listTasksByPriority();
                case 8 -> manager.saveToFile();      // manual save
                case 9 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 9);
    }

    private static void printMenu() {
        System.out.println("\n=== TASK MANAGER ===");
        System.out.println("1. Add Task");
        System.out.println("2. Delete Task");
        System.out.println("3. Mark Task as Completed");
        System.out.println("4. List All Tasks");
        System.out.println("5. List Completed Tasks");
        System.out.println("6. List Pending Tasks");
        System.out.println("7. List Tasks by Priority");
        System.out.println("8. Save to File (manual)");
        System.out.println("9. Exit");
    }

    private static void addTask() {
        System.out.print("Enter description: ");
        String desc = scanner.nextLine();
        System.out.print("Enter due date (yyyy-mm-dd): ");
        LocalDate date = null;
        while (date == null) {
            try {
                date = LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.print("Invalid date format. Use yyyy-mm-dd: ");
            }
        }
        System.out.print("Enter priority (LOW, MEDIUM, HIGH): ");
        Task.Priority priority = null;
        while (priority == null) {
            try {
                priority = Task.Priority.valueOf(scanner.nextLine().trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.print("Invalid priority. Enter LOW, MEDIUM, or HIGH: ");
            }
        }
        manager.addTask(desc, date, priority);
    }

    private static void deleteTask() {
        int id = readInt("Enter task ID to delete: ");
        if (manager.deleteTask(id)) {
            System.out.println("Task deleted.");
        } else {
            System.out.println("Task not found.");
        }
    }

    private static void markTaskComplete() {
        int id = readInt("Enter task ID to mark complete: ");
        if (manager.markCompleted(id)) {
            System.out.println("Task marked complete.");
        } else {
            System.out.println("Task not found.");
        }
    }

    private static void listAllTasks() {
        List<Task> tasks = manager.getAllTasks();
        if (tasks.isEmpty()) System.out.println("No tasks.");
        else tasks.forEach(System.out::println);
    }

    private static void listTasksByStatus(boolean completed) {
        List<Task> tasks = manager.getTasks(completed);
        String status = completed ? "Completed" : "Pending";
        System.out.println("--- " + status + " Tasks ---");
        if (tasks.isEmpty()) System.out.println("None.");
        else tasks.forEach(System.out::println);
    }

    private static void listTasksByPriority() {
        System.out.print("Enter priority (LOW, MEDIUM, HIGH): ");
        try {
            Task.Priority priority = Task.Priority.valueOf(scanner.nextLine().trim().toUpperCase());
            List<Task> tasks = manager.getTasksByPriority(priority);
            System.out.println("--- Priority: " + priority + " ---");
            if (tasks.isEmpty()) System.out.println("No tasks.");
            else tasks.forEach(System.out::println);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid priority.");
        }
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("Invalid number. " + prompt);
        }
        int val = scanner.nextInt();
        scanner.nextLine();
        return val;
    }
}