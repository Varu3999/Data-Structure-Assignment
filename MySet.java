public class MySet<X>
{
	public MyLinkedList<X> linklist = new MyLinkedList();
	
	public Boolean IsEmpty()
	{
		return (linklist.size()==0);
	}

	public Boolean IsMember(X o) 
	{
		return linklist.contains(o);
	}	

	public X remove() 
	{
		return linklist.removeFirst();
	}

	public void addElement(X o) 
	{
		if (!linklist.contains(o))
		{
			linklist.addLast(o);
		}
	}

	public void Delete(X o)	
	{
		if (linklist.contains(o))
		{linklist.remove(o);}
	}

	public MySet<X> MySetclone() 	// Similar to linked list clone, makes a clone of the MySet
	{				
		MySet<X> a = new MySet();
		a.linklist = this.linklist.clone();
		return a;
	}

	public MySet<X> union(MySet<X> a) 
	{
		MyLinkedList<X> templl = new MyLinkedList();
		MySet<X> b = new MySet();
		b = a.MySetclone();
		templl = linklist.clone();
		while(templl.size!=0)
		{
			X temp = templl.removeFirst();
			if (!(b.IsMember(temp)))
			{
				b.addElement(temp);
				(b.linklist.size)++;
			}

		}
		return b;
	}

	public MySet<X> intersection(MySet<X> a) 
	{
		MyLinkedList<X> templl = new MyLinkedList();
		MySet<X> b = new MySet();
		templl = linklist.clone();
		for (int i = 0; i<linklist.size(); i++)
		{
			X temp = templl.removeFirst();
			if (a.IsMember(temp))
			{
				b.addElement(temp);
				b.linklist.size++;

			}
		}
		return b;
	}
}