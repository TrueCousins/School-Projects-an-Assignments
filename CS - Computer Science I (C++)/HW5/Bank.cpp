// Implementation file for the Bank class
#include "Bank.h"
#include <iostream>
using namespace std;

// Returns account number and balance when called to main
int Bank::getAccountNumber() const { return accountNumber; }
double Bank::getBalance() const { return balance; }

// Constructor for Bank, sets default
Bank::Bank()
{
   accountNumber = 9999;
   balance = 0.0;
}

// Constructor, allows the account number and balance to change
Bank::Bank(int x, double y)
{
   accountNumber = x;
   balance = y;
}

// Withdraw money function
bool Bank::withdraw(double x)
{
   // If not enough money
   if (balance < x)
   {
      cout << "Insufficient balance\n";
      balance = 0.0; // Sets balance to 0
      return false;
   }
   else
   {
      balance -= x;
      return true;
   }
      
}

// Deposit function
void Bank::deposit(double x)
{
   balance += x;
} 



// Sets account number when needed
void Bank::setAccountNumber(int x){ accountNumber = x; }
void Bank::setBalance(double x){ balance = x; }


Bank::~Bank()
{
}
