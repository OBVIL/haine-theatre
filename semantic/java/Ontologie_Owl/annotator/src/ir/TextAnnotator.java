package ir;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import owl.OntologyManager;
import util.FileIO;
import util.Parameters;
import util.TextUtil;

public class TextAnnotator {
	
	private static String START_BODY = "<body>";
	private static String END_BODY = "</body>";
		
	
	private static String getTEIBody(String text){
		int start = text.indexOf(START_BODY);
		int end = text.indexOf(END_BODY);
		String body = text.substring(start+START_BODY.length(), end);
		return body;
	}
	
	private static String annotateXMLText(String text){
		StringBuffer newXMLText = new StringBuffer();
		//text = text.replaceAll("\n", " ");
		text = text.replaceAll("(</*[�������a-zA-Z_0-9:=\" ]+/*>)", "\r$1\r");
		//text = text.replaceAll("(\n){2,}", "\n");
		//FileIO.writeFile(text, "body.txt", Parameters.UTF8);
		
		String [] elements = text.split("\r");
		
		int counter = 1;
		for(String element : elements){
			System.out.println("Annotating part "+counter+"/"+elements.length);
			if(!element.matches("</*[�������a-zA-Z_0-9:=\" ]+/*>") && element.matches(".*[a-zA-Z]+.*")){
				//System.out.println(element);
				String newElement = annotateText(element);
				newXMLText = newXMLText.append(newElement);
				
			}else{
				newXMLText = newXMLText.append(element);
			}
			counter++;
		}
		
		return newXMLText.toString();
	}
	
	
	private static String annotateText(String text) {
		ArrayList<String> textChars = new ArrayList<String>();
		
		for(char c : text.toCharArray()){
			textChars.add(c+"");
		}
		HashMap<String, HashMap<String, List<Position>>> annotations = IR._matchWithOntology(text);
		HashMap<String, List<Position>> conceptMetaData = annotations.get(Parameters.CONCEPT);
		HashMap<String, List<Position>> individualMetaData = annotations.get(Parameters.INDIVIDUAL);
		
		for(String key : conceptMetaData.keySet()){
			String semantic_field = OntologyManager.getOntologyConcept().get(key).getSemanticField();
			if(semantic_field != null){
				for(Position position : conceptMetaData.get(key)){
					int start = position.getStart();
					int end = position.getEnd();
					String rend = IR.FORMS_KEYS.get(position.getText().toLowerCase());
					rend = TextUtil.capitalize(rend).replace(" ", "_");
					String tagStart = "<term type=\""+semantic_field+"\" rend=\""+rend+"\">"+textChars.get(start);
					String tagEnd = textChars.get(end-1)+"</term>";
					textChars.set(start, tagStart);
					textChars.set(end-1, tagEnd);
				}
			}
			
		}
		
		for(String key : individualMetaData.keySet()){
			String semantic_field = OntologyManager.getIndividualHashMap().get(key).getFormatting();
			if(semantic_field != null){
				for(Position position : individualMetaData.get(key)){
					int start = position.getStart();
					int end = position.getEnd();
					String tagStart = "<term type=\""+semantic_field+"\" rend=\""+key.replace(" ", "_")+"\">"+textChars.get(start);
					String tagEnd = textChars.get(end-1)+"</term>";
					textChars.set(start, tagStart);
					textChars.set(end-1, tagEnd);
				}
			}
			
		}
		text = "";
		
		for(String c : textChars){
			text = text + c;
		}
		
		return text;
	}
	public static void annotateTEIFile(String filePath){

		String text = FileIO.readFile(new File(filePath), Parameters.UTF8);
		
		String body = getTEIBody(text);
		
		text = text.replace(body, "$BODY_VAR");
		
		String newBodyText = annotateXMLText(body);
		
		text = text.replace("$BODY_VAR", newBodyText);
		
		FileIO.writeFile(text, "tei.xml", Parameters.UTF8);
	}
	
	public static String annotateTEIFile(File xmlFile){

		String text = FileIO.readFile(xmlFile, Parameters.UTF8);
		
		String body = getTEIBody(text);
		
		text = text.replace(body, "$BODY_VAR");
		
		String newBodyText = annotateXMLText(body);
		
		text = text.replace("$BODY_VAR", newBodyText);
				
		return text;
	}

	public static void main(String [] args){
		
		annotateTEIFile("voisin_defense_1671.xml");
		
		//String test = "bonjour tout le monde. Je suis Aubignac. Le faux raisonnement du P�re est importante";
		//System.out.println(annotateText(test));
//		String element = "bonjour tout le monde.";
//		boolean expr = !element.matches("</*[a-zA-Z_0-9:=\" ]+/*>") && element.matches(".*[a-zA-Z]+.*");
//		System.out.println(expr);
		
	}

}
