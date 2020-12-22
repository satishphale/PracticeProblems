package GoldbergsAlgorithm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class GoldbergsAlgorithm {

    //Graph in the form of adjacency list
    public static List<List<Integer>> adjacent;

    //method to read the file
    private String readAsString(String s) throws IOException {
        String data = new String(Files.readAllBytes(Paths.get(s)));
        return data;
    }


    public static void main(String[] args) throws IOException {
        GoldbergsAlgorithm ga = new GoldbergsAlgorithm();

        String currentDirectory = System.getProperty("user.dir");
        String data = ga.readAsString(currentDirectory + "/goldberg_data.txt");
        String input[] = data.split("\n#\n");

        //calculating the number of vertices from the input
        for (int i1 = 0; i1 < input.length; i1++) {
            System.out.println("Test case: " + (i1 + 1));
            int V;

            Set<Integer> vertices = new HashSet<>();

            String edges[] = input[i1].split("\n");
            //for each edge
            for (int j = 0; j < edges.length; j++) {
                String[] edge = edges[j].split(" ");
                int vertex1 = Integer.parseInt(edge[0]);
                int vertex2 = Integer.parseInt(edge[1]);

                vertices.add(vertex1);
                vertices.add(vertex2);
            }


            //number of vertices are the size of set
            V = vertices.size();


            adjacent = new ArrayList<List<Integer>>();
            for (int i = 0; i < V; i++) {
                List<Integer> node = new ArrayList<>();
                adjacent.add(node);
            }

            //String edges[] = input[i1].split("\n");
            //for each edge
            for (int j = 0; j < edges.length; j++) {
                String[] edge = edges[j].split(" ");
                int vertex1 = Integer.parseInt(edge[0]);
                int vertex2 = Integer.parseInt(edge[1]);
                adjacent.get(vertex1).add(vertex2);
                adjacent.get(vertex2).add(vertex1);
            }

            List<List<Integer>> subgraph = ga.findDensestSubgraph(adjacent,V);
            int no_vertices = ga.getVertices(subgraph);
            int no_edges = ga.getEdges(subgraph,no_vertices);
            double density = (double) no_edges/(double) no_vertices;

            System.out.println("\nThe size of the densest subgraph is: "+ no_edges+"\n Density of subgraph is: "+density+"\n");
        }
    }

    private List<List<Integer>> findDensestSubgraph(List<List<Integer>> adjacent, int V) {
        int min_degree = 0;
        int max_degree = getEdges(adjacent,V);
        List<List<Integer>> subgraph = null;
        List<List<Integer>> source_segment;
        double difference = 1/((double)V*((double) V-1));

        while (max_degree - min_degree >= difference)
        {
            int least_density = (max_degree+min_degree)/2;
            source_segment = makeGraph(adjacent);

            if (source_segment == null)
                max_degree = least_density;
            else
            {
                min_degree = least_density;
                subgraph = source_segment;
            }


        }

    return subgraph;
    }


    private List<List<Integer>> makeGraph(List<List<Integer>> adjacent) {
        List<List<Integer>> subgraph = null;

        return subgraph;
    }

    //method to count the number of edges in the graph
    private int getEdges(List<List<Integer>> adjacent, int v) {
        int i = 0;
        int size = 0;
        while (i != v) {
            size += adjacent.get(i).size();
            i++;
        }
        return size/2;
    }

    //Method to count the number of vertices in the graph
    private int getVertices(List<List<Integer>> adjacent) {
        int vertices = adjacent.size();
        return vertices;
    }

}