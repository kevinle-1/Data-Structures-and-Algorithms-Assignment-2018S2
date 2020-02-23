/**
 * FileIO.java - DSA Assignment 2018 S2
 * Kevin Le - 19472960
 * 
 * 16/10/2018
 */
import java.util.*;
import java.io.*;

public class FileIO
{
    /**
     * IMPORTS: 
     *      N/A
     * 
     * ASSERTION:
     *      Method used to read in all the files, sets variables to be assigned to after the read functions are called and returned
     *      Sets the large list of state files to a string array for one compact import. Once all files have been read in
     *      Calls menu for program to start. 
     */
    public static void readIn()
    {
        Scanner sc = new Scanner(System.in);
        String fileCandList, fileLocationList;

        NationalCandidate[] nationalCand; 
        Divisions stateDivisions; 
        String[] locations = null; 

        //Store all the file names into appropriate variables
        fileCandList = "HouseCandidatesDownload-20499.csv";

        //House State First Prefs By Polling Place
        String[] stateFileNames = {"HouseStateFirstPrefsByPollingPlaceDownload-20499-NSW.csv", 
                                   "HouseStateFirstPrefsByPollingPlaceDownload-20499-VIC.csv", 
                                   "HouseStateFirstPrefsByPollingPlaceDownload-20499-QLD.csv", 
                                   "HouseStateFirstPrefsByPollingPlaceDownload-20499-WA.csv", 
                                   "HouseStateFirstPrefsByPollingPlaceDownload-20499-SA.csv", 
                                   "HouseStateFirstPrefsByPollingPlaceDownload-20499-TAS.csv", 
                                   "HouseStateFirstPrefsByPollingPlaceDownload-20499-ACT.csv", 
                                   "HouseStateFirstPrefsByPollingPlaceDownload-20499-NT.csv"};

        //Read the "HouseCandidatesDownload" file. Returning a nationalCandidate object array
        nationalCand = readCandidateFile(fileCandList);

        //Read all the "HouseStateFirstPrefsByPollingPlace" files. Returns a Divisions object
        stateDivisions = readStateCandidateFile(stateFileNames);

        //Read all the Airport Dist and Electorate Location files, returning all lines stored in a String array
        locations = readLocations();

        //Call menu passing in all the data stored

        System.out.println("Done."); 
        Election.menu(nationalCand, stateDivisions, locations); 
    }

    /**
     * IMPORTS:
     *      National candidate file filename
     * 
     * EXPORTS:
     *      Array of NationalCandidate objects
     * 
     * ASSERTION:
     *      Opens and reads the file of all candidates nationally. Reads twice, first run through the file, numLines are counted, second line
     *      array is declared and set to size of num lines, then creates NationalCandidate objects by splitting required data, 
     *      storing into an Object array. Returning it.
     */
    public static NationalCandidate[] readCandidateFile(String fileName)
    {
        int index = 0, numLines = 0, ii;
        String line; 
        NationalCandidate[] nationalCand = null;

        FileInputStream stream = null;
        InputStreamReader inReader;
        BufferedReader bufReader;

        //First loop counts number of lines, second loop fills array with objects.
        for(ii = 0; ii < 2; ii++)
        {

            //Initialize line when second loop is run. 
            if(ii == 1)
            {
                nationalCand = new NationalCandidate[numLines];
            }

            try 
            {
                stream = new FileInputStream(fileName); //Open File
                inReader = new InputStreamReader(stream); //Create reader to read stream 
                bufReader = new BufferedReader(inReader); //Read stream one line at a time
                
                //Skips first 2 lines
                bufReader.readLine();
                bufReader.readLine();
    
                while((line = bufReader.readLine()) != null)
                {   
                    if(ii == 0)
                    {
                        numLines++;
                    }
                    else
                    {
                        nationalCand[index] = new NationalCandidate(getS(line, 0), getI(line, 1), getS(line, 2), getS(line, 3), getS(line, 4), 
                                                                    getI(line, 5), getS(line, 6), getS(line, 7), getS(line, 8), getS(line, 9));

                        index++;
                    }
                }
            }
            catch (IOException e) 
            {
                if(stream != null)
                {
                    try
                    {
                        stream.close();
                    }
                    catch (IOException ex2)
                    {
                        //Finished.
                    }
                }	
            }     
        }

        return nationalCand; 
    }

