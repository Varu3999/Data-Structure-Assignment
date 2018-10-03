import java.lang.*;
import java.util.*;
public class InvertedPageIndex	
{
	public MySet<PageEntry> setOfPages = new MySet();
	public MyHashTable hashtable = new MyHashTable();

	public void addPage(PageEntry p)
	{
		setOfPages.addElement(p);
		Node<WordEntry> temp = new Node();
		temp = p.pageindex.wordEntryList.head;
		while(temp!=null)
		{
			hashtable.addPositionsForWord(temp.getElement());
			temp = temp.getNext();
		}
	}

	public MySet<PageEntry> getPagesWhichContainWord(String str)
	{
		MySet<PageEntry> pagesContainingWord = new MySet();
		int hashid = hashtable.getHashIndex(str);
		MyLinkedList<WordEntry> wordlist = hashtable.hm.get(hashid);
		if (wordlist==null) return null;
		Node<WordEntry> temp = wordlist.head;
		while(temp!=null)
		{
			if(temp.getElement().word.equals(str))
			{
				pagesContainingWord.addElement(temp.getElement().indexList.head.getElement().page);
			}	
			temp = temp.getNext();
		}

		return pagesContainingWord;
	}	

	public float getidf(String word)
	{
		int a = getPagesWhichContainWord(word).linklist.size();
		int b = setOfPages.linklist.size();
		float ans = (float) b/a;
		return (float) Math.log(ans);
	}

	public float getRelevance(String word, PageEntry p)
	{
		if (p.getTermFrequency(word)==0) return (float) 0;
		else return getidf(word)*(p.getTermFrequency(word));
	}

	public MySet<PageEntry> getPagesWhichContainPhrase(String[] str)
	{
		MySet<PageEntry> answer = new MySet();
		Node<PageEntry> temppeNode = setOfPages.linklist.head;
		while(temppeNode!=null)
		{
			int var = temppeNode.getElement().pageContainsPhrase(str);
			if(var>0)
			{
				answer.addElement(temppeNode.getElement());
			}
			temppeNode = temppeNode.getNext();
		}
		return answer;
	}

	public float relevanceForAndOrPhrase(String[] arr, PageEntry p, Boolean doTheseWordsRepresentAPhrase)
	{
		if(doTheseWordsRepresentAPhrase==false)
		{
			float rel = (float) 0;
			for(int i=0; i<arr.length; i++)
			{
				rel = rel + getRelevance(arr[i],p);
			}
			return rel;
		}
		else 
		{
			int m = p.pageContainsPhrase(arr);
			//System.out.println(p.name+" "+p.pageContainsPhrase(arr)+" "+p.pageContainsPhrase(arr));
			int wp = p.wordCount;
			int k = arr.length;
			int totalPagesN = setOfPages.linklist.size();
			int pageContainingPhrase = getPagesWhichContainPhrase(arr).linklist.size();
			float a = ((float) m)/((float) (wp - (k-1)*m));
			float b = ((float) totalPagesN)/((float) pageContainingPhrase);
			return ((float) a)*((float) (Math.log(b)));
		}
	}

	public ArrayList<SearchResult> mysetToArrayList(MySet<PageEntry> set, String[] arr, Boolean doTheseWordsRepresentAPhrase)
	{
		ArrayList<SearchResult> answerList = new ArrayList();
		Node<PageEntry> penode = set.linklist.head;
		while(penode!=null)
		{
			SearchResult temp = new SearchResult(penode.getElement(), relevanceForAndOrPhrase(arr,penode.getElement(),doTheseWordsRepresentAPhrase));
			answerList.add(temp);
			penode=penode.getNext();
		}
		return answerList;
	}
}