package PalindromeByRecursion;

public class PalindromeByRecursion {

    static  int k = 0;
    boolean flag = true;
    private void palindrome(int[] arr)
    {
        if (arr.length/2!=k)
        {
            int x = arr[k];
            k++;
            palindrome(arr);
            if (arr.length%2==1&&k==arr.length/2) {
                k++;
               // return;
            }
            if (x==arr[k])
            {
                k++;
                return;
            }
            else if (x!=arr[k])
            {
                flag = false;
                return;
            }

        }

    }
    public static void main(String[] args)
    {
        PalindromeByRecursion pd = new PalindromeByRecursion();
        int[] arr = {1,2,3,4,4,3,2,1};

        pd.palindrome(arr);
        System.out.println(pd.flag);
    }
}
