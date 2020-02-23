/***********************************************************
 * DSAStack.java - DSA Assignment 2018 S2
 * Stack
 * Kevin Le - 19472960
 * 
 * 27/10/2018
 * 
 **********************************************************
 * 
 * REFERENCES:
 *      This file comprises previously submitted code by Kevin Le
 * 
 *      Re-Use of Previously Submitted Practical 3 - Stacks, Queues and Recursion, in unit 
 *      Data Structures and Algorithms - COMP1002. 
 * 
 *      Implementation of Stack based on Pseudocode from Lecture 3 - Stacks, Queues and Recursion.  
 *      Curtin University - Department of Computing. Accessed 18/9/2018. 
 */
import java.util.*;

public class DSAStack
{
    private int count; 
    private Object[] stack;
    private static final int DEFAULT_CAPACITY = 100; 

    /**
     * Default Constructor
     * 
     * ASSERTION:
     *      Imports stack to default capacity of 100. 
     */
    public DSAStack()
    {
        count = 0;
        stack = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Constructor 
     * 
     * IMPORTS:
     *      maxCapacity of Stack - must be set for stack init
     * 
     * ASSERTION:
     *      Creates a new stack of size specified capacity. 
     */
    public DSAStack(int maxCapacity)
    {
        count = 0; 
        stack = new Object[maxCapacity];
    }
    
    /**
     * IMPORTS:
     *      Object value to add on stack
     * 
     * ASSERTION:
     *      Adds object onto the stack
     */
    public void push(Object value)
    {
        //Check if stack is full 
        if(isFull())
        {
            throw new IllegalArgumentException("Stack is Full!");
        }
        else 
        {
            stack[count] = value;
            count++;
        }
    }

    /**
     * ASSERTION:
     *      Takes the top value off the stack
     */
    public Object pop()
    {
        Object topVal = top();
        count = count - 1;
        return topVal;
    }

    /**
     * ASSERTION:
     *      Takes the top value off the stack
     */
    public Object top()
    {
        Object topVal;
        if(isEmpty())
        {
            throw new IllegalArgumentException("Stack is empty!");
        }
        else 
        {
            topVal = stack[count - 1]; 
        }
        return topVal; 
    }

    //Accessor
    //EXPORTS: Returns the number of items in the stack as an Integer
    public int getCount()
    {
        return count;
    }

    //EXPORTS: Returns boolean if the stack is empty. 
    public boolean isEmpty()
    {
        return (count == 0);
    }

    //EXPORTS: Returns boolean if the stack is full. 
    public boolean isFull()
    {
        return (count == stack.length); 
    }
}
