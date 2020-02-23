/***********************************************************
 * DSGraph.java - DSA Assignment 2018 S2
 * Graph Class
 * Kevin Le - 19472960
 * 
 * 27/10/2018
 * 
 * Contains a Vertex and Edge private class
 * 
 **********************************************************
 * 
 * REFERENCES:
 *      This file comprises previously submitted code by Kevin Le
 * 
 *      Re-Use of Previously Submitted Practical 6 - Graphs, in unit Data Structures and Algorithms - COMP1002 
 * 
 *      Implementation of Graph based on Pseudocode from Lecture 6 - Graphs. Curtin University - 
 *      Department of Computing. Accessed 9/10/2018. 
 */

import java.util.*;

public class DSAGraph 
{
    private DSALinkedList<DSAGraphVertex> vertices;

    /**
     * Constructor
     * 
     * ASSERTION:
     *      Creates a new Graph Object.
     */
    public DSAGraph()
    {
        //Create a linked lists of vertices. 
        vertices = new DSALinkedList<DSAGraphVertex>(); 
    }

    /**
     * IMPORTS:
     *      String, vertex label. 
     *      Object, value to be stored at vertex.
     * 
     * ASSERTION:
     *      Creates new vertex and inserts vertex into linked list of vertices. 
     */
    public void addVertex(String inLabel, Object inValue)
    {
        DSAGraphVertex vertex = new DSAGraphVertex(inLabel, inValue);
        vertices.insertFirst(vertex);
    }

    /**
     * IMPORTS:
     *      Edge 1 and Edge 2. Both DSAGraphVertex Objects. The 2 vertices for the link/ connection to be made
     *      Weight, any integer value for the edge to contain. Normally time/distance
     */
    public void addEdge(DSAGraphVertex vertex1, DSAGraphVertex vertex2, int weight)
    {
        //Check if connection. 
        /*if(!(vertex1.checkExists(vertex2)))
        {*/
            vertex1.addEdge(vertex2, weight);
            vertex2.addEdge(vertex1, weight);
        //}
    }

    /**
     * EXPORTS:
     *      Number of vertices in graph as an Integer
     * 
     * ASSERTION:   
     *      Iterates through the vertices linked list counting the number of vertices, then returns the number
     */
    public int getVertexCount()
    {
        //Count through the linked list. Iterator hasNext methods. 
        int count = 0; 
        
        for(DSAGraphVertex vertex : vertices)
        {
            count++; 
        }

        return count; 
    }

    /**
     * IMPORTS:
     *      String representing the vertex label
     * 
     * EXPORTS:
     *      Boolean representing if the vertex exists in the graph or not
     * 
     * ASSERTION:  
     *      Iterates through vertices linked list, if there is a match with the label, 
     *      then the vertex exists. Else false. 
     */
    public boolean exists(String label)
    {
        boolean retVal = false; 

        for(DSAGraphVertex i : vertices)
        {
            if(i.getLabel().equals(label))
            {
                retVal = true; 
            }
        }

        return retVal; 
    }

    /**
     * EXPORTS:
     *      DSAGraphVertex Object
     * 
     * ASSERTION: 
     *      Returns the first vertex in the vertices linked list. 
     */
    public DSAGraphVertex getFirstVertex()
    {
        return(vertices.peekFirst()); 
    }

    /**
     * IMPORTS:
     *      The label of the vertex as a String 
     * 
     * EXPORTS:
     *      DSAGraphVertex object 
     * 
     * ASSERTION:
     *      Find if a vertex has the same label as the label to search by, if true, vertex is found 
     *      return that vertex. 
     */
    public DSAGraphVertex getVertex(String label)
    {
        DSAGraphVertex retVal = null; 

        for(DSAGraphVertex i : vertices)
        {
            if(i.getLabel().equals(label))
            {
                retVal = i; 
            }
        }

        return retVal; 
    }

