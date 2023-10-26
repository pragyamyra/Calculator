import java.util.*;

public class InfixEvaluator {
    public static double evaluateInfix(String infix) {
        Stack<Double> valueStack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();
        
        for (int i = 0; i < infix.length(); i++) {
            char token = infix.charAt(i);
            
            if (Character.isDigit(token) || token == '.') {
                
                StringBuilder numBuilder = new StringBuilder();
                while (i < infix.length() && (Character.isDigit(infix.charAt(i)) || infix.charAt(i) == '.')) {
                    numBuilder.append(infix.charAt(i));
                    i++;
                }
                i--; 
                String numStr = numBuilder.toString();
                double num = Double.parseDouble(numStr);
                valueStack.push(num);
            } else if (token == '(') {
            
                operatorStack.push(token);
            } else if (token == ')') {
                
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    evaluateTop(valueStack, operatorStack);
                }
                operatorStack.pop(); 
            } else if (isOperator(token)) {
                
                while (!operatorStack.isEmpty() && precedence(token) <= precedence(operatorStack.peek())) {
                    evaluateTop(valueStack, operatorStack);
                }
                operatorStack.push(token);
            }
        }

        
        while (!operatorStack.isEmpty()) {
            evaluateTop(valueStack, operatorStack);
        }

        
        if (valueStack.size() != 1) {
            throw new IllegalArgumentException(".....Invalid expression.....");
        }

        return valueStack.pop();
    }

    private static void evaluateTop(Stack<Double> valueStack, Stack<Character> operatorStack) {
        if (valueStack.size() < 2 || operatorStack.isEmpty() || operatorStack.peek() == '(') {
            throw new IllegalArgumentException(".....Invalid expression.....");
        }

        char operator = operatorStack.pop();
        double operand2 = valueStack.pop();
        double operand1 = valueStack.pop();
        double result;

        switch (operator) {
            case '+':
                result = operand1 + operand2;
                break;
            case '-':
                result = operand1 - operand2;
                break;
            case '*':
                result = operand1 * operand2;
                break;
            case '/':
                if (operand2 == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                result = operand1 / operand2;
                break;
            default:
                throw new IllegalArgumentException("Unsupported operator");
        }

        valueStack.push(result);
    }

    private static int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return 0;
        }
    }

    private static boolean isOperator(char token) {
        return "+-*/".indexOf(token) != -1;
    }

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        
        System.out.println("Welcome to the Command-Line Calculator!");
        System.out.println("Enter an arithmetic expression (e.g., 2+3/4*2), or type 'exit' to quit.");

        while (true) {
            System.out.println();
            System.out.print("Expression: ");
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Goodbye!");
                break;
            }

            try {
                double result = evaluateInfix(input);
                System.out.println("Result: " + result);
            } catch (Exception e) {
                System.out.println("Invalid expression. Please try again.");
            }
        }

        sc.close();
        
        
       
    }
}
