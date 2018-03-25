package ApriorExample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Controller {
	
	int k = 2;
	
	ArrayList< ArrayList<String> > inputList = new ArrayList< ArrayList<String> >();
	
	ArrayList< HashMap< ArrayList<String>, Integer> > supportList = new ArrayList< HashMap< ArrayList<String>, Integer> > ();
	
	public Controller(String path) throws IOException {
		this.inputList = new InputGenerator().getInput(path);
		calcuateSingleSupport();
		generateSupportCount(supportList.get(0));
		//processQuery();
		//print();
	}
	
	
	private HashMap<String, Integer> uniqueProduct() {
		
		HashMap<String, Integer> uniqueMap = new HashMap<String, Integer> () ;
		
		for(int i=0;i<inputList.size();i++) 
			
			for(int j=0;j<inputList.get(i).size();j++) {
				if(uniqueMap.containsKey(inputList.get(i).get(j))) {continue;} 
				else uniqueMap.put(inputList.get(i).get(j), 1);
			}
		
		return uniqueMap;
	}
	
	
	private int checkSupportCount( ArrayList<String> itemList ) {
		int count = 0;
		for(int i=0;i<inputList.size();i++) {
			if(inputList.get(i).containsAll(itemList)) count++;
			else continue;
		}
		return count;
	}
	
	
	private void calcuateSingleSupport() {
		
		HashMap<String, Integer> uniqueMap = uniqueProduct() ;
		HashMap< ArrayList<String>, Integer> temp = new HashMap< ArrayList<String>, Integer>();
		
		int count;
		for( String key : uniqueMap.keySet() ) {
			count = checkSupportCount( new ArrayList<String>(Arrays.asList(key) ) );
			if( count >= k ) {
				
				temp.put(new ArrayList<String>(Arrays.asList(key) ), count);
			}
		}
		supportList.add(temp);
	}
	
	private int  calculateSupport( Set< ArrayList<String> > keySet){
		ArrayList< ArrayList<String> > temp = new ArrayList< ArrayList<String> > ();
		HashMap< ArrayList<String>, Integer> answer = new HashMap< ArrayList<String>, Integer> ();
		for( ArrayList<String> key: keySet) temp.add(key);
		int count;
		int length = supportList.size()+1;
		for(int i=0;i<temp.size();i++) {
			for(int j=i+1;j<temp.size();j++) {
				Set<String> s=new HashSet<String>();
				s.addAll(temp.get(i));
				s.addAll(temp.get(j));
				
				ArrayList<String> arr = new ArrayList<String>(s);
				
				ArrayList<String> copy = (ArrayList<String>) arr.clone();
				
				copy.remove(0);
				
				ArrayList<String> copy1 = (ArrayList<String>) arr.clone();
				
				copy1.remove(copy1.size()-1);
				
				
				System.out.println("previous  -------------------------"+arr.toString());
				
				
				if(supportList.get(supportList.size()-1).containsKey(copy)&& supportList.get(supportList.size()-1).containsKey(copy1) && supportList.get(supportList.size()-1).get(copy) >= k) {
					count = checkSupportCount(arr);
					if(count>=k) {
						answer.put(new ArrayList<String>(s), count);
						System.out.println("included "+arr);
					}
					
				}
				
			}
		}	
		if(answer.size()>0) {
			supportList.add(answer);
			return 1;
		}
		return 0;
	}

	private void generateSupportCount(HashMap< ArrayList<String>, Integer>  current) {
		int temp = calculateSupport(current.keySet());
		if(temp==1) generateSupportCount(supportList.get(supportList.size()-1));
		else return ;
	}
	
	
	private void processQuery() {
		
		ArrayList<ArrayList<String> > allKey = new ArrayList<ArrayList<String> > ();
		
		for(int i=0;i<supportList.size();i++) {
			HashMap< ArrayList<String>, Integer> temp = supportList.get(i);
			for( ArrayList<String> key : temp.keySet()) allKey.add(key);
		}
		
		for(int i=0;i<allKey.size();i++) {
			for(int j=0;j<allKey.size();j++) {
				ArrayList<String> all = new ArrayList<String>();
				all.addAll(allKey.get(i));
				all.addAll(allKey.get(j));
				
				int index = all.size() -1;
				int leftIndex = allKey.get(i).size()-1 ;
				
				if(index <supportList.size() && supportList.get(index).containsKey(all) && supportList.get(leftIndex).containsKey(allKey.get(i))) {
					double allSupport = supportList.get(index).get(all);
					double left = supportList.get(leftIndex).get(allKey.get(i));
					System.out.println(allKey.get(i).toString()+"--->"+allKey.get(j).toString()+"  "+allSupport / left);
				}
				
			}
		}
		
		
	}
	
	
	
	private void print(){
		//for(int i=0;i<inputList.size();i++) System.out.println(inputList.get(i).size());
		//System.out.println(supportList.size());
		for(int i=0;i<supportList.size();i++) {
			HashMap< ArrayList<String>, Integer> temp = supportList.get(i);
			
			for(ArrayList<String> key : temp.keySet()) {
				System.out.println(key.toString()+"---------->"+temp.get(key));
			}
		}
	}

}
