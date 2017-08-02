/*
Colleen Cousins
CS 1337.002
January 22-2015
DESCRIPTION:
Write a C++ program that displays a right triangle using 0's to
create it.
*/

#include <iostream>

using namespace std; 

int main()
{
   char zero = '0';     // stores a 0 character literal


   // Outside For loop counts the height
   for (int height = 0; height <= 5; height++)
   {
      // Inside for loop will display and count the number of 0's
      // displayed for each row
      for (int x = 1; x <= height; x++)
      {
         cout << zero; 
      }
      cout << endl;
   }

   
   return 0;
} // end main function