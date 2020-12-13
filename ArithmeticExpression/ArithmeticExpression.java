package ArithmeticExpression;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Stack;

public class ArithmeticExpression {




    //Function to read file
    private String readAsString(String s) throws IOException {
        String data = new String(Files.readAllBytes(Paths.get(s)));
        return data;
    }

    public static void main(String[] args) throws IOException {
        ArithmeticExpression ae = new ArithmeticExpression();
        //Getting the current directory path
        String currentDirectory = System.getProperty("user.dir");

        String data = ae.readAsString(currentDirectory+"/arithmetic_data.txt");

        //Splitting the test cases from input file using the delimitor
        String[] test_cases = data.split("\n");

        //for every test case this code will run
        for(int i = 0 ; i<test_cases.length;i++) {
            System.out.println("Test case: " + (i + 1));

            String input = test_cases[i];
            ae.evaluate(input);

        }

    }

    private void evaluate(String input) {
        String[] tokens = input.split("");//= Integer.parseInt(input);

        char[] values = input.toCharArray();

        Stack<Character> operator = new Stack<>();
        Stack<Integer> operand = new Stack<>();

        for (int i=0;i<tokens.length;i++){

            if (values[i]=='(')
            {
                operator.add('(');
            }

            else if (values[i] >= '0' && values[i] <= '9') {
             StringBuffer sb = new StringBuffer();

             while (i<tokens.length && values[i]>= '0' && values[i] <= '9')
                 sb.append(values[i++]);

                 operand.add(Integer.parseInt(sb.toString()));
                 i--;
            }

            else if (values[i] == '+' || values[i] == '-' ||values[i] == '*' ||values[i] == '/' || values[i] == '^')
            {
                if (!operator.empty() && precedence(values[i],operator.peek()) && operator.peek()!='^')
                    operand.add(performOperation(operator.pop(),operand.pop(),operand.pop()));
                operator.add(values[i]);
            }

            else if (values[i] == ')')
            {
                while (operator.peek()!='(')
                    operand.add(performOperation(operator.pop(),operand.pop(),operand.pop()));
                operator.pop();}
        }


        while (!operator.empty())
            operand.add(performOperation(operator.pop(),operand.pop(),operand.pop()));

        System.out.println(operand.peek());


    }

    private boolean precedence(char value, Character peek) {
                if ((peek=='(') || (peek == ')'))
                    return false;
                if ((peek=='*'||peek == '/') && (value == '^')

                || (peek=='+'||peek == '-') && (value == '^')

                || (value=='*'||value == '/') && (peek == '+' || peek == '-'))

            return false;
        return true;
    }

    private int performOperation(Character operator, Integer op1, Integer op2) {
        switch (operator) {
            case '^':
                return (int) Math.pow((double) op2,(double) op1);
            case '+': {
                return op2 + op1;
            }
            case '-':{
                return op2 - op1;}
            case '*':
                return op2 * op1;
            case '/': {
                if (op2==0)
                    throw new
                        UnsupportedOperationException(
                        "Cannot divide by zero");;
                return op2 / op1;
            }


        }
        return 1;

    }
}
