/*
Colleen Cousins
CS 1337.002
May 1st, 2015

DESCRIPTION:
A program that displays a menu for the user to generate a random test with the number
of questions the user wants that dont exceed the bounds. Then a report will be printed 
to a file at the end 
*/

#include <iostream>
#include "Question.h"      // Needed to access the classes
#include <fstream>         // Needed to read files
#include <cstdlib>         // Needed to use rand()
#include <ctime>           // Needed to use time
#include <string>          // Needed to use strings
#include <cctype>          // Needed to use the toupper() functoin

using namespace std;

// Function Prototypes
int countNumberOfQuestions(char *filename);
Question *readQFile(char *filename, int n);
answer *readAFile(char *filename, int n);
int *generateRandNum(int k, int n);
char *randomTest(Question *all, int *random, int k, int n);
void displayScore(char *userA, answer *key, int *randomQ, int k);
void generatedReport(int k, int *random, char *userA, answer *key, Question *allQ, char *fname);

int main()
{
   // c-string of the txt file names
   char *fname = "questions.txt";
   char *fname2 = "key.txt";
   char *fname3 = "exam.txt";

   // Stores the number of questions that were counted from the function
   int questionNum = countNumberOfQuestions(fname);
   //cout << "Questions: " << questionNum << endl;

   char option;               // Users choice for menu
   int k = -9999;             // Input validation if user skips choice a first
   int *random = nullptr;     // int pointer to hold the random numbers generated
   
  // Dynamically allocated array to store all the questons
   Question *allQuestions = new Question[questionNum];
   allQuestions =  readQFile(fname, questionNum);
   
   // Dynamically allocated array to store all the answers
   answer *key = new answer[questionNum];
   key = readAFile(fname2, questionNum);
   
   // Dynamically allocated array to store the user's choice for the questoins
   char *userAnswer = new char[questionNum];
   
   // Display the menu
   do
   {
      cout << "\t\tWelcome!\n";
      cout << "a. Enter the number of questions\n";
      cout << "b. Start the exam review\n";
      cout << "c. Get score\n";
      cout << "d. Generate report\n";
      cout << "e. Exit\n";
      cin >> option;

      switch (tolower(option))
      {
      case 'a':
         // User inputs how many questions they want
         cout << "Please enter the number of questions you would like: ";
         cin >> k;

         // Input validation: Makes sure user input k does not go out of bounds of the number of questions in file
         while (k > questionNum || k <= 0)
         {
            cout << "Error: The number you have chosen is too much or is not Valid.\n";
            cout << "Please choose a number from 1 to " << questionNum << ":\n";
            cin >> k;
         }


         random = new int[k];
         random = generateRandNum(k, questionNum);
        
         break;
      case 'b':
         // Input validation if user doesn't choose a
         if (k == -9999)
         {
            cout << "Please enter the number of questions first.\n";
            break;
         }
         cout << "\t\tStarting Test" << endl << endl; 
         userAnswer = randomTest(allQuestions, random, k, questionNum);
         break;
      case 'c':
         // Input validation if user doesn't choose a
         if (k == -9999)
         {
            cout << "Please enter the number of questions first.\n";
            break;
         }
         // displays score
         displayScore(userAnswer, key, random, k);
         break;
      case 'd':
         // Will write report to exam.txt
         generatedReport(k, random, userAnswer, key, allQuestions, fname3);
         break;
      case 'e':
         cout << "Exit" << endl;
         // Frees all memory and sets back to null
         delete[] allQuestions;
         allQuestions = nullptr;
         delete[] key;
         key = nullptr;
         delete[] userAnswer;
         userAnswer = nullptr;
         delete[] random;
         random = nullptr;
         break;
      default:
         cout << "Invalid Option!";
         break;
      }
      
   } while (option != 'e');

   
   return 0;
}  // END Main function



