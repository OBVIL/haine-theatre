package ir;

public class Position {
	
	private int startPosition;
	private int endPosition;
	private String retrievedText;
	
	public Position(int startPosition, int endPosition, String retrievedText)
	{
		this.startPosition=startPosition;
		this.endPosition=endPosition;
		this.retrievedText=retrievedText;
	}
	
	
	public int getStartPosition() {
		return startPosition;
	}
	public void setStartPosition(int startPosition) {
		this.startPosition = startPosition;
	}
	public int getEndPosition() {
		return endPosition;
	}
	public void setEndPosition(int endPosition) {
		this.endPosition = endPosition;
	}
	public String getRetrievedText() {
		return retrievedText;
	}
	public void setRetrievedText(String retrievedText) {
		this.retrievedText = retrievedText;
	}
	
	@Override
	public String toString() {
		return "Position [startPosition=" + startPosition + ", endPosition="
				+ endPosition + ", textRetrieved=" + retrievedText + "]";
	}
	
}
