/*
 * Colleen Cousins
 * CS 2336.001
 * Due: Nov 1, 2015
 * This program asks the user to enter a string. The program will then find 
 * all the permutations of that string and display it. This program demonstrates
 * recursion.  
 */

import java.util.Scanner;	// Scanner is in the java.util package

public class Assignment6 {

	public static void main(String[] args)
	{
		// Create a scanner object
		Scanner input = new Scanner(System.in);
		
		// Ask for user input
		System.out.println("Enter in a string: ");
		String str = input.nextLine();
		
		//System.out.println(str);
		
		
		displayPermutation(str);

	} // end of main

	
	// Calls the second method
	public static void displayPermutation(String s)
	{
		displayPermutation("",s);
	} // end of displayPermutation(String)
	
	// Recursive method
	public static void displayPermutation(String s1, String s2)
	{
		int x = s2.length(); // get string length of user input
		
		if(x == 0)
			System.out.println(s1);
		else
		{
			for(int i = 0; i < x; i++)
			{
				displayPermutation(s1 + s2.charAt(i), s2.substring(0, i) + s2.substring( i + 1, x));
			}
		}
	} // End of displayPermutation(String, String)
	
}
