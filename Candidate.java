/*************************************************************************************************
 * Candidate.java - DSA Assignment 2018 S2
 * Candidate Object - Storing All the common fields for NationalCandidate and StateCandidate - Is a super class
 * Kevin Le - 19472960
 * 
 * 27/10/2018
 * 
 * Default Constructor, Copy Constructor, Equals Method, Mutators Omitted due to not being required and used. 
 */
public class Candidate
{
    private String state; 

    //Party Information
    private String partyShortname; 
    private String partyName; 

    //NationalCandidate Information
    private int candID; 
    private String candSurname; 
    private String candFirstname;

    //Elected
    private String elected;
    private String historicElected; 

    /**
     * IMPORTS:
     *      Required information to construct a Candidate object. The common variables of HouseCandidates and HouseStates CSVs
     * 
     * ASSERTION:
     *      Constructs a new Candidate object 
     */
    public Candidate(String inState, String inPartyShortname, String inPartyName, int inCandID, 
                     String inCandSurname, String inCandFirstName, String inElected, String inHistoricElected)
    {
        state = inState;

        partyShortname = inPartyShortname;
        partyName = inPartyName;

        candID = inCandID; 
        candSurname = inCandSurname;
        candFirstname = inCandFirstName;

        elected = inElected; 
        historicElected = inHistoricElected; 
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
        return(state + "," + partyShortname + "," + partyName + "," + candID + "," + candSurname + "," + candFirstname + "," + 
               elected + "," + historicElected);
    }

    //Accessor
    //EXPORT: Returns the State as a String    
    public String getState()
    {
        return state; 
    }

    //Accessor
    //EXPORT: Returns the Party Name (Abbreviated) as a String  
    public String getPartyShortname()
    {
        return partyShortname; 
    }

    //Accessor
    //EXPORT: Returns the Party Name (Full Form) as a String      
    public String getPartyName()
    {
        return partyShortname; 
    }

    //Accessor
    //EXPORT: Returns the Candidate ID as an Integer  
    public int getCandID()
    {
        return candID; 
    }

    //Accessor
    //EXPORT: Returns the Candidates Surname as a String  
    public String getCandSurname()
    {
        return candSurname;
    }

    //Accessor
    //EXPORT: Returns the Candidates Firstname as a String  
    public String getCandFirstname()
    {
        return candFirstname;
    }

    //Accessor
    //EXPORT: Returns the Elected result (Y/N) as a String  
    public String getElected()
    {
        return elected;
    }

    //Accessor
    //EXPORT: Returns the Historic Elected result (Y/N) as a String  
    public String getHistoricElected()
    {
        return historicElected;
    }
}