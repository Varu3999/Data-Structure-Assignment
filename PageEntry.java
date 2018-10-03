import java.util.*;
import java.io.*;
public class PageEntry
{
	public PageIndex pageindex;
	public String name;
	public int wordCount = 0;

	public PageEntry()
	{
		name = "";
		pageindex = new PageIndex();
	}

	public String punctElim(String a)
	{
		for(int i=0; i<a.length(); i++)
		{
			if(a.charAt(i)=='{'||a.charAt(i)=='}'||a.charAt(i)=='['||a.charAt(i)==']'||a.charAt(i)=='<'||a.charAt(i)=='>'||a.charAt(i)=='='||a.charAt(i)=='('||a.charAt(i)==')'||a.charAt(i)=='.'||a.charAt(i)==','||a.charAt(i)==';'||a.charAt(i)=='"'||a.charAt(i)=='?'||a.charAt(i)=='#'||a.charAt(i)=='!'||a.charAt(i)=='-'||a.charAt(i)==':'||a.charAt(i)=='\'')
			{a=a.substring(0,i)+" "+a.substring(i+1);}
		}
		return a.toLowerCase();
	}

	public PageEntry(String p)
	{
		pageindex = new PageIndex();
		name = p;
		try
		{
			String newWord;
			int counter = 1;
			int phraseCounter = 1;
			FileInputStream fstream = new FileInputStream("webpages\\"+p);
			Scanner s = new Scanner(fstream);
			while(s.hasNextLine())	
			{
				String a = s.nextLine();
				String[] arr = punctElim(a).split(" ");
				for(int i=0; i<arr.length; i++) 
				{
					newWord = arr[i];
					if (newWord.equals("")||newWord.equals(" ")){}
					else if (newWord.equals("a")||newWord.equals("an")||newWord.equals("the")||newWord.equals("they")||newWord.equals("these")||newWord.equals("this")||newWord.equals("for")||newWord.equals("is")||newWord.equals("are")||newWord.equals("was")||newWord.equals("of")||newWord.equals("or")||newWord.equals("and")||newWord.equals("does")||newWord.equals("will")||newWord.equals("whose"))
					{
						counter++;
					}
					else if (newWord.equals("stacks")||newWord.equals("structures")||newWord.equals("applications"))
					{		
						newWord = newWord.substring(0,newWord.length()-1);
						Position pos = new Position(this,counter);
						pos.phraseIndex = phraseCounter;
						pageindex.addPositionForWord(newWord,pos);
						counter++;
						phraseCounter++;
					}
					else
					{
						Position pos = new Position(this,counter);
						pos.phraseIndex = phraseCounter;
						pageindex.addPositionForWord(newWord,pos);
						counter++;
						phraseCounter++;
					}
				}
			}
			wordCount = counter - 1;
		}
		catch(FileNotFoundException e){System.out.println("File "+p+" not found Exception");}
	}	

	public PageIndex getPageIndex()
	{
		return pageindex;
	}

	public float getTermFrequency(String word)
	{
		Node<WordEntry> tempwe = pageindex.wordEntryList.head;
		int wordCounter = 0;
		while(tempwe!=null)
		{
			if(tempwe.getElement().word.equals(word))
			{
				wordCounter = tempwe.getElement().indexList.size();;
			}
			tempwe = tempwe.getNext();
		}
		float ans = (float) wordCounter/wordCount;
		return ans;		
	}

	public WordEntry findWordEntryFromPage(String w)
	{
		Node<WordEntry> temp = this.pageindex.wordEntryList.head;
		while(temp!=null)
		{
			if(temp.getElement().word.equals(w))
			{
				return temp.getElement();
			}
			temp = temp.getNext();
		}
		return null;
	}

	public String modify(String s)
	{
		if(s.equals("structures"))	return("structure");
		else if(s.equals("stacks"))	return("stack");
		else if(s.equals("applications"))	return("application");
		else return(s);
	}

	public int pageContainsPhrase(String[] str)
	{
		Boolean flag;
		int count = 0;
		WordEntry firstwe = findWordEntryFromPage(modify(str[0]));
		WordEntry tempwe;
		int check;
		if(firstwe==null)
		{
			return 0;
		}
		else
		{
			Vector<Integer> vector = firstwe.indexTree.inorder();
			for(int i=0; i<vector.size(); i++)
			{
				check = vector.get(i);
				flag = true;
				for(int j=1; j<str.length; j++)
				{
					check++;
					tempwe = findWordEntryFromPage(modify(str[j]));
					if(tempwe==null)
					{
						flag=false;
					}
					else
					{
						flag = flag && tempwe.indexTree.search(check);
					}
				}
				if (flag==true)
				{
					count++;
				}
			}
			//System.out.println("Value of count-"+count);
			return count;
		}
	}

	// public float getRelevanceOfPage(String str[], boolean doTheseWordsRepresentAPhrase)
	// {

	// }
}