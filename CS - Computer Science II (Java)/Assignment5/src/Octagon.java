
public class Octagon extends GeometricObject implements Comparable<Octagon>, Cloneable
{
	private double side;
	
	// Default constructor 
	public Octagon()
	{
		
	}
	
	// Construct an octagon object with side value
	public Octagon(double side) 
	{
	        this.side = side;
	}
	
	// Getter method for side
	public double getSide()
	{
		return side;
	}
	
	// Set a new side
	public void setSide(double side)
	{
		this.side = side;
	}
	
	// Getter method for the area of an octagon
	public double getArea()
	{
		return ((2 + (4 * (Math.sqrt(2)))) * side * side);
	}
	
	// Getter method for the perimeter
	public double getPerimeter()
	{
		return side * 8;
	}
	
	// Implement the compareTo method defined in Comparable 
	@Override
	public int compareTo(Octagon oct)
	{
		if(getSide() > oct.getSide())
		{
			return 1;
		}
		else if (getSide() < oct.getSide())
		{
			return -1;
		}
		else
			return 0;
	}
	
	// Overrides to do a deep copy
	@Override
	public Octagon clone() throws CloneNotSupportedException
	{
	
		Octagon octClone = new Octagon(side);
		
		return octClone;
	}

	

}
