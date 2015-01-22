package ir;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import util.Parameters;
import util.TextUtil;

public class Morphalou {
	
	private static HashMap<String, String> morphalouList = new HashMap<String, String>();
	private static HashMap<String, Set<String>> morphalouForms = new HashMap<String, Set<String>>();
	
	private static void loadMorphalou(){
		
		if(morphalouList.isEmpty()){
			System.err.println("Loading morphalou...");
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader (new FileInputStream(Parameters.GET_MORPHALOU_PATH()),Parameters.UTF8));
		    		       
		        String line = br.readLine();
		        while (line != null) {
		        	String [] elements = line.split("\t");
		        	String form = elements[0];
		        	String lemma = elements[elements.length-1];
					
		        	morphalouList.put(form, lemma);
					
					Set<String> forms = morphalouForms.get(lemma);
					if(forms == null){
						forms = new HashSet<String>();
						forms.add(form);
						morphalouForms.put(lemma, forms);
					}else{
						forms.add(form);
						
					}
					
		            line = br.readLine();
		        }
		        System.err.println("Loading morphalou done.");
		        br.close();
		    } catch (Exception e) {
		        
		    }
					
		}
		
		
		
	}
	
		
	public static Set<String> getTermForms(String term){
		loadMorphalou();
		Set<String> forms = new HashSet<String>();
		String lemma = 	morphalouList.get(term.toLowerCase());
		
		forms.add(term);
		if(lemma != null){
			Set<String> results =  morphalouForms.get(lemma);
			if(results != null){
				boolean isCapitalize = TextUtil.isCapitalize(term);
				for(String form : results){
					if(isCapitalize){
						form = TextUtil.capitalize(form);
					}
					forms.add(form);
				}
			}
		}
						
		return forms;
		
	}
	
	public static void main(String [] args){
		System.out.println(Morphalou.getTermForms("repr�sentative"));
		
		System.out.println(Morphalou.getTermForms("T"));
		
		//System.out.println(Morphalou.capitalize("p�re mich�le"));
		//System.out.println(Morphalou.isCapitalize("P�re Mich�le"));
	}

}