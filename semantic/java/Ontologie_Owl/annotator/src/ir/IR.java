package ir;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import owl.Concept;
import owl.Individual;
import owl.OWLManager;
import owl.OntologyManager;
import util.FileIO;
import util.Parameters;
import util.TextUtil;
import edu.stanford.smi.protegex.owl.model.OWLModel;



public class IR {
	// building term unique value for the TEI file
	private static HashMap<String, String> FORMS_KEYS = new HashMap<String, String>();
   
	private static HashMap<String, String> accents = new HashMap<String, String>();
	
	private static void ADD_FORMS_KEYS(String id, String term){
		id = TextUtil.stripFirstAccent(id.toLowerCase()).replace("-", " ");
		FORMS_KEYS.put(id, term);
	}
	
	public static String GET_FORMS_KEYS(String id){
		
		id = TextUtil.stripFirstAccent(id.toLowerCase()).replace("-", " ");
		return  FORMS_KEYS.get(id);
	}
	        
	private static HashMap<String, String> getAccents(){
		if(accents.isEmpty()){
			accents.put("�", "[�E"+"�".toUpperCase()+"]");
			accents.put("�", "[�E"+"�".toUpperCase()+"]");
			accents.put("�", "[�E"+"�".toUpperCase()+"]");
			accents.put("�", "[�A"+"�".toUpperCase()+"]");
			accents.put("�", "[�A"+"�".toUpperCase()+"]");
			accents.put("�", "[�"+"�".toUpperCase()+"]");
			accents.put("�", "[�"+"�".toUpperCase()+"]");
			accents.put("i", "[i"+"�".toUpperCase()+"]");
			accents.put("�", "[i"+"�".toUpperCase()+"]");
			
			accents.put("�".toUpperCase(), "[�E"+"�".toUpperCase()+"]");
			accents.put("�".toUpperCase(), "[�E"+"�".toUpperCase()+"]");
			accents.put("�".toUpperCase(), "[�E"+"�".toUpperCase()+"]");
			accents.put("�".toUpperCase(), "[�A"+"�".toUpperCase()+"]");
			accents.put("�".toUpperCase(), "[�A"+"�".toUpperCase()+"]");
			accents.put("�".toUpperCase(), "[�"+"�".toUpperCase()+"]");
			accents.put("�".toUpperCase(), "[�"+"�".toUpperCase()+"]");
			accents.put("i".toUpperCase(), "[i"+"�".toUpperCase()+"]");
			accents.put("�".toUpperCase(), "[i"+"�".toUpperCase()+"]");
			
			accents.put("�", "[�"+"�".toUpperCase()+"]");
			accents.put("�".toUpperCase(), "[�"+"�".toUpperCase()+"]");
		}
		
		return accents;
	}
	
  private static String changeAccentToRegex(String term){
		
		String [] tokens = term.split(" ");
		String newTerm = "";
		for(String token : tokens){
			String firstChar = token.substring(0, 1);
			String regex = getAccents().get(firstChar);
			if(regex == null){
				regex = firstChar;
			}
			newTerm = newTerm + " " + regex + token.substring(1);
		}
		
		return newTerm.trim();
		
	}
	
	public static List<Position> matchTerm(String term, String text){
		List<Position> positions = new ArrayList<Position>();
		try{
			   Pattern pattern = Pattern.compile("\\b" + term  + "\\b");
			   Matcher matcher = pattern.matcher(text);
			   while (matcher.find()){
				   Position position = new Position(matcher.start(), matcher.end(), text.substring(matcher.start(), matcher.end()));
				   //System.out.println(text.substring(matcher.start(), matcher.end()));
				   positions.add(position);
			   }
			      
			}catch(PatternSyntaxException pse){
			
		}

		
		return positions;
	}
	
	public static List<Position> matchTerm(List<String> terms, String text){
		List<Position> positions = new ArrayList<Position>();
		String regex = "";
		
		Collections.sort(terms, new StringComparator());
		
		for(String term : terms){
			term = term.replace("-", "\\-");
			//regex = regex + "\\b" + term +"\\b|";
			regex = regex + "\\b" + changeAccentToRegex(term) +"\\b|";
		}
		
		regex = regex.substring(0, regex.length() - 1);
		
		//System.out.println(regex);
		try{
			   //Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
			   Pattern pattern = Pattern.compile(regex);
			   Matcher matcher = pattern.matcher(text);
			   while (matcher.find()){
				   Position position = new Position(matcher.start(), matcher.end(), text.substring(matcher.start(), matcher.end()));
				   //System.out.println(text.substring(matcher.start(), matcher.end()));
				   positions.add(position);
			   }
			      
			}catch(PatternSyntaxException pse){
			
		}

		
		return positions;
	}
	
	public static List<Position> matchRegex(String regex, String text){
		List<Position> positions = new ArrayList<Position>();
				
		try{
			  
			   Pattern pattern = Pattern.compile(regex);
			   Matcher matcher = pattern.matcher(text);
			   while (matcher.find()){
				   Position position = new Position(matcher.start(), matcher.end(), text.substring(matcher.start(), matcher.end()));
				   //System.out.println(text.substring(matcher.start(), matcher.end()));
				   positions.add(position);
			   }
			      
			}catch(PatternSyntaxException pse){
			
		}

		
		return positions;
	}
	
