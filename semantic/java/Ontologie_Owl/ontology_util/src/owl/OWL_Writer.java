package owl;

import java.io.File;

import util.FileIO;

public class OWL_Writer {
	
	public static void main(String [] args){
		
		String individual = "";
		File dir = new File("corpus");
		for(File f : dir.listFiles()){
			System.out.println(f.getName());
			String label = f.getName();
			label = label.replace(".csv", "");
			
			String ind = "  <Autorit� rdf:ID=\""+label+"\">\n"+
	    "<rdfs:label rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n"+
	    ">"+label+"</rdfs:label>\n"+
	    "<Champ_S�mantique rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\"\n"+
	    ">Autorit�</Champ_S�mantique>\n"+
	    "</Autorit�>\n";
		individual = individual+ind;
			
			
		}
		
		FileIO.writeFile(individual, "individual.txt", "UTF-8");
	}

}
