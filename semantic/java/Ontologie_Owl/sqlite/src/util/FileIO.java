package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class FileIO {
	
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

}
