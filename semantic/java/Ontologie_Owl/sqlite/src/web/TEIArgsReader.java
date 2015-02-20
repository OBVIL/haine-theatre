package web;

import java.util.HashMap;

public class TEIArgsReader {
	
	private static final String TEI_PATH = "TEI_PATH";
	private static final String SQLITE_PATH = "SQLITE_PATH";
	private HashMap<String, String> arguments = new HashMap<String, String>();
	
	public TEIArgsReader(String [] args){
		for(String arg : args){
			String key = arg.split("=")[0].trim();
			String value = arg.split("=")[1].trim();
			arguments.put(key, value);
		}
	}
	
	public String getTEIPath(){
		return arguments.get(TEI_PATH);
	}
	
	public String getSQLITEPath(){
		return arguments.get(SQLITE_PATH);
	}
	
	public static void main(String [] args){
		
		
	}

}
