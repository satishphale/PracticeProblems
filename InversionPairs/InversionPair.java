package InversionPairs;

import java.nio.file.Files;
import java.nio.file.Paths;

public class InversionPair {

    static int count;

    private void merge(int[] arr, int l, int m, int r) {
        //variables to store the sizes of two temp arrays
        int l1 = m - l + 1;
        int l2 = r - m;

        //creating the two temp arrays
        int[] L = new int[l1];
        int[] R = new int[l2];

        //copying the values to the arrays
        for (int i = 0; i < l1; i++)
            L[i] = arr[l + i];
        for (int i = 0; i < l2; i++)
            R[i] = arr[m + 1 + i];

        //creating the two pointers
        int i = 0;
        int j = 0;
        int k = l;
        while (i < l1 && j < l2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                count = count + (l1 - i);
                j++;
            }
            k++;
        }

        //copy the remaining values
        while (i < l1) {
            arr[k] = L[i];
            i++;
            k++;
        }
        while (j < l2) {
            arr[k] = R[j];
            j++;
            k++;
        }

    }

    private void sort(int[] arr, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;
            sort(arr, l, m);
            sort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }

    private String readAsString(String input) throws Exception {
        String data = new String(Files.readAllBytes(Paths.get(input)));
        return data;
    }

    public static void main(String[] args) throws Exception {
        InversionPair ip = new InversionPair();
        String current_directory = System.getProperty("user.dir");
        String data = ip.readAsString(current_directory + "/ip_data.txt");
        String[] input_data = data.split("\n#\n");

        //for each test case
        for (int t = 0; t < input_data.length; t++)
        {
            System.out.println("Test case: "+ (t+1));
            String[] input = input_data[t].split(",");
            int[] arr = new int[input.length];
            for (int i = 0; i < input.length; i++)
                arr[i] = Integer.parseInt(input[i]);

            ip.sort(arr, 0, arr.length - 1);

            System.out.println("The Number of inversion pairs are: " + count);
            count= 0;


        }

    }
}