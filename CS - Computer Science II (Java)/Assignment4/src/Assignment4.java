/*
Colleen Cousins
CS 2336.001
Due October 10, 2015
This program asks the user to enter a file name and displays the occurence of
each letter in the file.
*/

import java.util.*;     // Needed for Scanner
import java.io.*;       // Needed for file

public class Assignment4  
{
    
    public static void main(String[] args)throws Exception // IO/exception
    {
        // Create a scanner for file name
        Scanner userInput = new Scanner(System.in);
        
        // Array to hold number of occurences
        int[] count = new int[26];
        
        
        // Ask for user's input
        System.out.print("Enter file name: ");      // C:\Users\colle\Documents\Lincoln.txt
        String filename = userInput.nextLine();
        
        // Create Scanner for file
        try (Scanner input = new Scanner(new File(filename)))
        {
            // Read datea from a file
            while (input.hasNext())
            {
                // Holds the string of text per line in text file
                String textLine = input.nextLine();
                
                // counts the number of occurences
                for (int x = 0; x < textLine.length(); x++)
                {
                  if (Character.isLetter(textLine.charAt(x)))
                    count[Character.toUpperCase(textLine.charAt(x)) - 'A']++;
                }
            }
        }
        // Method displays occurences 
        displayCounts(count);
    }
    
    public static void displayCounts(int[] count)
    {
          for (int x = 0; x < count.length; x++)
          {
            System.out.println("The occurrence of " + (char)(x + 'A') + "'s is "
                + count[x]);
          }
    }
    
}
