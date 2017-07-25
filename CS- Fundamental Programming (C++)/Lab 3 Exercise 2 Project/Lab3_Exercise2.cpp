// Lab 3 Exercise 2
// Calculate MPH (Miles Per Hour).

#include <iostream>
#include <iomanip>                  // needed to use setprecision, fixed, and setw

using namespace std;

int main()
{
   double startMileage,             // To hold the starting mileage
          endMileage,               // To hold the endling mileage
          totalHours,               // To hold the total hours
          totalMiles,               // To hold the total miles
          mph;                      // To hold the MPH

   cout << fixed << setprecision(1);
   

   // Input starting mileage, ending mileage, and number of hours 
   cout << "Enter the starting mileage: ";
   cin >> startMileage;
   cout << "Enter the ending mileage: ";
   cin >> endMileage;
   cout << "Enter the number of hours traveled: ";
   cin >> totalHours;


   // Calculate the total miles and MPH
   totalMiles = endMileage - startMileage;
   mph = totalMiles / totalHours;
   cout << endl; 


   // Display the total miles and MPH
   cout << "Total miles\t" << setw(6) << totalMiles << endl;
   cout << "Miles/Hour\t" << setw(6) << mph << endl;

 

   return 0;
}
