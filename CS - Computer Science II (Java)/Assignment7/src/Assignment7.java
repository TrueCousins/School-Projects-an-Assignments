// Colleen Cousins
// CS 2336.001
// 2:30pm-3:45pm
// Due Nov. 15, 2015
// (Same Number subsequence) Write an O(n) program that prompts the 
// user to enter a sequence of integers ending with 0 and finds the 
// longest subsequence with the same number

import java.util.Scanner;
import java.util.ArrayList;		// Needed for ArrayList
import java.util.TreeMap;		// Needed for TreeMap

public class Assignment7 {

	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		boolean go = true;
		
		// create an array list and tree map
		ArrayList<Integer> list = new ArrayList<Integer>();
		TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
		
		int counter = 1; // holds the number of like numbers
		
		// User input
		System.out.println("Enter a series of numbers ending with 0: ");
		
		// Place user input in list
		while(go)
		{
			int series = input.nextInt();
			
			if(series > 0)
				list.add(series);
			else
				go = false;
		}
		//System.out.println("List: " + list);
		
		// If values of x is the same as previous index
		for(int x = 1; x < list.size(); x++)
		{
			if(list.get(x).equals(list.get(x-1)))
			{
				counter++;
			
			}
			else
			{
				map.put(counter, x - counter);
				counter = 1;
			}
		}
		
		// Display result
		System.out.println("The longest same number sequence starts at index "
				+ map.lastEntry().getValue() + " with " + map.lastEntry().getKey() 
				+ " value of " + list.get(map.lastEntry().getKey()));

	}

}