// Counts the number of questions in the file
int countNumberOfQuestions(char *filename)
{
   int num = 0;          // keeps count of file lines
   string str;           // holds the place to count the lines
   ifstream infile;      // open the input file 

   // Input file opened
   infile.open(filename);
   if (infile)
   {
      // Reads each line
      while (getline(infile, str))
      {
         num++;   // increments each time a line is read
      }
   }
   else
      cout << "Cannot open the file.\n";

   infile.close();   // Close file 


   return num / 5;   // return the number of lines/5 to get the number of questoins

}  //END countNumberOfQuestoins function


// Stores questions.txt into dynamically allocated array
Question *readQFile(char *filename, int n)
{
   //cout << "N: " << n; 

   
   Question *arrInfo = nullptr;     // Dynamic array created to store info
   arrInfo = new Question[n];

   //int value;
   ifstream infile;

   const int SIZE = 300;            // Array size
   char str[SIZE];                  // Will hold the line of the Question or Answer
 
   infile.open(filename);           // Open question.txt

   if (infile)
   {
      //cout << "\t\tFile Read Successfully! " << endl << endl;

      // Stores questions.txt into the arrInfo
      for (int i = 0; i < 17; i++)
      {
         infile.getline(str, SIZE);
         arrInfo[i].setQuestion(str);
         //cout << "Q" << i+1 << ":" << arrInfo[i].getQuestion() << endl;

         infile.getline(str, SIZE);
         arrInfo[i].setOption_a(str);
         //cout << arrInfo[i].getOption_a() << endl;

         infile.getline(str, SIZE);
         arrInfo[i].setOption_b(str);
         //cout << arrInfo[i].getOption_b() << endl;

         infile.getline(str, SIZE);
         arrInfo[i].setOption_c(str);
         //cout << arrInfo[i].getOption_c() << endl;

         infile.getline(str, SIZE);
         arrInfo[i].setOption_d(str);
         //cout << arrInfo[i].getOption_d() << endl << endl;
      }

   }
   else
      cout << "Cannot open the file.\n";     // Displays if cannot open file

   infile.close();         // Closes file 
   return arrInfo;         // return array pointer

}  // END readQFile function


// Stores key.txt into dynamically allocated array
answer *readAFile(char *filename, int n)
{
   answer *arr = nullptr;     // Dynamic array created to store the key
   arr = new answer[n];

   const int SIZE = 300;      // Array size
   char str[SIZE];            // will help pass the question number and explanation info from key.txt
   char str2;                 // will pass the right answer to each questoin
   ifstream infile;           

   infile.open(filename);     // Opens the file to key.txt

   if (infile)
   {
      //cout << "\t\tFile Read Successfully! " << endl << endl;

      // Stores the data from the key.txt to the array, arr
      for (int i = 0; i < n; i++)
      {
         infile.getline(str, 12, ':');
         arr[i].setQNum(str);
         //cout << arr[i].getQNum();

         infile >> str2;
         arr[i].setRightAnswer(str2);
         //cout << arr[i].getRightAnswer();

         infile.getline(str, SIZE);
         arr[i].setExplanation(str);
         //cout << arr[i].getExplanation() << endl;

      }

   }
   else
      cout << "Cannot open the file.\n";  // Displays if cannot open file

   infile.close();      // Closes file 
   return arr;          // returns the answer pointer arr back to main

} // END readAFile function


// Generates the random question numbers for the test
int *generateRandNum(int k, int n)
{
   int *r = new int[k];       // dynamically allocated array to hold the random numbers
   int num = 0;               // will temporarily hold the random number
   bool check;                // Check to see if numbers repeat or not
   unsigned seed = time(0);     
   srand(seed);               


   for (int i = 0; i<k; i++)
   {
      do
      {
         num = rand() % n;    // Generates random number into num

         check = true;

         // Will check if numbers have been used or not
         for (int j = 0; j < i; j++)   
         {
            if (num == r[j])  //if number is already used
            {
               check = false; //set check to false
               break;         //no need to check the other elements of r[]
            }
         }

      } while (!check);       //loop until new, unique number is found
      r[i] = num;             //store the generated number in the array
   }

   return r;    // returns the int pointer back to main

} // END generateRandNum function


