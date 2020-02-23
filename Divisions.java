/****************************************************************
 * Divisions.java - DSA Assignment 2018 S2
 * Divisions Object - Storing HouseStateFirstPrefsByPollingPlace For 8 States/Territories
 * Kevin Le - 19472960
 * 
 * 16/10/2018
 * 
 * Default Constructor, Copy Constructor, Equals Method, Mutators Omitted due to not being required and used. 
 * 
 * This object class is for the management of the 8 state/ candidate files. It aggregates the divisions into
 * objects, with each division containing a linked list of Candidates. 
 */

public class Divisions
{
    private DSALinkedList<Division> divisions;

    /**
     * Alternate Constructor
     * 
     * ASSERTION:
     *      Creates a new Divisions Object. Instantiates a linked list of Division Objects. 
     */
    public Divisions()
    {
        //Divisions list of objects to store division objects
        divisions = new DSALinkedList<Division>(); 
    }
                    
    /**
     * IMPORTS:
     *      All the elements in the CSV row, extracted by FileIO.java
     * 
     * ASSERTION:
     *      Creates a new Candidate object. Finding the division to add it to. If the Linked List of divisions is found to
     *      already contain the division, add the candidate to that division. If no such division exists. Create a new division
     *      first before adding candidate. 
     */
    public void addCandidate(int inDivID, String inDivName, String inState, String inPartyShortname, String inPartyName, int inCandID, 
                             String inCandSurname, String inCandFirstName, String inElected, String inHistoricElected, int inPollingPlaceID, 
                             String inPollingPlace, int inBallotPosition, int inVotes, double inSwing)
    {
        Division div; 

        //If candidates division already exists, add candidate to that division. 
        if((div = getDivision(inDivID)) != null)
        {
            div.addStateCandidate(inState, inPartyShortname, inPartyName, inCandID, inCandSurname, inCandFirstName, 
                                  inElected, inHistoricElected, inPollingPlaceID, inPollingPlace, inBallotPosition, inVotes, inSwing);
        } 
        //Division does not exist - Create a new div! 
        else
        {
            //Create a new Division, adding candidate in. 
            Division newDiv = new Division(inState, inDivName, inDivID);
            newDiv.addStateCandidate(inState, inPartyShortname, inPartyName, inCandID, inCandSurname, inCandFirstName, 
                                     inElected, inHistoricElected, inPollingPlaceID, inPollingPlace, inBallotPosition, inVotes, inSwing);

            divisions.insertFirst(newDiv); //Add new division to the divisions list. 
        }
    }


    /**
     * IMPORTS: 
     *      Numerical Division ID
     * 
     * EXPORTS: 
     *      Division object if successfully found. Null if no result. 
     * 
     * ASSERTION: 
     *      Searches through linked list of divisions to find a matching division for the divID.
     */ 
    public Division getDivision(int inDivID)
    {
        Division retVal = null; 

        //For each division in the linked list "divisions"
        for(Division current : divisions)
        {
            //Check if its DivID matches with the input ID
            if(current.getDivID() == inDivID)
            {
                //If it does, make the current Division Object the return value. 
                retVal = current;
            }
        }

        return retVal; 
    }

    /**
     * EXPORTS:
     *      Integer representing the number of divisions in the linked list
     * 
     * ASSERTION: 
     *      Counts the number of Division objects in the linked list "divisions". 
     */
    public int getDivisionCount()
    {
        int count = 0; 

        //For each division that exists in the linked list, increment count by 1
        for(Division current : divisions)
        {   
            count++;
        }

        return count; 
    }

    //Accessor
    //EXPORT: Returns the divisions linked list
    public DSALinkedList<Division> getDivisionList()
    {
        return divisions; 
    }

    //Accessor
    //EXPORT: Returns the linked list of candidates
    public DSALinkedList<StateCandidate> getStateCandidates()
    {
        return getStateCandidates(); 
    }

    /**
     * ASSERTION:
     *      Prints all the details of the divisions located in the linked list. 
     */
    public void printDivisions()
    {
        //For each division 
        for(Division i : divisions)
        {
            //Use the Division.toString() method to print out its contents.
            System.out.println(i.toString()); 
        }
    }

