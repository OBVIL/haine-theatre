package ir;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.Field.TermVector;

public class Doc {
	
	private String id ="";
	private String url="";
	private String content="";
	
	public Doc(String id, String url, String content)
	{
		this.id=id;
		this.url=url;
		this.content=content;
	}
	
	public String getTextAtPosition (int startPosition, int endPosition)
	{
		return content.substring(startPosition, endPosition);
	}
	public Document getLuceneDocument()
	{
		Document doc = new Document();
		
		Field fid = new Field (FieldName.ID, id, Store.YES, Index.NO,TermVector.NO);
		
		Field furi = new Field (FieldName.URL, url, Store.YES, Index.NO,TermVector.NO);
		
		Field fcontent = new Field (FieldName.CONTENTS, content, Store.YES, Index.ANALYZED,TermVector.WITH_POSITIONS_OFFSETS);
					
		doc.add(fid);
		
		doc.add(furi);
		
		doc.add(fcontent);
		
		return doc;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Doc [id=" + id + ", url=" + url + ", content=" + content + "]";
	}
	
	public static void main (String [] args)
	{
		Doc d= new Doc("2","eeee","new york's of cities is a powerful contry. New york also need information.");
		System.out.println(d.getTextAtPosition(10+1, 14-1));
		d.getLuceneDocument();
	}
	

}
