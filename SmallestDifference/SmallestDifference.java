package SmallestDifference;

import java.util.Arrays;

public class SmallestDifference {

    private void difference(int[] arr1,int[] arr2)
    {
        Arrays.sort(arr1);
        Arrays.sort(arr2);
        int diff = Integer.MAX_VALUE;
        int a = 0;
        int b = 0;
        int i=0;
        int j = 0;

        while (i!=arr1.length && j != arr2.length)
        {
            int temp_diff = Math.abs(arr2[j]-arr1[i]);
            if (arr1[i]<arr2[j])
            {
                if (temp_diff < diff)
                {
                    diff = temp_diff;
                    a = arr1[i];
                    b = arr2[j];
                }
                i++;
            }
            else
            {
                if (temp_diff < diff)
                {
                    diff = temp_diff;
                    a = arr1[i];
                    b = arr2[j];
                }
                j++;
            }
        }

        System.out.println("A: "+a+" B:"+b+" "+" diff: "+diff);

    }
    public static void main(String[] args)
    {
        SmallestDifference sd = new SmallestDifference();
        int[] arr1 = {-1,3,5,10,20,28};
        int[] arr2 ={15,47,26,134,135};
        int num = 2;
        sd.difference(arr1,arr2);

    }
}
