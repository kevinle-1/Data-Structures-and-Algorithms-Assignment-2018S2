/*************************************************************************************************
 * NationalCandidate.java - DSA Assignment 2018 S2
 * National Candidate Object - Storing "HouseCandidatesDownload-20499.csv"
 * Kevin Le - 19472960
 * 
 * 16/10/2018
 * 
 * Default Constructor, Copy Constructor, Equals Method, Mutators Omitted due to not being required and used. 
 */

public class NationalCandidate extends Candidate
{
    //Store the entire CSV row into a NationalCandidate object to allow for individual accessing of data. 

    //Division Information
    private int divID; 
    private String divName;

    /**
     * IMPORT: 
     *      Required Details to create NationalCandidate object based on "HouseCandidatesDownload-20499.csv" from AEC 
     * 
     * ASSERTION:
     *      Constructs a new NationalCandidate Object.
     */
    public NationalCandidate(String inState, int inDivID, String inDivName, String inPartyShortname, String inPartyName, int inCandID, 
                             String inCandSurname, String inCandFirstName, String inElected, String inHistoricElected)
    {
        super(inState, inPartyShortname, inPartyName, inCandID, inCandSurname, inCandFirstName, inElected, inHistoricElected); 

        divID = inDivID; 
        divName = inDivName; 
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
        return(getState() + ", " + getCandFirstname() + " " + getCandSurname() + ", " + getPartyName() + "," + getPartyShortname()  + " - " + divName + "," + divID);
    }
    
    /**
     * IMPORTS: 
     *      infoType parameter. Number identifies menu choice made by user from calling function. 
     * 
     * EXPORTS:
     *      Returns required field data. 1 = Surname, 2 = State, 3 = Party Name, 4 = Division Name 
     * 
     * ASSERTION:
     *      For implementation with insertion sort based on users option on what to sort by. 
     *      Call this method with appropriate param to get desired return (only returns string)
     *      Used by sort to reduce method calls needed.
     *      getCandInfo options: 1 = surname, 2 = state, 3 = party (full name), 4 = Division
     */
    public String getCandInfo(int infoType)
    {
        String retVal = null; 
        //infoType integer determines what is returned.
        if(infoType == 1)
        {
            retVal = getCandSurname();
        }
        else if(infoType == 2)
        {
            retVal = getState();
        }
        else if(infoType == 3)
        {
            retVal = getPartyName();
        }
        else if(infoType == 4)
        {
            retVal = getDivName();
        }
        else
        {
            System.out.println("Invalid Parameter Sent to Sort.");
        }

        return retVal; 
    }

    //Accessor
    //EXPORT: Returns the division ID as an Integer
    public int getDivID()
    {
        return divID; 
    }

    //Accessor
    //EXPORT: Returns the division name as a String
    public String getDivName()
    {
        return divName; 
    }
}