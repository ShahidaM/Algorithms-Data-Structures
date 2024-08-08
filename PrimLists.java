/*

    Description: Prim's Algorithm Implementation in Java using Heaps & Best First Traversal. 
                 Graph Traversals: Depth First Traversal using Recursion and iteration with stack.
                                   Breadth First Traversal using queue.
                                   Dijkstra shortest path algorithm.
   
    Author: Shahida Mohammed-Ahmed

*/
// Simple weighted graph representation 
// Uses an Adjacency Linked Lists, suitable for sparse graphs

import java.io.*;
import java.util.EmptyStackException;
import java.util.Scanner;


class GraphLists {
    class Node {
        public int vert;
        public int wgt;
        public Node next;
    }
    
    // V = number of vertices
    // E = number of edges
    // adj[] is the adjacency lists array
    private int V, E;
    private Node[] adj;
    private Node z;
    private int[] mst;
    
    
    // used for traversing graph
    private int[] visited;
    private int id;
    
    
    // default constructor
    public GraphLists(String graphFile)  throws IOException
    {
        int u, v;
        int e, wgt;
        Node t;

        FileReader fr = new FileReader(graphFile);
		BufferedReader reader = new BufferedReader(fr);
	           
        String splits = " +";  // multiple whitespace as delimiter
		String line = reader.readLine();        
        String[] parts = line.split(splits);
        System.out.println("Parts[] = " + parts[0] + " " + parts[1]);
        
        V = Integer.parseInt(parts[0]);
        E = Integer.parseInt(parts[1]);
        
        // create sentinel node
        z = new Node(); 
        z.next = z;
        
        // create adjacency lists, initialised to sentinel node z   
        visited = new int[V+1];  

        adj = new Node[V+1];  

        for(v = 1; v <= V; ++v) {

            adj[v] = z;
        }               
        
       //reading the edges from wGraph1.txt 
        System.out.println("Reading edges from text file");
        for(e = 1; e <= E; ++e)
        {
            line = reader.readLine();
            parts = line.split(splits);
            u = Integer.parseInt(parts[0]);
            v = Integer.parseInt(parts[1]); 
            wgt = Integer.parseInt(parts[2]);
            
            System.out.println("Edge " + toChar(u) + "--(" + wgt + ")--" + toChar(v));    
            
            // write code to put edge into adjacency list
            //adding edges into adjacency list   
            t = new Node();
            t.vert = v;
            t.wgt = wgt;
            t.next = adj[u];
            adj[u] = t;

            t = new Node();
            t.vert = u;
            t.wgt = wgt;
            t.next = adj[v];
            adj[v] = t;


        }	 

    }//ends graph lists constructor
   
    // convert vertex into char for pretty printing
    private char toChar(int u)
    {  
        return (char)(u + 64);
    }
    
    // method to display the graph representation
    public void display() {
        int v;
        Node n;
        
        for(v = 1; v <= V; ++v){

            System.out.print("\nadj[" + toChar(v) + "] ->" );

            for(n = adj[v]; n != z; n = n.next) {

                System.out.print(" |" + toChar(n.vert) + " | " + n.wgt + "| ->");
            
            }
        }
      
    }

    /*
        DEPTH FIRST GRAPH TRAVERSAL
    */

    //method to initialise Depth First Traversal of Graph using recursion 
    public void DF(int s) {

        int v;
        id = 0;

        for(v = 1; v <= V; ++v) {

            visited[v] = 0;
        }

        System.out.println();
        System.out.println("\nStarting Depth First Traversal using recursion\n");
        System.out.println("Starting with vertex: " + toChar(s));

        dfVisit(0, s);
        
    }
    
    
    // Recursive Depth First Traversal for adjacency list
    private void dfVisit(int prev, int v)
    {
        int u;

        visited[v] = ++id; 

        System.out.println("\nDF Visited vertex " + toChar(v) + " along edge " + toChar(prev) + " ----- " + toChar(v)); //displaying the order of visiting to the user 

        for(Node t = adj[v]; t != z; t = t.next) {

            u = t.vert;    //holds neighbours of current vertex 
            if (visited[u] == 0) {

                dfVisit(v, u); //recursion - calling dfVisit from the dfVisit method 
        
            }

        } 
    }

