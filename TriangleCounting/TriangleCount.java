package TriangleCounting;// Java program to find number
// of triangles in an Undirected 
// Graph. The program is for 
// adjacency matrix representation 
// of the graph 
import ApproximateDensestSubgraph.ApproximateDensestSubgraph;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

class TriangleCount
{ 
	// Number of vertices in 
	// the graph 
	static int V;

// Utility function for 
// matrix multiplication 
void multiply(int A[][], int B[][], 
							int C[][]) 
{ 
	for (int i = 0; i < V; i++) 
	{ 
		for (int j = 0; j < V; j++) 
		{ 
			C[i][j] = 0; 
			for (int k = 0; k < V; 
								k++) 
			{ 
				C[i][j] += A[i][k]* 
							B[k][j]; 
			} 
		} 
	} 
} 

// Utility function to calculate 
// trace of a matrix (sum of 
// diagnonal elements) 
int getTrace(int graph[][]) 
{ 
	int trace = 0; 

	for (int i = 0; i < V; i++) 
	{ 
		trace += graph[i][i]; 
	} 
	return trace; 
} 

// Utility function for 
// calculating number of 
// triangles in graph 
int triangleInGraph(int graph[][]) 
{ 
	// To Store graph^2 
	int[][] aux2 = new int[V][V]; 

	// To Store graph^3 
	int[][] aux3 = new int[V][V]; 

	// Initialising aux matrices 
	// with 0 
	for (int i = 0; i < V; ++i) 
	{ 
		for (int j = 0; j < V; ++j) 
		{ 
			aux2[i][j] = aux3[i][j] = 0; 
		} 
	} 

	// aux2 is graph^2 now 
	// printMatrix(aux2) 
	multiply(graph, graph, aux2); 

	// after this multiplication aux3 
	// is graph^3 printMatrix(aux3) 
	multiply(graph, aux2, aux3); 

	int trace = getTrace(aux3); 

	return trace / 6; 
}

	//method to read the file
	private String readAsString(String s) throws IOException {
		String data = new String(Files.readAllBytes(Paths.get(s)));
		return data;
	}

// Driver code 
public static void main(String args[]) throws IOException {
	TriangleCount obj = new TriangleCount();


	String currentDirectory = System.getProperty("user.dir");
	String data = obj.readAsString(currentDirectory + "/asd_data.txt");
	String input[] = data.split("\n#\n");

	BufferedWriter outputWriter = new BufferedWriter(new FileWriter(currentDirectory+"/output.txt"));
	//calculating the number of vertices from the input
	for (int i1 = 0; i1 < input.length; i1++) {
		outputWriter.write("\nTest case: " + (i1 + 1)+"\n\n");


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

		int graph[][] =new int[V][V];

		for (int i = 0; i < V; ++i)
		{
			for (int j = 0; j < V; ++j)
			{
				graph[i][j] = 0;
			}
		}

		//String edges[] = input[i1].split("\n");
		//for each edge
		int no_triangles = 0;

		for (int j = 0; j < edges.length; j++) {
			String[] edge = edges[j].split(" ");

			int vertex1 = Integer.parseInt(edge[0]);
			int vertex2 = Integer.parseInt(edge[1]);

			// To Store graph^3
			int[][] aux = new int[V][V];



			graph[vertex1][vertex2] = 1;
			graph[vertex2][vertex1] = 1;

			// Initialising aux matrices
			// with 0
			for (int i12 = 0; i12 < V; ++i12)
			{
				for (int j1 = 0; j1 < V; ++j1)
				{
					aux[i12][j1] = 0;
				}
			}

			obj.multiply(graph,graph,aux);

			int cur_triangle =obj.triangleInGraph(graph);

			outputWriter.write("\n\nTotal number of Triangle in Graph : " +
					cur_triangle+" after adding the edge:"+"<"+vertex1+","+vertex2+">");


			outputWriter.write("\nTriangles on edge: "+"<"+vertex1+","+vertex2+"> are: "+(cur_triangle-no_triangles));

			no_triangles = cur_triangle;

			int sum=0;
			for (int i=0;i<V;i++)
				for (int v=0;v<V;v++)
				{
					if (i<v)
					sum +=aux[i][v];
				}

			int closed = sum - 3*cur_triangle;
			double gcc=0.0;
				if ((cur_triangle+sum)!=0)
					gcc= (double) cur_triangle/ ((double)cur_triangle+(double)closed);
			outputWriter.write("\nGlobal clustering coefficient: "+gcc);



		}
		outputWriter.write("\n***********************************************************************");

	}
	outputWriter.flush();
	outputWriter.close();
} 
}

// This code is contributed by Anshika Goyal. 
