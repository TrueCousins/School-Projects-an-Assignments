/*
Colleen Cousins
CS 1337.002
March 1st, 2015

DESCRIPTION:
A program for a simple grocery store checkout. It will perform checkout,
keep track of inventory, and able to give out coupons

PSUEDOCODE:
1.	 Start Program
2.	 Read input file
3.	 Extract data from products.txt into a structured array
4.	 Ask User to Start checking items out or exit
5.	 User inputs PLU codes and weight/quantity of items
6.	 Total items bought is calculated
7.	 Coupons are added if they meet requirements
8.	 Ends checkout and next customer can start
9.	 Repeat steps 4-7 for each new customer
10. End program when done
11. An output file will update the stocks when store closes
12. End Program

FLOW CHART
 - Start program
 - Read input file "products.txt"
 - Display menu
 - Are you a new customer? or Exit
 - If new customer, proceed with checkout ***
      - Ask Customer for PLU code
      - Ask for the weight/quantity of item
      - If PLU code input == 0 then exit out of checkout
      - Input validation for all input will be set
            -- Repeat PLU code, weight/quantity when invalid data is input
      - Calculate the total
         - Display Subtotal
         - If total > $50, apply 5% coupon
         - If 1 out of 3 customer, apply a 5% coupon
      - Display Total
 - If a new customer is next, repeat the checkout function ***
 - If Exit
      - Update inventory
 - End Program
*/

#include <iostream>
#include <fstream>
#include <string>
#include <iomanip>
#include <cstdlib>  // Needed to use rand()
#include <ctime>    // Needed to use time()

using namespace std;

// Function protypes for check out and inventory update
void checkOut(int);     
int updateInven(int);

struct InvenInfo
{
   int PLU;          // Price Look Up Code
   string pName;     // Product Name
   int saleType;     // Product Sale Type: 0 = per unit, 1 = per pound
   double pricePer,  // Price Per Pound/Unit
          iLev;      // Current Inventory Level
};

// Global Varaiable/Constant
const int ARRAY_SIZE = 100;            
InvenInfo product[ARRAY_SIZE];
double totalSales;

int main()
{
   ifstream inputFile;
   
   int count = 0;    // Holds the number of products in the products.txt file
   int choice;       // Holds the Menu choice of customer

   // Begin reading inventory file
   inputFile.open("products.txt");

   if (inputFile)
   {
      // Keeps track of how many products are in the file
      while (count < ARRAY_SIZE && inputFile >> product[count].PLU >> product[count].pName >> product[count].saleType >> product[count].pricePer >> product[count].iLev)
      {
         count++;
      }
      
      
      // Writes products to strucured arrays and displays output
      for (int i = 0; i < count; i++)
      {
         inputFile >> product[i].PLU >> product[i].pName >> product[i].saleType >> product[i].pricePer >> product[i].iLev;

         // Display to show that the file was read successfully
         cout << product[i].PLU << " "
            << product[i].pName << " "
            << product[i].saleType << " "
            << product[i].pricePer << " "
            << product[i].iLev << " " << endl;
      }
   
   }
   else
   {
      // If file can't open
      cout << "Error!!!" << endl;
   }
   
   inputFile.close(); // closes inputFile 
   cout << endl << endl;

   // MENU SYSTEM TO BEGIN CHECKOUT
   do
   {
      cout << "\t\tWelcome to the Simple Grocery Store!\n\n"
         << "1. Start new customer\n"
         << "2. Exit\n"
         << "Enter the number for your choice:\n";
      cin >> choice;

      // Validate the Menu choice
      while (choice < 1 || choice > 2)
      {
         cout << "Please enter a valid menu choice:\n";
         cin >> choice;
      }

      if (choice == 1)
         checkOut(count);     // Calls the Check-Out function
      else
         updateInven(count);  // Calls the Update Inventory function

   } while (choice != 2);
   
   return 0;
} // END MAIN FUNCTION

