/*
Colleen Cousins
CS 1337.002
January 22-2015
DESCRIPTION:
Write a program that asks the user for the unit price of the item,
the number of items the shopper wants to purchase, the name of 
the first state and its tax rate, the name of the second state 
and its tax rate. Then, calculate the total cost for each state 
and decide which state should the shopper buy the item from. Display
the results to the shopper.
*/

#include <iostream>
#include <string>             // Needed to use string
#include <iomanip>            // Needed to use setprecision(x) adn fixed

using namespace std;

int main()
{
   double price = 0.0,        // stores the item's price
          taxRate1 = 0.0,     // stores the tax rate of the first state
          taxRate2 = 0.0,     // stores the tax rate of the second state
          total1 = 0.0,       // stores the total cost if bought in the first state
          total2 = 0.0;       // stores the total cost if bought in the second state
   int numItems = 0;          // stores the number of items bought
   string state1, state2;     // stores the names of the two states inputted

   // Asks for the unit price
   cout << "What is the unit price of the item? \n";
   cin >> price;

   //Input Validation for Unit Price
   while (price <= 0)
   {
      cout << "Invalid Input. Please enter a unit price higher than 0: \n";
      cin >> price;
   }

   // Asks for number of items bought
   cout << "Enter the number of items you want to purchase: \n";
   cin >> numItems;

   // Input Validation for number of items bought
   while (numItems <= 0)
   {
      cout << "Invalid Input. Please enter a unit price higher than 0 \n";
      cin >> numItems;
   }

   // Asks for the First state and its tax rate
   cout << "Enter name of the First State and it's tax rate. \n";
   cout << "Leave a space between your answers: \n";
   cin >> state1 >> taxRate1;

   //Input Validation for state1 and tax1
   while (taxRate1 < 0)
   {
      cout << "Invalid Input. Please Re-enter first state and it's tax rate: \n";
      cin >> state1 >> taxRate1;
   }

   // Asks for the second state and its tax rate
   cout << "Enter name of the Second State and it's tax rate. \n";
   cout << "Leave a space between your answers: \n";
   cin >> state2 >> taxRate2;

   //Input Validation for state2 and tax2
   while (taxRate1 < 0)
   {
      cout << "Invalid Input. Please Re-enter second state and it's tax rate: \n";
      cin >> state2 >> taxRate2;
   }
   // Calculates the total prices for each state
   taxRate1 /= 100;
   taxRate2 /= 100;
   total1 = price * numItems * taxRate1;
   total1 += (price * numItems);
   total2 = price * numItems * taxRate2;
   total2 += (price * numItems); 
   taxRate1 *= 100;
   taxRate2 *= 100;

   // Set two digits after the decimal point 
   cout << fixed << setprecision(2); 
   
   // Displays answer
   cout << endl; 
   cout << "Unit Price: $" << price << endl;
   cout << "Unit Price Cost in " << state1 << " : $" << total1 << endl;
   cout << "Unit Price Cost in " << state2 << " : $" << total2 << endl;
   cout << "Number of Items: " << numItems << endl;
   cout << "First State and It's Tax Rate: " << state1 << " "<< taxRate1 << "%"  << endl;
   cout << "Second State and It's Tax Rate: " << state2 << " " << taxRate2 << "%" << endl;
   
   // Sorts out which state was cheaper and displays the 
   // recommendation
   if (total1 > total2)
   {
      cout << "I recommend you buy from " << state2 << "." << endl; 
   }
   else
   {
      cout << "I recommend you buy from " << state1 << "." << endl;
   }
   
   return 0;
} // end main function