/***********************************************************
 * DSALinkedList.java - DSA Assignment 2018 S2
 * Linked List
 * Kevin Le - 19472960
 * 
 * 27/10/2018
 * 
 * Contains a DSALinkedListNode and Iterator Private Class
 * 
 **********************************************************
 * 
 * REFERENCES:
 *      This file comprises previously submitted code by Kevin Le
 * 
 *      Re-Use of Previously Submitted Practical 4 - Linked Lists, in unit 
 *      Data Structures and Algorithms - COMP1002. 
 * 
 *      Implementation of Linked List  and Iterator based on Pseudocode from Lecture 4
 *      Linked Lists, Iterators and Generics.  Curtin University - Department of Computing. 
 *      Accessed 9/10/2018. 
 */
import java.util.*;

public class DSALinkedList<E> implements Iterable<E>
{
    DSAListNode<E> first, last, newNd, currNd, prevNd;
    E nodeValue;  

    /**
     * Constructor
     * 
     * ASSERTION:
     *      Initialize Linked List with both head(first) and tail(last) pointing to null/nothing
     */
    public DSALinkedList()
    {
        first = null;
        last = null;
    }

    /**
     * IMPORTS:
     *      E newValue, value to be inserted in the front linked list
     * 
     * ASSERTION:
     *      Creates a new Linked List node containing the value, adding it to the linked
     *      list making the required connections. 
     */
    public void insertFirst(E newValue) //Inserts at front of List
    {
        newNd = new DSAListNode(newValue); //Create new node 

        if(isEmpty()) 
        {
            last = newNd; //If empty the new node is the last node
        }
        else
        {
            first.prev = newNd; //If not empty the new node gets added on  
        }
        newNd.next = first;
        first = newNd; //New node is now the first node
    }

    /**
     * IMPORTS:
     *      E newValue, value to be inserted at the end of the linked list
     * 
     * ASSERTION:
     *      Creates a new Linked List node containing the value, adding it to the linked
     *      list making the required connections. 
     */
    public void insertLast(E newValue) //Inserts at the end of the list
    {
        //newNd = allocate DSAListNode(newValue)
        newNd = new DSAListNode(newValue); //Create a new Node

        if(isEmpty()) //If the node is empty
        {
            first = newNd; //Make it the first node
        }
        else
        {
            last.next = newNd; //Old last gets replaced with this last
            newNd.next = last; // 
        }
        last = newNd; //New node is now the last node. 
    }

    /**
     * EXPORTS: 
     *      Boolean if the list is null 
     * 
     * ASSERTION:
     *      Checks if the first pointer isnt null. If null no nodes have been added. 
     */
    public boolean isEmpty() //Checks if the first is null 
    {
        return (first == null);
    }

    /**
     * EXPORTS:
     *      E 
     * 
     * ASSERTION:
     *      Returns the first element in the linked list
     */
    public E peekFirst()
    {
        if(isEmpty())
        {
            throw new IllegalArgumentException("Empty!");
        }
        else
        {
            //Retrieve first value from node
            nodeValue = first.value;
        }
        return nodeValue;
    }

    /**
     * EXPORTS:
     *      E 
     * 
     * ASSERTION:
     *      Returns the last element in the linked list
     */
    public E peekLast()
    {
        if(isEmpty())
        {
            throw new IllegalArgumentException("Empty!");
        }
        else
        {
            //Retrieve last value from node
            nodeValue = last.value; 
        }
        return nodeValue; 
    }

    /**
     * EXPORTS:
     *      Object - Temporary node
     * 
     * ASSERTION:
     *      Removes the first node from the linked list. 
     */
    public Object removeFirst()
    {   
        //Temporary node
        DSAListNode temp = first; 

        if(isEmpty()) //Before remove, must check if empty or NullPointer
        {
            throw new IllegalArgumentException("Empty!");           
        }
        else
        {
            if(first.next == null) //If there is only one node
             {
                last = null; //Set the last node to null 
            }
            else 
            {
                first.next.prev = null; //Set all node pointers to null 
                first = first.next; //First is now the next before this node
            }
        }
        return temp;
    }

    /**
     * EXPORTS:
     *      Object - Temporary node
     * 
     * ASSERTION:
     *      Removes the last node from the linked list. 
     */
    public E removeLast()
    {
        DSAListNode temp = last; 

        if(isEmpty())
        {
            throw new IllegalArgumentException("Empty!");           
        }
        else
        {
            if(first.next == null) //If there is only one node
            {
                first = null; //Set the first node to null 
            }
            else
            {
                last.prev.next = null; //Set all node pointers to null 
                last = last.prev; //Last is now the next before this node
            }
        }
        return nodeValue;
    }

    /**
     * EXPORTS:
     *      Iterator Object 
     * 
     * ASSERTION: 
     *      Returns a new Iterator object for the current Linked List. 
     */
    public Iterator iterator()
    {
        return new DSALinkedListIterator(this);
    }

    /*********************************************************************
     * DSAListNode Private inner class. 
     * 
     * Stores the Node information. 
     */
    private class DSAListNode<E>
    {
        E value;
        DSAListNode next;
        DSAListNode prev; 

        /**
         * Constructor
         * 
         * IMPORTS: 
         *      E value to be stored in the node
         * 
         * ASSERTION:
         *      Creates new Node
         */
        public DSAListNode(E inValue)
        {
            value = inValue; 
        }

        //Prints the node value. 
        public void display()
        {
            System.out.println(value);
        }
    }

    /*********************************************************************
     * DSALinkedListIterator Private inner class implementing Javas Iterator
     * 
     * Provides a way to iterate node by node through the list.  
     */
    private class DSALinkedListIterator<E> implements Iterator<E>
    {
        private DSAListNode<E> iterNext;
        
        /**
         * Constructor
         * 
         * IMPORTS: 
         *      Linked List
         * 
         * ASSERTION:
         *      Creates new Iterator
         */
        public DSALinkedListIterator(DSALinkedList list)
        {
            iterNext = list.first; 
        }

        /**
         * EXPORTS:
         *      Boolean if the list has a next value that can be seen from the current node
         * 
         * ASSERTION:
         *      Checks if the next isnt null 
         */
        public boolean hasNext()
        {
            return (iterNext != null);
        }

        /**
         * EXPORTS:
         *      E Value
         * 
         * ASSERTION:
         *      Returns the next node from the current node. 
         */
        public E next()
        {
            E value; 

            if(iterNext == null)
            {
                value = null;
            }
            else
            {
                value = iterNext.value; 
                iterNext = iterNext.next; 
            }

            return value;
        }

        //Unsupported method.
        public void remove()
        {
            System.out.println("Not Supported!");
        }
    }
}