#pragma once

// Bank class declaration
class Bank
{

private:
   int accountNumber;
   double balance;

public:
   // Constructors
   Bank();
   Bank(int x, double y);


   bool withdraw(double x);      
   void deposit(double x);
   int getAccountNumber() const;
   double getBalance() const;

   // Non member function
   friend void displayAccountInfo(Bank obj);

   void setAccountNumber(int x);
   void setBalance(double x);
   
   ~Bank();
};

