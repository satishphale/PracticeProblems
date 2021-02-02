package PairDifference;

import java.util.Arrays;
import java.util.Scanner;
class PairDifference
{


private void printPair(int[] num,int d)
{
	Arrays.sort(num);
	int i=0;
	int j=1;
	int count = 0;
	while(j<num.length)
	{
		if(num[j]-num[i]<=d)
		{	
			count++;
			System.out.println(num[i]+" "+num[j]);
			i=j+1;
			j+=2;
		}	
		else
		{
			i+=1;
			j+=1;		
		}
	}
	System.out.println("  "+count);
	int[] t = new int[10];
	int[][] temp = new int[10][10];
	for (int k=0;k<temp.length;k++)
	{
		t[k] = temp[k][0];
	}


}

public static void main(String[] args)
{
	PairDifference pd = new PairDifference();
	Scanner sc = new Scanner(System.in);
	
	System.out.println("Enter the string....");
	String str = sc.nextLine();
	
	System.out.println("Enter the difference");
	int d = sc.nextInt();

	String[] str_arr = str.split(" ");
	int num[] = new int[str_arr.length];
	for(int i=0;i<num.length;i++)
		num[i] = Integer.parseInt(str_arr[i]);	
	
	pd.printPair(num,d);



}
}
