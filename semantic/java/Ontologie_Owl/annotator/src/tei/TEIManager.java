package tei;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import util.FileIO;
import util.Parameters;


public class TEIManager {
	private static String START_BODY = "<body>";
	private static String END_BODY = "</body>";
	
	
	public static String getBody(String filePath){
		String txt = FileIO.readFile(new File(filePath), Parameters.UTF8);
		int start = txt.indexOf(START_BODY);
		int end = txt.indexOf(END_BODY);
		String body = txt.substring(start+START_BODY.length(), end);
		//System.out.println(body);
		
				
		//SPLIT TEXT WITH XML TAGS
		body = body.replaceAll("\n", " ");
		body = body.replaceAll("(</*[a-zA-Z_0-9:=\" ]+/*>)", "\n$1\n");
		body = body.replaceAll("(\n){2,}", "\n");
		FileIO.writeFile(body, "body.txt", Parameters.UTF8);
		
		String [] elements = body.split("\n");
		
		for(String element : elements){
			if(!element.matches("</*[a-zA-Z_0-9:=\" ]+/*>")){
				System.out.println(element);
			}
		}
		return body;
	}
	
	 
	 
	public static List<Article> parseTEIFile(String filePath){
		List<Article> articles = new ArrayList<Article>();
		try{
			 DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			 DocumentBuilder builder = factory.newDocumentBuilder();
			 Document document = builder.parse(new File(filePath));
			 NodeList nodeList = document.getDocumentElement().getChildNodes();
			 
			 String fileName = filePath;
			 for(int i = 0 ; i< nodeList.getLength();i++){
				 Node element = nodeList.item(i);
				 if(element.getNodeName().equals("text")){
					 NodeList elements = element.getChildNodes();
					 for(int j = 0 ; j < elements.getLength(); j++){
						 Node child = elements.item(j);
						 if(child.getNodeName().equals("body")){
							 
							 NodeList childs = child.getChildNodes();
							 for(int k = 0 ; k < childs.getLength(); k++){
								 Node article = childs.item(k);
								 if(article.getNodeName().equalsIgnoreCase("div")){
									// if(article.getAttributes().getNamedItem("type") !=null 
										//	 && article.getAttributes().getNamedItem("type").getNodeValue().equals("article")){
										 	//System.out.println(article.getTextContent());
										 	String text = article.getTextContent();
										 	String headText = "";
										 	//IR._matchWithOntology(article.getTextContent());
										 	 NodeList heads = article.getChildNodes();
											 for(int l = 0 ; l < heads.getLength(); l++){
												 Node head = heads.item(l);
												 if(head.getNodeName().equals("head")){
													 //System.out.println(head.getTextContent());
													 headText = head.getTextContent();
													 break;
													 
												 }
											 }
										 articles.add(new Article(fileName, headText, text));
									// }
										 		
								 }
							 }
							 
							 break;
							 
						 }
					 }
					 
					 break;
					 
				 }
					 
			 }
			
		}catch(Exception e){
			
		}
		
		return articles;
		
	}
	
	public static List<Article> parseTEIFile2(String filePath){
		List<Article> articles = new ArrayList<Article>();
		try{
			 DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			 DocumentBuilder builder = factory.newDocumentBuilder();
			 Document document = builder.parse(new File(filePath));
			 NodeList divList = document.getElementsByTagName("div");
			  String fileName = filePath;
			  
			 for(int i = 0 ; i< divList.getLength();i++){
				 String head = "";
				 String text = "";
				 Node divElement = divList.item(i);
				 NodeList elements = divElement.getChildNodes();
				 for(int j = 0 ; j < elements.getLength(); j++){
					Node element = elements.item(j);
					if(element.getNodeName().equals("head")){
						head = element.getTextContent();
					}
					if(element.getNodeName().equals("p")){
						text = text + element.getTextContent() +"\n";
					}
					if(element.getNodeName().equals("div")){
						System.out.println(((Element)element).getElementsByTagName("div").getLength());
					}
								
					 
				 }
					articles.add(new Article(fileName, head, text)); 
			 }
			
		}catch(Exception e){
			
		}
		
		return articles;
		
	}
	public static void main(String [] args) throws Exception{
		//List<Article> articles = TEIReader.getBody("voisin_defense_1671.xml");
		TEIManager.getBody("voisin_defense_1671.xml");
//		System.out.println(articles.size());
//		for(Article a : articles){
//			System.out.println(a.getFileName() + " "+a.getHead().replace("\n"," ").trim() + " "+a.getText().replace("\n", " ").trim());
//		}
		
		 
		

	}

}
