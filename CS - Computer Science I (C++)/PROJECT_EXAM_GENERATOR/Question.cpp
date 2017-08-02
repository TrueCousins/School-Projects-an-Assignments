#include "Question.h"
#include <iostream>
#include <fstream>
#include <string>
using namespace std;

// Functions for Question class
//-----------------------------------------------
// All Question class member functions help set the c-string
void Question::setQuestion(char *g)
{
   int i = 0;
   while (*(g + i) != '\0')
   {
      i++;
   }

   question = new char[i + 1];
   i = 0;
   while (*(g + i) != '\0')
   {
      *(question + i) = *(g + i);
      i++;
   }
   question[i] = '\0';

   //cout << question;
}
void Question::setOption_a(char *g)
{
   int i = 0;
   while (*(g + i) != '\0')
   {
      i++;
   }

   option_a = new char[i + 1];
   i = 0;
   while (*(g + i) != '\0')
   {
      *(option_a + i) = *(g + i);
      i++;
   }
   option_a[i] = '\0';
}
void Question::setOption_b(char *g)
{
   int i = 0;
   while (*(g + i) != '\0')
   {
      i++;
   }

   option_b = new char[i + 1];
   i = 0;
   while (*(g + i) != '\0')
   {
      *(option_b + i) = *(g + i);
      i++;
   }
   option_b[i] = '\0';
}
void Question::setOption_c(char *g)
{
   int i = 0;
   while (*(g + i) != '\0')
   {
      i++;
   }

   option_c = new char[i + 1];
   i = 0;
   while (*(g + i) != '\0')
   {
      *(option_c + i) = *(g + i);
      i++;
   }
   option_c[i] = '\0';
}
void Question::setOption_d(char *g)
{
   int i = 0;
   while (*(g + i) != '\0')
   {
      i++;
   }

   option_d = new char[i + 1];
   i = 0;
   while (*(g + i) != '\0')
   {
      *(option_d + i) = *(g + i);
      i++;
   }
   option_d[i] = '\0';
}

// Functions for answer class
//-----------------------------------------------
// All answer class member function helps set the c-string
// Sets the c-sting
void answer::setQNum(char *g)
{
   int i = 0;
   while (*(g + i) != '\0')
   {
      i++;
   }

   qNum = new char[i + 1];
   i = 0;
   while (*(g + i) != '\0')
   {
      *(qNum + i) = *(g + i);
      i++;
   }
   qNum[i] = '\0';
}


void answer::setExplanation(char *g)
{
   int i = 0;
   while (*(g + i) != '\0')
   {
      i++;
   }

   explanation = new char[i + 1];
   i = 0;
   while (*(g + i) != '\0')
   {
      *(explanation + i) = *(g + i);
      i++;
   }
   explanation[i] = '\0';
}



