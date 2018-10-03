public class WordEntry
{
	public String word;
	public MyLinkedList<Position> indexList;
	public AVLTree indexTree;

	public WordEntry(String w)
	{
		word = w;
		indexList = new MyLinkedList();
		indexTree = new AVLTree();
	}

	public void addPosition(Position position)
	{
		indexList.addLast(position);
		indexTree.insert(position);
	}

	public void addPositions(MyLinkedList<Position> positions)
	{
		indexList = indexList.unionlist(positions);
	}

	public MyLinkedList<Position> getAllPositionsForThisWord()
	{
		return indexList;
	}
}