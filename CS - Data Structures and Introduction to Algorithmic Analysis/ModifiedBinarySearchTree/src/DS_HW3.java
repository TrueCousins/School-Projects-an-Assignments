/*
Description:
This is the file for testing ModifiedBinarySearchTree.
When running program, input the Integer files first. Then input the Float file.
*/


import java.util.*;     // Needed for Scanner
import java.io.*;       // Needed for file

public class DS_HW3  {
    
    public static void main(String[] args) throws Exception {
        
        // Will hold inputA1 to build tree
        // Assuming the input file is Integers
        ModifiedBinarySearchTree<Integer> treeA1 = new ModifiedBinarySearchTree<>();
        
        // Will hold inputB2 to build tree
        // Assuming the input file is Floats
        ModifiedBinarySearchTree<Float> treeA2 = new ModifiedBinarySearchTree<>();
        
        // List of inputB1 and input B2 to be compared to ModifiedBinarySearchTree
        ArrayList inputB1 = new ArrayList <>();
        ArrayList inputB2 = new ArrayList <>();
        
        // Make treeA1 and treeA2 empty
        treeA1.makeEmpty();
        treeA2.makeEmpty();
        
        // Input Integer text file
        Scanner input = new Scanner(System.in);
        System.out.println("Enter inputA1.text location or file name: ");
        String filename = input.nextLine();
        // C:\\Users\colle\Documents\text files\inputA1.txt
        
        System.out.println();
        // Inserts the Integers into a tree
        treeA1.treeInput(filename);
       
        // Prints the tree out in inorder
        System.out.print("InputA1: ");
        treeA1.printTree();
        
        // Find max and min of treeA1
        System.out.println();
        treeA1.findMax();
        treeA1.findMin();
        System.out.println();
        
        // Input Integer text file 
        System.out.println("Enter inputB1.text location or file name: ");
        filename = input.nextLine();
        // C:\\Users\colle\Documents\text files\inputB1.txt
        
        System.out.println();
        // Creates fileI and fileS instances 
        java.io.File fileI = new java.io.File(filename);
        
        // Create scanner for the files
        Scanner inputI = new Scanner(fileI);
        
        // Compares inputA1 and inputB1
        // Contain has the remove and find method inside of it
        // If the array contains a number that is in the tree, the remove method
        // will remove it from the tree. If it is not in the tree, the contains will
        // state if not in tree.
        while(inputI.hasNext())
        {
            treeA1.contains(inputI.nextInt()); 
        }
        inputI.close();     // close file inputI 
         
        System.out.println();
        // Print the tree 
        System.out.println("The binary search tree is now: ");
        treeA1.printTree();
        System.out.println("\n");
        
        //----------------------------------------------------------------------
        Scanner input2 = new Scanner(System.in);
        System.out.println("Enter inputA2.text location or file name: ");
        String filename2 = input2.nextLine();
        // C:\\Users\colle\Documents\text files\inputA2.txt
        
        
        treeA2.treeInput(filename2);
       
        System.out.print("InputA2: ");
        treeA2.printTree();
        System.out.println();
        treeA2.findMax();
        treeA2.findMin();
        
        System.out.println(); 
        System.out.println("Enter inputB2.text location or file name: ");
        filename2 = input2.nextLine();
        // C:\\Users\colle\Documents\text files\inputB2.txt
        System.out.println();
        
        // Creates fileI and fileS instances 
        java.io.File fileI2 = new java.io.File(filename2);
        
        // Create scanner for the files
        Scanner inputI2 = new Scanner(fileI2);
        
        // Compares inputA2 and inputB2
        // Contain has the remove and find method inside of it
        // If the array contains a number that is in the tree, the remove method
        // will remove it from the tree. If it is not in the tree, the contains will
        // state if not in tree.
        while(inputI2.hasNext())
        {
            treeA2.contains(inputI2.nextFloat());
        }
        inputI2.close();     // close file inputI2 
         
        System.out.println();
        System.out.println("The binary search tree is now: ");
        treeA2.printTree();
        System.out.println("\n");
        
    }
    
}
