package SelectionSort;

public class SelectionSort {

    private void sort(int[] arr)
    {
        for (int i=0;i<arr.length-1;i++)
        {
            int min= arr[i];
            int index=0;
            for (int j = i+1; j<arr.length; j++)
            {
                if (min >= arr[j]) {
                    min = arr[j];
                    index = j;
                }
            }

            arr[index] = arr[i];
            arr[i] = min;


        }

        for (int i=0;i<arr.length;i++)
            System.out.println(arr[i]);
    }
    public static void main(String[] args)
    {

        SelectionSort ss = new SelectionSort();
        int[] arr = {7,5,3,9,2,1,4,6,8};

        ss.sort(arr);
    }
}
