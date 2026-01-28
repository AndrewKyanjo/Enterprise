// Day27.java
/**
 * Day 27: Final Project Planning – CLI Task Manager
 * 
 * This file outlines the design and structure of the final project.
 * 
 * Project: Task Manager
 * Features:
 *   - Add task (with description, due date, priority)
 *   - Delete task
 *   - Mark task as complete
 *   - List all tasks (filter by status, priority)
 *   - Save tasks to file
 *   - Load tasks from file on startup
 * 
 * Classes:
 *   - Task (model)
 *   - TaskManager (business logic + file I/O)
 *   - Main (CLI menu)
 * 
 * The project will be implemented over Days 28-29.
 */

// === SKELETON CLASSES (to be filled later) ===

// --- Task class ---
// Fields: id (auto-generated), description, dueDate (LocalDate), priority (enum: LOW, MEDIUM, HIGH), completed (boolean)
// Constructors, getters, setters, toString, toCsv, fromCsv

// --- Priority enum (inside Task) ---
// LOW, MEDIUM, HIGH

// --- TaskManager class ---
// - List<Task> tasks
// - nextId counter
// - loadFromFile() : reads tasks from "tasks.txt"
// - saveToFile() : writes tasks to "tasks.txt"
// - addTask(...) : creates Task with new id, adds to list, saves
// - deleteTask(id) : removes task, saves
// - markCompleted(id) : sets completed=true, saves
// - getAllTasks() : returns list
// - getTasks(completedFilter, priorityFilter) : filtered list

// --- Main class (Day27) ---
// - main(): loads tasks, shows menu loop with options:
//   1. Add Task
//   2. Delete Task
//   3. Mark Task Complete
//   4. List All Tasks
//   5. List Completed Tasks
//   6. List Pending Tasks
//   7. Save (manual save)
//   8. Exit

// This skeleton will be fully implemented in Day28-29.
public class Day27 {
    public static void main(String[] args) {
        System.out.println("Task Manager Design Document");
        System.out.println("See comments above for the full design.");
        // No actual functionality yet – just planning.
    }
}