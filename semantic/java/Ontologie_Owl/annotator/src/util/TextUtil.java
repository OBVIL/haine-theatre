package util;

import java.text.Normalizer;
import java.util.HashMap;

public class TextUtil {
	
	private static HashMap<String, String> accents = new HashMap<String, String>();
	
	public static HashMap<String, String> getAccents(){
		if(accents.isEmpty()){
			accents.put("�", "[�|E|"+"�".toUpperCase()+"]");
			accents.put("�", "[�|E|"+"�".toUpperCase()+"]");
			accents.put("�", "[�|E|"+"�".toUpperCase()+"]");
			accents.put("�", "[�|A|"+"�".toUpperCase()+"]");
			accents.put("�", "[�|A|"+"�".toUpperCase()+"]");
			
			accents.put("�".toUpperCase(), "[�|E|"+"�".toUpperCase()+"]");
			accents.put("�".toUpperCase(), "[�|E|"+"�".toUpperCase()+"]");
			accents.put("�".toUpperCase(), "[�|E|"+"�".toUpperCase()+"]");
			accents.put("�".toUpperCase(), "[�|A|"+"�".toUpperCase()+"]");
			accents.put("�".toUpperCase(), "[�|A|"+"�".toUpperCase()+"]");
			
			accents.put("�", "[�|"+"�".toUpperCase()+"]");
			accents.put("�".toUpperCase(), "[�|"+"�".toUpperCase()+"]");
		}
		
		return accents;
	}
	public static String capitalize(String term){
		//System.out.println("term ici "+term);
		String [] tokens = term.split(" ");
		String newTerm = "";
		for(String token : tokens){
			newTerm = newTerm + " " + token.substring(0, 1).toUpperCase() + token.substring(1);
		}
		
		return newTerm.trim();
		
	}
	
	public static boolean isCapitalize(String term){
		String [] tokens = term.split(" ");
		
		for(String token : tokens){
			if(!Character.isUpperCase(token.charAt(0)))
				return false;
		}
		return true;
	}
	
	public static String removeDoubleSpace(String text){
		while(text.contains("  ")){
			text = text.replaceAll("  ", " ");
		}
		
		return text.trim();
	}
	
	public static String stripAccent(String txt){
		if(txt.equals("�")){
			return "�";
		}
		txt = Normalizer.normalize(txt, Normalizer.Form.NFD);
		txt = txt.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
		
		return txt;
	}
	
	public static String stripFirstAccent(String term){
		//System.out.println("term ici "+term);
		String [] tokens = term.split(" ");
		String newTerm = "";
		for(String token : tokens){
			newTerm = newTerm + " " + stripAccent(token.substring(0, 1)) + token.substring(1);
		}
		
		return newTerm.trim();
		
	}
	public static void main(String [] args){
		String term = "�v�que de ��esar";
			System.out.println(stripFirstAccent(term));
		
	}

}
