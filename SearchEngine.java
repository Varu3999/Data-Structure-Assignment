import java.io.*;
import java.util.*;
public class SearchEngine
{
	InvertedPageIndex invPageIndex;

	public SearchEngine()
	{
		invPageIndex = new InvertedPageIndex();
	}

	public String printArrList(ArrayList<SearchResult> a)
	{
		if (a.size()==0) {return("No webpage contains the given phrase");}
		else
		{
			String out = a.get(0).p.name/*+" "+a.get(0).relevance*/;
			for(int i=1; i<a.size(); i++)
			{
				out = out+", "+a.get(i).p.name/*+" "+a.get(i).relevance*/;
			}
			return out;
		}
	}

	public String printSet(MySet<PageEntry> m)
	{
		if(m!=null)
		{
			Node<PageEntry> penode = m.linklist.head;
			if(penode==null) {return("No webpage contains the given phrase");}
			String s = penode.getElement().name;
			penode = penode.getNext();
			while(penode!=null)
			{
				s = s + ", " + penode.getElement().name;
				penode = penode.getNext();
			}
			return s;
		}
		else return("m is null!");
	}

	public String getx(String s)
	{
		String temps = s.substring(32);
		int spaceid = 0;
		while(temps.charAt(spaceid)!=' ')	{spaceid++;}
		return temps.substring(0,spaceid);
	}

	public String gety(String s)
	{
		String temps = s.substring(32);
		int spaceid = 0;
		while(temps.charAt(spaceid)!=' ')	{spaceid++;}
		return temps.substring(spaceid+1);
	}

	public String[] getInputArray(String s)
	{
		if(s.substring(0,34).equals("queryFindPagesWhichContainAllWords"))
		{
			return (s.substring(35)).toLowerCase().split(" ");
		}
		else if(s.substring(0,41).equals("queryFindPagesWhichContainAnyOfTheseWords"))
		{
			return (s.substring(42)).toLowerCase().split(" ");
		}
		else
		{
			return (s.substring(33)).toLowerCase().split(" ");
		}
	}

