package tei;

public class Article {
	
	private String fileName = "";
	private String head = "";
	private String text = "";
	
	public Article(){
		
	}
	
	public Article(String fileName, String head, String text) {
		super();
		this.fileName = fileName;
		this.head = head;
		this.text = text;
	}

	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "Article [fileName=" + fileName + ", head=" + head + ", text="
				+ text + "]";
	}
	
	
	
	

}