    /**
     * ASSERTION: 
     *      Prints all the details of the candidates linked list, located in each Division object in the divisions linked list.
     */
    public void printCandidates()
    {
        DSALinkedList<StateCandidate> candList; 

        //For each division 
        for(Division i : divisions)
        {
            candList = i.getStateCandidates(); 

            //For each candidate in that division
            for(StateCandidate j : candList)
            {
                //Print out the candidates Information. 
                System.out.println(j.toString());
            }
        }
    }

    /**
     * Class Interface to access margins. 
     * 
     * IMPORTS: 
     *      Party name, party to find margins for 
     *      Custom Margin parameter, 1 = Custom Margins. Modified by menu in calling function. 
     *      upperMargin margin used if custom margins selected. 
     *      lowerMargin margin used if custom margins selected. 
     * 
     * EXPORTS:
     *      Linked list of Margin objects. 
     * 
     * ASSERTION:
     *      For each Division in the "divisions" linked list, iterate through the Division objects candidates linked list
     *      summing up the votes for the parties matching the search term. If the search term doesnt match, add vote count
     *      to votes against. Calculate margin for that division, saving to Margins linked list if it is considered significant
     *      Once entire divisions linked list is searched through. Return Margin linked list. 
     */
    public DSALinkedList<Margin> getMargins(String inPartyName, int customMargin, int upperMargin, int lowerMargin)
    {
        //Linked List for StateCandidate Objects to be assigned to. 
        DSALinkedList<StateCandidate> candList; 
        //Linked list of Marginal Seats to be added to if party has a marginal seat.
        DSALinkedList<Margin> marginalSeats = new DSALinkedList<Margin>(); 
        Margin seat; 

        int votesFor = 0, votesAgainst = 0; 
        double margin; 

        //For each division 
        for(Division i : divisions)
        {
            //Get the linked list of candidates
            candList = i.getStateCandidates(); 

            //For each candidate in the division 
            for(StateCandidate j : candList)
            {
                //If the partyname to search for marginal seats matches the current candidate in the current division
                if(j.getPartyName().equals(inPartyName) || j.getPartyShortname().equals(inPartyName))
                {
                    //Count it as votes for the party
                    votesFor += j.getVotes(); 
                }
                else
                {
                    //Count it as votes against the party 
                    votesAgainst += j.getVotes(); 
                }
            }

            //Calculate margins for each party after votes for and against have been summed up 
            margin = (((double)votesFor/((double)votesFor + (double)votesAgainst)) * 100 - 50); 

            //1 specifies use of custom margins
            if(customMargin == 1)
            {
                if((margin > lowerMargin && margin < upperMargin)) //Then it is a marginal seat!
                {
                    //Create new Margin object for that division inside the upper and lower margins specified 
                    //Margin object Constructor takes State, Division Name, Margin (Real), VotesFor (Integer), and VotesAgainst(Integer)
                    seat = new Margin(i.getState(), i.getDivName(), i.getDivID(), margin, votesFor, votesAgainst);

                    //Insert into linked list of marginal divisions.
                    marginalSeats.insertFirst(seat);
                }

            }
            //Use default margins
            else
            {
                if((margin > -6 && margin < 6)) 
                {
                    seat = new Margin(i.getState(), i.getDivName(), i.getDivID(), margin, votesFor, votesAgainst);
                    marginalSeats.insertFirst(seat);
                }
            }

            //DEBUG PRINT
            /*System.out.println("Total votes For " + inPartyName + ": " + votesFor);
            System.out.println("Total votes Against " + inPartyName + ": " + votesAgainst);
            System.out.println("Margin: " + (((double)votesFor/(double)votesAgainst)*100 - 50));*/

            votesFor = 0; 
            votesAgainst = 0; 
        }

        //Returns linked list of marginal seats for x party
        return marginalSeats; 
    }

    /****************************************************************
     * Division Private inner class. Provides methods to the public interface Divisions.
     * 
     * Stores Linked List of candidate objects.
     */
    private class Division
    {
        private String state; 
        private int divID; 
        private String divName; 
        private DSALinkedList<StateCandidate> stateCandidates;

        /**
         * Alternate Constructor
         * 
         * IMPORTS:
         *      State in the acronym form, as a String
         *      Division Name as a String
         *      Division ID as an Integer
         * 
         * ASSERTION:
         *      Constructor to create a new Division Object. Each division contains a linked list of candidates
         *      (Makes sense for a division to have candidates)
         */
        public Division(String inState, String inDivName, int inDivID)
        {
            state = inState; 
            divID = inDivID; 
            divName = inDivName;

            stateCandidates = new DSALinkedList<StateCandidate>(); 
        }

