package sax;

import ir.TextAnnotator;

import java.io.*;
import java.util.ArrayList;

import org.xml.sax.*;

import javax.xml.parsers.*;

import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;


public class TEIAnnotator extends DefaultHandler implements LexicalHandler
{
    private static final String BODY = "body";
	//static String displayText[] = new String[2500];
	static ArrayList<String> xmlBuffer = new ArrayList<String>();
	
    static int numberLines = 0;
    static boolean newCData = false;
    static boolean isBody = false;
    static String cdata = "";
    
    public static void main(String args[])
    {
        TEIAnnotator obj = new TEIAnnotator();
        
       	obj.annotate("aubignac_dissertation_1666.xml");
       
       	
        

        try {
           // FileWriter filewriter = new FileWriter("new.xml");
            OutputStreamWriter streamWriter = new OutputStreamWriter(new FileOutputStream(new File("new.xml")), "UTF-8");


               for(int loopIndex = 0; loopIndex < xmlBuffer.size(); loopIndex++){

            	   //streamWriter.write(displayText[loopIndex].toCharArray());
            	   streamWriter.write(xmlBuffer.get(loopIndex).toCharArray());
            	   

            	}

               streamWriter.close();
        }
        catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    public void annotate(String uri)
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
    	xmlBuffer.add( "<?xml version=\"1.0\" encoding=\""+"UTF-8" + "\"?>\n");
//        displayText[numberLines] = "";
//        displayText[numberLines] += "<?xml version=\"1.0\" encoding=\""+
//            "UTF-8" + "\"?>\n";
        numberLines++;
    }

    public void processingInstruction(String target, String data)
    {
    	String text = "";
//        displayText[numberLines] = "";
//        displayText[numberLines] += "<?";
//        displayText[numberLines] += target;
//        if (data != null && data.length() > 0) {
//            displayText[numberLines] += ' ';
//            displayText[numberLines] += data;
//        }
//        displayText[numberLines] += "?>";
    	
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
      
//    	displayText[numberLines] = "";
//        displayText[numberLines] += '<';
//        displayText[numberLines] += qualifiedName;
//        if (attributes != null) {
//            int numberAttributes = attributes.getLength();
//            for (int loopIndex = 0; loopIndex < numberAttributes; loopIndex++){
//                displayText[numberLines] += ' ';
//                displayText[numberLines] += attributes.getQName(loopIndex);
//                displayText[numberLines] += "=\"";
//                displayText[numberLines] += attributes.getValue(loopIndex);
//                displayText[numberLines] += '"';
//            }
//        }
//        displayText[numberLines] += '>';
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
            }
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
    	
//    	displayText[numberLines] = "";
//            displayText[numberLines] += newCData;
    	
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
      
//    	displayText[numberLines] = "";
//        displayText[numberLines] += "</";
//        displayText[numberLines] += qualifiedName;
//        displayText[numberLines] += '>';
    	
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
