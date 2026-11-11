public class GenLL <T> {
    private class ListNode
    {
        T data;
        ListNode next;

        public ListNode(T aData, ListNode aNext)
        {
            this.data = aData;
            this.next = aNext;
        }
    }

    private ListNode head;
    private int size;
    
    //Adds a new node to the end of the list 
    public void add(T data)
    {
        ListNode newNode = new ListNode(data, null);
        
        //Empty Linked List 
        if(head == null)
        {
            head = newNode;
            size++;
            return;
        }

        //Adding to End 
        ListNode current = head;
        while(current.next != null)
        {
            current = current.next;
        }

        current.next = newNode;
        size++;
    }

    //Removes the node whose data is equal 
    public boolean remove (T value)
    {
        //Empty Linked List 
        if(head == null){return false;}

        //Linked List w/Head only 
        if(head.data.equals(value))
        {
            head = head.next;
            size--;
            return true;
        }

        //Linked List w/Multiple Nodes
        ListNode previous = head;
        ListNode current = head.next;
        while(current != null)
        {
            if(current.data.equals(value))
            {
                previous.next = current.next;
                size--;
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }

    //Returns the data of the node at that position
    public T get (int index)
    {
        if(index < 0 || index >= size) {return null;}

        ListNode current = head;
        for(int i = 0; i< index; i++)
        {
            current = current.next;
        }
        return current.data;
    }

    public int getSize()
    {
        return size;
    }

    public boolean isEmpty()
    {
        return size == 0;
    }

    public void displayAll()
    {
        if(head == null)
        {
            System.out.println("The list is empty");
            return;
        }

        ListNode current = head;
        while(current != null)
        {
            System.out.println(current.data);
            current = current.next;
        }
    }
}