    /**
     * IMPORTS: 
     *      N/A
     * 
     * EXPORTS:
     *      String array with raw location data. (Of Electorate Locations and State Airports)
     * 
     * ASSERTION:
     *      Combines both arrays to keep export compact. As Airport data contains the same information as the Electorate locations
     *      Runs 4 times through files as data is relatively small. First 2 runs sum up number of lines. Second 2 runs read in the 
     *      Airport and then the Electorate locations into the same array. Then returning. 
     */
    public static String[] readLocations()
    {
        int index = 0, numLines = 0, ii;
        String line; 
        String[] fileNames = {"ElectDist1.1.csv", "AirportDist1.0.csv", 
                              "ElectDist1.1.csv", "AirportDist1.0.csv"};

        String[] locations = null; 

        FileInputStream stream = null;
        InputStreamReader inReader;
        BufferedReader bufReader;

        //Read both files counting number of lines first, then add all file data to String array.
        for(ii = 0; ii < 4; ii++)
        {
            //Number of lines for both files have been counted. 
            if(ii == 2)
            {
                locations = new String[numLines];
            }

            try 
            {
                stream = new FileInputStream(fileNames[ii]); //Open File
                inReader = new InputStreamReader(stream); //Create reader to read stream 
                bufReader = new BufferedReader(inReader); //Read stream one line at a time
                
                //Skips first line
                bufReader.readLine();
    
                while((line = bufReader.readLine()) != null)
                {   
                    if(ii == 0 || ii == 1)
                    {
                        numLines++;
                    }
                    else
                    {
                        locations[index] = line;
                        index++;
                    }
                }
            }
            catch (IOException e) 
            {
                if(stream != null)
                {
                    try
                    {
                        stream.close();
                    }
                    catch (IOException ex2)
                    {
                        //Finished.
                    }
                }	
            }     
        }

        return locations; 
    }

    /**
     * IMPORTS:
     *      String array with all the First Prefs by Polling place - 8 files. 
     * 
     * EXPORTS: 
     *      Divisions object 
     * 
     * ASSERTION: 
     *      Dealing with larger CSV files, suited to a linked list. >80,000 lines. Utilises a Division object public 
     *      method .addCandidate(). FileIO class only has to handle splitting the data, which is then sent to the method
     *      which is added to the Divisions objects linked list of Division. 
     */
    public static Divisions readStateCandidateFile(String[] stateFileNames)
    {
        int jj, kk, ll;
        String line; 

        FileInputStream stream = null; 
        InputStreamReader inReader;
        BufferedReader bufReader;

        Divisions stateDivisions = new Divisions(); 

        for(kk = 0; kk < stateFileNames.length; kk++)
        {
            try 
            {
                stream = new FileInputStream(stateFileNames[kk]);
                inReader = new InputStreamReader(stream);
                bufReader = new BufferedReader(inReader); 

                //Skips first 2 lines
                bufReader.readLine();
                bufReader.readLine();

                while((line = bufReader.readLine()) != null)
                {  
                    stateDivisions.addCandidate(getI(line, 1), getS(line, 2), getS(line, 0), getS(line, 11), getS(line, 12), 
                                                getI(line, 5), getS(line, 6), getS(line, 7), getS(line, 9), getS(line, 10), 
                                                getI(line, 3), getS(line, 4), getI(line, 8), getI(line, 13), getR(line, 14));
                }   
            }
            catch (IOException e) 
            {
                if (stream != null)
                {
                    try
                    {
                        stream.close();
                    }
                    catch (IOException ex2)
                    {
                        //Finished.
                    }
                }	
            }    
        }

        return stateDivisions; 
    }

    /**
     * IMPORTS: 
     *      Line to be split as String
     *      Index of line to be split on 
     * 
     * EXPORTS: 
     *      Returns the index of the line split as a String
     * 
     *  Regex retrieved from Rohit Jain' answer via Stack Overflow, "Splitting on comma outside quotes" 
     *  https://stackoverflow.com/questions/18893390/splitting-on-comma-outside-quotes
     *  (Accessed 16 Oct 2018).
     */

    public static String getS(String line, int num)
    {
        String[] lineArray;
		String string;

        //Force ignore of commas in quotes. 
		lineArray = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
		string = lineArray[num];

		return string;
    }

