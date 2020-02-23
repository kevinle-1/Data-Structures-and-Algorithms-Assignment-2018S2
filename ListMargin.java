/**
 * ListMargin.java - DSA Assignment 2018 S2
 * Kevin Le - 19472960
 * 
 * 16/10/2018
 */

import java.util.*;

public class ListMargin 
{
    /**
     * IMPORTS:
     *      Division object, containing a linked list of Candidates. 
     * 
     * EXPORTS:
     *      Linked List of Margin objects, containing Division and Vote information for that particular division
     *      according to party searched by. 
     * 
     * ASSERTION:
     *      Menu interface for utilising getMargin method in Division class to return a linked list of Marginal seats for
     *      selected party. 
     */
    public static DSALinkedList<Margin> listMargins(Divisions stateDivisions)
    {
        Scanner sc = new Scanner(System.in); 

        //Default margins
        int upper = 6, lower = -6; 
        int choice; 

        String divName, partyName;
        DSALinkedList<Margin> margins; 
        
        //Prompt user for party name
        System.out.println("\nEnter Party to List Margins for:");
        partyName = FileIO.inputS().toUpperCase(); 

        //Prompt user if they would like to use custom margins. If not use default.

        System.out.println("\nCustom Margins?:");
        System.out.println("(1) -Yes");
        System.out.println("(2) -No");

        choice = FileIO.input(1, 2);

        //If user wants to use custom margins prompt for new upper/lower values. 
        if(choice == 1)
        {
            try
            {
                System.out.println("\nUpper Threshold?:");
                upper = sc.nextInt(); 
    
                System.out.println("\nLower Threshold? (Negative Value):");
                lower = sc.nextInt();
            }
            catch(InputMismatchException e)
            {
                System.out.println("Selection must be an integer!");
                //Use Default Margins if incorrect input 
                choice = 2;
            }
        }

        //Calls getMargins in Divisions object which returns a linked list populated with marginal seats for x party.
        //Choice parameter is to let getMargins know if custom margins are used or default. 
        margins = stateDivisions.getMargins(partyName, choice, upper, lower); 

        //Call output margins to print out margins to screen, and prompt for writing. 
        outputMargins(margins, partyName);

        return margins; 
    }

    /**
     * IMPORTS:
     *      Linked List of Margin Objects (Which hold data about Division and Votecounts)
     *      Party name entered by user in listMargins()
     * 
     * ASSERTION:
     *      Print out all the Margin objects (using print method in Margin). Prompt user if they'd like to write to a file. 
     */
    public static void outputMargins(DSALinkedList<Margin> margins, String partyName)
    {
        Scanner sc = new Scanner(System.in); 
        String[] tempArr; 

        int index = 0, size = 0, choice = 0;

        //For each Margin object in margins linked list. Use printOut method to display. Count linked list size. 
        System.out.println("Marginal Seats:");
        for(Margin i : margins)
        {
            i.printOut(); 
            size++;
        }

        //If for each doesn't iterate through the linked lists, no margins for that party were found. 
        if(size == 0)
        {
            System.out.println("\nNo Marginal seats for: " + partyName);
        }
        else
        {
            //If there were margins in the linked list, prompt for writing. 

            System.out.println("\nWrite Report to File?");
            System.out.println("(1) -Yes");
            System.out.println("(2) -No");
    
            choice = FileIO.input(1, 2);

            //Store margins linked list into an array of Strings, for compatiability with FileIO.writeTo()
            // size calculated during print for each loop above. 
            if(choice == 1)
            {
                tempArr = new String[size];
                for(Margin j : margins)
                {
                    tempArr[index] = j.toString(); 
                    index++;
                }
                FileIO.writeTo(tempArr);
            }
        }
    }
}