package FindThreeLargestNumbers;

public class FindThreeLargestNumbers {

    private void getLargest(int[] arr,int[] largest)
    {
        for (int i=0;i<arr.length;i++)
        {
            if (arr[i]>largest[2])
            {
                largest[0] = largest[1];
                largest[1] = largest[2];
                largest[2] = arr[i];
            }
            else if (arr[i]>largest[1])
            {
                largest[0] = largest[1];
                largest[1] = arr[i];
            }
            else if (arr[i]>largest[0])
            {
                largest[0] = arr[i];
            }
        }
        System.out.println(largest[0]+","+largest[1]+","+largest[2]);
    }
    public static void main(String[] args)
    {
        FindThreeLargestNumbers st = new FindThreeLargestNumbers();
        int arr[] ={141,1,17,-7,-17,-27,18,541,8,7,7};
        int largest[] = new int[3];

        st.getLargest(arr,largest);

    }
}
