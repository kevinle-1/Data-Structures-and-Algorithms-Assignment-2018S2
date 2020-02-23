/**
 * Margin.java - DSA Assignment 2018 S2
 * Margin Object - Storing Margins calculated in Divisions.getMargins()
 * Kevin Le - 19472960
 * 
 * 16/10/2018
 * 
 * Default Constructor, Copy Constructor, Equals Method, Mutators Omitted due to not being required. 
 */

public class Margin 
{
    private String state; 
    private String divName;
    private int divID; 
    private double margin;
    private int votesFor;
    private int votesAgainst; 

    /**
     * IMPORTS: 
     *      Required parameters to construct a new Margin object. 
     * 
     * ASSERTION: 
     *      Creates a new Margin object. Containing state, divName, divId, margin, voteFor party, votes against party. 
     */
    public Margin(String inState, String inDivName, int inDivID, double inMargin, int inVotesFor, int inVotesAgainst)
    {
        state = inState; 
        divName = inDivName;
        divID = inDivID;
        margin = inMargin; 
        votesFor = inVotesFor;
        votesAgainst = inVotesAgainst;
    }

    /**
     * ASSERTION: 
     *      Returns a String representation of the Object.
     * 
     * EXPORTS:
     *      String. 
     */
    public String toString()
    {
        return(state + "," + divName + "," + divID + "," + margin + "," + votesFor + "," + votesAgainst);
    }

    /**
     * ASSERTION: 
     *      Prints a neat representation of the data. 
     */
    public void printOut()
    {
        System.out.println(state + ", " + divName + "," + divID + ", " + "Margin: " + String.format("%.3g", margin));
    }

    //Accessor 
    //EXPORT: Returns the State as a String
    public String getState()
    {
        return state; 
    }

    //Accessor 
    //EXPORT: Returns the Division ID as an integer
    public int getDivID()
    {
        return divID; 
    }

    //Accessor 
    //EXPORT: Returns the Division name as a String
    public String getDivName()
    {
        return divName; 
    }    

    //Accessor 
    //EXPORT: Returns the Margin as a Real value
    public double getMargin()
    {
        return margin; 
    }

    //Accessor 
    //EXPORT: Returns the amount of votes for the party in the current division as an Integer
    public int getVotesFor()
    {
        return votesFor; 
    }

    //Accessor 
    //EXPORT: Returns the amount of votes against the party in the current division as an Integer
    public int getVotesAgainst()
    {
        return votesAgainst; 
    }
}