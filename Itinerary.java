/**
 * Itinerary.java - DSA Assignment 2018 S2
 * Kevin Le - 19472960
 * 
 * 23/10/2018
 */
import java.util.*;

public class Itinerary 
{
    /**
     * IMPORTS:
     *      Linked list of margins (Margin objects)
     *      String array with all location data from ElectDistx.x.csv and AirportDistx.x.csv
     *      Boolean indicating if margins have been read in yet. 
     * 
     * ASSERTION:
     *      Declares a new graph to be filled, checks if marginal seats have been calculated yet. If true
     *      runs the Itinerary creation. 
     */
    public static void createGraph(DSALinkedList<Margin> margins, String[] locations, boolean read)
    {
        DSAGraph route = new DSAGraph();

        if(read == false || margins.isEmpty())
        {
            System.out.println("Margins have not been calculated yet!");
        }
        else
        {
            route = addVertices(margins, locations);
            path(route, margins);
        }
    }

    /**
     * IMPORTS: 
     *      Linked list of marginal divisions
     *      String array containing all the lines of 
     *      
     * ASSERTION:
     *      Creates the graph for the marginal divisions, adding the airports and marginal divisions first, then 
     *      adding the edges for those vertices. 
     */
    public static DSAGraph addVertices(DSALinkedList<Margin> margins, String[] locations)
    {
        int ii, jj; 
        String prevState = margins.peekFirst().getState();
        DSAGraph route = new DSAGraph(); 

        //For each margin
        for(Margin current : margins)
        { 
            //If both states contain airports
            if(getAirport(prevState) != null && getAirport(current.getState()) != null)
            {
                //Add both airports as vertices to the graph
                route.addVertex(getAirport(prevState), getAirport(prevState));
                route.addVertex(getAirport(current.getState()), getAirport(current.getState()));

                //At the end of the array (hence 2000 start - the airport locations are appended at the end of the array)
                for(ii = 2000; ii < locations.length; ii++)
                {
                    //If the current line contains the states airport, and the destination airport matches the other states airport 
                    if(FileIO.getS(locations[ii], 1).equals(getAirport(prevState)) && FileIO.getS(locations[ii], 5).equals(getAirport(current.getState())))
                    {
                        //Add the edge/link for the departure and arrival airport. 
                        route.addEdge(route.getVertex(getAirport(prevState)), route.getVertex(getAirport(current.getState())), 
                                    convertToMin(FileIO.getS(locations[ii], 9)));
                    }
                }
            }

            //Add the current marginal division as a vertex to the graph
            route.addVertex(current.getDivName(), current);

            //Set the new state to the current state. (Used to find if the division exists in a new state.)
            prevState = current.getState();
        }

        //Add edges for each margin 
        for(Margin k : margins)
        {
            for(jj = 0; jj < locations.length; jj++)
            {
                //If the current line matches the divisions name. 
                if(FileIO.getS(locations[jj], 1).equals(k.getDivName()))
                {
                    //Check if vertex going to exists in graph before adding edge/link. 
                    if(route.exists(FileIO.getS(locations[jj], 5)))
                    {
                        try 
                        {
                            //Add edge for current vertex to the destination vertex. 
                            route.addEdge(route.getVertex(k.getDivName()), route.getVertex(FileIO.getS(locations[jj], 5)), 
                            FileIO.getI(locations[jj], 9));      
                        } 
                        //Invalid dataype found
                        catch (NumberFormatException e) 
                        {
                            System.out.println("Invalid Line Found.");
                        }

                        //Debug println. 
                        //System.out.println(k.getDivName() + " -> " + FileIO.getS(locations[jj], 5) + " | " + FileIO.getI(locations[jj], 9));
                    }
                }
            }
        }

        //Return the graph
        return route; 
    }

    /**
     * IMPORTS:
     *      Graph with all nodes to search through. 
     *      Linked list of all marginal seats
     * 
     * ASSERTION:
     *      Calls traverse in DSAGraph class to perform a Breadth First Search, returning the result in a string array. 
     *      For loop iterates through String array return summing up times and outputs in a readable format. 
     */
    public static void path(DSAGraph route, DSALinkedList<Margin> margins)
    {
        String[] locations; 
        int ii, index = 0, minutes; 
        double hours, totalHours = 0.0; 
        String[] toWrite;

        locations = route.traverse(route.getVertex(margins.peekFirst().getDivName()));

        /********************
         * 
         * Edges appear to be disconnected so DFS cannot traverse the entire graph.
         * 
         *************************/

        toWrite = new String[locations.length]; 

        for(ii = 0; ii < locations.length; ii += 2)
        {
            if(locations[ii] != null && locations[ii+1] != null && locations[ii+2] != null)
            {
                minutes = Integer.parseInt(locations[ii+1]);
                hours = ((double)minutes/3600);
                System.out.println(locations[ii] + " to " + locations[ii+2] + " - " + String.format("%.2g", hours) + " Hours");

                toWrite[index] = (locations[ii] + " to " + locations[ii+2] + " - " + String.format("%.2g", hours) + " Hours");

                index++;
                totalHours += (hours + 3.0); 
            }
        }

        System.out.println("\nTotal Hours: " + String.format("%.2g", totalHours));

        //Prompt if user wants to write. 
        FileIO.promptWrite(toWrite);
    }

    /**
     * IMPORTS:
     *      Time as a string in the format HH:MM:SS
     * 
     * EXPORTS:
     *      Time as an Integer in Seconds
     * 
     * ASSERTION:
     *      Converts the time from the Airport CSV to the same unit as the times in the Electorate Locations
     */
    public static int convertToMin(String time)
    {
        int hour, minute, result; 
        String[] timeMin = time.split(":");

        hour = Integer.parseInt(timeMin[0]);
        minute = Integer.parseInt(timeMin[1]);

        result = (hour * 3600) + (minute * 60); 

        return result; 
    }

    /**
     * IMPORTS:
     *      String state name (WA format)
     * 
     * ASSERTION:
     *      Returns the airport for the particular state. If Airport exists in the Airport File
     *      Hardcoded as Airports don't change often. 
     */
    public static String getAirport(String state)
    {
        String airport = null; 
        
        if(state.equals("WA"))
        {
            airport = "Perth Airport";
        }
        if(state.equals("SA"))
        {
            airport = "Adelaide Airport";
        }
        if(state.equals("VIC"))
        {
            airport = "Melbourne Airport";
        }
        if(state.equals("TAS"))
        {
            airport = "Hobart Airport";
        }
        if(state.equals("NSW"))
        {
            airport = "Sydney Airport";
        }
        if(state.equals("NT"))
        {
            airport = "Darwin Airport";
        }

        return airport; 
    }
}