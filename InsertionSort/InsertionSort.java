package InsertionSort;

public class InsertionSort {

    private void sort(int[] arr)
    {
        for (int i=1;i<arr.length;i++)
        {
            for (int j=0;j<i;j++)
            {
                if (arr[i]<arr[j])
                {
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        for (int i=0;i<arr.length;i++)
            System.out.println(arr[i]);
    }
    public  static void main(String[] args)
    {
        InsertionSort is = new InsertionSort();
        int[] arr = {7,5,3,9,2,1,4,6,8};

        is.sort(arr);
    }
}
