class Node<T>
{
	public T nodeval;
	public Node<T> next;

	public Node() 
	{
		next = null;
		nodeval = null;
	}
	
	public Node(T o, Node<T> n) 
	{
		nodeval = o;
		next = n;
	}

	public T getElement()
	{
		return nodeval;
	}

	public Node<T> getNext() 
	{
		return next;
	}

	public void setNext(Node<T> n) 
	{
		next = n;
	}

}


public class MyLinkedList<T>
{
	public Node<T> head = new Node();
	public Node<T> tail = new Node();
	public int size;

	public MyLinkedList() 
	{
		head = null;
		tail = null;
		size = 0;
	}

	public Boolean IsEmpty() 
	{
		return (size == 0);
	}

	public int size() 
	{
		return size;
	}

	public void addLast(T o) 
	{
		Node<T> lastnode = new Node(o,null);
		if (this.IsEmpty()){head=lastnode;}
		else tail.setNext(lastnode);
		tail = lastnode;
		size++;
	}

	public void addFirst(T o) 
	{
		head = new Node(o,head);
		if (size==0){tail=head;}
		size++;
	}

	public T removeFirst() 
	{ 
        if (this.IsEmpty()) return null; 
        T firstelement = head.getElement(); 
        head = head.getNext();  
        size--; 
        if (size == 0) 
        	tail = null;
        return firstelement;
    }

	public MyLinkedList<T> clone() 		// Makes a copy of the current linked list rather than copying its reference
	{	
		Node<T> pointer = head;
		MyLinkedList<T> newll = new MyLinkedList();
		while(pointer!=null)
		{
			newll.addLast(pointer.getElement());
			pointer = pointer.getNext();
		}
		return newll;
	}

    public Boolean contains(T o) 
    {
        Node<T> pointer = head;
        while(pointer!=null)
        {
            if (pointer.getElement().equals(o))
            	return true;
            pointer=pointer.getNext();
        }
        return false;
    }

    public T remove(T o) 
    {
    	if (this.IsEmpty() || !(contains(o))) return null; 
    	Node<T> pointer = head;
    	if (pointer.getElement().equals(o)) {this.removeFirst();}
    	else 
    	{
	    	while(pointer != null)
	    	{
	    		Node<T> k = pointer.next;
	    		if (k == null)
	    			break;
	    		if (k.getElement().equals(o))
	    		{
	    			pointer.setNext(k.next);
	    		}
	    		pointer = pointer.getNext();
	    	}
    	}
    	return o;
    }

    public MyLinkedList<T> unionlist(MyLinkedList<T> a)
    {
    	MyLinkedList<T> templl = a.clone();
    	MyLinkedList<T> newll = this.clone();
    	Node<T> pointer = templl.head;	
    	for(int i=0; i<templl.size(); i++)
    	{
    		newll.addLast(pointer.getElement());
    		pointer = pointer.next;
    	}
    	return newll;
    }

	public T get(int i) 	// Returns the ith element of the linked list
	{		
        Node<T> pointer = head;
        int x = 0;
        while (pointer != null)
        {
            if (x == i) 
                return(pointer.getElement());
            x++;
            pointer = pointer.getNext();
        }
        return null;
    }

    public void printList() 	// Prints all the elements of the current linked list
    {		
        Node<T> tmp = this.head;
        while(tmp!=null){System.out.println(tmp.getElement());tmp = tmp.getNext();}
    }    
}