    /**
     * IMPORTS:
     *      Line to be split as String
     *      Index of line to be split on 
     * 
     * EXPORTS: 
     *      Returns the index of the line split as a Integer
     * 
     *  Regex retrieved from Rohit Jain' answer via Stack Overflow, "Splitting on comma outside quotes" 
     *  https://stackoverflow.com/questions/18893390/splitting-on-comma-outside-quotes
     *  (Accessed 16 Oct 2018).
     */
    public static int getI(String line, int num)
    {
        String[] lineArray;
        String string;
        int extractedInt;

        lineArray = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        string = lineArray[num];

        //Convert String -> Integer
        extractedInt = Integer.parseInt(string);

        return extractedInt;
    }

    /**
     * IMPORTS:
     *      Line to be split as String
     *      Index of line to be split on 
     * 
     * EXPORTS: 
     *      Returns the index of the line split as a double (Real)
     * 
     *  Regex retrieved from Rohit Jain' answer via Stack Overflow, "Splitting on comma outside quotes" 
     *  https://stackoverflow.com/questions/18893390/splitting-on-comma-outside-quotes
     *  (Accessed 16 Oct 2018).
     */
    public static double getR(String line, int num)
    {
        String[] lineArray;
        String string;
        double extractedReal;

        lineArray = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        string = lineArray[num];

        //Convert String -> Real
        extractedReal = Double.parseDouble(string);

        return extractedReal;
    }

    /**
     * IMPORTS:
     *      Array of objects, filtered and/or sorted by calling method. 
     * 
     * ASSERTION: 
     *      Prompts the user if they would like to write the sorted/filtered result to a file. If yes, calls
     *      writeTo, passing in array of objects. 
     */
    public static void promptWrite(Object[] array)
    {
        Scanner sc = new Scanner (System.in); 
        int choice = 0; 

        //Prompt user if they want to write the result of the filter and sort

        System.out.println("\nWrite Report to File?");
        System.out.println("(1) -Yes");
        System.out.println("(2) -No");

        choice = input(1, 2);

        //If yes, call FileIO writeTo function passing in array to be written
        if(choice == 1)
        {
            writeTo(array);
        }
    }

    /**
     * IMPORTS:
     *      Object array to be written
     * 
     * ASSERTION:
     *      Takes any object array (Requires that object having a toString method) and writes the
     *      toString return to the file specified by the user at the prompt. 
     */
    public static void writeTo(Object[] array)
    {
        Scanner sc = new Scanner (System.in);

        String fileName;
        int ii;

        FileOutputStream fileStrm = null; 
        PrintWriter pw; 

        System.out.println("\nWhat is the file name to write to?");
        fileName = sc.nextLine();

        try 
		{
            pw = new PrintWriter(new FileOutputStream(fileName, false));

            for(ii = 0; ii < array.length; ii++)
            {
                if(array[ii] != null)
                {
                    pw.println(array[ii].toString());
                }
            }

            pw.close();
        }
        catch (IOException e) 
		{
			if (fileStrm != null)
			{
				try 
				{
					fileStrm.close(); 
				} 
				catch (IOException ex2)
				{
					//Finished.
				}
			}
			System.out.println("Error writing to file");
		}
    }   

    /**
     * IMPORTS:
     *      Integer upper bound
     *      Integer lower bound
     * 
     * ASSERTION:
     *      Simple input method that can be called, passing in the max/min value that can be accepted, validating input. 
     *      Returns the input once valid. Allows user to retry.
     */
    public static int input(int lower, int upper)
    {
        Scanner sc = new Scanner(System.in);
        int choice; 

        do
        {
            try 
            {
                choice = sc.nextInt();
            } 
            catch(InputMismatchException e)
            {
                String flush = sc.nextLine();
                System.out.println("Selection must be an integer. Try again.");
                choice = upper + 10; 
            }
        }
        while(!(choice >= lower && choice <= upper));

        return choice; 
    }

    /**
     * IMPORTS:
     *      None
     * 
     * EXPORTS:
     *      String input. 
     * 
     * ASSERTION:
     *      String input method that can be called. validating input. 
     *      Returns input once valid. Allows user to retry.
     */
    public static String inputS()
    {
        Scanner sc = new Scanner(System.in);
        String choice; 

        try 
        {
            choice = sc.nextLine();
        } 
        catch(InputMismatchException e)
        {
            String flush = sc.nextLine();
            System.out.println("Selection must be a String!");
            choice = null; 
        }

        return choice; 
    }
}