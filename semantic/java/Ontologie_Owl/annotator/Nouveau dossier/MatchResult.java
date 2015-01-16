package ir;

public class MatchResult implements Comparable{

	private String value;
	private int startPosition;
	private int endPosition;
	private float score;
	
	public MatchResult(String value, int startPosition, float score) {
		super();
		this.value = value;
		this.startPosition = startPosition;
	}
	
	public MatchResult(String value, int startPosition, int endPosition, float score) {
		super();
		this.value = value;
		this.startPosition = startPosition;
		this.endPosition=endPosition;
	}
	
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param startPosition the startPosition to set
	 */
	public void setStartPosition(int startPosition) {
		this.startPosition = startPosition;
	}
	/**
	 * @return the startPosition
	 */
	public int getStartPosition() {
		return startPosition;
	}
	
	public int getEndPosition() {
		return endPosition;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(float score) {
		this.score = score;
	}
	
	/**
	 * @return the score
	 */
	public float getScore() {
		return score;
	}

	@Override
	public int compareTo(Object o) {
		MatchResult o1 =this;
		MatchResult o2 = (MatchResult) o;
		if (o1.getScore()>o2.getScore()){
			return 1;
		}else if (o1.getScore()<o2.getScore()){
			return -1;
		}else
		return 0;
	}
	
	@Override
	public String toString(){
		return "match "+value+" at "+startPosition+ " end at "+endPosition;
	}
	
	
}