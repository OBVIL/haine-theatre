package ir;

public class Position {
	
	private int start;
	private int end;
	private String text;
	
	public Position() {
		
	}
	public Position(int start, int end, String text) {
		
		this.start = start;
		this.end = end;
		this.text = text;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	@Override
	public String toString() {
		return "Position [start=" + start + ", end=" + end + ", text=" + text
				+ "]";
	}
	
	
	

}