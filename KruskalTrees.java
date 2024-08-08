/*


    Author: Shahida

*/

// Kruskal's Minimum Spanning Tree Algorithm
// Union-find implemented using disjoint set trees without compression

import java.io.*; 
import java.util.Scanner;   
 
class Edge {
    public int u, v, wgt;

    public Edge() {
        u = 0;
        v = 0;
        wgt = 0;
    }

    //constructor that reads in u, v and wgt from wGraph1.txt
    public Edge(int x, int y, int w) { 
        this.u = x;
        this.v = y;
        this.wgt = w;
    }
    
   
    public void show() {
        System.out.print("Edge " + toChar(u) + "--" + wgt + "--" + toChar(v) + "\n") ;
    }
    
    // convert vertex into char for pretty printing
    private char toChar(int u)
    {  
        return (char)(u + 64);
    }


}

/*

    Heap Code for efficient implementation of Kruskal's Alg
    And sorting the edges 

*/

class Heap
{
	private int[] h; //heap array
    int N, Nmax; //heap size 
    Edge[] edge; //edges of the graph 


    // Bottom up heap constructor 
    public Heap(int _N, Edge[] _edge) {
        int i;
        Nmax = N = _N;
        h = new int[N+1];
        edge = _edge;
       
        // initially just fill heap array with 
        // indices of edge[] array.
        for (i = 0; i <= N; ++i) {
            h[i] = i;

        }
           
        // Then convert h[] into a heap
        // from the bottom up.
        for(i = N / 2; i > 0; --i) {
            
            siftDown(i);
        
        }
    }

    //siftDown to sort the heap from edges with the smallest weights to edges with the biggest weights
    private void siftDown( int k) {
        int e, j;

        e = h[k];
        while(k <= N / 2) {

            // missing lines
            j = 2 * k; //checking for a left child (j is the left child of node k) there is 
                     //left child if j <= N 

            if(j < N && edge[h[j]].wgt > edge[h[j + 1]].wgt) { //checking for a right child & chechking which child has the bigger value 
                                       //right child if j < N
                ++j;

            }//ends if 

            if(edge[e].wgt <= edge[h[j]].wgt) {

                break;

            }//ends if 

            h[k] = h[j];
            k = j;

      }//ends while 

       
        h[k] = e;
    }

    public int remove() {
        h[0] = h[1];
        h[1] = h[N--];
        siftDown(1);
        return h[0];
    }
}

/****************************************************
*
*       UnionFind partition to support union-find operations
*       Implemented simply using Discrete Set Trees
*
*****************************************************/

class UnionFindSets
{
    private int[] treeParent;
    private int N;
    
    //constructor 
    public UnionFindSets(int V)
    {
        N = V;
        treeParent = new int[V+1];
        // missing lines
        for(int i = 0; i <= V; ++i) {

            treeParent[i] = i;

        }

        
    }

    //determines which subset a particular element is in 
    public int findSet(int vertex)
    {   

        int parent = treeParent[vertex];
        

        if(parent == vertex) {

            return vertex;

        }
        else {

            return findSet(parent);
        }

    }
    
    //joins two subsets into a single subset 
    public void union(int set1, int set2)
    {
        // missing

        treeParent[set2] = set1;

    }
    
    public void showTrees()
    {
        int i;
        for(i=1; i<=N; ++i)
            System.out.print(toChar(i) + "->" + toChar(treeParent[i]) + "  " );
        System.out.print("\n");
    }
    
    public void showSets()
    {

        int u, root;
        int[] shown = new int[N+1];
        for (u=1; u<=N; ++u)
        {   
            root = findSet(u);
            if(shown[root] != 1) {
                showSet(root);
                shown[root] = 1;
            }            
        }   
        System.out.print("\n");

    }

    private void showSet(int root)
    {
        int v;
        System.out.print("Set{");
        for(v=1; v<=N; ++v)
            if(findSet(v) == root)
                System.out.print(toChar(v) + " ");
        System.out.print("}  ");

    
    }
    
    private char toChar(int u)
    {  
        return (char)(u + 64);
    }
}

class Graph 
{ 
    private int V, E;
    private Edge[] edge;
    private Edge[] mst;        

    public Graph(String graphFile) throws IOException
    {
        int u, v;
        int w, e;

        FileReader fr = new FileReader(graphFile);
		BufferedReader reader = new BufferedReader(fr);
	           
        String splits = " +";  // multiple whitespace as delimiter
		String line = reader.readLine();        
        String[] parts = line.split(splits);
        System.out.println("Parts[] = " + parts[0] + " " + parts[1]);
        
        V = Integer.parseInt(parts[0]);
        E = Integer.parseInt(parts[1]);
        
        //create array of edges
        edge = new Edge[E+1];   
        
       // read the edges
        System.out.println("Reading edges from text file");
        for(e = 1; e <= E; ++e)
        {
            line = reader.readLine();
            parts = line.split(splits);
            u = Integer.parseInt(parts[0]);
            v = Integer.parseInt(parts[1]); 
            w = Integer.parseInt(parts[2]);
            
            System.out.println("Edge " + toChar(u) + "--(" + w + ")--" + toChar(v) + "\n");                         
             
            // create Edge object  
            edge[e] = new Edge(u, v, w); 
        }
    }


    /**********************************************************
    *
    *       Kruskal's minimum spanning tree algorithm
    *
    **********************************************************/
    public Edge[] MST_Kruskal() 
    {
        int ei, i = 0;
        Edge e;
        int uSet, vSet;
        UnionFindSets partition;
        
        // create edge array to store MST
        // Initially it has no edges.
        mst = new Edge[V-1];

        // priority queue for indices of array of edges
        Heap h = new Heap(E, edge);

        // create partition of singleton sets for the vertices
       partition = new UnionFindSets(V);
       partition.showSets();

        
        while(i < V - 1) { //merging singleton sets when adding edges to the minimum spanning tree

            ei = h.remove();
            e = edge[ei];

            uSet = partition.findSet(e.u);
            vSet = partition.findSet(e.v); 
            System.out.println("uSet: " + uSet);
            System.out.println("vSet: " + vSet);
            
            if(uSet != vSet) {

                mst[i] = e;
                ++i;
                System.out.println("\nStep " + i + ":");
                e.show();
                partition.union(uSet, vSet);
                partition.showSets();
                partition.showTrees();

            }
            else { //avoiding vertices that will make a cycle

                System.out.println("\nCycle will be formed - Leaving this vertex");
                e.show();
            }

            
        }
        
        return mst;
    }


        // convert vertex into char for pretty printing
        private char toChar(int u)
        {  
            return (char)(u + 64);
        }

        public void showMST()
        {
            System.out.print("\nMinimum spanning tree build from following edges:\n");
            for(int e = 0; e < V-1; ++e) {
                mst[e].show(); 
            }
            System.out.println();
        
        }

} // end of Graph class
    
    // test code
class KruskalTrees {
    public static void main(String[] args) throws IOException
    {
        Scanner scan = new Scanner(System.in);

        //Prompting the user to enter the name of the file
        System.out.println("Please enter the file name that contains a graph (wGraph1.txt): ");
        String fileName = scan.nextLine();

        Graph g = new Graph(fileName);

        g.MST_Kruskal();

        g.showMST();
        
    }
}    


