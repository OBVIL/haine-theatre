package sqlite;

public class SemanticChapterStat {
	
	String id = "";
	String chapter_id = "";
	int chapter_length = 0;
	int chapter_term_count = 0;
	int chapter_semantic_term_count = 0;
	String text = "";
	
	public SemanticChapterStat() {
	
	}
		
	public SemanticChapterStat(String id, String chapter_id,
			int chapter_length, int chapter_term_count,
			int chapter_semantic_term_count) {
		super();
		this.id = id;
		this.chapter_id = chapter_id;
		this.chapter_length = chapter_length;
		this.chapter_term_count = chapter_term_count;
		this.chapter_semantic_term_count = chapter_semantic_term_count;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getChapter_id() {
		return chapter_id;
	}
	public void setChapter_id(String chapter_id) {
		this.chapter_id = chapter_id;
	}
	public int getChapter_length() {
		return chapter_length;
	}
	public void setChapter_length(int chapter_length) {
		this.chapter_length = chapter_length;
	}
	public int getChapter_term_count() {
		return chapter_term_count;
	}
	public void setChapter_term_count(int chapter_term_count) {
		this.chapter_term_count = chapter_term_count;
	}


	public int getChapter_semantic_term_count() {
		return chapter_semantic_term_count;
	}


	public void setChapter_semantic_term_count(int chapter_semantic_term_count) {
		this.chapter_semantic_term_count = chapter_semantic_term_count;
	}
	
	public void setText(String text){
		this.text = text;
	}

	@Override
	public String toString() {
		return "SemanticChapterStat [id=" + id + ", chapter_id=" + chapter_id
				+ ", chapter_length=" + chapter_length
				+ ", chapter_term_count=" + chapter_term_count
				+ ", chapter_semantic_term_count="
				+ chapter_semantic_term_count + ", text=" + text + "]";
	}

	public String getSqliteQuery(){
		String query = "INSERT INTO SemanticChapterStat (id,chapter_id,chapter_length,chapter_term_count,chapter_semantic_term_count)\n";
		query = query + "VALUES ('"+id+"','"+chapter_id+"',"+chapter_length+","+chapter_term_count+","+chapter_semantic_term_count+");\n";
		
		return query;
	}
	
	
	

}