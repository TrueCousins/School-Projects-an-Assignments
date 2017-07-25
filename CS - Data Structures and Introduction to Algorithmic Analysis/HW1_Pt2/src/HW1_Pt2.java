/*

This progam uses the examples of a drawing box which is a generic class. A 
drawing box of integers and strings of names is created. The program will 
display if the box is empty. This program then asks the user to enter the file
name or location of inputI.txt and inputS.txt. This is because I
could only get my program to read files from the location and not just by the 
name. The program will then add the files to the generic class and draw 3 items
from the box to display.
*/

import java.util.*;     // Needed for Scanner
import java.io.*;       // Needed for file


public class HW1_Pt2 {

    public static void main(String[] args) throws Exception {
        
        DrawingBox<Integer> intBox =  new DrawingBox<Integer>();
        DrawingBox<String> stringBox = new DrawingBox<String>();
        
        // Testing if the drawing box is empty
        // They should be empty.
        System.out.println("Is the integer box empty? " + intBox.isEmpty());
        intBox.drawItem();
         
        System.out.println("Is the string box empty? " + stringBox.isEmpty());
        stringBox.drawItem();
        System.out.println();
         
        
        // User input file name or location
        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter inputI.text location or file name: ");
        String filenameI = userInput.nextLine();
       // C:\\Users\colle\Documents\text files\inputI.txt
        
        System.out.println("Enter inputS.text location or file name: ");
        String filenameS = userInput.nextLine();
       // C:\\Users\colle\Documents\text files\inputS.txt
        System.out.println();
        
        //java.io.File file = new java.io.File("C:\\Users\\colle\\Documents\\text files\\inputI.txt");
        //java.io.File file2 = new java.io.File("C:\\Users\\colle\\Documents\\text files\\inputS.txt");
       
        // Creates fileI and fileS instances 
        java.io.File fileI = new java.io.File(filenameI);
        java.io.File fileS = new java.io.File(filenameS);
       
        // Create scanner for the files
        Scanner inputI = new Scanner(fileI);
        Scanner inputS = new Scanner(fileS);
        
         System.out.println("Now adding integers and string to a drawing box.");
         System.out.println();
         
         // Reads input files and adds them to a list
         while(inputI.hasNext() && inputS.hasNext())
         {
             intBox.add(inputI.nextInt());
             stringBox.add(inputS.next());
         }
         inputI.close();     // close file inputI 
         inputS.close();    // close file inputS
        
        // DISPLAY RESULTS
        System.out.println("Is the integer box empty? " + intBox.isEmpty());
        intBox.drawItem();
        
        System.out.println("Is the string box empty? " + stringBox.isEmpty());
        stringBox.drawItem();
        
        
        /*
        intBox.add(50);
        intBox.add(30);
        intBox.add(70);
        System.out.println(intBox.isEmpty());
        intBox.drawItem();
       //DrawingBox.<Integer>drawItem(intBox);
        
        System.out.println();
        */
    }
}
