/*
Write a program that reads words from a text file and displays all the words 
(duplicates allowed) in ascending alphabetical order. The text file is passed 
as a command-line argument.
 */
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class ExtraCredit2 {

    public static void main(String[] args) throws Exception {
        
         // Create a scanner for file name
        Scanner userInput = new Scanner(System.in);
         
        // Ask for user's input
        System.out.print("Enter file name: ");
        String filename = userInput.nextLine();
        
         // Create Scanner for file
        try (Scanner input = new Scanner(new File(filename)))
        {
            // Read datea from a file
            while (input.hasNext())
            {
                
                // Reads the data from file to a list
                List<String> s = readDataFromFile(filename);
                
                // Sort the list
                Collections.sort(s);
                
                // Display result
                displayList(s);
                
            }
        }
        catch (FileNotFoundException e)
	{
            System.out.println("File not found");
	    e.printStackTrace();
	}
	catch (Exception e)
        {
            System.out.println("unexpected error occured- terminating...");
        }
             
    }

   public static void displayList(List<String> s) 
    {

        assert s !=null;

        Iterator<String> i = s.iterator();
        while(i.hasNext())
        {
            String str = i.next();
            System.out.println(str);
        }
    }
	
    private static List<String> readDataFromFile(String fileName) throws FileNotFoundException 
    {
            assert fileName != null;
            List<String> words = new LinkedList<String>(); 
        try (Scanner input = new Scanner (new File (fileName)))
        {
            while(input.hasNext())
            {
                String s = input.next();
                words.add(s);
            }
        }
            return words;
    }
    
}
