package ir;

public class Entry {
	public String idDoc="";
	public int startPosition;
	
	public Entry(String idDoc, int startPosition) {
		
		this.idDoc = idDoc;
		this.startPosition = startPosition;
	}

	@Override
	public String toString() {
		return "Entry [idDoc=" + idDoc + ", startPosition=" + startPosition
				+ "]";
	}
	
	
	

}