	public static void matchWithOntology(String ontolgyPath, String textPath){
		String txt = FileIO.readFile(new File(textPath), Parameters.UTF8);
		
		HashMap<String, List<Position>> conceptMetaData = new HashMap<String, List<Position>>();
		HashMap<String, List<Position>> individuMetaData = new HashMap<String, List<Position>>();
				
		OWLManager manager = new OWLManager();
		
		System.err.println("Loading ontology...");
		OWLModel model = manager.loadOWLModel(new File(ontolgyPath));
		HashMap<String, Concept> concepts = manager.getOntologyConcept(model);
		Set<Individual> individuals = OntologyManager.getIndividualList();
		System.err.println("Loading ontology done.");
		for(String key : concepts.keySet()){
			Concept c = concepts.get(key);
			System.err.println("Annotating text with the concept "+key);
			List<String> terms = new ArrayList<String>();
			List<String> exact_terms = new ArrayList<String>();
			List<String> search_terms = new ArrayList<String>();
						
			terms.addAll(c.getLinguistic_signs());
			exact_terms.addAll(c.getExact_Linguistic_signs());
			
			if(!terms.isEmpty()){
				Set<String> forms = new HashSet<String>();
				for(String term : terms){
					forms.addAll(Morphalou.getWordForms(term));
				}
				terms.clear();
				terms.addAll(forms);
				
				for(String term : terms){
					search_terms.add("(?i)_"+term);
				}

			}
			
			if(!exact_terms.isEmpty()){
				Set<String> forms = new HashSet<String>();
				for(String exact_term : exact_terms){
					forms.addAll(Morphalou.getWordForms(exact_term));
				}
				exact_terms.clear();
				exact_terms.addAll(forms);
				
				for(String exact_term : exact_terms){
					search_terms.add("(nn)_"+exact_term);
				}

			}
			if(!search_terms.isEmpty()){
				//order list of terms and exact terms
				Collections.sort(search_terms, new StringComparator());
				
				//transform list in regex expression
				String regex = "";
				for(String search_term : search_terms){
					if(search_term.startsWith("(nn)_")){
						regex = regex + "\\b" + search_term.replace("(nn)_", "") + "\\b|" ;
					}
					
					if(search_term.startsWith("(?i)_")){
						regex = regex + "((?i)\\b" + search_term.replace("(?i)_", "") + "\\b)|" ;
					}
				}
				regex = regex.substring(0, regex.length() - 1);
				regex = regex.replace("-", "\\-");
				List<Position> positions = IR.matchRegex(regex, txt);
				
				if(!positions.isEmpty()){
					conceptMetaData.put(key, positions);
				}
			}
			
		
		}
		
		/*for(String key : concepts.keySet()){
			Concept c = concepts.get(key);
			
			for(Individual individual : c.getIndividuals()){
				if(!individual.getLabels().isEmpty()){
					System.err.println("Annotating text with the individual "+individual.getLabels().iterator().next());
					List<String> terms = new ArrayList<String>();
					terms.addAll(individual.getLabels());
					List<Position> positions = IR.matchTerm(terms, txt);
					if(!positions.isEmpty()){
						individuMetaData.put(individual.getLocalName(), positions);
					}
				}
				
				
			}
			
			
				
		}*/
		
		for(Individual individual : individuals){
			if(!individual.getLabels().isEmpty()){
				//System.err.println("Annotating text with the individual "+individual.getLabels().iterator().next());
				List<String> terms = new ArrayList<String>();
				terms.addAll(individual.getLabels());
				List<Position> positions = IR.matchTerm(terms, txt);
				if(!positions.isEmpty()){
					individuMetaData.put(individual.getLocalName(), positions);
				}
			}
		}
		
		for(String key : conceptMetaData.keySet()){
			
			Set<String> instances = new HashSet<String>();
			for(Position position : conceptMetaData.get(key)){
				instances.add(position.getText());
			}
			
			System.out.println(key + " terms "+ instances + " size "+ conceptMetaData.get(key).size());
		}
		System.out.println("--------------------------------------------");
		for(String key : individuMetaData.keySet()){
			
			Set<String> instances = new HashSet<String>();
			for(Position position : individuMetaData.get(key)){
				instances.add(position.getText());
			}
			
			System.out.println(key + " terms "+ instances+ " size "+ individuMetaData.get(key).size());
		}
//		List<String> tagss = new ArrayList<String>();
//		tagss.addAll(tags);
//		Collections.sort(tagss, new stringComparator());
//		System.out.println(tagss);
//		for(String tag : tagss){
//			txt = txt.replace(tag, "<FONT style=\"BACKGROUND-COLOR: yellow\">"+tag+"</FONT>");
//		}
//		
//		FileIO.writeFile(txt, "D:\\text.html", "UTF-8");
		
	}
	
