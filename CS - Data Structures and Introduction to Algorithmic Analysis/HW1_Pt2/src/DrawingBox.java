// Generic class Drawing box
public class DrawingBox<E> {
    
    // generic array list
    private java.util.ArrayList<E> list = new java.util.ArrayList<>();
    
    // Adds datat to list
    public void add(E n)
    {
        list.add(n);
    }
    
    // Returns true or false if list is empty
    public boolean isEmpty()
    {
        return list.isEmpty();
    }
    
    /**
     *
     * @param <E>
     * @param list
     */
    // Draws 3 items out of the drawing box
    public <E> void drawItem()
    {
        int x = 0;
        
        // Displays 3 random drawings or if the box is empty
        if(list.isEmpty() == true)
        {
            System.out.println("The drawing box is empty.");
        }
        else
        {
            // Randomly draws 3 items
            for(int i = 0; i < 3; i++)
            {
                x = (int) (Math.random()*list.size());
                System.out.print(list.get(x) + " ");
             
            }
            System.out.println();
        }    
    }   // END drawItem
    
}
