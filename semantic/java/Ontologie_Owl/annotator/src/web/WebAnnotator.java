package web;

import java.io.File;

import ir.TextAnnotator;
import util.FileIO;
import util.Parameters;

public class WebAnnotator {
	
	public static void main(String [] args){
		if(args.length == 2){
			ArgsReader argsReader = new ArgsReader(args);
			String xmlPath = argsReader.getXMLPath();
			Parameters.PLUGINS_PROPERTIES_PATH = argsReader.getPluginPropertiesPath();
			File file = new File(xmlPath);
			
			if(file.isDirectory()){
				for(File xmlFile : file.listFiles()){
					System.out.println("Annotating file "+xmlFile.getAbsolutePath());
					String text = TextAnnotator.annotateTEIFile(xmlFile);
					String path = Parameters.GET_XML_DIR_RESULT_PATH()+File.separator+xmlFile.getName();
					System.out.println("Saving annotated file in "+path);
					FileIO.writeFile(text, path, Parameters.UTF8);
				}
			}else{
				System.out.println("Annotating file "+file.getAbsolutePath());
				String text = TextAnnotator.annotateTEIFile(file);
				String path = Parameters.GET_XML_DIR_RESULT_PATH()+File.separator+file.getName();
				System.out.println("Saving annotated file in "+path);
				FileIO.writeFile(text, path, Parameters.UTF8);
			}
			
			
		}else{
			System.out.println("Command line syntax : WebAnnotator \"XML_PATH=path of the xml corpus or file\" \"PLUGINS_PROPERTIES_PATH=path of the plugins properties\"");
		}
	}

}