    // method to initialise Iterative Depth First Traversal of Graph 
    public void DF_iteration(int s) //s is the vertex we start with 
    {
        Stack visitStack = new Stack();
        visitStack.push(s); //pushing the current vertex to the stack 

        int v, u;
        id = 0;
    
        for(v = 1; v <= V; ++v) {

            visited[v] = 0;
        }

        System.out.println("\nStarting Iterative Depth First Traversal\n");
        System.out.println("\nStarting with vertex: " + toChar(s));

        while(visitStack.isEmpty() != true) {

            v = visitStack.pop();
            
            if(visited[v] == 0) {

                visited[v] = ++id;
                System.out.println("DF iteration just visited vertex: " + toChar(v));

                for(Node t = adj[v]; t != z; t = t.next) {

                    u = t.vert; //hold neighbours of current vertex
                    if(visited[u] == 0) {

                        visitStack.push(u); //pushing neighbours that havent been Visited onto the stack
                    }

                } 
            }
            

        }

        //displaying the order in which the vertices were visited
        id = 0;

        System.out.println();
        for(u = 1; u <= V; u++) {

            id++;
            System.out.println("DF Order of visiting from vertex: " + toChar(s) + " to vertex " + toChar(id) + " ----- " + visited[u]);


        }

        
    }

    /*
        BREADTH FIRST GRAPH TRAVERSAL
    */

    //method to initialise Breath First Traversal of Graph
    public void BF(int s) //s is the vertex we start with 
    {
        Queue visitQueue = new Queue();

        int v, u;
        id = 0;
        
        for(v = 1; v <= V; ++v) {

            visited[v] = 0;
        }

        visitQueue.enQueue(s); //enqueing the current node to the queue

        System.out.println("\nStarting Breath First Traversal\n");
        System.out.println("Starting with vertex: " + toChar(s));
        
        while(visitQueue.isEmpty() != true){

            v = visitQueue.deQueue();

            if(visited[v] == 0) {

                System.out.println("BF iteration just visited vertex: " + toChar(v));
                visited[v] = ++id;

                for(Node t = adj[v]; t != z; t = t.next){ 

                    u = t.vert; //hold neighbours of current vertex
        
                    if(visited[u] == 0) {

                        visitQueue.enQueue(u); //enqueing neighbours that havent been Visited onto the queue

                    }

                } 


            }

        }

        //displaying the order in which the vertices were visited
    
        id = 0;

        System.out.println();

        for(u = 1; u <= V; u++) {

            id++;
            System.out.println("BF Order of visiting from vertex: " + toChar(s) + " to vertex " + toChar(id) + " ----- " + visited[u]);


        }
    
    }//ends breadth first traversal method
    
	public void MST_Prim(int s)
	{
        int v, u;
        int wgt, wgt_sum = 0;
        int[]  dist, parent, hPos;
        Node t;

        //code here
        
        /*
            INITIALISING THE DIST[] PARENT[] HPOS[] ARRAYS:
        */

        //the dist array between nodes
        dist = new int[V + 1];

        //the parent array 
        parent = new int[V + 1];

        //current position in the heap using hPos array
        hPos = new int[V + 1];

        for(v = 1; v <= V; ++v) {

            dist[v] = Integer.MAX_VALUE;  //initialising the dist[] to max value (infinity)
            parent[v] = 0;    //initialising parent to be 0
            hPos[v] = 0;    //initialising hPos to be 0
 
        }//ends for 
        
        dist[s] = 0;
        
        Heap pq =  new Heap(V, dist, hPos);
        pq.insert(s); //inserting s as the root of the MST
        
        /* 
            BEST FIRST TRAVERSAL - visiting vertices with the highest priority 
        */
        while (pq.isEmpty() != true)  
        {
            v = pq.remove(); //adding v to the minimum 

            //adding the dist of v to the MST and incrementuing the weight of the MST
            wgt_sum = wgt_sum + dist[v];

            System.out.println("\n");
            System.out.println("Adding edge " + toChar(parent[v]) +  " ---(" + dist[v] + ")--- " + toChar(v) + " to Minimum Spanning Tree");

            dist[v] = -dist[v]; //marking v in the MST 

            //checking neighbouring vertices of v - looping through the adjacency list
            for(t = adj[v]; t != z; t = t.next){

                u = t.vert; //hold neighbours of current vertex

                wgt = t.wgt; //weight of each branch 

                if(wgt < dist[u]){

                    dist[u] = wgt;  
                    parent[u] = v;
                   

                    if(hPos[u] == 0) { //if vertex u is not in the heap

                        pq.insert(u); //add vertex u to the heap
            
                    } //ends inner if 
                    else {

                        pq.siftUp(hPos[u]); //records where u is on the heap 

                    } //ends else

                }//ends outer if 

            }
            
        }

        System.out.print("\n\nWeight of MST = " + wgt_sum + "\n");
        
        mst = parent;                      		
	}
    
