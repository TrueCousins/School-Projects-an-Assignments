
import java.util.*;

public class MergeVSInsertionSort {

    public static void main(String[] args) {
        // TODO code application logic here
        Integer[] case1 = new Integer[100000];
        Integer[] case2 = new Integer[100000];
        Integer[] case3 = new Integer[100000];
        
        //int[] case4 = new int[100];
        //int[] case5 = new int[100];
        //int[] case6 = new int[100];
        Random rand = new Random();
        
        
        
        // Case 1: Ascending order
        for (int i = 0; i < case1.length; i++)
            case1[i] = rand.nextInt(100) + 1;
        Arrays.sort(case1);
        
       
        System.out.println("Case:1 and 4");
        for (int i = 0; i < case1.length; i++)
        {
            
            System.out.print(case1[i] + " ");
           
        }
      
         System.out.println();
         
        // Case 2: Descending Order
        System.out.println("Case:2 ");
        for (int i = case1.length - 1; i >= 0; i--)
        {
            case2[i] = case1[i];
            System.out.print(case2[i] + " ");
        }
           
        System.out.println();
        
        
        // Case 3: Unsorted Random Order
        System.out.println("Case: 3 ");
         for (int i = 0; i < case1.length; i++)
            case3[i] = rand.nextInt(100) + 1;
         
         for (int i = 0; i < case3.length; i++)
            System.out.print(case3[i] + " ");
        
         System.out.println();
         
        /* 
        // Merge Sort Times
        System.out.print("Runtime 1: ");
        mergeSort(case1);
        System.out.print("Runtime 2: ");
        mergeSort(case2); 
        System.out.print("Runtime 3: ");
        mergeSort(case3);
         */
         
         
        // Insertion Sort Times
        System.out.print("Runtime 1: ");
        insertionSort(case1);
        System.out.print("Runtime 2: ");
        insertionSort(case2); 
        System.out.print("Runtime 3: ");
        insertionSort(case3);
        
         
                
        //Integer[] a = {2, 6, 3, 5, 1};
	//	mergeSort(a);
	//	System.out.println(Arrays.toString(a));
    }
    
   	public static void mergeSort(Comparable [ ] a)
	{
            //System.out.println("1");
               
                long startTime = System.currentTimeMillis();
                long startTime2 = System.nanoTime();
		Comparable[] tmp = new Comparable[a.length];
		mergeSort(a, tmp,  0,  a.length - 1);
                
                long endTime = System.currentTimeMillis();
                long endTime2 = System.nanoTime();
                long totalTime = endTime - startTime;
                long totalTime2 = endTime2 - startTime2;
                System.out.println(totalTime);
                System.out.println("NANO: " + totalTime2);
              //  System.out.println("2");
	}


	private static void mergeSort(Comparable [ ] a, Comparable [ ] tmp, int left, int right)
	{
           // System.out.println("3");
		if( left < right )
		{
			int center = (left + right) / 2;
			mergeSort(a, tmp, left, center);
			mergeSort(a, tmp, center + 1, right);
			merge(a, tmp, left, center + 1, right);
		}
               // System.out.println("4");
	}


    private static void merge(Comparable[ ] a, Comparable[ ] tmp, int left, int right, int rightEnd )
    {
       // System.out.println("5");
        int leftEnd = right - 1;
        int k = left;
        int num = rightEnd - left + 1;

        while(left <= leftEnd && right <= rightEnd)
            if(a[left].compareTo(a[right]) <= 0)
                tmp[k++] = a[left++];
            else
                tmp[k++] = a[right++];

        while(left <= leftEnd)    // Copy rest of first half
            tmp[k++] = a[left++];

        while(right <= rightEnd)  // Copy rest of right half
            tmp[k++] = a[right++];

        // Copy tmp back
        for(int i = 0; i < num; i++, rightEnd--)
            a[rightEnd] = tmp[rightEnd];
       // System.out.println("6");
    }
       
    public static void insertionSort(Integer[] arr) {
        
      int i, j, newValue;
      long startTime = System.currentTimeMillis();
      long startTime2 = System.nanoTime();
      
      for (i = 1; i < arr.length; i++) {

            newValue = arr[i];

            j = i;

            while (j > 0 && arr[j - 1] > newValue) {

                  arr[j] = arr[j - 1];

                  j--;

            }

            arr[j] = newValue;
            

      }
      
                long endTime = System.currentTimeMillis();
                long endTime2 = System.nanoTime();
                long totalTime = endTime - startTime;
                long totalTime2 = endTime2 - startTime2;
                System.out.println(totalTime);
                System.out.println("NANO: " + totalTime2);
}
}
