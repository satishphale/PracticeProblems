package IncrementalConnectivity;// Java implementation of
// incremental connectivity 
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

class IncrementalConnectivity
{

	static BufferedWriter outputWriter; //

	// Finding the root of node i
static int root(int arr[], int i) 
{ 
	while (arr[i] != i) 
	{ 
		arr[i] = arr[arr[i]]; 
		i = arr[i]; 
	} 
	return i; 
} 

// union of two nodes a and b 
static void weighted_union(int arr[], int rank[], 
						int a, int b) 
{ 
	int root_a = root (arr, a); 
	int root_b = root (arr, b); 

	// union based on rank 
	if (rank[root_a] < rank[root_b]) 
	{ 
		arr[root_a] = arr[root_b]; 
		rank[root_b] += rank[root_a]; 
	} 
	else
	{ 
		arr[root_b] = arr[root_a]; 
		rank[root_a] += rank[root_b]; 
	} 
} 

// Returns true if two nodes have same root 
static boolean areSame(int arr[], 
					int a, int b) 
{ 
	return (root(arr, a) == root(arr, b)); 
} 

// Performing an operation 
// according to query type 
static void query(String type, int x, int y,
				int arr[], int rank[]) throws IOException {
	// type 1 query means checking if 
	// node x and y are connected or not 
	if (type.equals("Q:"))
	{ 
		// If roots of x and y is same then yes 
		// is the answer 
		if (areSame(arr, x, y) == true)
			outputWriter.write("Q: "+x+","+y+": "+"YES\n");
		else
			outputWriter.write("Q: "+x+","+y+": "+"NO\n");
	} 

	// type 2 query refers union of x and y 
	else if (type.equals("I:"))
	{ 
		// If x and y have different roots then 
		// union them 
		if (areSame(arr, x, y) == false) 
			weighted_union(arr, rank, x, y); 
	} 
}

	private String readAsString(String str) throws Exception
	{
		String data = new String(Files.readAllBytes(Paths.get(str)));
		return data;
	}
// Driver Code 
public static void main(String[] args) throws Exception
{ 
	// No.of nodes 
	int n = 7;
	IncrementalConnectivity inc = new IncrementalConnectivity();
	String currentDirectory = System.getProperty("user.dir");//int[] parents = new int[];
	outputWriter = new BufferedWriter(new FileWriter(currentDirectory+"/output.txt"));

	// The following two arrays are used to 
	// implement disjoint set data structure. 
	// arr[] holds the parent nodes while rank 
	// array holds the rank of subset 
	int []arr = new int[n]; 
	int []rank = new int[n];

	// initializing both array and rank 
	for (int i = 0; i < n; i++) 
	{ 
		arr[i] = i; 
		rank[i] = 1; 
	} 

	//reading the data from file ods_data
	String data = inc.readAsString(currentDirectory + "/inc_data.txt");

	//splitting the file data
	String[] test_cases = data.split("\n");

	for (int i=0;i<test_cases.length;i++)
	{
		String[] query = test_cases[i].split(" ");
		String[] vertices = query[1].split(",");
		query(query[0], Integer.parseInt(vertices[0]), Integer.parseInt(vertices[1]), arr, rank);
	}

	outputWriter.flush();
	outputWriter.close();

} 
} 

// This code is contributed by Rajput-Ji 
