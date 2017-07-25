/*
Colleen Cousins
CS 3345.002
Due February 14, 2016
This program asks the user to enter an integer. It will then calculate the 
function c(n) and d(n). The function c(n) is a recursive function.
c(n) is the summation of c(i-1)*c(n-i), i = 0 to n, and d(n0 = (2n)!/n!(n+1)!. 
The program will then print out the sums of each function and should show that
d(n) and c(n) are true. 
*/
import java.util.Scanner;   // Needed to used scanner

public class HW1_Pt1 {

    public static void main(String[] args) {
       
       
        Scanner input = new Scanner(System.in);
        
        int n = 0;
        
        // Input validation 
        while(n <= 0)
        {
            // User input an integer
            System.out.println("Enter a number greater than or equal to 0: ");
            n = input.nextInt();  
        }
        
        // Displays result and calls on the c and d method 
        System.out.println("c(" + n + ") = " + c(n));
        System.out.println("d(" + n + ") = " + d(n));
        
        // Displays if c(n) and d(n) are equal or not
        if(c(n) == d(n))
            System.out.println("c(n) = d(n) is true");
        else
            System.out.println("c(n) = d(n) is false");
        
        // System.out.println("c(10) is " + c(10));
        // System.out.println("d(10) is " + d(10));
        
        
        
    } // END MAIN
    
    
    // 1, 1, 2, 5, 14, 132, 429, 1430, 4862, 16796
    static long c(int n)
    {
        int result = 0;
        
        if(n <= 1) // base case
            return 1;
        
        // Recursively works out c(n)
        for (int i = 0; i < n; i++)
            result += c(i) * c(n - i - 1);
        
        return result;
        
    } // END C METHOD
    
   
    static long d(int n)
    {
        int result = 1;
        int j = n * 2;  // numerator
        
        if(n > (j - n))
        {
            n = j - n;
        }
            
        for(int i = 0; i < n; ++i)
        {
            result *= (j-i);
            result /=(i+1);
        }
        
        return result/( n + 1);
      
    }// END D METHOD
    
}
