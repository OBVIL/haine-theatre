package web;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.UUID;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;

import sqlite.SemanticChapterStat;
import sqlite.SemanticTag;
import util.FileIO;
import util.Parameters;
import util.TextUtil;

public class TEIToSqlite extends DefaultHandler implements LexicalHandler{
	
    private static final String BODY = "body";
	
	//static ArrayList<String> xmlBuffer = new ArrayList<String>();
	
	   
    static boolean isBody = false;
    static StringBuffer cdata = new StringBuffer();
    static String div ="div";
    static String term ="term";
    static boolean isChapter = false;
    static int chapterOpened = 0;
    static int semanticTermCount = 0;
    static String chapter_id = "";
    static ArrayList<SemanticChapterStat> semanticChapterStats = new ArrayList<SemanticChapterStat>();
    static ArrayList<SemanticTag> semanticTags = new ArrayList<SemanticTag>();
    
    static String dataBase = "CREATE  TABLE  IF NOT EXISTS \"SemanticChapterStat\" (\n"+
    		"\"id\" VARCHAR PRIMARY KEY  NOT NULL , \n"+
    		"\"chapter_id\" VARCHAR NOT NULL , \n"+
    		"\"chapter_length\" INTEGER NOT NULL , \n"+
    		"\"chapter_term_count\" INTEGER NOT NULL , \n"+
    		"\"chapter_semantic_term_count\" INTEGER NOT NULL );\n"+
    		"\n"+
    		"CREATE  TABLE  IF NOT EXISTS \"SemanticTag\" (\n"+
    		"\"id\" VARCHAR PRIMARY KEY  NOT NULL , \n"+
    		"\"term_id\" VARCHAR NOT NULL , \n"+
    		"\"parent_id\" VARCHAR NOT NULL , \n"+
    		"\"chapter_id\" VARCHAR NOT NULL );\n\n";
   
    public static void resetAnnotator(){
		
		isBody = false;
		cdata = new StringBuffer();
		isChapter = false;
	    chapterOpened = 0;
	    semanticTermCount = 0;
	    chapter_id = "";
	    semanticChapterStats = new ArrayList<SemanticChapterStat>();
	    semanticTags = new ArrayList<SemanticTag>();
	    
		
	}
    
    public static void main(String args[])
    {
    	
    	if(args.length ==2){
    		TEIArgsReader reader = new TEIArgsReader(args);
    		TEIToSqlite obj = new TEIToSqlite();
    		File tei = new File(reader.getTEIPath());
    		StringBuffer writer = new StringBuffer();
    		writer.append(dataBase);
    		if(tei.isDirectory()){
    			for(File xmlFile : tei.listFiles()){
					System.out.println("creating sqlite code for "+xmlFile.getAbsolutePath());
					obj.teiToSqlite(xmlFile.getAbsolutePath());
					
					for(SemanticTag tag : semanticTags){
						writer.append(tag.getSqliteQuery());
			        }
			       			       
			        for(SemanticChapterStat stat : semanticChapterStats){
			        	writer.append(stat.getSqliteQuery());
			        }
			        
			        TEIToSqlite.resetAnnotator();
					
				}
    			FileIO.writeFile(writer.toString(), reader.getSQLITEPath(), Parameters.UTF8);
			}else{
				System.out.println("creating sqlite code for "+tei.getAbsolutePath());
				obj.teiToSqlite(tei.getAbsolutePath());
				for(SemanticTag tag : semanticTags){
					writer.append(tag.getSqliteQuery());
		        }
		       			       
		        for(SemanticChapterStat stat : semanticChapterStats){
		        	writer.append(stat.getSqliteQuery());
		        }
				System.out.println("Saving sqlite file in "+reader.getSQLITEPath());
				FileIO.writeFile(writer.toString(), reader.getSQLITEPath(), Parameters.UTF8);
			}
    		    		
    	}else{
    		System.out.println("Command line syntax : TEIToSQLITE \"TEI_PATH=path of the tei corpus or file\" \"SQLITE_PATH=path of the sqlite file resut\"");
    	}
    	
               	
              
    }

    public void teiToSqlite(String uri)
    {
        DefaultHandler handler = this;
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = factory.newSAXParser();
            InputSource is = new InputSource(new FileInputStream(new File(uri)));
            is.setEncoding("UTF-8");
            saxParser.setProperty("http://xml.org/sax/properties/lexical-handler",
            	       this);
            saxParser.parse(is, handler);
          
           // saxParser.parse(new File(uri), handler);
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }

    public void startDocument()
    {
    	//xmlBuffer.add( "<?xml version=\"1.0\" encoding=\""+"UTF-8" + "\"?>\n");
//        displayText[numberLines] = "";
//        displayText[numberLines] += "<?xml version=\"1.0\" encoding=\""+
//            "UTF-8" + "\"?>\n";
        
    }

    public void processingInstruction(String target, String data)
    {
    	String text = "";

    	
      text = "";
      text += "<?";
      text += target;
      if (data != null && data.length() > 0) {
    	  text += ' ';
    	  text += data;
      }
      text += "?>";
    //  xmlBuffer.add(text);
     
    }

