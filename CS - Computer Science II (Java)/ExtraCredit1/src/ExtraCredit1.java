/*
Colleen Cousins
CS 2336.001
Due October 10, 2015
Write a recursive method that returns the number of the uppercase letters in a
string.
*/
import java.util.Scanner;

public class ExtraCredit1 {
    
    public static void main(String[] args) {
        
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a string: ");
        String s = input.nextLine();
        System.out.println("The uppercase letters in " + s + " is " + countUppercase(s));
        
    }
    
    public static int countUppercase(String str)
    {
        return countUppercase(str, str.length() - 1);
    }

  public static int countUppercase(String str, int high)
  {
      
      if (high < 0)
          return 0;
      else
          return countUppercase(str, high - 1) +
          (Character.isUpperCase(str.charAt(high)) ? 1 : 0);
  }
}
