package util;

import java.io.File;
import java.util.HashMap;

public class Parameters {
	
	public static String UTF8 = "UTF-8";
		
	public static String INDIVIDUAL = "INDIVIDUAL";
	
	public static String CONCEPT = "CONCEPT";
	
	
	//plugins properties
	public static String PLUGINS_PROPERTIES_PATH = "plugins.properties";
	private static HashMap<String, String> PLUGINS_PROPERTIES = new HashMap<String, String>();
	private static String MORPHALOU_PATH = "MORPHALOU_PATH";
	private static String ONTOLOGY_PATH = "ONTOLOGY_PATH";
	private static String XML_DIR_RESULT_PATH = "XML_DIR_RESULT_PATH";
	
	
	private static HashMap<String, String> getPluginsProperties(){
		if (PLUGINS_PROPERTIES.isEmpty()){
			String properties = FileIO.readFile(new File(PLUGINS_PROPERTIES_PATH), UTF8);
			for(String property : properties.split("\n")){
				property = property.replace("\r", "");
				String name = property.split("=")[0].trim();
				String value = property.split("=")[1].trim();
				PLUGINS_PROPERTIES.put(name, value);
			}
				
		}
		
		return PLUGINS_PROPERTIES;
	}
	
	public static String GET_ONTOLOGY_PATH(){
		return getPluginsProperties().get(ONTOLOGY_PATH);
	}
	
	public static String GET_MORPHALOU_PATH(){
		return getPluginsProperties().get(MORPHALOU_PATH);
	}
	
	public static String GET_XML_DIR_RESULT_PATH(){
		return getPluginsProperties().get(XML_DIR_RESULT_PATH);
	}
	

}
