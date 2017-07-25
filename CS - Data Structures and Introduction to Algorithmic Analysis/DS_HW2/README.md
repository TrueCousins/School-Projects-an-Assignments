# Description
This program is used to test ArrayofLists.java. The program creates two lists, 
one that holds Integers and the other will hold Floats. It will then read in 
input files as Strings and later convert the numbers into Integer or Floats 
depending on the list.They will then be placed into the list depending on the 
commands in the input files.

How to run:
1)  User enters in input1.txt and input2.txt
    - For me, I had to type in the location of my files for the program to run.
2) The prorgram will then print the list at the end.

# Code
```Java

import java.util.*;     // Needed for Scanner
import java.io.*;       // Needed for file

public class DS_HW2 {

    public static void main(String[] args) throws Exception {
        
        // Create two list
        ArrayofLists list = new ArrayofLists();     // Will hold ArrayofLists of Integers
        ArrayofLists list2 = new ArrayofLists();    // Will hold ArrayofLists of Floats
        
        System.out.println("Is list empty? " + list.isEmpty());
        System.out.println("Is list2 empty? " + list2.isEmpty());
        System.out.println();
        
        
        // User input file name or location
        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter input1.text location or file name: ");
        String filename1 = userInput.nextLine();
        // C:\\Users\colle\Documents\text files\input1.txt
        
        System.out.println("Enter input2.text location or file name: ");
        String filename2 = userInput.nextLine();
        // C:\\Users\colle\Documents\text files\input2.txt
        System.out.println();
        
        // Creates file1 and file2 instances 
        java.io.File file1 = new java.io.File(filename1);
        java.io.File file2 = new java.io.File(filename2);
        
        // Create scanner for the files
        Scanner input1 = new Scanner(file1);
        Scanner input2 = new Scanner(file2);
        
        // Arrays to temporay hold data for list
        ArrayList<String> getCommand = new ArrayList<String>(); // Will hold command letter
        ArrayList<Integer> getNum = new ArrayList<>();          // Will hold numbers to be put into methods
        
        // Arrays to temporary hold data for list2
        ArrayList<String> getCommand2 = new ArrayList<String>();
        ArrayList getNum2 = new ArrayList();
        
        // ArrayList to hold command charachters
        ArrayList<String> commands = new ArrayList<>();
        commands.add("a");
        commands.add("r");
        commands.add("g");
        commands.add("s");
        commands.add("p");
        commands.add("q");
        
        
        // Reads input files and adds them to list
        while(input1.hasNext())
        {
            // file is read into getCommand
            if(input1.hasNext())
            {
                getCommand.add(input1.next());
            }
            else
                getNum.add(input1.nextInt()); // nothing really goes into this. It's just for placement sake
            

        }
        //System.out.println("Int file: ");
        //System.out.println(getCommand);
        
        // Reads input files and adds them to list
        while(input2.hasNext())
        {
            if(input2.hasNext())
            {
                getCommand2.add(input2.next());
            }
            else
                getNum2.add(input2.nextFloat()); 
        }
        //System.out.println("Float file: ");
        //System.out.println(getCommand2);
        input1.close();     // close file input1 
        input2.close();     // close file input2
        
        //**************************************************************************
        //* INTEGER LIST                                                           *
        //**************************************************************************
        
        
        // Holds the integers that will be converted from a string
        int si;        // si holds the index
        int si2;       // si2 holds the value
        
        // For loop will compare the commands at each line
        for(int i = 0; i < getCommand.size(); i++)
        {
            if(getCommand.get(i).equals(commands.get(0)))   // Command "a", add
            {
                // Turns String to Integer and add to getNum list
                si = Integer.parseInt(getCommand.get(i+1));
                getNum.add((si));
                si2 = Integer.parseInt(getCommand.get(i+2));
                getNum.add(si2);
                
                // Add si2 at index si
                System.out.println("Now adding " + si2 + " to index " + si + "." );
                list.add(si,si2);
                //System.out.println(getNum);
            }
            else if(getCommand.get(i).equals(commands.get(1)))  // Command "r", remove
            {
                // Turns String to Integer and add to getNum list
                si = Integer.parseInt(getCommand.get(i+1));
                getNum.add((si));
                
                // Removes at index si
                System.out.println("Now removing " + si + ".");
                list.remove(si);
                //System.out.println(getNum);
                
            }
            else if(getCommand.get(i).equals(commands.get(2)))  // Command "g", get
            {
                // Turns String to Integer and add to getNum list
                si = Integer.parseInt(getCommand.get(i+1));
                getNum.add((si));
                
                // Gets value at index si
                System.out.println("Now getting value at index " + si + ".");
                list.get(si);
                //System.out.println(getNum);
                
            }
            else if(getCommand.get(i).equals(commands.get(3)))  // Command "s", set
            {
                // Turns String to Integer and add to getNum list
                si = Integer.parseInt(getCommand.get(i+1));
                getNum.add((si));
                si2 = Integer.parseInt(getCommand.get(i+2));
                getNum.add(si2);
                
                // Set a new value si2, at index si
                System.out.println("Now setting " + si2 + " into index " + si + ".");
                list.set(si, si2);
                //System.out.println(getNum);
                
            }
            else if(getCommand.get(i).equals(commands.get(4)))  // Command "p", printList
            {
                // Prints out the list
                System.out.println("Now printing list: ");
                list.printList(list.size());
                //System.out.println(getNum);
            }
            else if(getCommand.get(i).equals(commands.get(5)))  // Command "q", add(x)
            {
                // Turns String to Integer and add to getNum list
                si = Integer.parseInt(getCommand.get(i+1));
                getNum.add((si));
                
                // Adds a value si to end of list
                System.out.println("Now adding " + si + " to the end of the list.");
                list.add(si);
                //System.out.println(getNum);
            }
            else
                System.out.println();
        }
        
        //*******************************************************************************
        //* FLOAT LIST                                                                  *
        //*******************************************************************************
        System.out.println("Now working with floats.");
        
        // Holds temp data to be added to the list
        // These values are String converted to Float
        int si3 = 0;
        float si4 = (float) 0;
        //float si5;
        
        
        // For loop will compare the commands at each line
        for(int i = 0; i < getCommand2.size(); i++)
        {
            if(getCommand2.get(i).equals(commands.get(0)))   // Command "a", add
            {
                // Turns String to Integer and add to getNum2 list
                si3 = Integer.parseInt(getCommand2.get(i+1));
                getNum2.add(si3);
                // Turns String to Float and add to getNum2 list
                si4 = Float.parseFloat(getCommand2.get(i+2));
                getNum2.add(si4);
                
                // Add value si4 at index si3
                System.out.println("Now adding " + si4 + " to index " + si3 + "." );
                list2.add(si3,si4);
                //System.out.println(getNum2);
            }
            else if(getCommand2.get(i).equals(commands.get(1)))  // Command "r", remove
            {
                // Turns String to Integer and add to getNum2 list
                si3 = Integer.parseInt(getCommand2.get(i+1));
                getNum2.add((si3));
                
                // Remove at index si3
                System.out.println("Now removing " + si3 + ".");
                list2.remove(si3);
                //System.out.println(getNum2);
                
            }
            else if(getCommand2.get(i).equals(commands.get(2)))  // Command "g", get
            {
                // Turns String to Integer and add to getNum2 list
                si3 = Integer.parseInt(getCommand2.get(i+1));
                getNum2.add((si3));
                
                // Get value at index si3
                System.out.println("Now getting value at index " + si3 + ".");
                list2.get(si3);
                //System.out.println(getNum2);
                
            }
            else if(getCommand2.get(i).equals(commands.get(3)))  // Command "s", set
            {
                // Turns String to Integer and add to getNum2 list
                si3 = Integer.parseInt(getCommand2.get(i+1));
                getNum2.add((si3));
                // Turns String to Float and add to getNum2 list
                si4 = Float.parseFloat(getCommand2.get(i+2));
                getNum2.add(si4);
                
                // Set a new value si4, at index si3
                System.out.println("Now setting " + si3 + " into index " + si4 + ".");
                list2.set(si3, si4);
                //System.out.println(getNum2);
                
            }
            else if(getCommand2.get(i).equals(commands.get(4)))  // Command "p", printList
            {
                // Print list2
                System.out.println("Now printing list of floats: ");
                list2.printList(list2.size());
                //System.out.println(getNum2);
            }
            else if(getCommand2.get(i).equals(commands.get(5)))  // Command "q", add(x)
            {
                // Turns String to Float and add to getNum2 list
                si4 = Float.parseFloat(getCommand2.get(i + 1));
                getNum2.add((si4));
                
                // Adds a value si4 to end of list
                System.out.println("Now adding " + si4 + " to the end of the list.");
                list2.add(si4);
                //System.out.println(getNum2);
            }
            else
                System.out.println();
        }
        
         
        //System.out.println(getCommand);
        //System.out.println(getNum);
        //System.out.println("List1 contains: ");
        //list.printList(20);
        //System.out.println("List2 contains: ");
        //list2.printList(20);
        
        System.out.println("Is list empty? " + list.isEmpty());
        System.out.println("Is list2 empty? " + list2.isEmpty());
        
        System.out.println("The size of list is now " + list.size());
        System.out.println("The size of list2 is now " + list.size());
        
        System.out.println("Clearing list and list2 now");
        list.clear();
        list2.clear();
        
        System.out.println("Is list empty? " + list.isEmpty());
        System.out.println("Is list2 empty? " + list2.isEmpty());
        
        
    }
    
}

```
