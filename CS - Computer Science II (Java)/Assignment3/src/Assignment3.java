// Colleen Cousins
// CS 2336.001
// 2:30pm-3:45pm
// Due September 26, 2015
// This program asks the user to enter numbers for a, b, and c. Depending on the 
// discriminant the program will display the roots for the numbers entered through the 
// quadratic formula.

import java.util.Scanner;	// Scanner is in the java.util package

public class Assignment3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// Creates a scanner object
		Scanner input =  new Scanner(System.in);
		
		// User enters coefficients
		System.out.println("Enter coefficients for a, b, and c: ");
		
		int a = input.nextInt();
		int b = input.nextInt();
		int c = input.nextInt();
		
		QuadraticEquation quad = new QuadraticEquation(a, b, c);
		
		// If the Discriminat is > 0 then display both roots
		if(quad.getDiscriminant() > 0)
		{
			System.out.println("The roots of the equation are " + quad.getRoot1() + " and "
					+ quad.getRoot2());
		}
		else if(quad.getDiscriminant() == 0)
		{
			// If discriminant = 0 then there is only one root to display
			System.out.println("The root of the equation is " + quad.getRoot1() );
		
		}
		else
		{
			// If Discriminant < 0 then no root is displayed
			System.out.println("The equation has no roots.");
		}
		

	}

}
