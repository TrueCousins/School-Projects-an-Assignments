# Description
A simple program theat takes in user input for Financial Aid Qualification 
# Code

```C++
#include <iostream> 
using namespace std; 

int main()
{
	char choice;				// holds the choice of yes or no
	double income;				// holds the amount of income

	// Displays questions
	cout << "Are you an undergraduage student? Please enter y for Yes or a n for No. ";
	cin >> choice;

	
	// Determines if qualified for financial aid or not
	if (choice == 'y' || choice == 'Y')
	{
		cout << "What is your current yearly income? ";
		cin >> income;
		if (income >= 0 && income <= 15000) // nested if else statement
		{
			cout << "You qualify for $1000 in financial aid.\n";
		}
		else if (income < 0) // Error condition
		{
			cout << "ERROR: Please restart program and enter a number\n";
			cout << "greater than 0 for income.\n";
		}
		else
		{
			cout << "You qualify for $500 in financial aid.\n";
		}
	}
	else if ( choice == 'n' || choice == 'N')  // Do not qualify
	{
		cout << "You do not quilify for financial aid.\n";
	}
	else
	{
		cout << "ERROR: Please restart program and enter a Y for Yes\n";
		cout << "or a N for No.\n";
	}
		

	return 0;
} // end main function
```