// Generates a randomized test with the number of questions input by user
char *randomTest(Question *all, int *random, int k, int n)
{
   char *userA = new char[n];    // Dynamically allocate the user answers

   // Displays the Queston, Question number, and answer choices
   // And the user's input 
   for (int i = 0; i < k; i++)
   {
      cout << "Q" << random[i] + 1 << ": " << all[random[i]].getQuestion() << endl;
      cout << "a) " << all[random[i]].getOption_a() << endl;
      cout << "b) " << all[random[i]].getOption_b() << endl;
      cout << "c) " << all[random[i]].getOption_c() << endl;
      cout << "d) " << all[random[i]].getOption_d() << endl;
      cin >> userA[i];

      // Input Validation
      while (tolower(userA[i]) < 'a' || tolower(userA[i]) > 'd')
      {
         cout << "Please enter A, B, C, or, D as your answer choice\n";
         cin >> userA[i];
      }
   }
   /*
   cout << "User's choice:\n";
   for (int i = 0; i < k; i++)
   {
   cout << userA[i] << endl;
   }
   */
   return userA;     // return the char pointer userA back to main

}  // END randomTest


// Counts the number of right answers the user got and Display the score
void displayScore(char *userA, answer *key, int *randomQ, int k)
{
   int right = 0;    // Holds the number of right answers the user got

   for (int i = 0; i < k; i++)
   {
      //cout << "User: " << toupper(userA[i]) << endl;
      //cout << "Key: " << key[randomQ[i]].getRightAnswer() << endl;

      // Compares the user choice to the key, if right then it will increment
      if (toupper(userA[i]) == key[randomQ[i]].getRightAnswer())
      {
         right++;
      }
   }
   // Displays score
   cout << "Your score is " << right << "/" << k << endl;

} // END displayScore


// Generate the report
void generatedReport(int k, int *random, char *userA, answer *key, Question *allQ, char *fname)
{
   ofstream outfile;

   
   /*
   cout << "\t\t\tYOUR REPORT\n";
   for (int i = 0; i < k; i++)
   {
      cout << "Q:" << random[i] + 1 << " " << allQ[random[i]].getQuestion() << endl << endl;

      cout << "Right Answer\t\t" << "Explanation" << endl;
      cout << key[random[i]].getRightAnswer() << "\t\t\t" << key[random[i]].getExplanation() << endl << endl;
      cout << "You chose " << static_cast<char>(toupper(userA[i])) << endl;
      cout << "Your Answer was";

      if (toupper(userA[i]) == key[random[i]].getRightAnswer())
      {
         cout << " correct." << endl;

      }
      else
         cout << " incorrect." << endl;
      cout << endl;
   }
   */


   outfile.open("exam.txt");     // opens exam.txt

   if (outfile)
   {
      // Writes out the question, questoin number, user answer, right answer, explanation, and if user was correct/incorrect
      outfile << "\t\t\tYOUR REPORT\n";
      for (int i = 0; i < k; i++)
      {
         outfile << "Q:" << random[i] + 1 << " " << allQ[random[i]].getQuestion() << endl << endl;

         outfile << "Right Answer\t\t" << "Explanation" << endl;
         outfile << key[random[i]].getRightAnswer() << "\t\t\t" << key[random[i]].getExplanation() << endl << endl;
         outfile << "You chose " << static_cast<char>(toupper(userA[i])) << endl;
         outfile << "Your Answer was";

         if (toupper(userA[i]) == key[random[i]].getRightAnswer())
         {
            outfile << " correct." << endl;

         }
         else
            outfile << " incorrect." << endl;
         outfile << endl;
      }
      cout << "Your report was successfully printed to exam.txt.\n";
   }
   else
      cout << "ERROR" << endl;      // Display if cannot find file

   outfile.close();        // Close file 

}  // END generatedReport functoin