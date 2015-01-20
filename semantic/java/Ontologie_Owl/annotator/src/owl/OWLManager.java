package owl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import edu.stanford.smi.protege.exception.OntologyLoadException;
import edu.stanford.smi.protegex.owl.ProtegeOWL;
import edu.stanford.smi.protegex.owl.model.OWLModel;
import edu.stanford.smi.protegex.owl.model.RDFProperty;
import edu.stanford.smi.protegex.owl.model.impl.DefaultOWLIndividual;
import edu.stanford.smi.protegex.owl.model.impl.DefaultOWLNamedClass;

public class OWLManager {
	
	
	public HashMap<String, ArrayList<Individual>> getOntologyIndividual(OWLModel owlModel){
		HashMap<String, ArrayList<Individual>> results = new HashMap<String, ArrayList<Individual>>();
		RDFProperty formatting_propertie = owlModel.getRDFProperty("Champ_Sémantique");
		for(Object ind : owlModel.getOWLIndividuals()){
			if(ind instanceof DefaultOWLIndividual){
				DefaultOWLIndividual i = (DefaultOWLIndividual)ind;
				Individual individual = new Individual(i.getURI(),i.getLocalName());
				for(Object label : i.getLabels()){
					individual.addLabel((String)label);
				}
				String formatting =(String)i.getPropertyValue(formatting_propertie);
				individual.setFormatting(formatting);
				
				for(Object type : i.getDirectTypes()){
					if(type instanceof DefaultOWLNamedClass){
						DefaultOWLNamedClass cls = (DefaultOWLNamedClass)type;
						individual.addConcept(cls.getLocalName());
						ArrayList<Individual> lists = results.get(cls.getLocalName());
						if(lists == null){
							lists = new ArrayList<Individual>();
							lists.add(individual);
							results.put(cls.getLocalName(), lists);
						}else{
							lists.add(individual);
						}
					}
				}
			}
		}
		return results;
	}
	public HashMap<String, Concept> getOntologyConcept(OWLModel owlModel){
		HashMap<String, Concept> concepts = new HashMap<String, Concept>();
		RDFProperty definition_propertie = owlModel.getRDFProperty("Définition");
		RDFProperty linguistic_sign_propertie = owlModel.getRDFProperty("Signe_Linguistique");
		RDFProperty exact_linguistic_sign_propertie = owlModel.getRDFProperty("Signe_Linguistique_Exacte");
		RDFProperty formatting_propertie = owlModel.getRDFProperty("Champ_Sémantique");
		
		HashMap<String, ArrayList<Individual>> individuals = getOntologyIndividual(owlModel);
		for(Object cls : owlModel.getRDFSClasses()){
			
			if(cls instanceof DefaultOWLNamedClass){
				DefaultOWLNamedClass concept = (DefaultOWLNamedClass)cls;
				if(concept.getURI().contains("http://www.owl-ontologies.com/")){
				//	System.out.println(concept.getURI());
					Concept c = new Concept(concept.getURI(),concept.getLocalName());
					
					for(Object label : concept.getLabels()){
						c.addLabel((String)label);
					}
					
					String definition = (String)concept.getPropertyValue(definition_propertie);
					c.setDefinition(definition);
					
					for(Object sign : concept.getPropertyValues(linguistic_sign_propertie)){
						c.addLinguistic_signs((String)sign);
					}
					if(exact_linguistic_sign_propertie != null){
						for(Object exact_sign : concept.getPropertyValues(exact_linguistic_sign_propertie)){
							c.addExact_Linguistic_signs((String)exact_sign);
						}
					}
					//System.out.println(formatting_propertie);
					String formatting = (String)concept.getPropertyValue(formatting_propertie);
					c.setSemanticField(formatting);
					
					
					
					
					c.setFathers(getFatherConcepts(concept));
					c.setChildrens(getChildrenConcepts(concept));
					//System.out.println(c);
					ArrayList<Individual> lists = individuals.get(c.getLocalName());
					if(lists != null){
						c.setIndividuals(lists);
					}
					//System.out.println(c);
					concepts.put(c.getLocalName(), c);
				}
				
				
				
			}
		}
		
		return concepts;
	
	}
	
	private Set<String> getFatherConcepts(DefaultOWLNamedClass concept){
		Set<String> fathers = new HashSet<String>();
		for(Object cls : concept.getDirectSuperclasses()){
			if(cls instanceof DefaultOWLNamedClass){
				fathers.add(((DefaultOWLNamedClass)cls).getLocalName());
			}
		}
		return fathers;
	}
	
	private Set<String> getChildrenConcepts(DefaultOWLNamedClass concept){
		Set<String> childrens = new HashSet<String>();
		for(Object cls : concept.getDirectSubclasses()){
			if(cls instanceof DefaultOWLNamedClass){
				childrens.add(((DefaultOWLNamedClass)cls).getLocalName());
			}
		}
		return childrens;
	}
	
	public OWLModel loadOWLModel (File  file){		
		
			try{
				try {
					return ProtegeOWL.createJenaOWLModelFromInputStream(new FileInputStream(file));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}catch (OntologyLoadException e){
				
			}
			
			return null;
			
					
	}
	
	public static void main(String [] args){
		
		OWLManager manager = new OWLManager();
		String ontology = "ontologie.owl";
		OWLModel model = manager.loadOWLModel(new File(ontology));
		manager.getOntologyConcept(model);
		
		
		
		
	}

}
