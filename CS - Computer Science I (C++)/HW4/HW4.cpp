/*
Colleen Cousins
CS 1337.002
March 1st, 2015

DESCRIPTION:
A program that displays a menu for the user to create and initialize a dynamic array, calculate the min, max, and
average of the array, and search for an element in the array.
*/
#include <iostream>
#include <iomanip>
#include <cstdlib>         // Needed for rand()
#include <ctime>           // Needed for time

int *createArray(int &size);                                               // creates an array of random numbers
void findStats(int *arr, int size, int &min, int &max, double &average);   // finds the min, max, and average in the array
int *searchElement(int *arr, int size, int *element);                      // will search for an integer in the array that the user inputs 

using namespace std;

int main()
{
   int choice;                   // holds the users menu choice
   int size;                     // holds the integer the user inputs for array size
   int *arr1 = nullptr;          // the pointer of the array


   int min = 0, max = 0;         // hoids the min and max
   double average = 0.0;         // holds the average

   int inElement = 0;            // holds the integer input by user to search for
   int *element = nullptr;       // pointer of the element
   int *position = nullptr;      // pointer of the position of the element


   // Displays Menu
   cout << "\t\tMENU\n";

   cout << "1) Create and initialize a dynamic array\n"
      << "2) Display statistics on the array\n"
      << "3) Search for an element\n"
      << "4) Quit\n\n";

   cout << "Please enter your choice: ";
   cin >> choice;

   // Quit program in the beginning if needed
   if (choice == 4)
      return 0;

   // Input validation, makes sure choice 1 is entered first to get array
   while (choice != 1)
   {
      cout << "Error. Please enter 1 first or 4 to exit: ";
      cin >> choice;

      // Quit program early
      if (choice == 4)
         return 0;
   }

   if (choice == 1)
   {
      cout << "Please enter an integer value between 1 and 100: ";
      cin >> size;

      // Input Validation
      while (size < 1 || size > 100)
      {
         cout << "Error. Please enter an integer value between 1 and 100:";
         cin >> size;
      }

      arr1 = createArray(size);     // creates array and stores address in arr1

      cout << "\nArray was successfully created!!!\n";
      /*
      //CHECKS TO SEE IF CREATEARRAY WORKS
      // Displays the array, the element, value, and address
      for (int i = 0; i < size; i++)
      {
      cout << "Element " << i << ": " << arr1[i] << " Addresses: " << arr1 << "  " << &arr1[i] << endl;
      }*/
      cout << endl << endl;
   }


   // Loops for the rest of the menu
   do
   {
      // Displays menu again
      cout << "\t\tMENU\n";

      cout << "1) Create and initialize a dynamic array\n"
         << "2) Display statistics on the array\n"
         << "3) Search for an element\n"
         << "4) Quit\n\n";

      cout << "Please enter your choice: ";
      cin >> choice;

      // Input Validation
      while (choice < 1 || choice > 4)
      {
         cout << "Please enter a valid menu choice: ";
         cin >> choice;
      }


      if (choice == 1)
      {
         cout << "Please enter an integer value between 1 and 100: ";
         cin >> size;

         // Input validatoin
         while (size < 1 || size > 100)
         {
            cout << "Error. Please enter an integer value between 1 and 100:";
            cin >> size;
         }

         arr1 = createArray(size);

         cout << "\nArray was successfully created!!!\n";
         /*
         //CHECKS TO SEE IF CREATEARRAY WORKS
         // Displays the array, the element, value, and address
         for (int i = 0; i < size; i++)
         {
         cout << "Element " << i << ": " << arr1[i] << " Addresses: " << arr1 << "  " << &arr1[i] << endl;
         }*/
         cout << endl << endl;
      }
      else if (choice == 2)
      {

         findStats(arr1, size, min, max, average);    // finds the min, max, and average


         // Displays results
         cout << endl << "Max: " << max << endl;
         cout << "Min: " << min << endl;
         cout << "Average: " << average << endl << endl;


         cout << endl << endl;
      }
      else if (choice == 3)
      {
         cout << "Please enter an integer value to search for in the array: ";
         cin >> inElement;

         element = &inElement;

         position = searchElement(arr1, size, element);


         if (*position != -1)
         {
            cout << endl << inElement << " is found in element " << *position << " of the array.\n";

            cout << endl << endl;
         }
         else
            cout << "\n\nCannot be found" << endl << endl;

      }


   } while (choice != 4);


   // free arr1 memory
   delete[] arr1;
   arr1 = 0;
   return 0;
}  // ends main function



int *createArray(int &size)
{
   int *arr = nullptr;           // pointer to an int
   arr = new int[size];          // dynamicall allocate the array
   srand(time(0));               // random numbers

   // assigns random number to array
   for (int i = 0; i < size; i++)
   {
      arr[i] = rand() % 101;
   }

   return arr;    // returns pointer of the array

}  // ends createArray function


void findStats(int *arr, int size, int &min, int &max, double &average)
{
   max = arr[0];
   min = arr[0];
   double total = 0.0;

   // searches for max and min of the array
   for (int i = 0; i < size; i++)
   {
      if (arr[i] > max)
         max = arr[i];

      if (arr[i] < min)
         min = arr[i];

      total += arr[i]; // keeps track of total
   }

   // calculates the average
   average = total / size;

}  // ends findStats function

int *searchElement(int *arr, int size, int *element)
{


   int *position = nullptr; // pointer to an int
   int i = 0;

   for (; i < size; i++)
   {
      if (*element == arr[i])
      {
         position = &i;    // stores address of i in position

         return position;  // returns address
      }

   }

   // returns if no similar number is found
   i = -1;
   position = &i;

   return position;

}  // ends searchElement function
