// CS 11336 Lab 2 Exercise 2
// Program by: Colleen Cousins

#include <iostream>
#include <iomanip>
using namespace std;

int main()
{
	double profitMargin;		// Holds the profit margin
	double buildCost;			// Holds the cost of building product
	double profit;				// Holds the profit for product
	double sellPrice;			// Holds the selling price of the product

   // Amount for building cost and the Profit Margin
	buildCost = 32.75;			
	profitMargin = .45;

	cout << fixed << setprecision(2);	// number of digits set after the decimal

   // Calculate the profit
	profit = buildCost*profitMargin;

   // Calculate the selling price
	sellPrice = buildCost + profit;

   //Displays the profit and the selling price of the circuit board
	cout << "The profit for the circuit board is: " << profit << endl;
	cout << "The selling price of the circuit boards is: " << sellPrice <<endl;

	return 0;
}	// end function main