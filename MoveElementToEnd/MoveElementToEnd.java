package MoveElementToEnd;

import java.awt.*;

public class MoveElementToEnd {

    private void move(int[] arr,int num)
    {
        int i =0;
        int j = arr.length-1;

        while (i<j)
        {
            if (arr[i] == 2 && arr[j] != 2)
            {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }

            if (arr[i] != 2)
            i++;

            if (arr[j] == 2)
            j--;

        }

        for (int i1 = 0;i1<arr.length;i1++)
            System.out.println(arr[i1]);

    }
    public static void main(String[] args)
    {
        MoveElementToEnd mete = new MoveElementToEnd();
        int[] arr = {2,1,2,2,2,3,4,2};
        int num = 2;

        mete.move(arr,num);
    }
}
