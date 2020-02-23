/**
 * ListNominee.java - DSA Assignment 2018 S2
 * Kevin Le - 19472960
 *
 * 16/10/2018
 */

import java.util.*;

public class ListNominee
{
    /**
     * IMPORTS:
     *      Array of National candidate objects
     *
     * ASSERTION:
     *      Prompts user for Filter choice, calls appropriate filter methods then sends to sort() to prompt user
     *      for sort to perform on filtered result.
     */
    public static void listNominees(NationalCandidate[] nationalCand)
    {
        Scanner sc = new Scanner(System.in);
        //Temporary Candidate array to store filtered result in.
        NationalCandidate[] tempCandidate = null;

        int choice;

        //Prompt user for filter type

        System.out.println("\nFilter By:");
        System.out.println("(1) -State");
        System.out.println("(2) -Party");
        System.out.println("(3) -Division");
        System.out.println("(4) -All");

        choice = FileIO.input(1, 4);

        //Add extra nextLine to absorb the \n

        //Call appropriate filter methods to filter candidate array.
        //After filtering, passes filtered array to be sorted.
        switch(choice)
        {
            case 1:
                tempCandidate = filterState(nationalCand);
                sortMenu(tempCandidate);
                break;
            case 2:
                tempCandidate = filterParty(nationalCand);
                sortMenu(tempCandidate);
                break;
            case 3:
                tempCandidate = filterDivision(nationalCand);
                sortMenu(tempCandidate);
                break;
            case 4:
                //All options chosen
                tempCandidate = filterState(nationalCand);
                sortMenu(tempCandidate);

                tempCandidate = filterParty(nationalCand);
                sortMenu(tempCandidate);

                tempCandidate = filterDivision(nationalCand);
                sortMenu(tempCandidate);
                break;
        }
    }

    /**
     * IMPORTS:
     *      Filtered array of National Candidate objects (Filtered by method above)
     *
     * ASSERTION:
     *      Prompts user for sort type, then performs the specified sort by calling sort function,
     *      passing in a number param for the sort function to use to call National Candidate, which passes
     *      the appropriate data depending on number. (Surname/State/Party/Division).
     */
    public static void sortMenu(NationalCandidate[] tempCandidate)
    {
        Scanner sc = new Scanner(System.in);
        int infoType, ii;

        //List Sorting Options

        System.out.println("\nSort/Order By:");
        System.out.println("(1) -Surname");
        System.out.println("(2) -State");
        System.out.println("(3) -Party");
        System.out.println("(4) -Division");
        System.out.println("(5) -All");

        infoType = FileIO.input(1, 5);

        //All sorts
        if(infoType == 5)
        {
            //For loop to make it go through all sort functions 1,2,3,4.
            for(ii = 1; ii < 5; ii++)
            {
                System.out.println("Performing sort option: " + ii);
                sort(tempCandidate, ii);

                System.out.println("\nSorted result:");
                printResult(tempCandidate);

                //Prompt if they want to write the current sort
                FileIO.promptWrite(tempCandidate);
            }
        }
        //Normal sort
        else
        {
            //Call insertion sort method, passing in menu choice of what to sort on and array to modify order of.
            sort(tempCandidate, infoType);

            System.out.println("\nSorted result:");
            printResult(tempCandidate);

            //Prompt if they want to write the sort
            FileIO.promptWrite(tempCandidate);
        }
    }

    /**
     * IMPORTS:
     *      Object array to print out
     *
     * ASSERTION:
     *      Used by sort method to print out an array. Specifically in this case, an array after sorting.
     */
    public static void printResult(Object[] array)
    {
        int jj;
        //Print Array.

        for(jj = 0; jj < array.length; jj++)
        {
            if(array[jj] != null)
            {
                System.out.println(array[jj].toString());
            }
        }
    }

    /**
     * IMPORTS:
     *      Array of NationalCandidate objects
     *      infoType parameter which decides on what data to sort on.
     *
     * ASSERTION:
     *      Insertion sort optimal due to being in place and simple to implement. Sorts based on
     *      the infoType, which retrieves the appropriate data to sort on from getCandInfo() in
     *      NationalCandidate. Modifies array, no need to return.
     *
     * REMARKS:
     *      This algorithm translated from Practical 2 of Data Structures and Algorithms COMP1002 -
     *      Insertion Sort.
     *
     *      Parts adapted from from java2s Sorting Objects using insertion sort : Sort « Collections « Java Tutorial
     *      http://www.java2s.com/Tutorial/Java/0140__Collections/SortingObjectsusinginsertionsort.htm
     *
     */
    public static void sort(NationalCandidate[] nationalCand, int infoType)
    {
        int ii, jj;

        for (jj = 1; jj < nationalCand.length; jj++)
        {
            //Have to check if not null due to array potentially having blank slots in the end.
            if(nationalCand[jj] != null)
            {
                NationalCandidate temp = nationalCand[jj];
                ii = jj;

                //compareTo compares strings lexigraphically
                //getCandInfo options: 1 = surname, 2 = state, 3 = party (full name), 4 = Division
                while (ii > 0 && nationalCand[ii - 1].getCandInfo(infoType).compareTo(temp.getCandInfo(infoType)) > 0)  // keeps sort stable - insert into sub array to left of nn
                {
                    nationalCand[ii] = nationalCand[ii - 1];
                    ii = ii - 1;
                }

                //Overwrite main array with sorted array
                nationalCand[ii] = temp;
            }
        }
    }

