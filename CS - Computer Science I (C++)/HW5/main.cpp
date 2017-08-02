/*
Colleen Cousins
CS 1337.002
April 16th, 2015

DESCRIPTION:
A simple program of a banking system. The program uses classes for the bank system and user will
be able to create an account, withdraw, and deposit.

*/

#include <iostream>
#include "Bank.h"    // Need for the Bank Class 
#include <ctime>     // for the time function
#include <cstdlib>   // for rand and srand
#include <cctype>
using namespace std;

// Will display the Account Number and Balance
// Is able to use data members from Bank.h
void displayAccountInfo(Bank obj)
{

   cout << "Account Number: " << obj.getAccountNumber() << endl;
   cout << "Balance: $" << obj.getBalance() << endl;

}

int main()
{

   Bank *accnt1 = new Bank();             // An account needed to be created
   Bank *accnt2 = new Bank(1111, 1000);   // An account needed to be created, but already has an account number and balance

   char choice;      // Holds the user's choice for the menu and if a user or not
   double with;      // Holds the amount the user wants to withdraw
   double dep;       // Holds the amount the user wants to deposit
   int accountN;     // Holds the user input for account number
   double bal;       // Holds the users initial balance
      
   // For Account 1
   do
   {
      // Ask if new user or not
      cout << "Are you a new user? Y = Yes, N = No, or 0 to Exit\n";
      cin >> choice;

      // If new user
      if (choice == 'y' || choice == 'Y')
      {
         unsigned seed = time(0);
         srand(seed);

         // Assigns a random four digit Account Number
         accountN = (rand() % (9999 - 1000 + 1))+1000;
         accnt1->setAccountNumber(accountN);
         cout << "Your Account Number is " << accnt1->getAccountNumber() << endl;

         cout << "Enter the amount you wish to deposit for your initial balance: ";
         cin >> bal;

         // Input validation
         while (bal < 0)
         {
            cout << "Error: Please enter an amount that is 0 or greater: ";
            cin >> bal;
         }

         // sets initial balance
         accnt1->setBalance(bal);

         // Displays Menu for the user
         do
         {
            cout << endl << endl;
            cout << "Welcome to Your Account!\n";
            cout << "a) Withdraw\n";
            cout << "b) Deposit\n";
            cout << "c) Show Balance\n";
            cout << "d) Exit\n";
            cin >> choice;

            switch (tolower(choice))
            {
            case 'a':
               cout << "Enter the amount to be Withdrawn: ";
               cin >> with;

               // Input validation
               while (with < 0)
               {
                  cout << "Error: Please enter an amount that is 0 or greater: ";
                  cin >> with;
               }

               accnt1->withdraw(with);
               break;
            case 'b':
               cout << "Enter the amount you wish to deposit: ";
               cin >> dep;

               // Input validation
               while (dep < 0)
               {
                  cout << "Error: Please enter an amount that is 0 or greater: ";
                  cin >> dep;
               }
               accnt1->deposit(dep);
               break;
            case 'c':
               displayAccountInfo(*accnt1);
               break;
            case 'd':
               cout << "EXIT\n" << endl;
               break;
            default:
               "Invalid Option!";
               break;
            }

            } while (choice != 'd');
         }

         // If old user
         else if (choice == 'n' || choice == 'N')
         {
            cout << "Welcome Customer!\n";
            cout << "Please enter your account number: ";
            cin >> accountN;

            // Input validation for account number
            while (accountN != accnt2->getAccountNumber())
            {
               cout << "Invalid Account Number. Please try again or Press 0 to exit\n";
               cin >> accountN;

               if (accountN == 0)
                  break;            // If user wants to exit
            }

            do
            {
               if (accountN == 0)
                  break;            // If user wants to exit

               cout << endl << endl;
               cout << "Welcome to Your Account!\n";
               cout << "a) Withdraw\n";
               cout << "b) Deposit\n";
               cout << "c) Show Balance\n";
               cout << "d) Exit\n";
               cin >> choice;

               switch (tolower(choice))
               {
               case 'a':
                  cout << "Enter the amount to be Withdrawn: ";
                  cin >> with;

                  // Input validation
                  while (with < 0)
                  {
                     cout << "Error: Please enter an amount that is 0 or greater: ";
                     cin >> with;
                  }
                  accnt1->withdraw(with);
                  break;
               case 'b':
                  cout << "Enter the amount you wish to deposit: ";
                  cin >> dep;

                  // Input validation
                  while (dep < 0)
                  {
                     cout << "Error: Please enter an amount that is 0 or greater: ";
                     cin >> dep;
                  }
                  accnt1->deposit(dep);
                  break;
               case 'c':
                  displayAccountInfo(*accnt1);
                  break;
               case 'd':
                  cout << "EXIT\n" << endl;
                  break;
               default:
                  "Invalid Option!";
                  break;

               }
            } while (choice != 0);
      }
      else
         cout << "Exit";   
   }while (choice != 'd');

   // For Account 2
   do
   {
      cout << "Are you a new user? Y = yes, N = No, or 0 to exit\n";
      cin >> choice;

      // If New User
      if (choice == 'y' || choice == 'Y')
      {
         unsigned seed = time(0);
         srand(seed);

         accountN = (rand() % (9999 - 1000 + 1)) + 1000;
         accnt2->setAccountNumber(accountN);
         cout << "Your Account Number is " << accnt1->getAccountNumber() << endl;

         cout << "Enter the amount you wish to deposit for your initial balance: ";
         cin >> bal;

         // Input validation
         while (bal < 0)
         {
            cout << "Error: Please enter an amount that is 0 or greater: ";
            cin >> bal;
         }

         // sets initial balance
         accnt1->setBalance(bal);

         do
         {
            cout << endl << endl;
            cout << "Welcome to Your Account!\n";
            cout << "a) Withdraw\n";
            cout << "b) Deposit\n";
            cout << "c) Show Balance\n";
            cout << "d) Exit\n";
            cin >> choice;
            cout << endl;

            switch (tolower(choice))
            {
            case 'a':
               cout << "Enter the amount to be Withdrawn: ";
               cin >> with;

               // Input validation
               while (with < 0)
               {
                  cout << "Error: Please enter an amount that is 0 or greater: ";
                  cin >> with;
               }
               accnt1->withdraw(with);
               break;
            case 'b':
               cout << "Enter the amount you wish to deposit: ";
               cin >> dep;

               // Input validation
               while (dep < 0)
               {
                  cout << "Error: Please enter an amount that is 0 or greater: ";
                  cin >> dep;
               }
               accnt1->deposit(dep);
               break;
            case 'c':
               displayAccountInfo(*accnt1);
               break;
            case 'd':
               cout << "EXIT\n" << endl;
               break;
            default:
               "Invalid Option!";
               break;
            }

         } while (choice != 'd');
      }
      
      // If old user
      else if (choice == 'n' || choice == 'N')
      {
         cout << "Welcome Customer!\n";
         cout << "Please enter your account number: ";
         cin >> accountN;
         cout << endl;

         // Input validation for account number
         while (accountN != accnt2->getAccountNumber())
         {
            cout << "Invalid Account Number. Please try again or Press 0 to exit\n";
            cin >> accountN;

            if (accountN == 0)
               break;            // If user wants to exit
         }

         do
         {
            if (accountN == 0)
               break;           // If user wants to exit 

            cout << endl << endl;
            cout << "Welcome to Your Account!\n";
            cout << "a) Withdraw\n";
            cout << "b) Deposit\n";
            cout << "c) Show Balance\n";
            cout << "d) Exit\n";
            cin >> choice;

            switch (tolower(choice))
            {
            case 'a':
               cout << "Enter the amount to be Withdrawn: ";
               cin >> with;
               // Input validation
               while (with < 0)
               {
                  cout << "Error: Please enter an amount that is 0 or greater: ";
                  cin >> with;
               }
               accnt2->withdraw(with);
               break;
            case 'b':
               cout << "Enter the amount you wish to deposit: ";
               cin >> dep;
               // Input validation
               while (dep < 0)
               {
                  cout << "Error: Please enter an amount that is 0 or greater: ";
                  cin >> dep;
               }
               accnt2->deposit(dep);
               break;
            case 'c':
               displayAccountInfo(*accnt2);
               break;
            case 'd':
               cout << "EXIT\n" << endl;
               break;
            default:
               "Invalid Option!";
               break;

            }

         } while (choice != 'd');
      }
      else
         cout << "EXIT\n";   

   } while (choice != '0');

   // Free dynamically allocated memory
   delete accnt1;
   delete accnt2;
   accnt1 = nullptr;
   accnt2 = nullptr;

   return 0;
} // end main function
