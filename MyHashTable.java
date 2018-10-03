import java.util.*;
public class MyHashTable
{
	public HashMap<Integer,MyLinkedList<WordEntry>> hm = new HashMap<Integer,MyLinkedList<WordEntry>>();
	public int getHashIndex(String str)
	{
		int counter = 0;
		for(int i=0; i<str.length(); i++)
		{
			counter = counter + (int) str.charAt(i);
		}
		return counter%100;
	}

	public void addPositionsForWord(WordEntry w)
	{
		int hashid = getHashIndex(w.word);
		MyLinkedList<WordEntry> newentry = new MyLinkedList();
		if(!hm.containsKey(hashid))
		{
			newentry.addLast(w);
			hm.put(hashid,newentry);
		}	
		else 
		{
			newentry = hm.get(hashid);
			newentry.addLast(w);
		}
	}
}