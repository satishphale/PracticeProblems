package GoldbergsAlgorithm;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class GoldbergsAlgorithm {

    //Graph in the form of adjacency list
    public static List<List<Integer>> adjacent;
    static int V=0;

    private class Node
    {
        int node;
        int cost;

        Node()
        {

        }
        Node(int node,int cost)
        {
            this.node = node;
            this.cost = cost;
        }
    }

    //method to read the file
    private String readAsString(String s) throws IOException {
        String data = new String(Files.readAllBytes(Paths.get(s)));
        return data;
    }


    //method to find the densest subgraph
    private List<List<Integer>> findDensestSubgraph(List<List<Integer>> adjacent, int V) {
        int min_degree = 0;
        int max_degree = getEdges(adjacent,V);
        List<List<Integer>> subgraph = null;
        List<List<Integer>> source_segment;
        double difference = 1/((double)V*((double) V-1));

        while (max_degree - min_degree >= difference)
        {
            //applying binary search
            int least_density = (max_degree+min_degree)/2;
            source_segment = TryDensity(adjacent,least_density);

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

    //method to get subgraph
    private List<List<Integer>> TryDensity(List<List<Integer>> adjacent,int density) {
        List<List<Integer>> subgraph = null;
        int source = V;
        int sink=V+1;

        List<List<Node>> weighted_graph = getWeightedGraph(adjacent,density);
        stCut(weighted_graph,source,sink);




        return subgraph;
    }

    private void stCut(List<List<Node>> weighted_graph, int s, int t)
    {
        int u,v;

        List<List<Node>> r_graph = weighted_graph;

        int parent;


    }

    //method to Assign weights to unweighted graph
    List<List<Node>> getWeightedGraph(List<List<Integer>> adjacent,int density)
    {
        List<List<Node>> weighted_graph = new ArrayList<List<Node>>();

        for (int i=0;i<adjacent.size();i++)
        {
            List<Node> node = new ArrayList<Node>();

            List<Integer> temp = adjacent.get(i);
            for (int j = 0;j<temp.size();j++) {
                Node tmp_node = new Node();
                tmp_node.node = temp.get(j);
                tmp_node.cost = 1;
                node.add(tmp_node);
            }
            weighted_graph.add(node);
        }

        List<Node> source_adj = new ArrayList<>();
        for(int i=0;i<V;i++)
        {
            Node tmp=new Node();
            tmp.node=i;
            tmp.cost=V;
            source_adj.add(tmp);
        }
        weighted_graph.add(source_adj);

        List<Node> sink_adj = new ArrayList<>();
        for(int i=0;i<V;i++)
        {
            Node tmp=new Node();
            int degree_of_node = adjacent.get(i).size();

            tmp.node=i;
            tmp.cost=V+2*density-degree_of_node;
            sink_adj.add(tmp);
        }
        weighted_graph.add(sink_adj);


        return weighted_graph;
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
        if (adjacent==null)
            return 0;
        int vertices = adjacent.size();
        return vertices;
    }


    public static void main(String[] args) throws IOException {
        GoldbergsAlgorithm ga = new GoldbergsAlgorithm();

        String currentDirectory = System.getProperty("user.dir");
        String data = ga.readAsString(currentDirectory + "/goldberg_data.txt");
        String input[] = data.split("\n#\n");

        //calculating the number of vertices from the input
        for (int i1 = 0; i1 < input.length; i1++) {
            System.out.println("Test case: " + (i1 + 1));

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



}