// Colleen Cousins
// CS 2336.001
// 2:30pm-3:45pm
// Due September 26, 2015
// This program asks the user to enter numbers for a, b, and c. Depending on the 
// discriminant the program will display the roots for the numbers entered through the 
// quadratic formula.


public class QuadraticEquation 
{
	private int a, b, c;	// Private data a, b, and c
	
	//Constructor for the arguments a, b, and c
	QuadraticEquation()
	{
		a = 0;
		b = 0;
		c = 0;
	}
	
	//User will input the coefficients for a, b, and c
	QuadraticEquation(int newA, int newB, int newC)
	{
		a = newA;
		b = newB;
		c = newC;
	}
	
	// Getter Methods
	public int getA()
	{
		return a;
	}
	public int getB()
	{
		return b;
	}
	public int getC()
	{
		return c;
	}
	
	// Method to get Discriminant 
	public double getDiscriminant()
	{
		return (Math.pow(b, 2) - 4 * a * c);
	}
	
	// Methods to calculate the roots
	public double getRoot1()
	{
		//System.out.println("In root 1.");
		return ((-b + Math.sqrt(getDiscriminant()))/(2 * a));
	}
	public double getRoot2()
	{
		//System.out.println("In root 2.");
		return ((-b - Math.sqrt(getDiscriminant()))/(2 * a));
	}
	

}
