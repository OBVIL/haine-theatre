package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;



public class FileIO {

	@SuppressWarnings("static-access")
	public static String readFile(File f, String encoding)
	{
		ArrayList<Integer> corpus= new ArrayList<Integer>();
		
		try {
			InputStreamReader d=null;
			try {
				d = new InputStreamReader (new FileInputStream(f),encoding);
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			int c;
			
				try {
					c = d.read();
					 while(c!=-1){
					    //  System.out.print((char)c);
					      corpus.add(new Integer(c));
						 //corpus=corpus+(char)c;
					      c = d.read();
					 }
					 d.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
							
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    String c="";
   char [] val=new char[corpus.size()];
   for(int i=0;i<corpus.size();i++)
	  val[i]=(char)corpus.get(i).intValue();
     c=c.copyValueOf(val);
	 return c;
	}
	
	@SuppressWarnings("static-access")
	public static String readFile(File f)
	{
		ArrayList<Integer> corpus= new ArrayList<Integer>();
		
		try {
			InputStreamReader d=null;
			d = new InputStreamReader (new FileInputStream(f));
			int c;
			
				try {
					c = d.read();
					 while(c!=-1){
					    //  System.out.print((char)c);
					      corpus.add(new Integer(c));
						 //corpus=corpus+(char)c;
					      c = d.read();
					 }
					 d.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
							
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    String c="";
   char [] val=new char[corpus.size()];
   for(int i=0;i<corpus.size();i++)
	  val[i]=(char)corpus.get(i).intValue();
     c=c.copyValueOf(val);
	 return c;
	}
	
	public static File writeFile(String text, String path)
	{
		File result=new File(path);
		PrintWriter pw = null;
		 try {
				
				pw = new PrintWriter(result);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
			pw.print(text);
			pw.close();
	return result;
	}
	
	public static File writeFile(String text, String path,String encoding)
	{
		File result=new File(path);
		PrintWriter pw = null;
		 try {
				
				pw = new PrintWriter(result,encoding);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
			pw.print(text);
			pw.close();
	return result;
	}
	
	public static void makeDir(String dirPath)
	{
	    File directory = new File(dirPath);
	    if (directory.exists() && directory.isFile())
	    {
	        System.out.println("The dir with name could not be" +
	        " created as it is a normal file");
	    }
	    else
	    {
	        try
	        {
	            if (!directory.exists())
	            {
	                directory.mkdir();
	            }
	            String username = System.getProperty("user.name");
	            String filename = " path/" + username + ".txt"; //extension if you need one

	        }
	        catch (Exception e)
	        {
	            System.out.println("prompt for error");
	        }
	    }
	}
	
	public static void copyDirectory(String dirSource, String dirTarget){
		
		try {
			File srcDir = new File(dirSource);
			File destDir = new File(dirTarget);
			FileUtils.copyDirectory(srcDir, destDir);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void removeDirectory(String directory){
		try {
			FileUtils.deleteDirectory(new File(directory));
						
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void main(String [] args){
		
	}

}