	public static HashMap<String,  HashMap<String, List<Position>>> _matchWithOntology(String txt){
				
		HashMap<String, List<Position>> conceptMetaData = new HashMap<String, List<Position>>();
		HashMap<String, List<Position>> individualMetaData = new HashMap<String, List<Position>>();
		HashMap<String, HashMap<String, List<Position>>> results = new HashMap<String, HashMap<String, List<Position>>>();
		//System.err.println("Loading ontology...");
		HashMap<String, Concept> concepts = OntologyManager.getOntologyConcept();
		Set<Individual> individuals = OntologyManager.getIndividualList();
		//System.err.println("Loading ontology done.");
		
		for(String key : concepts.keySet()){
			Concept c = concepts.get(key);
			//System.err.println("Annotating text with the concept "+key);
			List<String> terms = new ArrayList<String>();
			List<String> exact_terms = new ArrayList<String>();
			List<String> search_terms = new ArrayList<String>();
						
			terms.addAll(c.getLinguistic_signs());
			exact_terms.addAll(c.getExact_Linguistic_signs());
			
			if(!terms.isEmpty()){
				Set<String> forms = new HashSet<String>();
				for(String term : terms){
					Set<String> _forms = Morphalou.getTermForms(term);
					Iterator<String> iter = _forms.iterator();
					while(iter.hasNext()){
						String element = iter.next();
						ADD_FORMS_KEYS(element, term);
					}
					forms.addAll(_forms);
				}
				terms.clear();
				terms.addAll(forms);
				
				for(String term : terms){
					search_terms.add("(?i)_"+term);
				}

			}
			
			if(!exact_terms.isEmpty()){
				Set<String> forms = new HashSet<String>();
				for(String exact_term : exact_terms){
					Set<String> _forms = Morphalou.getTermForms(exact_term);
					Iterator<String> iter = _forms.iterator();
					while(iter.hasNext()){
						String element = iter.next();
						ADD_FORMS_KEYS(element, exact_term);
					}
					forms.addAll(_forms);
				}
				exact_terms.clear();
				exact_terms.addAll(forms);
				
				for(String exact_term : exact_terms){
					search_terms.add("(nn)_"+exact_term);
				}

			}
			if(!search_terms.isEmpty()){
				//order list of terms and exact terms
				Collections.sort(search_terms, new StringComparator());
				
				//transform list in regex expression
				String regex = "";
				for(String search_term : search_terms){
					if(search_term.startsWith("(nn)_")){
						//regex = regex + "\\b" + search_term.replace("(nn)_", "") + "\\b|" ;
						regex = regex + "\\b" + changeAccentToRegex(search_term.replace("(nn)_", "")) + "\\b|" ;
					}
					
					if(search_term.startsWith("(?i)_")){
						//regex = regex + "((?i)\\b" + search_term.replace("(?i)_", "") + "\\b)|" ;
						regex = regex + "((?i)\\b" + changeAccentToRegex(search_term.replace("(?i)_", "")) + "\\b)|" ;
					}
				}
				regex = regex.substring(0, regex.length() - 1);
				regex = regex.replace("-", "[\\-\\s]+");
				regex = regex.replace(" ", "[\\-\\s]+");
				//System.out.println(regex);
				List<Position> positions = IR.matchRegex(regex, txt);
				
				if(!positions.isEmpty()){
					conceptMetaData.put(key, positions);
				}
			}
			
		
		}
					
		for(Individual individual : individuals){
			if(!individual.getLabels().isEmpty()){
				//System.err.println("Annotating text with the individual "+individual.getLabels().iterator().next());
				List<String> terms = new ArrayList<String>();
				terms.addAll(individual.getLabels());
				List<Position> positions = IR.matchTerm(terms, txt);
				if(!positions.isEmpty()){
					individualMetaData.put(individual.getLocalName(), positions);
				}
			}
		}
		
		/*for(String key : conceptMetaData.keySet()){
			
			Set<String> instances = new HashSet<String>();
			for(Position position : conceptMetaData.get(key)){
				instances.add(position.getText());
			}
			
			System.out.println(key + " terms "+ instances + " size "+ conceptMetaData.get(key).size());
		}
		System.out.println("--------------------------------------------");
		for(String key : individualMetaData.keySet()){
			
			Set<String> instances = new HashSet<String>();
			for(Position position : individualMetaData.get(key)){
				instances.add(position.getText());
			}
			
			System.out.println(key + " terms "+ instances+ " size "+ individualMetaData.get(key).size());
		}
		System.out.println("--------------------------------------------");
		for(String key : FORMS_KEYS.keySet()){
			System.out.println(key+" : "+FORMS_KEYS.get(key));
		}*/
		
		results.put(Parameters.CONCEPT, conceptMetaData);
		results.put(Parameters.INDIVIDUAL, individualMetaData);
		
		return results;
		
		
	}
	
	public static void main(String[] args) {
		
		IR._matchWithOntology(FileIO.readFile(new File("text.txt"), "UTF-8"));

	}
	
	

}
