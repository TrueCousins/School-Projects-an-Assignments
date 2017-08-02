// Colleen Cousins
// CS 2336.001
// Due Oct. 24, 2015
// This program demonstrates the Comparable and Cloneable interfaces
// by cloning the object Octagon and then comparing the two.

public class Assignment5 {

	public static void main(String[] args) throws CloneNotSupportedException
	{
		double side = 5;
		
		// Create objects of the Octagon class and create an identical copy
		Octagon oct = new Octagon(side);
		Octagon clonedOct = (Octagon) oct.clone();
		int compare;	// To hold the comparison outcome
		
		// Display the Original Octagon sides, area, and perimeter
		System.out.println("Side of Original Octagon: " + oct.getSide());
		System.out.println("Area of Original Octagon: " + oct.getArea());
		System.out.println("Perimeter of Original Octagon: " + oct.getPerimeter());
		
		// Display the Cloned Octagons sides, area, and perimeter
		System.out.println("");
		System.out.println("Clone Side: " + clonedOct.getSide());
		System.out.println("Clone Area: " + clonedOct.getArea());
		System.out.println("Clone Perimeter: " + clonedOct.getPerimeter());
		System.out.println("");
		
		//oct.setSide(8);
		//System.out.println("Now Oct is " + oct.getSide());
		
		
		compare = oct.compareTo(clonedOct);			// Use compareTo method to compare the Original and copy
		//System.out.println("First: " + compare);
		
		int x = 0;
		for(int i = 0; i < 2; i++)
		{
			
			if(compare == 0)
				System.out.println("The clone's side and the original's side are equal.");
			else if(compare < 0)
				System.out.println("The clone's side is more than the original.");
			else
				System.out.println("The clone's side is less than the original.");
			
			// Testing compareTo
			System.out.println("");
			
			oct.setSide(8);	// Set original octagon side to 8
			if(x == 0)
			{
				System.out.println("Now testing the CompareTo method by changing the original side.");
				System.out.println("Now the original Octagon side is " + oct.getSide());
				System.out.println("Clone side is still " + clonedOct.getSide());
				x++;
			}
			compare = oct.compareTo(clonedOct);	
			
			
		}
		
	

	}

}
