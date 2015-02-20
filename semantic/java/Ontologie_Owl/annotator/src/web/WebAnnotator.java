package web;

import ir.TextAnnotator;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;

import util.FileIO;
import util.Parameters;

public class WebAnnotator extends DefaultHandler implements LexicalHandler{

	private static final String BODY = "body";
	//static String displayText[] = new String[2500];
	static ArrayList<String> xmlBuffer = new ArrayList<String>();
	static int numberLines = 0;
	static boolean newCData = false;
	static boolean isBody = false;
	static String cdata = "";
	static String div ="div";
	static String corpusName = "";
	static int chapterCounter = 1;
	
	public static void main(String[] args) {
		
		if(args.length == 2){
			WebAnnotator annotator = new WebAnnotator();
			
			ArgsReader argsReader = new ArgsReader(args);
			String xmlPath = argsReader.getXMLPath();
			Parameters.PLUGINS_PROPERTIES_PATH = argsReader.getPluginPropertiesPath();
			File file = new File(xmlPath);
			
			if(file.isDirectory()){
				for(File xmlFile : file.listFiles()){
					System.out.println("Annotating file "+xmlFile.getAbsolutePath());
					annotator.annotate(xmlFile.getAbsolutePath());
					String path = Parameters.GET_XML_DIR_RESULT_PATH()+File.separator+xmlFile.getName();
					System.out.println("Saving annotated file in "+path);
					FileIO.writeFile(annotator.getTEIText(), path, Parameters.UTF8);
					WebAnnotator.resetAnnotator();
				}
			}else{
				System.out.println("Annotating file "+file.getAbsolutePath());
				annotator.annotate(file.getAbsolutePath());
				String path = Parameters.GET_XML_DIR_RESULT_PATH()+File.separator+file.getName();
				System.out.println("Saving annotated file in "+path);
				FileIO.writeFile(annotator.getTEIText(), path, Parameters.UTF8);
			}
			
			
		}else{
			System.out.println("Command line syntax : WebAnnotator \"XML_PATH=path of the xml corpus or file\" \"PLUGINS_PROPERTIES_PATH=path of the plugins properties\"");
		}

	}
	
	public String getTEIText(){
		StringBuffer text = new StringBuffer();
		for(int i=0;i<xmlBuffer.size();i++){
			text.append(xmlBuffer.get(i).toCharArray());
		}
		
		return text.toString();
	}
	
	public static void resetAnnotator(){
		xmlBuffer = new ArrayList<String>();
		numberLines = 0;
		newCData = false;
		isBody = false;
		cdata = "";
		chapterCounter = 1;
		corpusName = "";
		
	}
	
	 public void annotate(String uri)
	    {
	        DefaultHandler handler = this;
	        SAXParserFactory factory = SAXParserFactory.newInstance();
	        try {
	            SAXParser saxParser = factory.newSAXParser();
	            File f = new File(uri);
	            corpusName = f.getName().replace(".xml", "");
	            InputSource is = new InputSource(new FileInputStream(f));
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
	    	xmlBuffer.add( "<?xml version=\"1.0\" encoding=\""+"UTF-8" + "\"?>\n");
//	        displayText[numberLines] = "";
//	        displayText[numberLines] += "<?xml version=\"1.0\" encoding=\""+
//	            "UTF-8" + "\"?>\n";
	        numberLines++;
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
	      xmlBuffer.add(text);
	      numberLines++;
	    }

	    public void startElement(String uri, String localName,
	        String qualifiedName, Attributes attributes)
	    {
	    	if(qualifiedName.equals(BODY)){
	    		isBody = true;
	    	}
	    	if(newCData){
	    		writeCData();
	    		newCData = false;
	    		cdata = "";
	    	}
	    	String text = "";
	    	boolean isPart = false;
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
	            	
	            	if(qualifiedName.equalsIgnoreCase(div)
	            			&& (attributes.getQName(loopIndex).equalsIgnoreCase("type")
	            			&& (attributes.getValue(loopIndex).equalsIgnoreCase("article")
	            			|| attributes.getValue(loopIndex).equalsIgnoreCase("chapter")))){
	            		isPart = true;
	            	}
	            }
	        }
	        
	        if(isPart){
	        	text += " xml:id=\""+corpusName+"_"+chapterCounter+"\"";
	        	chapterCounter++;
	        	//isPart = false;
	        }
	        text += '>';
	        xmlBuffer.add(text);
	        numberLines++;
	       
	    }
	    
	    public void writeCData(){
	    	
	    	String text = "";
	   
	    	String newCData = cdata;
	    	if(cdata.indexOf("\n") < 0 && cdata.length() > 0 && isBody) {
	    		newCData = TextAnnotator.annotateText(cdata);
	    	}
	    	
//	    	displayText[numberLines] = "";
//	            displayText[numberLines] += newCData;
	    	
	    	text += newCData;
	    	text = text.replace("&", "&amp;");
	    	 xmlBuffer.add(text);
	            numberLines++;
	            if(cdata.indexOf("\n") < 0 && cdata.length() > 0) {
	            System.out.println(cdata);
	           
	            }
	           
	           
	    }

	    public void characters(char characters[], int start, int length)
	    {
	        String characterData = (new String(characters, start, length));
	      	//displayText[numberLines] = "";
	      	//xmlBuffer.add("");
	        cdata = cdata + characterData;
	        newCData = true;
	             
	    }
	    
	   
	    public void ignorableWhitespace(char characters[], int start, int length)
	    {
	        characters(characters, start, length);
	    }

	    public void endElement(String uri, String localName, String qualifiedName)
	    {
	    	String text = "";
	    	
	    	if(newCData){
	    		writeCData();
	    		newCData = false;
	    		cdata = "";
	    	}
	      
//	    	displayText[numberLines] = "";
//	        displayText[numberLines] += "</";
//	        displayText[numberLines] += qualifiedName;
//	        displayText[numberLines] += '>';
	    	
	    	text = "";
	    	text += "</";
	    	text += qualifiedName;
	    	text += '>';
	    	xmlBuffer.add(text);
	        numberLines++;

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
			xmlBuffer.add("\n<!--"+characterData+"-->");
	        numberLines++;
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
