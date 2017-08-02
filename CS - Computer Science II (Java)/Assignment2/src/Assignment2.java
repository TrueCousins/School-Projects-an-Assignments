 
// Colleen Cousins
//CS 2336.001
// 2:30pm-3:45pm
// Due September 20, 2015
// This program displays the first 100 palindromic 
// prime numbers and displays them out.

public class Assignment2
{

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		
		
		int number = 2;		// Holds the number to check if a prime and a palindromic number
		int total = 100;	// Makes sure to display 100 numbers out
		int counter = 1;	// Will keep count of the numbers in a line
		int line = 10;		// Every 10 numbers the line will end and go to the next one
		
		
		while(counter <= total)
		{
			if(isPrime(number) && isPalin(number))
			{
				// Displays number if both a prime and palindromic
				System.out.print(number + " ");
				
				// Ends line every 10 numbers and skips another line for space between the rows
				if(counter % line == 0)
				{
					System.out.println();
					System.out.println();
					//System.out.println("Counter: " +counter);
				}
				counter++;
			}
			number++;
		} 
	} // end main
	

// Method 1: Checks if a prime number
public static boolean isPrime(int num)
{
	if(num == 2)
		return true;
	
	// Checks if it is a prime number
	for(int x = 2; x <= num/2; x++)
	{
		if(num % x == 0)
		{
			return false;
		}
	}
	return true;
	
} // end isPrime

// Method 2: Check if a Palindromic number
public static boolean isPalin(int num)
{
	int numBackup = num; 	// Holds the current number to check if equal to reversed
	int reversed = 0;		// Will hold the number in reverse
	
	// Will reverse the number to check if it is palindromic
	while (num > 0)
	{
		reversed = reversed * 10 + num % 10;
		num = num / 10;
		//System.out.println("Num: " +num);
		//System.out.println("Reversed: " +reversed);
	}
	
	return (numBackup == reversed);		// If current number and the reversed number are equal then return true
	
}// end isPalin


}