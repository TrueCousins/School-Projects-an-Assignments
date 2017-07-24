// Lab 6 Exercise 1
// Calculate the product
//
// Program by:			Colleen Cousins

#include <iostream> 
using namespace std; 

int main()
{
	// Variables
	int number,
		product = 1,
		count = 0;

	// Using do-while loop
	do
	{
		//Display statement and allows input
		cout << "Enter an integar number to be included in the product"
			<< endl << "or enter 0 to end the input: ";
		cin >> number;

		// Calculates product of numbers that were inputted
		if (number != 0)
		{
			product = product * number;
			count++;
		}
			

	} while (number != 0);
	
	// Displays final product
	if (count > 0)
	{
		cout << endl << "The product is " << product << "." << endl;
	}
	
	return 0;
}