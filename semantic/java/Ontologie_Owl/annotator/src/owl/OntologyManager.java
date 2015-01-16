package owl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import util.Parameters;
import edu.stanford.smi.protegex.owl.model.OWLModel;

public class OntologyManager {
	
	private static OWLModel ontology = null;
	private static OWLManager owlManager = new OWLManager();
	
	private static void loadOntology(){
		if(ontology == null){
			ontology = owlManager.loadOWLModel(new File(Parameters.GET_ONTOLOGY_PATH()));
		}
	}
	
	public static HashMap<String, ArrayList<Individual>> getOntologyIndividual(){
		loadOntology();
		return owlManager.getOntologyIndividual(ontology);
	}
	
	public static HashMap<String, Concept> getOntologyConcept(){
		loadOntology();
		return owlManager.getOntologyConcept(ontology);
	}
	
	public static Set<Individual> getIndividualList(){
		loadOntology();
		Set<Individual> results = new HashSet<Individual>(); 
		HashMap<String, ArrayList<Individual>> individuals =  owlManager.getOntologyIndividual(ontology);
		for(String key : individuals.keySet()){
			results.addAll(individuals.get(key));
		}
		
		return results;
	}
	
	public static void main(String [] args){
		int size = 0;
		for(String key : getOntologyIndividual().keySet()){
			size = size + getOntologyIndividual().get(key).size();
		}
		
		System.out.println(size);
		System.out.println(getIndividualList().size());
	}
	
	

}
