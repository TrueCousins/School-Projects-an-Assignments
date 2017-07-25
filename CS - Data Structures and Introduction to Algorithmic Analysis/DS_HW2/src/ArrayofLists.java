/*
Colleen Cousins
CS 3345.002
Due March 13, 2016
This program is a new data structure. The class ArrayofLists is an array of 
linked lists that stores AnyType into it.The list is inialized at 20 for testing
in main. There are 11 methods the data structure is able to use. 
*/
import java.util.LinkedList;
import java.util.Iterator;

public class ArrayofLists<E>  {
    public static final int INITIAL_CAPACITY = 20;
    LinkedList [] sublist = new LinkedList[INITIAL_CAPACITY];
    int lastIndex = 0;
    
    // Create a default list of index 20
    protected ArrayofLists()
    {
        for(int i = 0; i < INITIAL_CAPACITY; i++)
        {
            sublist[i] = new LinkedList();
        }
    }
    
    // Create a list from an array of objecs 
    protected ArrayofLists(E[] objects)
    {
        for(int i = 0; i < objects.length; i++)
        {
            add(objects[i]);
        }
            
    }
    
    // Clear entire list
    public void clear()
    {
        for(int i = 0; i < INITIAL_CAPACITY; i++)
        {
            sublist[i].clear();
        }
        
    }
    
    // Return size of the list
    public int size()
    {
        return lastIndex;
    }
    
    // Returns true/false if list is empty
    public boolean isEmpty()
    {
        for(int i = 0; i < INITIAL_CAPACITY;i++)
        {
            if(sublist[i].isEmpty()==false)
                return false;
                        
        }
        return true;
        
    }
    
    // Returns the element at the specified index
    public void get(int idx)    
    {
        System.out.println(sublist[idx]);
    }
    
    // Replace the element at the specified postion in this ist with the
    // specified element
    public void set(int index, E newVal) // THIS WORKS
    {
        
        //System.out.println("in set function");
        if(sublist[index].isEmpty())
        {
            sublist[index].add(newVal);
        }
        else
        {
            sublist[index].remove();
            sublist[index].add(newVal);
        }
        
        
      
        //System.out.println(myList[index]);
        lastIndex++;
    }

    /**
     *
     * @param e
     */
    // Add a new element at the end of the list
    public void add(E e) 
    {
        /*
        myList[size] = new LinkedList();
        myList[size].add(new Integer((String) e));
        //myList[size]= (LinkedList) e;
        size++;*/
        add(lastIndex, e);
        
    }
     
    // Add a new element at the specified index
    public void add(int index, E e) // THIS WORKS
    {
        //System.out.println("in add function");
        sublist[index].add(e);
      
        //System.out.println(myList[index]);
        lastIndex++;
    }
    
    /**
     *
     * @param idx
     * @return
     */
    //Remove the element at the specified position in this list
    public void remove (int idx)
    {
        //E e = (E) myList[idx];
        if(sublist[idx].isEmpty())
        {
            System.out.println("Nothing in this index.");
        }
        else
            sublist[idx].remove();
        //return e;
    }
    
    public Iterator<E> iterator() 
    {
        return new ArrayofListIterator();
    }

    private class ArrayofListIterator implements Iterator<E>
    {
        private int current = 0;
        
        @Override
        public boolean hasNext()
        {
            return(current < lastIndex);
        }
        
        @Override
        public E next()
        {
            return (E) sublist[current++];
        }
    }
    
    // Print entire list
    public void printList(int x) 
    {
        for(int i = 0; i < x; i++)
            System.out.println(sublist[i]);
        
    }
    

    
}
