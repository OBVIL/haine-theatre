package util;

public class TextUtil {
	
	public static String capitalize(String term){
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

}
