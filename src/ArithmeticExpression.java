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

        String data = ae.readAsString(currentDirectory+"/data_paths.txt");

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

        Stack<String> operator = new Stack<>();
        Stack<Integer> operand = new Stack<>();

        for (int i=0;i<tokens.length;i++){

            if (tokens[i].equals("("))
            {
                operator.add("(");
            }
            System.out.println();



        }

    }
}
