package sqlite;

public class SemanticTag {
	
	String id = "";
	String term_id = "";
	String parent_id = "";
	String chapter_id = "";
	
	
	public SemanticTag(String id, String term_id, String parent_id,
			String chapter_id) {
		
		this.id = id;
		this.term_id = term_id;
		this.parent_id = parent_id;
		this.chapter_id = chapter_id;
	}
	
	public SemanticTag() {
		
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTerm_id() {
		return term_id;
	}
	public void setTerm_id(String term_id) {
		this.term_id = term_id;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public String getChapter_id() {
		return chapter_id;
	}
	public void setChapter_id(String chapter_id) {
		this.chapter_id = chapter_id;
	}

	@Override
	public String toString() {
		return "SemanticTag [id=" + id + ", term_id=" + term_id
				+ ", parent_id=" + parent_id + ", chapter_id=" + chapter_id
				+ "]";
	}
	
	public String getSqliteQuery(){
		String query = "INSERT INTO SemanticTag (id,term_id,parent_id,chapter_id)\n";
		query = query + "VALUES ('"+id+"','"+term_id+"','"+parent_id+"','"+chapter_id+"');\n";
		
		return query;
	}

}
