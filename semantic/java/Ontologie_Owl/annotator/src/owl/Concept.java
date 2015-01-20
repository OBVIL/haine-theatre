package owl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Concept {
	
	private String uri = "";
	private String localName = "";
	private String definition = "";
	private String semantic_field = "";
	private Set<String> labels = new HashSet<String>();
	private Set<String> linguistic_signs = new HashSet<String>();
	private Set<String> exact_linguistic_signs = new HashSet<String>();
	private Set<String> fathers = new HashSet<String>();
	private Set<String> childrens = new HashSet<String>();
	private ArrayList<Individual> individuals = new ArrayList<Individual>();
	
	
	
	public String getSemanticField() {
		return semantic_field;
	}

	public void setSemanticField(String semantic_field) {
		this.semantic_field = semantic_field;
	}

	public Concept(){
		
	}
	
	public Concept(String uri, String localName){
		this.uri = uri;
		this.localName = localName;
	}
	
	public Concept(String localName){
		this.localName = localName;
	}
	
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getLocalName() {
		return localName;
	}
	public void setLocalName(String localName) {
		this.localName = localName;
	}
	public String getDefinition() {
		return definition;
	}
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	public Set<String> getLabels() {
		return labels;
	}
	public void setLabels(Set<String> labels) {
		this.labels = labels;
	}
	public void addLabel(String label) {
		this.labels.add(label);
	}
	public Set<String> getLinguistic_signs() {
		return linguistic_signs;
	}
	public void setLinguistic_signs(Set<String> linguistic_signs) {
		this.linguistic_signs = linguistic_signs;
	}
	public void addLinguistic_signs(String linguistic_sign) {
		this.linguistic_signs.add(linguistic_sign);
	}
	public Set<String> getExact_Linguistic_signs() {
		return exact_linguistic_signs;
	}
	public void setExact_Linguistic_signs(Set<String> exact_linguistic_signs) {
		this.exact_linguistic_signs = exact_linguistic_signs;
	}
	public void addExact_Linguistic_signs(String exact_linguistic_sign) {
		this.exact_linguistic_signs.add(exact_linguistic_sign);
	}
	
	public Set<String> getFathers() {
		return fathers;
	}

	public void setFathers(Set<String> fathers) {
		this.fathers = fathers;
	}
	
	public void addFather(String father) {
		this.fathers.add(father);
	}

	public Set<String> getChildrens() {
		return childrens;
	}

	public void setChildrens(Set<String> childrens) {
		this.childrens = childrens;
	}
	
	public void addChildren(String children) {
		this.childrens.add(children);
	}
	
	

	public ArrayList<Individual> getIndividuals() {
		return individuals;
	}

	public void setIndividuals(ArrayList<Individual> individuals) {
		this.individuals = individuals;
	}

	@Override
	public String toString() {
		return "Concept [uri=" + uri + ", localName=" + localName
				+ ", definition=" + definition + ", labels=" + labels
				+ ", linguistic_signs=" + linguistic_signs + ", fathers="
				+ fathers + ", childrens=" + childrens + ", individuals="
				+ individuals + "]";
	}

	

	
	
	
}
