/*
This program is a generic modified binary search tree. Each method is wriiten
in nonrecurisveley. 
Methods:
    1)  treeInput
    2)  makeEmpty
    3)  isEmpty
    4)  contains
    5)  findMax
    6)  findMin
    7)  find            
    8)  insert
    9)  remove       
    10) printTree


*/

import java.util.ArrayList; // Needed to read in inputs from file 
import java.util.Scanner;   // Needed to read in file
import java.util.Stack;     // Needed to print tree in inorder


public class ModifiedBinarySearchTree <E extends Comparable<E>> 
{
    
    //*************************************************************************************
    // METHODS                                                                            *
    //*************************************************************************************
    private BinaryNode<E> root;
    protected int size = 0;
    
   
    
    /** Returns the root of the tree */
    private BinaryNode<E> getRoot()             // might need to change private back to public
    {
        return root;
    }
    
    /** Get the number of nodes in the tree */
    public int getSize() 
    {
        return size;
    }
    
   
    private BinaryNode<E> createNewNode(E e)
    {
        return new BinaryNode<E>(e);
    }
    
    // The BinaryNode Class according to the book
    // maybe add a parent node
    private static class BinaryNode<E>
    {
        BinaryNode(E theElement)
        {
            this(theElement, null, null);
        }
        
        BinaryNode( E theElement, BinaryNode<E> lt, BinaryNode<E> rt)
        {
            element = theElement;
            left = lt;
            right = rt;
        }
        
        E element;
        BinaryNode<E> left;
        BinaryNode<E> right;
    }
    
    // ************************** 1) Input into tree ***************************
    public void treeInput(String fname) throws Exception   
    {
        //insert(e);
        
        // Creates fileI and fileS instances 
        java.io.File fileI = new java.io.File(fname);
        
        // Create scanner for the files
        Scanner inputI = new Scanner(fileI);
        
        // Holds input file numbers
        ArrayList inputFile = new ArrayList();
        
        // Reads input files and adds them to a list
         while(inputI.hasNext())
         {
             if(inputI.hasNextInt())
                 inputFile.add(inputI.nextInt());
             else
                 inputFile.add(inputI.nextFloat());
         }

         inputI.close();     // close file inputI 
         
         // Inserts array into the tree
         for(int x = 0; x < inputFile.size(); x++)
                insert((E) inputFile.get(x));
        
        System.out.println();
    }
  
    // ************************** 2) Make tree empty ***************************
    
    // Makes the tree empty
    public void makeEmpty()
    {
        root = null;
        size = 0;
    }
    
    // ************************** 3) Is tree empty? ****************************
    
    // Tests if the tree is empty
    public boolean isEmpty()
    {
        return root == null;
    }
    
    // ************************** 4) Does tree contain e?  *********************
    
    // Checks if a number is in the tree
    public boolean contains(E e)
    {
        BinaryNode<E> current = root;
        
        while(current != null)
        {
            if(e.compareTo(current.element) < 0)
            {
                current = current.left;
            }
            else if(e.compareTo(current.element) > 0)
            {
                current = current.right;
            }
            else
            {
                // If e is in the tree, remove it from the tree
                find(e);
                remove(e);
                System.out.println("Key " + e + " is removed");
                return true;
            }
                
        }
        // If e is not in the tree, print message
        find(e);
        System.out.println("Key " + e + " is not in the tree");
        return false;
    }
    /*
    private E contains(E t)      
    {
        BinaryNode<E> x = root;
        
        while(x != null)
        {
            int res = t.compareTo(x.element);
            if(res < 0)
                x = x.left;
            else if(res > 0)
                x = x.right;
            else
                return x.element;
        }
        return null;
        
    }*/
    
    // ************************** 5) Find max in tree **************************
    
    // Finds the max in tree and prints it
    public void findMax()
    {
        BinaryNode<E> t = root;
        
        if(t != null)
        {
            while(t.right !=null)
            {
                t = t.right;
            }
        }
        System.out.println("Max: " + t.element);
        
    
    /*
    public E findMax()
    {
        BinaryNode<E> t = root;
        if(t != null)
            while(t.right != null)
                t = t.right;
        return t.element;
    }
    */
    
    
    
    }
    
    // ************************** 6) Find min in tree **************************
    
    // Finds the min value in tree and prints it
    public void findMin()
    {
        BinaryNode<E> t = root;
        if(t != null)
        {
            while(t.left !=null)
            {
                t = t.left;
            }
        }
        System.out.println("Min: " +t.element);
    }
    
    
    /*
    public E findMin()
    {
        BinaryNode<E> t = root;
        if(t != null)
            while(t.left != null)
                t = t.left;
        return t.element;
    }*/
    
    
    
    // ************************** 7) Find **************************************
    
    // Finds a node in the tree and returns the node or null
    public E find(E e)
    {
        
        BinaryNode<E> current = root; // Start from the root

        while (current != null) 
        { 
            if (e.compareTo(current.element) < 0)
            {
                current = current.left;
            }
            else if (e.compareTo(current.element) > 0)
            {
                current = current.right;
            }
            else // element matches current.element
            {
                
                //System.out.println("in Search/find: key " + e + " is found." );
                return e; // Returns Element if found
            }
            
        }
        
        //System.out.println("in Search/find: key " + e + " is not found.");
        // Returns null if not found
        return null;
    }
    
    // ************************** 8) Insert into tree **************************
    
