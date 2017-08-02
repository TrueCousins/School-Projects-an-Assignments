#pragma once

class Question
{
   char *question;
   char *option_a;
   char *option_b;
   char *option_c;
   char *option_d;

public:
   // Accessor
   char *getQuestion(){ return question; }
   char *getOption_a(){ return option_a; }
   char *getOption_b(){ return option_b; }
   char *getOption_c(){ return option_c; }
   char *getOption_d(){ return option_d; }

   // Mutator
   void setQuestion(char *g);
   void setOption_a(char *g);
   void setOption_b(char *g);
   void setOption_c(char *g);
   void setOption_d(char *g);

   // Constructor
   Question()
   {
      question = nullptr;
      option_a = nullptr;
      option_b = nullptr;
      option_c = nullptr;
      option_d = nullptr;
   }

   // Deconstructor
   ~Question()
   {
      // frees memory
      delete [] question;
      delete [] option_a;
      delete [] option_b;
      delete [] option_c;
      delete [] option_d;
   }

   
};

class answer
{
   char *qNum;
   char rightAnswer;
   char *explanation;

public:
   // Accessor
   char *getQNum(){ return qNum; }
   char getRightAnswer(){ return rightAnswer; }
   char *getExplanation(){ return explanation; }

   // Mutator
   void setQNum(char *g);
   void setRightAnswer(char g){ rightAnswer = g; }
   void setExplanation(char *g);

   // Constructor
   answer()
   {
      qNum = nullptr;
      explanation = nullptr;
   }

   // Deconsturctor
   ~answer()
   {
      // Frees memory
      delete[] qNum;
      delete[] explanation;
   }
};
