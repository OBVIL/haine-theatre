package web;


import java.util.HashMap;

public class ArgsReader {
	
	private static final String XML_PATH = "XML_PATH";
	private static final String PLUGINS_PROPERTIES_PATH = "PLUGINS_PROPERTIES_PATH";
	private HashMap<String, String> arguments = new HashMap<String, String>();
	
	public ArgsReader(String [] args){
		for(String arg : args){
			String key = arg.split("=")[0].trim();
			String value = arg.split("=")[1].trim();
			arguments.put(key, value);
		}
	}
	
	public String getXMLPath(){
		return arguments.get(XML_PATH);
	}
	
	public String getPluginPropertiesPath(){
		return arguments.get(PLUGINS_PROPERTIES_PATH);
	}
		
	@Override
	public String toString() {
		return "ArgsReader [getXMLPath()=" + getXMLPath()
				+ ", getPluginPropertiesPath()=" + getPluginPropertiesPath()
				+ "]";
	}

	public static void main(String [] args){
		
		
	}
}

