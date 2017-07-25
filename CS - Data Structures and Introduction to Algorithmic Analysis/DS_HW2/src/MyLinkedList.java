
import java.util.Iterator;

public class MyLinkedList<E> extends MyAbstractList<E> {
    /*
    private static class Node<E>
    {
        E element;
        Node<E> next;
        
        public Node(E e)
        {
            element = e;
        }
    }*/
    
    private Node<E> head, tail;
    
    public MyLinkedList()
    {
    }
    
    public MyLinkedList(E[] objects)
    {
        super(objects);
    }
    
    public E getFirst()
    {
        if(size == 0)
            return null;
        else
            return head.element;
    }
    
    public E getLast()
    {
        if(size == 0)
            return null;
        else 
            return tail.element;
    }
    
    public void addFirst(E e)
    {
        Node <E> newNode = new Node <>(e);
        newNode.next = head;
        head = newNode;
        size++;
        
        if(tail == null)
            tail = head;
    }
    
    public void addLast(E e)
    {
        Node <E> newNode = new Node<>(e);
        
        if(tail == null)
        {
            head = tail = newNode;
        }
        else
        {
            tail.next = newNode;
            tail=tail.next;
        }
        
        size++;
    }
    
    @Override
    public void add(int idx, E x)
    {
        if(idx == 0)
            addFirst(x);
        else if (idx >= size)
            addLast(x);
        else
        {
            Node <E> current = head;
            for(int i = 1; i < idx; i++)
            {
                current = current.next;
            }
            
            Node<E> temp = current.next;
            current.next = new Node<E>(x);
            (current.next).next = temp;
            size++;
        }
    }
   
    public E removeFirst()
    {
        if(size == 0)
            return null;
        else
        {
            Node<E> temp = head;
            head = head.next;
            size--;
            if(head == null)
                tail = null;
            return temp.element;
        }
    }
    
    public E removeLast()
    {
        if(size == 0)
            return null;
        else if (size == 1)
        {
            Node<E> temp = head;
             head = tail = null;
             size = 0;
             return temp.element;
        }
        else
        {
            Node<E> current = head;
            
            for(int i = 0; i < size - 2; i++)
                current = current.next;
            Node<E> temp = tail;
            tail = current;
            tail.next = null;
            size--;
            return temp.element;
        }
    }

    @Override
    public void clear() {
        size = 0;
        head = tail = null;
    }

    @Override
    public boolean contains(E e) {
        // Implement it in this exercise
      Node<E> current = head;
      for (int i = 0; i < size; i++) {
        if (current.element.equals(e))
          return true;
        current = current.next;
      }

      return false;
    }

    @Override
    public E get(int index) {
        // Implement it in this exercise
      if (index < 0 || index > size - 1)
        return null;

      Node<E> current = head;
      for (int i = 0; i < index; i++)
        current = current.next;

      return current.element;
    }

    @Override
    public int indexOf(E e) {
        // Implement it in this exercise
      Node<E> current = head;
      for (int i = 0; i < size; i++) {
        if (current.element.equals(e))
          return i;
        current = current.next;
      }

      return -1;
    }

    @Override
    public int lastIndexOf(E e) {
 // Implement it in this exercise
      int lastIndex = -1;
      Node<E> current = head;
      for (int i = 0; i < size; i++) {
        if (current.element.equals(e))
          lastIndex = i;
        current = current.next;
      }

      return lastIndex;    }

    /**
     *
     * @param index
     * @return
     */
    @Override
    
    public E remove(int index) {
        
        if(index < 0 || index >= size)
            return null;
        else if(index == 0)
            return removeFirst();
        else if (index == size - 1)
            return removeLast();
        else
        {
            Node<E> previous = head;
            
            for(int i = 1; i < index; i++)
                previous = previous.next;
            
            Node<E> current = previous.next;
            previous.next = current.next;
            size--;
            return current.element;
        }
        
        
    }

    @Override
    public Object set(int index, E e) {
        if (index < 0 || index > size - 1)
            return null;

        Node<E> current = head;
        for (int i = 0; i < index; i++)
            current = current.next;

        E temp =  current.element;
        current.element = e;

        return temp;    
    }

    @Override
    public Iterator<E> iterator() 
    {
        return new LinkedListIterator();
    }
    
    private class LinkedListIterator implements java.util.Iterator<E> {
      private Node<E> current = head; // Current index 
      private boolean okToRemove = false;
      
      @Override
      public boolean hasNext() {
        return (current != null);
      }

      @Override
      public E next() {
        E e = current.element;
        current = current.next;
        return e;
      }

      @Override
      public void remove() {
          if(!okToRemove)
              throw new IllegalStateException();
          
          MyLinkedList.this.remove(current.element); // might need to change element to something else
          okToRemove = false;
        
      }
    }
    
    private class Node<E> {
      E element;
      Node<E> next;

      public Node(E element) {
        this.element = element;
      }
    }
    
    
    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder("[");
        
        Node<E> current = head;
        for(int i = 0; i < size; i++)
        {
            result.append(current.element);
            current = current.next;
            if(current != null)
            {
                result.append(", ");
            }
            else
            {
                result.append("]");
            }
        }
        return result.toString();
    }
    
}
