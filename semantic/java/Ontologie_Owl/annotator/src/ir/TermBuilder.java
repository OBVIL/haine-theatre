package ir;

import java.util.ArrayList;
import java.util.List;

public class TermBuilder {
	
	
	public static void main(String [] args){
		ArrayList<String> list1 = new ArrayList<String>();
		ArrayList<String> list2 = new ArrayList<String>();
		ArrayList<String> list3 = new ArrayList<String>();
		List<List<String>> list = new ArrayList<List<String>>();
		
		list1.add("a1");
		list1.add("a2");
		
		list2.add("b1");
		list2.add("b2");
		list2.add("b3");
		
		list3.add("c1");
		list3.add("c2");
		list3.add("c3");
		
		
		list.add(list1);
		list.add(list2);
		list.add(list3);
		
		getAllTermsPossibilities(list);
	
	}
	
	public static List<String> getAllTermsPossibilities(List<List<String>> list){
		int size = list.size();
		List<String> terms = new ArrayList<String>();
		switch(size){
		case 1 : terms = buildLevel1(list); break;
		case 2 : terms = buildLevel2(list); break;
		case 3 : terms = buildLevel3(list); break;
		case 4 : terms = buildLevel4(list); break;
		case 5 : terms = buildLevel5(list); break;
		case 6 : terms = buildLevel6(list); break;
		case 7 : terms = buildLevel7(list); break;
		case 8 : terms = buildLevel8(list); break;
		
		}
		
		return terms ;
	}
	
	private static List<String> buildLevel1(List<List<String>> list){
		return list.get(0);
	}
	
	private static List<String> buildLevel2(List<List<String>> list){
		List<String> terms = new ArrayList<String>();
		for(String w1 : list.get(0)){
			for(String w2 : list.get(1)){
				//System.out.println(w1+" "+w2);
				terms.add(w1+" "+w2);
			}
			
		}
		return terms;
	}
	
	private static List<String> buildLevel3(List<List<String>> list){
		List<String> terms = new ArrayList<String>();
		for(String w1 : list.get(0)){
			for(String w2 : list.get(1)){
				for(String w3: list.get(2)){
					//System.out.println(w1+" "+w2+" "+w3);
					terms.add(w1+" "+w2+" "+w3);
				}
				
			}
			
		}
		return terms;
	}
	
	private static List<String> buildLevel4(List<List<String>> list){
		List<String> terms = new ArrayList<String>();
		for(String w1 : list.get(0)){
			for(String w2 : list.get(1)){
				for(String w3: list.get(2)){
					for(String w4: list.get(3)){
						//System.out.println(w1+" "+w2+" "+w3+" "+w4);
						terms.add(w1+" "+w2+" "+w3+" "+w4);
					}
					
				}
				
			}
			
		}
		return terms;
	}
	
	private static List<String> buildLevel5(List<List<String>> list){
		List<String> terms = new ArrayList<String>();
		for(String w1 : list.get(0)){
			for(String w2 : list.get(1)){
				for(String w3: list.get(2)){
					for(String w4: list.get(3)){
						for(String w5: list.get(4)){
							System.out.println(w1+" "+w2+" "+w3+" "+w4+" "+w5);
							terms.add(w1+" "+w2+" "+w3+" "+w4+" "+w5);
						}
						
					}
					
				}
				
			}
			
		}
		return terms;
	}
	
	private  static List<String> buildLevel6(List<List<String>> list){
		List<String> terms = new ArrayList<String>();
		for(String w1 : list.get(0)){
			for(String w2 : list.get(1)){
				for(String w3: list.get(2)){
					for(String w4: list.get(3)){
						for(String w5: list.get(4)){
							for(String w6: list.get(5)){
								//System.out.println(w1+" "+w2+" "+w3+" "+w4+" "+w5+" "+w6);
								terms.add(w1+" "+w2+" "+w3+" "+w4+" "+w5+" "+w6);
							}
							
						}
						
					}
					
				}
				
			}
			
		}
		return terms;
	}
	
	private static List<String> buildLevel7(List<List<String>> list){
		List<String> terms = new ArrayList<String>();
		for(String w1 : list.get(0)){
			for(String w2 : list.get(1)){
				for(String w3: list.get(2)){
					for(String w4: list.get(3)){
						for(String w5: list.get(4)){
							for(String w6: list.get(5)){
								for(String w7: list.get(6)){
									//System.out.println(w1+" "+w2+" "+w3+" "+w4+" "+w5+" "+w6+" "+w7);
									terms.add(w1+" "+w2+" "+w3+" "+w4+" "+w5+" "+w6+" "+w7);
								}
								
							}
							
						}
						
					}
					
				}
				
			}
			
		}
		return terms;
	}
	
	private static List<String> buildLevel8(List<List<String>> list){
		List<String> terms = new ArrayList<String>();
		for(String w1 : list.get(0)){
			for(String w2 : list.get(1)){
				for(String w3: list.get(2)){
					for(String w4: list.get(3)){
						for(String w5: list.get(4)){
							for(String w6: list.get(5)){
								for(String w7: list.get(6)){
									for(String w8 : list.get(7)){
										//System.out.println(w1+" "+w2+" "+w3+" "+w4+" "+w5+" "+w6+" "+w7);
										terms.add(w1+" "+w2+" "+w3+" "+w4+" "+w5+" "+w6+" "+w7+" "+w8);
									}
									
								}
								
							}
							
						}
						
					}
					
				}
				
			}
			
		}
		return terms;
	}




}
