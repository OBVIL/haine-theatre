package ir;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;



public class Annotator {
	
	public static int SLOP_VALUE = 1;
	
	public static SearchEngine SE = new SearchEngine(Properties.FRENCH_LANGUAGE);
		
	public static ArrayList<Term> annotateDocuments(ArrayList<Doc> documents, ArrayList<Term> terms)
	{
		ArrayList<Document> docs = new ArrayList<Document>();
		
		for(Doc d : documents)
			docs.add(d.getLuceneDocument());
		
		SE.createIndex(docs);
		
		for(Term term : terms)
		{
			for(Doc d : documents)
			{
				computeAnnotation(d,term);
			}
		}
		
		return terms;
	}
	
	private static void computeAnnotation(Doc d, Term t)
	{
		List<MatchResult> results;
		ArrayList<Position> positions = new ArrayList<Position>();
		results = SE.search(FieldName.CONTENTS, d.getContent(), t.getText(), SLOP_VALUE);
		//int increment = 1;
		if(!results.isEmpty())
		{
			for(int i = 0 ; i<results.size();i++)
			{
				MatchResult r = results.get(i);
				Position pos = new Position (r.getStartPosition(),
						r.getEndPosition(),
						d.getTextAtPosition(r.getStartPosition(), r.getEndPosition())
						);
				positions.add(pos);
				
								
			}
			
			Annotation annotation = new Annotation(d.getId(),positions);
			t.addAnnotation(d.getId(), annotation);
		}
		
				
	}
	
	
	
	public static void main(String [] args)
	{
		ArrayList<Term> terms = new ArrayList<Term>();
		ArrayList<Doc> documents = new ArrayList<Doc>();
		
	/*	
		terms.add(new Term("t1","Nag of Head",Properties.ENGLISH_LANGUAGE));
		terms.add(new Term("t2","Macao Trading Co.",Properties.ENGLISH_LANGUAGE));
		terms.add(new Term("t3","MAS Muslim Youth Center",Properties.ENGLISH_LANGUAGE));
		terms.add(new Term("t4","New York",Properties.ENGLISH_LANGUAGE));
		terms.add(new Term("t5","Roy's Barber Shop",Properties.ENGLISH_LANGUAGE));
		//terms.add(new Term("t6","@foursquare!",Properties.ENGLISH_LANGUAGE));
		
		
		documents.add(new Doc("doc1","doc1","NEW york cities is a magic contries. Also in london you can see people in new york of Cities."));
		documents.add(new Doc("doc2","doc2","I'am going this night to the us marshall."));
		documents.add(new Doc("doc3","doc3","Many system in the us marshall ar quick. The chicago of many systems are used."));
		
		
		documents.add(new Doc("doc1","doc1","I just ousted Peter C. as the mayor of Nag's Head on @foursquare! http://t.co/343YrEHQ"));
		documents.add(new Doc("doc2","doc2","I'm at Macao Trading Co. (New York, NY) w/ 3 others http://t.co/0zuv4k2I"));
		documents.add(new Doc("doc3","doc3","I just ousted moonzareen as the mayor of MAS Muslim Youth Center on @foursquare! http://t.co/oNSctKcl"));
		documents.add(new Doc("doc4","doc4","I'm at (New York, NY) w/ 3 others http://t.co/0zuv4k2I Macao Trading Co."));
		documents.add(new Doc("doc5","doc5","I just ousted Jason S. as the mayor Roy's Barber Shop on @foursquare! http://t.co/bYPHrIql"));
		*/
		
		terms.add(new Term("t1","le",Properties.FRENCH_LANGUAGE));
		//terms.add(new Term("t1","ambiance amicale",Properties.FRENCH_LANGUAGE));
		String text = FileIO.readFile(new File("text.txt"), "UTF-8");
		
		documents.add(new Doc("doc1","doc1",text));
		//documents.add(new Doc("doc2","doc2","il s'agit d'une ambiance très amicale"));
		
		Annotator.annotateDocuments(documents, terms);
	//	String text ="I just ousted Jason S. in the mayor Roy's Barber Shop on @foursquare! http://t.co/bYPHrIql";
	//	System.out.println(text.substring(48, 52));
		for(Term t : terms)
			t.printTermAnnotation();
	}

}