    //Displaying the Minimum Spanning Tree 
    public void showMST()
    {
        System.out.print("\n\nMinimum Spanning tree parent array is:\n");
        for(int v = 1; v <= V; ++v)
            System.out.println(toChar(v) + " -> " + toChar(mst[v]));
        System.out.println("");
    }

    public void SPT_Dijkstra(int s)
    {
        int v, u;
        Node n;
        int[] dist = new int[V + 1];
        int[] hPos = new int[V + 1];
        Heap pq = new Heap(V, dist, hPos);
        pq.insert(s);
        for (v = 1; v <= V; ++v) {
            if (v != s)
                dist[v] = Integer.MAX_VALUE;
            hPos[v] = 0;
        }

        int[] prev = new int[V + 1]; // array to store previous vertices
        int totalWeight = 0; // variable to store the total weight of the shortest paths
        while (!pq.isEmpty()) {
            v = pq.remove();
            hPos[v] = 0;

            for (n = adj[v]; n != z; n = n.next) {
                u = n.vert;
                if (dist[u] > dist[v] + n.wgt) {
                    dist[u] = dist[v] + n.wgt;
                    prev[u] = v; // store the previous vertex
                    if (hPos[u] == 0) {
                        pq.insert(u);
                    } else {
                        pq.siftUp(hPos[u]);
                    }
                }
            }
            totalWeight += dist[v]; // add weight to total weight outside the inner loop
        }

        System.out.println("Dijkstra Shortest Path Tree\n");

        System.out.print("Shortest Paths: ");
        for (v = 1; v <= V; ++v) {
            System.out.print(toChar(s) + " -> " + toChar(v) + " (" + dist[v] + ") ");
        }
        System.out.println();
        System.out.println("Total Weight of Shortest Paths: " + totalWeight);

        // Print the path for each vertex
        System.out.println("Paths:");
        for (v = 1; v <= V; ++v) {
            if (v != s) {
                System.out.print(toChar(s) + " -> " + toChar(v) + " (" + dist[v] + "): ");
                printPath(prev, s, v);
                System.out.println();
            }
        }
    }

    // Recursive function to print the path
    private void printPath(int[] prev, int s, int v) {
        if (prev[v] == s) {
            System.out.print(toChar(s) + " -> " + toChar(v));
        } else {
            printPath(prev, s, prev[v]);
            System.out.print(" -> " + toChar(v));
        }
    }
}



public class PrimLists {
    public static void main(String[] args) throws IOException
    {
        Scanner scan = new Scanner(System.in);

        //Prompting the user to enter the name of the file
        System.out.println("Please enter the file name that contains a graph (wGraph1.txt): ");
        String fileName = scan.nextLine();

        //Prompting the user to an interger to represent the starting vertex
        System.out.println("Please a number to represent a vertex (1 - 13): ");
        int s = scan.nextInt();
        
        GraphLists g = new GraphLists(fileName);
       
        g.display();             
               
        g.DF(s); //depth first traversal using recursion 
        
        g.DF_iteration(s); //depth first traversal using a stack (iteration)

        g.BF(s);   //breadth first traversal using a queue (iteration)
        
        g.MST_Prim(s); //Prims Algorithm using Best First Traversal (Heap)
        
        g.showMST();    

             
        
    }
    
    
}