        /**
         * EXPORTS: 
         *      String 
         * 
         * ASSERTION:
         *      Returns a string representation of the Division Object 
         */
        public String toString()
        {
            return(state + "," + divID + "," + divName); 
        }

        //Accessor
        //EXPORT: Returns the Linked List of StateCandidate objects
        public DSALinkedList<StateCandidate> getStateCandidates()
        {
            return stateCandidates; 
        }

        //Accessor
        //EXPORT: Returns the state as a String
        public String getState()
        {
            return state; 
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

        /**
         * IMPORTS:
         *      Required fields for the StateCandidate object constructor 
         * 
         * ASSERTION:   
         *      Creates a new stateCandidate object with the imported parametres. Then inserts the object into the Linked
         *      List of candidates contained in the Division object. Calling function responsible for passing correct info. 
         */
        public void addStateCandidate(String inState, String inPartyShortname, String inPartyName, int inCandID, String inCandSurname, 
                                      String inCandFirstName, String inElected, String inHistoricElected, int inPollingPlaceID, 
                                      String inPollingPlace, int inBallotPosition, int inVotes, double inSwing)
        {
            //Create new stateCandidate Object with all the class fields. 
            StateCandidate stateCandidate = new StateCandidate(inState, inPartyShortname, inPartyName, inCandID, inCandSurname, 
                                                               inCandFirstName, inElected, inHistoricElected, inPollingPlaceID, 
                                                               inPollingPlace, inBallotPosition, inVotes, inSwing); 

            //Insert stateCandidate object into linked list of candidates contained in the current division object. 
            stateCandidates.insertFirst(stateCandidate); 
        }
    }


    /****************************************************************
     * StateCandidate Private class. 
     * 
     * Stores the State Level candidate information in the object. Inherits from Candidate Class
     */
    private class StateCandidate extends Candidate
    {
        private int pollingPlaceID;
        private String pollingPlace; 
        private int ballotPosition;
        private int votes; 
        private double swing; 
    
        /**
         * Alternate Constructor 
         * 
         * IMPORTS:
         *      Via CSV: StateAb,DivisionID,DivisionNm,PollingPlaceID,PollingPlace,CandidateID,Surname,GivenNm,
         *               BallotPosition,Elected,HistoricElected,PartyAb,PartyNm,OrdinaryVotes,Swing
         * 
         * ASSERTION:
         *      Creates a new StateCandidate Object using all the information in the CSV column. 
         */
        public StateCandidate(String inState, String inPartyShortname, String inPartyName, int inCandID, String inCandSurname, String inCandFirstName, 
                            String inElected, String inHistoricElected, int inPollingPlaceID, String inPollingPlace, int inBallotPosition, int inVotes, double inSwing)
        {
            //Use supers constructor
            super(inState, inPartyShortname, inPartyName, inCandID, inCandSurname, inCandFirstName, inElected, inHistoricElected);
    
            pollingPlaceID = inPollingPlaceID;
            pollingPlace = inPollingPlace;
            ballotPosition = inBallotPosition; 

            votes = inVotes; 
            swing = inSwing; 
        }

        /**
         * EXPORTS: 
         *      String 
         * 
         * ASSERTION:
         *      Returns a string representation of the StateCandidate Object 
         */
        public String toString()
        {
            return(super.toString() + "," + pollingPlaceID + "," + pollingPlace + "," + ballotPosition + "," + votes + "," + swing);
        }
    
        //Accessor
        //EXPORT: Returns the pollingPlaceID as an int
        public int getPollingPlaceID()
        {
            return pollingPlaceID; 
        }

        //Accessor
        //EXPORT: Returns the pollingPlace as a String
        public String getPollingPlace()
        {
            return pollingPlace;
        }
    
        //Accessor
        //EXPORT: Returns the ballotPosition as an int
        public int getBallotPosition()
        {
            return ballotPosition; 
        }
    
        //Accessor
        //EXPORT: Returns the number of votes as an int
        public int getVotes()
        {
            return votes; 
        }
    
        //Accessor
        //EXPORT: Returns the swing as a Real Number
        public double getSwing()
        {
            return swing; 
        }

        //No Mutators (Setters) included as function had no requirement for field to be modified after creation of object.
    }
}