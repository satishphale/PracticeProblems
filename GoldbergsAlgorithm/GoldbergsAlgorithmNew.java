package GoldbergsAlgorithm;
import org.jgrapht.Graph;
import org.jgrapht.alg.densesubgraph.*;
import org.jgrapht.graph.DefaultEdge;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class GoldbergsAlgorithmNew {


    //Graph in the form of adjacency list
    public static List<List<Integer>> adjacent;
    static int V=5;
    static int E =7;

    static Graph<Integer, DefaultEdge> graph;


    private static class Node
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
            source_segment = TryDensity(adjacent,least_density,max_degree);

            if (source_segment == null)
                max_degree = least_density;
            else
            {
                min_degree = least_density;
                adjacent = source_segment;
            }


        }

        return adjacent;
    }

    //method to get subgraph
    private List<List<Integer>> TryDensity(List<List<Integer>> adjacent,int density,int no_edges) {
        List<List<Integer>> subgraph = null;
        int source = V;
        int sink=V+1;

        //This method assigns wights to the graph
        List<List<Node>> weighted_graph = getWeightedGraph(adjacent,density,no_edges);
        //method to find the st cut


        int graph[][] = new int[V+2][V+2];

        for (int i =0;i<V+2;i++) {
            List<Node> cur_node = weighted_graph.get(i);
            for (int j = 0; j < cur_node.size(); j++)
            {
                Node node = cur_node.get(j);
                graph[i][node.node] = node.cost;
        }
        }

        //stCut(weighted_graph,source,sink);
        //subgraph =
                minCut(graph, source, sink);




        return subgraph;
    }

    // Returns true if there is a path
    // from source 's' to sink 't' in residual
    // graph. Also fills parent[] to store the path
    private static boolean bfs(int[][] rGraph, int s,
                               int t, int[] parent) {

        // Create a visited array and mark
        // all vertices as not visited
        boolean[] visited = new boolean[rGraph.length];

        // Create a queue, enqueue source vertex
        // and mark source vertex as visited
        Queue<Integer> q = new LinkedList<Integer>();
        q.add(s);
        visited[s] = true;
        parent[s] = -1;

        // Standard BFS Loop
        while (!q.isEmpty()) {
            int v = q.poll();
            for (int i = 0; i < rGraph.length; i++) {
                if (rGraph[v][i] > 0 && !visited[i]) {
                    q.offer(i);
                    visited[i] = true;
                    parent[i] = v;
                }
            }
        }

        // If we reached sink in BFS starting
        // from source, then return true, else false
        return (visited[t] == true);
    }

    // A DFS based function to find all reachable
    // vertices from s. The function marks visited[i]
    // as true if i is reachable from s. The initial
    // values in visited[] must be false. We can also
    // use BFS to find reachable vertices
    private static void dfs(int[][] rGraph, int s,
                            boolean[] visited) {
        visited[s] = true;
        for (int i = 0; i < rGraph.length; i++) {
            if (rGraph[s][i] > 0 && !visited[i]) {
                dfs(rGraph, i, visited);
            }
        }
    }

    // Prints the minimum s-t cut
    private static List<List<Integer>> minCut2(int[][] graph, int s, int t) {
        int u, v;

        // Create a residual graph and fill the residual
        // graph with given capacities in the original
        // graph as residual capacities in residual graph
        // rGraph[i][j] indicates residual capacity of edge i-j
        int[][] rGraph = new int[graph.length][graph.length];
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                rGraph[i][j] = graph[i][j];
            }
        }

        // This array is filled by BFS and to store path
        int[] parent = new int[graph.length];

        // Augment the flow while tere is path from source to sink
        while (bfs(rGraph, s, t, parent)) {

            // Find minimum residual capacity of the edhes
            // along the path filled by BFS. Or we can say
            // find the maximum flow through the path found.
            int pathFlow = Integer.MAX_VALUE;
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                pathFlow = Math.min(pathFlow, rGraph[u][v]);
            }

            // update residual capacities of the edges and
            // reverse edges along the path
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                rGraph[u][v] = rGraph[u][v] - pathFlow;
                rGraph[v][u] = rGraph[v][u] + pathFlow;
            }
        }

        // Flow is maximum now, find vertices reachable from s
        boolean[] isVisited = new boolean[graph.length];
        dfs(rGraph, s, isVisited);


        List<List<Integer>> induced_graph = new ArrayList<List<Integer>>();
        // Print all edges that are from a reachable vertex to
        // non-reachable vertex in the original graph
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                if (graph[i][j] > 0 && isVisited[i] && !isVisited[j] && i != V && j != (V + 1)) {
                    System.out.println(i + " - " + j);
//                    if (induced_graph.get(i) == null && induced_graph.get(j) == null) {
                        List<Integer> tmp = new ArrayList<>();
                        tmp.add(i);
                        induced_graph.add(tmp);
                        List<Integer> tmp2 = new ArrayList<>();
                        tmp2.add(j);
                        induced_graph.add(tmp2);
  //                  }
                }
            }

        }
        return induced_graph;
    }

    // Prints the minimum s-t cut
    private static void minCut(int[][] graph, int s, int t) {
        int u,v;

        // Create a residual graph and fill the residual
        // graph with given capacities in the original
        // graph as residual capacities in residual graph
        // rGraph[i][j] indicates residual capacity of edge i-j
        int[][] rGraph = new int[graph.length][graph.length];
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                rGraph[i][j] = graph[i][j];
            }
        }

        // This array is filled by BFS and to store path
        int[] parent = new int[graph.length];

        // Augment the flow while tere is path from source to sink
        while (bfs(rGraph, s, t, parent)) {

            // Find minimum residual capacity of the edhes
            // along the path filled by BFS. Or we can say
            // find the maximum flow through the path found.
            int pathFlow = Integer.MAX_VALUE;
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                pathFlow = Math.min(pathFlow, rGraph[u][v]);
            }

            // update residual capacities of the edges and
            // reverse edges along the path
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                rGraph[u][v] = rGraph[u][v] - pathFlow;
                rGraph[v][u] = rGraph[v][u] + pathFlow;
            }
        }

        // Flow is maximum now, find vertices reachable from s
        boolean[] isVisited = new boolean[graph.length];
        dfs(rGraph, s, isVisited);

        // Print all edges that are from a reachable vertex to
        // non-reachable vertex in the original graph
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                if (graph[i][j] > 0 && isVisited[i] && !isVisited[j]) {
                    System.out.println(i + " - " + j);
                }
            }
        }
    return;
    }


    //method to Assign weights to unweighted graph
    List<List<Node>> getWeightedGraph(List<List<Integer>> adjacent,int density,int no_of_edges)
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
            tmp.cost=no_of_edges;
            source_adj.add(tmp);
            weighted_graph.get(i).add(new Node(V, tmp.cost));
        }
        weighted_graph.add(source_adj);

        List<Node> sink_adj = new ArrayList<>();
        for(int i=0;i<V;i++)
        {
            Node tmp=new Node();
            int degree_of_node = adjacent.get(i).size();

            tmp.node=i;
            tmp.cost=no_of_edges+2*density-degree_of_node;
            sink_adj.add(tmp);
            weighted_graph.get(i).add(new Node(V+1, tmp.cost));
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
        GoldbergsAlgorithmNew ga = new GoldbergsAlgorithmNew();

        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addVertex(4);

        graph.addEdge(0,1);
        graph.addEdge(1,0);
        graph.addEdge(1,2);
        graph.addEdge(1,3);
        graph.addEdge(1,4);
        graph.addEdge(2,1);
        graph.addEdge(2,3);
        graph.addEdge(2,4);
        graph.addEdge(3,1);
        graph.addEdge(3,2);
        graph.addEdge(3,4);
        graph.addEdge(4,1);
        graph.addEdge(4,2);
        graph.addEdge(4,3);


        GoldbergMaximumDensitySubgraphAlgorithm sb = new GoldbergMaximumDensitySubgraphAlgorithm(graph,V,E,0.0);

        Graph sm = sb.calculateDensest();

        System.out.println(sm);

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