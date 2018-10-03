public class Position {

	public int wordIndex;
	public int phraseIndex;
	public PageEntry page;

	public Position(PageEntry p, int wordindex) 
	{
		wordIndex = wordindex;
		page = p;
	}
	
	public PageEntry getPageEntry()
	{
		return page;
	}

	public int getWordIndex()
	{
		return wordIndex;
	}
}