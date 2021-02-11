package LongestPathDP;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LongestPathDP {

    private int getDist(int i,int j,char[][] mat, int[][] dp)
    {
        int rows = mat.length;
        int cols = mat[0].length;
        int cost = 0;
        int x=Integer.MIN_VALUE,y=Integer.MIN_VALUE,z=Integer.MIN_VALUE,a=Integer.MIN_VALUE,b=Integer.MIN_VALUE,c=Integer.MIN_VALUE,d=Integer.MIN_VALUE,e=Integer.MIN_VALUE;

        if (i < 0 || i >= rows || j < 0 || j >= cols)
            return 0;

        if (dp[i][j] != -1)
            return dp[i][j];
        if (i<rows-1 && (int)mat[i][j]+1 == (int)mat[i+1][j])
            x = dp[i][j] = 1+ getDist(i+1,j,mat,dp);

        if (j<cols-1 && (int)mat[i][j]+1 == (int)mat[i][j+1])
           y = dp[i][j] = 1+ getDist(i,j+1,mat,dp);

        if (j>0 && (int)mat[i][j]+1 == (int)mat[i][j-1])
            z = dp[i][j] = 1+ getDist(i,j-1,mat,dp);

        if (i>0 && (int)mat[i][j]+1 == (int)mat[i-1][j])
            a = dp[i][j] = 1+ getDist(i-1,j,mat,dp);

        if (i<rows-1 && j<cols-1 && (int)mat[i][j]+1 == (int)mat[i+1][j+1])
            b = dp[i][j] = 1+ getDist(i+1,j+1,mat,dp);

        if (i>0 && j<cols-1 && (int)mat[i][j]+1 == (int)mat[i-1][j+1])
            c = dp[i][j] = 1+ getDist(i-1,j+1,mat,dp);

        if (i<rows-1 && j>0 && (int)mat[i][j]+1 == (int)mat[i+1][j-1])
            d = dp[i][j] = 1+ getDist(i+1,j-1,mat,dp);

        if (i>0 && j>0 && (int)mat[i][j]+1 == (int)mat[i-1][j-1])
            e = dp[i][j] = 1+ getDist(i-1,j-1,mat,dp);

        cost = dp[i][j] = Math.max(x,Math.max(y,Math.max(z,Math.max(a,Math.max(b,Math.max(d,Math.max(e,1)))))));
        return cost;
    }

    private void longestPath(char[][] mat)
    {
        int rows = mat.length;
        int cols = mat[0].length;
        int[][] dp = new int[rows][cols];

        for (int i =0 ;i<rows;i++)
            for (int j = 0;j<cols;j++)
            {
                dp[i][j] = -1;
            }

        int cost=0;
        for (int i =0 ;i<rows;i++)
            for (int j = 0;j<cols;j++)
            {
               int temp_cost = getDist(i,j,mat,dp);
                if (temp_cost>cost)
                    cost = temp_cost;
            }

        System.out.println("Longest string length is: "+cost);

    }


    //method to read the file
    private String readAsString(String s) throws IOException {
        String data = new String(Files.readAllBytes(Paths.get(s)));
        return data;
    }

    public static void main(String[] args) throws Exception
    {
        LongestPathDP lp = new LongestPathDP();
/*        char [][] mat =
                {
                        {'A','B','H'},
                        {'F','E','C'},
                        {'G','D','K'},
                };*/

        char[][] mat = new char[4][4];
        String currentDirectory = System.getProperty("user.dir");
        String data = lp.readAsString(currentDirectory + "/lpd_data.txt");
        String input[] = data.split("\n#\n");

        //for each test case
        for (int t=0;t<input.length;t++)
        {
            String[] rows = input[t].split("\n");

            //for each rows
            for (int e=0;e<rows.length;e++)
            {
                //for each columns
                char[] col = rows[e].toCharArray();
                for (int c=0;c< col.length;c++)
                {
                    mat[e][c] = col[c];
                }
            }
            for (int i = 0; i < mat.length; i++) {
                for (int j = 0; j < mat[0].length; j++) {
                    System.out.print(" " + mat[i][j]);
                }
                System.out.print("\n");
            }

            lp.longestPath(mat);
        }

    }
}
