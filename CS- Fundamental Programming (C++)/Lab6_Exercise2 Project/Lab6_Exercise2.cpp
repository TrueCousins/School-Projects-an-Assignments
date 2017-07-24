// Lab 6 Exercise 2
// Calculate investment amount
// 
// Program by:			Colleen Cousins

#include <iostream>
#include <iomanip>
using namespace std;

int main()
{
	//Variables
	double initialAmt,			// holds the initial investment amount
			 annualRate,			// holds the anual rate
			 monthlyRate,			// holds the monthly rate
			 balance,				// will hold total balance
			 earned;					// holds the amount earned
	int months;
	

	// Ask for initial investment amount
	cout << "Enter the amount of the investment.\n";
	cout << "The minimum investment is $10,000.00.\n";
	cin >> initialAmt;

	while (initialAmt < 10000)
	{
		cout << "Error, the minimum investment is $10,000.00.\n";

		cout << "Enter the amount of the investment: ";
		cin >> initialAmt;
	}
	

	// Ask for Annual Interest Rate
	cout << "Enter the annual interest  rate: ";
	cin >> annualRate;

	while (annualRate < 0)
	{
		cout << "Error, the interest rate should be positive.\n";

		cout << "Enter the annual interest rate: ";
		cin >> annualRate;
	}


	// Ask for number of months
	cout << "Enter the number of months of the investment: ";
	cin >> months; 

	while (months < 0)
	{
		cout << "Error, the number of months should be positive.\n"; 
		
		cout << "Enter the number of months of the investment: ";
		cin >> months; 
	}


	// Calculations
	monthlyRate = (annualRate / 12) / 100;
	balance = initialAmt;
	int mon = months;

	while (mon > 0)
	{
		earned = balance * monthlyRate;
		balance = balance + earned;
		mon--;
	}


	cout << fixed << setprecision(2); 
	cout << "After " << months << " months your investment of $" << initialAmt
		<< " will be worth $" << balance << endl;
	
	return 0;
}