void checkOut(int size)
{
   int pluInput = 0;             // User input for plu code
   int plu = -1;                 // number of array for plu code
   double qOrW = 0.0;            // quantity/weight of product
   double total = 0.0;           // total amount per customer
   
   const int MIN_VALUE = 1;      // min value for random customer
   const int MAX_VALUE = 3;      // max value for random customer
   int randomDiscount;           // holds a random number between 1 and 3

   InvenInfo receipt[ARRAY_SIZE];   // Holds the items bought per customer
   int j = 0;                       // Holds the size for the receipt array

   do
   {
      plu = -1; // resets plu

      cout << endl << "Please enter the PLU code or a 0 to exit: " << endl;
      cin >> pluInput;

      // Exit at the beginning
      if (pluInput == 0)
         break;

      // Gets a copy of the product
      for (int i = 0; i < size; i++)
      {
         if (pluInput == product[i].PLU)
         {
            plu = i;
            receipt[j].pName = product[i].pName;
            receipt[j].pricePer = product[i].pricePer;
            j++;
            break;
         }
      }

      // Input Validation for PLU 
      while (plu == -1)
      {
         cout << endl << "Please enter a valid PLU code or a 0 to exit:\n";
         cin >> pluInput;

         for (int i = 0; i < size; i++)
         {
            if (pluInput == product[i].PLU)
            {
               plu = i;
               receipt[j].pName = product[i].pName;
               receipt[j].pricePer = product[i].pricePer;
               j++;
               break;
            }
         }

         // exit when done
         if (pluInput == 0)
            break;
      }

      // Input Weight or Quantity
      if ((plu > -1) && (product[plu].saleType == 0))
      {
         cout << "Enter the Quantity: ";
         cin >> qOrW;

         // Input Validation for Quantity
         while (qOrW <= 0)
         {
            cout << "Please enter a valid Quantity: ";
            cin >> qOrW;
         }
      }
      else if ((plu > -1) && (product[plu].saleType == 1))
      {
         cout << "Enter the Weight: ";
         cin >> qOrW;

         // Input Validation for Weight
         while (qOrW <= 0)
         {
            cout << "Please enter a valid Weight: ";
            cin >> qOrW;
         }
      }
      else
      {
         while (plu > -1 && qOrW <= 0)
         {
            cout << "Please enter a valid Quanity or Weight:\n";
            cin >> qOrW;
         }
         break;
      }

      // Keeps track of total
      total += qOrW * product[plu].pricePer;

      // Updates the stock of iventory
      product[plu].iLev -= qOrW;

   } while (pluInput != 0);

   
   // RECEIPT
   cout << endl << "\t\tRECEIPT" << endl;
   for (int i = 0; i < j; i++)
   {
      cout << setw(25) << left << receipt[i].pName << "$" << receipt[i].pricePer << endl;
  
   }

   
   // Total before any discounts
   cout << fixed << setprecision(2) << endl << "Subtotal: $" << total << endl;
 
    // Random coupon, 1 out of 3
    unsigned seed = time(0);
    srand(seed);
    randomDiscount = (rand() % (MAX_VALUE - MIN_VALUE + 1)) + MIN_VALUE;
    cout << "RANDOM DISCOUNT #: " << randomDiscount << endl;               
    cout << endl;

    if (randomDiscount == 3 && pluInput == 0) // When customers get a 3, they will get a random coupon
    {
       total *= 0.95;
       cout << "Congrats!!!\n"
            << "You are a lucky 1 out of 3 customers to win a random coupon!\n"
            << "You get an additional 5% discount to your purchase!!!\n";

    }

   // 5% discount
   if (total > 50)
   {  
      total *= 0.95;
      cout << "The total exceeds $50. You get a 5% discount!!!\n";
      
   }


   totalSales += total; // Keeps track of the total sales 

   cout << endl << "Total: $" << total << endl;
   cout << "\t\tHave a Nice Day!!!\n\n";
   
} // end checkout function

int updateInven(int size)
{
   ofstream outFile;
   
   // Opens output file
   outFile.open("inventory.txt");

   if (outFile)
   {
      for (int i = 0; i < size; i++)
      {
         // Outputs the remaining inventory and how much was made by the end of the day
         outFile << product[i].PLU << " "
            << product[i].pName << " "
            << product[i].saleType << " "
            << product[i].pricePer << " "
            << product[i].iLev << " " << endl;
      }

      outFile << endl << "TOTAL SALES: $" << totalSales;
   }
   else
   {
      cout << "Error: Cannot Open File\n";
      return 1;
   }

   outFile.close(); // close output file
   return 0;

} // end updateInven function