package UnionFindByRank;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class OptimizedDisjointSet {
    static class Edge{
        int source;
        int destination;

        public Edge(int source, int destination) {
            this.source = source;
            this.destination = destination;
        }
    }

    static class SubSet {
        int parent;
        int rank;
    }

    static class Graph{
        int vertices;
        LinkedList<Edge>[] adjList;
        ArrayList<Edge> allEdges = new ArrayList<>();

        Graph(int vertices){
            this.vertices = vertices;
            adjList = new LinkedList[vertices];
            for (int i = 0; i <vertices ; i++) {
                adjList[i] = new LinkedList<>();
            }
        }
        public void addEgde(int source, int destination){
            Edge edge = new Edge(source, destination);
            adjList[source].addFirst(edge);
            allEdges.add(edge); //add to total edges
        }
       //(Path Compression)
        public int find(SubSet [] subSet, int vertex){
            if(subSet[vertex].parent!=vertex)
                subSet[vertex].parent = find(subSet, subSet[vertex].parent);;
            return subSet[vertex].parent;
        }
        //(Union by rank)
        public void union(SubSet [] subSet, int x, int y){
            int x_set_parent = find(subSet, x);
            int y_set_parent = find(subSet, y);

            //attach the smaller rank tree to the higher rank tree
            if(subSet[x_set_parent].rank>subSet[y_set_parent].rank){
                //make x as parent of y
                subSet[y_set_parent].parent = x_set_parent;
            }else if(subSet[x_set_parent].rank<subSet[y_set_parent].rank){
                //make y as parent of x
                subSet[x_set_parent].parent = y_set_parent;
            }else{
                // make any tree child of other tree
                subSet[y_set_parent].parent = x_set_parent;
                //now increase the rank of x for the next time
                subSet[x_set_parent].rank++;
            }
        }
        public void makeSet(SubSet[] subSets) {
            //make set
            for (int i = 0; i < vertices; i++) {
                subSets[i] = new SubSet();
                subSets[i].parent = i;
                subSets[i].rank = 0;
            }
        }
        public void disjointSets(){
            //create a subsets []
            SubSet[] subSets = new SubSet[vertices];

//            //makeset
            makeSet(subSets);

            //iterate through all the edges and keep making the sets
            for (int i = 0; i <allEdges.size() ; i++) {
                Edge edge = allEdges.get(i);
                int x_set = find(subSets, edge.source);
                int y_set = find(subSets, edge.destination);

                //check if source vertex and destination vertex belongs to the same set
                // if in same set then cycle has been detected else combine them into one set
                if(x_set==y_set) {
                    //do nothing
                }else
                    union(subSets, x_set, y_set);
            }
            printSets(subSets);
        }

        public void printSets(SubSet[] subSets){
            //Find different Sets
            HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
            for (int i = 0; i <subSets.length ; i++) {
                if(map.containsKey(subSets[i].parent)){
                    ArrayList<Integer> list = map.get(subSets[i].parent);
                    list.add(i);//add vertex
                    map.put(subSets[i].parent,list);
                }else{
                    ArrayList<Integer> list = new ArrayList<>();
                    list.add(i);
                    map.put(subSets[i].parent,list);
                }
            }
            //Print the Different sets
            Set<Integer> set = map.keySet();
            Iterator<Integer> iterator = set.iterator();
            while(iterator.hasNext()){
                int key = iterator.next();
                System.out.println("Set Id: " + key + " elements: " + map.get(key));
            }
        }
    }

    private String readAsString(String str) throws Exception
    {
        String data = new String(Files.readAllBytes(Paths.get(str)));
        return data;
    }
    public static void main(String[] args) throws Exception {
////        int vertices1 = 6;
////        Graph graph = new Graph(vertices);
////        graph.addEgde(0, 1);
////        graph.addEgde(0, 2);
////        graph.addEgde(1, 3);
////        graph.addEgde(4, 5);
////        System.out.println("Disjoint Sets: ");
//        graph.disjointSets();

        int vertices;
        OptimizedDisjointSet ods = new OptimizedDisjointSet();

        String currentDirectory = System.getProperty("user.dir");//int[] parents = new int[];

        //reading the data from file ods_data
        String data = ods.readAsString(currentDirectory + "/ods_data.txt");
        //splitting the file data
        String[] test_cases = data.split("\n#\n");

        Set<Integer> V = new HashSet<>();
        //calculating the number of vertices in the graph
        //for each test case
        for (int i = 0; i < test_cases.length; i++)
        {
            String[] edges = test_cases[i].split("\n");
            //for each edge
            for (int j = 0; j < edges.length; j++) {
                String[] edge = edges[j].split(" ");
                V.add(Integer.parseInt(edge[0]));
                V.add(Integer.parseInt(edge[1]));
            }

            vertices = V.size();
            Graph graph = new Graph(vertices);

            //for each edge
            for (int j = 0; j < edges.length; j++) {
                String[] edge = edges[j].split(" ");
                graph.addEgde(Integer.parseInt(edge[0]), Integer.parseInt(edge[1]));
            }
            System.out.println("Disjoint Sets: ");
            graph.disjointSets();

        }


    }

}
