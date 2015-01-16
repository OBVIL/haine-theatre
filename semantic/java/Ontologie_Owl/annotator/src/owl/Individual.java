package owl;

import java.util.HashSet;
import java.util.Set;

public class Individual {
	private String uri = "";
	private String localName = "";
	private Set<String> labels = new HashSet<String>();
	private Set<String> concepts = new HashSet<String>();
	
	public Individual(){
		
	}
	
	public Individual(String uri, String localName){
		this.uri = uri;
		this.localName = localName;
	}
	
	public Individual(String localName){
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
	public Set<String> getLabels() {
		return labels;
	}
	public void setLabels(Set<String> labels) {
		this.labels = labels;
	}
	public void addLabel(String label) {
		this.labels.add(label);
	}
	public Set<String> getConcepts() {
		return concepts;
	}
	public void setConcepts(Set<String> concepts) {
		this.concepts = concepts;
	}
	public void addConcept(String concept) {
		this.concepts.add(concept);
	}

	@Override
	public String toString() {
		return "Individual [uri=" + uri + ", localName=" + localName
				+ ", labels=" + labels + ", concepts=" + concepts + "]";
	}
	
	
	
}
