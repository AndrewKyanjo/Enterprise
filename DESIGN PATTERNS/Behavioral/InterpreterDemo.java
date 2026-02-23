package DESIGN PATTERNS.Behavioral;

interface Expression {
    boolean interpret(String context);
}

class TerminalExpression implements Expression {
    private String data;
    public TerminalExpression(String data) { this.data = data; }
    public boolean interpret(String context) {
        return context.contains(data);
    }
}

class OrExpression implements Expression {
    private Expression expr1;
    private Expression expr2;
    public OrExpression(Expression e1, Expression e2) { expr1 = e1; expr2 = e2; }
    public boolean interpret(String context) {
        return expr1.interpret(context) || expr2.interpret(context);
    }
}

class AndExpression implements Expression {
    private Expression expr1;
    private Expression expr2;
    public AndExpression(Expression e1, Expression e2) { expr1 = e1; expr2 = e2; }
    public boolean interpret(String context) {
        return expr1.interpret(context) && expr2.interpret(context);
    }
}

// Usage
public class InterpreterDemo {
    public static void main(String[] args) {
        Expression isMale = new OrExpression(
                new TerminalExpression("John"),
                new TerminalExpression("Robert")
        );
        Expression isMarried = new AndExpression(
                new TerminalExpression("Julie"),
                new TerminalExpression("Married")
        );

        System.out.println("John is male? " + isMale.interpret("John"));
        System.out.println("Julie is married? " + isMarried.interpret("Married Julie"));
    }
}