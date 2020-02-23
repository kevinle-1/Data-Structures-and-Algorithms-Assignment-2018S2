/**
 * Election.java - DSA Assignment 2018 S2
 * 
 * MAIN METHOD
 * 
 * Kevin Le - 19472960
 * 
 * 16/10/2018
 */

import java.util.*;

public class Election
{
    /**
     * Main method to start program. Read in files first before displaying menu. 
     */
    public static void main(String[] args)
    {
        //Read in files
        System.out.println("Reading in files...");
        FileIO.readIn();
    }

    /**
     * IMPORTS: 
     *      Array of National Candidate Objects
     *      Divisions object 
     *      String array of locations 
     * 
     * ASSERTION:
     *      After files have been read in, menu recieves the data structures holding the information 
     *      required for each method. (List Nominees, Nominee Search, List by Margin, Itinerary By Margin)
     *      Prompts user for desired action calling appropriate class, passing in its required info. 
     */
    public static void menu(NationalCandidate[] nationalCand, Divisions stateDivisions, String[] locations)
    {
        Scanner sc = new Scanner(System.in); 
        DSALinkedList<Margin> margins = null; 

        int selection = 1, menu = 1;
        boolean read = false; 
    
        //Begin infinite loop, program will only end if exit condition of != 1 is set. 
        do
        {
            System.out.println("\n(1) -List Nominees");
            System.out.println("(2) -Nominee Search");
            System.out.println("(3) -List by Margin");
            System.out.println("(4) -Itinerary by Margin");
            System.out.println("(0) -Quit");
            System.out.println("Choice:");
    
            selection = FileIO.input(0, 4);

            switch (selection) 
            {
                case 1:
                    ListNominee.listNominees(nationalCand);
                    break;
                case 2:
                    NomineeSearch.nomineeSearch(nationalCand);
                    break;
                case 3:
                    margins = ListMargin.listMargins(stateDivisions);
                    read = true; 
                    break;
                case 4:
                    Itinerary.createGraph(margins, locations, read); 
                    break;
                case 0:
                    System.out.println("Exiting.");
                    menu = 0; 
                    break;
                default:
                    break;
            }
        }
        while(menu == 1);
    }
}