package DungeonGame;

public class DungeonGame {

    public static void main(String[] args)
    {
        int arr[][] = {
                {-2,-3,3},
                {-5,-10,1},
                {10,30,-5}};
        int n = arr.length;
        int m = arr[0].length;

        int cost[][] = new int[n][m];
        cost[n-1][m-1] = Math.max(1,1-arr[n-1][m-1]);

        for(int i=m-2;i>=0;i--)
            cost[n-1][i] = Math.max(1,Math.max(1,cost[n-1][i+1]-arr[n-1][i]));

        for (int j=n-2;j>=0;j--)
            cost[j][m-1] = Math.max(1,Math.max(1,cost[j+1][m-1]-arr[j][m-1]));

        for (int i = m-2;i>=0;i--)
            for (int j=n-2;j>=0;j--)
                cost[i][j] = Math.max(1,Math.min(cost[i+1][j],cost[i][j+1])-arr[i][j]);

        System.out.println(cost[0][0]);

    }
}