	public void performAction(String actionMessage)
	{
		if(actionMessage.length()>6)
		{
			if(actionMessage.substring(0,7).equals("addPage"))
			{
				String y = actionMessage.substring(8);
				String x = y.toLowerCase();
				if(x.equals("stacks")) x=x.substring(0,x.length()-1);
				if(x.equals("applications")) x=x.substring(0,x.length()-1);
				if(x.equals("structures")) x=x.substring(0,x.length()-1);
				PageEntry newpage = new PageEntry(x);
				invPageIndex.addPage(newpage);			
			}

			else if(actionMessage.substring(0,30).equals("queryFindPagesWhichContainWord"))
			{
				String y = actionMessage.substring(31);
				String x = y.toLowerCase();
				if(x.equals("stacks")) x=x.substring(0,x.length()-1);
				if(x.equals("applications")) x=x.substring(0,x.length()-1);
				if(x.equals("structures")) x=x.substring(0,x.length()-1);
				String answer = "";
				MySet<PageEntry> tempo = invPageIndex.getPagesWhichContainWord(x);
				if(tempo==null) {System.out.println("No webpage contains word "+y);}
				else if(tempo.IsEmpty())	{System.out.println("No webpage contains word "+y);} 
				// else
				// {	
				// 	PageEntry page1;
				// 	while(!tempo.IsEmpty()) 
				// 	{
				// 		page1 = tempo.remove();
				// 		if (page1!=null)
				// 		answer = answer + ", " + page1.name;
				// 	}	
				// }
				// if(answer.length()<=2) System.out.print("");
				// else System.out.println(answer.substring(2));
				Node<PageEntry> page1node = tempo.linklist.head;
				ArrayList<SearchResult> answerList = new ArrayList();
				while(page1node!=null)
				{
					SearchResult tempsr = new SearchResult(page1node.getElement(),page1node.getElement().getTermFrequency(x));
					answerList.add(tempsr);
					page1node = page1node.getNext();
				}
				MySort m = new MySort();
				ArrayList finall = (m.sortThisList(answerList));
				if(finall!=null && finall.size()>0)
				{
					System.out.println(printArrList(finall));
				}
			}

			else if(actionMessage.substring(0,31).equals("queryFindPositionsOfWordInAPage"))
			{
				String wordd = getx(actionMessage);
				String wordx = wordd.toLowerCase();
				String pagey = gety(actionMessage);
				if(wordx.equals("stacks")) wordx=wordx.substring(0,wordx.length()-1);
				if(wordx.equals("applications")) wordx=wordx.substring(0,wordx.length()-1);
				if(wordx.equals("structures")) wordx=wordx.substring(0,wordx.length()-1);
				String answer = "";
				MySet<PageEntry> temppagelist =  invPageIndex.setOfPages.MySetclone();
				PageEntry temppage;
				PageEntry actualpagey = null;
				while(!temppagelist.IsEmpty())
				{
					temppage = temppagelist.remove();
					if(temppage.name.equals(pagey))
						actualpagey = temppage;
				}
				if(actualpagey==null)	{System.out.println("No webpage "+pagey+" found");}
				int hashid = invPageIndex.hashtable.getHashIndex(wordx);
				if(!invPageIndex.hashtable.hm.containsKey(hashid)) {System.out.println("Webpage "+ pagey +" does not contain word "+wordd);}
				MyLinkedList<WordEntry> tempwelist = invPageIndex.hashtable.hm.get(hashid);
				if(tempwelist==null) {System.out.print("");}
				else	
				{	Node<WordEntry> tempnode = tempwelist.head;
					WordEntry tempwe = null;
					while(tempnode!=null)
					{
						if(tempnode.getElement().indexList.head.getElement().page.name.equals(pagey) && tempnode.getElement().word.equals(wordx))
							break;
						tempnode = tempnode.getNext();
					}
					if(tempnode==null)	{System.out.println("Webpage "+ pagey +" does not contain word "+wordd);}
					else{tempwe=tempnode.getElement();}
					if(tempwe==null){}
					else	
					{
						Node<Position> temppos = tempwe.indexList.head;
						while(temppos!=null)
						{
							if(temppos.getElement().page.name.equals(actualpagey.name))
							{
								answer = answer + ", " + temppos.getElement().wordIndex;
							}
							temppos = temppos.getNext();
						}
						if(answer.length()<=2)	{System.out.println("Webpage "+ pagey +" does not contain word "+wordd);}
						else System.out.println(answer.substring(2));
					}
				}
			}
			
			else if(actionMessage.substring(0,34).equals("queryFindPagesWhichContainAllWords"))
			{
				String[] inpArr = getInputArray(actionMessage);
				MySet<PageEntry> answer = invPageIndex.getPagesWhichContainWord(inpArr[0]);
				for(int i=1; i<inpArr.length; i++)
				{
					answer = answer.intersection(invPageIndex.getPagesWhichContainWord(inpArr[i]));
				}
				//System.out.println(printSet(answer));
				ArrayList<SearchResult> finalarr = invPageIndex.mysetToArrayList(answer,inpArr,false);
				MySort m = new MySort();
				System.out.println(printArrList(m.sortThisList(finalarr)));
				//return m.sortThisList(finalarr);
			}

			else if(actionMessage.substring(0,41).equals("queryFindPagesWhichContainAnyOfTheseWords"))
			{
				String[] inpArr = getInputArray(actionMessage);
				MySet<PageEntry> answer = invPageIndex.getPagesWhichContainWord(inpArr[0]);
				for(int i=1; i<inpArr.length; i++)
				{
					answer = answer.union(invPageIndex.getPagesWhichContainWord(inpArr[i]));
				}
				//System.out.println(printSet(answer));
				ArrayList<SearchResult> finalarr = invPageIndex.mysetToArrayList(answer,inpArr,false);
				MySort m = new MySort();
				System.out.println(printArrList(m.sortThisList(finalarr)));
			}

			else if(actionMessage.substring(0,32).equals("queryFindPagesWhichContainPhrase"))
			{
				String[] inpArr = getInputArray(actionMessage);
				MySet<PageEntry> answer = invPageIndex.getPagesWhichContainPhrase(inpArr);
				ArrayList<SearchResult> finalarr = invPageIndex.mysetToArrayList(answer,inpArr,true);
				MySort m = new MySort();
				System.out.println(printArrList(m.sortThisList(finalarr)));
				//System.out.println(printSet(answer));
				//System.out.println(printSet(invPageIndex.setOfPages));
			}
		}

		else System.out.println("No message matched with the " + actionMessage);
	}
}