    /**
     * IMPORTS:
     *      Array of NationalCandidate objects
     *
     * EXPORTS:
     *      Array of NationalCandidate objects containing matches with the "search term"
     *
     * ASSERTION:
     *      Prompts user for state to filter by, iterates through Object array finding matches, if a
     *      match is found for the "search term", Print out the result, and assign current object to a
     *      temporary array (to be returned) and increment array index by 1. Continue until end of array.
     */
    public static NationalCandidate[] filterState(NationalCandidate[] nationalCand)
    {
        String stateName;
        int index = 0;

        Scanner sc = new Scanner(System.in);
        //Temporary candidate array for the filtered result to be placed in
        NationalCandidate[] tempCandidate = new NationalCandidate[nationalCand.length];

        //Prompt for stateName
        System.out.println("\nState Name? (Acronym Format):");
        stateName = FileIO.inputS();

        //Iterate through the entire array.
        for(int ii = 0; ii < nationalCand.length; ii++)
        {
            //If array isnt empty
            if(nationalCand[ii] != null)
            {
                //Get the current array index object's state, compare with user filter term.
                if(nationalCand[ii].getState().equals(stateName.toUpperCase()))
                {
                    //If result found print.
                    System.out.println(nationalCand[ii].toString());

                    //Store result into a temporary array for further sorting.
                    tempCandidate[index] = nationalCand[ii];
                    index++;
                }
            }
        }

        //Return array with search result
        return tempCandidate;
    }

    /**
     * IMPORTS:
     *      Array of NationalCandidate objects
     *
     * EXPORTS:
     *      Array of NationalCandidate objects containing matches with the "search term"
     *
     * ASSERTION:
     *      Prompts user for Party to filter by, iterates through Object array finding matches, if a
     *      match is found for the "search term", Print out the result, and assign current object to a
     *      temporary array (to be returned) and increment array index by 1. Continue until end of array.
     */
    //Method to filter party
    public static NationalCandidate[] filterParty(NationalCandidate[] nationalCand)
    {
        String partyName;
        int index = 0;

        Scanner sc = new Scanner(System.in);
        NationalCandidate[] tempCandidate = new NationalCandidate[nationalCand.length];

        System.out.println("\nParty Name? (Abbreviated form):");
        partyName = FileIO.inputS();

        for(int ii = 0; ii < nationalCand.length; ii++)
        {
            if(nationalCand[ii] != null)
            {
                if(nationalCand[ii].getPartyName().equals(partyName) || nationalCand[ii].getPartyShortname().equals(partyName.toUpperCase()))
                {
                    System.out.println(nationalCand[ii].toString());

                    tempCandidate[index] = nationalCand[ii];
                    index++;
                }
            }
        }

        return tempCandidate;
    }

    /**
     * IMPORTS:
     *     Array of NationalCandidate objects
     *
     * EXPORTS:
     *      Array of NationalCandidate objects containing matches with the "search term"
     *
     * ASSERTION:
     *      Prompts user for the identifying datatype of the division (ID/ Name)
     *      Prompts for input relative to the datatype chosen, then searches through array for matches,
     *      storing into temporary array to return.
     */
    public static NationalCandidate[] filterDivision(NationalCandidate[] nationalCand)
    {
        String divName = null;

        int index = 0;
        int divID = 0;
        int dataType = 0;

        Scanner sc = new Scanner(System.in);
        NationalCandidate[] tempCandidate = new NationalCandidate[nationalCand.length];

        //Division is stored in 2 ways in CSV row, ID and Name, prompts user for how they want to search division by.

        System.out.println("\nDivision ID Type?:");
        System.out.println("(1) -Name");
        System.out.println("(2) -ID");

        dataType = FileIO.input(1, 2);

        //If Name, prompt for name. If ID, prompt for ID
        if(dataType == 1)
        {
            System.out.println("\nEnter Name:");
            divName = FileIO.inputS();
        }
        else if(dataType == 2)
        {
            do
            {
                try
                {
                    System.out.println("\nEnter ID:");
                    divID = sc.nextInt();
                }
                catch(InputMismatchException e)
                {
                    String flush = sc.nextLine();
                    System.out.println("Selection must be an integer!");
                    divID = -1;
                }
            }
            while(divID < 0);
        }

        //Implement same search loop as previous filter methods.
        for(int ii = 0; ii < nationalCand.length; ii++)
        {
            if(nationalCand[ii] != null)
            {
                if(nationalCand[ii].getDivName().equals(divName) || nationalCand[ii].getDivID() == divID)
                {
                    System.out.println(nationalCand[ii].toString());

                    tempCandidate[index] = nationalCand[ii];
                    index++;
                }
            }
        }

        return tempCandidate;
    }
}
