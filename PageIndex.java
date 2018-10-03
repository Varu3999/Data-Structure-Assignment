public class PageIndex 
{
	public MyLinkedList<WordEntry> wordEntryList = new MyLinkedList();

	public void addPositionForWord(String str, Position p)
	{
		MyLinkedList<WordEntry> newll = wordEntryList.clone();
		Node<WordEntry> we = newll.head;
		int flag = 0;
		while(we!=null)
		{
			if(we.nodeval.word.equals(str))
			{
				we.nodeval.indexList.addLast(p);
				we.nodeval.indexTree.insert(p);
				flag=1;
			}
			we = we.next;
		}
		if(flag==0)
		{
			WordEntry newwe	= new WordEntry(str);
			newwe.indexList.addLast(p);
			newwe.indexTree.insert(p);
			wordEntryList.addLast(newwe);
		}
	}

	public MyLinkedList<WordEntry> getWordEntries()
	{
		return wordEntryList;
	}
}