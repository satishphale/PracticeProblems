package ActivitySelection;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ActivitySelection {

    public static void main(String[] args)
    {
        System.out.println("Enter the n: ");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] start = new int[n];
        int[] finish = new int[n];
        System.out.println("Enter the start time: ");
        for (int i=0;i<n;i++)
            start[i] = sc.nextInt();

        System.out.println("Enter the end time");
        for(int i=0 ;i <n;i++) {
            finish[i] = sc.nextInt();
            //fin.put(finish[i],i);

        }
        System.out.println("Following activities are selected: ");
        int i=0;
        //System.out.println(i);
        int count =1;

        for (int j=1;j<n;j++)
            if (start[j]>finish[i])
            {
                count+=1;
                i=j;
            }
        System.out.println(count);
    }
}