/*

Heap Code for efficient implementation of Prim's Alg

*/

class Heap
{
    private int[] h;	   // heap array
    private int[] hPos;	   // hPos[h[k]] == k
    private int[] dist;    // dist[v] = priority of v

    private int N;         // heap size
   
    // The heap constructor gets passed from the Graph:
    //    1. maximum heap size
    //    2. reference to the dist[] array
    //    3. reference to the hPos[] array
    public Heap(int maxSize, int[] _dist, int[] _hPos) 
    {
        N = 0;
        h = new int[maxSize + 1];
        dist = _dist;
        hPos = _hPos;
    }


    public boolean isEmpty() 
    {
        return N == 0;
    }


    public void siftUp( int k) 
    {
        int v = h[k];

        // code yourself
        // must use hPos[] and dist[] arrays
        h[0] = 0;

        while(dist[v] < dist[h[k/2]]) { //while the distance is less that the edge above 

            h[k] = h[k/2];
            hPos[h[k]] = k;
            k = k/2;

        }//ends while

        h[k] = v;
        hPos[v] = k;
    }


    public void siftDown( int k) 
    {
        int v, j;
       
        v = h[k];    //parent

        // code yourself 
        // must use hPos[] and dist[] arrays

        while(k <= N / 2) { //if there is left child/branch

            j = 2 * k; //checking for a left child/left branch


            if(j < N && dist[h[j]] > dist[h[j + 1]]) { //checking if the there is a right child/branch and checking which branch has a larger distance 

                ++j;

            }//ends if

            if(dist[v] <= dist[h[j]]) { //comparing the child with the parent 

                break;

            }

            h[k] = h[j]; 
            hPos[h[k]] = k;  //updating position of child 
            k = j;  //assigning the vertex a new position

        } //ends while

        h[k] = v;
        hPos[v] = k; //updates the newer highest priority hPos value
    }


    public void insert( int x) 
    {
        h[++N] = x;
        hPos[x] = N;
        siftUp(N);
    }


    public int remove() 
    {   
        int v = h[1];
        hPos[v] = 0; // v is no longer in heap
        h[N+1] = 0;  // put null node into empty spot
        
        h[1] = h[N--];
        siftDown(1);
        
        return v;
    }

}

/*

Iterative Depth first Traversal using a stack 

*/

class Stack {
        
    class Node {

        int data;
        Node next;

    }

    private Node top;

    public Stack() {

        top = null;

    }

    public void push(int x) {
        Node  t = new Node();
        t.data = x;
        t.next = top;
        top = t;
    }

    public int pop() {

        if(top == null){

            throw new EmptyStackException();
        }
        else {

            int x = top.data;
            top = top.next;
            return x; 

        }//ends else 

    }//ends pop method

    public void displayStack() {
        Node t = top;

        System.out.println("\nStack contents are:  ");
        
        while (t != null) {
            
            System.out.print(t.data + " ");
            t = t.next;
        }
        
        System.out.println("\n");
    }

    public boolean isEmpty() {

        return top == null;
    
    }// ends isEmpty()

}

/*

Iterative Breath first Traversal using a queue

*/


class Queue {

    private class Node {
      int data;
      Node next;
    }
  
    Node z;
    Node head;
    Node tail;
  
    public Queue() {
      z = new Node();
      z.next = z;
      head = z;
      tail = null;
    }
  
    public void enQueue(int x) {
        Node temp;
    
        temp = new Node();
        temp.data = x;
        temp.next = z;
    
        if (head == z) {

            head = temp;
        }
        else {

            tail.next = temp;
        }
        
        tail = temp; 
    }
  
    public int deQueue() {
      
          
        int x = head.data;
          
        head = head.next;
        
        if (head == head.next) // empty queue
            tail = null;
                       
        return x;
    
    }// ends dequeue
  
    public boolean isEmpty() {
  
      return head == z;
  
    }// ends isEmpty()

}

