/*
Colleen Cousins
CS 1337.002
January 23-2015
DESCRIPTION:
Find and Fix the errors of the program. 
*/
#include <iostream>
#include <string>       // Needed to use string
using namespace std; 

int main()
{ // placed left brace on its own line
   float value1 = 3000000000;    // stores the value 3000000000,
                                 // changed data type from int to float
   double value2 = 0.0;          // stores the value 0.0, had to initialize
   int value3 = 0;               // stores the value 0, had to initialize
   char middleInitial = 'G';     // stores middle initial, changed "" to ''
   bool me4 = true;              // stores the value true, changed boolean to bool
                                 // also changed variable name 4me to me4
   double capacity = 54.89;      // stores the capapacity
   string name = "Michelle";     // stores the name Michelle, changed '' to ""

   // Displays results
   cout << "value1 = " << value1 << endl;

   // value2 now = 54.89
   value2 = capacity;
   
   // continues displaying results
   cout << "value2 = " << value2 << endl;
   cout << "middleInitial = " << middleInitial << endl;
   value3 = 13; // assigns 13 to value3, switched value3 and 13 around
   cout << "value3 = " << value3 << endl;
   cout << "me4 = " << me4 << endl;    //changed the vairable name from 4me to me4
   cout << "name = " << name << endl; 
   
   return 0;
} // end main function