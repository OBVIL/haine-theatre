package tei;

public class Test {
	
	public static void main(String [] args){
		String text = "bonjour tout le père et mère Œuvre";
		 
		char[] chars = text.toCharArray();
		for(char e : chars){
			System.out.println(e);
		}
	}

}
