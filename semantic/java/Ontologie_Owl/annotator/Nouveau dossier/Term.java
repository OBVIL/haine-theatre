package ir;

import java.util.ArrayList;
import java.util.HashMap;


public class Term {
	
	//annotation attributes
	private String id;
	private String text;
	private String language;
	private HashMap<String, ArrayList<Annotation>> annotations=new HashMap<String, ArrayList<Annotation>>();
	
		
	public Term(String id, String language)
	{
		this.id=id;
		this.language=language;
	}
	public Term(String id, String text, String language)
	{
		this.id=id;
		this.text=text;
		this.language=language;
		
	}
	
	public void addAllAnnotation(String idDoc , ArrayList<Annotation> annotation)
	{
		annotations.put(idDoc, annotation);
	}
	
	public HashMap<String, ArrayList<Annotation>> getAnnotations()
	{
		return annotations;
	}
	
	public void addAnnotation(String idDoc , Annotation annotation)
	{
		if(annotations.containsKey(idDoc))
		{
		  annotations.get(idDoc).add(annotation);
		}
		else
		{
			ArrayList<Annotation> a = new ArrayList<Annotation>();
			a.add(annotation);
			annotations.put(idDoc, a);
		}
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
		
	}
	
		
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	
	
	
	@Override
	public String toString() {
		return "Term [id=" + id + ", text=" + text + ", language=" + language
				+ ", annotations=" + annotations + "]";
	}
	
	public void printTermAnnotation()
	{
		System.out.println("TERM : "+text);
		for(String key : this.annotations.keySet())
		{
			ArrayList<Annotation> a = this.annotations.get(key);
			System.out.println("Annotations in the document "+key);
			for(Annotation as : a)
			{
				for(Position pos : as.getPositions())
				{
					System.out.println(pos.getRetrievedText()+" ["+pos.getStartPosition()+ ", "+pos.getEndPosition()+"]");
				}
				System.out.println();
			}
			
		}
		System.out.println("----------TERM END DETAILS------------------");
		System.out.println();
	}
		
	public static void  main (String [] args )
	{
		Term t = new Term ("1","new york's of cities with londan",Properties.ENGLISH_LANGUAGE);
		System.out.println(t.toString());
		
	}
	
	

}
