package ir;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.Normalizer;

public class Test {

	public static String stripAccent(String txt){
		txt = Normalizer.normalize(txt, Normalizer.Form.NFD);
		txt = txt.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
		
		return txt;
	}
	public static void main(String[] args) {
		String term = "((?i)\\b[�E�]v�que\\b)";
		String text = "M. L'�v�que d'Alet �v�que";
		
		 Pattern pattern = Pattern.compile(term );
		   Matcher matcher = pattern.matcher(text);
		   while (matcher.find()){
			   Position position = new Position(matcher.start(), matcher.end(), text.substring(matcher.start(), matcher.end()));
			   //System.out.println(text.substring(matcher.start(), matcher.end()));
			   System.out.println(position);
		   }
		   
	}

}
