public class SearchResult implements Comparable<SearchResult>
{
	public PageEntry p;
	public float relevance;

	public SearchResult(PageEntry page, float r)
	{
		p=page;
		relevance=r;
	}

	public PageEntry getPageEntry()
	{
		return p;
	}

	public float getRelevance()
	{
		return relevance;
	}

	public int compareTo(SearchResult otherObject)
	{
		if(relevance>otherObject.relevance)
		{
			return -1;
		}
		else if(relevance==otherObject.relevance)
		{
			return 0;
		}
		else
		{
			return 1;
		}
	}	
}