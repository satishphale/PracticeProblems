package UnionFindByRank;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class UnionFindByRank {



    private String readAsString(String str) throws Exception
    {
        String data = new String(Files.readAllBytes(Paths.get(str)));
        return data;
    }
    public static void main(String args[]) throws Exception
    {
        UnionFindByRank ufr = new UnionFindByRank();
        String current_directory = System.getProperty("user.dir");
        //int[] parents = new int[];

        //reading the data from file
        String data = ufr.readAsString(current_directory+"/ufr_data.txt");
        //splitting the file data
        String[] test_cases = data.split("\n#\n");

        Set<Integer> V = new HashSet<>();
        //calculating the number of vertices in the graph
        //for each test case
        for (int i=0;i<test_cases.length;i++)
        {
            String[] edges = test_cases[i].split("\n");
            //for each edge
            for (int j=0;j<edges.length;j++)
            {
                String[] edge = edges[j].split(" ");
                V.add(Integer.parseInt(edge[0]));
                V.add(Integer.parseInt(edge[1]));
            }
        }

        int[] parents = new int[V.size()];



    }

}