    /**
     * IMPORTS:
     *      DSAGraphVertex Object
     * 
     * EXPORTS:
     *      DSALinkedList of Edge Objects
     * 
     * ASSERTION:
     *      Returns the Adjacency List of the vertex 
     */
    public DSALinkedList<Edge> getAdjacent(DSAGraphVertex vertex)
    {
        return(vertex.adjList);
    }

    /**
     * EXPORTS:
     *      DSALinkedList of Graph Vertices
     * 
     * ASSERTION:
     *      Returns the Linked List of vertices.
     */
    public DSALinkedList<DSAGraphVertex> getVertices()
    {
        return vertices; 
    }

    /**
     * IMPORTS: 
     *      DSAGraphVertex Object
     * 
     * ASSERTION:
     *      Displays the adjacency list of the vertex selected 
     */
    public void displayList(DSAGraphVertex vertex)
    {
        vertex.displayAdj();
    }

    /**
     * IMPORTS:
     *      DSAGraphVertex Representing the point to begin the traversal at. 
     * 
     * EXPORTS:
     *      String array containing the results of the traversal (in the format From, Weight, To)
     * 
     * ASSERTION:
     *      Depth First Search algorithm for graph traversal. Traverses graph and returns the visited vertices
     *      stored inside a string array. For the calling function of Itinerary to process
     * 
     * REFERENCES: 
     *      Obtained from GeeksforGeeks at "Iterative Depth First Traversal of Graph". Retrieved from:
     *      https://www.geeksforgeeks.org/iterative-depth-first-traversal/. Accessed 26/10/2018 
     */
    public String[] traverse(DSAGraphVertex source)
    {
        //Stack must have a set size so 5 times the actual size is safe. 
        //As stack will have more vertices added on than the actual number of vertices
        DSAStack stack = new DSAStack(getVertexCount() * 5); 
        DSAGraphVertex s, v;
        int index = 0; 
        DSALinkedList<Edge> adjList; 

        //Set array to same size as stack 
        String[] locations = new String[getVertexCount() * 5];
        System.out.println("Itinerary: ");

        //Add the current vertex to the stack
        stack.push(source);

        try 
        {
            while(stack.isEmpty() == false)
            {
                //Pop a vertex off the stack
                s = (DSAGraphVertex)stack.pop();
                //Get the adjacency list for that vertex 
                adjList = s.getAdjList(); 
    
                //if the vertex hasnt been visited yet by DFS
                if(s.getVisited() == false)
                {
                    //Store its label in the string array
                    locations[index] = s.getLabel();
                    index++; 

                    //Store the weight to get to that vertex in the string array 
                    locations[index] = String.valueOf(s.getDistance()); 
                    index++; 

                    //Mark it as visited
                    s.setVisited(true);
                }
    
                //For the vertices in the adjacency list of the vertex 
                for(Edge e : adjList)
                {
                    //Get the weight of the edge 
                    v = e.getVertex(); 
                    v.setDistance(e.getWeight());
                    //Add the vertices in the list that have not been visited to the stack 
                    if(v.getVisited() == false)
                    {
                        stack.push(v);
                    }
                }
            }
        } 
        catch (NullPointerException e)
        {
            System.out.println("NPE - Error.");
        }

        //Return the string array. 
        return locations; 
    }


    /*********************************************************************
     * DSAGraphVertex Private inner class. 
     * 
     * Stores the vertex information. 
     */
    private class DSAGraphVertex
    {
        private String label;
        private DSALinkedList<Edge> adjList;
        private Object value; 
        private boolean visited = false; 

        //Fields for the traversal to use 
        private int distance; 
        private DSAGraphVertex prev;

        /**
         * Constructor 
         * 
         * IMPORTS:
         *      String label for the vertex
         *      Object, any object to be stored in the vertex. 
         */
        public DSAGraphVertex(String inLabel, Object inValue)
        {
            label = inLabel;
            value = inValue; 

            //Create a new adjacency list 
            adjList = new DSALinkedList<Edge>();
        }

        //Accessor
        //EXPORTS: Returns the label as a string.
        public String getLabel()
        {
            return label;
        }

