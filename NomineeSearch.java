/**
 * NomineeSearch.java - DSA Assignment 2018 S2
 * Kevin Le - 19472960
 * 
 * 16/10/2018
 */

import java.util.*;

public class NomineeSearch
{
    /**
     * IMPORTS: 
     *      Array of NationalCandidate objects. 
     * 
     * ASSERTION: 
     *      Creates 2 temporary NationalCandidate object arrays to store search results. Prompts user for 
     *      substring to search by, converting search term to uppercase to match CSV file with toUpperCase() 
     *      and using the startsWith() method in java to search for string in for loop iterating through entire array.
     * 
     *      If a match is found, result is printed and stored to an array to apply users filtering choice. Filtered
     *      by methods located in ListNominee, saving filtered method into secondary temp array, in case writing is chosen. 
     */
    public static void nomineeSearch(NationalCandidate[] nationalCand)
    {
        Scanner sc = new Scanner(System.in);
        int ii;
        int index = 0;  
        String search; 

        //Temporary candidate object arrays to store search result and filtered result. 
        NationalCandidate[] temporaryCandidate = new NationalCandidate[nationalCand.length];

        System.out.println("\nEnter Nominee Last Name (Any size):\n");
        search = FileIO.inputS();

        //Search through array for the results. 
        for(ii = 0; ii < nationalCand.length; ii++)
        {
            if(nationalCand[ii] != null)
            {
                //If candidate surname in array contains the search term
                if(nationalCand[ii].getCandSurname().startsWith(search.toUpperCase()))
                {
                    //Print the current array object 
                    System.out.println(nationalCand[ii].toString());   
                    //Store into temporary candidate object 
                    temporaryCandidate[index] = nationalCand[ii];
                    //Increment array index (also used for number of search results)
                    index++;
                }
            }
        }

        promptFilter(temporaryCandidate, index);
    }

    public static void promptFilter(NationalCandidate[] temporaryCandidate, int index)
    {
        int choice; 
        NationalCandidate[] tempCandidates = new NationalCandidate[temporaryCandidate.length];

        //If elements HAVE been found begin filter/ write 
        if(!(index == 0))
        {
            //Print search results
            System.out.println("Results found: " + index);

            System.out.println("\nFilter By:");
            System.out.println("(1) -State");
            System.out.println("(2) -Party");
            System.out.println("(3) -All");

            choice = FileIO.input(1, 3);
    
            //Chosen filter applied and assigned to another temp array. 
            if(choice == 1)
            {
                tempCandidates = ListNominee.filterState(temporaryCandidate);
                FileIO.promptWrite(tempCandidates);
            }
            else if(choice == 2)
            {
                tempCandidates = ListNominee.filterParty(temporaryCandidate);
                FileIO.promptWrite(tempCandidates);
            }
            //Perform both Filters
            else if(choice == 3)
            {
                tempCandidates = ListNominee.filterState(temporaryCandidate);
                FileIO.promptWrite(tempCandidates);

                tempCandidates = ListNominee.filterParty(temporaryCandidate);
                FileIO.promptWrite(tempCandidates);
            }
        }
        //0 results
        else
        {
            System.out.println("No Candidate Found.");
        }
    }
}