// Lab 5 Exercise 2
// Convert to Roman numerals


#include <iostream>
using namespace std; 

int main()
{
	int number;					// holds number

	// Display the choice of number
	cout << "Enter a number from 1 to 5: ";
	cin >> number; 
	
	// Determines the number with switch statement
	switch (number)
	{
		case 1: cout << "The Roman numeral equivalent is: I\n";
			break;
		case 2: cout << "The Roman numeral equivalent is: II\n";
			break;
		case 3: cout << "The Roman numeral equivalent is: III\n";
			break;
		case 4: cout << "The Roman numeral equivalent is: IV\n";
			break;
		case 5: cout << "The Roman numeral equivalent is: V\n";
			break;
		default: cout << "The number must be in the range of 1 through 5 inclusive.\n";
	}

	return 0;
} // end function main