    // Inserts e into the tree
    public boolean insert(E e) 
    {
        if (root == null)
            root = createNewNode(e); // Create a new root
        else 
        {
          // Locate the parent node
            BinaryNode<E> parent = null;
            BinaryNode<E> current = root;
            
            while (current != null)
                if (e.compareTo(current.element) < 0) 
                {
                    parent = current;
                    current = current.left;
                }
                else if (e.compareTo(current.element) > 0)
                {
                    parent = current;
                    current = current.right;
                }
                else
                    return false; // Duplicate node not inserted

            // Create the new node and attach it to the parent node
            if (e.compareTo(parent.element) < 0)
                parent.left = createNewNode(e);
            else
                parent.right = createNewNode(e);
        }
        size++;
        return true; // Element inserted
    }
    
    /*
    private BinaryNode<E> insert(E e)
    {
        BinaryNode<E> parent = null;
        BinaryNode<E> current = root;
        
        if(root == null){
            root = createNewNode(e);
            return new BinaryNode<>(e, null, null);
        }
        else
        { 
            while (current != null)
            {
                if (e.compareTo(current.element) < 0) 
                {
                    parent = current;
                    current = current.left;
                }
                else if (e.compareTo(current.element) > 0)
                {
                    parent = current;
                    current = current.right;
                }
                else
                    ; // Duplicate node not inserted
            }
            // Create the new node and attach it to the parent node
            if (e.compareTo(parent.element) < 0)
                parent.left = createNewNode(e);
            else
                parent.right = createNewNode(e);
            
        }
        size++;
        return parent;
    }
    */
    // ************************** 9) Remove node in tree ***********************
    
    // Removes e from the tree
    public boolean remove(E e)
    {
        // Locate the node to be deleted and also locate its parent node
        BinaryNode<E> parent = null;
        BinaryNode<E> current = root;
        
        while (current != null) 
        {   
            if (e.compareTo(current.element) < 0)
            {
                parent = current;
                current = current.left;
            }
            else if (e.compareTo(current.element) > 0) 
            {
                parent = current;
                current = current.right;
            }
            else
                break; // Element is in the tree pointed by current
        }

        if (current == null)
        {
            //System.out.println("in remove: key " + e + " is not found." );
            return false; // Element is not in the tree
        }
            

        // If current has no left children
        if (current.left == null) 
        {
            // Connect the parent with the right child of the current node
            if (parent == null) 
            {
                root = current.right;
            }
            else
            {
                if (e.compareTo(parent.element) < 0)
                    parent.left = current.right;
                else
                    parent.right = current.right;
            }
        }
        else
        {
            
            
            // Else, the current node has a left child
            // Locate the rightmost node in the left subtree of
            // the current node and also its parent
            BinaryNode<E> parentOfRightMost = current;
            BinaryNode<E> rightMost = current.left;

            while (rightMost.right != null) 
            {
                parentOfRightMost = rightMost;
                rightMost = rightMost.right; // Keep going to the right
            }

            // Replace the element in current by the element in rightMost
            current.element = rightMost.element;

            // Eliminate rightmost node
            if (parentOfRightMost.right == rightMost)
            {
                //System.out.println("Key " + e + " is not in the tree");
                parentOfRightMost.right = rightMost.left;
            }
            else
            {
                // Special case: parentOfRightMost == current
                parentOfRightMost.left = rightMost.left;  
            }
                   
        }

        size--;
        return true; // Element inserted
    }
    
    
    /*
    public void remove(E e)
    {
        
        System.out.println("Testing: " + e);
        // Locate the node to be deleted and also locate its parent node
        BinaryNode<E> parent = null;
        
        BinaryNode<E> current = root;
        while (current != null) 
        {   
            if (e.compareTo(current.element) < 0)
            {
                parent = current;
                current = current.left;
            }
            else if (e.compareTo(current.element) > 0) 
            {
                parent = current;
                current = current.right;
            }
            else
                break; // Element is in the tree pointed by current
        }
        //BinaryNode <E> current = (BinaryNode <E>) search(e);
        System.out.println("Debug 2");
        if (search(e) == null)
        {
            System.out.println("in remove: key " + e + " is found." );
            
        }
            

        // Case 1: current has no left children
        if (search(e).left == null) 
        {
            // Connect the parent with the right child of the current node
            if (parent == null) 
            {
                root = current.right;
            }
            else
            {
                if (e.compareTo(parent.element) < 0)
                    parent.left = current.right;
                else
                    parent.right = current.right;
            }
        }
        else
        {
            
            
            // Case 2: The current node has a left child
            // Locate the rightmost node in the left subtree of
            // the current node and also its parent
            BinaryNode<E> parentOfRightMost = current;
            BinaryNode<E> rightMost = current.left;

            while (rightMost.right != null) 
            {
                parentOfRightMost = rightMost;
                rightMost = rightMost.right; // Keep going to the right
            }

            // Replace the element in current by the element in rightMost
            current.element = rightMost.element;

            // Eliminate rightmost node
            if (parentOfRightMost.right == rightMost)
            {
                System.out.println("Key " + e + " is not in the tree");
                parentOfRightMost.right = rightMost.left;
            }
            else
            {
                // Special case: parentOfRightMost == current
                parentOfRightMost.left = rightMost.left;  
            }
                   
        }

        size--;
        
    }
    */
    
    // ************************** 10) Print tree inorder ***********************
    
    // Prints the tree in inorder
    public void printTree()
    {
        BinaryNode<E> x = root;
        
        if(x == null)
        {
            System.out.println("Tree is empty.");
            return;
        }
            
        
        Stack <BinaryNode> stack = new Stack<BinaryNode>();
        
        while(!stack.isEmpty() || x != null)
        {
            if(x != null)
            {
                stack.push(x);
                x = x.left;
            }
            else
            {
                x = stack.pop();
                System.out.print(x.element + " ");
                x = x.right;
            }
        }
    }
    
    

    
}
