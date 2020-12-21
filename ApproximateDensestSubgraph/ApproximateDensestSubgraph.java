package ApproximateDensestSubgraph;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ApproximateDensestSubgraph {

    private static class DenseNode
    {
        Set vertices;
        int edges;
        double density;

        DenseNode() { }
        DenseNode(Set vertices,int edges,double density)
        {
            this.vertices=vertices;
            this.edges = edges;
            this.density = density;
        }

    }


    public static void main(String[] args) throws IOException {


        ApproximateDensestSubgraph asd = new ApproximateDensestSubgraph();
        String currentDirectory = System.getProperty("user.dir");
        String data = asd.readAsString(currentDirectory + "/asd_data.txt");
        String input[] = data.split("\n#\n");

        //calculating the number of vertices from the input
        for (int i1 = 0; i1 < input.length; i1++) {
            int V;// = 5;

            Set<Integer> vertices = new HashSet<>();
            HashMap<Integer,DenseNode> dn = new HashMap<>();

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


            List<List<Integer>> adjacent = new ArrayList<List<Integer>>();
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

            int no_edges = getEdges(adjacent, V) / 2;
            int no_vertices = getVertices(adjacent);
            double density = no_edges / (double) no_vertices;

            Set<Integer> first_vertices = new HashSet<>();
            Iterator<Integer> itr = vertices.iterator();
            while (itr.hasNext())
                first_vertices.add(itr.next());
            DenseNode first_dn = new DenseNode(first_vertices, no_edges, density);
            dn.put(V, first_dn);

            int i = no_vertices - 1;
            while (i != 2) {

                Set<Integer> new_vertices = new HashSet<>();
                String min_vertex = getMinimumDegreeVertex(adjacent, V);
                String[] pair = min_vertex.split("_");
                int vertex_to_remove = Integer.parseInt(pair[1]);

                adjacent = removeVertex(adjacent, vertex_to_remove);
                vertices.remove(vertex_to_remove);
                //new_vertices.
                Iterator<Integer> set_itr = vertices.iterator();
                while (set_itr.hasNext())
                    new_vertices.add(set_itr.next());
                V = new_vertices.size();
                no_edges = getEdges(adjacent, V) / 2;
                no_vertices = getVertices(adjacent);
                density = no_edges / (double) no_vertices;

                dn.put(i, new DenseNode(new_vertices, no_edges, density));
                i--;

            }

            DenseNode max_dense_node = new DenseNode();
            Iterator<Integer> itr1 = dn.keySet().iterator();

            max_dense_node.vertices = first_dn.vertices;
            max_dense_node.density = first_dn.density;
            max_dense_node.edges = first_dn.edges;

            while (itr1.hasNext()) {
                int x = itr1.next();
                DenseNode tmp_dn = dn.get(x);

                if (tmp_dn.density > first_dn.density) {
                    max_dense_node.vertices = tmp_dn.vertices;
                    max_dense_node.density = tmp_dn.density;
                    max_dense_node.edges = tmp_dn.edges;
                }


            }

            System.out.println("Vertices in the densest subgraph are: " + max_dense_node.vertices + " Size of the subgraph is: " + max_dense_node.edges + " Density of subgraph is: " + max_dense_node.density);


        }
            }


    private static List<List<Integer>> removeVertex(List<List<Integer>> adjacent, int vertex_to_remove) {
        List<List<Integer>> tmp = adjacent;
        //tmp.get(vertex_to_remove);
        tmp.remove(vertex_to_remove);
        //int size = adjacent.size();
        int i=0;
        while (i!= adjacent.size())
        {
            List<Integer> lst = adjacent.get(i);
            if (lst.contains(vertex_to_remove))
            {
                int index = tmp.get(i).indexOf(vertex_to_remove);
                tmp.get(i).remove(index);
        }
            i++;
        }


        return tmp;
    }

    private static int getVertices(List<List<Integer>> adjacent) {
        int vertices = adjacent.size();
        return vertices;
    }

    private static int getEdges(List<List<Integer>> adjacent, int v) {
        int i = 0;
        int size = 0;
        while (i != v) {
            size += adjacent.get(i).size();
            i++;
        }
        return size;
    }
    private static String getMinimumDegreeVertex(List<List<Integer>> adjacent,int V) {
        int i = 0;
        StringBuffer sb = null;// = new StringBuffer();
        while (i!=V)
        {
            int min_size=Integer.MAX_VALUE;
            int cur_size = adjacent.get(i).size();
            if (cur_size<min_size){
                sb = new StringBuffer();
                sb.append(cur_size);
                sb.append("_");
                sb.append(i);
            }

        i++;
        }

        return sb.toString();
    }

    private String readAsString(String s) throws IOException {
        String data = new String(Files.readAllBytes(Paths.get(s)));
        return data;
    }
}
