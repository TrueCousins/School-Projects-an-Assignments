// Colleen Cousins
// CS 2336.001
// 2:30pm-3:45pm
// Due September 8, 2015
// This program asks the user for the speed and acceleration and will calculate
// the minimum run way length for a plane.

import java.util.Scanner;      // Scanner is in the java.util package

public class Assignment1
{

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		
		// Creates a scanner object
		Scanner input = new Scanner(System.in);
		
		
		// Ask the user to enter the speed and acceleration
		// Place a space between your inputs
		System.out.print("Enter the speed and acceleration: ");
		double speed = input.nextDouble();
		double acceleration = input.nextDouble();
		
		// Compute the Length
		double length = speed*speed/(2*acceleration);
		
		// Display the result
		System.out.printf("The minimum runway length for this airplane is %4.3f", +length);

	}

}
