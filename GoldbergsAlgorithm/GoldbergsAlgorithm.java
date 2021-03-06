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
            source_segment = TryDensity(adjacent,least_density,max_degree);

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
    private List<List<Integer>> TryDensity(List<List<Integer>> adjacent,int density,int no_edges) {
        List<List<Integer>> subgraph = null;
        int source = V;
        int sink=V+1;

        //This method assigns wights to the graph
        List<List<Node>> weighted_graph = getWeightedGraph(adjacent,density,no_edges);
        //method to find the st cut

        stCut(weighted_graph,source,sink);




        return subgraph;
    }

    private void stCut(List<List<Node>> weighted_graph, int s, int t)
    {
        int u,v;

        //creating the residual graph
        List<List<Node>> r_graph = weighted_graph;

        //parent array to path detection
        int[] parent = new int[r_graph.size()];

        //Augment the flow while there is path
        while (bfs(r_graph,s,t,parent))
        {
            //Finding the minimum residual capacity along the path
            int path_flow = Integer.MAX_VALUE;
            for (v=t;v!=s;v=parent[v])
            {
                u = parent[v];
                int index = r_graph.get(u).indexOf(v);
                path_flow = Math.min(path_flow,r_graph.get(u).get(index).cost);
            }

            //update the capacities and reverse the edge along the path
            for (v=t;v!=s;v=parent[v])
            {
                u = parent[v];
                int index = r_graph.get(u).indexOf(v);
                r_graph.get(u).get(index).cost -= path_flow;

                int index2 = r_graph.get(v).indexOf(u);
                r_graph.get(v).get(index2).cost += path_flow;
            }

        }
        //Now the flow is maximum and Using DFS to find the vertices that are reachable from S
        boolean[] is_visited = new boolean[weighted_graph.size()];

        dfs(r_graph,s,is_visited);

        // Print all edges that are from a reachable vertex to
        // non-reachable vertex in the original graph
        for (int i = 0; i < weighted_graph.size(); i++) {
                List<Node> tmp = weighted_graph.get(i);
            for (Node n: tmp) {
                if (n.cost > 0 && is_visited[i] && !is_visited[n.node]) {
                    System.out.println(i + " - " + n.node);
                }
            }

            }



        }

    private void dfs(List<List<Node>> r_graph, int s, boolean[] is_visited) {
        is_visited[s] = true;
        List<Node> lst = r_graph.get(s);
        for (Node n: lst)
            if (n.cost>0 && !is_visited[n.node])
                dfs(r_graph,n.node,is_visited);
    }

    //method to find the path
    private boolean bfs(List<List<Node>> r_graph, int s, int t, int[] parent)
    {
        boolean[] visited = new boolean[r_graph.size()];
        visited[s] = true;
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(s);
        parent[s] = -1;

        while (!queue.isEmpty())
        {
            int u = queue.poll();

            List<Node> tmp = r_graph.get(u);

            for (Node node:
                 tmp) {

                if (!visited[node.node])
                {
                    queue.add(node.node);
                    visited[node.node] = true;
                    parent[node.node] = u;
                }
            }

        }
    return (visited[t]==true);
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