        //Accessor
        //EXPORTS: Returns the object stored in the vertex.
        public Object getValue()
        {
            return value; 
        }

        //Accessor
        //EXPORTS: Returns the distance/weight as an integer.
        public int getDistance()
        {
            return distance; 
        }

        //Accessor
        //EXPORTS: Returns the previous vertex as a DSAGraphVertex Object.
        public DSAGraphVertex getPrev()
        {
            return prev; 
        }
    
        //Accessor
        //EXPORTS: Returns the adjacency List Linked List
        public DSALinkedList<Edge> getAdjList()
        {
            return adjList;
        }

        //Accessor
        //EXPORTS: Returns the visited as a boolean.
        public boolean getVisited()
        {
            return visited; 
        }

        //toString method
        //EXPORTS: Returns a string representation of the vertex
        public String toString()
        {
            return(label + ", " + value + ", " + visited + ", " + distance);
        }

        //Accessor
        //IMPORTS: Label of vertex to find
        //EXPORTS: DSAGraphVertex Object if found. 
        public DSAGraphVertex getVertex(String inLabel)
        {
            DSAGraphVertex retVal = null; 
    
            for(DSAGraphVertex i : vertices)
            {
                if(i.getLabel().equals(inLabel))
                {
                    retVal = i; 
                }
            }

            return retVal;
        }

        //Mutator
        //IMPORTS: Distance as an Integer
        public void setDistance(int inDistance)
        {
            distance = inDistance; 
        }

        //Mutator
        //IMPORTS: DSAGraphVertex object
        public void setPrev(DSAGraphVertex inPrev)
        {
            prev = inPrev;
        }

        //Mutator
        //IMPORTS: Visited as a boolean
        public void setVisited(boolean inVisited)
        {
            visited = inVisited;
        }

        //Displays adjacency list of current vertex
        public void displayAdj()
        {
            for(Edge e : adjList)
            {
                System.out.println(e); 
            }
        }

        /**
         * IMPORTS:
         *      DSAGraphVertexObject
         * 
         * EXPORTS:
         *      Boolean - true if vertex exists. 
         * 
         * ASSERTION:
         *      Check if the vertex exists in the adjacency list of the object 
         */
        public Boolean checkExists(DSAGraphVertex vertex)
        {
            Boolean retVal = false; 
            Iterator iter = adjList.iterator();

            while(iter.hasNext() == true)
            {
                if(iter.next() == vertex)
                {
                    retVal = true; 
                }
            }
            
            return retVal;  
        }

        /**
         * IMPORTS: 
         *      DSAGraphVertex Object 
         *      Weight of the edge to be added. 
         * 
         * ASSERTION: 
         *      Vertex added to the adjacency list of the current vertex. Edge object is created first. Then
         *      added to the adjacency list. 
         */
        public void addEdge(DSAGraphVertex inVertex, int inWeight)
        {
            if(checkExists(inVertex) == false)
            {
                Edge edge = new Edge(inVertex, inWeight);
                adjList.insertFirst(edge);
            }
        }
    }

    /*********************************************************************
     * Edge Private inner class. 
     * 
     * Stores the edge information. 
     */
    private class Edge
    {
        private DSAGraphVertex vertex; 
        private int weight; 

        /**
         * Constructor
         * 
         * IMPORTS:
         *      DSAGraphVertex object, weight of edge
         */
        public Edge(DSAGraphVertex inVertex, int inWeight)
        {
            vertex = inVertex;
            weight = inWeight; 
        }

        //Accessor
        //EXPORTS: Returns DSAGraphVertex Object
        public DSAGraphVertex getVertex()
        {
            return vertex;
        }

        //Accessor 
        //EXPORTS: Returns the edge weight as an Integer
        public int getWeight()
        {
            return weight; 
        }

        //toString Method
        //EXPORTS: Returns a string representation of the Edge
        public String toString()
        {
            return(vertex.toString() + ", " + weight); 
        }
    }
}
