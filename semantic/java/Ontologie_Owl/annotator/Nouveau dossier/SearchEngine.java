package ir;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.Field.TermVector;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;



public class SearchEngine {

private RAMDirectory directory;
private StopAnalyzer analyzer;
private IndexWriterConfig config;
private IndexWriter indexWriter;


//public SearchEngine() 
//{
//	
//	try {
//	directory = new RAMDirectory();
//		
//	analyzer = new SnowballAnalyzer(Version.LUCENE_34,Properties.ENGLISH_LANGUAGE,StopWords.ENGLISH_STOP_LIST);
//	
//	config = new IndexWriterConfig (Version.LUCENE_34, analyzer);
//	
//	indexWriter = new IndexWriter(directory, config);
//	}
//	catch (Exception e)
//	{
//		System.out.println("Error during creating search engine on SearchEngine class");
//	}
//}

public SearchEngine(String language) 
{
	
	try {
	directory = new RAMDirectory();
		
	analyzer = new StopAnalyzer(Version.LUCENE_34);
	
	config = new IndexWriterConfig (Version.LUCENE_34, analyzer);
	
	indexWriter = new IndexWriter(directory, config);
	}
	catch (Exception e)
	{
		System.out.println("Error during creating search engine on SearchEngine class");
	}
}

public void createIndex (ArrayList<Document> documents)
{
	try {
		indexWriter.deleteAll();
		for(Document d : documents)
			indexWriter.addDocument(d);
		
		indexWriter.commit();
		
	} catch(Exception e){
	System.out.println("Creating index error on SearchEngine class");
  }
}

public void createIndex(String fieldName,String text)
{
		
	    try {
	    indexWriter.deleteAll();
		
	    Document doc = new Document();
		
		Field body =new Field (fieldName, text, Store.YES, Index.ANALYZED,TermVector.WITH_POSITIONS);
		
		doc.add(body);
		
		indexWriter.addDocument(doc);
		
        doc = new Document();
		
	  //  body =new Field (fieldName, "with new york cities we are the best.", Store.YES, Index.ANALYZED,TermVector.WITH_POSITIONS);
		
		doc.add(body);
		
		indexWriter.addDocument(doc);
		
		indexWriter.commit();
	    }
	    catch(Exception e){
	    	System.out.println("Creating index error on SearchEngine class");
	    }
			
	}
	

public  List<MatchResult> search (String field , String text, String term , int slop) 
	{
		term = "\""+term+"\""+"~"+slop;
	try{
		QueryParser parser = new QueryParser(Version.LUCENE_34, field ,analyzer);
					
		Query rq = parser.parse(term);
				
		QueryScorer scorer = new QueryScorer (rq,IndexReader.open(directory), field);
	  
		ResultFormatter rf= new ResultFormatter ();
		
		Highlighter highlighter = new Highlighter(rf,scorer);
		
		highlighter.getBestFragment(analyzer,field, text);
			
		return rf.getMatch();
	}
	catch (Exception e)
	{
		System.out.println("Error during search process on SearchEngine class");
	}
	return null;
		
	}
	
	

	public void testAnalyzer(String text, String language)
	{
		try {
		
		directory = new RAMDirectory();
				
		StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_34, new  HashSet<String>());
				
		config = new IndexWriterConfig (Version.LUCENE_34, analyzer);
		
		indexWriter = new IndexWriter(directory, config);
		
		TokenStream stream = analyzer.tokenStream(FieldName.CONTENTS, new StringReader(text));
		
		while(stream.incrementToken())
		{
		Iterator list =stream.getAttributeImplsIterator();
		//stream.g
       while(list.hasNext())
       {  Object x = list.next();
    	   System.out.print("["+x+ "] ");
    	   break;
      
       }
       
	}
		System.out.println();
		}
		catch(Exception e)
		{
			System.out.println("error");
		}
}
	
	
	
	public static void main (String [] args){
	
		
			SearchEngine lpe = new SearchEngine(Properties.FRENCH_LANGUAGE);
			//String sb = FileIO.readFile(new File("text.txt"), "UTF-8");
			//String sb = lpe.loadTextFile("c:\\Temp\\text2.txt");
			String sb="le traitement de la maladie est difficile! Av.-JC etc. a.b.c. J.-C";
			//sb="I just ousted Jason S. as salon.net the mayor Roy's Barber of Shop on @foursquare! http://t.co/bYPHrIql";
			lpe.testAnalyzer(sb,Properties.FRENCH_LANGUAGE);
			/*
			lpe.createIndex(FieldName.CONTENTS,sb.toString());
			
			List<MatchResult> list=lpe.search(FieldName.CONTENTS,sb,"new york citi",1);
			
			
			
			for(MatchResult r : list)
			{
				System.out.println(r.getValue()+" "+r.getStartPosition()+" "+r.getEndPosition());
				
			}*/
		
	}
}


