package DijkstraAlgorithm;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Dijstras {

     static class Node implements Comparator<Node> {
        int node;
        int cost;

        Node()
        {

        }
        Node(int node ,int cost){
            this.node = node;
            this.cost = cost;
        }


        @Override
        public int compare(Node node, Node t1) {
            if (node.cost<t1.cost)
                return -1;
            if (node.cost>t1.cost)
                return 1;
            return 0;
        }
    }


    private int dist[];
    private Set<Integer> visited;
    private PriorityQueue<Node> pq;
    private int V; // Number of vertices
    List<List<Node> > adj;


    Dijstras(int V)
    {
        this.V = V;
        visited = new HashSet<>();
        pq = new PriorityQueue<Node>(V,new Node());
        dist = new int[V];
    }


    public static void main(String args[]) throws Exception
    {
        int V;// = 5;

        Set<Integer> vertices = new HashSet<>();

        String currentDirectory = System.getProperty("user.dir");
        String data = readAsString(currentDirectory+"/dijstras_data.txt");

        String input[] = data.split("\n#\n");

        //calculating the number of vertices from the input
        for (int i1=0;i1< input.length;i1++)
        {
            String edges[] = input[i1].split("\n");
            //for each edge
            for (int j=0;j<edges.length;j++) {
                String[] edge = edges[j].split(" ");
                int vertex1 = Integer.parseInt(edge[0]);
                int vertex2 = Integer.parseInt(edge[1]);

                vertices.add(vertex1);
                vertices.add(vertex2);
            }}

        //number of vertices are the size of set
        V = vertices.size();


        //for each input case
        for (int i1=0;i1< input.length;i1++)
        {
            Dijstras dj = new Dijstras(V);
            List<List<Node>> adjacent = new ArrayList<List<Node>>();
            for (int i=0;i< V;i++)
            {
                List<Node> node = new ArrayList<>();
                adjacent.add(node);
            }

            int start = 0;

            String edges[] = input[i1].split("\n");
            //for each edge
            for (int j=0;j<edges.length;j++)
            {
                String[] edge = edges[j].split(" ");
                int vertex1 = Integer.parseInt(edge[0]);
                int vertex2 = Integer.parseInt(edge[1]);
                int cost = Integer.parseInt(edge[2]);

                adjacent.get(vertex1).add(new Node(vertex2,cost));
            }

            dj.dijstras(adjacent,start);

            System.out.println("the minimum distance from node ");
            for (int i = 0;i<dj.dist.length;i++)
            {
                System.out.println(start+" ----> "+i+" is "+dj.dist[i]);
            }

        }

    }

    private void dijstras(List<List<Node>> adjacent, int start) {
        this.adj = adjacent;

        for (int i=0;i<V;i++)
            dist[i] = Integer.MAX_VALUE;

        pq.add(new Node(start,0));
        //visited.add(start);

        dist[start] = 0;

        while (visited.size() != V)
        {
            int u = pq.remove().node;

            visited.add(u);

            evaluateNeighbours(u);

        }


    }

    void evaluateNeighbours(int u) {

        for (int i=0;i<adj.get(u).size();i++)
        {
            Node v = adj.get(u).get(i);
            int new_cost = -1;

            if (!visited.contains(v.node))
            {
                int edge_cost= v.cost;
                new_cost = edge_cost+dist[u];

                if (new_cost < dist[v.node])
                {
                    dist[v.node] = new_cost;
                }

                pq.add(v);
            }

        }
    }

    private static String readAsString(String s) throws Exception {
        String data = new String(Files.readAllBytes(Paths.get(s)));
        System.out.println();
        return  data;
    }
}