    public void startElement(String uri, String localName,
        String qualifiedName, Attributes attributes)
    {
    	if(qualifiedName.equals(BODY)){
    		isBody = true;
    	}
    	
    	String text = "";
    	String term_id = "";
    	String parent_id = "";
      
    	text = "";
    	text += '<';
    	text += qualifiedName;
        if (attributes != null) {
            int numberAttributes = attributes.getLength();
            for (int loopIndex = 0; loopIndex < numberAttributes; loopIndex++){
            	text += ' ';
            	text += attributes.getQName(loopIndex);
            	text += "=\"";
            	text += attributes.getValue(loopIndex);
            	text += '"';
            	
            	if(qualifiedName.equalsIgnoreCase(div)){
            		
            		if((attributes.getQName(loopIndex).equalsIgnoreCase("type")
                			&& (attributes.getValue(loopIndex).equalsIgnoreCase("article")
                        			|| attributes.getValue(loopIndex).equalsIgnoreCase("chapter")))){
            			isChapter = true;
            		}
            		if(attributes.getQName(loopIndex).equalsIgnoreCase("xml:id")){
            			chapter_id = attributes.getValue(loopIndex);
            		}
            		
            	}
            	
            	if(qualifiedName.equalsIgnoreCase(term)){
            		
            		if(attributes.getQName(loopIndex).equalsIgnoreCase("type")){
            			parent_id = attributes.getValue(loopIndex);
            		}
            		
            		if(attributes.getQName(loopIndex).equalsIgnoreCase("rend")){
            			term_id = attributes.getValue(loopIndex);
            		}
            	
            	}
            	
            	
            }
        }
        text += '>';
        
       // xmlBuffer.add(text);
       if(isChapter && qualifiedName.equalsIgnoreCase(div)){
    	   chapterOpened ++;
    	   System.out.println(text + " "+isChapter+" "+chapterOpened);
    	   
       }
       
       if(isChapter && qualifiedName.equalsIgnoreCase(term)){
    	   SemanticTag semanticTag = new SemanticTag();
    	   semanticTag.setId(UUID.randomUUID().toString());
    	   semanticTag.setTerm_id(term_id);
    	   semanticTag.setParent_id(parent_id);
    	   semanticTag.setChapter_id(chapter_id);
    	   semanticTags.add(semanticTag);
    	   semanticTermCount ++;
       
       }
       
       
       
    }
    

    public void characters(char characters[], int start, int length)
    {
        String characterData = (new String(characters, start, length));
      	
        if(isChapter){
        	 cdata .append(characterData+" ");
        }
       
        
             
    }
    
   
    public void ignorableWhitespace(char characters[], int start, int length)
    {
        characters(characters, start, length);
    }

    public void endElement(String uri, String localName, String qualifiedName)
    {
    	String text = "";
    	
    	if(qualifiedName.equalsIgnoreCase(div) && isChapter){
    		chapterOpened --;
    	}
    	if(qualifiedName.equalsIgnoreCase(div) && chapterOpened == 0 && isChapter){
    		isChapter = false;
    		String data = cdata.toString();
    		data = TextUtil.removeDoubleSpace(data);
    		System.out.println("cdata length "+cdata.length());
    		SemanticChapterStat scs = new SemanticChapterStat();
    		scs.setId(UUID.randomUUID().toString());
    		scs.setChapter_id(chapter_id);
    		scs.setChapter_length(data.length());
    		scs.setChapter_semantic_term_count(semanticTermCount);
    		scs.setChapter_term_count(data.split("\\s+").length);
    		scs.setText(data);
    		semanticChapterStats.add(scs);
    		cdata = new StringBuffer();
    		semanticTermCount = 0;
    		System.out.println("closed chapter with xml id "+chapterOpened);
    	}
    	
    	text = "";
    	text += "</";
    	text += qualifiedName;
    	text += '>';
    //	xmlBuffer.add(text);
        

    }

    public void warning(SAXParseException exception)
    {
        System.err.println("Warning: " +
            exception.getMessage());
    }

    public void error(SAXParseException exception)
    {
        System.err.println("Error: " +
            exception.getMessage());
    }

    public void fatalError(SAXParseException exception)
    {
        System.err.println("Fatal error: " +
            exception.getMessage());
    }

	@Override
	public void comment(char[] characters, int start, int length) throws SAXException {
		String characterData = (new String(characters, start, length));
      	//displayText[numberLines] = "";
      	//xmlBuffer.add("");
        //cdata = cdata + characterData;
		//System.out.println("comment "+characterData);
		//xmlBuffer.add("\n<!--"+characterData+"-->");
      
        //newCData = true;
		
	}

	@Override
	public void endCDATA() throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endDTD() throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endEntity(String arg0) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startCDATA() throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startDTD(String arg0, String arg1, String arg2)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startEntity(String arg0) throws SAXException {
		// TODO Auto-generated method stub
		
	}

}